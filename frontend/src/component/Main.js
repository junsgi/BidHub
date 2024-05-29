import "../css/Main.css";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { convertSeconds, getAuctionItems, getCount, dot, bidding_api, getAuctionItemDetail, auctionClose } from "../Api";
import { Pagination, Button, Modal } from 'react-bootstrap';

import Auction from "./Auction";
import Home from "./Home";
const Main = () => {
    //paging start
    const [list, SetList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const length = useRef(0);
    const refresh = () => {
        getAuctionItems(SetList, currentPage);
        getCount(length, callback);
    } 
    useEffect(refresh, [currentPage])


    const onPageClick = (num) => setCurrentPage(num);
    const output = list.map((d, i) => {
        return <Item
            key={d.aitem_id}
            data={d}
            refresh = {refresh}
            top={i * 150}
        />
    })
    let [items, setItems] = useState([]);
    let callback = () => {
        let temp = [];
        for (let number = 1; number <= length.current; number++) {
            temp.push(
                <Pagination.Item key={number} active={number === currentPage} onClick={() => onPageClick(number)}>
                    {number}
                </Pagination.Item>,
            );
        }
        setItems(temp);
    }
    //paging end

    return (
        <div className="Main">
            <Auction
                list={list}
                items={items}
                output={output}
            />
            <Home />
        </div>
    );
}

export default Main;


function Item(props) {
    const id = props.data.aitem_id;
    const title = props.data.title;
    const current = dot(props.data.current);
    const immediate = dot(props.data.immediate);
    let [remaining, setRemaining] = useState(parseInt(props.data.remaining));
    const top = props.top;
    const [modalShow, setModalShow] = useState(false);
    let tickTock;
    useEffect(() => {
        tickTock = setInterval(() => {
            setRemaining(e => {
                if (e - 1 < 0) {
                    clearInterval(tickTock);
                    return 0;
                }
                return e - 1;
            });
        }, 1000);
        return () => {
            clearInterval(tickTock)
        };
    }, [])
    return (
        <>
            <tr className="item" style={{ top: `${top}px` }} onClick={() => setModalShow(true)}>
                <td className="img element">
                    <img src={`http://localhost:3977/auctionitem/img/${id}`} width={128} height={128}></img>
                </td>
                <td className="info element">
                    <h4>{title}</h4>
                    <div className="price">
                        <p>현재가 : {current}원</p>, &nbsp;
                        <p>즉시 구매가 : {immediate}원</p>
                    </div>
                    <div>
                        {
                            convertSeconds(remaining)
                        }
                    </div>
                </td>
            </tr>
            {
                modalShow &&
                <AuctionItemDetail
                    show={modalShow}
                    {...props}
                    remain={convertSeconds(remaining)}
                    onHide={() => { setModalShow(false) }}
                />
            }
        </>
    );

    
}


const AuctionItemDetail = (props) => {
    const [info, setInfo] = useState({
        aitemBid: "",
        aitemContent: "",
        aitemCurrent: "",
        aitemDate: "",
        aitemId: "",
        aitemImg: "",
        aitemImmediate: "",
        aitemStart: "",
        aitemTitle: "",
        memId: "",
    })
    const id = props.data.aitem_id;
    const title = props.data.title;
    const remaining = props.remain;
    const refreshInfo = () => {
        getAuctionItemDetail(id, setInfo);
        return () => {window.location.reload();}
    }
    useEffect(refreshInfo, []);

    const bidding = () => {
        let flag = window.confirm("입찰하시겠습니까?\n환불 x, 확인 후 취소 x");
        if (flag) {
            const data = {
                userId: sessionStorage.getItem("id"),
                itemId: id,
                current: info.aitemCurrent
            }
            bidding_api(data, refreshInfo, false)
        }
    }
    const biddingImm = () => {
        let flag = window.confirm("즉시 구매하시겠습니까?\n환불 x, 확인 후 취소 x");
        if (flag) {
            const data = {
                userId: sessionStorage.getItem("id"),
                itemId: id,
                current: info.aitemCurrent
            }
            bidding_api(data, refreshInfo, true)
        }
    }
    const biddingClose = () => {
        auctionClose({ itemId: id })
        
    };
    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {title}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <table>
                    <tbody>
                        <tr>
                            <td>
                                <img src={`http://localhost:3977/auctionitem/img/${id}`} width={256} height={256}></img>
                            </td>
                            <td>
                                <p>시작가 : {dot(info.aitemStart)}원</p>
                                <p>즉시 구매가 : {dot(info.aitemImmediate)}원</p>
                                <p>입찰 단위 : {dot(info.aitemBid)}원</p>
                                {info.aitemCurrent > 0 && <h3>현재가 : {dot(info.aitemCurrent)}원</h3>}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                판매자 : {info.memId}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                {info.aitemContent ?? "설명 없음"}
                            </td>
                        </tr>
                        <tr>
                            남은 시간 : {info.aitemDate.includes("1970") ? "종료된 경매" : remaining}
                        </tr>
                    </tbody>
                </table>
            </Modal.Body>
            <Modal.Footer>
                {
                    remaining !== "종료된 경매"
                        ? info.memId === sessionStorage.getItem("id")
                            ? <Button variant="warning" onClick={biddingClose} >경매 종료</Button>
                            : <>
                                <Button variant="danger" onClick={biddingImm} >즉시 구매</Button>
                                <Button variant="danger" onClick={bidding}>입찰</Button>
                            </>
                        : null
                }

                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}


import { useEffect, useState } from "react";
import "../css/Auction.css";
import { Pagination, Button, Modal } from 'react-bootstrap';
import { bidding_api, getAuctionItemDetail } from "../Api";
const Auction = ({ list, SetList }) => {

    //paging start
    const [currentPage, setCurrentPage] = useState(1);
    const onPageClick = (num) => setCurrentPage(num);
    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <Pagination.Item key={number} active={number === currentPage} onClick={() => onPageClick(number)}>
                {number}
            </Pagination.Item>,
        );
    }
    //paging end

    return (
        <>
            <table className="Auction">
                <tbody >
                    {
                        list.map((d, i) => {
                            return <Item
                                key={d.aitem_id}
                                data={d}
                                top={i * 160}
                            />
                        })
                    }
                </tbody>
                <tfoot>
                    <tr colSpan={2}>
                        <Pagination>{items}</Pagination>
                    </tr>
                </tfoot>
            </table>
        </>
    );
}

export default Auction;

function Item(props) {
    const id = props.data.aitem_id;
    const title = props.data.title;
    const current = props.data.current;
    const immediate = props.data.immediate;
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

        return () => clearInterval(tickTock);
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
                        <p>현재가 : {current}</p>, &nbsp;
                        <p>즉시 구매가 : {immediate}</p>
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
                />}
        </>
    );

    function convertSeconds(seconds) {
        if (seconds <= 0) return "종료된 경매"
        const MINUTE = 60;
        const HOUR = 3600;
        const DAY = 86400;

        let days = 0;
        let hours = 0;
        let minutes = 0;
        let remainingSeconds = seconds;

        // days
        days = Math.floor(remainingSeconds / DAY);
        remainingSeconds %= DAY;

        // hours
        hours = Math.floor(remainingSeconds / HOUR);
        remainingSeconds %= HOUR;

        // minutes
        minutes = Math.floor(remainingSeconds / MINUTE);
        remainingSeconds %= MINUTE;

        return `${days}일 ${hours}시간 ${minutes}분 ${remainingSeconds}초 남음`;
    }
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
                                <p>시작가 : {info.aitemStart}원</p>
                                <p>즉시 구매가 : {info.aitemImmediate}원</p>
                                <p>입찰 단위 : {info.aitemBid}원</p>
                                {info.aitemCurrent > 0 && <h3>현재가 : {info.aitemCurrent}원</h3>}
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
                            남은 시간 : {remaining}
                        </tr>
                    </tbody>
                </table>
            </Modal.Body>
            <Modal.Footer>
                {
                    remaining !== "종료된 경매" 
                    ?   info.memId === sessionStorage.getItem("id")
                        ? <Button variant="warning" >경매 종료</Button>
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

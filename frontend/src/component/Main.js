import "../css/Main.css";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { convertSeconds, getAuctionItems, getCount, dot, bidding_api, getAuctionItemDetail, auctionClose } from "../Api";
import { Pagination, Button, Modal } from 'react-bootstrap';
import AuctionItemDetail from "./AuctionItemDetail";
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
                items={items}
                output={output}
            />
            <Home 
                refresh = {refresh}
            />
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
                    <h4><b>{title}</b></h4>
                    <div className="price">
                        <p>현재가 : {current}원</p>
                        {immediate && <p>, &nbsp;즉시 구매가 : {immediate}원</p>}
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
import "../css/Main.css";
import { useEffect, useRef, useState } from "react";
import { getAuctionItems, getCount } from "../Api";
import { Pagination } from 'react-bootstrap';
import Auction from "./Auction";
import Home from "./Home";
import AuctionItem from "./AuctionItem";
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
        return <AuctionItem
            key={d.aitem_id}
            data={d}
            // refresh = {refresh}
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
import "../css/Main.css";
import React, { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { getAuctionItems, getCount } from "../Api";
import { Pagination } from 'react-bootstrap';
import Auction from "./Auction";
import Home from "./Home";
import AuctionItem from "./AuctionItem";

export const REFRESH = React.createContext();

const Main = () => {
    //paging start
    const [list, SetList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [sort, setSort] = useState(0);
    const length = useRef(0);
    const refresh = useCallback(() => {
        console.log("auctionList refresh")
        getAuctionItems(SetList, currentPage, sort, length, callback);
    }, [currentPage, sort]);

    const refreshDispatch = useMemo(()=>{return {refresh}}, [currentPage, sort])

    const onPageClick = (num) => setCurrentPage(num);
    const output = list.map((d, i) => {
        return <AuctionItem
            key={d.aitem_id}
            data={d}
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
    const onClick = e => setSort(parseInt(e.target.name))

    useEffect(refresh, [currentPage, sort])

    return (
        <div className="Main">
            <REFRESH.Provider value = {refreshDispatch} >
            <Auction
                items={items}
                output={output}
                onClick={onClick}
            />
            <Home />
            </REFRESH.Provider>
        </div>
    );
}

export default Main;
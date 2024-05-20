import "../css/Main.css";
import { useEffect, useState } from "react";
import { getAuctionItems } from "../Api";
import Auction from "./Auction";
import Home from "./Home";
const Main = () => {
    const [list, SetList] = useState([]);
    useEffect(() => {
        getAuctionItems(SetList);
    }, []);

    
    return (
        <div className="Main">
            <Auction 
                list = {list}
                SetList = {SetList}
            />
            <Home />
        </div>
    );
}

export default Main;
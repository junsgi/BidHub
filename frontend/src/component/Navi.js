import "../App.css";
import React from "react";
const Navi = () => {
    return (
        <nav onClick={()=>alert("BidHub!")}>
            <span className="Bid">Bid</span>
            <span className="Hub">Hub</span>
        </nav>
    );
}
export default React.memo(Navi);
import AuctionItemDetail from "../modal/AuctionItemDetail";
import React, {  useState } from "react";
import { convertSeconds,dot } from "../Api";
import { useNavigate } from "react-router-dom";

const WinItem = (props) => {
    const navi = useNavigate();
    const id = props.data.aitem_id;
    const title = props.data.title;
    const current = dot(props.data.current);
    const immediate = dot(props.data.immediate);
    const status = props.data.status === "0" ? "완료" : "대기";
    const top = props.top;
    const [modalShow, setModalShow] = useState(false);
    return (
        <>
            <tr className="item" style={{ top: `${top}px` }} onClick={() => navi(`/mypage/${id}`)}>
                <td className="img element">
                    <img src={`http://localhost:3977/auctionitem/img/${id}`} width={128} height={128}></img>
                </td>
                <td className="info element">
                    <h4>{title}</h4>
                    <div className="price">
                        <div>현재가 : {current}원</div> 
                        {immediate && <div>즉시 구매가 : {immediate}원</div>}
                    </div>
                    <div>
                        결제 {status}
                    </div>
                </td>
            </tr>
            {
                modalShow &&
                <AuctionItemDetail
                    show={modalShow}
                    {...props}
                    remain={convertSeconds(-1)}
                    onHide={() => { setModalShow(false) }}
                    flag = {"true"}
                />
            }
        </>
    );
};
export default WinItem;
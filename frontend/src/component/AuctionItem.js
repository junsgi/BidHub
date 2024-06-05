import React, { useContext, useEffect, useState } from "react";
import { convertSeconds, dot } from "../Api";
import AuctionItemDetail from "../modal/AuctionItemDetail";
import { REFRESH } from "./Main";
function AuctionItem(props) {

    const { refresh } = useContext(REFRESH)
    const id = props.data.aitem_id;
    const title = props.data.title;
    const current = dot(props.data.current);
    const immediate = dot(props.data.immediate);
    let [remaining, setRemaining] = useState(parseInt(props.data.remaining));
    const top = props.top;
    const [modalShow, setModalShow] = useState(false);
    let tickTock;
    useEffect(() => {
        // console.log(`auctionItem load title : ${title}`)
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
    // console.log(`auctionItem updated title : ${title}`)
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
                    setRemaining = {setRemaining}
                    onHide={() => { setModalShow(false); refresh() }}
                />
            }
        </>
    );


}

export default React.memo(AuctionItem);
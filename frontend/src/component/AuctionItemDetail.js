import {useEffect,  useState } from "react";
import { dot, bidding_api, getAuctionItemDetail, auctionClose } from "../Api";
import {  Button, Modal } from 'react-bootstrap';

import Auction from "./Auction";
import Home from "./Home";
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
export default AuctionItemDetail;
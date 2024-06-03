import { useContext, useEffect, useState } from "react";
import { dot, bidding_api, getAuctionItemDetail, auctionClose, bidPayment } from "../Api";
import { Button, Modal } from 'react-bootstrap';
import {P} from "../App";
const AuctionItemDetail = (props) => {
    const {setpoint} = useContext(P);

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
    const status = props.data.status;
    const flag = props.flag; // 낙찰 목록인지 아닌지
    const refreshInfo = () => {
        getAuctionItemDetail(id, setInfo);
    }
    useEffect(() => {
        getAuctionItemDetail(id, setInfo);
    }, [id]);

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

    const payment = () => {
        const data = {
            userId: sessionStorage.getItem("id"),
            itemId: id,
        }
        bidPayment(data, setpoint)
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
                                <img src={`http://localhost:3977/auctionitem/img/${id}`} alt="bidhub" width={256} height={256}></img>
                            </td>
                            <td>
                                <p>시작가 : {dot(info.aitemStart)}원</p>
                                {info.aitemImmediate && <p>즉시 구매가 : {dot(info.aitemImmediate)}원</p>}
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
                            <td>
                                남은 시간 : {info.aitemDate.includes("1970") ? "종료된 경매" : remaining}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </Modal.Body>
            <Modal.Footer>
                {
                    !sessionStorage.getItem("id")
                        ? "로그인 후 이용 가능합니다."
                        : remaining !== "종료된 경매"
                            ? info.memId === sessionStorage.getItem("id")
                                ? <Button variant="warning" onClick={biddingClose} >경매 종료</Button>
                                : <>
                                    {info.aitemImmediate && <Button variant="danger" onClick={biddingImm} >즉시 구매</Button>}
                                    <Button variant="danger" onClick={bidding}>입찰</Button>
                                  </>
                            : null
                }
                {flag && status==="1" && <Button onClick={payment}>결제</Button>}
                <Button onClick={props.onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
}
export default AuctionItemDetail;
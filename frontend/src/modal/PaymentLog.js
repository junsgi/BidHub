import { Button, Modal, Table } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import { getPaymentLog } from "../Api";

<div
    // {...props}
    size="lg"
    aria-labelledby="contained-modal-title-vcenter"
    centered
>
    <Modal.Header closeButton>
        <h1>포인트 충전 내역</h1>
    </Modal.Header>
    <Modal.Body>
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>승인 시간</th>
                    <th>주문 시간</th>
                    <th>결제 아이디</th>
                    <th>금액</th>
                </tr>
            </thead>
            <tbody>
                {/* {list} */}
            </tbody>
        </Table>
    </Modal.Body>
</div>
function PaymentLog() {
    const [list, SetList] = useState([]);
    useEffect(() => {
        getPaymentLog(SetList)
    }, [])
    return (
        <div className="overflow-x-auto table-lg w-2/4 m-auto ">
            <p className="text-3xl font-bold mb-4">충전 내역</p>
            <table className="table text-center">
                {/* head */}
                <thead>
                    <tr>
                        <th className="text-3xl">주문 시간</th>
                        <th className="text-3xl">승인 시간</th>
                        <th className="text-3xl">결제 아이디</th>
                        <th className="text-3xl">금액</th>
                    </tr>
                </thead>
                <tbody>
                    {list}
                </tbody>
            </table>
        </div>
    );
}
export default PaymentLog;
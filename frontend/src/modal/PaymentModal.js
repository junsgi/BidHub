import { Button, Modal, Table } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import { getPaymentLog } from "../Api";


function PaymentModal(props) {
    const [list, SetList] = useState([]);
    useEffect(() => {
        getPaymentLog(SetList)
    }, [])
    return (
        <Modal
            {...props}
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
                        {list}
                    </tbody>
                </Table>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>닫기</Button>
            </Modal.Footer>
        </Modal>
    );
}
export default PaymentModal;
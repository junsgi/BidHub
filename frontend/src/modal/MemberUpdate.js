import { Button, Modal, InputGroup, Form } from "react-bootstrap";
import React from "react";
import { dot } from "../Api";

function MemberUpdate(props) {
    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <h1>정보 수정</h1>
            </Modal.Header>
            <Modal.Body>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">닉네임</InputGroup.Text>
                    <Form.Control
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                    />
                    <Button onClick={props.onHide}>수정</Button>

                </InputGroup>
                <hr />
                <hr />
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1"> 기존 비밀번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
                    <Form.Control
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                    />
                </InputGroup>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">새 비밀번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
                    <Form.Control
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                    />
                </InputGroup>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">비밀번호 확인&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </InputGroup.Text>
                    <Form.Control
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                    />
                    <Button onClick={props.onHide}>수정</Button>
                </InputGroup>

            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>닫기</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default MemberUpdate;
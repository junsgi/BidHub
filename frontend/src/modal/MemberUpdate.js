import { Button, Modal, InputGroup, Form } from "react-bootstrap";
import React, { useMemo, useReducer } from "react";
import { updateNickOrPasswd } from "../Api";

const reducer = (state, action) => {
    return {...state, [action.name] : action.value };
}

function MemberUpdate(props) {
    const [update, dispatch] = useReducer(reducer, {nick : "", pw : "", newPw : "", check : ""});
    const onChange = e => dispatch({ name : e.target.name, value : e.target.value})
    const ID = useMemo(()=>sessionStorage.getItem("id"), []);
    const onClick = e => {
        const PATH = e.target.name;
        const DATA = {
            id : ID,
            before : "",
            after : ""
        }
        if (PATH === "nickname") {
            if (!update.nick) alert("새 닉네임을 입력해주세요");
            else {
                DATA.after = update.nick;
                updateNickOrPasswd(DATA, PATH);
            }
        }else {
            let flag = update.pw && update.newPw && update.check;
            if (!flag) alert("비밀번호를 입력해주세요");
            else if (update.newPw !== update.check) alert("새 비밀번호가 틀렸습니다.");
            else {
                DATA.before = update.pw;
                DATA.after = update.newPw;
                updateNickOrPasswd(DATA, PATH);
            }
            
        }
    }
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
                <h4>닉네임 수정</h4>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">새 닉네임</InputGroup.Text>
                    <Form.Control
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                        name="nick"
                        onChange={onChange}
                    />
                    <Button name = "nickname" onClick={onClick}>수정</Button>

                </InputGroup>
                <hr />
                <h4>비밀번호 수정</h4>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1"> 기존 비밀번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
                    <Form.Control
                        type="password"
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                        name="pw"
                        onChange={onChange}
                    />
                </InputGroup>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">새 비밀번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</InputGroup.Text>
                    <Form.Control
                        type="password"
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                        name="newPw"
                        onChange={onChange}
                    />
                </InputGroup>
                <InputGroup className="mb-3">
                    <InputGroup.Text id="basic-addon1">비밀번호 확인&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </InputGroup.Text>
                    <Form.Control
                        type="password"
                        aria-label="Username"
                        aria-describedby="basic-addon1"
                        name="check"
                        onChange={onChange}
                    />
                    <Button name = "passwd" onClick={onClick}>수정</Button>
                </InputGroup>

            </Modal.Body>
            <Modal.Footer>
                <Button onClick={props.onHide}>닫기</Button>
            </Modal.Footer>
        </Modal>
    );
}

export default MemberUpdate;
import {Form, InputGroup} from 'react-bootstrap';
import '../css/MyPage.css';
import { useState } from 'react';
const MyPage = (props) => {
    const SetStatus = props.SetStatus;
    const NICK = sessionStorage.getItem("nickname") ?? "null";
    const logout = () => {
        sessionStorage.clear();
        SetStatus("guest");
    }
    const [point, SetPoint] = useState(sessionStorage.getItem("point") ?? NaN);

    return (
        <div className="mypage">
            <h2>{NICK}님&nbsp;<sub className='logout' onClick={logout}>로그아웃</sub></h2>
            <InputGroup className="mb-3">
                <InputGroup.Text id="basic-addon1">아이디</InputGroup.Text>
                <Form.Control
                    placeholder="Username"
                    aria-label="Username"
                    aria-describedby="basic-addon1"
                    value = {sessionStorage.getItem("id")}
                    disabled
                    readOnly
                />
            </InputGroup>
            <InputGroup className="mb-3">
                <InputGroup.Text id="basic-addon1">포인트</InputGroup.Text>
                <Form.Control
                    placeholder="Username"
                    aria-label="Username"
                    aria-describedby="basic-addon1"
                    value = {point}
                    disabled
                    readOnly
                />
            </InputGroup>
        </div>
    );
}

export default MyPage;
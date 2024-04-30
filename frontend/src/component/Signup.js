import { useState } from 'react';
import { Button, Form, InputGroup } from 'react-bootstrap';
import { signup } from '../Api';
const Signup = (props) => {
    const rightBnt = {
        textAlign: 'right',
        width: '100%',
        paddingLeft: "5px"
    }
    const [validated, setValidated] = useState(false);
    const [CK, SetCK] = useState("");
    const [NICK, SetNICK] = useState("");
    const [control, SetControl] = useState([false, false, false, false]);
    const [message, SetMessage] = useState(["", "", "", ""]);
    const [ID, SetID] = useState("");
    const [PW, SetPW] = useState("");
    const SetStatus = props.SetStatus;
    
    const idHandler = e => SetID(e.target.value);
    const pwHandler = e => SetPW(e.target.value);
    const ckHandler = e => SetCK(e.target.value);
    const nickHandler = e => SetNICK(e.target.value);

    const handleSubmit = (event) => {
        event.preventDefault(); // 기본 폼 제출 동작을 막음
        let con = [...control];
        let mes = [...message];
        if (!ID) {
            con[0] = true;
            mes[0] = "아이디를 입력해주세요";
        }else {
            con[0] = false;
            mes[0] = "";
        }

        if (!NICK) {
            if (!con[0]) { // 아이디가 있다면
                SetNICK(e => ID)
                con[1] = false;
                mes[1] = "";
            }
        }else {
            con[1] = false;
            mes[1] = "";
        }

        if (!PW) {
            con[2] = true;
            mes[2] = "비밀번호를 입력해주세요";
        }else {
            con[2] = false;
            mes[2] = "";
        }

        if (!CK || PW !== CK) {
            con[3] = true;
            mes[3] = "입력한 비밀번호와 다릅니다.";
        }else {
            con[3] = false;
            mes[3] = "";
        }

        SetControl(e => con);
        SetMessage(e => mes);
        if (!(con[0] || con[1] || con[2] || con[3])) {
            let data = {
                id : ID,
                nickname : NICK,
                pw : PW
            }
            signup(data, SetMessage, SetControl, SetStatus);
        }
        setValidated(true);
    };


    return (
        <Form className='SignupForm' noValidate validated={validated} onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="validationCustomId">
                <Form.Label>ID</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        required = {control[0]} isInvalid = {control[0]} type="text" placeholder="아이디를 입력해 주세요" onChange={idHandler} value = {ID} />
                    <Form.Control.Feedback type='invalid'><b>{message[0]}</b></Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>닉네임</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        required = {control[1]} isInvalid = {control[1]} type="text" placeholder="닉네임을 입력해 주세요" onChange={nickHandler} value = {NICK} />
                    <Form.Control.Feedback type='invalid'><b>{message[1]}</b></Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3" controlId="validationCustomPW">
                <Form.Label>비밀번호</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        required = {control[2]} isInvalid = {control[2]} type="password" placeholder="비밀번호를 입력해 주세요" onChange={pwHandler} value = {PW} />
                    <Form.Control.Feedback type='invalid'><b>{message[2]}</b></Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3" controlId="validationCustomPWCheck">
                <Form.Label>비밀번호 재입력</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        
                        aria-describedby="inputGroupPrepend"
                        required = {control[3]} isInvalid = {control[3]} type="password" placeholder="비밀번호를 재입력해 주세요" onChange={ckHandler} value = {CK} />
                    <Form.Control.Feedback type='invalid'><b>{message[3]}</b></Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <div style={rightBnt}>
                <Button variant="dark" type="button" onClick={()=>SetStatus("guest")}>
                    돌아가기
                </Button> &nbsp;
                <Button variant="dark" type="submit">
                    회원가입
                </Button>
            </div>
        </Form>
    );
}

export default Signup;
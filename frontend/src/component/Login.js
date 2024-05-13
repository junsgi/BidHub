import { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { login } from '../Api';
const Login = (props) => {
    const rightBnt = {
        textAlign : 'right',
        width : '100%',
        paddingLeft : "5px"
    }
    const [data, setData] = useState({
        id : "",
        pw : ""
    })
    const dataHandler = (e) => setData({...data, [e.target.name] : e.target.value})
    const submit = () => login(data, SetStatus);
    const SetStatus = props.SetStatus;
    return (
        <Form className='LoginForm'>
            <Form.Group className="mb-3" controlId="formGroupid">
                <Form.Label>ID</Form.Label>
                <Form.Control type="text" placeholder="아이디를 입력해 주세요" value = {data.id} name = "id" onChange={dataHandler}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="비밀번호를 입력해 주세요" value = {data.pw} name = "pw" onChange={dataHandler}/>
            </Form.Group>
            <div style={rightBnt}>
                <Button variant="dark" type="button" onClick={()=>SetStatus("signup")}>
                    회원가입
                </Button> &nbsp;
                <Button variant="dark" type="button" onClick={submit}>
                    로그인
                </Button>
            </div>
        </Form>
    );
}

export default Login;
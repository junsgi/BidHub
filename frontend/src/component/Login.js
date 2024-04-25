import { Button, Form } from 'react-bootstrap';
const Login = (props) => {
    const rightBnt = {
        textAlign : 'right',
        width : '100%',
        paddingLeft : "5px"
    }
    const idHandler = props.idHandler;
    const pwHandler = props.pwHandler;
    const submit = props.submit;
    const statusHandler = props.statusHandler;
    return (
        <Form className='LoginForm'>
            <Form.Group className="mb-3" controlId="formGroupid">
                <Form.Label>ID</Form.Label>
                <Form.Control type="text" placeholder="아이디를 입력해 주세요" onChange={idHandler}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formGroupPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="비밀번호를 입력해 주세요" onChange={pwHandler}/>
            </Form.Group>
            <div style={rightBnt}>
                <Button variant="dark" type="button" onClick={()=>statusHandler("signup")}>
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
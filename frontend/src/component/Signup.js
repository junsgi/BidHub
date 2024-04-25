import { useState } from 'react';
import { Button, Form, InputGroup } from 'react-bootstrap';
const Signup = (props) => {
    const rightBnt = {
        textAlign: 'right',
        width: '100%',
        paddingLeft: "5px"
    }
    const [validated, setValidated] = useState(false);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        setValidated(true);
    };
    const idHandler = props.idHandler;
    const pwHandler = props.pwHandler;
    const statusHandler = props.statusHandler;


    return (
        <Form className='SignupForm' noValidate validated={validated} onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="validationCustomId">
                <Form.Label>ID</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        required = {false} type="text" placeholder="아이디를 입력해 주세요" onChange={idHandler} />
                    <Form.Control.Feedback type='invalid'>아이디가 중복입니다.</Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3">
                <Form.Label>닉네임</Form.Label>
                <InputGroup>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        type="text" placeholder="닉네임을 입력해 주세요" onChange={idHandler} />
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3" controlId="validationCustomPW">
                <Form.Label>비밀번호</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        type="password" placeholder="아이디를 입력해 주세요" onChange={idHandler} />
                </InputGroup>
            </Form.Group>

            <Form.Group className="mb-3" controlId="validationCustomPWCheck">
                <Form.Label>비밀번호 재입력</Form.Label>
                <InputGroup hasValidation>
                    <Form.Control 
                        aria-describedby="inputGroupPrepend"
                        required = {false} type="password" placeholder="아이디를 입력해 주세요" onChange={idHandler} />
                    <Form.Control.Feedback type='invalid'>비밀번호가 일치하지 않습니다.</Form.Control.Feedback>
                </InputGroup>
            </Form.Group>

            <div style={rightBnt}>
                <Button variant="dark" type="submit">
                    회원가입
                </Button>
            </div>
        </Form>
    );
}

export default Signup;
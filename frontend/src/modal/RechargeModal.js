import { Form, Button, Modal } from "react-bootstrap";
import React, { useEffect} from "react";
import { dot } from "../Api";

function RechargeModal(props) {
    useEffect(() => {
      return () => {
        props.SetMoney(0);
      }
    }, [])
    return (
      <Modal
        {...props}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Header closeButton>
        </Modal.Header>
        <Modal.Body>
          <h4>충전 <sub>{dot(props.money)}</sub></h4>
          <Form className='LoginForm' onSubmit={event => event.preventDefault()} >
            <Form.Group className="mb-3" controlId="formGroupid">
              <Form.Control type="text" placeholder="금액을 입력해 주세요" onChange={props.onChange} onKeyDown={e => {if (e.key === "Enter") e.stopPropagation()}} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={props.onHide}>닫기</Button>
          <Button onClick={props.submit}>충전</Button>
        </Modal.Footer>
      </Modal>
    );
  }
  
export default RechargeModal;
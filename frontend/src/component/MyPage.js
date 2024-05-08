import { Form, InputGroup, Button, Modal } from "react-bootstrap";
import "../css/MyPage.css";
import { useEffect, useState } from "react";
import { recharge } from "../Api";
const MyPage = (props) => {
  const SetStatus = props.SetStatus;
  const NICK = sessionStorage.getItem("nickname") ?? "null";
  const logout = () => {
    sessionStorage.clear();
    SetStatus("guest");
  };
  const [point, SetPoint] = useState(sessionStorage.getItem("point") ?? NaN);
  const [money, SetMoney] = useState(0);
  const [pointModal, SetPointModal] = useState(false);
  const linkKakao = () => SetPointModal(e => !e);
  const inputMoney = (e) => {
    SetMoney(e.target.value);
  }
  const submit = () => {
    if (!money || money <= 0) alert('금액을 입력해주세요!');
    else if (isNaN(money)) alert("숫자만 입력해주세요!");
    else {
      let data = {
        partner_user_id : sessionStorage.getItem("id"),
        total_amount : parseInt(money)
      };
      recharge(data);
    }
  }
  
  return (
    <div className="mypage">
      <div className="myInfo">
        <h2>
          {NICK}님&nbsp;
          <sub className="logout" onClick={logout}>
            로그아웃
          </sub>
        </h2>
        <InputGroup className="mb-3">
          <InputGroup.Text id="basic-addon1">아이디</InputGroup.Text>
          <Form.Control
            placeholder="Username"
            aria-label="Username"
            aria-describedby="basic-addon1"
            value={sessionStorage.getItem("id")}
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
            value={point}
            disabled
            readOnly
          />
        </InputGroup>
      </div>
      <div className="btns">
        <Button variant="dark">정보 수정</Button>
        <Button variant="dark">경매 등록</Button>
        <Button variant="dark" onClick={linkKakao}>포인트</Button>
      </div>
      <table className="mySuccessTable">
        <thead>
          <th colSpan={2}>
            <h3>내 낙찰 목록</h3>
          </th>
        </thead>
        <tbody>
          {
            [1, 2].map((e, i) => {
              return (
                <MyItem key={i} />
              )
            })
          }
        </tbody>
      </table>
      {
        pointModal &&
        <PointModal
          show={pointModal}
          onHide={() => SetPointModal(false)}
          onChange = {inputMoney}
          submit = {submit}
        />
      }
    </div>
  );
};

export default MyPage;

const MyItem = () => {
  return (
    <tr className="item">
      <td className="img">
        <img src={process.env.PUBLIC_URL + "/img/bidhub.png"}></img>
      </td>
      <td className="info">
        <h6>제목</h6>
        <div className="price">
          <div>낙찰 금액 : </div>
          <div>낙찰 날짜 : </div>
        </div>
      </td>
    </tr>
  );
};


function PointModal(props) {
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
        <h4>충전</h4>
        <Form className='LoginForm' onSubmit={event => event.preventDefault()} >
          <Form.Group className="mb-3" controlId="formGroupid">
            <Form.Control type="text" placeholder="금액을 입력해 주세요" onChange={props.onChange} onKeyDown={e => e.key === "Enter" ? e.stopPropagation() : null }/>
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
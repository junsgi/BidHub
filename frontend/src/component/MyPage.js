import { Form, InputGroup, Button, Modal } from "react-bootstrap";
import "../css/MyPage.css";
import React, { useEffect, useState, useContext, useMemo, useCallback } from "react";
import { getSucItems, recharge, dot, without } from "../Api";
import { P } from "../App";
import RechargeModal from "../modal/RechargeModal";
import MemberUpdate from "../modal/MemberUpdate";
import PaymentModal from "../modal/PaymentModal";
const MyPage = ({ SetStatus }) => {
  const NICK = sessionStorage.getItem("nickname") ?? "null";
  const { point, setpoint } = useContext(P);
  const pointAddDot = useMemo(()=>dot(point), [point]);

  const [pointModal, SetPointModal] = useState(false);
  const [updateModal, SetUpdateModal] = useState(false);
  const [paymentModal, SetPaymentModal] = useState(false);

  const logout = () => {
    sessionStorage.clear();
    SetStatus("guest");
  };

  const regist = () => {
    SetStatus("regist");
  }


  const userId = sessionStorage.getItem("id") ?? "Null";
  const [money, SetMoney] = useState(0);
  const [SucList, SetList] = useState([]);

  const linkKakao = () => SetPointModal(e => !e);
  const inputMoney = e => SetMoney(e.target.value);
  const paymentModalOnOff = () => SetPaymentModal(e => !e)

  const submit = () => {
    if (!money || money <= 0) alert('금액을 입력해주세요!');
    else if (isNaN(money)) alert("숫자만 입력해주세요!");
    else {
      let data = {
        partner_user_id: userId,
        total_amount: parseInt(money)
      };
      recharge(data);
      SetPointModal(false);
    }
  }
  const updateClick = useCallback(() => SetUpdateModal(e => !e), [])


  useEffect(() => {
    console.log("mypage mounted");
    // 메시지 이벤트 핸들러 등록
    window.addEventListener('message', function (event) {
      setpoint();
    });
    setpoint();
    getSucItems(SetList);
  }, []);
  console.log("mypage updated");

  return (
    <div className="mypage">
      <div className="myInfo">
        <h2>
          {NICK}님 &nbsp;
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
            value={userId || "userId"}
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
            value={pointAddDot || 0}
            disabled
            readOnly
          />
        </InputGroup>
      </div>
      <div className="btns">
        <Button variant="dark" onClick={updateClick}>정보 수정</Button>
        <Button variant="dark" onClick={regist}>경매 등록</Button>
        <Button variant="dark" onClick={linkKakao}>포인트</Button>
        <Button variant="dark" onClick={paymentModalOnOff}>충전 내역</Button>
      </div>
      <br />
      <table className="mySuccessTable">
        <thead>
          <tr>
            <th colSpan={2}>
              <h3>내 낙찰 목록</h3>
            </th>
          </tr>
        </thead>
        <tbody>
          {SucList}
        </tbody>
      </table>
      <hr />
      <Button variant="dark" onClick={() => without(logout)}>회원 탈퇴</Button>
      {
        pointModal &&
        <RechargeModal
          show={pointModal}
          onHide={() => SetPointModal(false)}
          onChange={inputMoney}
          money={money}
          submit={submit}
          SetMoney={SetMoney}
        />
      }
      {
        updateModal && 
        <MemberUpdate 
          show={updateModal}
          onHide={() => SetUpdateModal(false)}
        />
      }
      {
        updateModal && 
        <MemberUpdate 
          show={updateModal}
          onHide={() => SetUpdateModal(false)}
        />
      }
      {
        paymentModal &&
        <PaymentModal
          show = {paymentModal}
          onHide={() => SetPaymentModal(false)}
        />
      }
    </div>
  );
};


export default MyPage;

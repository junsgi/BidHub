import { Form, InputGroup, Button } from "react-bootstrap";
import "../css/MyPage.css";
import { useState } from "react";
const MyPage = (props) => {
  const SetStatus = props.SetStatus;
  const NICK = sessionStorage.getItem("nickname") ?? "null";
  const logout = () => {
    sessionStorage.clear();
    SetStatus("guest");
  };
  const [point, SetPoint] = useState(sessionStorage.getItem("point") ?? NaN);

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
        <Button variant="dark">포인트</Button>
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
                <MyItem key = {i}/>
              )
            })
          }
        </tbody>
      </table>
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

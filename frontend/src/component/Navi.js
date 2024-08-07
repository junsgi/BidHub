import React, { useContext } from "react";
import { Link } from "react-router-dom";
import USER from "../context/userInfo";
const Navi = ({ logout }) => {
  const {user} = useContext(USER);
  const {id, nickname} = user;
  return (
    <div className="navbar bg-base-100">
      <div className="flex-1 mx-56">
        <Link to="/" className="btn btn-ghost text-5xl">Bid<span className="text-amber-300">Hub</span></Link>
      </div>
      <div className="flex-none mx-56">
        <ul className="menu menu-horizontal px-1 text-2xl">
          {
            id.length >= 1
              ? <>
                <li>
                  <Link to="/regist">경매 등록</Link>
                </li>
                <li>
                  <Link to="/mypage">{nickname}님</Link>
                </li>
                <li>
                  <Link to="/" onClick={logout}>로그아웃</Link>
                </li>

              </>
              : <>
                <li>
                  <Link to="./login">로그인</Link>
                </li>
                <li>
                  <Link to="./signup">회원가입</Link>
                </li>
              </>
          }
        </ul>
      </div>
    </div>
  );
};
export default React.memo(Navi);

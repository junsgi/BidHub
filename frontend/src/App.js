import "./App.css"
import React, { useCallback, useMemo, useState, useEffect } from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Auction from "./component/Auction";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import Login from "./component/Login";
import Signup from "./component/Signup";
import MyPage from "./component/MyPage";
import Detail from "./component/Detail";
import Regist from "./component/Regist";
import Recharge from "./component/Recharge";
import USER from "./context/userInfo";
import MemberUpdate from "./component/MemberUpdate";
import Toss from "./component/Toss";
import TossProc from "./component/TossProc";
import PaymentLog from "./component/PaymentLog";
import Winning from "./component/Winning";
function App() {
  const [user, setUser] = useState({
    id: sessionStorage.getItem("id") ?? "",
    nickname: sessionStorage.getItem("nickname") ?? "",
    point: +(sessionStorage.getItem("point")) ?? 0,
  });
  const contextUser = useMemo(() => { return { user, setUser } }, [user, setUser])
  const logout = useCallback(() => { sessionStorage.clear(); setUser({ id: "", nickname: "", point: 0 }) }, []);

  return (
    <div className="App">
      <BrowserRouter>
        <USER.Provider value={contextUser}>
          <Navi logout={logout} NICKNAME={user.nickname} ID={user.id} />
          <Routes>
            <Route path="/" element={<Auction />} ></Route>
            <Route path="/login" element={<Login />}></Route>

            <Route path="/mypage">
              <Route index path="" element={<MyPage />} />
              <Route path="update" element={<MemberUpdate />}></Route>

              <Route path="recharge" >
                <Route index path="" element={<Recharge />}></Route>
                <Route index path="log" element={<PaymentLog />}></Route>
                <Route path="toss" element={<Toss />}></Route>
              </Route>
              <Route path = "successfulBid" element = {<Winning />}/>
              <Route path=":id" element={<Detail />}></Route>
            </Route>

            <Route path="/signup" element={<Signup />}></Route>
            <Route path="/approve" element={<PaymentProc />}></Route>
            <Route path="/success" element={<TossProc />}></Route>
            <Route path="/detail/:id" element={<Detail />}></Route>
            <Route path="/regist" element={<Regist />}></Route>
            <Route path="*" element={<Auction />}></Route>
          </Routes>
        </USER.Provider>
      </BrowserRouter>
    </div >
  );
}

export default App;

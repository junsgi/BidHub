import "./App.css"
import React, { useCallback, useMemo, useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
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
function App() {
  const [user, setUser] = useState({
     id: sessionStorage.getItem("id") ?? "", 
     nickname: sessionStorage.getItem("nickname") ?? "",
     point : +(sessionStorage.getItem("point")) ?? 0,
    });
  const contextUser = useMemo(()=>{return {user, setUser}}, [user, setUser])
  const logout = useCallback(() => { sessionStorage.clear(); setUser({ id: "", nickname: "", point : 0 }) }, []);

  useEffect(() => {
    console.log("App updated")

  })


  return (
    <div className="App">
      <USER.Provider value={contextUser}>
        <Navi logout={logout} NICKNAME={user.nickname} ID={user.id} />
        <Routes>
          <Route path="/" element={<Auction />} ></Route>
          <Route path="/login" element={<Login/>}></Route>
          <Route path="/mypage">
            <Route index path="" element={<MyPage />} />
            <Route path="recharge" element={<Recharge />}></Route>
            <Route path="update" element={<MemberUpdate />}></Route>
          </Route>
          <Route path="/signup" element={<Signup />}></Route>
          <Route path="/approve" element={<PaymentProc />}></Route>
          <Route path="*" element={<Auction />}></Route>
          <Route path="/detail/:id" element={<Detail />}></Route>
          <Route path="/regist" element={<Regist />}></Route>
        </Routes>
      </USER.Provider>
    </div>
  );
}

export default App;

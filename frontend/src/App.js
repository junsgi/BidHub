import React, { useCallback, useMemo, useState, useRef, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import Auction from "./component/Auction";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import { getPoint, getAuctionItems } from "./Api";
import Login from "./component/Login";
import Signup from "./component/Signup";
import MyPage from "./component/MyPage";
import Detail from "./component/Detail";
export const P = React.createContext();
function App() {
  const [point, SetPoint] = useState(() => {
    const savedPoint = sessionStorage.getItem("point");
    return savedPoint !== null ? Number(savedPoint) : 0;
  });
  const setpoint = useCallback(() => {
    getPoint(SetPoint);
  }, []);
  const pointDispatch = useMemo(() => {
    return { point, setpoint };
  }, []);



  const [user, SetUser] = useState({id : sessionStorage.getItem("id") ?? "", nickname : sessionStorage.getItem("nickname") ?? ""});
  const logout = useCallback(()=>{sessionStorage.clear(); SetUser({id : "", nickname : ""})}, []);

  console.log("App updated")
  useEffect(()=>{
    console.log("App mounted");
  }, [])

  return (
    <div className="App">
      <P.Provider value={pointDispatch}>
        <Navi logout = {logout} NICKNAME = {user.nickname} ID = {user.id} />
        <Routes>
          <Route index path="/" element = {<Auction />} ></Route>
          <Route path="/login" element = {<Login SetUser = {SetUser} />}></Route>
          <Route path="/mypage" element = {<MyPage />}></Route>
          <Route path="/signup" element = {<Signup />}></Route>
          <Route path="/approve" element = {<PaymentProc />}></Route>
          <Route path="/detail/:id" element = {<Detail />}></Route>
        </Routes>
      </P.Provider>
    </div>
  );
}

export default App;

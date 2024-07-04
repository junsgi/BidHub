import React, { useCallback, useMemo, useState, useRef, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import Auction from "./component/Auction";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import { getPoint, getAuctionItems } from "./Api";
import Login from "./component/Login";
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
  }, [point]);

  useEffect(()=>{
    console.log("App mounted");
  }, [])

  useEffect(()=>console.log("App updated"))
  return (
    <div className="App">
      <P.Provider value={pointDispatch}>
        <Navi />
        <Routes>
          <Route index path="/" element = {<Auction />} ></Route>
          <Route path="/login" element = {<Login />}></Route>
          <Route path="/approve" element = {<PaymentProc />}></Route>
        </Routes>
      </P.Provider>
    </div>
  );
}

export default App;

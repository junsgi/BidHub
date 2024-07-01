import React, { useCallback, useMemo, useState, useRef, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import Auction from "./component/Auction";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import { getPoint, getAuctionItems } from "./Api";
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

  return (
    <div className="App">
      <P.Provider value={pointDispatch}>
        <Navi />
        <Auction />
        <Routes>
          <Route path="/approve" element = {<PaymentProc />}></Route>
        </Routes>
      </P.Provider>
    </div>
  );
}

export default App;

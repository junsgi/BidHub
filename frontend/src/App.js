import "./App.css";
import React, { useCallback, useMemo, useState } from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Main from "./component/Main";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import { getPoint } from "./Api";
export const P = React.createContext();
function App() {
  const [point, SetPoint] = useState(() => {
    const savedPoint = sessionStorage.getItem("point");
    return savedPoint !== null ? Number(savedPoint) : 0;
  });;

  const setpoint = useCallback(() => {
    getPoint(SetPoint);
  }, [])
  const pointDispatch = useMemo(()=>{
    return {point, setpoint};
  }, [point])

  return (
    <div className="App">
      <P.Provider value = {pointDispatch}>
        <Navi />
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Main />}></Route>
            <Route path="/approve" element={<PaymentProc />}></Route>
          </Routes>
        </BrowserRouter>
      </P.Provider>
    </div>
  );
}


export default App;
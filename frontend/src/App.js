import "./App.css";
import React, { useState } from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Main from "./component/Main";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
import { getPoint } from "./Api";
export const P = React.createContext();
function App() {
  const [point, SetPoint] = useState(Number(sessionStorage.getItem("point")) ?? 0);

  const setpoint = () => {
    console.log("가져왕")
    getPoint(SetPoint);
  }
  return (
    <div className="App">
      <P.Provider value = {{point, setpoint}}>
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
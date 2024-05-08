import "./App.css";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import Main from "./component/Main";
import Navi from "./component/Navi";
import PaymentProc from "./component/PaymentProc";
function App() {
  return (
    <div className="App">
      <Navi />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Main />}></Route>
          <Route path="/approve" element={<PaymentProc />}></Route>

        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

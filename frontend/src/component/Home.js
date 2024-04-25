import { useState } from 'react';
import { login, signup } from '../Api';
import Login from './Login';
import Signup from './Signup';
const Home = () => {
    const [ID, SetID] = useState("");
    const [PW, SetPW] = useState("");
    const [CK, SetCK] = useState("");
    const [NICK, SetNICK] = useState("");
    const [Status, SetStatus] = useState("guest");
    const [control, SetControl] = useState([false, false, false, false]);

    const idHandler = e => SetID(e.target.value);
    const pwHandler = e => SetPW(e.target.value);
    const ckHandler = e => SetCK(e.target.value);
    const nickHandler = e => SetNICK(e.target.value);
    const statusHandler = (command) => SetStatus(command);
    const submit = () => login({ id: ID, pw: PW }, SetStatus);

    const successSignup = (status) => {
        
    }
    const signUp = () => {

    }


    if (Status === "guest") {
        return (
            <div className='Home'>
                <Login
                    idHandler={idHandler}
                    pwHandler={pwHandler}
                    submit={submit}
                    statusHandler = {statusHandler}
                />
            </div>
        );
    }
    else if (Status === "member") {
        return (
            <div className='Home'>
                <button onClick={()=>{
                    sessionStorage.clear();
                    SetStatus("guest");
                }}>logout</button>
            </div>
        );
    }else if (Status === "signup") {
        return (
            <div className='Home'>
                <Signup 
                />
                <button onClick={()=>{
                    sessionStorage.clear();
                    SetStatus("guest");
                }}>돌아가기</button>
            </div>
        );
    }
}

export default Home;
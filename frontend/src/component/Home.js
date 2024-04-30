import { useEffect, useState } from 'react';
import { login, signup } from '../Api';
import Login from './Login';
import Signup from './Signup';
import MyPage from './MyPage';
const Home = () => {
    const [Status, SetStatus] = useState("guest");
    const id = sessionStorage.getItem("id") ?? false;
    useEffect(()=>{
        if (id) SetStatus("member");
    }, [Status])
    if (!id && Status === "guest") {
        return (
            <div className='Home'>
                <Login
                    SetStatus = {SetStatus}
                />
            </div>
        );
    }
    else if (id || Status === "member") {
        return (
            <div className='Home'>
                <MyPage 
                    SetStatus = {SetStatus}
                />
            </div>
        );
    }else if (Status === "signup") {
        return (
            <div className='Home'>
                <Signup 
                    SetStatus = {SetStatus}
                />
            </div>
        );
    }
}

export default Home;
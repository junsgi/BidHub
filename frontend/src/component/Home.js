import { useEffect, useState } from 'react';
import Login from './Login';
import Signup from './Signup';
import MyPage from './MyPage';
import Regist from './Regist';
const Home = () => {
    const [Status, SetStatus] = useState("guest");
    useEffect(() => {
        if (sessionStorage.getItem("id") && !(Status === "member" && Status === "regist")) {
            SetStatus("member");
        }
    }, []);
    
    if (Status === "member") {
        return (
            <div className='Home'>
                <MyPage
                    SetStatus={SetStatus}
                />
            </div>
        );
    } else if (Status === "regist") {
        return (
            <div className='Home'>
                <Regist
                    SetStatus={SetStatus}
                />
            </div>
        );
    }
    else if (Status === "guest") {
        return (
            <div className='Home'>
                <Login
                    SetStatus={SetStatus}
                />
            </div>
        );
    }
    else if (Status === "signup") {
        return (
            <div className='Home'>
                <Signup
                    SetStatus={SetStatus}
                />
            </div>
        );
    } else {
        return (
            <div>
                ㅈ됨
            </div>
        );
    }
}

export default Home;
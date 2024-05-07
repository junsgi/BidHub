import axios from "axios";
const S = "http://localhost:3977/";

export const login = async (data, SetStatus) => {
    let URL = S + "member/login";
    await axios.post(URL, data)
    .then(res => {
        alert(res.data.message);
        if (res.data.status) {
            sessionStorage.setItem("id", data.id);
            sessionStorage.setItem("nickname", res.data.nickname);
            sessionStorage.setItem("point", res.data.point);
            SetStatus("member");
        }
    })
    .catch(e => console.error(e))
}

export const signup = async (data, mes, con, status) => {
    let URL = S + "member/signup";
    await axios.post(URL, data)
    .then(res => {
        if (res.data.status) {
            alert(res.data.message);
            status("guest");
        }else {
            mes([res.data.message, "", "", ""]);
            con([true, false, false, false]);
        }
    })
    .catch(e => console.error(e))
}

export const recharge = async (data, SetPoint) => {
    const URL = S + "member/point";

    await axios.post(URL, data)
    .then(res => {
        if (res.data.status){
            SetPoint(e => parseInt(e) + parseInt(data.total_amount))
            console.log(res.data.message);
            // window.open(res.data.message,'kakao', "popup=yes");
        }else {
            alert(res.data.message);
        }
    })
    .catch(e => {
        console.error(e);
    })
}
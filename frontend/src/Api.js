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
            SetStatus("member");
        }
    })
    .catch(e => console.error(e))
}

export const signup = async (data, success) => {
    let URL = S + "member/signup";
    await axios.post(URL, data)
    .then(res => {
        alert(res.data.message);
        success(res.data.status);
    })
    .catch(e => console.error(e))
}
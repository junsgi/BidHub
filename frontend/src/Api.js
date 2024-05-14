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

export const recharge = async (data) => {
    const URL = S + "member/point";

    await axios.post(URL, data)
    .then(res => {
        if (res.data.status){
            let result = res.data.message.split("_");
            sessionStorage.setItem("tid", result[1]);
            sessionStorage.setItem("orderId", `${result[2]}_${result[3]}_${result[4]}`);
            // alert(`${result[2]}_${result[3]}_${result[4]}`)
            window.open(result[0], '_blank', "popup=yes");
        }else {
            alert(res.data.message);
        }
    })
    .catch(e => {
        console.error(e);
    })
}



export const pointApproved = async (data) => {
    let URL = S + "member/point/approved";
    await axios.post(URL, data)
    .then(res => {
        alert(res.data.message);
        sessionStorage.removeItem("tid");
        sessionStorage.removeItem("orderId");
        window.close();
    })
}

export const getPoint = async (SetPoint) => {
    let URL = S + `member/getpoint?id=${sessionStorage.getItem("id")}`;
    await axios.get(URL)
    .then(res => {
        if (res.data.status) {
            SetPoint(res.data.point);
        }else {
            alert(res.data.message);
        }
    })
    .catch(e => {
        console.error(e);
    })
}

export const submit = async (data) => {
    let URL = S + "auction/submit";
    await axios.post(URL, data)
    .then(res => {
        window.location.reload();
        alert(res.data.message)
    })
    .catch(e => {
        console.error(e);
    })
}

export const getAuctionItems = async (SetList) => {
    let URL = S + "auction/";
    await axios.get(URL)
    .then(res => {
        SetList(res.data)
    })
    .catch(e => {
        console.error(e)
    })
}
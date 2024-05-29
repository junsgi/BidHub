import axios from "axios";
import MyItem from "./component/SucItem";
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
    let URL = S + "auctionitem/submit";
    await axios.post(URL, data)
    .then(res => {
        window.location.reload();
        alert(res.data.message)
    })
    .catch(e => {
        console.error(e);
    })
}

export const getAuctionItems = async (SetList, st) => {
    let URL = S + `auctionitem/?st=${st}`;

    await axios.get(URL)
    .then(res => {
        SetList(res.data)
    })
    .catch(e => {
        console.error(e)
    })
}


export const getAuctionItemDetail = async (id, setInfo) => {
    let URL = S + `auctionitem/${id}`;
    await axios.get(URL)
    .then(res => {
        setInfo(res.data);
        console.log(res.data)
    })
    .catch(e => console.error(e))
}

export const bidding_api = async (data, callBack, imm) => {
    let URL = S + "auction/bidding";
    if (imm) URL += "/immediately";
    await axios
    .post(URL, data)
    .then(res => {
        alert(res.data.message)
    })
    .catch(e => console.error(e))
    .finally(callBack)
}

export const auctionClose = async (data) => {
    let URL = S + "auction/close";
    await axios.post(URL, data)
    .then(res => {
        let flag = window.confirm(res.data.message);
        auctionDecide({...data, flag : flag})
    })
    .catch(e => console.error(e));
}

const auctionDecide = async (data) => {
    let URL = S + "auction/decide";
    await axios.post(URL, data)
    .then(res => {
        if (res.data.status){
            alert(res.data.message);
            window.location.reload();
        } 
    })
    .catch(e => console.error(e))
}

export const dot = num => {
    num = String(num)
    let LEN = num.length
    if (LEN <= 3) return num;
    let res = "";
    let i = 0;

    for(i = 0 ; LEN % 3 > 0 && i < LEN % 3 ; i++) res += num[i];
    if (LEN % 3 !== 0) {
        res += ","; 
    }
    for(let j = 0;i < LEN ; i++, j++) {
        if (j > 0 && j % 3 === 0) res += ",";
        res += num[i];
    }
    return res;
}

export const getCount = async (ref, callback) => {
    let URL = S + "auctionitem/count";
    await axios.get(URL)
    .then(res => {
        ref.current = Math.ceil(res.data / 5)
        callback()
    })
}

export function convertSeconds(seconds) {
    if (seconds <= 0) return "종료된 경매"
    const MINUTE = 60;
    const HOUR = 3600;
    const DAY = 86400;

    let days = 0;
    let hours = 0;
    let minutes = 0;
    let remainingSeconds = seconds;

    // days
    days = Math.floor(remainingSeconds / DAY);
    remainingSeconds %= DAY;

    // hours
    hours = Math.floor(remainingSeconds / HOUR);
    remainingSeconds %= HOUR;

    // minutes
    minutes = Math.floor(remainingSeconds / MINUTE);
    remainingSeconds %= MINUTE;

    return `${days}일 ${hours}시간 ${minutes}분 ${remainingSeconds}초 남음`;
}

export const getSucItems = async (SetList) => {
    let URL = S + `suc/${sessionStorage.getItem("id")}`;
    await axios.get(URL)
    .then(res => SetList(res.data.map(e => <MyItem key={e.aitem_id} data = {e} />)))
    .catch(e => console.error(e))
}
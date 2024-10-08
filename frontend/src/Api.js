import axios from "axios";
export const login = async (data, SetUser, navi) => {
  let URL = "/member/login";
  await axios
    .post(URL, data)
    .then((res) => {
      console.log(res.data)
      alert(res.data.message);
      if (res.data.status) {
        SetUser(prev => { return { id: data.id, nickname: res.data.nickname, point: Number(res.data.point) } });
        sessionStorage.setItem("id", data.id);
        sessionStorage.setItem("nickname", res.data.nickname);
        sessionStorage.setItem("point", res.data.point);
        navi("/")
      }
    })
    .catch((e) => console.error(e));
};

export const signup = async (data, mes, con, status) => {
  let URL = "/member/signup";
  await axios
    .post(URL, data)
    .then((res) => {
      if (res.data.status) {
        alert(res.data.message);
        status("guest");
      } else {
        mes([res.data.message, "", "", ""]);
        con([true, false, false, false]);
      }
    })
    .catch((e) => console.error(e));
};

export const recharge = async (data) => {
  const URL = "/member/point";

  await axios
    .post(URL, data)
    .then((res) => {
      if (res.data.status) {
        let result = res.data.message.split("_");
        sessionStorage.setItem("tid", result[1]);
        sessionStorage.setItem(
          "orderId",
          `${result[2]}_${result[3]}_${result[4]}`
        );
        // alert(`${result[2]}_${result[3]}_${result[4]}`)
        window.open(result[0], "_blank", "popup=yes");
      } else {
        alert(res.data.message);
      }
    })
    .catch((e) => {
      console.error(e);
    });
};

export const pointApproved = async (data) => {
  let URL = "/member/point/approved";
  await axios.post(URL, data).then((res) => {
    alert(res.data.message);
    window.close();
  });
};

export const getPoint = async (SetPoint) => {
  let URL = `/member/getpoint?id=${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then((res) => {
      if (res.data.status) {
        SetPoint(res.data.point);
      } else {
        alert(res.data.message);
      }
    })
    .catch((e) => {
      console.error(e);
    });
};

export const submit = async (data) => {
  let URL = "/auctionitem/submit";
  return await axios
    .post(URL, data)
    .then((res) => {
      alert(res.data.message);
      return { status: res.data.status }
    })
    .catch((e) => {
      console.error(e);
      return { status: false };
    });
};

export const getAuctionItems = async (callBack, st, sort) => {
  let URL = `/auctionitem?st=${st}&sort=${sort}`;
  if (sort === 1) URL += `&id=${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then(res => callBack(res))
    .catch((e) => {
      console.error(e);
    });
};

export const getAuctionItemDetail = async (id, setInfo) => {
  let URL = `/auctionitem/${id}`;
  await axios
    .get(URL)
    .then((res) => {
      setInfo(res.data);
    })
    .catch((e) => console.error(e));
};

export const bidding_api = async (
  data,
  callBack,
  imm,
) => {
  let URL = "/auction/bidding";
  if (imm) URL += "/immediately";
  await axios
    .post(URL, data)
    .then((res) => {
      alert(res.data.message);
    })
    .catch((e) => console.error(e))
    .finally(() => {
      callBack();
    });
};

export const auctionClose = async (data, callback) => {
  let URL = "/auction/close";
  await axios
    .post(URL, data)
    .then((res) => {
      let flag = window.confirm(res.data.message);
      auctionDecide({ ...data, flag: flag }, callback);
    })
    .catch((e) => console.error(e));
};

const auctionDecide = async (data, callback) => {
  let URL = "/auction/decide";
  await axios
    .post(URL, data)
    .then((res) => {
      if (res.data.status) {
        alert(res.data.message);
        callback();
      }
    })
    .catch((e) => console.error(e));
};

export const bidPayment = async (data, setpoint) => {
  let URL = "/suc/payment";
  console.log(data);
  await axios.post(URL, data).then((res) => {
    alert(res.data.message);
    if (res.data.status) {
      setpoint();
    }
  });
};

export const getSucItems = async (callBack) => {
  let URL = `/suc/${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then(res => callBack(res))
    .catch((e) => console.error(e));
};

export const updateNickOrPasswd = async (data, path, callBack) => {
  let URL = `/member/update/${path}`;
  await axios
    .post(URL, data, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      alert(res.data.message);
      if (res.data.status)
        callBack(path)

      if (res.data.status && path === "nickname")
        sessionStorage.setItem("nickname", data.after);

    })
    .catch((e) => console.error(e));
};

export const without = async (logout) => {
  let flag = window.confirm("탈퇴 하시겠습니까?");
  if (flag) {
    let URL = `/member/without/${sessionStorage.getItem("id")}`;
    await axios
      .delete(URL)
      .then((res) => {
        alert(res.data.message);
        logout();
      })
      .catch((e) => console.error(e));
  }
};

export const getPaymentLog = async (SetList) => {
  let URL = `/paymentLog/${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then((res) => {
      const list = res.data.map((e) => {
        const approvedAt =
          e.approvedAt === "취소됨"
            ? e.approvedAt
            : e.approvedAt.replace("T", " ");
        const order = e.order
          .replace("T", " ")
          .substring(0, e.order.indexOf("."));
        const amount = dot(e.amount);
        const tid = e.tid;
        return (
          <tr key={tid} className="hover">
            <td className="text-2xl">{order}</td>
            <td className="text-2xl">{approvedAt}</td>
            <td className="text-2xl">{tid}</td>
            <td className="text-2xl">{amount}원</td>
          </tr>
        );
      });

      SetList(list);
    })
    .catch((e) => console.error(e));
};

export const dot = (num) => {
  if (!num) return 0;
  num = String(num);
  let LEN = num.length;
  if (LEN <= 3) return num;
  let res = "";
  let i = 0;

  for (i = 0; LEN % 3 > 0 && i < LEN % 3; i++) res += num[i];
  if (LEN % 3 !== 0) res += ",";

  for (let j = 0; i < LEN; i++, j++) {
    if (j > 0 && j % 3 === 0) res += ",";
    res += num[i];
  }
  return res;
};

export function convertSeconds(seconds) {
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

  return { days, hours, minutes, remainingSeconds };
}

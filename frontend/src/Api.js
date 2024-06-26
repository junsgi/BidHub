import axios from "axios";
import SucItem from "./component/SucItem";
const S = "http://localhost:3977/";

export const login = async (data, SetStatus) => {
  let URL = S + "member/login";
  await axios
    .post(URL, data)
    .then((res) => {
      alert(res.data.message);
      if (res.data.status) {
        sessionStorage.setItem("id", data.id);
        sessionStorage.setItem("nickname", res.data.nickname);
        SetStatus("member");
      }
    })
    .catch((e) => console.error(e));
};

export const signup = async (data, mes, con, status) => {
  let URL = S + "member/signup";
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
  const URL = S + "member/point";

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

export const pointApproved = async (data, setpoint) => {
  let URL = S + "member/point/approved";
  await axios.post(URL, data).then((res) => {
    alert(res.data.message);
    sessionStorage.removeItem("tid");
    sessionStorage.removeItem("orderId");
    window.close();
    setpoint();
  });
};

export const getPoint = async (SetPoint) => {
  let URL = S + `member/getpoint?id=${sessionStorage.getItem("id")}`;
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

export const submit = async (data, refresh, back) => {
  let URL = S + "auctionitem/submit";
  await axios
    .post(URL, data)
    .then((res) => {
      alert(res.data.message);
      if (res.data.status) {
        back();
        refresh();
      }
    })
    .catch((e) => {
      console.error(e);
    });
};

export const getAuctionItems = async (SetList, st, sort, pageLength) => {
  let URL = S + `auctionitem/?st=${st.current}&sort=${sort.current}`;
  if (sort === 2) URL += `&id=${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then((res) => {
      const list = res.data.list;
      pageLength.current = Math.ceil(res.data.len / 5);

      SetList((e) => {
        let result = [];
        for (let i = 0; i < list.length; i += 3) {
          let temp = [];

          for (let j = i; j < i + 3; j++) {
            if (j >= list.length) break;
            temp.push(
              <div
                key={list[j].aitem_id}
                className={
                  "card  bg-base-100 w-80 shadow-xl mr-4"
                }
              >
                <figure>
                  <img
                    src={`http://localhost:3977/auctionitem/img/${list[j].aitem_id}`}
                    style={{
                        width : "333px",
                        height : "200px"
                    }}
                  />
                  </figure>
                <div className="card-body">
                  <h2 className="card-title">{list[j].title}</h2>
                  <p>Ie?</p>
                </div>
              </div>
            );
          }

          result.push(
            <div key={i} className="flex flex-wrap justify-center mt-4">
              {temp}
            </div>
          );
        }
        return { ...e, list: result };
      });
    })
    .catch((e) => {
      console.error(e);
    });
};

export const getAuctionItemDetail = async (id, setInfo) => {
  let URL = S + `auctionitem/${id}`;
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
  setRemaining,
  setpoint
) => {
  let URL = S + "auction/bidding";
  if (imm) URL += "/immediately";
  await axios
    .post(URL, data)
    .then((res) => {
      alert(res.data.message);
      if (res.data.status) setRemaining((e) => -1);
    })
    .catch((e) => console.error(e))
    .finally(() => {
      callBack();
      setpoint();
    });
};

export const auctionClose = async (data, setRemaining) => {
  let URL = S + "auction/close";
  await axios
    .post(URL, data)
    .then((res) => {
      let flag = window.confirm(res.data.message);
      auctionDecide({ ...data, flag: flag }, setRemaining);
    })
    .catch((e) => console.error(e));
};

const auctionDecide = async (data, setRemaining) => {
  let URL = S + "auction/decide";
  console.log(data);
  await axios
    .post(URL, data)
    .then((res) => {
      if (res.data.status) {
        alert(res.data.message);
        setRemaining((e) => -1);
      } else {
        alert("다시 시도해주세요");
      }
    })
    .catch((e) => console.error(e));
};

export const bidPayment = async (data, setpoint) => {
  let URL = S + "suc/payment";
  console.log(data);
  await axios.post(URL, data).then((res) => {
    alert(res.data.message);
    if (res.data.status) {
      setpoint();
    }
  });
};

export const getSucItems = async (SetList) => {
  let URL = S + `suc/${sessionStorage.getItem("id")}`;
  await axios
    .get(URL)
    .then((res) =>
      SetList(res.data.map((e) => <SucItem key={e.aitem_id} data={e} />))
    )
    .catch((e) => console.error(e));
};

export const updateNickOrPasswd = async (data, path) => {
  let URL = S + `member/update/${path}`;
  await axios
    .post(URL, data, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((res) => {
      alert(res.data.message);
      if (res.data.status && path === "nickname") {
        sessionStorage.setItem("nickname", data.after);
      }
    })
    .catch((e) => console.error(e));
};

export const without = async (logout) => {
  let flag = window.confirm("탈퇴 하시겠습니까?");
  if (flag) {
    let URL = S + `member/without/${sessionStorage.getItem("id")}`;
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
  let URL = S + `paymentLog/${sessionStorage.getItem("id")}`;
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
        const amount = e.amount;
        const tid = e.tid;
        return (
          <tr key={tid}>
            <td>{approvedAt}</td>
            <td>{order}</td>
            <td>{tid}</td>
            <td>{amount}</td>
          </tr>
        );
      });

      SetList(list);
    })
    .catch((e) => console.error(e));
};

export const dot = (num) => {
  if (!num) return null;
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
  if (seconds <= 0) return "종료된 경매";
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

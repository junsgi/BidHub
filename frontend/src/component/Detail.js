import axios from "axios";
import { useCallback, useContext, useEffect, useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { bidding_api, convertSeconds, dot } from "../Api";
import USER from "../context/userInfo";
const Detail = () => {
    const { user } = useContext(USER);
    const navi = useNavigate();
    const [data, setData] = useState({
        aitemBid: "",
        aitemContent: "",
        aitemCurrent: 0,
        aitemDate: 0,
        aitemId: "",
        aitemImmediate: "",
        aitemStart: "",
        aitemTitle: "",
        memId: "",
        status: ""
    });
    const [buttons, setBtns] = useState([]);
    const start = useMemo(() => dot(data.aitemStart), [data]);
    const current = useMemo(() => dot(data.aitemCurrent), [data]);
    const bid = useMemo(() => dot(data.aitemBid), [data]);
    const immediate = useMemo(() => dot(data.aitemImmediate), [data]);
    const ID = useMemo(() => window.location.pathname.split("/")[2], [])

    const getDetail = useCallback(async () => {
        const response = await axios.get(`/auctionitem/${ID}`)
            .then(res => res.data)
            .catch(e => { console.error(e); return { status: 400 } })
        if (response.status === 400) {
            alert("다시 시도해주세요");
            navi(-1);
        } else {
            setData(prev => response)
        }
    }, [ID])

    const bidding = useCallback(() => {
        let flag = window.confirm("입찰하시겠습니까?\n환불 x, 확인 후 취소 x");
        if (flag) {
            const request = {
                userId: user.id,
                itemId: data.aitemId,
                current: data.aitemCurrent
            }
            bidding_api(request, getDetail, false, null)
        }
    }, [data, user, getDetail])

    const getDetailAndSetButtons = useCallback(async () => {
        const response = await axios.get(`/auctionitem/${ID}`)
            .then(res => res.data)
            .catch(e => { console.error(e); return { status: 400 } })

        if (response.status === 400) {
            alert("다시 시도해주세요");
            navi(-1);
        } else {
            setData(() => {
                let list = []
                const nope = <div key="nope" className=" text-4xl w-full m-auto mb-4 text-center" aria-readonly><Link to={"/login"} className="btn w-full btn-block text-3xl" >로그인 후 이용할 수 있습니다.</Link></div>
                const bid = <div key="bid" className=" text-4xl w-full m-auto mb-4 text-center"><button className="btn w-full text-3xl btn-info" onClick={bidding}>입찰하기</button></div>
                const imm = <div key="imm" className=" text-4xl w-full m-auto mb-4 text-center"><button className="btn w-full btn-block text-3xl">즉시 구매</button></div>
                const cancel = <div key="cancel" className=" text-4xl w-full m-auto mb-4 text-center"><button className="btn w-full text-3xl btn-warning">경매 중지</button></div>
                const remove = <div key="remove" className=" text-4xl w-full m-auto mb-4 text-center"><button className="btn w-full text-3xl btn-error">경매 삭제</button></div>
                if (!user.id) {
                    list.push(nope)
                }
                else if (user.id !== response.memId) {
                    list.push(bid);
                    if (dot(response.aitemImmediate) !== 0) {
                        list.push(imm)
                    }
                } else if (user?.id && user.id === response.memId) {
                    list.push(cancel);
                    list.push(remove);
                }
                setBtns(() => list);
                return response;
            })
        }
    }, [user, ID])


    useEffect(() => {
        getDetailAndSetButtons();
    }, [ID, navi])
    return (
        <div className="flex flex-col w-96 border-opacity-50 m-auto place-items-center">
            <div className="w-full  w-full text-center mb-4">
                <h1 className="text-7xl">{data.aitemTitle}</h1>
                <h1 className="text-2xl">시작가 : {start}원</h1>
            </div>
            <div className=" m-auto mb-4">
                <img className="w-full shadow-2xl" src={`http://localhost:3977/auctionitem/img/${ID}`} alt="img"></img>
            </div>

            <div className="divider w-full m-auto mb-4 text-2xl">판매자</div>
            <div className="w-full m-auto mb-4 text-center">
                {data.memId}
            </div>

            <div className="divider w-full m-auto mb-4 text-2xl">남은시간</div>
            <div className="  w-full m-auto mb-4">
                {data.aitemDate > 0 ? <Timer re={data.aitemDate}></Timer> : "종료된 거래"}
            </div>
            <div className="divider w-full m-auto mb-4 text-2xl">현재가격</div>
            <div className="  text-4xl w-full m-auto mb-4 text-center font-bold">
                {current}원
            </div>
            <div className="divider w-full m-auto mb-4 text-2xl">입찰 단위</div>
            <div className=" text-4xl w-full m-auto mb-4 text-center">
                {bid}원
            </div>
            {
                immediate !== 0 &&
                <>
                    <div className="divider w-full m-auto mb-4 text-2xl">즉시 구매가</div>
                    <div className="  text-4xl w-full m-auto mb-4 text-center">
                        {immediate}원
                    </div>
                </>
            }
            {buttons}
        </div >
    )
}


export default Detail;



const Timer = ({ re }) => {
    const [remaining, setRemaining] = useState(re);
    const { days, hours, minutes, remainingSeconds } = useMemo(() => convertSeconds(remaining), [remaining]);

    useEffect(() => {
        let interval = null;

        if (remaining >= 0) {
            interval = setInterval(() => {
                setRemaining(prev => prev - 1)
            }, 1000)
        } else if (interval) {
            clearInterval(interval)
            interval = null;
        }
        return () => {
            if (interval)
                clearInterval(interval);
        }
    }, [remaining])
    return (
        <div className="flex gap-5">
            <div>
                <span className="countdown text-4xl">
                    <span style={{ "--value": days }}></span>
                </span>
                일
            </div>
            <div>
                <span className="countdown text-4xl">
                    <span style={{ "--value": hours }}></span>
                </span>
                시
            </div>
            <div>
                <span className="countdown text-4xl">
                    <span style={{ "--value": minutes }}></span>
                </span>
                분
            </div>
            <div>
                <span className="countdown text-4xl">
                    <span style={{ "--value": remainingSeconds }}></span>
                </span>
                초
            </div>
        </div>
    );
}
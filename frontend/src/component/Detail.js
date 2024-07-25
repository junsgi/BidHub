import axios from "axios";
import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { convertSeconds, dot } from "../Api";
const Detail = () => {
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
    const start = useMemo(() => dot(data.aitemStart), [data]);
    const current = useMemo(() => dot(data.aitemCurrent), [data]);
    const bid = useMemo(() => dot(data.aitemBid), [data]);
    const immediate = useMemo(() => dot(data.aitemImmediate), [data]);
    const ID = useMemo(() => window.location.pathname.split("/")[2], [])

    useEffect(() => {
        const getDetail = async () => {
            const response = await axios.get(`/auctionitem/${ID}`)
                .then(res => res.data)
                .catch(e => { console.error(e); return { status: 400 } })

            if (response.status === 400) {
                alert("다시 시도해주세요");
                navi(-1);
            } else {
                setData(prev => response)
            }
        }
        getDetail();
    }, [ID, navi])


    return (
        <div className="flex flex-col border-opacity-50 m-auto">
            <div className="w-full place-items-center w-full text-center mb-4">
                <h1 className="text-7xl">{data.aitemTitle}</h1>
                <h1 className="text-2xl">시작가 : {start}원</h1>
            </div>
            <div className="place-items-center w-96 m-auto mb-4">
                <img className="w-96 shadow-2xl" src={`http://localhost:3977/auctionitem/img/${ID}`} alt="img"></img>
            </div>
            <div className="divider w-96 m-auto mb-4 text-2xl">남은시간</div>
            <div className=" place-items-center w-96 m-auto mb-4">
                {data.aitemDate > 0 ? <Timer re={data.aitemDate}></Timer> : "종료된 거래"}
            </div>
            <div className="divider w-96 m-auto mb-4 text-2xl">현재가격</div>
            <div className=" place-items-center text-4xl w-96 m-auto mb-4 text-center">
                {current}원
            </div>
            <div className="divider w-96 m-auto mb-4 text-2xl">입찰 단위</div>
            <div className="place-items-center text-4xl w-96 m-auto mb-4 text-center">
                {bid}원
            </div>
            {
                immediate !== 0 &&
                <>
                    <div className="divider w-96 m-auto mb-4 text-2xl">즉시 구매가</div>
                    <div className=" place-items-center text-4xl w-96 m-auto mb-4 text-center">
                        {immediate}원
                    </div>
                </>
            }

            <div className="divider w-96 m-auto mb-4"></div>
            <div className="place-items-center text-4xl w-96 m-auto mb-4 text-center">
                <button className="btn w-96 btn-block text-3xl btn-info">입찰하기</button>
            </div>

            <div className="place-items-center text-4xl w-96 m-auto mb-4 text-center">
                <button className="btn w-96 btn-block text-3xl">즉시 구매</button>
            </div>

            <div className="place-items-center text-4xl w-96 m-auto mb-4 text-center">
                <button className="btn w-96 btn-block text-3xl btn-warning">경매 중지</button>
            </div>

            <div className="place-items-center text-4xl w-96 m-auto mb-4 text-center">
                <button className="btn w-96 btn-block text-3xl btn-error">경매 삭제</button>
            </div>
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
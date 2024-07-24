import React, { useEffect, useMemo, useState } from "react";
import { convertSeconds, dot } from "../Api";
import { useNavigate } from "react-router-dom";
function AuctionItem({data}) {
    const navi = useNavigate();
    const [remaining, setRemaining] = useState(+data.remaining);
    const { days, hours, minutes, remainingSeconds } = useMemo(() => convertSeconds(remaining), [remaining]);
    const current = useMemo(() => dot(data.current), [data]);
    const immediate = useMemo(() => dot(data.immediate), [data]);
    const goToDetail = () => navi(`/detail/${data.aitem_id}`)
    useEffect(()=>{
        let interval = null;

        if (remaining >= 0) {
            interval = setInterval(()=>{
                setRemaining(prev => prev - 1)
            }, 1000)
        }else if (interval){
            clearInterval(interval)
            interval = null;
        }

        return () => {
            if (interval)
                clearInterval(interval);
        }
    }, [])
    return (
        <div
            key={data.aitem_id}
            className={"card bg-base-100 w-60 shadow-xl mr-4 cursor-pointer"}
            onClick={goToDetail}
        >
            <figure>
                <img
                    src={`http://localhost:3977/auctionitem/img/${data.aitem_id}`}
                    style={{
                        width: "100%",
                        height: "200px",
                        botton: "0"
                    }}
                />
            </figure>
            <hr />
            <div className="card-body top-0">
                <h2 className="card-title font-semibold line-clamp-1 text-3xl">{data.title}</h2>
                <p className="text-xl">현재가 : {current}원</p>
                {immediate && <p className="text-xl"> 즉시 구매가 : {immediate}원 </p>}
                <p className="text-xl">{`${days}일 ${hours}시간 ${minutes}분 ${remainingSeconds}초 남음`}</p>
                
            </div>
        </div>
    );
}

export default React.memo(AuctionItem);
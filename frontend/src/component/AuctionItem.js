import React, { useMemo } from "react";
import { convertSeconds, dot } from "../Api";
function AuctionItem({data}) {
    console.log(data)
    const remaining = convertSeconds(+data.remaining)
    const current = useMemo(() => dot(data.current), [data]);
    const immediate = useMemo(() => dot(data.immediate), [data]);
    return (
        <div
            key={data.aitem_id}
            className={"card bg-base-100 w-60 shadow-xl mr-4"}
        >
            <figure>
                <img
                    src={`http://localhost:3977/auctionitem/img/${data.aitem_id}`}
                    style={{
                        width: "100%",
                        height: "200px",
                    }}
                />
            </figure>
            <hr />
            <div className="card-body">
                <h2 className="card-title">{data.title}</h2>
                <p>현재가 : {current}원</p>
                {immediate && <p> 즉시 구매가 : {immediate}원 </p>}
                <p className="text-sm">{remaining}</p>
            </div>
        </div>
    );
}

export default React.memo(AuctionItem);
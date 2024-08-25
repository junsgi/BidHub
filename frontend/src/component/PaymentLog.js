import React, { useEffect, useState } from "react";
import { getPaymentLog } from "../Api";

function PaymentLog() {
    const [list, SetList] = useState([]);
    useEffect(() => {
        getPaymentLog(SetList)
    }, [])
    return (
        <div className="overflow-x-auto table-lg w-2/4 m-auto ">
            <p className="text-3xl font-bold mb-4">충전 내역</p>
            <table className="table text-center">
                {/* head */}
                <thead>
                    <tr>
                        <th className="text-3xl">주문 시간</th>
                        <th className="text-3xl">승인 시간</th>
                        <th className="text-3xl">결제 아이디</th>
                        <th className="text-3xl">금액</th>
                    </tr>
                </thead>
                <tbody>
                    {list}
                </tbody>
            </table>
        </div>
    );
}
export default PaymentLog;
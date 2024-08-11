import { useEffect, useContext } from "react";
import { pointApproved } from "../Api";
import USER from "../context/userInfo";
const PaymentProc = () => {
    const { user } = useContext(USER);
    const token = window.location.search.split("=")[1];
    const tid = sessionStorage.getItem("tid");
    const partner_order_id = sessionStorage.getItem("orderId");

    useEffect(() => {
        // 창이 닫힐 때 실행될 함수
        window.onbeforeunload = function () {
            sessionStorage.removeItem("tid");
            sessionStorage.removeItem("orderId");
            window.opener.postMessage('close', '*');
        };

        if (token && tid && partner_order_id) {
            let data = {
                tid: tid,
                partner_order_id: partner_order_id,
                partner_user_id: user.id ?? sessionStorage.getItem("id"),
                pg_token: token
            };
            pointApproved(data);
        }
    }, [user.id, partner_order_id, tid, token])


    return (
        <div className="flex items-center justify-center h-screen">
            <div className="text-5xl font-bold">
                결제 대기중.
            </div>
        </div>
    )
}

export default PaymentProc
import { useEffect, useContext } from "react";
import { pointApproved } from "../Api";
import { P } from "../App";
const PaymentProc = () => {
    const token = window.location.search.split("=")[1];
    const tid = sessionStorage.getItem("tid");
    const partner_order_id = sessionStorage.getItem("orderId");
    const { point, setpoint } = useContext(P);
    useEffect(() => {
        // 창이 닫힐 때 실행될 함수
        window.onbeforeunload = function () {
            window.opener.postMessage('close', '*');
        };
        
        if (token && tid && partner_order_id) {
            let data = {
                tid: tid,
                partner_order_id: partner_order_id,
                partner_user_id: sessionStorage.getItem("id"),
                pg_token: token
            };
            pointApproved(data, setpoint);
        }
    }, [])


    return (
        <div>
            결제 대기중
        </div>
    )
}

export default PaymentProc
import { useEffect } from "react";
import { pointApproved } from "../Api";

const PaymentProc = () => {
    const token = window.location.search.split("=")[1];
    const tid = sessionStorage.getItem("tid");
    const partner_order_id = sessionStorage.getItem("orderId");
    useEffect(() => {
        if (token && tid && partner_order_id) {
            let data = {
                tid: tid,
                partner_order_id: partner_order_id,
                partner_user_id: sessionStorage.getItem("id"),
                pg_token: token
            };
            pointApproved(data);
        }
    }, [])
    return (
        <div>
            결제 대기중
        </div>
    )
}

export default PaymentProc
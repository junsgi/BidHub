import { useContext, useEffect } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import USER from "../context/userInfo";
import axios from "axios";

const TossProc = () => {
    const navi = useNavigate();
    const {user} = useContext(USER);
    const [params] = useSearchParams();
    const orderId = params.get("orderId");
    const amount = params.get("amount")
    useEffect(() => {
        const DATA = {
            tid : orderId,
            partner_user_id : user.id,
            pg_token : amount
        }
        const toss = async () => {
            const response = await axios.post("/member/toss", DATA)
                                        .then(res => res.data)
                                        .catch(e => {return {status : false, message : e.message}});
            alert(response.message);
            navi("/")
        }
        toss();
    }, [])
}
export default TossProc;
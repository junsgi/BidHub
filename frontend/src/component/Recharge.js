import { useCallback, useContext, useMemo, useState } from "react";
import { dot } from "../Api";
import axios from "axios";
import USER from "../context/userInfo";
import { Link, useNavigate } from "react-router-dom";

const Recharge = () => {
    const {user} = useContext(USER);
    const navi = useNavigate();
    const [recharge, setRecharge] = useState(0);
    const addDot = useMemo(()=>{
        return isNaN(recharge) ? 0 : dot(recharge);
    }, [recharge])
    
    const onChange = useCallback(e => 
        setRecharge(prev => isNaN(e.target.value) ? prev : +e.target.value), 
    [])

    const onClick = useCallback(async () => {
        if (isNaN(recharge)) {
            alert("숫자만 입력할 수 있습니다.");
        }else if (recharge <= 0){
            alert("금액을 입력해주세요.")
        }
        else{
            const DATA = {
                partner_user_id : user.id,
                total_amount : recharge
            }
            let response = await axios.post("/member/point", DATA)
                                      .then(res => res.data)
                                      .catch(e => {return {status : 400, message : e.message}});
            if (response.status !== 400) {
                let list = response.message.split("_");
                const URL = list[0];
                sessionStorage.setItem("tid", list[1]);
                sessionStorage.setItem("orderId", `${list[2]}_${list[3]}_${list[4]}`);
                window.open(URL);
                navi("/");

            } 
        }
    }, [recharge, user.id, navi])
    return (
        <div className="mt-4 w-64 m-auto">
            <p className="mb-4 text-2xl font-bold">포인트 충전</p>
            <label className="input mb-2 input-bordered flex items-center gap-2">
                <p className="text-2xl font-bold">P</p>
                <input type="text" className="grow" placeholder="금액" onChange={onChange} value={recharge}/>
            </label>
            <p className="mb-3 text-center">{addDot}원</p>
            
            <div className="w-64 input-bordered items-center">
                <button className="btn btn-block text-3xl btn-warning mb-2" onClick={onClick}>카카오페이</button>
                <Link to={"./toss"} state={{amount : recharge}} className="btn btn-block text-3xl btn-info mb-2" >토스</Link>
            </div>
        </div>
    );
}
export default Recharge;
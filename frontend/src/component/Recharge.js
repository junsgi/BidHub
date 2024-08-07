import { useCallback, useContext, useMemo, useState } from "react";
import { dot } from "../Api";
import axios from "axios";
import USER from "../context/userInfo";

const Recharge = () => {
    const {user, setUser} = useContext(USER);
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
        }else{
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
                console.log(list)
                window.open(URL)
            } 
        }
    }, [recharge])
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
            </div>
        </div>
    );
}
export default Recharge;
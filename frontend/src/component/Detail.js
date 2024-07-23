import axios from "axios";
import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

const Detail = () => {
    const ID = useMemo(() => window.location.pathname.split("/")[2])
    const [data, setData] = useState({
        aitemBid: "",
        aitemContent: "",
        aitemCurrent: "",
        aitemDate: "",
        aitemId: "",
        aitemImmediate: "",
        aitemStart: "",
        aitemTitle: "",
        memId: "",
        status: ""
    });
    const navi = useNavigate();
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
    }, [])
    return (
        <div className="flex w-3/5 flex-col border-opacity-50 ">
            <div className="w-full place-items-center"><img className="w-96" src={`http://localhost:3977/auctionitem/img/${ID}`}></img></div>
            <div className="divider">OR</div>
            <div className="card bg-base-300 rounded-box grid h-20 place-items-center">content</div>
        </div>
    )
}


export default Detail;
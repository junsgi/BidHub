import { useCallback, useEffect, useState } from "react";
import { getSucItems } from "../Api";
import AuctionItem from "./AuctionItem";

const Winning = () => {
    const [list, setList] = useState([]);
    const callBack = useCallback((res) => {
        const list = res.data;
        setList(() => {
            let result = [];
            for (let i = 0; i < list.length; i += 3) {
                let temp = [];

                for (let j = i; j < i + 3; j++) {
                    if (j >= list.length) break;
                    temp.push(<AuctionItem key={list[j].aitem_id} data={list[j]} />);
                }

                result.push(
                    <div key={i} className="flex flex-wrap justify-center mt-4">
                        {temp}
                    </div>
                );
            }
            return result;
        });
    }, [])
    useEffect(() => {
        getSucItems(callBack)
    }, [])
    return (
        <div>
            {list}
        </div>
    );
}

export default Winning
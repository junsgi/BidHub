import { useState } from "react";
import { getSucItems } from "../Api";

const Winning = () => {
    const [list, setList] = useState([]);
    getSucItems(setList)
    return (
        <div>
            {list}
        </div>
    );
}

export default Winning
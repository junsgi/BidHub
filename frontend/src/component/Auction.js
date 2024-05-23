import { useState } from "react";
import "../css/Auction.css";
import { Pagination } from 'react-bootstrap';
const Auction = ({list, SetList}) => {
    


    //paging start
    const [currentPage, setCurrentPage] = useState(1);
    const onPageClick = (num) => setCurrentPage(num);
    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <Pagination.Item key={number} active={number === currentPage} onClick={()=>onPageClick(number)}>
                {number}
            </Pagination.Item>,
        );
    }
    //paging end

    return (
        <>
            <table className="Auction">
                <tbody >
                    {
                        list.map((d, i) => {
                            return <Item
                                key={d.aitem_id}
                                data={d}
                                top={i * 160}
                            />
                        })
                    }
                </tbody>
                <tfoot>
                    <tr colSpan={2}>
                        <Pagination>{items}</Pagination>
                    </tr>
                </tfoot>
            </table>
        </>
    );
}

export default Auction;

function Item(props) {
    const id = props.data.aitem_id;
    const title = props.data.title;
    const current = props.data.current;
    const immediate = props.data.immediate;
    const remaining = parseInt(props.data.remaining) < 0 ? "경매 종료" : parseInt(props.data.remaining);
    const top = props.top;
    return (
        <tr className="item" style={{ top: `${top}px` }}>
            <td className="img element">
                <img src={`http://localhost:3977/auction/img/${id}`} width={128} height={128}></img>
            </td>
            <td className="info element">
                <h4>{title}</h4>
                <div className="price">
                    <p>현재가 : {current}</p>, &nbsp;
                    <p>즉시 구매가 : {immediate}</p>
                </div>
                <div>
                    남은 시간 : {remaining}
                </div>
            </td>
        </tr>
    );
}
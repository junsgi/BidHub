import "../css/Auction.css";
import { Pagination, Button } from 'react-bootstrap';
const Auction = ({ items, output }) => {
    return (
        <>
            <table className="Auction">
                <tbody >
                    {output}
                </tbody>
                <tfoot>
                    <tr>
                        <td style={{paddingTop : "20px"}}>
                            <Pagination>{items}</Pagination>
                        </td>

                        <td style={{textAlign : "end", marginRight : "15px"}}>
                            <Button variant="danger">기본순</Button>&nbsp;
                            <Button variant="danger">종료 제외</Button>&nbsp;
                            <Button variant="danger">내 것만</Button>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </>
    );
}

export default Auction;

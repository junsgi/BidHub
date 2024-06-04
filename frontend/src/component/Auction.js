import "../css/Auction.css";
import { Pagination, Button } from 'react-bootstrap';
const Auction = ({ items, output, onClick }) => {
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
                            <Button variant="danger" name = '0' onClick = {onClick}>기본순</Button>&nbsp;
                            <Button variant="danger" name = '1' onClick = {onClick}>종료 제외</Button>&nbsp;
                            <Button variant="danger" name = '2' onClick = {onClick}>내 것만</Button>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </>
    );
}

export default Auction;

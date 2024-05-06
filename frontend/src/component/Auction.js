import "../css/Auction.css";
const Auction = () => {
    return (
        <>
            <table className="Auction">
                <tbody >
                    {
                        [1, 2, 3, 4, 5].map((d, i)=>{
                            return <Item key={i}/>
                        })
                    }
                </tbody>
                <tfoot>
                    <tr colSpan = {2}>
                        
                    </tr>
                </tfoot>
            </table>
        </>
    );
}

export default Auction;

function Item() {
    return (
        <tr className="item" >
            <td className="img">
                <img src={process.env.PUBLIC_URL + "/img/bidhub.png"}></img>
            </td>
            <td className="info">
                <h4>제목</h4>
                <div className="price">
                    <p>현재가 : </p>, 
                    <p>즉시 구매가 : </p>
                </div>
                <div>
                    남은 시간
                </div>
            </td>
        </tr>
    );
}
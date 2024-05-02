import "../css/Auction.css";
const Auction = () => {
    return (
        <>
            <table className="Auction">
                <tbody >
                    {
                        [1, 2].map((d, i)=>{
                            return <Item key={i}/>
                        })
                    }
                </tbody>
            </table>
        </>
    );
}

export default Auction;

function Item() {
    return (
        <tr className="item" >
            <td><img src={process.env.PUBLIC_URL + "/img/bidhub.png"}></img></td>
            <td className="info">
                <h4>제목</h4>
                <div>
                    <p>현재가</p>
                    <p>적시 구매까</p>
                </div>
                <div>
                    남언 시간
                </div>
            </td>
        </tr>
    );
}
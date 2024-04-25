import "../App.css";
const Navi = () => {
    return (
        <nav onClick={()=>alert("BidHub!")}>
            <span className="Bid">Bid</span>
            <span className="Hub">Hub</span>
        </nav>
    );
}
export default Navi;
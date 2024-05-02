import "../css/Main.css";
import Auction from "./Auction";
import Home from "./Home";
const Main = () => {
    return (
        <div className="Main">
            <Auction />
            <Home />
        </div>
    );
}

export default Main;
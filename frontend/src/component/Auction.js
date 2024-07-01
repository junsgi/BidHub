import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { getAuctionItems } from "../Api";

const Auction = () => {
  const [AuctionObj, SetAuctionObj] = useState({
    len: 0,
    list: [],
  });
  const currentSort = useRef(0);
  const currentPage = useRef(1);
  const pageLength = useRef(0);
  useEffect(() => {
    getAuctionItems(SetAuctionObj, currentPage, currentSort, pageLength);
  }, []);
  return (
    <>
      {AuctionObj.list}
      <Paging />
    </>
  );
};

const Paging = () => {
  return (
    <div className="join flex justify-center mt-4 mb-4">
      <button className="join-item btn">«</button>
      <button className="join-item btn btn-active">1</button>
      <button className="join-item btn">2</button>
      <button className="join-item btn">3</button>
      <button className="join-item btn">4</button>
      <button className="join-item btn">»</button>
    </div>
  );
};
export default React.memo(Auction);

import React, {
  useCallback,
  useEffect,
  useReducer,
  useRef,
  useState,
} from "react";
import { getAuctionItems } from "../Api";
import AuctionItem from "./AuctionItem";

const currentPageAndSort = (state, action) => {
  console.log("ACTION!")
  console.log(action)
  switch (action.type) {
    case "UPDATE page":
      return {...state, page : action.value};
    case "UPDATE sort":
      return {...state, sort : action.value};
  }
}

const Auction = () => {
  const [AuctionObj, SetAuctionObj] = useState({
    len: 0,
    list: [],
  });
  const [current, dispatch] = useReducer(currentPageAndSort, { page : 1, sort : 0 });
  const pageLength = useRef(0);

  const callBack = (res) => {
    const list = res.data.list;
    console.log(res.data.len)
    pageLength.current = Math.ceil(res.data.len / 9);
    SetAuctionObj((prev) => {
      let result = [];
      for (let i = 0; i < list.length; i += 3) {
        let temp = [];

        for (let j = i; j < i + 3; j++) {
          if (j >= list.length) break;
          temp.push(<AuctionItem key = {list[j].aitem_id} data = {list[j]}/>);
        }

        result.push(
          <div key={i} className="flex flex-wrap justify-center mt-4">
            {temp}
          </div>
        );
      }
      return { ...prev, list: result };
    });
  }

  const updatePage = (num) => {
    console.log(num)
    dispatch({ type : "UPDATE page", value : num})
  }
  const updateSort = (num) => {
    dispatch({ type : "UPDATE sort", value : num})
  }
  useEffect(()=>{
    console.log("Auction mounted")
  }, [])
  useEffect(() => {
    console.log("change currentPage")
    getAuctionItems(callBack, current.page, current.sort);
  }, [current]);
  useEffect(()=>console.log("Auction update"));
  return (
    <>
      {AuctionObj.list}
      {pageLength.current >= 1 && <Paging pageLength = {pageLength} currentPage = {current.page} updatePage = {updatePage}/>}
    </>
  );
};

const Paging = ({pageLength, currentPage, updatePage}) => {
  const LEN = pageLength.current;
  console.log(LEN)
  const CURRENT = currentPage;

  const update = useCallback((idx)=>updatePage(Math.floor(LEN / 5) * 5 + idx + 1), []);

  const SELECT = " btn-active"
  const joinItemsList = new Array(LEN).fill(0).map((it, idx) => {
    let name = "join-item btn";
    if (idx + 1 === CURRENT) name += SELECT;
    return (<button key = {idx} className={name} onClick={()=>update(idx)}>
              {Math.floor(LEN / 5) * 5 + idx + 1}
            </button>);
  })
  return (
    <div className="join flex justify-center mt-4 mb-4">
      <button className="join-item btn">«</button>
      {joinItemsList}
      <button className="join-item btn">»</button>
    </div>
  );
};

export default React.memo(Auction);

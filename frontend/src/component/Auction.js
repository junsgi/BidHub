import React, {
  useCallback,
  useEffect,
  useMemo,
  useReducer,
  useRef,
  useState,
} from "react";
import { getAuctionItems } from "../Api";
import AuctionItem from "./AuctionItem";

const currentPageAndSort = (state, action) => {
  switch (action.type) {
    case "UPDATE page":
      sessionStorage.setItem("page", action.value);
      return { ...state, page: action.value };
    case "UPDATE sort":
      sessionStorage.setItem("sort", action.value);
      sessionStorage.setItem("page", 1);
      return { ...state, sort: action.value, page : 1 };
    default:
      return state;
  }
}

const Auction = () => {
  const [AuctionObj, SetAuctionObj] = useState({
    len: 0,
    list: [],
  });
  const [current, dispatch] = useReducer(currentPageAndSort, { 
    page: sessionStorage.getItem("page") ?? 1,
    sort: sessionStorage.getItem("sort") ?? 0
  });

  const nomal = useRef();
  const onlyMine = useRef();
  const pageLength = useRef(0);

  const callBack = useCallback((res) => {
    const list = res.data.list;
    pageLength.current = Math.ceil(res.data.len / 9);
    SetAuctionObj((prev) => {
      let result = [];
      for (let i = 0; i < list.length; i += 3) {
        let temp = [];

        for (let j = i; j < i + 3; j++) {
          if (j >= list.length) break;
          temp.push(<AuctionItem key={list[j].aitem_id} data={list[j]} />);
        }

        result.push(
          <div key={i} className="flex flex-wrap justify-center mt-4">
            {temp}
          </div>
        );
      }
      return { ...prev, list: result };
    });
  }, [])

  const updatePage = useCallback((num) => {
    dispatch({ type: "UPDATE page", value: num })
  }, [dispatch])

  const updateSort = useCallback(e => {
    let num = +e.target.getAttribute("name")
    if (num === 0){
      nomal.current.className = "tab text-2xl tab-active"
      onlyMine.current.className = "tab text-2xl"
    }else {
      nomal.current.className = "tab text-2xl"
      onlyMine.current.className = "tab text-2xl tab-active"
    }
    dispatch({ type: "UPDATE sort", value: num })
  }, [dispatch])

  useEffect(() => {
    getAuctionItems(callBack, current.page, current.sort);
  }, [current, callBack]);
  return (
    <div>
      <div className="flex flex-wrap m-auto mt-4 justify-center">
        <div role="tablist" className="tabs tabs-bordered">
          <span name = "0" role="tab" className="tab text-2xl tab-active" ref = {nomal} onClick={updateSort}>기본</span>
          <span name = "1" role="tab" className="tab text-2xl" ref = {onlyMine} onClick={updateSort}>내것만</span>
        </div>
      </div>
      {AuctionObj.list}
      {pageLength.current >= 1 && <Paging pageLength={pageLength} currentPage={current.page} updatePage={updatePage} />}
    </div>
  );
};

const Paging = ({ pageLength, currentPage, updatePage }) => {
  const LEN = pageLength.current;
  const CURRENT = currentPage;
  const update = useCallback((idx) => updatePage(Math.floor(LEN / 5) * 5 + idx + 1), [LEN, updatePage]);

  const SELECT = " btn-active"
  const joinItemsList = useMemo(() => {
    return new Array(LEN).fill(0).map((it, idx) => {
      let name = "join-item btn";
      if (idx + 1 === +CURRENT) {
        name += SELECT;
      }
      return (<button key={idx} className={name} onClick={() => update(idx)}>
        {Math.floor(LEN / 5) * 5 + idx + 1}
      </button>);
    })
  }, [LEN, CURRENT, update])
  return (
    <div className="join flex justify-center mt-4 mb-4">
      <button className="join-item btn">«</button>
      {joinItemsList}
      <button className="join-item btn">»</button>
    </div>
  );
};

export default Auction;

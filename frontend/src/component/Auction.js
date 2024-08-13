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
      return { ...state, sort: action.value };
    default:
      return state;
  }
}

const Auction = () => {
  const [AuctionObj, SetAuctionObj] = useState({
    len: 0,
    list: [],
  });
  const p = sessionStorage.getItem("page") ?? 1;
  const [current, dispatch] = useReducer(currentPageAndSort, { page: p, sort: 0 });
  const pageLength = useRef(0);

  const callBack = (res) => {
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
  }

  const updatePage = (num) => {
    dispatch({ type: "UPDATE page", value: num })
  }
  const updateSort = (num) => {
    dispatch({ type: "UPDATE sort", value: num })
  }

  useEffect(() => {
    getAuctionItems(callBack, current.page, current.sort);
  }, [current]);
  useEffect(() => {
    console.log("AUCTION MOUNTED");
  }, [])
  useEffect(() => {
    console.log("AUCTION UPDATE");
  })
  return (
    <>
      {AuctionObj.list}
      {pageLength.current >= 1 && <Paging pageLength={pageLength} currentPage={current.page} updatePage={updatePage} />}
    </>
  );
};

const Paging = React.memo(({ pageLength, currentPage, updatePage }) => {
  const LEN = pageLength.current;
  const CURRENT = currentPage;
  const update = useCallback((idx) => updatePage(Math.floor(LEN / 5) * 5 + idx + 1), [LEN, updatePage]);

  const SELECT = " btn-active"
  const joinItemsList = useMemo(() => {
    return new Array(LEN).fill(0).map((it, idx) => {
      let name = "join-item btn";
      if (idx + 1 == CURRENT) {
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
});

export default Auction;

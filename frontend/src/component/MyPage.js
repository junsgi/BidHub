import React from "react";
const MyPage = () => {
  const ID = sessionStorage.getItem("id");
  const POINT = sessionStorage.getItem("point");
  const NICK = sessionStorage.getItem("nickname");
  return (
    <div className="mt-4">
      <p className="m-auto mb-4 w-64 text-2xl">마이페이지</p>
      <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 16 16"
          fill="currentColor"
          className="h-4 w-4 opacity-70">
          <path
            d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" />
        </svg>
        <input type="text" className="grow" name="id" value={ID + " (id)"} readOnly />
      </label>
      <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 16 16"
          fill="currentColor"
          className="h-4 w-4 opacity-70">
          <path
            d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" />
        </svg>
        <input type="text" className="grow" value={NICK + " (nickname)"} readOnly />
      </label>
      <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
        <p className="text-2xl font-bold">P</p>
        <input type="text" className="grow" value={POINT + " (point)"} readOnly />
      </label>
      <div className="m-auto w-64 input-bordered items-center">
        <button className="btn w-64 btn-block text-3xl mb-2">정보 수정</button>
        <button className="btn w-64 btn-block text-3xl btn-info mb-2">포인트 충전</button>

      </div>
    </div>
  );
};


export default MyPage;

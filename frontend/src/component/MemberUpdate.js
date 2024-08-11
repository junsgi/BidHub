import React, { useCallback, useContext, useReducer } from "react";
import { updateNickOrPasswd } from "../Api";
import USER from "../context/userInfo";

const reducer = (state, action) => {
    switch(action.status){
        case "UPDATE":
            return { ...state, [action.name]: action.value };
        case "PASSWD":
            return {...state, newPw : "", check : ""};
        default:
            return {...state, nick : ""}

    }
}
function MemberUpdate() {
    const {user, setUser} = useContext(USER);
    const [update, dispatch] = useReducer(reducer, { nick: "", newPw: "", check: "" });
    const onChange = useCallback(e => dispatch({ name: e.target.name, value: e.target.value, status : "UPDATE" }), [dispatch])
    const successCB = useCallback(path => {
        if (path === "nickname") {
            setUser(prev => {
                console.log(prev)
                return {...prev, nickname : update.nick}
            })
        }
        dispatch({status : path.toUpperCase()})
    }, [dispatch, setUser, update.nick])

    const onClick = useCallback(e => {
        const PATH = e.target.name;
        const DATA = {
            id: user.id,
            before: "",
            after: ""
        }
        if (PATH === "nickname") {
            if (!update.nick) alert("새 닉네임을 입력해주세요");
            else {
                DATA.after = update.nick;
                updateNickOrPasswd(DATA, PATH, successCB);
            }
        } else {
            let flag = update.newPw && update.check;
            if (!flag) alert("비밀번호를 입력해주세요");
            else if (update.newPw !== update.check) alert("새 비밀번호가 틀렸습니다.");
            else {
                DATA.before = update.check;
                DATA.after = update.newPw;
                updateNickOrPasswd(DATA, PATH, successCB);
            }

        }
    }, [user, update, successCB])
    return (
        <div className="mt-4 w-64 m-auto">
            <p className="m-auto mb-4 w-64 text-2xl">정보수정</p>
            <p className="m-auto mb-4 w-64 text-xl">비밀번호 수정</p>
            <form>
                <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                    <input type="password" className="grow" placeholder="새 비밀번호" name = "newPw" onChange={onChange} value = {update.newPw}/>
                </label>
                <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                    <input type="password" className="grow" placeholder="새 비밀번호 확인" name = "check" onChange={onChange} value = {update.check}/>
                </label>
            </form>
            <button className="btn w-64 btn-info text-3xl mb-4" onClick={onClick} name = "passwd">비밀번호 수정</button>


            <p className="m-auto mb-2 w-64 text-xl">닉네임 수정</p>
            <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                <input type="text" className="grow" placeholder="새 닉네임" name = "nick" onChange={onChange} value = {update.nick}/>
            </label>
            <button className="btn w-64 btn-info text-3xl mb-4" onClick={onClick} name = "nickname">닉네임 수정</button>

        </div>
    );
}

export default MemberUpdate;
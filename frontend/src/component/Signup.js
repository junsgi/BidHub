import { useCallback, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
const Signup = () => {
    const navi = useNavigate();
    const [data, SetData] = useState({
        id: "",
        nickname: "",
        pw: "",
        pwCheck: ""
    })
    const onChange = useCallback((e) => SetData(prev => { return { ...prev, [e.target.name]: e.target.value } }), [])

    const submit = useCallback(async () => {
        const { id, nickname, pw, pwCheck } = data
        if (!(id && pw && pwCheck)) {
            alert("* 모양이 있는 곳은 필수 입력란입니다.");
            return;
        }
        if (pw !== pwCheck) {
            alert("비밀번호가 틀렸습니다.");
            return;
        }

        let URL = "http://localhost:3977/member/signup";
        const response = await axios
                                .post(URL, data)
                                .then((res) => {return res.data;})
                                .catch((e) => {return {status : false, message : e.message};});
        alert(response.message);
        if (response.status) {
            navi("/login")
        }
    }, [data])

    return (
        <div className="mt-4">
            <p className="m-auto mb-4 w-64 text-2xl">회원가입</p>
            <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    className="h-4 w-4 opacity-70">
                    <path
                        d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6ZM12.735 14c.618 0 1.093-.561.872-1.139a6.002 6.002 0 0 0-11.215 0c-.22.578.254 1.139.872 1.139h9.47Z" />
                </svg>
                <input type="text" className="grow" placeholder="id *" name="id" onChange={onChange} />
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
                <input type="text" className="grow" placeholder="nickname" name="nickname" onChange={onChange} />
            </label>
            <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    className="h-4 w-4 opacity-70">
                    <path
                        fillRule="evenodd"
                        d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                        clipRule="evenodd" />
                </svg>
                <input type="password" className="grow" placeholder="password *" name="pw" onChange={onChange} />
            </label>
            <label className="input mb-2 m-auto w-64 input-bordered flex items-center gap-2">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    className="h-4 w-4 opacity-70">
                    <path
                        fillRule="evenodd"
                        d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                        clipRule="evenodd" />
                </svg>
                <input type="password" className="grow" placeholder="password check *" name="pwCheck" onChange={onChange} />
            </label>
            <div className="flex m-auto w-64 input-bordered flex items-center">
                <button to="signup" className="btn bg-amber-300 w-full" onClick={submit}>Signup</button>
            </div>
        </div>
    );
}

export default Signup;
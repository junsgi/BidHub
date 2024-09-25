import { useMemo, useRef, useState } from "react";
import { Form, InputGroup, FloatingLabel, Button } from "react-bootstrap";
import { dot, submit } from "../Api";
import { useNavigate } from "react-router-dom";
const Regist = () => {
    const navi = useNavigate();
    const imgRef = useRef();
    const [data, SetData] = useState({
        title: "",
        start: "",
        immediate: "",
        bid: "",
        date: null,
        img: null,
        content: "",
        userId: sessionStorage.getItem("id")
    });
    const {start, immediate, bid} = useMemo(()=>{
        let start = dot(data.start);
        let immediate = dot(data.immediate);
        let bid = dot(data.bid);
        return {start, immediate, bid}
    }, [data.start, data.immediate, data.bid])

    const handler = (e) => {
        SetData(prev => {
            if ((e.target.name === "start" || 
                e.target.name === "immediate" ||
                e.target.name === "bid") &&
                isNaN(e.target.value)
            ){
                return prev
            }
            return {...prev, [e.target.name] : e.target.value}
        });
    }
    
    const request = async () => {
        let file = null
        let form = new FormData();
        if (imgRef.current.files.length === 1 && 
            imgRef.current.files[0].type.substring(0, 5) === "image") {
            file = imgRef.current.files[0];
        }
        for(const i of Object.keys(data)){
            if ((i === "title" || i === "start" || i === "bid" || i === "date") && !data[i]){
                alert("필수 입력란이 비어있습니다.")
                return
            }

            form.append(i, data[i]);
        }
        if (!file) form.set("img", new Blob());
        else form.set("img", file);
        console.log("Tlqkfsuddk")
        for(const i of form){
            console.log(i)
        }
        const response = await submit(form);
        if (response.status) {
            navi('/')
        }
        
    }

    let min = useMemo(()=>{
        let temp = new Date();
        temp.setHours(temp.getHours() + 9);
        temp = temp.toISOString();
        temp = temp.substring(0, temp.lastIndexOf(".") - 3)
        return temp
    }, [])
    return (
        <div className="regist w-96 m-auto">
            <h4 className="text-3xl font-bold mb-2">경매 등록</h4>
            <p className="text-2xl">제목 <span className="text-red-600">*</span></p>
            <InputGroup className="mb-3 ">
                <Form.Control
                    placeholder="제목"
                    name = "title"
                    className="h-12 text-xl"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                    value = {data.title}
                />
            </InputGroup>

            <p className="text-2xl">시작가 <span className="text-red-600">*</span></p>
            <InputGroup >
                <Form.Control
                    name = "start"
                    placeholder="시작 가격"
                    className="h-12 text-xl"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                    value = {data.start}
                />
            </InputGroup>
            <p className="mb-3">{start}원</p>

            <p className="text-2xl">즉시 구매가</p>
            <InputGroup>
                <Form.Control
                name = "immediate"
                    placeholder="즉시 구매가"
                    className="h-12 text-xl"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                    value = {data.immediate}
                />
            </InputGroup>
            <p className="mb-3">{immediate}원</p>

            <p className="text-2xl">입찰 단위 <span className="text-red-600">*</span></p>
            <InputGroup>
                <Form.Control
                name = "bid"
                    placeholder="입찰 단위"
                    className="h-12 text-xl"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                    value = {data.bid}
                />
            </InputGroup>
            <p className="mb-3">{bid}원</p>

            <p className="text-2xl">경매 기간 <span className="text-red-600">*</span></p>
            <InputGroup className="mb-3">
                <Form.Control
                name = "date"
                    type="datetime-local"
                    className="h-12 text-l"
                    aria-describedby="basic-addon1"
                    min={min}
                    onChange={handler}
                    required = {true}
                    value = {data.date}
                />
            </InputGroup>

            <p className="text-2xl">사진</p>
            <InputGroup className="mb-3">
                <Form.Control
                    type="file"
                    name = "img"
                    className="h-10 text-xl"
                    multiple="multiple"
                    ref={imgRef}
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            <p className="text-2xl">물품 설명<sub>(최대 300자)</sub></p>
            <FloatingLabel controlId="floatingTextarea2">
                <Form.Control
                    as="textarea"
                    name="content"
                    className="h-12 text-xl"
                    onChange={handler}
                    placeholder="Leave a comment here"
                    maxLength={300}
                    style={{ height: '100px' }}
                    required = {true}
                    value = {data.content}
                />
            </FloatingLabel>
            <br />
            <div style={{textAlign:'end'}}>
                <Button variant="primary" onClick={request}>경매 등록</Button>
            </div>
        </div>
    );
}

export default Regist;
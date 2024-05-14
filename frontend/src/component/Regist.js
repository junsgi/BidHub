import { useEffect, useRef, useState } from "react";
import { Form, InputGroup, FloatingLabel, Button } from "react-bootstrap";
import { submit } from "../Api";

const Regist = ({ SetStatus }) => {
    const back = () => SetStatus("member");
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
    const handler = (e) => {
        SetData({
            ...data,
            [e.target.name] : e.target.value
        });
    }

    const request = () => {
        let file = null
        let form = new FormData();
        if (imgRef.current.files.length === 1 && 
            imgRef.current.files[0].type.substring(0, 5) === "image") {
            file = imgRef.current.files[0];
        }
        for(const i of Object.keys(data)){
            form.append(i, data[i]);
        }
        if (!file) form.set("img", new Blob());
        else form.set("img", file);
        submit(form);
    }
    return (
        <div className="regist">
            <h3 onClick={back}> {"<"} </h3>
            <h3>경매 등록</h3>

            제목
            <InputGroup className="mb-3">
                <Form.Control
                    placeholder="제목"
                    name = "title"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            시작가
            <InputGroup className="mb-3">
                <Form.Control
                    name = "start"
                    placeholder="시작 가격"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            즉시 구매가
            <InputGroup className="mb-3">
                <Form.Control
                name = "immediate"
                    placeholder="즉시 구매가"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            입찰 단위
            <InputGroup className="mb-3">
                <Form.Control
                name = "bid"
                    placeholder="입찰 단위"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            경매 기간
            <InputGroup className="mb-3">
                <Form.Control
                name = "date"
                    type="datetime-local"
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            사진
            <InputGroup className="mb-3">
                <Form.Control
                    type="file"
                    name = "img"
                    multiple="multiple"
                    ref={imgRef}
                    aria-describedby="basic-addon1"
                    onChange={handler}
                />
            </InputGroup>

            물품 설명
            <FloatingLabel controlId="floatingTextarea2">
                <Form.Control
                    as="textarea"
                    name="content"
                    onChange={handler}
                    placeholder="Leave a comment here"
                    style={{ height: '100px' }}
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
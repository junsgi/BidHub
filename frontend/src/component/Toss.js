import { loadTossPayments, ANONYMOUS } from "@tosspayments/tosspayments-sdk";
import { useContext, useEffect, useState } from "react";
import USER from "../context/userInfo";
import { useLocation, useNavigate } from "react-router-dom";
import { dot } from "../Api";

const customerKey = "ydiDnCHNfp4stZUKO9P7S";

export default function Toss() {
    const navi = useNavigate();
    const location = useLocation();
    const AMOUNT = location.state?.amount ?? "error";
    // const clientKey = "test_ck_vZnjEJeQVxeazAwbvk6zrPmOoBN0";
    const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm"
    const [amount, setAmount] = useState({
        currency: "KRW",
        value: AMOUNT,
    });
    const [ready, setReady] = useState(false);
    const [widgets, setWidgets] = useState(null);

    useEffect(() => {
        async function fetchPaymentWidgets() {
            // ------  결제위젯 초기화 ------
            const tossPayments = await loadTossPayments(clientKey);

            // 회원 결제
            const widgets = tossPayments.widgets({customerKey: clientKey});
            // 비회원 결제
            // const widgets = tossPayments.widgets({ customerKey: ANONYMOUS });

            setWidgets(widgets);
        }
        if (isNaN(AMOUNT)) {
            alert("다시 시도해주세요");
            navi(-1);
        }else {
            fetchPaymentWidgets();
        }
    }, [clientKey, customerKey]);

    useEffect(() => {
        async function renderPaymentWidgets() {
            if (widgets == null) {
                return;
            }
            // ------ 주문의 결제 금액 설정 ------
            await widgets.setAmount(amount);

            await Promise.all([
                // ------  결제 UI 렌더링 ------
                widgets.renderPaymentMethods({
                    selector: "#payment-method",
                    variantKey: "DEFAULT",
                }),
                // ------  이용약관 UI 렌더링 ------
                widgets.renderAgreement({
                    selector: "#agreement",
                    variantKey: "AGREEMENT",
                }),
            ]);

            setReady(true);
        }

        renderPaymentWidgets();
    }, [widgets]);

    useEffect(() => {
        if (widgets == null) {
            return;
        }

        setAmount(amount);
    }, [widgets, amount]);

    return (
        <div className="wrapper m-auto w-4/6">
            <div className="box_section">
                {/* 결제 UI */}
                <div id="payment-method" />

                {/* 이용약관 UI */}
                <div id="agreement" />

                {/* 결제하기 버튼 */}
                <button
                    className="btn btn-info text-3xl mb-2 mt-2 max-w-full w-11/12 ml-6"
                    disabled={!ready}
                    onClick={async () => {
                        try {
                            // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
                            // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
                            // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
                            await widgets.requestPayment({
                                orderId: "jhvSCJ8pzpNN8UK7IxKnH",
                                orderName: "BidHub 포인트 충전",
                                successUrl: window.location.origin + "/success",
                                failUrl: window.location.origin + "/fail",
                                customerEmail: "customer123@gmail.com",
                                customerName: "김토스",
                                customerMobilePhone: "01012341234",
                            });
                        } catch (error) {
                            // 에러 처리하기
                            console.error(error);
                        }
                    }}
                >
                    {dot(AMOUNT)}원 결제하기
                </button>
            </div>
        </div>
    );
}

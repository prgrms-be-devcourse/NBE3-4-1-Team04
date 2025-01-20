import React from 'react';

const PaymentButton = ({ handlePayment }) => {
    return (
        <div className="card border">
            <div className="card-body">
                <h5 className="card-title text-center mb-4">배송 정보</h5>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일 주소</label>
                    <input type="email" className="form-control" id="email" placeholder="이메일을 입력하세요" />
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">배송지 주소</label>
                    <input type="text" className="form-control" id="address" placeholder="배송지 주소를 입력하세요" />
                </div>
                <div className="d-flex justify-content-end">
                    <button className="btn btn-primary" onClick={handlePayment}>결제하기</button>
                </div>
            </div>
        </div>
    );
};

export default PaymentButton;

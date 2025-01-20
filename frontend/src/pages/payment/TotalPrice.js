import React from 'react';

const TotalPrice = ({ totalItems, totalPrice }) => {
    return (
        <div>
            <div className="d-flex justify-content-between align-items-center" style={{ width: '100%', padding: '10px', borderRadius: '5px' }}>
                <div style={{ flex: '0 0 70%' }}>
                    <h5 className="fw-bolder">
                        총계 <span style={{ fontStyle: 'italic' }}>(VAT 포함)</span>
                    </h5>
                </div>
                <div style={{ flex: '0 0 10%', textAlign: 'center' }}>
                    <p>{totalItems} <span>ea</span></p>
                </div>
                <div style={{ flex: '0 0 20%', textAlign: 'right' }}>
                    <p>${totalPrice}</p>
                </div>
            </div>
            <hr />
            <div style={{ textAlign: 'right' }}>
                <span style={{ fontSize: '0.7rem', color: 'red' }}>
                    * 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
                </span>
            </div>
        </div>
    );
};

export default TotalPrice;

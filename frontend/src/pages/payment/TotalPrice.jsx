import React from 'react';

const TotalPrice = ({ totalItems, totalPrice }) => {
    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-2">
                <div>
                    <h5 className="mb-0" style={{
                        fontSize: '1.2rem',
                        fontWeight: 'bold'
                    }}>
                        총계<span style={{
                            color: '#6c757d',
                            marginLeft: '8px',
                            fontWeight: 'normal'
                        }}>(VAT포함)</span>
                    </h5>
                </div>
                <div className="d-flex align-items-center">
                    <span style={{
                        marginRight: '40px',
                        fontSize: '1.1rem'
                    }}>{totalItems}ea</span>
                    <span style={{
                        fontSize: '1.3rem',
                        fontWeight: 'bold'
                    }}>₩{totalPrice.toLocaleString()}</span>
                </div>
            </div>
            <p className="text-danger mb-0" style={{
                fontSize: '0.8rem',
                marginTop: '10px'
            }}>
                • 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </p>
        </div>
    );
};

export default TotalPrice;
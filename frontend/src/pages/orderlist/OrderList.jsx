import React from 'react';
import Header from '../../components/header/Header';
import Navigation from '../../components/navigation/Navigation';

const OrderList = () => {
    return (
        <div>
            <Header />
            <Navigation />
            <div className="container">
                <h2>조회 결과</h2>
                {/* 주문 목록 */}
            </div>
        </div>
    );
};

export default OrderList; 
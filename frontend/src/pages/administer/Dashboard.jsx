// dashboard.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
    const navigate = useNavigate();

    return (
        <div className="dashboard-container">
            <div className="sidebar">
                <h2>관리자 메뉴</h2>
                <ul>
                    <li onClick={() => navigate('/manage-stock')}>상품 입출고 관리</li>
                    <li onClick={() => navigate('/aggregate-orders')}>배송 주문 취합</li>
                    <li onClick={() => navigate('/customer-orders')}>주문 고객 정보 조회</li>
                    <li onClick={() => navigate('/add-product')}>신규 상품 등록</li>
                </ul>
            </div>
            <div className="content">
                <div className="header">
                    <h1 className="welcome-title">환영합니다, 관리자님!</h1>
                    <p className="welcome-message">좌측 메뉴에서 원하는 작업을 선택하세요.</p>
                </div>

            </div>
        </div>
    );
};

export default Dashboard;

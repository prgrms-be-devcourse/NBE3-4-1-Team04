import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import './Navigation.css';

const Navigation = () => {
    const location = useLocation();

    const getNavigationText = () => {
        switch (location.pathname) {
            case '/':
                return '상품목록';
            case '/payment':
                return (
                    <>
                        <Link to="/" className="nav-link">상품목록</Link>
                        <span className="nav-separator">&gt;</span>
                        <span>결제하기</span>
                    </>
                );
            case '/orders':
                return (
                    <>
                        <Link to="/" className="nav-link">상품목록</Link>
                        <span className="nav-separator">&gt;</span>
                        <span>주문내역 조회</span>
                    </>
                );
            case '/orderlist':
                return (
                    <>
                        <Link to="/" className="nav-link">상품목록</Link>
                        <span className="nav-separator">&gt;</span>
                        <Link to="/orders" className="nav-link">주문내역 조회</Link>
                        <span className="nav-separator">&gt;</span>
                        <span>조회결과</span>
                    </>
                );
            case '/administer':
                return '관리자';
            default:
                return '상품목록';
        }
    };

    return (
        <div className="border-bottom">
            <div className="container py-3">
                <h2 className="h4 mb-0">{getNavigationText()}</h2>
            </div>
        </div>
    );
};

export default Navigation;
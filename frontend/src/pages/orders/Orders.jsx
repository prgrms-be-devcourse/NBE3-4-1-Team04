import React, { useState } from 'react';
import Navigation from '../../components/navigation/Navigation';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';

const Orders = () => {
    const [searchType, setSearchType] = useState('orderNumber');

    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <main className="container mt-5">
                    <h2>주문 내역</h2>
                    <div className="row">
                        <div className="col-12">
                            <div className="card">
                                <div className="card-body">
                                    <div className="d-flex mb-3">
                                        <div className="form-check me-4">
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="searchType"
                                                id="orderNumber"
                                                checked={searchType === 'orderNumber'}
                                                onChange={() => setSearchType('orderNumber')}
                                            />
                                            <label className="form-check-label" htmlFor="orderNumber">
                                                주문번호 조회
                                            </label>
                                        </div>
                                        <div className="form-check">
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="searchType"
                                                id="email"
                                                checked={searchType === 'email'}
                                                onChange={() => setSearchType('email')}
                                            />
                                            <label className="form-check-label" htmlFor="email">
                                                이메일 조회
                                            </label>
                                        </div>
                                    </div>

                                    {searchType === 'orderNumber' ? (
                                        <div className="order-number-search">
                                            <h5>주문번호 조회</h5>
                                            <p className="text-danger small">(입력된 주문번호에 대한 주문 내역 확인)</p>
                                            <div className="input-group mb-3">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    placeholder="주문번호 예시) 250114123456"
                                                />
                                                <button className="btn btn-success" type="button">조회</button>
                                            </div>
                                            <p className="small text-muted">• 주문번호는 모든 절차가 종료된 후 안내됩니다.</p>
                                        </div>
                                    ) : (
                                        <div className="email-search">
                                            <h5>이메일 조회</h5>
                                            <p className="text-danger small">(입력된 이메일과 관련된 모든 주문 내역 확인)</p>
                                            <div className="input-group mb-3">
                                                <input
                                                    type="email"
                                                    className="form-control"
                                                    placeholder="이메일 예시) Grids&Circles@gamil.com"
                                                />
                                                <button className="btn btn-success" type="button">조회</button>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
            <Footer />
        </div>
    );
};

export default Orders;
import React, { useState } from 'react';
import Navigation from '../../components/navigation/Navigation';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import { useNavigate } from 'react-router-dom';

const Orders = () => {
    const [searchType, setSearchType] = useState('orderNumber');
    const [searchValue, setSearchValue] = useState('');
    const navigate = useNavigate();

    const handleSearch = () => {
        if (!searchValue.trim()) {
            alert(searchType === 'orderNumber' ? '주문번호를 입력해주세요.' : '이메일을 입력해주세요.');
            return;
        }

        if (searchType === 'email' && !isValidEmail(searchValue)) {
            alert('유효한 이메일 주소를 입력해주세요.');
            return;
        }

        // OrderList 페이지로 이동하면서 검색 정보 전달
        navigate('/orderlist', {
            state: {
                searchType,
                searchValue: searchValue.trim()
            }
        });
    };

    const isValidEmail = (email) => {
        return email.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
    };

    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <main className="container mt-5 mb-5">
                    <div className="row justify-content-center">
                        <div className="col-md-10">
                            <div className="card" style={{ 
                                backgroundColor: '#fdfbf6',
                                padding: '2rem',
                                minHeight: '400px',
                                borderRadius: '20px'
                            }}>
                                <div className="card-body">
                                    <div className="d-flex mb-4">
                                        <div className="form-check me-5">
                                            <input
                                                className="form-check-input"
                                                type="radio"
                                                name="searchType"
                                                id="orderNumber"
                                                checked={searchType === 'orderNumber'}
                                                onChange={() => setSearchType('orderNumber')}
                                                style={{ transform: 'scale(1.2)' }}
                                            />
                                            <label className="form-check-label fs-5" htmlFor="orderNumber">
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
                                                style={{ transform: 'scale(1.2)' }}
                                            />
                                            <label className="form-check-label fs-5" htmlFor="email">
                                                이메일 조회
                                            </label>
                                        </div>
                                    </div>

                                    {searchType === 'orderNumber' ? (
                                        <div className="order-number-search p-4">
                                            <h4 className="mb-3">주문번호 조회</h4>
                                            <p className="text-danger mb-4 fs-6">(입력된 주문번호 단건에 대한 주문내역 확인)</p>
                                            <div className="mb-4">
                                                <input
                                                    type="text"
                                                    className="form-control form-control-lg"
                                                    placeholder="주문번호 예시) 250114123456"
                                                    value={searchValue}
                                                    onChange={(e) => setSearchValue(e.target.value)}
                                                />
                                            </div>
                                            <p className="text-muted fs-6">• 주문번호는 결제가 종료된 이후 안내됩니다.</p>
                                            <div className="text-center mt-4">
                                                <button 
                                                    className="btn btn-lg px-5 py-2" 
                                                    style={{
                                                        backgroundColor: '#5C8A3C',
                                                        color: 'white',
                                                        borderRadius: '30px',
                                                        minWidth: '200px'
                                                    }}
                                                    onClick={handleSearch}
                                                >
                                                    조 회 →
                                                </button>
                                            </div>
                                        </div>
                                    ) : (
                                        <div className="email-search p-4">
                                            <h4 className="mb-3">이메일 조회</h4>
                                            <p className="text-danger mb-4 fs-6">(입력된 이메일과 관련된 모든 주문내역 확인)</p>
                                            <div className="mb-4">
                                                <input
                                                    type="email"
                                                    className="form-control form-control-lg"
                                                    placeholder="이메일 예시) Grids&Circles@gamil.com"
                                                    value={searchValue}
                                                    onChange={(e) => setSearchValue(e.target.value)}
                                                />
                                            </div>
                                            <div className="text-center mt-4">
                                                <button 
                                                    className="btn btn-lg px-5 py-2" 
                                                    style={{
                                                        backgroundColor: '#5C8A3C',
                                                        color: 'white',
                                                        borderRadius: '30px',
                                                        minWidth: '200px'
                                                    }}
                                                    onClick={handleSearch}
                                                >
                                                    조 회 →
                                                </button>
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
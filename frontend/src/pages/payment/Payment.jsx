import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from '../../components/header/Header.jsx';
import Navigation from '../../components/navigation/Navigation.jsx';
import Footer from '../../components/footer/Footer.jsx';
import ProductList from './ProductList';
import TotalPrice from './TotalPrice';
import PaymentButton from './PaymentButton';
import PaymentConfirmation from './modal/PaymentConfirmation';
import useLocalStorage from "../../components/localstorage/LocalStorage";

const Payment = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [totalItems, setTotalItems] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0);
    const [showModal, setShowModal] = useState(false);
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [orderItems, setOrderItems, clearOrderItems] = useLocalStorage('orderItems', []);

    useEffect(() => {
        if (!location.state?.selectedItems) {
            alert('선택된 상품이 없습니다.');
            navigate('/');
            return;
        }
    }, [location.state, navigate]);

    const handlePayment = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleConfirmPayment = () => {
        clearOrderItems();
        setShowModal(false);
        alert('결제가 완료되었습니다.');
        navigate('/');
    };

    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <main className="container mt-4">
                    {location.state?.selectedItems && (
                        <div className="row justify-content-center">
                            <div className="col-md-10">
                                <div className="card mb-4" style={{
                                    backgroundColor: '#fdfbf6',
                                    borderRadius: '20px',
                                    border: '1px solid #eee',
                                    boxShadow: '0 2px 4px rgba(0,0,0,0.05)'
                                }}>
                                    <div className="card-body p-4">
                                        <h2 className="card-title mb-4" style={{
                                            fontSize: '2rem',
                                            fontWeight: 'bold'
                                        }}>
                                            Picked-Up List
                                        </h2>
                                        <div className="mb-4">
                                            <ProductList
                                                initialItems={location.state.selectedItems}
                                                setTotalItems={setTotalItems}
                                                setTotalPrice={setTotalPrice}
                                            />
                                        </div>
                                        <div style={{
                                            borderTop: '1px solid #dee2e6',
                                            paddingTop: '20px',
                                            marginTop: '20px'
                                        }}>
                                            <div className="d-flex justify-content-between align-items-center">
                                                <h4 className="mb-0" style={{
                                                    fontSize: '1.5rem',
                                                    marginLeft: '80px'
                                                }}>
                                                    총계<span className="text-muted">(VAT포함)</span>
                                                </h4>
                                                <div className="d-flex align-items-center">
                                                    <span style={{
                                                        fontSize: '1.5rem',
                                                        marginRight: '50px'
                                                    }}>{totalItems}개</span>
                                                    <span style={{
                                                        fontSize: '1.8rem',
                                                        fontWeight: 'bold'
                                                    }}>₩{totalPrice.toLocaleString()}</span>
                                                </div>
                                            </div>
                                            <p className="text-danger mt-3 mb-0" style={{ fontSize: '0.9rem' }}>
                                                • 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
                                            </p>
                                        </div>
                                    </div>
                                </div>

                                <div className="card" style={{
                                    backgroundColor: '#fdfbf6',
                                    borderRadius: '20px',
                                    border: '1px solid #eee'
                                }}>
                                    <div className="card-body p-4">
                                        <div className="row">
                                            <div className="col-md-8">
                                                <div style={{ height: '120px' }}>
                                                    <div className="mb-3">
                                                        <div className="d-flex align-items-center">
                                                            <label className="form-label mb-0 me-3" style={{
                                                                fontSize: '1.1rem',
                                                                fontWeight: '500',
                                                                width: '120px'
                                                            }}>이메일 주소</label>
                                                            <input
                                                                type="email"
                                                                className="form-control form-control-lg"
                                                                placeholder="이메일 예시) Grids&Circles@gamil.com"
                                                                value={email}
                                                                onChange={(e) => setEmail(e.target.value)}
                                                                style={{
                                                                    backgroundColor: 'white',
                                                                    border: '1px solid #ced4da',
                                                                    borderRadius: '10px',
                                                                    fontSize: '1rem',
                                                                    padding: '8px 15px',
                                                                    height: '50px'
                                                                }}
                                                            />
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <div className="d-flex align-items-center">
                                                            <label className="form-label mb-0 me-3" style={{
                                                                fontSize: '1.1rem',
                                                                fontWeight: '500',
                                                                width: '120px'
                                                            }}>배송지 주소</label>
                                                            <input
                                                                type="text"
                                                                className="form-control form-control-lg"
                                                                placeholder="주소 예시) 경상남도 거제시 00동"
                                                                value={address}
                                                                onChange={(e) => setAddress(e.target.value)}
                                                                style={{
                                                                    backgroundColor: 'white',
                                                                    border: '1px solid #ced4da',
                                                                    borderRadius: '10px',
                                                                    fontSize: '1rem',
                                                                    padding: '8px 15px',
                                                                    height: '50px'
                                                                }}
                                                            />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="col-md-4 d-flex align-items-center justify-content-center">
                                                <button
                                                    className="btn w-100"
                                                    style={{
                                                        backgroundColor: '#5C8A3C',
                                                        color: 'white',
                                                        height: '120px',
                                                        borderRadius: '10px',
                                                        fontSize: '1.3rem',
                                                        fontWeight: '500'
                                                    }}
                                                    onClick={handlePayment}
                                                >
                                                    결제
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    )}
                </main>
            </div>
            <Footer />
            <PaymentConfirmation
                show={showModal}
                handleClose={handleCloseModal}
                handleConfirm={handleConfirmPayment}
                email={email}
                address={address}
                totalItems={totalItems}
                totalPrice={totalPrice}
            />
        </div>
    );
};

export default Payment;

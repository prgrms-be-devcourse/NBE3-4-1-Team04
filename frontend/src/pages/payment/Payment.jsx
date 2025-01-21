import React from 'react';
import Header from '../../components/header/Header';
import Navigation from '../../components/navigation/Navigation';
import Footer from '../../components/footer/Footer';

const Payment = () => {
    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <div className="container" style={{ marginTop: '-10px' }}>
                    <div className="row">
                        <div className="col-md-8">
                            <div className="payment-form">
                                {/* 결제 폼 내용 */}
                            </div>
                        </div>
                        <div className="col-md-4">
                            {/* 주문 요약 정보 */}
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Payment; 
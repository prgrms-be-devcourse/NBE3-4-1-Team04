import React, { useState } from 'react';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import Navigation from "../../components/navigation/Navigation";
import ProductList from "./ProductList";
import TotalPrice from "./TotalPrice";
import PaymentButton from "./PaymentButton";
import PaymentConfirmation from "./modal/PaymentConfirmation";

const Payment = () => {
    const [totalItems, setTotalItems] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0);
    const [showModal, setShowModal] = useState(false);
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');

    const handlePayment = () => {
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    const handleConfirmPayment = () => {
        setShowModal(false);
        alert('결제가 완료되었습니다.');
    };

    return (
        <div>
            <Navigation />
            <Header />
            <main>
                <section className="py-5">
                    <div className="container px-4 px-lg-5 mt-5">
                        <div className="container my-5 picked-up-list-container">
                            <h2 className="text-center mb-4">Picked-Up List</h2>
                            <div className="row g-4 justify-content-center">
                                <ProductList setTotalItems={setTotalItems} setTotalPrice={setTotalPrice} />
                            </div>
                            <div className="mt-4">
                                <TotalPrice totalItems={totalItems} totalPrice={totalPrice} />
                            </div>
                            <div className="mt-4">
                                <PaymentButton setEmail={setEmail}
                                               setAddress={setAddress}
                                               totalItems={totalItems}
                                               handlePayment={handlePayment} />
                            </div>
                        </div>
                    </div>
                </section>
            </main>
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

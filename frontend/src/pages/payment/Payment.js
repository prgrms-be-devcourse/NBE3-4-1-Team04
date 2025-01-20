import React, { useState } from 'react';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import Navigation from "../../components/navigation/Navigation";
import ProductList from "./ProductList";
import TotalPrice from "./TotalPrice";
import PaymentButton from "./PaymentButton";

const Payment = () => {
    const [totalItems, setTotalItems] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0);

    const handlePayment = () => {
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
                                <PaymentButton />
                            </div>
                        </div>
                    </div>
                </section>
            </main>
            <Footer />
        </div>
    );
};

export default Payment;

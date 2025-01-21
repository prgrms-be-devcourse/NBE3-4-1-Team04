import React, { useState } from 'react';
import ItemsErrorModal from './modal/ItemsErrorModal';

const PaymentButton = ({ setEmail, setAddress, totalItems, handlePayment }) => {
    const [emailError, setEmailError] = useState('');
    const [addressError, setAddressError] = useState('');
    const [itemsError, setItemsError] = useState('');
    const [showItemsErrorModal, setShowItemsErrorModal] = useState(false);

    const validateEmail = (email) => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(String(email).toLowerCase());
    };

    const handleEmailChange = (e) => {
        const email = e.target.value;
        setEmail(email);
    };

    const handleEmailBlur = (e) => {
        const email = e.target.value;
        if (!validateEmail(email)) {
            setEmailError('유효한 이메일 주소를 입력하세요.');
        } else {
            setEmailError('');
        }
    };

    const handleAddressChange = (e) => {
        const address = e.target.value;
        setAddress(address);
    };

    const handleAddressBlur = (e) => {
        const address = e.target.value;
        if (address.trim() === '') {
            setAddressError('주소를 입력하세요.');
        } else {
            setAddressError('');
        }
    };

    const handleClick = () => {
        if (totalItems === 0) {
            setShowItemsErrorModal(true);
        } else {
            setItemsError('');
        }

        const email = document.getElementById('email').value;
        if (!validateEmail(email)) {
            setEmailError('유효한 이메일 주소를 입력하세요.');
        }

        const address = document.getElementById('address').value;
        if (address.trim() === '') {
            setAddressError('주소를 입력하세요.');
        }

        if (!emailError && !addressError && totalItems !== 0) {
            handlePayment();
        }
    };

    return (
        <div className="card border">
            <div className="card-body">
                <h5 className="card-title text-center mb-4">배송 정보</h5>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일 주소</label>
                    <input type="email" className="form-control" id="email"
                           placeholder="ex) Grids&Circles@gmail.com" onChange={handleEmailChange} onBlur={handleEmailBlur} />
                    {emailError && <div className="text-danger">{emailError}</div>}
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">배송지 주소</label>
                    <input type="text" className="form-control" id="address"
                           placeholder="ex) 경상남도 거제시 00동" onChange={handleAddressChange} onBlur={handleAddressBlur} />
                    {addressError && <div className="text-danger">{addressError}</div>}
                </div>
                <div className="d-flex justify-content-end">
                    <button className="btn btn-primary" onClick={handleClick}>결제하기</button>
                </div>
                {itemsError && <div className="text-danger">{itemsError}</div>}
            </div>

            <ItemsErrorModal
                show={showItemsErrorModal}
                handleClose={() => setShowItemsErrorModal(false)}
            />
        </div>
    );
};

export default PaymentButton;
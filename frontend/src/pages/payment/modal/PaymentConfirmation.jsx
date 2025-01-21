import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const PaymentConfirmation = ({ show, handleClose, handleConfirm, email, address, totalItems, totalPrice }) => {
    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>결제 확인</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="text-center mb-4">
                    <h5 className="mb-3">
                        <span className="text-success">{email}</span> 님!
                    </h5>
                    <p className="mb-1">이용해주셔서 감사합니다.</p>
                    <p>아래 상세 내용들을 확인해주세요!</p>
                </div>
                <div className="card" style={{ backgroundColor: '#fdfbf6' }}>
                    <div className="card-body">
                        <div className="mb-3">
                            <div className="d-flex justify-content-between">
                                <span>주문 상품</span>
                                <span>{totalItems}개</span>
                            </div>
                        </div>
                        <div className="mb-3">
                            <div className="d-flex justify-content-between">
                                <span>총 가격</span>
                                <span>₩{totalPrice.toLocaleString()}</span>
                            </div>
                        </div>
                        <div>
                            <div className="d-flex justify-content-between">
                                <span>배송지 주소</span>
                                <span>{address}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    이전
                </Button>
                <Button
                    variant="success"
                    onClick={handleConfirm}
                    style={{ backgroundColor: '#5C8A3C' }}
                >
                    결제 확정
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default PaymentConfirmation;
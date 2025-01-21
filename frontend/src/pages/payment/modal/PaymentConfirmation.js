import React from 'react';
import { Modal, Button } from 'react-bootstrap';

const PaymentConfirmation = ({ show, handleClose, handleConfirm, email, address, product1, orderNumber }) => {
    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton className="modal-header">
                <Modal.Title className="modal-title">결제 확인</Modal.Title>
            </Modal.Header>
            <Modal.Body className="modal-body">
                <p className="text-center"><span className="text-success">'{email}'</span> 님!</p>
                <p className="text-center">이용해주셔서 감사합니다.</p>
                <p className="text-center">아래 상세 내용들을 확인해주세요!</p>
                <div className="card border-success">
                    <div className="card-body">
                        <p className="card-text">주문 상품: {product1}</p>
                        <p className="card-text">배송지 주소: {address}</p>
                        <p className="card-text">주문 번호 (주문 확인 시 필수): {orderNumber}</p>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer className="modal-footer">
                <Button variant="secondary" onClick={handleClose}>
                    이전
                </Button>
                <Button variant="primary" onClick={handleConfirm}>
                    결제 확정
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default PaymentConfirmation;

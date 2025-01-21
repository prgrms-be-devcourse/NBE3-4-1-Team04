import React from 'react';
import { Modal, Button } from 'react-bootstrap';

const ItemsErrorModal = ({ show, handleClose }) => {
    return (
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>❗</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className="text-center">
                    <i className="fas fa-exclamation-triangle text-warning mb-3" style={{ fontSize: '2rem' }}></i>
                    <p>선택된 상품이 없어서 결제를 할 수 없어요!</p>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    상품 등록하러 가기
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ItemsErrorModal;
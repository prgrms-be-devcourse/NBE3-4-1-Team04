import React, { useState, useEffect } from 'react';
import './OrderHistory.css';

const OrderHistory = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedOrder, setSelectedOrder] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetch('/api/admin/orders'); // 주문 내역 API
                const data = await response.json();
                setOrders(data);
            } catch (error) {
                console.error('주문 내역 로딩 실패:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchOrders();
    }, []);

    const handleDeleteOrder = async (orderId) => {
        try {
            await fetch(`/api/admin/orders/${orderId}`, { method: 'DELETE' });
            setOrders(orders.filter((order) => order.id !== orderId));
            alert('주문이 삭제되었습니다.');
        } catch (error) {
            console.error('주문 삭제 중 오류 발생:', error);
        }
    };

    const handleUpdateOrder = async (e) => {
        e.preventDefault();
        try {
            await fetch(`/api/admin/orders/${selectedOrder.id}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(selectedOrder),
            });
            alert('주문이 수정되었습니다.');
            setSelectedOrder(null);
            window.location.reload();
        } catch (error) {
            console.error('주문 수정 중 오류 발생:', error);
        }
    };

    return (
        <div className="order-history-container">
            <h1 className="page-title">주문 내역</h1>
            {loading ? (
                <div className="loading">로딩 중...</div>
            ) : (
                <div className="order-list">
                    {orders.map((order) => (
                        <div key={order.id} className="order-card">
                            <div className="order-details">
                                <p><strong>이메일:</strong> {order.email}</p>
                                <p><strong>주소:</strong> {order.address}</p>
                                <p><strong>우편번호:</strong> {order.zipcode}</p>
                                <p><strong>주문번호:</strong> {order.orderNumber}</p>
                                <p><strong>상품:</strong> {order.items.map((item) => item.name).join(', ')}</p>
                                <p><strong>수량:</strong> {order.items.map((item) => item.quantity).join(', ')}</p>
                                <p><strong>금액:</strong> ₩{order.totalPrice.toLocaleString()}</p>
                            </div>
                            <div className="actions">
                                <button className="edit-button" onClick={() => setSelectedOrder(order)}>수정</button>
                                <button className="delete-button" onClick={() => handleDeleteOrder(order.id)}>삭제</button>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {selectedOrder && (
                <form className="edit-order-form" onSubmit={handleUpdateOrder}>
                    <h2>주문 수정</h2>
                    <input
                        type="text"
                        value={selectedOrder.address}
                        onChange={(e) => setSelectedOrder({ ...selectedOrder, address: e.target.value })}
                    />
                    <input
                        type="text"
                        value={selectedOrder.zipcode}
                        onChange={(e) => setSelectedOrder({ ...selectedOrder, zipcode: e.target.value })}
                    />
                    <button type="submit">저장</button>
                    <button type="button" onClick={() => setSelectedOrder(null)}>취소</button>
                </form>
            )}
        </div>
    );
};

export default OrderHistory;

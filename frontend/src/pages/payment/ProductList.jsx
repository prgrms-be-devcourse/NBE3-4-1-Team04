import React, { useEffect } from 'react';
import useLocalStorage from '../../components/localstorage/LocalStorage';

const ProductList = ({ initialItems, setTotalItems, setTotalPrice }) => {
    const [items, setItems] = useLocalStorage('orderItems', initialItems);

    useEffect(() => {
        const totalQty = items.reduce((sum, item) => sum + item.quantity, 0);
        const totalAmount = items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        setTotalItems(totalQty);
        setTotalPrice(totalAmount);
    }, [items, setTotalItems, setTotalPrice]);

    const handleRemoveItem = (id) => {
        if (window.confirm('선택하신 상품을 취소하시겠습니까?')) {
            setItems((prevItems) =>
                prevItems.reduce((result, item) => {
                    if (item.id === id) {
                        if (item.quantity > 1) {
                            result.push({ ...item, quantity: item.quantity - 1 });
                        }
                    } else {
                        result.push(item);
                    }
                    return result;
                }, [])
            );
        }
    };

    return (
        <div>
            {items.map((item) => (
                <div key={item.id} className="d-flex align-items-center py-3" style={{
                    borderBottom: '1px solid #dee2e6'
                }}>
                    <div className="me-3">
                        <img
                            src={item.imageUrl || `/images/coffee-${item.id}.png`}
                            alt={item.name}
                            style={{
                                width: '100px',
                                height: '100px',
                                objectFit: 'cover'
                            }}
                            onError={(e) => {
                                e.target.onerror = null;
                                e.target.src = '/images/coffee-bag.png';
                            }}
                        />
                    </div>
                    <div className="flex-grow-1">
                        <h5 className="mb-2" style={{ fontSize: '1.3rem' }}>{item.name}</h5>
                        <p className="mb-0 text-success" style={{ fontSize: '1.1rem' }}>
                            ₩{(item.price / item.quantity).toLocaleString()} / 1ea
                        </p>
                    </div>
                    <div className="d-flex align-items-center" style={{ minWidth: '350px' }}>
                        <div className="ms-auto d-flex align-items-center">
                            <span className="me-7" style={{
                                minWidth: '60px',
                                textAlign: 'center',
                                fontSize: '1.2rem'
                            }}>
                                {item.quantity}개
                            </span>
                            <span className="me-5 fw-bold" style={{
                                minWidth: '100px',
                                textAlign: 'right',
                                fontSize: '1.2rem'
                            }}>
                                ₩{(item.price).toLocaleString()}
                            </span>
                            <button
                                className="btn btn-sm btn-danger"
                                style={{ minWidth: '60px' }}
                                onClick={() => handleRemoveItem(item.id)}
                            >
                                취소
                            </button>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ProductList;
import React, { useState, useEffect } from 'react';
import './Home.css';
import Navigation from '../../components/navigation/Navigation';
import Header from '../../components/header/Header';
import { productService } from '../../services/productService';
import { useNavigate } from 'react-router-dom';
import Footer from '../../components/footer/Footer';

const Home = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [orderItems, setOrderItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const data = await productService.getAllProducts();
                setProducts(data);
            } catch (error) {
                console.error('상품 목록 로딩 실패:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchProducts();

        // Local Storage에서 주문 아이템 불러오기
        const savedOrderItems = JSON.parse(localStorage.getItem('orderItems'));
        if (savedOrderItems) {
            setOrderItems(savedOrderItems);
        }
    }, []);

    const saveToLocalStorage = (items) => {
        localStorage.setItem('orderItems', JSON.stringify(items));
    };

    const addToOrder = (product) => {
        setOrderItems(prev => {
            const existingItem = prev.find(item => item.id === product.id);
            let newOrderItems;
            if (existingItem) {
                newOrderItems = prev.map(item =>
                    item.id === product.id
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                newOrderItems = [...prev, { ...product, quantity: 1 }];
            }
            saveToLocalStorage(newOrderItems);
            return newOrderItems;
        });
    };

    const removeFromOrder = (productId) => {
        setOrderItems(prev => {
            const existingItem = prev.find(item => item.id === productId);
            let newOrderItems;
            if (existingItem.quantity > 1) {
                newOrderItems = prev.map(item =>
                    item.id === productId
                        ? { ...item, quantity: item.quantity - 1 }
                        : item
                );
            } else {
                newOrderItems = prev.filter(item => item.id !== productId);
            }
            saveToLocalStorage(newOrderItems);
            return newOrderItems;
        });
    };

    const calculateTotal = () => {
        return orderItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    };

    const handlePayment = () => {
        if (orderItems.length === 0) {
            alert('주문할 상품을 선택해주세요.');
            return;
        }
        // 주문 정보를 state로 전달
        navigate('/payment', { 
            state: { 
                orderItems,
                totalAmount: calculateTotal()
            } 
        });
    };

    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <div className="container" style={{ marginTop: '-10px' }}>
                    <div className="row">
                        <div className="col-md-8">
                            {loading ? (
                                <div className="text-center p-5">
                                    <div className="spinner-border text-success" role="status">
                                        <span className="visually-hidden">Loading...</span>
                                    </div>
                                </div>
                            ) : (
                                <div className="product-list">
                                    {products.map((product) => (
                                        <div key={product.id} className="product-card">
                                            <div className="product-content">
                                                <div className="left-section">
                                                    <div className="product-image">
                                                        <img
                                                            src={product.imageUrl}
                                                            alt={product.name}
                                                            onError={(e) => {
                                                                e.target.onerror = null;
                                                                e.target.src = '/images/coffee-bag.png';  // 기본 이미지
                                                            }}
                                                        />
                                                    </div>
                                                    <div className="product-details">
                                                        <div className="product-name">{product.name}</div>
                                                        <div className="price-per-unit">₩{product.pricePerGram.toLocaleString()} / 100g</div>
                                                    </div>
                                                </div>
                                                <div className="right-section">
                                                    <div className="price">₩{product.price.toLocaleString()}</div>
                                                    <button 
                                                        className={product.stock > 0 ? 'add-button' : 'soldout-button'}
                                                        disabled={product.stock === 0}
                                                        onClick={() => addToOrder(product)}
                                                    >
                                                        {product.stock > 0 ? '추가' : 'SoldOut'}
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                        <div className="col-md-4">
                            <div className="card" style={{
                                position: 'sticky',
                                top: '80px',
                                zIndex: 100,
                                marginTop: '20px'
                            }}>
                                <div className="card-header">
                                    <h5 className="mb-0">Order Summary</h5>
                                </div>
                                <div className="card-body" style={{ maxHeight: 'calc(100vh - 300px)', overflowY: 'auto' }}>
                                    {orderItems.map(item => (
                                        <div key={item.id} className="d-flex justify-content-between align-items-center mb-2">
                                            <span>{item.name}</span>
                                            <div className="d-flex align-items-center">
                                                <span className="me-3">{item.quantity}개</span>
                                                <button 
                                                    className="btn btn-outline-danger btn-sm"
                                                    onClick={() => removeFromOrder(item.id)}
                                                >
                                                    취소
                                                </button>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            <div className="total-section">
                                <div className="total-row">
                                    <span>총액(VAT포함)</span>
                                    <span className="total-amount">₩{calculateTotal().toLocaleString()}</span>
                                </div>
                            </div>
                            <div className="delivery-notice">
                                • 당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
                            </div>
                            <div className="action-buttons">
                                <button 
                                    className="payment-button" 
                                    onClick={handlePayment}
                                    disabled={orderItems.length === 0}
                                >
                                    결제창으로 이동하기
                                </button>
                                <button className="history-button" onClick={() => navigate('/orders')}>
                                    주문내역 확인하기
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Home;

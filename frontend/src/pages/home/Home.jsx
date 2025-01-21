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
    }, []);

    const addToOrder = (product) => {
        setOrderItems(prev => {
            const existingItem = prev.find(item => item.id === product.id);
            if (existingItem) {
                return prev.map(item => 
                    item.id === product.id 
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            }
            return [...prev, { ...product, quantity: 1 }];
        });
    };

    const removeFromOrder = (productId) => {
        setOrderItems(prev => {
            const existingItem = prev.find(item => item.id === productId);
            if (existingItem.quantity > 1) {
                // 수량이 1보다 크면 하나만 차감
                return prev.map(item => 
                    item.id === productId 
                        ? { ...item, quantity: item.quantity - 1 }
                        : item
                );
            } else {
                // 수량이 1이면 완전히 제거
                return prev.filter(item => item.id !== productId);
            }
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
                            <div className="order-summary">
                                <h5>Order summary</h5>
                                {orderItems.map((item) => (
                                    <div key={item.id} className="order-item">
                                        <div className="order-item-content">
                                            <div className="item-info">
                                                <span className="item-name">{item.name}</span>
                                                <span className="item-quantity">{item.quantity}ea</span>
                                            </div>
                                            <button 
                                                className="cancel-button"
                                                onClick={() => removeFromOrder(item.id)}
                                            >
                                                취소
                                            </button>
                                        </div>
                                    </div>
                                ))}
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
            </div>
            <Footer />
        </div>
    );
};

export default Home;
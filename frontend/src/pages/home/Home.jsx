import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../../components/header/Header.jsx';
import Navigation from '../../components/navigation/Navigation.jsx';
import Footer from '../../components/footer/Footer.jsx';
import { productService } from '../../services/productService';
import './Home.css';

const Home = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [orderItems, setOrderItems] = useState([]);
    const navigate = useNavigate();

const navigateToAdmin = () => {
        navigate('/administer'); // 관리자 페이지로 이동
    };

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                // 임시 데이터로 Columbia Quindio를 품절 상태로 설정
                const data = await productService.getAllProducts();
                const updatedData = data.map(product => ({
                    ...product,
                    stock: product.name === "Columbia Quindio (400G)" ? 0 : product.stock
                }));
                setProducts(updatedData);
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

        const selectedItems = orderItems.map(item => ({
            id: item.id,
            name: item.name,
            price: item.price,
            quantity: item.quantity,
            imageUrl: item.imageUrl
        }));

        console.log('전달되는 상품 데이터:', selectedItems); // 데이터 확인용

        navigate('/payment', {
            state: { selectedItems }
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
                                                        style={product.stock === 0 ? {
                                                            backgroundColor: '#000',
                                                            color: '#fff',
                                                            border: 'none',
                                                            cursor: 'not-allowed'
                                                        } : {}}
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
                                                    className="btn btn-sm"
                                                    style={{
                                                        backgroundColor: '#E86056',
                                                        color: 'white',
                                                        border: 'none',
                                                        padding: '2px 8px'
                                                    }}
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
                            <div className="action-buttons" style={{ marginTop: '20px' }}>
                                <button
                                    className="btn w-100 mb-2"
                                    style={{
                                        backgroundColor: '#5C8A3C',
                                        color: 'white',
                                        border: 'none'
                                    }}
                                    onClick={handlePayment}
                                >
                                    결제창으로 이동하기
                                </button>
                                <button
                                    className="btn w-100"
                                    style={{
                                        backgroundColor: '#5C8A3C',
                                        color: 'white',
                                        border: 'none'
                                    }}
                                    onClick={() => navigate('/orders')}
                                >
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
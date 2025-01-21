
import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Header from '../../components/header/Header';
import Navigation from '../../components/navigation/Navigation';
import Footer from '../../components/footer/Footer';
import { productService } from '../../services/productService';

const OrderList = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [orders, setOrders] = useState([]);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const productsData = await productService.getAllProducts();
                setProducts(productsData);
            } catch (error) {
                console.error('상품 정보 로딩 실패:', error);
            }
        };
        fetchProducts();
    }, []);

    useEffect(() => {
        const fetchOrders = async () => {
            if (!location.state) {
                navigate('/orders');
                return;
            }

            const { searchType, searchValue } = location.state;
            
            try {
                // TODO: API 호출로 변경
                // const response = await orderService.getOrders(searchType, searchValue);
                // setOrders(response.data);
                
                // 임시 데이터
                const tempOrders = [
                    {
                        orderId: "250114123456",
                        orderDate: "2024-01-14",
                        email: "test@example.com",
                        items: [
                            { name: "Columbia Quindio (400G)", quantity: 2, price: 36000 },
                            { name: "Ethiopia Sidamo (400G)", quantity: 1, price: 22000 }
                        ],
                        totalAmount: 58000,
                        status: "출고완료"  // "출고완료", "출고전", "취소" 중 하나로 표시
                    },
                    {
                        orderId: "250121111111",
                        orderDate: "2024-01-21",
                        email: "test@example.com",
                        items: [
                            { name: "Brazil Serra Do Caparao (400G)", quantity: 2, price: 32000 },
                            { name: "Columbia Narino (400G)", quantity: 1, price: 20000 }
                        ],
                        totalAmount: 52000,
                        status: "출고전"
                    }
                ];

                // 검색 타입에 따라 다른 결과 반환
                if (searchType === 'orderNumber') {
                    // 주문번호로 검색 시 해당 주문만 반환
                    const filteredOrders = tempOrders.filter(order => 
                        order.orderId === searchValue
                    );
                    setOrders(filteredOrders);
                } else {
                    // 이메일로 검색 시 해당 이메일의 모든 주문 반환
                    const filteredOrders = tempOrders.filter(order => 
                        order.email === searchValue
                    );
                    setOrders(filteredOrders);
                }
            } catch (error) {
                console.error('주문 조회 실패:', error);
                alert('주문 정보를 불러오는데 실패했습니다.');
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, [location.state, navigate]);

    const getProductImageUrl = (productName) => {
        const product = products.find(p => p.name === productName);
        if (product) {
            console.log(`Found product: ${productName}, Image URL: ${product.imageUrl}`);  // 디버깅용
            return product.imageUrl;
        }
        console.log(`Product not found: ${productName}`);  // 디버깅용
        return '/images/coffee-bag.png';
    };

    // 주문 아이템 취소 처리 함수
    const handleCancelItem = (orderId, itemIndex) => {
        setOrders(prevOrders => {
            return prevOrders.map(order => {
                if (order.orderId !== orderId) return order;

                const updatedItems = [...order.items];
                const item = updatedItems[itemIndex];

                if (item.quantity > 1) {
                    // 수량이 1보다 크면 하나 감소
                    updatedItems[itemIndex] = {
                        ...item,
                        quantity: item.quantity - 1,
                        price: (item.price / item.quantity) * (item.quantity - 1)
                    };
                } else {
                    // 수량이 1이면 아이템 제거
                    updatedItems.splice(itemIndex, 1);
                }

                // 총 금액 재계산
                const newTotalAmount = updatedItems.reduce((sum, item) => sum + item.price, 0);

                // 모든 아이템이 취소되었다면 주문 상태를 '취소'로 변경
                const newStatus = updatedItems.length === 0 ? '취소' : order.status;

                return {
                    ...order,
                    items: updatedItems,
                    totalAmount: newTotalAmount,
                    status: newStatus
                };
            });
        });
    };

    // 취소 버튼 클릭 시 확인 메시지 표시
    const confirmCancel = (orderId, itemIndex, itemName) => {
        if (window.confirm(`${itemName} 상품을 취소하시겠습니까?`)) {
            handleCancelItem(orderId, itemIndex);
        }
    };

    // 전체 주문 취소 처리 함수 추가
    const handleCancelOrder = (orderId) => {
        setOrders(prevOrders => {
            return prevOrders.map(order => {
                if (order.orderId !== orderId) return order;
                
                return {
                    ...order,
                    items: [],
                    totalAmount: 0,
                    status: '취소'
                };
            });
        });
    };

    // 전체 주문 취소 확인 함수 추가
    const confirmCancelOrder = (orderId) => {
        if (window.confirm('전체 주문을 취소하시겠습니까?')) {
            handleCancelOrder(orderId);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="app-container">
            <div className="content-wrapper">
                <Header />
                <Navigation />
                <main className="container mt-5 mb-5">
                    {orders.map((order) => (
                        <div key={order.orderId} className="card mb-4" style={{ backgroundColor: '#fdfbf6' }}>
                            <div className="card-body">
                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    <h5 className="mb-0" style={{ 
                                        color: '#5C8A3C', 
                                        fontWeight: 'bold',
                                        fontSize: '1.3rem'
                                    }}>
                                        주문번호 : {order.orderId}
                                    </h5>
                                    <div className="text-end">
                                        <span style={{
                                            color: '#000000',
                                            padding: '8px 20px',
                                            fontSize: '1.1rem',
                                            fontWeight: 'bold'
                                        }}>
                                            {order.status}
                                        </span>
                                    </div>
                                </div>

                                {order.items.map((item, index) => (
                                    <div key={index} className="d-flex align-items-center py-3" style={{ 
                                        borderBottom: index !== order.items.length - 1 ? '1px solid #dee2e6' : 'none' 
                                    }}>
                                        <div className="me-3">
                                            <img 
                                                src={getProductImageUrl(item.name)}
                                                alt={item.name} 
                                                style={{ width: '100px', height: '100px', objectFit: 'cover' }}
                                                onError={(e) => {
                                                    e.target.onerror = null;
                                                    e.target.src = '/images/coffee-bag.png';
                                                }}
                                            />
                                        </div>
                                        <div className="flex-grow-1">
                                            <h5 className="mb-2" style={{ fontSize: '1.3rem' }}>{item.name}</h5>
                                            <p className="mb-0 text-success" style={{ fontSize: '1.1rem' }}>₩{(item.price / item.quantity).toLocaleString()} / 1ea</p>
                                        </div>
                                        <div className="d-flex align-items-center" style={{ minWidth: '350px' }}>
                                            <div className="ms-auto d-flex align-items-center">
                                                <span className="me-7" style={{ minWidth: '60px', textAlign: 'center', fontSize: '1.2rem' }}>{item.quantity}개</span>
                                                <span className="me-5 fw-bold" style={{ minWidth: '100px', textAlign: 'right', fontSize: '1.2rem' }}>₩{item.price.toLocaleString()}</span>
                                                <button 
                                                    className="btn btn-sm btn-danger" 
                                                    style={{ minWidth: '60px' }}
                                                    onClick={() => confirmCancel(order.orderId, index, item.name)}
                                                    disabled={order.status === '취소'}  // 이미 취소된 주문은 비활성화
                                                >
                                                    취소
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                ))}

                                <div className="mt-4">
                                    <div className="d-flex justify-content-between align-items-center">
                                        <h4 className="mb-0 fw-bold">총계<span className="text-muted">(VAT포함)</span></h4>
                                        <div className="d-flex align-items-center">
                                            <h4 className="mb-0 me-4" style={{ minWidth: '20px', textAlign: 'center' }}>{order.items.reduce((acc, item) => acc + item.quantity, 0)}개</h4>
                                            <h3 className="mb-0 fw-bold" style={{ minWidth: '270px', textAlign: 'center' }}>₩{order.totalAmount.toLocaleString()}</h3>
                                        </div>
                                    </div>
                                </div>

                                <div className="mt-4">
                                    <div className="card bg-light" style={{ backgroundColor: '#fff' }}>  {/* Order Information 카드 배경색 흰색으로 설정 */}
                                        <div className="card-body">
                                            <div className="row">
                                                <div className="col-md-3">
                                                    <div style={{ 
                                                        color: '#5C8A3C',
                                                        fontSize: '3rem',
                                                        fontWeight: 'bold',
                                                        lineHeight: '1.2',
                                                        position: 'relative',
                                                        marginTop: '20px',
                                                        textAlign: 'center'
                                                    }}>
                                                        <div>Order</div>
                                                        <div>Information</div>
                                                    </div>
                                                </div>
                                                <div className="col-md-6" style={{ paddingLeft: '50px' }}>
                                                    <table className="table table-sm table-borderless mb-0">
                                                        <tbody>
                                                            <tr>
                                                                <td style={{ 
                                                                    width: '300px', 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: '600',
                                                                    paddingLeft: '50px'
                                                                }}>이메일 주소</td>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: 'bold'
                                                                }}>{order.email}</td>
                                                            </tr>
                                                            <tr>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: '600',
                                                                    paddingLeft: '50px'
                                                                }}>배송지 주소</td>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: 'bold'
                                                                }}>경상남도 거제시 00동</td>
                                                            </tr>
                                                            <tr>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: '600',
                                                                    paddingLeft: '50px'
                                                                }}>주문번호</td>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: 'bold'
                                                                }}>{order.orderId}</td>
                                                            </tr>
                                                            <tr>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: '600',
                                                                    paddingLeft: '50px'
                                                                }}>주문시간</td>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: 'bold'
                                                                }}>2025-01-14 14:05:15</td>
                                                            </tr>
                                                            <tr>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: '600',
                                                                    paddingLeft: '50px'
                                                                }}>배송예정일자</td>
                                                                <td style={{ 
                                                                    fontSize: '1.2rem', 
                                                                    fontWeight: 'bold'
                                                                }}>2025-01-15 14:00:00</td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div className="col-md-3 d-flex align-items-center justify-content-end">
                                                    <button 
                                                        className="btn btn-success" 
                                                        style={{
                                                            backgroundColor: '#5C8A3C',
                                                            padding: '60px 25px',
                                                            borderRadius: '8px',
                                                            fontSize: '1.1rem',
                                                            whiteSpace: 'nowrap'
                                                        }}
                                                        onClick={() => confirmCancelOrder(order.orderId)}
                                                        disabled={order.status === '취소' || order.items.length === 0}  // 이미 취소되었거나 아이템이 없는 경우 비활성화
                                                    >
                                                        전체주문취소
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                    <div className="mt-1 mb-2">
                        <p className="text-danger" style={{ fontSize: '16px', fontWeight: 'bold'}}>
                            • '주문취소'는 배송예정일자 오전 12시까지 가능합니다.
                        </p>
                    </div>
                </main>
            </div>
            <Footer />
        </div>
    );
};

export default OrderList; 
import React, { useState, useEffect } from 'react';
import Navigation from '../../components/navigation/Navigation';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import { productService } from '../../services/productService';

const Products = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await productService.getAllProducts();
            setProducts(data);
        } catch (err) {
            console.error('Error in Products component:', err);
            setError(err.message || '알 수 없는 오류가 발생했습니다.');
        } finally {
            setLoading(false);
        }
    };

    if (loading) return (
        <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
            <div className="spinner-border text-success" role="status">
                <span className="visually-hidden">로딩중...</span>
            </div>
        </div>
    );

    if (error) return (
        <div className="container mt-5">
            <div className="alert alert-danger" role="alert">
                <h4 className="alert-heading">오류가 발생했습니다</h4>
                <p>{error}</p>
                <hr />
                <p className="mb-0">
                    <button 
                        className="btn btn-outline-danger" 
                        onClick={() => {
                            setError(null);
                            fetchProducts();
                        }}
                    >
                        다시 시도
                    </button>
                </p>
            </div>
        </div>
    );

    return (
        <div>
            <Navigation />
            <Header />
            <main>
                <div className="container mt-5">
                    <h2 className="mb-4">상품목록</h2>
                    <div className="row">
                        {products.map((product) => (
                            <div key={product.id} className="col-12 mb-4">
                                <div className="card">
                                    <div className="row g-0">
                                        <div className="col-2">
                                            <img 
                                                src={product.imageUrl || '/coffee-default.jpg'} 
                                                alt={product.name}
                                                className="img-fluid rounded-start"
                                                style={{ width: '150px', height: '150px', objectFit: 'cover' }}
                                            />
                                        </div>
                                        <div className="col-8 d-flex align-items-center">
                                            <div className="card-body">
                                                <h5 className="card-title">{product.name}</h5>
                                                <p className="card-text text-success">₩{product.pricePerGram} / 100g</p>
                                                <p className="card-text">
                                                    <small className="text-muted">1EA</small>
                                                </p>
                                            </div>
                                        </div>
                                        <div className="col-2 d-flex align-items-center justify-content-center">
                                            <div className="text-end">
                                                <h5 className="mb-3">₩{product.price}</h5>
                                                <button 
                                                    className={`btn ${product.stock > 0 ? 'btn-success' : 'btn-secondary'}`}
                                                    disabled={product.stock === 0}
                                                >
                                                    {product.stock > 0 ? '추가' : 'SoldOut'}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </main>
            <Footer />
        </div>
    );
};

export default Products; 
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ManageProducts.css';

const ManageProducts = () => {
    const [products, setProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null); // 수정할 상품 선택

    const [newProduct, setNewProduct] = useState({
        category: '',
        itemName: '',
        itemPrice: '',
        quantity: '',
        itemDescription: '',
    });

    // 상품 데이터 가져오기
    useEffect(() => {
        axios.get('/api/admin/items') // API 경로
            .then((response) => {
                setProducts(response.data);
            })
            .catch((error) => {
                console.error('상품 데이터를 가져오는 중 오류 발생:', error);
            });
    }, []);

    // 신규 상품 등록
    const handleAddProduct = (e) => {
        e.preventDefault();
        axios.post('/api/admin/items', newProduct)
            .then(() => {
                alert('신규 상품이 등록되었습니다.');
                setNewProduct({
                    category: '',
                    itemName: '',
                    itemPrice: '',
                    quantity: '',
                    itemDescription: '',
                });
                window.location.reload();
            })
            .catch((error) => {
                console.error('상품 등록 중 오류 발생:', error);
            });
    };

    // 상품 삭제
    const handleDeleteProduct = (id) => {
        axios.delete(`/api/admin/items/${id}`)
            .then(() => {
                alert('상품이 삭제되었습니다.');
                setProducts(products.filter((product) => product.id !== id));
            })
            .catch((error) => {
                console.error('상품 삭제 중 오류 발생:', error);
            });
    };

    // 상품 수정
    const handleUpdateProduct = (e) => {
        e.preventDefault();
        axios.patch(`/api/admin/items/${selectedProduct.id}`, selectedProduct)
            .then(() => {
                alert('상품 정보가 성공적으로 수정되었습니다.');
                setSelectedProduct(null); // 수정 모드 종료
                window.location.reload();
            })
            .catch((error) => {
                console.error('상품 수정 중 오류 발생:', error);
            });
    };

    return (
        <div className="manage-products-container">
            <h1 className="page-title">상품 관리</h1>

            {/* 신규 상품 등록 */}
            <form className="add-product-form" onSubmit={handleAddProduct}>
                <h2>신규 상품 등록</h2>
                <input
                    type="text"
                    placeholder="카테고리"
                    value={newProduct.category}
                    onChange={(e) => setNewProduct({ ...newProduct, category: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="상품 이름"
                    value={newProduct.itemName}
                    onChange={(e) => setNewProduct({ ...newProduct, itemName: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="가격"
                    value={newProduct.itemPrice}
                    onChange={(e) => setNewProduct({ ...newProduct, itemPrice: e.target.value })}
                />
                <input
                    type="number"
                    placeholder="수량"
                    value={newProduct.quantity}
                    onChange={(e) => setNewProduct({ ...newProduct, quantity: e.target.value })}
                />
                <textarea
                    placeholder="상품 설명"
                    value={newProduct.itemDescription}
                    onChange={(e) => setNewProduct({ ...newProduct, itemDescription: e.target.value })}
                />
                <button type="submit">등록</button>
            </form>

            {/* 상품 목록 */}
            <h2 className="section-title">등록된 상품 목록</h2>
            <div className="product-list">
                {products.map((product) => (
                    <div key={product.id} className="product-item">
                        <img src={product.itemImage || '/default-image.png'} alt={product.itemName} />
                        <div>
                            <h3>{product.itemName}</h3>
                            <p>{product.itemDescription}</p>
                            <p>₩{product.itemPrice} / {product.quantity}개</p>
                        </div>
                        <div className="actions">
                            <button onClick={() => setSelectedProduct(product)}>수정</button>
                            <button onClick={() => handleDeleteProduct(product.id)}>삭제</button>
                        </div>
                    </div>
                ))}
            </div>

            {/* 상품 수정 폼 */}
            {selectedProduct && (
                <form className="edit-product-form" onSubmit={handleUpdateProduct}>
                    <h2>상품 정보 수정</h2>
                    <input
                        type="text"
                        placeholder="상품 이름"
                        value={selectedProduct.itemName}
                        onChange={(e) => setSelectedProduct({ ...selectedProduct, itemName: e.target.value })}
                    />
                    <input
                        type="number"
                        placeholder="가격"
                        value={selectedProduct.itemPrice}
                        onChange={(e) => setSelectedProduct({ ...selectedProduct, itemPrice: e.target.value })}
                    />
                    <input
                        type="number"
                        placeholder="수량"
                        value={selectedProduct.quantity}
                        onChange={(e) => setSelectedProduct({ ...selectedProduct, quantity: e.target.value })}
                    />
                    <textarea
                        placeholder="상품 설명"
                        value={selectedProduct.itemDescription}
                        onChange={(e) => setSelectedProduct({ ...selectedProduct, itemDescription: e.target.value })}
                    />
                    <button type="submit">저장</button>
                    <button type="button" onClick={() => setSelectedProduct(null)}>취소</button>
                </form>
            )}
        </div>
    );
};

export default ManageProducts;

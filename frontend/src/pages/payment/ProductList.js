import React, { useState } from 'react';

const ProductList = ({ setTotalItems, setTotalPrice }) => {
    const [quantity, setQuantity] = useState(1);
    const price = 40;

    const handleQuantityChange = (event) => {
        const newQuantity = parseInt(event.target.value, 10);
        setQuantity(newQuantity);
        setTotalItems(newQuantity);
        setTotalPrice(newQuantity * price);
    };

    const handleCancel = () => {
        alert('주문이 취소되었습니다.');
        setQuantity(0);
        setTotalItems(0);
        setTotalPrice(0);
    };

    return (
        <div className="col mb-5">
            <div className="card h-100 shadow-sm">
                <div className="row g-0">
                    {/* Product image */}
                    <div className="col-md-1 p-3">
                        <img
                            className="img-fluid rounded-start"
                            src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg"
                            alt="Product"
                            style={{ maxWidth: '100px', height: 'auto' }}
                        />
                    </div>
                    {/* Product details */}
                    <div className="col-md-6 p-3">
                        <div className="card-body">
                            <h5 className="card-title">Fancy Product</h5>
                        </div>
                    </div>
                    {/* Product quantity */}
                    <div className="col-md-2 p-3 text-center d-flex flex-column justify-content-center">
                        <input
                            type="number"
                            id="quantity"
                            className="form-control"
                            value={quantity}
                            onChange={handleQuantityChange}
                            min="1"
                        />
                    </div>
                    {/* Product price */}
                    <div className="col-md-2 p-3 text-center d-flex flex-column justify-content-center">
                        <p className="card-text">${price}</p>
                    </div>
                    {/* Cancel button */}
                    <div className="col-md-1 p-3 text-center d-flex flex-column justify-content-center">
                        <button className="btn btn-outline-danger" onClick={handleCancel}>
                            취소
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProductList;

import React from 'react';

const ProductStyle1 = () => {
    return (<div className="col mb-5">
        <div className="card h-100"> {/* Product image */} <img className="card-img-top"
                                                                src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg"
                                                                alt="Product"/> {/* Product details */}
            <div className="card-body p-4">
                <div className="text-center"> {/* Product name */} <h5 className="fw-bolder">Fancy
                    Product</h5> {/* Product price */} $40.00 - $80.00
                </div>
            </div>
            {/* Product actions */}
            <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div className="text-center"><a className="btn btn-outline-dark mt-auto" href="#">View options</a></div>
            </div>
        </div>
    </div>);
};
export default ProductStyle1;
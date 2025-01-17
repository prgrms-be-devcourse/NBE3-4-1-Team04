import React from 'react';
import Header from '../../components/header/Header';
import Footer from '../../components/footer/Footer';
import Navigation from "../../components/navigation/Navigation";
import ProductStyle1 from "../../components/product/ProductStyle1";
import ProductStyle2 from "../../components/product/ProductStyle2";
import ProductStyle3 from "../../components/product/ProductStyle3";
import ProductStyle4 from "../../components/product/ProductStyle4";
import ProductStyle5 from "../../components/product/ProductStyle5";
import ProductStyle6 from "../../components/product/ProductStyle6";
import ProductStyle7 from "../../components/product/ProductStyle7";
import ProductStyle8 from "../../components/product/ProductStyle8";

const Home = () => {
    return (
        <div>
            <Navigation />
            <Header />
            <main>
                <section className="py-5">
                    <div className="container px-4 px-lg-5 mt-5">
                        <div className="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                            <ProductStyle1 />
                            <ProductStyle2 />
                            <ProductStyle3 />
                            <ProductStyle4 />
                            <ProductStyle5 />
                            <ProductStyle6 />
                            <ProductStyle7 />
                            <ProductStyle8 />
                        </div>
                    </div>
                </section>
            </main>
            <Footer/>
        </div>
);
};

export default Home;
import React from "react";

const Footer = () => {
    const currentYear = new Date().getFullYear(); // 현재 연도 가져오기
    return (
        <footer className="py-5 bg-dark">
        <div className="container"><p className="m-0 text-center text-white">Copyright &copy; Backend First Project {currentYear}</p>
        </div>
    </footer>
    );
};
export default Footer;
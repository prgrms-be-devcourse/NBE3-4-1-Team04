import React from "react";

const Footer = () => {
    const currentYear = new Date().getFullYear();
    return (
        <footer>
            <div className="container">
                <p className="text-center text-white">
                    Copyright &copy; 2025 Programmers Devcourse NBE 3-4-4 {currentYear}
                </p>
            </div>
        </footer>
    );
};

export default Footer;
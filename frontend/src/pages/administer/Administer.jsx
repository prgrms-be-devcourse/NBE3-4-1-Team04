import React, { useState } from 'react';
// import Header from '../../components/header/Header';
// import Navigation from '../../components/navigation/Navigation';
import { useNavigate } from 'react-router-dom';
import './Administer.css';

const Administer = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();

        // 관리자 계정 정보 (하드코딩된 예시)
        const adminEmail = 'team4@admin.com';
        const adminPassword = 'admin_team4';

        // 입력된 값과 관리자 정보 비교
        if (email === adminEmail && password === adminPassword) {
            navigate('/Dashboard'); // 로그인 성공 시 관리자 페이지로 이동
        } else {
            setErrorMessage('잘못된 이메일 또는 비밀번호입니다.');
        }
    };

    return (
        <div className="admin-login-container">
            <h1 className="admin-login-title">관리자 로그인</h1>
            <form className="admin-login-form" onSubmit={handleLogin}>
                <input
                    type="email"
                    placeholder="이메일"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="admin-login-input"
                />
                <input
                    type="password"
                    placeholder="비밀번호"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="admin-login-input"
                />
                {errorMessage && <p className="admin-login-error">{errorMessage}</p>}
                <button type="submit" className="admin-login-button">로그인</button>
            </form>
        </div>
    );
};

export default Administer;
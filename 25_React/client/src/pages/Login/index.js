import React, {useState} from "react";
import './styles.css';
import logoImage from '../../assets/logo.svg'
import padlock from '../../assets/padlock.png'
import { Box } from "@mui/material";
import api from "../../services/api";
import {useNavigate} from 'react-router-dom';

export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const history = useNavigate();

    async function login(e){
        e.preventDefault();
        const data = {
            username,
            password,
        };
        try {
            const response = await api.post('auth/signin', data);
            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.accessToken);

            history('/people');
        } catch (error) {
            alert('Login failed! Try again.');  
            alert(error)          
        }
    }
    return (
        <Box sx={{ml: 2}}>
            <div className="login-container">
            <section className="form">
            <img src={logoImage} alt="Erudio Logo" />
            <form onSubmit={login}>
                <h1>Access your Account</h1>
                <input type="text" placeholder="username"
                value={username} onChange={(e) => {
                    setUsername(e.target.value)
                }} />
                <input type="password" placeholder="password"
                value={password} onChange={(e) => {
                    setPassword(e.target.value)
                }} />
                <button className="button" type="submit">Login</button>
            </form>
            </section>
            <img src={padlock} alt="Login" />
        </div>
        </Box>
    )
}
import axios, { InternalAxiosRequestConfig, AxiosError } from 'axios';

const API = axios.create({
    baseURL: 'http://localhost:9000/api',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Added explicit types for configuration layouts and error captures
API.interceptors.request.use(
    (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
        const token = localStorage.getItem('token');
        if (token && config.headers) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    }, 
    (error: AxiosError) => {
        return Promise.reject(error);
    }
);

export default API;
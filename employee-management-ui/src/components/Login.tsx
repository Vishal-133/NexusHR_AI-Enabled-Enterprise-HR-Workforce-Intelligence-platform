import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API from '../api/client';

const Login: React.FC = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            const response = await API.post('/auth/login', { 
                userOfficialEmail: email, 
                password: password 
            });
            
            // If backend authentication passes and returns a token
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('role', response.data.role || 'EMPLOYEE');
                localStorage.setItem('email', email);
                
                // UPDATED ACCORDINGLY: Catch the new profile properties from your backend DTO response!
                localStorage.setItem('firstName', response.data.firstName || 'User');
                localStorage.setItem('lastName', response.data.lastName || 'Account');
                localStorage.setItem('department', response.data.department || 'General');
                
                navigate('/dashboard');
            }
        } catch (err: any) {
            setError(err.response?.data?.message || 'Invalid Official Email or Security Password.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-indigo-950 to-slate-900 p-4">
            <div className="w-full max-w-md bg-slate-900/80 border border-slate-800 backdrop-blur-xl rounded-2xl p-8 shadow-2xl">
                <div className="text-center mb-8">
                    <div className="inline-flex p-3 bg-indigo-500/10 rounded-xl text-indigo-400 mb-3">
                        <i className="bi bi-shield-lock-fill text-3xl"></i>
                    </div>
                    <h2 className="text-2xl font-bold text-white tracking-tight">NexusHR Platform</h2>
                    <p className="text-slate-400 text-sm mt-1 font-sans">Enterprise Workforce Intelligence Portal</p>
                </div>

                {error && (
                    <div className="mb-4 p-3 bg-red-500/10 border border-red-500/20 rounded-xl text-red-400 text-sm flex items-center gap-2 font-sans">
                        <i className="bi bi-exclamation-triangle-fill"></i>
                        <span>{error}</span>
                    </div>
                )}

                <form onSubmit={handleLogin} className="space-y-5 font-sans">
                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2">Official Email Address</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-envelope"></i></span>
                            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required
                                className="w-full pl-10 pr-4 py-2.5 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="name@company.com" />
                        </div>
                    </div>

                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2">Security Password</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-lock"></i></span>
                            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required
                                className="w-full pl-10 pr-4 py-2.5 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="••••••••" />
                        </div>
                    </div>

                    <button type="submit" disabled={loading}
                        className="w-full py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-medium rounded-xl transition-all shadow-lg active:scale-[0.99] disabled:opacity-50 cursor-pointer">
                        {loading ? 'Verifying Context...' : 'Authenticate Credentials'}
                    </button>
                </form>

                <div className="text-center mt-6 text-sm text-slate-400 font-sans">
                    New to the platform?{' '}
                    <Link to="/register" className="text-indigo-400 hover:text-indigo-300 font-medium transition-colors">
                        Create an Account
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Login;
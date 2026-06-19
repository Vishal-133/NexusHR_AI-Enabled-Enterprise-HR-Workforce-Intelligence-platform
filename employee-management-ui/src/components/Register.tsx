import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import API from '../api/client';

const Register: React.FC = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [department, setDepartment] = useState('IT');
    const [role, setRole] = useState('EMPLOYEE');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        setLoading(true);

        try {
            // Sending all the exact fields to your Spring Boot DTO
            const response = await API.post('/auth/register', {
                userName: `${firstName} ${lastName}`, // Keeps compatibility with userName if needed
                firstName,
                lastName,
                userOfficialEmail: email,
                password,
                department,
                role
            });

            if (response.status === 201 || response.data) {
                setSuccess('Account created successfully! Redirecting to login...');
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            }
        } catch (err: any) {
            setError(err.response?.data?.message || 'Registration failed. Please check your fields.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-slate-900 via-indigo-950 to-slate-900 p-4">
            <div className="w-full max-w-md bg-slate-900/80 border border-slate-800 backdrop-blur-xl rounded-2xl p-6 shadow-2xl my-8">
                <div className="text-center mb-6">
                    <div className="inline-flex p-3 bg-indigo-500/10 rounded-xl text-indigo-400 mb-2">
                        <i className="bi bi-person-plus-fill text-2xl"></i>
                    </div>
                    <h2 className="text-2xl font-bold text-white tracking-tight">Create Account</h2>
                    <p className="text-slate-400 text-sm font-sans">Join the NexusHR Workforce Hub</p>
                </div>

                {error && (
                    <div className="mb-4 p-3 bg-red-500/10 border border-red-500/20 rounded-xl text-red-400 text-sm flex items-center gap-2 font-sans">
                        <i className="bi bi-exclamation-triangle-fill"></i>
                        <span>{error}</span>
                    </div>
                )}

                {success && (
                    <div className="mb-4 p-3 bg-emerald-500/10 border border-emerald-500/20 rounded-xl text-emerald-400 text-sm flex items-center gap-2 font-sans">
                        <i className="bi bi-check-circle-fill"></i>
                        <span>{success}</span>
                    </div>
                )}

                <form onSubmit={handleRegister} className="space-y-4 font-sans">
                    {/* Row for First Name and Last Name */}
                    <div className="grid grid-cols-2 gap-3">
                        <div>
                            <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">First Name</label>
                            <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} required
                                className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="Ram" />
                        </div>
                        <div>
                            <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Last Name</label>
                            <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} required
                                className="w-full px-3 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="Kumar" />
                        </div>
                    </div>

                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Official Email Address</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-envelope"></i></span>
                            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required
                                className="w-full pl-10 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="name@company.com" />
                        </div>
                    </div>

                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Security Password</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-lock"></i></span>
                            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required
                                className="w-full pl-10 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors" placeholder="••••••••" />
                        </div>
                    </div>

                    {/* Department Selection */}
                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Department</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-building"></i></span>
                            <select value={department} onChange={(e) => setDepartment(e.target.value)}
                                className="w-full pl-10 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors appearance-none cursor-pointer">
                                <option value="IT">IT Department</option>
                                <option value="General">General Administration</option>
                                <option value="HR">Human Resources</option>
                                <option value="Finance">Finance & Payroll</option>
                            </select>
                        </div>
                    </div>

                    {/* Platform Role Selection */}
                    <div>
                        <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Platform Role</label>
                        <div className="relative">
                            <span className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-500"><i className="bi bi-briefcase"></i></span>
                            <select value={role} onChange={(e) => setRole(e.target.value)}
                                className="w-full pl-10 pr-4 py-2 bg-slate-950 border border-slate-800 rounded-xl text-white focus:outline-none focus:border-indigo-500 transition-colors appearance-none cursor-pointer">
                                <option value="EMPLOYEE">Employee</option>
                                <option value="HR">HR Manager</option>
                                <option value="ADMIN">System Administrator</option>
                            </select>
                        </div>
                    </div>

                    <button type="submit" disabled={loading}
                        className="w-full py-2.5 mt-2 bg-indigo-600 hover:bg-indigo-500 text-white font-medium rounded-xl transition-all shadow-lg active:scale-[0.99] disabled:opacity-50 cursor-pointer">
                        {loading ? 'Processing Registration...' : 'Register User'}
                    </button>
                </form>

                <div className="text-center mt-4 text-sm text-slate-400 font-sans">
                    Already have an account?{' '}
                    <Link to="/login" className="text-indigo-400 hover:text-indigo-300 font-medium transition-colors">
                        Sign In
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Register;
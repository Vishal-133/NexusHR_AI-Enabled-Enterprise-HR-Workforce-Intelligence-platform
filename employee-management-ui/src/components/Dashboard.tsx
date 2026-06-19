import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../api/client';

interface EmployeeProfile {
    firstName: string;
    lastName: string;
    department: string;
    role: string;
    userOfficialEmail: string;
}

const Dashboard: React.FC = () => {
    const [activeTab, setActiveTab] = useState('overview');
    const [profile, setProfile] = useState<EmployeeProfile | null>(null);
    const [loadingProfile, setLoadingProfile] = useState(true);
    const [message, setMessage] = useState({ text: '', isError: false });
    
    const userRole = localStorage.getItem('role') || 'EMPLOYEE';
    const userEmail = localStorage.getItem('email') || '';
    const navigate = useNavigate();

    const [baseSalary, setBaseSalary] = useState('95000');
    const [generatedSlip, setGeneratedSlip] = useState<any>(null);

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await API.get(`/employees/profile?email=${userEmail}`);
                setProfile(response.data);
            } catch (err) {
                console.error("Failed to parse live profile row details.", err);
                setProfile({
                    firstName: "User",
                    lastName: "Account",
                    department: "General",
                    role: userRole,
                    userOfficialEmail: userEmail
                });
            } finally {
                setLoadingProfile(false);
            }
        };

        if (userEmail) {
            fetchUserProfile();
        }
    }, [userEmail, userRole]);

    const showNotification = (text: string, isError = false) => {
        setMessage({ text, isError });
        setTimeout(() => setMessage({ text: '', isError: false }), 4000);
    };

    const handleAttendance = async (type: 'check-in' | 'check-out') => {
        if (!profile) return;
        try {
            const response = await API.post(`/attendance/${type}`, {
                userOfficialEmail: profile.userOfficialEmail,
                firstName: profile.firstName,
                lastName: profile.lastName,
                department: profile.department,
                role: profile.role
            });
            if (response.data.success) {
                showNotification(`Attendance ${type === 'check-in' ? 'Check-In' : 'Check-Out'} committed live!`);
            }
        } catch (err: any) {
            showNotification(err.response?.data?.message || 'Error processing shift state.', true);
        }
    };

    const handleRunPayroll = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!profile) return;
        try {
            const response = await API.post('/payroll/run', {
                userOfficialEmail: profile.userOfficialEmail,
                firstName: profile.firstName,
                lastName: profile.lastName,
                department: profile.department,
                role: profile.role,
                baseSalary: parseFloat(baseSalary)
            });
            if (response.data.success) {
                setGeneratedSlip(response.data.data);
                showNotification('Payroll slip updated in MySQL database.');
            }
        } catch (err: any) {
            showNotification('Access denied or invalid execution inputs.', true);
        }
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    if (loadingProfile) {
        return (
            <div className="min-h-screen bg-slate-950 flex flex-col items-center justify-center text-slate-400 font-mono text-sm">
                <div className="w-12 h-12 border-4 border-t-indigo-500 border-slate-800 rounded-full animate-spin mb-4"></div>
                Resolving Enterprise User Space Lifecycle...
            </div>
        );
    }

    return (
        <div className="min-h-screen flex bg-slate-950 text-slate-100 font-sans">
            <aside className="w-64 bg-slate-900 border-r border-slate-800 flex flex-col justify-between p-4 shrink-0">
                <div>
                    <div className="flex items-center gap-3 px-2 py-4 border-b border-slate-800">
                        <span className="p-2 bg-indigo-600 rounded-lg text-white"><i className="bi bi-cpu-fill"></i></span>
                        <div>
                            <h4 className="font-bold text-white text-md tracking-wide">NexusHR Hub</h4>
                            <span className="text-xs text-indigo-400 font-medium">v2.0 Enterprise</span>
                        </div>
                    </div>

                    <nav className="mt-6 space-y-1">
                        {[
                            { id: 'overview', label: 'Dashboard Home', icon: 'bi-grid-1x2-fill' },
                            { id: 'attendance', label: 'Attendance Shifts', icon: 'bi-clock-fill' },
                            { id: 'leave', label: 'Leave Processing', icon: 'bi-calendar-range-fill' },
                            { id: 'payroll', label: 'Payroll Engine', icon: 'bi-cash-coin' }
                        ].map((tab) => (
                            <button key={tab.id} onClick={() => setActiveTab(tab.id)}
                                className={`w-full flex items-center gap-3 px-4 py-3 rounded-xl font-medium text-sm transition-all text-start cursor-pointer ${activeTab === tab.id ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-600/10' : 'text-slate-400 hover:bg-slate-800/50 hover:text-white'}`}>
                                <i className={tab.icon}></i>
                                {tab.label}
                            </button>
                        ))}
                    </nav>
                </div>

                <button onClick={handleLogout} className="w-full flex items-center justify-center gap-2 py-3 bg-slate-950 hover:bg-red-950/30 border border-slate-800 hover:border-red-900/50 text-slate-400 hover:text-red-400 rounded-xl text-sm font-medium transition-all cursor-pointer">
                    <i className="bi bi-power"></i> Terminate Workspace
                </button>
            </aside>

            <main className="flex-1 bg-slate-950 flex flex-col overflow-y-auto">
                <header className="flex items-center justify-between px-8 py-5 border-b border-slate-900 bg-slate-900/30 backdrop-blur-md sticky top-0 z-40">
                    <div className="flex items-center gap-3">
                        <div className="h-10 w-10 bg-indigo-600/20 text-indigo-400 font-bold flex items-center justify-center rounded-xl border border-indigo-500/20">
                            {profile?.firstName[0]}{profile?.lastName[0]}
                        </div>
                        <div>
                            <h2 className="text-sm font-bold text-white tracking-wide">{profile?.firstName} {profile?.lastName}</h2>
                            <p className="text-xs text-slate-400 font-mono">Dept: {profile?.department}</p>
                        </div>
                    </div>
                    <div className="flex items-center gap-4">
                        <span className="text-xs bg-slate-800 px-3 py-1.5 rounded-lg text-slate-400 border border-slate-700/50 font-mono">{profile?.userOfficialEmail}</span>
                        <span className="text-xs bg-indigo-500/10 text-indigo-400 font-semibold px-3 py-1.5 rounded-lg border border-indigo-500/20 uppercase">Role: {profile?.role}</span>
                    </div>
                </header>

                <div className="p-8 flex-1">
                    {message.text && (
                        <div className={`mb-6 p-4 rounded-xl border text-sm flex items-center gap-2 ${message.isError ? 'bg-red-500/10 border-red-500/20 text-red-400' : 'bg-emerald-500/10 border-emerald-500/20 text-emerald-400'}`}>
                            <i className={message.isError ? "bi bi-x-circle-fill" : "bi bi-check-circle-fill"}></i>
                            <span>{message.text}</span>
                        </div>
                    )}

                    {activeTab === 'overview' && (
                        <div className="bg-slate-900 border border-slate-800 rounded-2xl p-6 max-w-xl">
                            <h3 className="text-md font-bold text-white mb-2">Welcome Back, {profile?.firstName}!</h3>
                            <p className="text-sm text-slate-400 leading-relaxed">All profile properties have been safely parsed out of your backend database row structure. Use the side navigation options to punch shifts or check compensation records.</p>
                        </div>
                    )}

                    {activeTab === 'attendance' && (
                        <div className="max-w-xl bg-slate-900 border border-slate-800 rounded-2xl p-6 shadow-xl">
                            <h3 className="text-md font-bold text-white mb-2">Real-Time Attendance Shift Logger</h3>
                            <p className="text-slate-400 text-xs mb-6">Punches dynamic real-time timestamps using your active entity row configuration mapping properties.</p>
                            <div className="grid grid-cols-2 gap-4">
                                <button onClick={() => handleAttendance('check-in')} className="flex items-center justify-center gap-2 py-4 bg-emerald-600 hover:bg-emerald-500 text-white font-semibold rounded-xl shadow-md transition-colors cursor-pointer">
                                    <i className="bi bi-box-arrow-in-right text-lg"></i> Check-In Now
                                </button>
                                <button onClick={() => handleAttendance('check-out')} className="flex items-center justify-center gap-2 py-4 bg-red-600 hover:bg-red-500 text-white font-semibold rounded-xl shadow-md transition-colors cursor-pointer">
                                    <i className="bi bi-box-arrow-left text-lg"></i> Check-Out Now
                                </button>
                            </div>
                        </div>
                    )}

                    {activeTab === 'leave' && (
                        <div className="max-w-xl bg-slate-900 border border-slate-800 rounded-2xl p-6">
                            <h3 className="text-md font-bold text-white mb-4">Submit Leave Form Profile</h3>
                            <div className="space-y-4">
                                <div className="grid grid-cols-2 gap-4">
                                    <div><label className="block text-xs font-medium text-slate-400 mb-2">From Date</label><input type="date" className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-white focus:outline-none" /></div>
                                    <div><label className="block text-xs font-medium text-slate-400 mb-2">To Date</label><input type="date" className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-white focus:outline-none" /></div>
                                </div>
                                <button onClick={() => showNotification("Filing transaction simulation executed.")} className="px-6 py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-medium rounded-xl transition-colors cursor-pointer">Submit Request</button>
                            </div>
                        </div>
                    )}

                    {activeTab === 'payroll' && (
                        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                            <div className="bg-slate-900 border border-slate-800 rounded-2xl p-6">
                                <h3 className="text-md font-bold text-white mb-4">Run Payroll Metrics</h3>
                                <form onSubmit={handleRunPayroll} className="space-y-4">
                                    <div>
                                        <label className="block text-xs font-medium text-slate-400 mb-2">Base Salary Parameter (INR)</label>
                                        <input type="number" value={baseSalary} onChange={(e) => setBaseSalary(e.target.value)} required
                                            className="w-full bg-slate-950 border border-slate-800 rounded-xl p-3 text-white font-mono focus:outline-none" />
                                    </div>
                                    <button type="submit" className="w-full py-3 bg-indigo-600 hover:bg-indigo-500 text-white font-medium rounded-xl transition-colors cursor-pointer">
                                        Execute Runtime Calculation Pipeline
                                    </button>
                                </form>
                            </div>

                            {generatedSlip && (
                                <div className="bg-slate-900 border border-indigo-500/30 rounded-2xl p-6 shadow-2xl border-dashed bg-gradient-to-b from-slate-900 to-slate-950">
                                    <div className="flex justify-between items-start border-b border-slate-800 pb-4 mb-4">
                                        <div>
                                            <h4 className="font-bold text-white">Dynamic Payslip Generated</h4>
                                            <p className="text-xs text-slate-500 font-mono">Row ID Reference: {generatedSlip.id}</p>
                                        </div>
                                        <span className="px-2.5 py-1 text-xs font-bold bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 rounded-md tracking-wider">{generatedSlip.paymentStatus}</span>
                                    </div>
                                    <div className="space-y-2.5 text-sm font-mono">
                                        <div className="flex justify-between"><span className="text-slate-500">Employee Account:</span> <span className="text-white font-medium">{generatedSlip.firstName} {generatedSlip.lastName}</span></div>
                                        <div className="flex justify-between"><span className="text-slate-500">Target Email:</span> <span className="text-slate-300">{generatedSlip.userOfficialEmail}</span></div>
                                        <hr className="border-slate-800 my-2" />
                                        <div className="flex justify-between"><span className="text-slate-400">Base Wage Allocation:</span> <span className="text-white">₹{generatedSlip.baseSalary.toLocaleString()}</span></div>
                                        <div className="flex justify-between"><span className="text-slate-400">Calculated Allowances:</span> <span className="text-emerald-400">+₹{generatedSlip.allowances.toLocaleString()}</span></div>
                                        <div className="flex justify-between"><span className="text-slate-400">Tax Deductions:</span> <span className="text-red-400">-₹{generatedSlip.taxDeductions.toLocaleString()}</span></div>
                                        <div className="flex justify-between text-base font-bold border-t border-slate-800 pt-3 mt-3"><span className="text-indigo-400">Net Ledger Payout:</span> <span className="text-white">₹{generatedSlip.netSalary.toLocaleString()}</span></div>
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </main>
        </div>
    );
};

export default Dashboard;
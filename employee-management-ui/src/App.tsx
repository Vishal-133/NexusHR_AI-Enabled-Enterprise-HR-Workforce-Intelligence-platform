import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register'; // Import your new Register component
import Dashboard from './components/Dashboard';

// Route Guard to prevent unauthorized access if token is missing
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
    const token = localStorage.getItem('token');
    return token ? <>{children}</> : <Navigate to="/login" replace />;
};

function App() {
    return (
        <Router>
            <Routes>
                {/* Fallback landing route always triggers authentication check */}
                <Route path="/login" element={<Login />} />
                
                {/* Public Registration route added here */}
                <Route path="/register" element={<Register />} />
                
                {/* Guarded admin panel route containing workforce modules */}
                <Route path="/dashboard" element={
                    <ProtectedRoute>
                        <Dashboard />
                    </ProtectedRoute>
                } />
                
                {/* Redirect any other random URL typing back to login screen */}
                <Route path="*" element={<Navigate to="/login" replace />} />
            </Routes>
        </Router>
    );
}

export default App;
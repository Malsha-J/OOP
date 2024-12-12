"use client"

import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useState } from "react";
import { LoginScreen } from "./components/LoginScreen";
import ControlRoom from "./components/ControlRoom";

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

    const handleLogin = () => {
        setIsAuthenticated(true);
    };

    return (
        // <BuyingPortal/>
        // <TestComponent/>
        // <Router>
        //     <Routes>
        //         <Route path="/" element={isAuthenticated ? <Navigate to="/control-room" /> : <LoginScreen setAuth={setIsAuthenticated} onLogin={handleLogin} />} />
        //         <Route path="/control-room" element={isAuthenticated ? <ControlRoom /> : <Navigate to="/" />} />
        //     </Routes>
        // </Router>
        <ControlRoom/>
    );
}

export default App;

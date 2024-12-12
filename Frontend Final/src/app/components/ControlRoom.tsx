"use client";

import Link from 'next/link';
import { useState, useEffect, useRef } from "react";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from "axios";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { ResponsiveContainer, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from "recharts";
import { Power } from "lucide-react";
import { CounterControl } from "@/app/components/CounterControl";

interface Stats {
    issuedTickets: number;
    ticketsSold: number;
    activeVendors: number;
    activeCustomers: number;
}

interface LogEntry {
    message: string;
    timestamp: Date;
}

interface ChartData {
    time: string;
    tickets: number;
}

export default function ControlRoom() {
    const [isRunning, setIsRunning] = useState(false);
    const [chartData, setChartData] = useState<ChartData[]>([]);
    const [logs, setLogs] = useState<LogEntry[]>([]);
    const [isClient, setIsClient] = useState(false)
    const [stats, setStats] = useState<Stats>({
        issuedTickets: 0,
        ticketsSold: 0,
        activeVendors: 0,
        activeCustomers: 0,
    });
    const [config, setConfig] = useState({
        totalTickets: 1000,
        maxCapacity: 100,
        ticketReleaseRate: 10000,
        customerRetrievalRate: 2000,
        numVendors: 3,
        numCustomers: 10,
    });

    useEffect(() => {
        setIsClient(true)
    }, [])

    useEffect(() => {
        axios.get("http://localhost:8080/admin/config").then((response) => {
            setConfig(response.data);
        });

        // Cleanup the interval on component unmount
        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/stats");
                setStats(response.data);
            } catch (error) {
                addLog("Error fetching stats: " + error.message);
            }
        };
        const interval = setInterval(fetchStats, 3000); // Adjust interval as needed
        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/logs");
                setLogs(response.data);
            } catch (error) {
                console.error("Error fetching logs: " + error.message);

            }
        };
        const interval = setInterval(fetchLogs, 500); // Adjust as needed
        return () => clearInterval(interval);
    }, []);

    const logContainerRef = useRef<HTMLDivElement>(null);

// Scroll to the top whenever logs are updated
    useEffect(() => {
        if (logContainerRef.current) {
            logContainerRef.current.scrollTo({
                top: 0,
                behavior: "smooth",
            });
        }
    }, [logs]);

    const toggleSimulation = async () => {
        try {
            if (isRunning) {
                await fetch("http://localhost:8080/admin/simulation/stop", {method: "POST"});
                addLog("Simulation stopped");
                toast.success("Simulation stopped");
            } else {
                const response = await fetch("http://localhost:8080/admin/simulation/start", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        vendors: config.numVendors,
                        customers: config.numCustomers,
                    }),
                });
                response.ok ? toast.success("Simulation started") : toast.error("Failed to start simulation");
            }
            setIsRunning(!isRunning);
        } catch (error: any) {
            addLog("Error toggling simulation: " + error.message);
            toast.error("Failed to toggle simulation: " + error.message);
        }
    };

    const addLog = (message: string) => {
        setLogs((prev) => [{message, timestamp: new Date()}, ...prev.slice(0, 99)]);
    };

    const updateConfig = () => {
        axios.post("http://localhost:8080/admin/config", config)
            .then(() => toast.success("Configuration updated"))
            .catch((error) => toast.error("Failed to update configuration: " + error.message));
    };


    return (
        <div className="min-h-screen bg-[#0B0F19] text-white  outline outline-4 outline-white p-4">

            {/* Main Content */}
            <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <hr /><br />
            <h1 className="text-3xl font-bold text-center mb-8">Ticket Management System</h1>
            <hr />
            <br />
            <br />

                {/* Simulation Control */}
                <div className="space-y-6 mb-12">
                    <h2 className="text-xl font-semibold">Simulation Control</h2>
                    <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
                        <div className="space-y-4">
                            <Button
                                onClick={toggleSimulation}
                                className="w-full bg-blue-800 hover:bg-gray-700 h-12 flex items-center justify-center gap-2"
                            >
                                <Power className="w-5 h-5"/>
                                {isRunning ? "Stop Simulation" : "Start Simulation"}
                            </Button>
                            {/* Configuration Inputs */}
                            <div className="space-y-2">
                                <label className="text-sm text-gray-400">Total Tickets</label>
                                <div className="relative">
                                    <Input
                                        value={config.totalTickets}
                                        onChange={(e) => setConfig({...config, totalTickets: parseInt(e.target.value)})}
                                        className="w-full bg-gray-800 border-gray-700 text-white"
                                        type="number"
                                    />
                                </div>
                            </div>
                            <div className="space-y-2">
                                <label className="text-sm text-gray-400">Max Ticket Capacity</label>
                                <div className="relative">
                                    <Input
                                        value={config.maxCapacity}
                                        onChange={(e) => setConfig({...config, maxCapacity: parseInt(e.target.value)})}
                                        className="w-full bg-gray-800 border-gray-700 text-white"
                                        type="number"
                                    />
                                </div>
                            </div>
                            <div className="space-y-2">
                                <label className="text-sm text-gray-400">Ticket Release Rate</label>
                                <div className="relative">
                                    <Input
                                        value={config.ticketReleaseRate}
                                        onChange={(e) =>
                                            setConfig({...config, ticketReleaseRate: parseInt(e.target.value)})
                                        }
                                        className="w-full bg-gray-800 border-gray-700 text-white"
                                        type="number"
                                    />
                                </div>
                            </div>
                            <div className="space-y-2">
                                <label className="text-sm text-gray-400">Customer Retrieval Rate</label>
                                <div className="relative">
                                    <Input
                                        value={config.customerRetrievalRate}
                                        onChange={(e) =>
                                            setConfig({...config, customerRetrievalRate: parseInt(e.target.value)})
                                        }
                                        className="w-full bg-gray-800 border-gray-700 text-white"
                                        type="number"
                                    />
                                </div>
                            </div>
                            <div className="flex gap-3">
                                <CounterControl label={"Vendor"} value={config.numVendors}
                                                onChange={(val) => setConfig({...config, numVendors: val})}/>
                                <CounterControl label={"Customer"} value={config.numCustomers}
                                                onChange={(val) => setConfig({...config, numCustomers: val})}/>
                            </div>
                            <Button onClick={updateConfig} className="w-full bg-green-700 hover:bg-green-800">
                                Update Configuration
                            </Button>
                        </div>


                        {/* Real-time Analytics */}
                        <div className="lg:col-span-2 space-y-6 mt-[-6.5%]">
                            <h2 className="text-xl font-semibold">Ticket Pool</h2>

                            <div className="flex-col grid grid-cols-2 lg:grid-cols-4 gap-4">
                                <Card className="bg-gray-800 border-gray-700 p-4">
                                    <h3 className="text-sm font-medium text-gray-400">Tickets Issued</h3>
                                    <p className="text-2xl font-bold mt-2 text-gray-400">{stats.issuedTickets}</p>
                                </Card>
                                <Card className="bg-gray-800 border-gray-700 p-4">
                                    <h3 className="text-sm font-medium text-gray-400">Tickets Sold</h3>
                                    <p className="text-2xl font-bold mt-2 text-gray-400">{stats.ticketsSold}</p>
                                </Card>
                                <Card className="bg-gray-800 border-gray-700 p-4">
                                    <h3 className="text-sm font-medium text-gray-400">Active Vendors</h3>
                                    <p className="text-2xl font-bold mt-2 text-gray-400">{stats.activeVendors}</p>
                                </Card>
                                <Card className="bg-gray-800 border-gray-700 p-4">
                                    <h3 className="text-sm font-medium text-gray-400">Active Customers</h3>
                                    <p className="text-2xl font-bold mt-2 text-gray-400">{stats.activeCustomers}</p>
                                </Card>
                            </div>

                            <div className="grid md:grid-cols-2 gap-6">
                                <Card className="bg-gray-800 border-gray-700 p-4 w-[800px]">
                                    <div className="h-[450px]">
                                    <div>
                    {/* Event Log */}
                    <h2 className="text-xl font-semibold text-white mb-4">Event Log</h2>
                    <ScrollArea
                        className="h-[400px] w-full rounded-md border border-gray-800"
                        ref={logContainerRef} // Attach the ref here
                    >
                        <div className="p-4">
                            {logs
                                .slice() // Create a shallow copy to avoid mutating the original array
                                .reverse() // Reverse the array to show the latest log at the top
                                .map((log, index) => (
                                    <div key={index} className="flex items-center space-x-2 mb-3">
                                        <div className="w-2 h-2 bg-blue-500 rounded-full"/>
                                        <div className="flex-1">
                                            <p className="text-sm text-gray-300">{log.message}</p>
                                            <p className="text-xs text-gray-500">{log.timestamp.toString()}</p>
                                        </div>
                                    </div>
                                ))}
                        </div>
                    </ScrollArea>
                </div>

                                    </div>
                                </Card>
                            </div>
                        </div>
                    </div>
                </div>

            </main>
        </div>
    );
}

"use client"

import { useState, useEffect } from "react";
import { toast } from "react-toastify"; // Import Toast
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Ticket, User, Mail, Phone } from 'lucide-react';

interface TicketAvailability {
    general: number;
    vip: number;
}

interface PurchaseFeedback {
    message: string;
    type: "success" | "error";
}

export function BuyingPortal() {
    const [customerName, setCustomerName] = useState("");
    const [customerEmail, setCustomerEmail] = useState("");
    const [contactNo, setContactNo] = useState("");
    const [ticketsCount, setTicketsCount] = useState(1);
    const [ticketType, setTicketType] = useState("general");
    const [ticketAvailability, setTicketAvailability] = useState<TicketAvailability>({ general: 0, vip: 0 });
    const [loading, setLoading] = useState(false);
    const [feedback, setFeedback] = useState<PurchaseFeedback | null>(null);

    useEffect(() => {
        fetchAvailability();
    }, []);

    const fetchAvailability = async () => {
        try {
            const response = await fetch("/api/tickets/availability");
            if (response.ok) {
                const data = await response.json();
                setTicketAvailability({
                    general: data.general,
                    vip: data.vip
                });
            } else {
                setFeedback({ message: "Failed to fetch ticket availability.", type: "error" });
                toast.error("Failed to fetch ticket availability.");
            }
        } catch (error) {
            setFeedback({ message: "An error occurred while fetching availability.", type: "error" });
            toast.error("An error occurred while fetching availability.");
        }
    };

    const handlePurchase = () => {
        // Validate fields
        if (!customerName || !customerEmail || !contactNo || !ticketsCount || !ticketType) {
            toast.warn("Please fill all the required fields.");
            return;
        }
        if (ticketsCount <= 0) {
            toast.warn("Number of tickets must be at least 1.");
            return;
        }

        // Call backend API to purchase tickets
        setLoading(true);
        fetch("/api/tickets/purchase", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                customerName,
                customerEmail,
                contactNo,
                ticketsCount,
                ticketType,
            }),
        })
            .then((response) => {
                setLoading(false);
                if (response.ok) {
                    toast.success("Purchase successful!");
                    setCustomerName("");
                    setCustomerEmail("");
                    setContactNo("");
                    setTicketsCount(1);
                    setTicketType("general");
                } else {
                    return response.text().then((message) => {
                        toast.error(message || "Purchase failed.");
                    });
                }
            })
            .catch((error) => {
                setLoading(false);
                console.error("Error during purchase:", error);
                toast.error("An error occurred while processing your purchase.");
            });
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-900 p-4">
            <Card className="w-full max-w-4xl bg-gray-800 border-gray-700">
                <CardHeader>
                    <CardTitle className="text-2xl font-bold text-white">Buy Tickets</CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div className="space-y-2">
                            <Label htmlFor="customerName" className="text-sm font-medium text-gray-300">Customer Name</Label>
                            <div className="relative">
                                <Input
                                    id="customerName"
                                    value={customerName}
                                    onChange={(e) => setCustomerName(e.target.value)}
                                    className="w-full pl-10 bg-gray-700 border-gray-600 text-white placeholder-gray-400"
                                    placeholder="Enter your name"
                                />
                                <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="customerEmail" className="text-sm font-medium text-gray-300">Email</Label>
                            <div className="relative">
                                <Input
                                    id="customerEmail"
                                    type="email"
                                    value={customerEmail}
                                    onChange={(e) => setCustomerEmail(e.target.value)}
                                    className="w-full pl-10 bg-gray-700 border-gray-600 text-white placeholder-gray-400"
                                    placeholder="Enter your email"
                                />
                                <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="contactNo" className="text-sm font-medium text-gray-300">Contact Number</Label>
                            <div className="relative">
                                <Input
                                    id="contactNo"
                                    value={contactNo}
                                    onChange={(e) => setContactNo(e.target.value)}
                                    className="w-full pl-10 bg-gray-700 border-gray-600 text-white placeholder-gray-400"
                                    placeholder="Enter your contact number"
                                />
                                <Phone className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="ticketsCount" className="text-sm font-medium text-gray-300">Number of Tickets</Label>
                            <div className="relative">
                                <Input
                                    id="ticketsCount"
                                    type="number"
                                    value={ticketsCount}
                                    onChange={(e) => setTicketsCount(parseInt(e.target.value))}
                                    min={1}
                                    className="w-full pl-10 bg-gray-700 border-gray-600 text-white placeholder-gray-400"
                                />
                                <Ticket className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-5 w-5" />
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="ticketType" className="text-sm font-medium text-gray-300">Ticket Type</Label>
                            <Select value={ticketType} onValueChange={setTicketType}>
                                <SelectTrigger className="w-full bg-gray-700 border-gray-600 text-white">
                                    <SelectValue placeholder="Select ticket type" />
                                </SelectTrigger>
                                <SelectContent>
                                    <SelectItem value="general">General</SelectItem>
                                    <SelectItem value="vip">VIP</SelectItem>
                                </SelectContent>
                            </Select>
                        </div>
                    </div>
                    <div className="flex justify-between items-center">
                        <div>
                            <p className="text-sm font-medium text-gray-300">Available Tickets:</p>
                            <p className="text-sm text-gray-400">General: {ticketAvailability.general}</p>
                            <p className="text-sm text-gray-400">VIP: {ticketAvailability.vip}</p>
                        </div>
                        <Button onClick={handlePurchase} className="bg-blue-600 hover:bg-blue-700" disabled={loading}>
                            {loading ? "Processing..." : "Purchase Tickets"}
                        </Button>
                    </div>
                    {feedback && (
                        <div className={`mt-4 text-sm font-medium ${feedback.type === "success" ? "text-green-400" : "text-red-400"}`}>
                            {feedback.message}
                        </div>
                    )}
                </CardContent>
            </Card>
        </div>
    );
}

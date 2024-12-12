"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from "@/components/ui/collapsible"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Slider } from "@/components/ui/slider"
import { ChevronDown, Play, Settings, StopCircle } from 'lucide-react'

export function SimulationControls() {
    const [isRunning, setIsRunning] = useState(false)
    const [settingsOpen, setSettingsOpen] = useState(false)

    return (
        <div className="flex flex-wrap items-center gap-4">
            <Button
                size="lg"
                className={`w-full sm:w-auto ${isRunning ? "bg-red-500 hover:bg-red-600" : "bg-green-500 hover:bg-green-600"}`}
                onClick={() => setIsRunning(!isRunning)}
            >
                {isRunning ? (
                    <>
                        <StopCircle className="mr-2 h-5 w-5" /> Stop Simulation
                    </>
                ) : (
                    <>
                        <Play className="mr-2 h-5 w-5" /> Start Simulation
                    </>
                )}
            </Button>
            <Collapsible open={settingsOpen} onOpenChange={setSettingsOpen} className="w-full space-y-2">
                <CollapsibleTrigger asChild>
                    <Button variant="outline" className="w-full sm:w-auto">
                        <Settings className="mr-2 h-4 w-4" />
                        Settings
                        <ChevronDown className="ml-2 h-4 w-4" />
                    </Button>
                </CollapsibleTrigger>
                <CollapsibleContent className="space-y-4">
                    <div className="grid gap-4 sm:grid-cols-2">
                        <div className="space-y-2">
                            <Label htmlFor="ticketReleaseRate">Ticket Release Rate</Label>
                            <Slider id="ticketReleaseRate" min={1} max={100} step={1} defaultValue={[50]} />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="ticketRetrievalRate">Ticket Retrieval Rate</Label>
                            <Slider id="ticketRetrievalRate" min={1} max={100} step={1} defaultValue={[50]} />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="totalTickets">Total Tickets</Label>
                            <Input id="totalTickets" type="number" defaultValue={1000} />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="ticketPoolCapacity">Ticket Pool Capacity</Label>
                            <Input id="ticketPoolCapacity" type="number" defaultValue={500} />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="numberOfVendors">Number of Vendors</Label>
                            <Input id="numberOfVendors" type="number" defaultValue={10} />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="numberOfCustomers">Number of Customers</Label>
                            <Input id="numberOfCustomers" type="number" defaultValue={50} />
                        </div>
                    </div>
                    <Button className="w-full sm:w-auto">Save Settings</Button>
                </CollapsibleContent>
            </Collapsible>
        </div>
    )
}


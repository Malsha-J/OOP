import {Button} from "@/components/ui/button";
import { Plus, Minus } from 'lucide-react';

export const CounterControl = ({
                            label,
                            value,
                            onChange
                        }: {
    label: string
    value: number
    onChange: (value: number) => void
}) => (
    <div className="bg-gray-800 rounded-lg p-4 backdrop-blur-sm w-[48%]">
        <h3 className="text-gray-400 mb-4 text-center">{label}</h3>
        <div className="flex items-center justify-center gap-3 mb-3">
            <Button
                variant="outline"
                size="icon"
                className="bg-gray-800 hover:bg-neutral-700 border-neutral-400"
                onClick={() => onChange(Math.max(0, value - 1))}
            >
                <Minus className="h-4 w-4 text-gray-400" />
            </Button>
            <span className="text-xl text-white min-w-[3ch] text-center">{value}</span>
            <Button
                variant="outline"
                size="icon"
                className="bg-gray-800 hover:bg-neutral-700 border-neutral-400"
                onClick={() => onChange(value + 1)}
            >
                <Plus className="h-4 w-4 text-gray-400" />
            </Button>
        </div>
        <Button
            className="w-full bg-gray-800 hover:bg-neutral-700 text-gray-400"
            variant="outline"
        >
            Submit
        </Button>
    </div>
)
import {useEffect, useRef} from 'react';
import {Message as MessageType} from '../App';
import Message from './Message';
import {Box, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";

interface ChatWindowProps {
    messages: MessageType[];
    selectedAi: string;
    onAiChange: (value: string) => void;
}

function ChatWindow({messages, selectedAi, onAiChange}: ChatWindowProps) {
    const chatEndRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        chatEndRef.current?.scrollIntoView({behavior: 'smooth'});
    }, [messages]);

    const handleAiChange = (event: SelectChangeEvent) => {
        onAiChange(event.target.value);
    };

    return (
        <Box sx={{flexGrow: 1, overflowY: 'auto', p: 2}}>
            <FormControl fullWidth sx={{mb: 2}}>
                <InputLabel id="ai-select-label">Select AI</InputLabel>
                <Select
                    labelId="ai-select-label"
                    id="ai-select"
                    value={selectedAi}
                    label="Select AI"
                    onChange={handleAiChange}
                >
                    <MenuItem value="imperative/stream/sports">Sports</MenuItem>
                    <MenuItem value="imperative/stream/weather">Weather</MenuItem>
                </Select>
            </FormControl>
            <Box sx={{display: 'flex', flexDirection: 'column', gap: 2}}>
                {messages.map((msg) => (
                    <Message key={msg.id} message={msg}/>
                ))}
                <div ref={chatEndRef}/>
            </Box>
        </Box>
    );
}

export default ChatWindow;
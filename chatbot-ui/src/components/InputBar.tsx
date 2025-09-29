import {useState} from 'react';
import {Box, TextField, Button, CircularProgress} from "@mui/material";

interface InputBarProps {
    onSendMessage: (text: string) => void;
    isLoading: boolean;
}

function InputBar({onSendMessage, isLoading}: InputBarProps) {
    const [text, setText] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (text.trim() && !isLoading) {
            onSendMessage(text);
            setText('');
        }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{p: 2, display: 'flex', gap: 1}}>
            <TextField
                fullWidth
                variant="outlined"
                value={text}
                onChange={(e) => setText(e.target.value)}
                placeholder="Ask a question..."
                disabled={isLoading}
            />
            <Button
                type="submit"
                variant="contained"
                disabled={isLoading || !text.trim()}
                sx={{minWidth: 100}}
            >
                {isLoading ? <CircularProgress size={24}/> : 'Send'}
            </Button>
        </Box>
    );
}

export default InputBar;
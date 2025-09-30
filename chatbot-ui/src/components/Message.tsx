import {Message as MessageType} from '../App';
import {Box, Paper, Typography} from "@mui/material";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";

interface MessageProps {
    message: MessageType;
}

function Message({message}: MessageProps) {
    const isUser = message.sender === 'user';

    return (
        <Box sx={{display: 'flex', justifyContent: isUser ? 'flex-end' : 'flex-start'}}>
            <Paper
                elevation={1}
                sx={{
                    pr: 2,
                    pl: 2,
                    pt: 1.5,
                    pb: 1.5,
                    // Use theme colors for message bubbles
                    bgcolor: isUser ? 'primary.main' : 'grey.200',
                    color: isUser ? 'primary.contrastText' : 'text.primary',
                    maxWidth: '95%',
                    wordWrap: 'inherit',
                    borderRadius: isUser ? '20px 20px 5px 20px' : '20px 20px 20px 5px', // Rounded corners
                }}
            >
                {message.isStreaming ? (
                    <Typography component="div" sx={{whiteSpace: 'pre-wrap'}}>
                        {message.text}
                        <span className="streaming-cursor"/>
                    </Typography>
                ) : (
                    <ReactMarkdown rehypePlugins={[remarkGfm]}>{message.text}</ReactMarkdown>
                )}
            </Paper>
        </Box>
    );
}

export default Message;
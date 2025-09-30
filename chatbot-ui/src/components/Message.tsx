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
                    p: 1.5,
                    bgcolor: isUser ? 'primary.main' : 'grey.300',
                    color: isUser ? 'primary.contrastText' : 'text.primary',
                    maxWidth: '80%',
                    wordWrap: 'break-word',
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
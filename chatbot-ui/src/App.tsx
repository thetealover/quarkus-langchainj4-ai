import {useEffect, useRef, useState} from 'react';
import {v4 as uuidv4} from 'uuid';
import ChatWindow from './components/ChatWindow';
import InputBar from './components/InputBar';
import {Container, Typography, Paper} from "@mui/material";

const API_BASE_URL = 'http://localhost:8080';
const END_OF_STREAM_TOKEN = '[END_OF_STREAM]';

export interface Message {
    id: string;
    text: string;
    sender: 'user' | 'ai';
    isStreaming?: boolean;
}

const chatUserId = uuidv4().toUpperCase().replace(/-/g, '').slice(0, 16);

function App() {
    const [messages, setMessages] = useState<Message[]>([
        {
            id: 'initial-message',
            text: "Hi! I'm your AI assistant. How may I help you?",
            sender: 'ai',
        },
    ]);
    const [isLoading, setIsLoading] = useState(false);
    const [selectedAi, setSelectedAi] = useState("imperative/stream/sports");
    const eventSourceRef = useRef<EventSource | null>(null);

    const tokenQueueRef = useRef<string[]>([]);
    const animationFrameRef = useRef<number | null>(null);
    const activeAiMessageIdRef = useRef<string | null>(null);

    const processTokenQueue = () => {
        if (tokenQueueRef.current.length === 0) {
            animationFrameRef.current = null;
            return;
        }

        const tokensToProcess = tokenQueueRef.current.splice(0, tokenQueueRef.current.length);
        const textChunk = tokensToProcess.join('').replaceAll('%20', ' '); // The backend sends the spaces with %20

        setMessages((prev) =>
            prev.map((msg) =>
                msg.id === activeAiMessageIdRef.current
                    ? {...msg, text: msg.text + textChunk}
                    : msg
            )
        );

        animationFrameRef.current = requestAnimationFrame(processTokenQueue);
    };

    const addToTokenQueue = (token: string) => {
        tokenQueueRef.current.push(token);
        if (!animationFrameRef.current) {
            animationFrameRef.current = requestAnimationFrame(processTokenQueue);
        }
    };


    const handleSendMessage = async (text: string) => {
        if (isLoading) return;
        setIsLoading(true);

        const userMessage: Message = {id: uuidv4(), text, sender: 'user'};
        setMessages((prev) => [...prev, userMessage]);

        const aiMessageId = uuidv4();
        activeAiMessageIdRef.current = aiMessageId;
        const aiMessagePlaceholder: Message = {id: aiMessageId, text: '', sender: 'ai', isStreaming: true};
        setMessages((prev) => [...prev, aiMessagePlaceholder]);

        try {
            const response = await fetch(`${API_BASE_URL}/ai/${selectedAi}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({userId: chatUserId, message: text}),
            });

            if (!response.ok) throw new Error(`Failed to create stream. Status: ${response.status}`);
            const {streamKey} = await response.json();

            const eventSource = new EventSource(`${API_BASE_URL}/ai/stream/${streamKey}`);
            eventSourceRef.current = eventSource;

            const finalizeStream = () => {
                if (animationFrameRef.current) {
                    cancelAnimationFrame(animationFrameRef.current);
                    animationFrameRef.current = null;
                }
                processTokenQueue();
                tokenQueueRef.current = [];
                activeAiMessageIdRef.current = null;

                setMessages((prev) =>
                    prev.map((msg) =>
                        msg.id === aiMessageId ? {...msg, isStreaming: false} : msg
                    )
                );
                eventSource.close();
                eventSourceRef.current = null;
                setIsLoading(false);
            };

            eventSource.onmessage = (event) => {
                const token = event.data;

                if (token.trim() === END_OF_STREAM_TOKEN) {
                    finalizeStream();
                    return;
                }
                addToTokenQueue(token);
            };

            eventSource.onerror = () => {
                finalizeStream();
            };

        } catch (error) {
            console.error('Error communicating with AI service:', error);
            const errorMessage = 'Sorry, I encountered an error. Please try again later.';
            setMessages((prev) =>
                prev.map((msg) =>
                    msg.id === aiMessageId ? {...msg, text: errorMessage, isStreaming: false} : msg
                )
            );
            setIsLoading(false);
        }
    };

    useEffect(() => {
        return () => {
            eventSourceRef.current?.close();
            if (animationFrameRef.current) {
                cancelAnimationFrame(animationFrameRef.current);
            }
        };
    }, []);

    return (
        <Container maxWidth="md" sx={{display: 'flex', flexDirection: 'column', height: '100vh', py: 2}}>
            <Typography fontFamily="sans-serif" color="white" variant="h4" component="h1" align="center" gutterBottom>
                AI Chat UI
            </Typography>
            <Paper elevation={3} sx={{flexGrow: 1, display: 'flex', flexDirection: 'column'}}>
                <ChatWindow messages={messages} selectedAi={selectedAi} onAiChange={setSelectedAi}/>
                <InputBar onSendMessage={handleSendMessage} isLoading={isLoading}/>
            </Paper>
        </Container>
    );
}

export default App;
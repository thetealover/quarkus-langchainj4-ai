// --- CONFIGURATION ---
const STREAM_PAUSE_DURATION = 500; // 0.5 seconds

// --- DOM ELEMENT REFERENCES ---
const messageInput = document.getElementById("message-input") as HTMLInputElement;
const sendButton = document.getElementById("send-button") as HTMLButtonElement;
const messagesLog = document.getElementById("messages-log") as HTMLDivElement;
const websocketEndpoint = document.getElementById("websocket-url") as HTMLInputElement;
const websocketConnectButton = document.getElementById("websocket-connect-button") as HTMLButtonElement;

// --- STATE FOR STREAMING RESPONSES ---
let sentenceTimeoutId: number | null = null;
let currentSentenceElement: HTMLParagraphElement | null = null;
let ws: WebSocket | null = null;

// --- HELPER FUNCTION FOR USER/STATUS MESSAGES ---
function logStaticMessage(message: string, source: 'user' | 'server') {
    const p = document.createElement("p");
    p.textContent = `[${source.toUpperCase()}]: ${message}`;
    p.className = source === 'user' ? 'message user-message' : 'message server-message';
    messagesLog.appendChild(p);
    messagesLog.scrollTop = messagesLog.scrollHeight;
}

// --- WEBSOCKET CONNECTION FUNCTION ---
function connectWebSocket() {
    if (ws) {
        ws.close();
    }

    console.log(`Attempting to connect to ${websocketEndpoint.value}...`);
    ws = new WebSocket(`ws://localhost:8080/${websocketEndpoint.value}`);

    ws.onopen = () => {
        console.log("✅ WebSocket connection established.");
        logStaticMessage("Connected to the server!", "server");
        sendButton.disabled = false;
        messageInput.disabled = false;
    };

    ws.onmessage = (event) => {
        const messageChunk = event.data as string;
        console.log(`Received chunk: ${messageChunk}`);

        if (sentenceTimeoutId) {
            clearTimeout(sentenceTimeoutId);
        }

        if (!currentSentenceElement) {
            currentSentenceElement = document.createElement("p");
            currentSentenceElement.className = "message server-message";
            currentSentenceElement.textContent = "[SERVER]: ";
            messagesLog.appendChild(currentSentenceElement);
        }

        currentSentenceElement.textContent += messageChunk;
        messagesLog.scrollTop = messagesLog.scrollHeight;

        sentenceTimeoutId = window.setTimeout(() => {
            console.log("Sentence finalized due to pause.");
            currentSentenceElement = null;
            sentenceTimeoutId = null;
        }, STREAM_PAUSE_DURATION);
    };

    ws.onerror = (error) => {
        console.error("WebSocket error:", error);
        logStaticMessage("Connection error. See console for details.", "server");
    };

    ws.onclose = () => {
        console.log("❌ WebSocket connection closed.");
        if (sentenceTimeoutId) {
            clearTimeout(sentenceTimeoutId);
        }
        logStaticMessage("Disconnected from the server.", "server");
        sendButton.disabled = true;
        messageInput.disabled = true;
        ws = null;
    };
}

// --- SEND MESSAGE FUNCTIONALITY ---
function sendMessage() {
    const message = messageInput.value;
    if (message.trim() && ws?.readyState === WebSocket.OPEN) {
        ws.send(message);
        logStaticMessage(message, "user");
        messageInput.value = "";
    }
}

// --- EVENT LISTENERS ---
websocketConnectButton.addEventListener("click", connectWebSocket);
sendButton.addEventListener("click", sendMessage);
messageInput.addEventListener("keypress", (event) => {
    if (event.key === "Enter") {
        sendMessage();
    }
});

// Initially disable input until connection is established
sendButton.disabled = true;
messageInput.disabled = true;
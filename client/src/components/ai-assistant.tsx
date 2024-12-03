import React, {useState} from "react";
import {askQuestionApi} from "@/lib/apis/ai-assistant.api";

export function AIAssistant() {
    const [question, setQuestion] = useState("");
    const [response, setResponse] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(""); // Reset error before submitting

        try {
            // Call the API and set the response
            const aiResponse = await askQuestionApi({ question });
            setResponse(aiResponse);
        } catch ( error ) {

            // Handle errors (e.g., validation or network issues)
            setError("An error occurred while asking the AI.\n" + error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Ask your question"
                    required
                />
                <button type="submit" disabled={loading}>
                    {loading ? "Asking..." : "Ask"}
                </button>
            </form>
            {loading && <p>Loading...</p>}
            {response && <p>AI Response: {response}</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
}
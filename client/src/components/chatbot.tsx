"use client";

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { MessageCircle, X } from "lucide-react";
import { useForm } from "react-hook-form";
import { useAskQuestionMutation } from "@/lib/apis/ai-assistant.api";
import ReactMarkdown from "react-markdown";

interface Message {
  id: number;
  text: string;
  sender: "user" | "bot";
}

export function Chatbot() {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState<Message[]>([]);

  const { register, handleSubmit, reset } = useForm<{ question: string }>({
    defaultValues: { question: "" },
  });

  const { mutate, isPending } = useAskQuestionMutation();

  const onAskQuestion = async (question: string) => {
    mutate(
      { question },
      {
        onSuccess: (answer) => {
          const botMessage: Message = {
            id: Date.now(),
            text: answer,
            sender: "bot",
          };
          setMessages((prevMessages) => [...prevMessages, botMessage]);
        },
        onError: () => {
          const botMessage: Message = {
            id: Date.now(),
            text: "Sorry, something went wrong. Please try again.",
            sender: "bot",
          };
          setMessages((prevMessages) => [...prevMessages, botMessage]);
        },
      },
    );
  };

  useEffect(() => {
    const savedMessages = localStorage.getItem("chatHistory");
    if (savedMessages) {
      setMessages(JSON.parse(savedMessages));
    }
  }, []);

  useEffect(() => {
    localStorage.setItem("chatHistory", JSON.stringify(messages));
  }, [messages]);

  const onSubmit = async ({ question }: { question: string }) => {
    if (!question.trim()) return;

    const userMessage: Message = {
      id: Date.now(),
      text: question,
      sender: "user",
    };
    setMessages((prevMessages) => [...prevMessages, userMessage]);

    onAskQuestion(question);
    reset({ question: "" });
  };

  return (
    <>
      {!isOpen ? (
        <Button
          className="fixed bottom-4 right-4 rounded-full p-4"
          onClick={() => setIsOpen(true)}
        >
          <MessageCircle className="h-6 w-6" />
          <span className="sr-only">Open chat</span>
        </Button>
      ) : (
        <div className="fixed bottom-4 right-4 bg-white rounded-lg shadow-lg flex flex-col w-full max-w-screen-md h-[80vh]">
          <div className="flex justify-between items-center p-4 border-b">
            <h2 className="text-lg font-semibold">Chat with us</h2>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => setIsOpen(false)}
            >
              <X className="h-4 w-4" />
              <span className="sr-only">Close chat</span>
            </Button>
          </div>

          <ScrollArea className="flex-grow p-4">
            {messages.map((message) => (
              <div
                key={message.id}
                className={`mb-2 ${message.sender === "user" ? "text-right" : "text-left"}`}
              >
                <span
                  className={`inline-block p-2 rounded-lg ${
                    message.sender === "user"
                      ? "bg-blue-500 text-white"
                      : "bg-gray-200 text-gray-800"
                  }`}
                >
                  <ReactMarkdown children={message.text} />
                </span>
              </div>
            ))}
          </ScrollArea>

          <div className="p-4 border-t flex">
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="flex-grow flex items-center"
            >
              <Input
                type="text"
                placeholder="Type your message..."
                {...register("question", { required: "Question is required." })}
                className="flex-grow mr-2"
              />
              <Button type="submit" disabled={isPending}>
                {isPending ? "Sending..." : "Send"}
              </Button>
            </form>
          </div>
        </div>
      )}
    </>
  );
}

package com.example.health_guardian_server.controllers;

import com.example.health_guardian_server.services.AIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai-assistant")
public class AIController {
  private final AIService aiService;

  public AIController(AIService aiService) {
    this.aiService = aiService;
  }

  @PostMapping("/ask")
  public String askQuestion(@RequestBody QuestionRequest questionRequest) {
    // Lấy câu hỏi từ request body
    String question = questionRequest.getQuestion();

    String answer = aiService.askQuestion(question);

    return answer;
  }

  public static class QuestionRequest {
    private String question;

    public String getQuestion() {
      return question;
    }

    public void setQuestion(String question) {
      this.question = question;
    }
  }
}

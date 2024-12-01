package com.example.health_guardian_server.services.impl;

import com.example.health_guardian_server.services.AIService;
import com.google.cloud.dialogflow.v2.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
  private final SessionsClient sessionsClient;
  private final String projectId;

  public String askQuestion(String question) {
    try {
      String sessionId = java.util.UUID.randomUUID().toString();
      SessionName session = SessionName.of(projectId, sessionId);

      TextInput.Builder textInput = TextInput.newBuilder().setText(question).setLanguageCode("vi");
      QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

      DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
      QueryResult queryResult = response.getQueryResult();

      String fulfillmentText = queryResult.getFulfillmentText();
      if (fulfillmentText == null || fulfillmentText.isEmpty()) {
        return "Xin lỗi, tôi không hiểu câu hỏi của bạn.";
      }
      return fulfillmentText;
    } catch (Exception e) {
      e.printStackTrace();
      return "Có lỗi xảy ra khi xử lý câu hỏi.";
    }
  }
}

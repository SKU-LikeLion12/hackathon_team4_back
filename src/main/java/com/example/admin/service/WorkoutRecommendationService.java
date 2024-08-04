package com.example.admin.service;

import com.example.admin.DTO.ExerciseDTO;
import com.example.admin.DTO.WorkoutRecommendationDTO;
import com.example.admin.domain.PersonChild;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkoutRecommendationService {

    private final String OPENAI_API_KEY = "";  // OpenAI API 키
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final PersonChildService personChildService;



    public WorkoutRecommendationDTO recommendExercises(String token) {
        PersonChild personChild = personChildService.tokenToChild(token);

        return generateRecommendations(personChild);
    }

    private WorkoutRecommendationDTO generateRecommendations(PersonChild personChild) {
        Map<String, List<ExerciseDTO>> recommendations = new HashMap<>();

        int age = calculateAge(personChild.getBirthDate());
        String gender = personChild.getGender();
        double height = personChild.getHeight();
        double weight = personChild.getWeight();

        recommendations.put("Core", generateExercisesForArea(
                String.format(
                        "지적 장애가 있는 %d세 %s, 키 %.2f cm, 몸무게 %.2f kg인 사람을 위한 코어 근육 운동을 스트레칭, 근력운동, 유산소운동 순서대로 각각 한 가지씩 추천해 주세요. " +
                                "각 운동에 대해 다음과 같은 형식으로 한국어로 응답해 주세요:\n" +
                                "1. 운동 유형: 스트레칭, 근력운동, 유산소 중 하나를 선택\n" +
                                "2. 운동 이름: 추천하는 운동의 이름\n" +
                                "3. 설명: 간단한 설명\n" +
                                "4. 추천 횟수: 몇 회나 몇 분 동안 운동을 해야 하는지\n" +
                                "5. 사진 URL: 운동 방법을 설명하는 이미지 URL\n" +
                                "6. 참고 영상 링크: 운동 방법을 설명하는 한국 유튜브 링크\n" +
                                "응답 예시:\n" +
                                "운동 유형: 스트레칭\n" +
                                "운동 이름: 힙 브릿지 스트레칭\n" +
                                "설명: 허리를 눕혀서 두 다리를 벌린 뒤 무릎을 구부립니다. 양 발을 편히 바닥에 붙이면서, 손으로 발꿈치를 붙잡습니다. 이 상태에서 허리를 약간 들어 올리며 엉덩이를 천천히 들어 올립니다. 적당한 시간 유지 후 천천히 원래의 자세로 돌아가며 이를 반복합니다.\n" +
                                "추천 횟수: 10~15회 반복\n" +
                                "사진 URL: http://example.com/hip_bridge.jpg\n" +
                                "참고 영상 링크: https://www.youtube.com/watch?v=example",
                        age, gender, height, weight)
        ));

        return new WorkoutRecommendationDTO(recommendations);
    }

    private int calculateAge(String birthDate) {
        LocalDate birthDateLocal = LocalDate.parse(birthDate);
        return Period.between(birthDateLocal, LocalDate.now()).getYears();
    }

    private List<ExerciseDTO> generateExercisesForArea(String prompt) {
        String responseText = callOpenAIAPI(prompt);
        return parseOpenAIResponse(responseText);
    }

    private String callOpenAIAPI(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        // JSON 객체를 생성
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestNode = objectMapper.createObjectNode();
        requestNode.put("model", "gpt-4");
        requestNode.putArray("messages").addObject()
                .put("role", "system")
                .put("content", "You are a fitness expert.");
        requestNode.withArray("messages").addObject()
                .put("role", "user")
                .put("content", prompt);

        try {
            // JSON 객체를 문자열로 변환
            String requestBody = objectMapper.writeValueAsString(requestNode);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, JsonNode.class);

            JsonNode responseBody = response.getBody();
            if (responseBody != null && responseBody.has("choices")) {
                String result = responseBody.get("choices").get(0).get("message").get("content").asText();
                // 응답을 출력하여 확인
                System.out.println("OpenAI Response: " + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    private List<ExerciseDTO> parseOpenAIResponse(String responseText) {
        List<ExerciseDTO> exercises = new ArrayList<>();

        try {
            // 응답이 JSON 형식이 아닌 경우를 대비한 처리
            String[] lines = responseText.split("\n");
            String type = "";
            String name = "";
            String description = "";
            String repetitions = "";
            String imageUrl = "";
            String videoUrl = "";

            for (String line : lines) {
                if (line.toLowerCase().contains("유형")) {
                    type = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.toLowerCase().contains("이름")) {
                    name = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.toLowerCase().contains("설명")) {
                    description = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.toLowerCase().contains("횟수")) {
                    repetitions = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.toLowerCase().contains("사진")) {
                    imageUrl = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.toLowerCase().contains("영상")) {
                    videoUrl = line.substring(line.indexOf(":") + 1).trim();
                    exercises.add(new ExerciseDTO(type, name, description, repetitions, imageUrl, videoUrl));
                    // 모든 정보를 추가 후 초기화
                    type = "";
                    name = "";
                    description = "";
                    repetitions = "";
                    imageUrl = "";
                    videoUrl = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return exercises;
    }

}

package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import io.restassured.http.ContentType;
import model.ChatRequest;
import model.ChatResponse;
import model.Message;

import java.util.List;

import static io.restassured.RestAssured.given;

public class LLMClient {
    private final ObjectMapper mapper = new ObjectMapper();

    public String ask(String prompt) {

        ChatRequest request = new ChatRequest(
                Config.MODEL,
                List.of(new Message("user", prompt)),
                Config.TEMPERATURE
        );

        try {

            String response = given()
                    .contentType(ContentType.JSON)
                    .header(
                            "Authorization",
                            "Bearer " + Config.API_KEY
                    )
                    .body(request)
                    .when()
                    .post(Config.BASE_URL)
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            ChatResponse chatResponse =
                    mapper.readValue(response, ChatResponse.class);

            return chatResponse
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

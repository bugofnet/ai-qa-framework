package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.http.ContentType;
import model.ChatRequest;
import model.ChatResponse;
import model.Message;

import java.util.List;

import static io.restassured.RestAssured.given;

public class LLMClient {
    private static final String BASE_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODEL =
            "llama-3.3-70b-versatile";

    private final Dotenv dotenv = Dotenv.load();

    private final ObjectMapper mapper = new ObjectMapper();

    public String ask(String prompt) {

        ChatRequest request = new ChatRequest(
                MODEL,
                List.of(new Message("user", prompt)),
                0
        );

        try {

            String response = given()
                    .contentType(ContentType.JSON)
                    .header(
                            "Authorization",
                            "Bearer " + dotenv.get("GROQ_API_KEY")
                    )
                    .body(request)
                    .when()
                    .post(BASE_URL)
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

package model;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private double temperature;

    public ChatRequest() {
    }

    public ChatRequest(String model, List<Message> messages, double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }
}

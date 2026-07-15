package config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String API_KEY =
            dotenv.get("GROQ_API_KEY");

    public static final String BASE_URL =
            dotenv.get("BASE_URL");

    public static final String MODEL =
            dotenv.get("MODEL");

    public static final double TEMPERATURE =
            Double.parseDouble(dotenv.get("TEMPERATURE"));
}

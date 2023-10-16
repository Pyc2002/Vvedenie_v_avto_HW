package HW_3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class SpoonacularAbstractTest {
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() throws IOException {
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        configFile = new FileInputStream("src/test/resources.properties");
        prop.load(configFile);
        apiKey =  prop.getProperty("apiKey");
        baseUrl= prop.getProperty("base_url");
    }

    public static String getApiKey() {
        return apiKey;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
}

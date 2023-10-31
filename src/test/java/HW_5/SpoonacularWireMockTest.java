package HW_5;


import io.qameta.allure.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic(value = "WireMock и логи")
@Feature(value = "Fifth HomeWork")
public class SpoonacularWireMockTest extends AbstractTest{
    private static final Logger logger
            = LoggerFactory.getLogger(SpoonacularWireMockTest.class);
    @Test
    @Description("search recipes GET")
    @Severity(SeverityLevel.NORMAL)
    void searchRecipesTest() throws IOException{
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /recipes/complexSearch");
        //given
        stubFor(get(urlPathEqualTo("/recipes/complexSearch"))
//                .withQueryParam("query", equalTo("pasta"))
//                .withQueryParam("maxFat", equalTo("25"))
//                .withQueryParam("number", equalTo("2"))
                .withHeader("Accept", matching("text/.*"))

                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Pasta With Tuna")
                        .withHeader("Content-Type", "text/html")));
//                        .withBody("\"title\":\"Pasta With Tuna\"")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/recipes/complexSearch");
        request.addHeader("Accept", "text/html");
        logger.debug("http клиент создан");
        //when
        HttpResponse httpResponse = httpClient.execute(request);
        String response = convertResponseToString(httpResponse);
        //then
        verify(getRequestedFor(urlEqualTo("/recipes/complexSearch")));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals("text/html", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("Pasta With Tuna", response);
    }

    @Test
    @Description("search recipes by ingredients GET")
    @Severity(SeverityLevel.NORMAL)
    void searchRecipesByIngredientsTest() {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /recipes/findByIngredients");

        stubFor(get(urlPathEqualTo("/recipes/findByIngredients"))
                .withQueryParam("ingredients", equalTo("apple"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withBody("Slow Cooker Apple Pork Tenderloin")));
        logger.debug("Переход к блоку given()");
        given()

                .queryParam("ingredients", "apple")
                .when()
                .get(getBaseUrl() + "/recipes/findByIngredients")
                .then()
                .statusCode(200)
                .body(containsString("Apple"));
    }

    @Test
    @Description("search ingredients GET")
    @Severity(SeverityLevel.NORMAL)
    void searchIngredientsTest() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /food/ingredients/search");
        //given
        stubFor(get(urlPathMatching("/food/ingredients/search/*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("\"name\": \"applewood smoked bacon\"")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + "/food/ingredients/search");
        logger.debug("http клиент создан");
        //when
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertResponseToString(httpResponse);
        //then
        verify(getRequestedFor(urlEqualTo("/food/ingredients/search")));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals("application/json", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("\"name\": \"applewood smoked bacon\"", stringResponse);
    }

    @Test
    @Description("similar Recipes GET")
    @Severity(SeverityLevel.NORMAL)
    void getSimilarRecipesTest() {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /recipes/715538/similar");

        stubFor(get(urlPathEqualTo("/recipes/715538/similar"))
                .withQueryParam("number", equalTo("2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"1\":\"title\": \"Dinner Tonight: Roasted Beet Bruschetta\"")
                        .withBody("\"2\":\"title\": \"Dinner Tonight: Chickpea Bruschetta\"")));
        logger.debug("Переход к блоку given()");
        given()
                .pathParam("id", 715538)
                .queryParam("number", "2")
                .when()
                .get(getBaseUrl() + "/recipes/{id}/similar")
                .then()
                .statusCode(200)
                .body(containsString("Bruschetta"));
    }

    @Test
    @Description("classify cuisine POST")
    @Severity(SeverityLevel.NORMAL)
    void classifyCuisinePOSTTest() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для POST recipes/cuisine");
        //given
        stubFor(post(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("x-www-form-urlencoded"))
                .withRequestBody(containing("\"title\": \"Slow Cooked Applesauce\""))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Slow Cooked Applesauce")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(getBaseUrl() + "/recipes/cuisine");
        request.addHeader("Content-Type", "x-www-form-urlencoded");
        request.setEntity(new StringEntity("\"title\": \"Slow Cooked Applesauce\""));
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        String stringResponse = convertResponseToString(response);
        //then
        verify(postRequestedFor(urlEqualTo("/recipes/cuisine"))
                .withHeader("Content-Type", equalTo("x-www-form-urlencoded")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Slow Cooked Applesauce", stringResponse);
    }
    @Test
    @Description("dish pairing for wine GET")
    @Severity(SeverityLevel.NORMAL)
    void dishPairingForWineTest() {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /food/wine/dishes");

        stubFor(get(urlPathEqualTo("/food/wine/dishes"))
                .withQueryParam("wine", equalTo("Malbec"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"pairings\": [\n" +
                                "        \"stew\",\n" +
                                "        \"chili\",\n" +
                                "        \"steak\",\n" +
                                "        \"jjigae\",\n" +
                                "        \"burger\"")));
        logger.debug("Переход к блоку given()");
        given()

                .queryParam("wine", "Malbec")
                .when()
                .get(getBaseUrl() + "/food/wine/dishes")
                .then()
                .statusCode(200)
                .body(containsString("steak"))
                .body(containsString("stew"));
    }

    @Test
    @Description("parse ingredients POST Given()")
    @Severity(SeverityLevel.NORMAL)
    void parseIngredientsPOSTTest() {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для POST /recipes/parseIngredients");

        stubFor(post(urlPathEqualTo("/recipes/parseIngredients"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"unit\": \"tablespoon\"")));
        logger.debug("Переход к блоку given()");
        given()
                .when()
                .post(getBaseUrl() + "/recipes/parseIngredients")
                .then()
                .statusCode(200)
                .body(containsString("tablespoon"));
    }
    @Test
    @Description("parse ingredients POST HTTP")
    @Severity(SeverityLevel.NORMAL)
    void parseIngredientsHTTPPOSTTest() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для POST /recipes/parseIngredients");

        stubFor(post(urlPathEqualTo("/recipes/parseIngredients"))
                .withHeader("Content-Type", equalTo("x-www-form-urlencoded"))
                .withRequestBody(containing("\"original\": \"1 tablespoon Capers\""))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"unit\": \"tablespoon\"")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(getBaseUrl() + "/recipes/parseIngredients");
        request.addHeader("Content-Type", "x-www-form-urlencoded");
        request.setEntity(new StringEntity("\"original\": \"1 tablespoon Capers\""));
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        String stringResponse = convertResponseToString(response);
        //then
        verify(postRequestedFor(urlEqualTo("/recipes/parseIngredients"))
                .withHeader("Content-Type", equalTo("x-www-form-urlencoded")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("\"unit\": \"tablespoon\"", stringResponse);
    }
    @Test
    @Description("ingredient information GET")
    @Severity(SeverityLevel.NORMAL)
    void getIngredientInformationTest() {
        logger.info("Тест код ответ 200 запущен");
        logger.debug("Формирование мока для GET /food/ingredients/9266/information");

        stubFor(get(urlPathEqualTo("/food/ingredients/9266/information"))
                .withQueryParam("amount", equalTo("3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("\"originalName\": \"pineapples\"")));
        logger.debug("Переход к блоку given()");
        given()
                .pathParam("id", 9266)
                .queryParam("amount", "3")
                .when()
                .get(getBaseUrl() + "/food/ingredients/{id}/information")
                .then()
                .statusCode(200)
                .body(containsString("pineapples"));
    }
}

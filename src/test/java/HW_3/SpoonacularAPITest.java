package HW_3;



import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class SpoonacularAPITest {
    private String apiKey = "37c3e5cb47ff414bbdd9fd553add7921";

    @Test
    void searchRecipesTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "pasta")
                .queryParam("maxFat", 25)
                .queryParam("number", 2)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .prettyPeek()
                .then()
                .body("results[0].title", containsString("Pasta"))
                .body("results[0].id", is(654959))
                .body("results.size()", equalTo(2))
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .header("Connection", "keep-alive")
                .header("Content-Length", Integer::parseInt, lessThan(3000))
                .contentType(ContentType.JSON)
                .time(lessThan(1500L));
    }

    @Test
    void searchRecipesByIngredientsTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("ingredients", "apples")
                .queryParam("ingredients", "flour")
                .queryParam("ingredients", "sugar")
                .queryParam("number", 2)
                .when()
                .get("https://api.spoonacular.com/recipes/findByIngredients")
                .prettyPeek()
                .then()
                .body("title.toString()", containsString("Apple"))
                .body("$.size()", is(2))
                .statusCode(200)
                .time(lessThan(1500L));
    }
    @Test
    void searchIngredientsTest() {
         given()
                .queryParam("apiKey", apiKey)
                .queryParam("query", "banana")
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "desc")
                .queryParam("number", 3)
                .when()
                .get("https://api.spoonacular.com/food/ingredients/search")
                .prettyPeek()
                .then()
                .body("results[0].name", containsString("banana"))
                .body("results[1].name", containsString("banana"))
                .body("results[2].name", containsString("banana"))
                .body("results.size()", is(3))
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    void getSimilarRecipesTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("number", 2)
                .pathParam("id", 715538)
                .when()
                .get("https://api.spoonacular.com/recipes/{id}/similar")
                .prettyPeek()
                .then()
                .body("[0].title", containsString("Dinner Tonight"))
                .body("[1].title", containsString("Dinner Tonight"))
                .body("$.size()", is(2))
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    void classifyCuisinePOSTTest() {
        given()
                .queryParam("apiKey", apiKey)
                .when()
                .formParam("title", "Slow Cooked Applesauce")
                .formParam("ingredientList", "apples from a local tree if possible")
                .post("https://api.spoonacular.com/recipes/cuisine")
                .prettyPeek()
                .then()
                .body("cuisine", equalTo("Mediterranean"))
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    void dishPairingForWineTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("wine", "Merlot")
                .when()
                .get("https://api.spoonacular.com/food/wine/dishes")
                .prettyPeek()
                .then()
                .body("text", containsString("Merlot"))
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    void parseIngredientsPOSTTest() {
        given()
                .queryParam("apiKey", apiKey)
                .when()
                .formParam("ingredientList", "1 tablespoon Capers")
                .formParam("servings", 1)
                .formParam("includeNutrition", true)
                .post("https://api.spoonacular.com/recipes/parseIngredients")
                .prettyPeek()
                .then()
                .body("[0].original", equalTo("1 tablespoon Capers"))
                .body("[0].name", equalTo("capers"))
                .statusCode(200)
                .time(lessThan(1500L));
    }

    @Test
    void getIngredientInformationTest() {
        given()
                .queryParam("apiKey", apiKey)
                .queryParam("amount", 3)
                .pathParam("id", 9266)
                .when()
                .get("https://api.spoonacular.com/food/ingredients/{id}/information")
                .prettyPeek()
                .then()
                .body("id", is(9266))
                .body("amount", is(3.0F))
                .statusCode(200)
                .time(lessThan(1500L));
    }


}

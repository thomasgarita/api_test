import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Article;
import model.User;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static helpers.DataHelper.generateRandomEmail;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArticleTests extends BaseTest {


    private static String resourcePath = "/v1/article";

    private static Integer createdArticle = 0;

    @BeforeGroups (groups = "create_article")
    public void createArticle(){
        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

       Response response= given()
                .spec(RequestSpecs.generateToken())
                .body(testArticle)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdArticle = jsonPathEvaluator.get("id");
    }

    @Test
    public void Test_Create_Article_Success(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

       given()
                .spec(RequestSpecs.generateToken())
                .body(testArticle)
                .post(resourcePath)
                .then()
                .statusCode(200)
               .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_article")
    public void Test_Delete_Article_Success(){

        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + createdArticle.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test
    public void Test_Invalid_Token_Cant_Create_New_Articles(){

        Article testArticle = new Article(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testArticle)
                .post(resourcePath)
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


}

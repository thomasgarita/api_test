import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;

public class PostTests extends BaseTest {

    private static String resourcePath = "/v1/post";
    private static Integer createdPost = 0;

    @BeforeGroups(groups = "create_post")
    public void createPost(){
        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        Response response= given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdPost = jsonPathEvaluator.get("id");
    }


    @Test
    public void Test_Create_Post_Success(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test
    public void Test_Invalid_Path_Cant_Create_Post(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath + "1")
                .then()
                .statusCode(404);
    }

    @Test
    public void Test_Invalid_Token_Cant_Create_New_Posts(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testPost)
                .post(resourcePath)
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test
    public void Test_Get_All_Post_Success(){

        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "s")
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test
    public void Test_Invalid_Token_Cant_Get_All_Posts(){

        given()
                .spec(RequestSpecs.generateFakeToken())
                .get(resourcePath + "s")
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


    @Test(groups = "create_post")
    public void Test_Get_Post_Success(){

        given()
                .spec(RequestSpecs.generateToken())
                .get(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Invalid_Token_Cant_Get_Post(){

        given()
                .spec(RequestSpecs.generateFakeToken())
                .get(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


    @Test(groups = "create_post")
    public void Test_Update_Post_Success(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .put(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Invalid_Token_Cant_Update_Post(){

        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        given()
                .spec(RequestSpecs.generateFakeToken())
                .body(testPost)
                .put(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


    @Test(groups = "create_post")
    public void ZTest_Delete_Post_Success(){

        given()
                .spec(RequestSpecs.generateToken())
                .delete(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Invalid_Token_Cant_Delete_Post(){

        given()
                .spec(RequestSpecs.generateFakeToken())
                .delete(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

}

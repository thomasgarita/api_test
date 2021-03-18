import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;

public class CommentsTests extends BaseTest {

    private static String resourcePath = "/v1/comment";
    private static Integer createdPost = 0;
    private static Integer createdComment = 0;

    @BeforeGroups(groups = "create_post")
    public void createPost(){
        Post testPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        Response response= given()
                .spec(RequestSpecs.generateToken())
                .body(testPost)
                .post("/v1/post");

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdPost = jsonPathEvaluator.get("id");
    }

    @BeforeGroups(groups = "create_comment")
    public void createComment(){
        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        Response response = given().auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .post(resourcePath + "/" + createdPost.toString());

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdComment = jsonPathEvaluator.get("id");

    }


    @Test(groups = "create_post")
    public void Test_Create_Comment_Success(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given().auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .post(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Invalid_Password_Cant_Create_Comment(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given().auth()
                .basic("testuser", "testpasss")
                .body(testComment)
                .post(resourcePath + "/" + createdPost.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Get_All_Comments_Success(){

        given().auth()
                .basic("testuser", "testpass")
                .get(resourcePath + "s"+ "/" + createdPost.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = "create_post")
    public void Test_Empty_Password_Cant_Get_All_Comments(){

        given().auth()
                .basic("testuser", "")
                .get(resourcePath + "s"+ "/" + createdPost.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void Test_Get_One_Comment_Success(){

        given().auth()
                .basic("testuser", "testpass")
                .get(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void Test_Invalid_User_Cant_Get_One_Comment(){

        given().auth()
                .basic(" testuser", "testpass")
                .get(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void Test_Update_Comment_Success(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given().auth()
                .basic("testuser", "testpass")
                .body(testComment)
                .put(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void Test_Empty_User_Cant_Update_Comment(){

        Comment testComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomComment());

        given().auth()
                .basic(" ", "testpass")
                .body(testComment)
                .put(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void ZTest_Delete_Comment_Success(){

        given().auth()
                .basic("testuser", "testpass")
                .delete(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(200)
                .spec(ResponseSpecs.defaultSpec());
    }

    @Test(groups = {"create_post", "create_comment"})
    public void ZTest_Empty_Credentials_Cant_Delete_Comment(){

        given().auth()
                .basic(" ", " ")
                .delete(resourcePath + "/" + createdPost.toString() + "/" + createdComment.toString())
                .then()
                .statusCode(401)
                .spec(ResponseSpecs.defaultSpec());
    }


}

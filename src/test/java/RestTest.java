import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import objects.Issue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestTest {

    RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        RestAssured.port = 3000;
        RestAssured.baseURI = "http://";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("X-Redmine-API-Key", "c5ee3c3");
        requestSpec = builder.build();
    }

    @Test
    public void createIssue() {
        Issue issue = new Issue();
        issue.setProjectId(2071);
        issue.setSubject("Example");
        issue.setPriorityId(4);

        int issuesID = given()
                .spec(requestSpec)
                .body(issue)
                .when().post("/issues.json").then()
                .statusCode(201)
                .extract().path("issue.id");

        given().spec(requestSpec)
                .pathParam("issue_id", issuesID)
                .when().get("/issues/{issue_id}.json").then()
                .statusCode(200);
    }

    @Test
    public void updateIssue() {
        Issue issue = new Issue();
        issue.setProjectId(2071);
        issue.setSubject("Example");
        issue.setPriorityId(4);

        int issuesID = given()
                .spec(requestSpec)
                .body(issue)
                .when().post("/issues.json").then()
                .statusCode(201)
                .extract().path("issue.id");

        Issue update = new Issue();
        update.setSubjectName("Bug from aleshin");
        update.setDescription("Update new bug");

        given().spec(requestSpec).body(update)
                .pathParam("issue_id", issuesID)
                .when().put("/issues/{issue_id}.json").then()
                .statusCode(200);

        given().spec(requestSpec)
                .pathParam("issue_id", issuesID)
                .when().get("/issues/{issue_id}.json").then()
                .statusCode(200)
                .body("issue.subject", equalTo("Bug from aleshin"))
                .body("issue.description", equalTo("Update new bug"));
    }
}


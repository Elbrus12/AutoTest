/*Для тестироване API использована библиотека RestAssured и junit*/

package api;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// Общий класс для запуска всех тестов и обозначения переменной, которая используется во всех тестах, также тут прописан
// URL тестируемого API
public class HandlerTests {
    private final static String URL = "https://petstore.swagger.io/v2";
    Random rn = new Random();

// Проверка метода GET для PET, делаем запрос для выборки статусов, обозначено в переменной, далее извлекаем JSON ответ и проверяем
    @Test
    public void checkPetStatusTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        String status = "available";
       List<String> CheckStatus = given()
                .when()
                .get(String.format("/pet/findByStatus?status=%s",status))
                .then().log().all()
                .extract().body().jsonPath().getList("status");

       Assert.assertTrue(CheckStatus.contains(status));
    }

// Негативный сценарий для GET PET, ввести неверный endpoint
    @Test
    public void negativeCheckPetStatusTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
         given()
                .when()
                .get("214234222pet/findByStatus?status=available")
                .then().log().status();
    }

// Проверка добавления PET, параметризованы id и name, в ответе проверяем name и id
    @Test
    public void addNewPetTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        Integer id = 1;
        String name = "Test";
        String requestBody = String.format("""
                {
                  "id": %d,
                  "category": {
                    "id": 0,
                    "name": "%s"
                  },
                  "name": "doggie",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "%s"
                    }
                  ],
                  "status": "available"
                }""", id, name, name);
        given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then().log().all()
                .body("category.name",equalTo(name))
                .body("id",equalTo(id));
    }

// Негативный сценарий для POST PET, если указать тело запроса несоответствующей модели, пропущена скобка
    @Test
    public void NegativeAddNewPetTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError400());
        String requestBody = """
                {
                  "id": 1,
                  "category": {
                    "id": 0,
                    "name": "Test"
                  },
                  "name": "doggie",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "test"
                    }
                  ],
                  "status": "available"
                """;
        given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then().log().all();
    }

// Удаления PET, перед удалением необходимо сначала добавить (вызвать метод POST) затем использовав те же параметры удалить
    @Test
    public void DeletePetTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        Integer code = 200;
        Integer id = 1;
        String name = "Test";
        String requestBody = String.format("""
                {
                  "id": %d,
                  "category": {
                    "id": 0,
                    "name": "%s"
                  },
                  "name": "doggie",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "%s"
                    }
                  ],
                  "status": "available"
                }""", id, name, name);
        given()
                .body(requestBody)
                .when()
                .post("/pet")
                .then().log().all()
                .body("category.name",equalTo(name))
                .body("id",equalTo(id));
         given()
                .when()
                .delete(String.format("/pet/%s",id))
                .then().log().all()
                .body("code",equalTo(code));
    }

// Негативные сценарий удаления PET, в самом endpoint можно написать рандомный ID
    @Test
    public void NoDeletePetTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        Integer RandomNumber = rn.nextInt();
        System.out.println(RandomNumber);
        given()
                .when()
                .delete(String.format("/pet/%s", RandomNumber))
                .then().log().all();
    }

// Вызов GET запрос, для просмотра Store inventory, проверка, что возвращается не пустое значение
    @Test
    public void LookStoreInventTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
         Assert.assertNotNull(given()
                 .when()
                 .get("/store/inventory")
                 .then().log().all());
    }

// Вызов GET запрос с неправильным endpoint
    @Test
    public void unLookStoreInventTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        Assert.assertNotNull(given()
                .when()
                .get("23553store/inventory")
                .then().log().all());
    }

// POST запрос для добавления, параметризованы id, petID
    @Test
    public void addOrderStoryTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        Integer id, petId;
        id = 1;
        petId = 1;
        String RequestBody = String.format("""
                {
                  "id": %d,
                  "petId": %d,
                  "quantity": 0,
                  "shipDate": "2024-03-14T13:37:02.648Z",
                  "status": "placed",
                  "complete": true
                }
                """, id, petId);
        given()
                .body(RequestBody)
                .when()
                .post("/store/order")
                .then().log().all()
                .body("id",equalTo(id))
                .body("petId",equalTo(petId));
    }

// Негативный сценарий, не смогли добавить ничего POST запросом, из-за некорректного endpoint
    @Test
    public void noAddOrderStoryTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        String RequestBody = """
                {
                  "id": 1,
                  "petId": 1,
                  "quantity": 0,
                  "shipDate": "2024-03-14T13:37:02.648Z",
                  "status": "placed",
                  "complete": true
                }
                """;
        given()
                .body(RequestBody)
                .when()
                .post("234234store/order")
                .then().log().all();
    }

// Удаление StoreOrder, перед этим добавивь его. Параметризованы id, petId, code, code при положительном сценарии всегда 200
    @Test
    public void DeleteStoreOrderTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        Integer id, petId, code;
        id = 1;
        petId = 1;
        code = 200;
        String RequestBody = String.format("""
                {
                  "id": %d,
                  "petId": %d,
                  "quantity": 0,
                  "shipDate": "2024-03-14T13:37:02.648Z",
                  "status": "placed",
                  "complete": true
                }
                """, id, petId);
        given()
                .body(RequestBody)
                .when()
                .post("/store/order")
                .then().log().all()
                .body("id",equalTo(id))
                .body("petId",equalTo(petId));
        given()
                .when()
                .delete(String.format("/store/order/%s",id))
                .then().log().all()
                .body("code",equalTo(code));
    }


// Негативный сценарий удаления StoreOrder, путем добавления рандомного Id, code должен быть 404
    @Test
    public void unDeleteStoreOrderTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        Integer code, randomId;
        code = 404;
        randomId = rn.nextInt();
        given()
                .when()
                .delete(String.format("/store/order/%s",randomId))
                .then().log().all()
                .body("code",equalTo(code));
    }

// Создания пользователя, id, username - параметризованы, code получаем 200
    @Test
    public void CreateUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        String username, requestBody;
        Integer id, code;
        username ="Test";
        id = 1;
        requestBody = String.format("""
                {
                  "id": %d,
                  "username": "%s",
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 1
                }""",id, username);
        code = 200;
        given()
                .when()
                .body(requestBody)
                .post("/user")
                .then().log().all()
                .body("code", equalTo(code));
    }

// Негативный сценарий добавления пользователя, не укажем username
    @Test
    public void unCreateUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError400());
        String requestBody = """
                {
                  "id": 1,
                  "username": ,
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 1
                }""";
        Integer code = 400;
        given()
                .when()
                .body(requestBody)
                .post("/user")
                .then().log().all()
                .body("code", equalTo(code));
    }

// Получения пользователя GET, перед этим сначала добавивь его методом POST
    @Test
    public void GetUsersTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        String username, requestBody;
        Integer id, code;
        username ="Test";
        id = 1;
        requestBody = String.format("""
                {
                  "id": %d,
                  "username": "%s",
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 1
                }""",id, username);
        code = 200;
        given()
                .when()
                .body(requestBody)
                .post("/user")
                .then().log().all()
                .body("code", equalTo(code));
        given()
                .when()
                .get("user/Test")
                .then().log().all()
                .body("id",equalTo(id)).body("username",equalTo(username));
    }

// Негативный сценарий поиска, ищем несуществующего пользователя
    @Test
    public void unknownUsersTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        Integer code = 1;
        String message, username;
        username = rn.toString();
        message = "User not found";
        given()
                .when()
                .get(String.format("/user/%s",username))
                .then().log().all()
                .body("code",equalTo(code)).body("message",equalTo(message));

    }


// Удаляем пользователя по Username в endpoint
    @Test
    public void DeleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecOK200());
        String username, requestBody, message;
        Integer id, code;
        username ="Test";
        message = username;
        id = 1;
        requestBody = String.format("""
                {
                  "id": %d,
                  "username": "%s",
                  "firstName": "string",
                  "lastName": "string",
                  "email": "string",
                  "password": "string",
                  "phone": "string",
                  "userStatus": 1
                }""",id, username);
        code = 200;
        given()
                .when()
                .body(requestBody)
                .post("/user")
                .then().log().all()
                .body("code", equalTo(code));
        given()
                .when()
                .delete(String.format("/user/%s",username))
                .then().log().all()
                .body("code",equalTo(code)).body("message",equalTo(message));
    }

// Негативный сценарий удаления, вводи username несуществующего пользователя
    @Test
    public void unDeleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecError404());
        String username = rn.toString();
        given()
                .when()
                .delete(String.format("/user/%s",username))
                .then().log().all();
    }
}



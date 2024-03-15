/*В данной спецификации прописаны методы для проверки статусов кодов данного URL, который мы будем использовать*/

package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

// Класс для написания ContentType и URL
public class Specifications {
    public static RequestSpecification requestSpec(String url){
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

// Метод для получения статусы 200
    public static ResponseSpecification responseSpecOK200(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

// Метод для получения статусы 404
    public static ResponseSpecification responseSpecError404(){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

// Метод для получения статусы 400
    public static ResponseSpecification responseSpecError400(){
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

// Вывод и использования установки спецификации для
    public static void installSpecification(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}

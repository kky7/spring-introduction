package hello.hellospring; // 하위 패키지만 component scan함

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {
    public static void main(String[] args) {

        SpringApplication.run(HelloSpringApplication.class, args);
    }
}

// Tomcat started on port(s): 8080 (http) with context path ''
// http://localhost:8080/
// tomcat 내장 웹서버
// org.springframework.boot:spring-boot-starter-web:2.7.3 에서 org.springframework.boot:spring-boot-starter-tomcat:2.7.3 포함

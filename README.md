# 프로젝트 환경설정

## 프로젝트 생성
- https://start.spring.io/ 이용
- 또는 File - New - Project 로 알맞게 생성
- 실행 
  - Tomcat started on port(s): 8080 (http) with context path '' 
  - http://localhost:8080/
  
### 참고
IntelliJ 와 Eclipse의 차이

- 워크스페이스 개념이 없는 인텔리제이는 여러개의 프로젝트를 하나의 윈도우에 어떻게 구성할까?
    - 모듈(Module)이라는 개념이 있다.
    - 모듈: 하나의 작은 프로젝트
    - 인텔리제이에서는 프로젝트 안에 여러 개의 작은 프로젝트(모듈)를 구성할 수 있다.
    - 인텔리제이에서는 프로젝트 안에 작은 프로젝트(모듈)을 구성하는 것이기 때문에 서로 연관이 있는 프로젝트를 구성해서 사용하는 것이 바람직하다.
  
## 라이브러리 살펴보기
- 오른쪽 옆 gradle -> dependencies
- spring-boot-starter-web
  - org.springframework.boot:spring-boot-starter-tomcat:2.7.3 => tomcat 내장 웹서버
  - spring-webmvc: 스프링 웹 mvc
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
  - spring boot
    - spring core
- logging 
  - logback, slf4j
- test
  - 자바진영에서 대부분 junit 라이브러리 5버전을 많이 씀
  - assertj
  - mockito
  - spring-test: 스프링 통합 테스트 지원
  
## View 환경설정
- 컨트롤러에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리한다.
```java
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello"; // templates의 hello.html로 가라
    }
}
```
- resources : templates / {ViewName} + .html (여기선 view name이 hello)
  
## 빌드
intellij 터미널 사용
1. ./gradlew build
2. cd build/libs
3. java -jar hello~.jar

-----------------------------

# 스프링 웹 개발 기초
## 정적 컨텐츠
- 서버에서 하는 것 없이 **파일을 그대로** 웹 브라우저로 넘겨 주는 것

## MVC와 템플릿 엔진
- jsp, php : 템플릿 엔진
- html을 그대로 주는 것이 아니라 model, view, controller형태를 가지고 **서버에서 프로그래밍해서 html을 동적으로 바꿔서** 넘겨주는 것
- 과거에는 컨트롤러와 뷰가 따로 분리되어 있지 않았음. 뷰에서 모든 것을 다했었음 -> model1 방식
- 요즘은 뷰와 컨트롤러 분리, 뷰는 화면에 관련된 일만, 비즈니스 로직은 서버 뒷단에서 모두 처리, 모델에다 화면에 관련된거 담아서 넘겨주는 패턴
- 템플릿 엔진을 model, view, controller로 쪼개고, 뷰를 템플릿 엔진으로 html을 프로그래밍한 것으로 렌더링 해서, 렌더링 된 html을 클라이언트에 전달해준다. 

## API
- html이 아닌 json이라는 데이터 구조 포맷으로 클라이언트에게 데이터를 전달
- 데이터를 내려주면 화면은 클라이언트가 그려주는 형태
- 서버끼리 데이터 전달
- 기본은 json (객체를 이용해 json 형식으로 전달 가능)
- 객체를 반환. HttpMessageConverter를 이용해서 json 스타일로 바꿔서 반환을 해줌. 뷰 이런거 없이 http response에 data를 반환
------------------------------
  
# 회원 관리 예제 - 백엔드 개발
- 컨트롤러: 웹 MVC의 컨트롤러 역할
- 서비스: 핵심 비즈니스 로직 구현
- 리포지토리: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인: 비즈니스 도메인 객체 (ex. 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨)

- 서비스 클래스는 비즈니스에 가까운 용어를 써야 함. 비즈니스에 의존적으로 설계
- 레포지토리는 개발적으로 용어를 씀
- test
  - TDD: 선 개발 후 테스트 방식이 아닌 선 테스트 후 개발 방식의 프로그래밍 방법
  - 현재 하는 방식은 TDD가 아닌 개발 후 테스트
  - given, when, then
  - assertThat 사용
  - test끝날 때마다 수행: @AfterEach (test 끝날 때 마다 다음 test에 영향이 가지 않도록 실행되어야 할 것을 정의)
-----------------------------------
  
# 스프링 빈과 의존관계

## 컴포넌트 스캔과 자동 의존관계 설정
- memberController가 memberService를 통해서 회원가입하고, 데이터를 조회할 수 있어야한다.
  - memberController가 memberService를 의존한다.
  - 생성자에 @Autowired가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다. -> 객체 의존 관계를 외부에서 넣어주는 것을 DI 의존성 주입이라 한다.
  - 스프링이 올라올 때 @Component(@Controller, @Service, @Repository모두 포함하고 있음) 애노테이션이 있으면 다 스프링이 객체를 생성해서 컨테이너에 등록을 함. @Autowired는 연관관계를 연결해줌
- controller를 통해서 요청을 받는다. -> service를 통해서 비즈니스 로직을 처리한다. -> repository에서 데이터를 저장한다.
- package hello.hellospring; // 하위 패키지만 component scan함

```java
@Controller
public class MemberController { // 스프링 컨테이너 뜰 때 생성 -> 생성자 호출
    private final MemberService memberService;

    // 스프링 컨테이너에서 memberService를 가져와서 넣어준다. -> DI (dependency injection)
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
```

```java
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  
  ...
}
```

## 자바 코드로 직접 스프링 빈 등록하기

- @Component 관련 애노테이션, @Autowired 지운 상태

```java
package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
```
----------------------------
  
# 스프링 DB 접근 기술

## 순수 JDBC
- MemoryMemberRepository에서 JdbcMemberRepository로 구현 클래스를 변경
  - 인터페이스를 이용해 구현체만 변경 (조립하듯이 갈아낀다.)

## 스프링 통합 테스트
- test
  - @SpringBootTest: 스프링 컨테이너와 테스트를 함께 실행한다.
  - @Transactional: 테스트케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. -> DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
  - 통합 테스트가 아닌 가급적 순수한 단위테스트가 좋은 테스트일 확률이 높음
  
## 스프링 JdbcTemplate
- JdbcTemplate, MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해줌
- 그러나 SQL 직접 작성해야함

## JPA
- 기본적인 SQL도 JPA가 직접 만들어서 실행해줌
- SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임 전환
- 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을
  커밋한다. 만약 런타임 예외가 발생하면 롤백한다.

## 스프링 데이터 JPA
- 리포지토리에 구현 클래스 없이 인터페이스 만으로 개발
- 메서드 이름만으로 CRUD 기능 제공

---------------------------
  
# AOP

## AOP가 필요한 상황
- 모든 메소드의 호출 시간을 측정하고 싶을 때
- 공통 관심 사항(cross-cutting consern) vs 핵심 관심 사항(core concern)
- 모든 메소드에 호출 시간을 측정하는 코드를 작성할 때 생기는 문제
  - 회원가입, 회원 조회에 시간을 측정하는 기능은 핵심 관심 사항이 아니다.
  - 시간을 측정하는 로직은 공통 관심 사항이다.
  - 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
  - 시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
  - 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

## AOP 적용
- 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
- 핵심 관심 사항을 깔끔하게 유지할 수 있다.
- 변경이 필요하면 이 로직만 변경하면 된다.
- 원하는 적용 대상을 선택할 수 있다.
- AOP를 사용하는 객체는 프록시를 만들어서 중간에 프록시를 거치고 진짜 객체를 실행
  - 프록시 memberController -> memberController -> 프록시 memberService -> 실제 memberService -> 프록시 memberRepository -> 실제 memberRepository
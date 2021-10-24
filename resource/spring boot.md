# Spring boot

[*Spring Boot 中文文档*](https://www.baidu.com/link?url=xBpOg8TK3OKp9iarmu8TC7GEyZj3urfjNUeCARD5knW8e3RtlQIZee-iqfibTm3Lz0n0CWYw8bN6iJ0xU_SKA_&wd=&eqid=97a67ff6000a18bb00000002617029e9)

[toc]

## 1 开发一个spring boot应用

* 创建POM

* 添加Classpath依赖

* 编码

  ```java
  import org.springframework.boot.*;
  import org.springframework.boot.autoconfigure.*;
  import org.springframework.web.bind.annotation.*;
  
  @RestController
  @EnableAutoConfiguration
  public class Example {
  
      @RequestMapping("/")
      String home() {
          return "Hello World!";
      }
  
      public static void main(String[] args) throws Exception {
          SpringApplication.run(Example.class, args);
      }
  
  }
  ```

  

  1. `@RestController` 和`@RequestMapping` 是`Spring MVC`注解（不是`Spring Boot`特有的）。有关更多详细信息，请参阅 Spring 参考文档中的 [MVC 章节](https://docs.spring.io/spring/docs/5.0.4.RELEASE/spring-framework-reference/web.html#mvc)

  * `@RestController` 为stereotype注解。她能为代码阅读者提供一些提示，对于spring而言，这个类具有特殊的作用。

  * `@RequestMapping` 注解提供了路由信息。他告诉spring，将某一路径请求映射到某一方法上。

    

  2. `@EnableAutoConfiguration` 此注解告知`Spring Boot` 根据添加的`jar` 依赖来 ‘猜想’ 您如何配置`Spring` 并进行自动配置。

* 运行

* 创建可执行的jar或者可部署的war
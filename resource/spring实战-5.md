# Spring实战-第五版

[toc]

* 创建Spring应用

  生成的Spring应用是符合Maven或者Gradle项目结构的。其中源码放到`src/main/java` 中，测试代码放到`src/main/test` 中，而非Java的资源放到了`src/main/resouce`。

  * **mvnw 和 mvnw.cmd**：这是Maven包装器脚本。借助这些脚本，即便没有安装maven也能够构建项目

  * **pom.xml**: 这是Maven构建规范。

    * 在Spring应用中，可能存在一些dependencies 的artifact ID都有starter这个单词。Spring boot starter依赖的特别之处在于他们本身不包含代码，而是传递性地拉去其他的库。

    * spring 应用中可能存在Spring boot的插件，这个插件提供了一些重要的功能

      1. 他提供了一个Maven goal，允许我们使用Maven来运行应用。

      2. 他会确保依赖的所有库都会包含在可执行jar文件中，并且能够保证他们在运行时类路径下是可用的。

      3. 他会在jar中生成一个manifest文件，将引导类（就是spring boot应用程序的启动类）声明为可执行的jar的主类。

         * **引导应用**
           * <u>因为是通过可执行jar文件来运行应用，所以很重要一点就是要有一个主类，他将会在jar运行时被执行。同时还需要一个最小化Spring配置，以引导该应用</u>。这就是入口类或者说引导类所做的事情。引导类如下：

         ```java
         // 尽管在该类中只有很少的代码，但是他包含了很多的内容。其中最强大的一行代码也是最短的@SpringBootApplication，该注解明确表明这是一个Spring Boot应用，但是该注解远比看上去更强大。
         // @SpringBootApplication是一个组合注解，他组合了3个其他的注解。
         //		1. @SpringBootConfiguration：将类声明为配置类。这个注解实际上是@Configuration注解的特殊形式。
         // 		2. @EnableAutoConfiguration：启动Spring Boot的自动配置。这个注解会告诉Spring Boot自动配置他认为我们用到的组件。
         // 		3. @ComponentScan：启动组件扫描。这样我们能够通过像@Component，@Controller，@Service这样的注解声明其他类，Spring会自动发现他们并将他们注册为Spring 应用上下文的组件。
         @SpringBootApplication
         public class TacoCloudApplication {
           public static void main (String[] args) {
             StringApplication.run(TacoCloudApplication.class, args);
           }
         }
         ```

         

      ```xml
      <build>
      	<plugins>
        	<plugin>
          	<groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
        </plugins>
      </build>
      ```

      





## 1. 什么是Spring

`spring`的核心是提供一个容器(`container`)，通常称为`spring`应用上下文(`spring application context`)，他们会创建和管理应用组件。这些组件也可以称为`bean`，会在`Spring`应用上下文中装配在一起，从而形成一个完整的应用程序。

> 依赖注入（**DI**）：就是指将bean装配在一起的行为。



* 显示装配 （显示依赖注入）

  在历史上，指导Spring应用上下文将bean装配在一起的方式是使用一个或多个XML文件（描述各个组件以及他们与其他组件的关联关系）。例如：

  ```xml
  <!-- 改xml中描述两个bean， 并且通过构造器参数 将aService 装配到 bService中 -->
  <bean id = "aService" class = "com.demo.AService" />
  <bean id = "bService" class = "com.demo.BService">
    <constructor-arg ref = "aService" />
  </bean>
  ```

  但是在新版本中的Spring中，基于Java的配置更为常见。如：

  ```java
  // 该基于Java的配置和 XML配置是等价的
  // @Configuration 注解会告知Spring 这是一个配置类，会为Spring应用上下文提供bean。
  // @Bean 被@Bean修饰的方法，表明这些方法所返回的对象以bean的形式添加到Spring的应用上下文中（默认情况下，这些bean所对应的bean ID与定义他们的方法名称是相同的）。
  @Configuration
  public class ServiceConfiguration {
    @Bean
    public AService aService() {
      return new AService();
    }
    
    @Bean
    public BService bService(AService aService) {
      return new BService(aService);
    }
  }
  ```

  **相对于基于XML的配置方式，基于Java的配置会带来多项额外的收益，包括更强的类型安全性以及更好的重构能力。**<font color = red>**即便如此，不管使用Java还是使用XML的显示配置，只有Spring不能进行自动配置的时候才是必要的。**</font>

* 自动装配 （Autowiring）和组件扫描 （component scanning）

  借助组件扫描技术，Spring能够自动发现应用类路径下的组件，并将他们创建成Spring应用上下文中的bean。

  借助自动装配技术，Spring能够自动为组件注入他们所依赖的其他bean。

  **随着Spring Boot的引入，自动配置的能力已经远远超出了组件扫描和自动装配。**Spring Boot是Spring框架的扩展，提供了很多增强生产效率的方法。最为大家所熟知的增强方法就是自动配置（Autoconfiguration）。Spring Boot能够基于类路径中的条目、环境变量和其他因素合理猜测需要配置的组件并将他们装配在一起。



## 2. Spring web应用

Spring自带了一个强大的web框架，Spring MVC。**<u>Spring MVC的核心是控制器（controller）的概念</u>**。

- 控制器是处理HTTP请求并以某种方式进行信息响应的类。

  - 在面向浏览器的应用中，控制器会填充可选的数据模型并将请求传递给一个视图，以便生成返回给浏览器的HTML。

    面向浏览器的应用：

    * 构建领域类 - 即Dao 或者 Repository

    - 创建控制器类 - 即 Controller层

      填充数据模型，可以是Model或者Map等数据类型。

    - 设计视图

      控制器完成之后，将请求传递给视图。Spring提供了多种定义视图的方式，包括JSP，Thymeleaf，FreeMarker、Mustache和基于Groovy的模板。

      1. [<u>校验表单输入</u>](#jsr)：

         **Spring支持Java的Bean 校验API（Bean Validation API，也被称为JSR-303）**。这样在数据校验的时候，能够更容易的校验数据规则，而不必在应用程序代码中显示编写声明逻辑。

         要在Spring MVC中应用校验，我们需要：

         **a、在被校验的类上声明校验规则**

         **b、在控制器方法中声明要进行校验**

  - 或者将数据填充响应体（Restful API）



### 2.1 <span id=jsr>校验表单数据</span>









# 注解汇总

* `@Configuration` : 注解会告知Spring 这是一个配置类，会为Spring应用上下文提供bean。
* `@Bean`: 被@Bean修饰的方法，表明这些方法所返回的对象以bean的形式添加到Spring的应用上下文中（默认情况下，这些bean所对应的bean ID与定义他们的方法名称是相同的）。 **通常是在Spring不能自动装配时和@Configuration搭配使用。**



* `@SpringBootApplication`：该注解明确表明这是一个Spring Boot应用，并且是一个组合注解，他组合了3个其他的注解。
* `@SpringBootConfiguration`：将类声明为配置类。这个注解实际上是`@Configuration`注解的特殊形式。
* `@EnableAutoConfiguration`：启动Spring Boot的自动配置。这个注解会告诉Spring Boot自动配置他认为我们用到的组件。
* `@ComponentScan`：启动组件扫描。这样我们能够通过像`@Component`，`@Controller`，`@Service`，`@Repository`这样的注解声明其他类，Spring会自动发现他们并将他们注册为Spring 应用上下文的组件。



* `@Controller`： 该注解没有做太多事情，主要目的是让组件扫描将这个类识别为一个组件，并创建一个Spring应用上下文中的bean。

* `@AutoWired`

* `Spring MVC请求映射注解`

  | 注解              | 描述                                                |
  | ----------------- | --------------------------------------------------- |
  | `@RequestMapping` | 通用的请求处理,通常在类级别使用，以便于指定基本路径 |
  | `@GetMapping`     | 表示对某一uri，处理HTTP Get请求。                   |
  | `@PostMapping`    | 表示对某一uri，处理HTTP Post请求。                  |
  | `@PutMapping`     | 表示对某一uri，处理HTTP Put请求。                   |
  | `@DeleteMapping`  | 表示对某一uri，处理HTTP Delete请求。                |
  | `@PatchMapping`   | 表示对某一uri，处理HTTP PATCH请求。                 |



* `@RunWith`: 是JUnit的注解，他会提供一个测试运行器（runner）来指导JUnit如何运行测试。
* `@SpringBootTest`: 会告诉JUnit在启动测试的时候要添加上Spring Boot的功能。
* `@WebMvcTest`: Spring Boot提供的特殊测试注解，他会让这个测试在Spring MVC应用的上下文中执行。
* `@Test`:



* `Lombok`: 提供的注解

  * `@Data`：由lombok提供的，他会告诉lombok生成领域对象所有缺失的方法。同时还会生成以final属性作为参数的构造器。

  * `@Slf4j`：由lombok提供，在运行时，会自动生成一个SLF4J LOGGER。这个简单的注解和在泪中通过如下代码显示声明的效果是一样的

    ```java
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(XXX.class);
    ```

  * 









# 常用依赖

* `Spring Boot DevTools`: 为Spring 开发人员提供了一些便利的开发期工具。其中包括： 具体参考[Spring实战第五版1.3.5]()

  * 代码变更后会自动重启；
  * 当面向浏览器的资源（如模版，JavaScript，样式表）等发生变化时，会自动刷新浏览器
  * 自动禁用模板缓存
  * 如果使用H2数据库的话，内置了H2控制台

* `Spring boot starter web`: 

  <u>Spring MVC,也就是Spring web框架。**既可以来编写控制器类以处理web请求。** **Spring MVC还能用来创建REST API，以生成非HTML的输出。**</u>

  * 引入了Spring MVC的框架，以及嵌入式的tomcat，
  * **还引入了Spring的自动配置库**，当启动的时候，Spring Boot的自动配置将会探测到这些库，并自动完成如下功能
    * 在spring 应用上下文中配置bean以启动Spring MVC
    * 在spring 应用上下文中配置嵌入式的tomcat服务器

* `Spring boot starter thymeleaf`：

  * 引入thymeleaf和thymeleaf布局方言的依赖。
  * 配置thymeleaf试图解析器，以便于使用thymeleaf模版渲染Spring MVC视图。
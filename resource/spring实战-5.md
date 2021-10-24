# Spring实战-第五版

[toc]

* 初始化Spring应用

  生成的Spring应用是符合Maven或者Gradle项目结构的。其中源码放到`src/main/java` 中，测试代码放到`src/main/test` 中，而非Java的资源放到了`src/main/resouce`。

  * pom.xml: 这是Maven构建规范。

    * 在Spring应用中，可能存在一些dependencies 的artifact ID都有starter这个单词。Spring boot starter依赖的特别之处在于他们本身不包含代码，而是传递性地拉去其他的库。

    * spring 应用中可能存在Spring boot的插件，这个插件提供了一些重要的功能

      1. 他提供了一个Maven goal，允许我们使用Maven来运行应用。
      2. 他会确保依赖的所有库都会包含在可执行jar文件中，并且能够保证他们在运行时类路径下是可用的。
      3. 他会在jar中生成一个manifest文件，将引导类（就是spring boot应用程序的启动类）声明为可执行的jar的主类。

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













# 注解汇总

* `@Configuration` : 注解会告知Spring 这是一个配置类，会为Spring应用上下文提供bean。
* `@Bean`: 被@Bean修饰的方法，表明这些方法所返回的对象以bean的形式添加到Spring的应用上下文中（默认情况下，这些bean所对应的bean ID与定义他们的方法名称是相同的）。 **通常是在Spring不能自动装配时和@Configuration搭配使用。**
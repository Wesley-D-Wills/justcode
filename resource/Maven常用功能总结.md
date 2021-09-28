# Maven常用功能总结

[toc]

## 1. 简介

maven主要服务于基于Java平台的**项目构建**、**依赖管理**和**项目信息管理**。**<u>Maven是跨平台的。</u>**

* 项目构建

  除了编写源代码，程序员每天 都有相当一部分时间花在了编译、运行单元测试、生成文档、打包和部署等繁琐且不起眼的工作上，这就是构建。手动做这些工作成本太高。

  而**Maven用途之一就是服务于构建，它是异常强大的构建工具，能够自动化构建过程，从清理、编译、测试到生成报告，再到打包和部署。**

* 依赖管理

  在这个开源的年代里，几乎任何Java应用都会借用一些第三方的开源库，这些类库都可以通过依赖的方式引入到项目中来。随着依赖的增多，版本不一致、版本冲突、依赖臃肿等问题都会接踵而来。

  **Maven提供了一个优秀的解决方案，通过坐标系统准确定位每一个构件(artifact)**

* 项目信息管理

  **Maven能帮助程序员管理分散在项目中各个角落的项目信息，包括项目描述、开发者列表、版本控制系统地址、许可证、缺陷管理系统地址等**



## 2. 安装配置

* 安装以及环境配置略

* 安装目录分析

  ```
  bin  : 该目录中包含了mvn运行的脚本，这些脚本用来配置Java命令，准备好classpath和相关的Java系统属性，然后执行Java命令
  boot : 
  conf : 
  lib  : 
  LICENSE.txt
  NOTICE.txt
  README.txt
  ```

  

## 3. 使用入门

### 3.1 编写POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.test.mvnbool</groupId>
    <artifactId>hello-world</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>maven hello world project</name>
</project>
<!-- 
详解：
	1. 代码的第一行为XML头，制定了xml文档的版本和编码方式。
	2. project元素是所有pom.xml的根元素，它还声明了一些POM相关的命名空间及xsd元素，虽然这些属性不是必须的，但使用这些属性能让第三方工具（如IDE中的XML编译器）帮助我们快速编译POM
	3. modelVersion指定了当前POM模型的版本，对于Maven2 及 Maven 3来说，它只能是4.0.0
	4. groupId、artifactId和version定义了一个项目基本的坐标
		4.1 groupId 定义了项目属于哪个组，这个组往往和项目所在的组织或公司存在关联。
   		4.2 artifactId 定义了当前Maven项目在组中唯一的ID
		4.3 version 指定了当前项目的版本
	5. name元素声明了一个对于用户更为友好的项目名称
-->
```

### 3.2 编写主代码 && 测试代码 && 打包和运行

默认情况下，Maven假设项目主代码位于src/main/java目录。(**mvn clean compile**)

默认情况下，Maven项目中的测试代码目录是src/test/java。（**mvn clean test**）

打包： **mvn clean package**

本地开发的项目安装到本地仓库中： **mvn clean install**

### 3.3 使用Archetype生成项目骨架

Maven 3，简单地运行 **mvn archetype :generate**



## 4. 坐标和依赖

* Maven坐标元素包括

  以下五个元素中，`groupId`、`artifactId`、`version`是必须定义的， `packaging`是可选的（默认是`jar`），而`classifier`是不能直接定义的。

  * `groupId` : 定义当前Maven项目隶属的实际项目
  * `artifactId` ： 定义实际项目中的一个Maven项目(模块)，推荐的做法是使用实际项目名称作为artifactId的前缀
  * `version` ： 定义了Maven项目当前所处的版本
  * `packaging` ： 定义Maven项目的打包方式
  * `classifier` ： 该元素用来帮助定义构建输出的一些附属构建。附属构建与主构建对应，如主构件为xxx-1.0.0.jar，该项目可能还会通过使用一些插件生成如xxx-1.0.0-javadoc.jar、xxx-1.0.0-source.jar这样的一些舒服构建，其包含了Java文档和源代码。这时候javadoc 和 source就是这两个附属构建的classifier。这样附属构建也就拥有了自己唯一的坐标。

* 依赖的配置

  **根元素project 下的 dependencies 可以包含一个或者多个dependency元素，以声明一个或者多个项目依赖。**每个依赖可以包含的元素有：

  * `groupId`、`artifactId` 和 `version` ： 依赖的基本坐标，对于任何一个依赖来说，基本坐标是最重要的，Maven需要坐标才能找到需要的依赖。
  * `type` ： 依赖的类型，对应于项目坐标定义的packaging。大部分情况下，该元素不必声明，其默认值为jar。
  * `scope` ： 依赖的范围，[见 依赖范围](#scope)。如果没有指定，默认依赖范围为`compile`
  * `optional` ： 标记依赖是否可选，[见 可选依赖](#optional)
  * `exclusions` ： 用来排除传递性依赖，[见 排除依赖](#exclusions)

### 4.1 <span id="scope">依赖范围</span >

依赖范围就是用来控制依赖与这三种`classpath`(编译classpath、测试classpath、运行classpath)的关系，Maven有以下几种依赖范围：

* `compile` ：编译依赖范围。如果没有指定，就会默认使用该依赖范围。使用此依赖范围的Maven依赖，对于编译、测试、运行三种classpath都有效。
* `test`：测试依赖范围。使用此依赖范围的Maven依赖，只对于测试classpath有效，在编译主代码或者运行项目的使用时将无法使用此类依赖。
* `provided`：已提供依赖范围。使用此依赖范围的Maven依赖，对于编译和测试classpath有效，但在运行时无效。
* `runtime`：运行时依赖范围。使用此依赖范围的Maven依赖，对于测试和运行classpath有效，但在编译主代码时无效。
* `system`：系统依赖范围。该依赖与三种classpath的关系，和provided依赖范围完全一致。
* `import`：（<u>Maven2.0.9</u>及以上） 导入依赖范围。该依赖范围不会对三种classpath产生实际的影响。

| 依赖范围（Scope） | 对于编译classpath有效 | 对于测试classpath有效 | 对于运行时classpath有效 | 例子                            |
| ----------------- | --------------------- | --------------------- | ----------------------- | ------------------------------- |
| compile           | Y                     | Y                     | Y                       | spring-core                     |
| test              | —                     | Y                     | —                       | JUnit                           |
| provided          | Y                     | Y                     | —                       | servlet-api                     |
| runtime           | —                     | Y                     | Y                       | JDBC驱动实现                    |
| system            | Y                     | Y                     | —                       | 本地的，Maven仓库之外的类库文件 |

### 4.2 可传递性依赖

<u>见Maven实战5.6.2</u>

### 4.3 依赖调解

1. 第一原则： 路径最近者优先。例如：A->B->C->X(1.0)、A->D->X(2.0)。选择X(2.0)因为它的路径长度为2，另一个路径长度为3。
2. 第二原则： 第一声明者优先，在依赖路径长度相等的前提下，在POM中依赖声明的顺序决定了谁会被解析使用，顺序最靠前的那个依赖优胜。

### 4.4 <span id="optional">可选依赖</span>

**可选依赖，不进行传递**

### 4.5 <span id="exclusions">排除依赖</span>

传递性依赖会给项目隐式地引入很多依赖，这极大地简化了项目依赖的管理，但是有些时候这种特性也会带来问题。例如当前项目有一个第三方依赖，而这个第三方依赖由于某些原因依赖了另外一个类库的SNAPSHOT版本，那么这个SNAPSHOT就会成为当前项目的传递性依赖，而SNAPSHOT的不稳定性会直接影响到当前的项目，这时就需要排除掉该SNAPSHOT，并且在当前项目中声明该类库的某个正式发布的版本。 还有一些情况，你可能也想要替换某个传递性依赖。

代码中可以使用`exclusions`元素声明排除依赖，`exclusions`可以包含一个或者多个`exclusion`子元素，因此可以排除一个或者多个传递性依赖。**需要注意的是，声明`exclusion`的时候只需要`groupId`和`artifactId`，而不需要`version`元素，这是因为只需要`groupId`和`artifactId`就能唯一定位依赖图中的某个依赖。**

### 4.6 归类依赖

当有一些依赖来自同一个项目的不同模块，这些模块的版本相同，可以在pom文件中 定义版本，并且在dependency声明中应用这一版本。

**这里简单用到了Maven属性，首先使用`properties`元素定义Maven属性，该例中定义了一个 xxx.version 子元素，其值为1.0.0。 有了这个属性定义之后，Maven运行的时候会将POM中所有的${xxx.version}替换1.0.0。也就是说，可以使用美元符号和大括弧环绕的方式应用Maven属性。**

```xml
<project>
	<modelVersion>4.0.0</modelVersion>
    <groupId></groupId>
    <artifactId></artifactId>
    <version></version>
    
    <properties>
    	<xxx.version>1.0.0</xxx.version>
    </properties>
    
    <dependencies>
    	<dependency>
        	<groupId></groupId>
            <artifactId></artifactId>
            <version>${xxx.version}</version>
        </dependency>
    	<dependency>
        	<groupId></groupId>
            <artifactId></artifactId>
            <version>${xxx.version}</version>
        </dependency>
    </dependencies>
</project>
```



### 4.7 优化依赖

通常查看当前项目已解析依赖使用命令： `mvn dependency:list` （冒号前面是插件前缀，冒号后面是插件的目标）

查看当前项目的依赖树：`mvn dependency:tree`

帮助分析当前项目的依赖： `mvn dependency:analyze`, 【**注意**】dependency:analyze只会分析编译主代码和测试代码需要用到的依赖，一些执行测试和运行时需要的依赖它就发现不了。



## 5. 仓库

* 仓库的布局： `groupId/artifactId/version/artifactId-version[-classfier].packaging`

* 仓库的分类：

  **当Maven根据坐标寻找构件的时候，它首先会查看本地仓库，如果本地仓库存在此构件，则会直接使用；如果本地仓库不存在此构件，或者需要查看是否有更新的构建版本，Maven就会去远程仓库查找，发现需要的构件之后，下载到本地仓库再使用。如果本地仓库和远程仓库都没有需要的构件，Maven就会报错。**

  * 本地仓库 

  * 远程仓库

    * 远程仓库配置

      > 可以在POM中配置远程仓库，格式如下：
      >
      > ```xml
      > <project>
      > 	<repositories>
      >  	<repository>
      >      	<id>jboss</id> <!-- 任何一个仓库声明的id必须是唯一的，尤其需要注意的是，Maven自带的中央仓库使用的id为central，如果其他的仓库声明也使用该id，就会覆盖中央仓库的配置。 -->
      >          <name>JBoss Repository</name>
      >          <url>http://repository.jboss.com/maven2/</url>
      >          <releases>					<!-- releases 和 snapshots 用来控制Maven对于发布版构件和快照版构件的下载 -->
      >          	<enabled>true</enabled>	<!-- enabled ： true表示支持发布版或者快照版构件的下载，false表示不支持 -->
      >          	<updatePolicy>daily</updatePolicy>	<!-- updatePolicy用来配置Maven从远程仓库的更新频率,默认为daily（每天），其他可用值，never（从不检查更新），always（每次构建都检查更新），interval：X(每隔X分钟检查一次更新) -->
      >              <checksumPolicy>ignore</checksumPolicy>	<!-- 用来配置Maven检查检验和文件的策略。默认值为warn（Maven会在执行构建时输出告警信息），其他可用值：fail（Maven遇到校验和错误就让构建失败），ignore（使Maven完全忽略校验和错误） -->
      >          </releases>
      >          <snapshots>
      >          	<enabled>false</enabled>
      >              <updatePolicy>daily</updatePolicy>
      >              <checksumPolicy>ignore</checksumPolicy>
      >          </snapshots>
      >          <layout>default</layout>	<!-- default表示仓库布局是Maven2及Maven3的默认布局，而不是Maven1的布局 -->
      >      </repository>
      >  </repositories>
      > </project>
      > ```

      

    * 远程仓库认证

      > 大部分远程仓库无需认证就可以访问，但有时候出于安全方面的考虑，我们需要提供认证信息才能访问一些远程仓库。**配置认证信息和配置仓库信息不同，仓库信息可以直接配置在POM文件中，但是认证信息必须配置在setting.xml文件中。这是因为POM往往是被提交到代码仓库中供所有成员访问的，而setting.xml一般只放在本机。因此，在setting.xml中配置认证信息更为安全。**格式如下：
      >
      > ```xml
      > <settings>
      >  ...
      > 	<servers>
      >  	<server>
      >      	<id>my-proj</id>				<!-- 该id元素，必须与POM中需要认证的repository元素的id完全一致。换句话说，正是这个id将认证信息与仓库配置联系在了一起。 -->
      >          <username>repo-user</username>
      >          <password>repo-pwd</password>
      >      </server>
      >  </servers>
      >  ...
      > </settings>
      > ```

      

    * 部署到远程仓库

      > Maven除了能对项目进行编译、测试、打包之外，还能将项目生成的构建部署到仓库中。首先需要编辑项目中的POM文件。配置`distributionManagement`元素。格式如下：
      >
      > <font color=red>**【注意】：执行 mvn clean deploy，Maven就会将项目构建输出的构建部署到配置对应的远程仓库，如果项目当前的版本是快照版本，则部署到快照版本仓库地址，否则就部署到发布版本仓库地址。**</font>
      >
      > ```xml
      > <!-- 
      > 	distributionManagement包含repository 和 snapshotRepository 子元素，前者表示发布版本构件的仓库，后者表示快照版本地仓库。 
      > 	这两个元素都需要包含id、name、URL，id表示远程仓库的唯一标识，name是为了方便人阅读，关键的url表示该仓库的地址。
      > 
      > 	此外，往远程仓库部署构件的时候，往往需要认证。配置认证的方式参考 远程仓库认证（上一小节），简而言之，就是需要在setting.xml中创建一个server元素，其id与仓库的id匹配，并配置正确的认证信息。不论从远程仓库下载构件，还是部署构件至远程仓库，当需要认证的时候，配置的方式是一样的。
      > 	配置正确后，执行 mvn clean deploy, Maven就会将项目构建输出的构建部署到配置对应的远程仓库，如果项目当前的版本是快照版本，则部署到快照版本仓库地址，否则就部署到发布版本仓库地址。
      > -->
      > <project>
      > 	...
      >  <distributionManagement>
      >  	<repository>
      >      	<id>proj-releases</id>
      >          <name>proj relase repository</name>
      >          <url>http://192.168.1.100/content/repositories/proj-releases</url>
      >      </repository>
      >      <snapshotRepository>
      >      	<id>proj-snapshots</id>
      >          <name>proj snapshots repository</name>
      >          <url>http://192.168.1.100/content/repositories/proj-snapshots</url>
      >      </snapshotRepository>
      >  </distributionManagement>
      >  ...
      > </project>
      > ```

* 快照版本

  发布Maven的快照版本时（如：`2.1-SNAPSHOT`），Maven会自动为构件打上时间戳(如：`2.1-20210928.142140-13`，就表示2021年9月28日14点21分40秒的第13次快照)。有了该时间戳，Maven就能随时找到仓库中该构件`2.1-SNAPSHOT`版本最新的文件。这时，maven构件的过程中，就会检查是否有最新的版本的依赖。

  此外，当项目经过完善的测试后需要发布的时候，就应该将快照版本更改为发布版本。例如，将`2.1-SNAPSHOT`更改为`2.1`，表示该版本已经稳定，且只对应唯一的构件。相比之下，`2.1-SNAPSHOT`往往对应了大量的带有不同时间戳的构件，这也决定了其不稳定性。

  【注意】如果没有快照版本，反复重复部署`2.1`版本，供别人下载。虽然能够保证仓库中的构件是最新的，但是对于Maven来说，同样的版本和同样的坐标就意味着同样的构件。因此，如果本地仓库中已经包含了之前的`2.1`版本构件，Maven就不会再对照远程仓库进行更新。除非每次执行Maven命令之前，清楚本地仓库，但是这种要求手动干预的做法显然也是不可取的。因此建议不稳定版本使用 `2.1-SNAPSHOT`快照版本的形式。



* 镜像

  如果仓库X 可以提供 Y存储的所有内容，那么就可以认为X 是 Y 的一个镜像。换句话说，任何一个可以从仓库Y获得的构件，能够从他的镜像中获取。 例如，`http://maven.net.cn/content/groups/public`是中央仓库`http://repo1.maven.org/maven2`在中国的镜像，由于地理位置的因素，该镜像往往能够提供比中央仓库更快的服务。因此可以配置Maven使用该镜像来替代中央仓库。编辑`setting.xml` 

  > 配置Maven的镜像
  >
  > ```xml
  > <settings>
  > 	<mirrors>
  >  	<mirror>
  >      	<id>maven.net.cn</id>
  >          <name>ont of the central mirrors in china</name>
  >          <url>http://maven.net.cn/content/groups/public</url>
  >          <mirrorOf>central</mirrorOf>
  >      </mirror>
  >  </mirrors>
  > </settings>
  > <!-- 
  > 	该例中，<mirrorOf>的值为central，表示该配置为中央仓库的镜像，任何对于中央仓库的请求都会转至该镜像，用户也可以使用同样的方法配置其他仓库的镜像。
  > 	id、name、url三个元素与一般仓库配置无异，表示该镜像仓库的唯一标识符、名称及地址。类似的，如果该镜像需要认证，也可以基于该id配置仓库认证。
  > 
  > 	1. <mirrorOf>*</mirrorOf> ： 匹配所有的远程仓库
  > 	2. <mirrorOf>external:*</mirrorOf> ： 匹配所有的远程仓库，使用localhost的除外，使用file://协议的除外。也就是说，匹配所有不在本机上的远程仓库
  > 	3. <mirrorOf>repo1,repo2</mirrorOf> ： 匹配仓库repo1和repo2，使用逗号分隔多个远程仓库
  > 	4. <mirrorOf>*,!repo1</mirrorOf> ： 匹配所有远程仓库，repo1除外，使用感叹号将仓库从匹配中排除
  > -->
  > ```

  

## 6. 声明周期和插件

除了坐标、依赖和仓库之外，Maven另外两个核心概念就是生命周期和插件。在有关Maven的日常使用中，命令行的输入往往就对应了生命周期，如mvn package就表示执行默认生命周期阶段package。Maven生命周期是抽象的，其实际行为都由插件来完成，如package阶段的任务可能就会由maven-jar-plugin完成。生命周期和插件两者协同工作，密不可分。

### 6.1 何为生命周期

Maven的生命周期就是为了对所有的构建过程进行抽象和统一。这个生命周期包含了项目的**清理、初始化、编译、测试、打包、集成测试、验证、部署、和站点生成等**几乎所有构建步骤。

Maven生命周期是抽象的，这意味着生命周期本身不做任何实际的工作，在Maven的设计中，实际的任务（如编译源代码）都交由插件来完成。

* Maven拥有三套相互独立的生命周期 ：`clean`、`default`和`site`

  每个生命周期包含一些阶段，这些阶段是有顺序的，并且后面的阶段依赖于前面的阶段，用户和Maven最直接的交互方式就是调用这些生命周期阶段。

  * `clean 生命周期`的目的是清理项目
  * `default 生命周期` 的目的是构建项目
  * `site生命周期`的目的是 建立项目站点

### 6.2 插件的绑定

* Maven生命周期与插件相互绑定 —内置绑定，详情见[Maven实战 7.4章节](无)

* Maven生命周期与插件相互绑定 —自定义绑定[Maven实战 7.4.2章节](无)

  > 除了内置绑定之外，用户能够自己选择将某个插件目标绑定到生命周期的某个**阶段(phase)**上。
  >
  > 一个常见的例子就是创建项目的源码jar包，内置的插件绑定关系中并没有涉及这一任务，需要用户自行配置。maven-source-plugin可以帮助我们完成任务，他的jar-no-fork目标能够将项目的主代码打包成jar文件，可以将其绑定到default生命周期的verify阶段上，在执行完集成测试后和安装构建之前创建源码jar包。如下：
  >
  > ```xml
  > <build>
  > 	<plugins>
  >  	<plugin>
  >      	<groupId>org.apache.maven.plugins</groupId>
  >          <artifactId>maven-source-plugin</artifactId>
  >          <version>2.1.1</version>
  >          <executions>
  >          	<execution>
  >              	<id>attach-sources</id>
  >                  <phase>verify</phase>
  >                  <goals>
  >                  	<goal>jar-bo-fork</goal>
  >                  </goals>
  >              </execution>
  >          </executions>
  >      </plugin>
  >  </plugins>
  > </build>
  > ```
  >
  > 

### 6.3 插件配置

完成了插件和生命周期的绑定之后，用户还可以配置插件的目标参数，进一步调整插件目标所执行的任务，以满足项目的需求。用户可以通过命令行和POM配置等方式来配置这些参数。

* 命令行插件配置

  用户可以在Maven命令中使用`-D`参数，并伴随一个`参数键=参数值`的形式，来配置插件目标的参数。

  > 例如：
  >
  > maven-surefire-plugin提供了一个maven.test.skip参数，当其值为true的时候，就会跳过执行测试。于是，在运行命令的时候，加上如下-D参数就能跳过测试：
  >
  > ```shell
  > $ mvn install -Dmaven.test.skip = true # 参数-D是Java自带的，启功能是通过命令行设置一个Java系统属性
  > ```

* POM中插件全局配置

  并不是所有的插件都适合从命令行配置，有些参数的值从项目创建到项目发布都不会改变，或者说很少改变，对于这种情况，在POM文件中一次性配置就显然比重复再命令行输入要方便。

  用户可以在声明插件的时候，对此插件进行一个全局的配置。也就是说，所有的该基于该插件目标的任务，都会使用这些配置。

  > 例如：
  >
  > 我们通常会需要配置maven-compiler-plugin告诉他编译Java1.5版本的源文件，生成JVM1.5兼容的字节码文件。
  >
  > 在[Maven实战3.3章节中]()，介绍由于历史原因，Maven的核心插件之一——compile插件默认只支持编译Java1.3，因此需要配置该插件使其支持Java5 或者8。（Java 5 开始引入的注解。如果有注解Java 3则compile失败）
  >
  > ```xml
  > <!-- 在配置插件的过程中，如果插件是Maven官方的插件，可以省略groupId的配置（不推荐） -->
  > <build>
  >   <plugins>
  >     <plugin>     	
  >       <groupId>org.apache.maven.plugins</groupId>         
  >       <artifactId>maven-compiler-plugin</artifactId>         
  >       <version>2.1</version>         
  >       <configuration>         	
  >         <source>1.5</source>             
  >         <target>1.5</target>         
  >       </configuration>     
  >     </plugin> 
  >   </plugins>
  > </build>
  > ```
  >
  > 

* POM中插件任务配置

  除了为插件配置全局的参数，用户还可以为某个插件任务配置特性特定的参数。

  > 例如：
  >
  > 以Maven-antrun-plugin为例，他有一个目标，可以用来在Maven中调用Ant任务。用户将maven-antrun-plugin:run绑定到多个生命周期阶段上，再加上不同的配置，就可以让Maven在不同的生命阶段执行不同的任务。如下：
  >
  > ```xml
  > <build>	
  >   <plugins> 	
  >     <plugin>     	
  >       <groupId>org.apache.maven.plugins</groupId>         
  >       <artifactId>maven-antrun-plugin</artifactId>         
  >       <version>1.3</version>         
  >       <executions>         	
  >         <execution>             	
  >           <id>ant-validate</id>                 
  >           <phase>validate</phase>                 
  >           <goals>                 	
  >             <goal>run</goal>                 
  >           </goals>                 
  >           <configuration>                 	
  >             <tasks>                     	
  >               <echo>I'm bound to validate phase.</echo>                     
  >             </tasks>                 
  >           </configuration>             
  >         </execution>             
  >         <execution>             	
  >           <id>ant-verify</id>                 
  >           <phase>verify</phase>                 
  >           <goals>                 	
  >             <goal>run</goal>                 
  >           </goals>                 
  >           <configuration>                 	
  >             <tasks>                     	
  >               <echo>I'm bound to verify phase.</echo>                     
  >             </tasks>                 
  >           </configuration>             
  >         </execution>         
  >       </executions>     
  >     </plugin> 
  >   </plugins>
  > </build>
  > <!-- 	
  > 上述代码片段中，首先，maven-antrun-plugin:run与validate阶段绑定，从而构成一个id为ant-validate的任务。	
  > 插件全局配置中的configuration元素位于plugin元素下面，而这里的configuration元素位于execution元素下，表示这是特定任务的配置，而非插件整体的配置。	
  > 
  > ant-validate任务配置了一个echo ant任务，向命令行输出一段文字。第二个任务id为ant-verify，它绑定了verify阶段，同样输出一段文字到命令行，告诉该任务绑定到了verify阶段。
  > -->
  > ```

### 6.4 插件仓库

Maven会区别对待依赖的远程仓库 与 插件的远程仓库。

之前配置的远程仓库，只对一般的依赖有效果。当Maven需要的依赖在本地仓库不存在时，他会去所配置的远程仓库查找，**可是**当Maven需要的插件在本地仓库不存在时，他就不会去这些远程仓库查找。

> 不同于repositories 及其repository子元素，插件的远程仓库使用 pluginRepositories和 pluginRepository配置，如下：
>
> ```xml
> <pluginRepositories>	
>   <pluginRepository> 	
>     <id>central</id>     
>     <name>Maven plugin repository</name>     
>     <url>http://repo1.maven.org/maven2</url>     
>     <layout>default</layout>     
>     <snapshots>     	
>       <enabled>false</enabled>     
>     </snapshots>     
>     <releases>     	
>       <updatePolicy>never</updatePolicy>     
>     </releases> 
>   </pluginRepository>
> </pluginRepositories>
> ```




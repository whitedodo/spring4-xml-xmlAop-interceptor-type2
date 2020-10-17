# Spring Framework 4.2.4 Releases에서 MyBatis, HikariCP, XML 설정방식(XML Mapper) 회원 AOP, 게시판 구현(방식1) applicationContext.xml
(In Spring Framework 4.2.4 Releases, MyBatis, HikariCP, XML configuration method (XML Mapper) member AOP, bulletin board implementation (method 2) applicationContext.xml

### 기본 정보(Information)
##### 제작일자(Create date): 2020-10-17
##### 작성언어(Write language): Java
##### IDE: Eclipse IDE with Spring Tool Suite 4-4.7.2.
##### 제작자(Author): 도도(Dodo) / rabbit.white at daum dot net
##### 프레임워크(Framework): 
##### 라이브러리(Library): 
##### 1. Apache-Maven 3.6.3/1.16.0.2.20200610-1735 (https://maven.apache.org/)
##### (소프트웨어 프로젝트 관리 및 이해 도구)
##### 2. Spring Framework 4.2.4 RELEASES
##### 3. Spring-test 4.2.4 RELEASES
##### 4. https://mvnrepository.com/artifact/com.zaxxer/HikariCP
##### - HikariCP 3.4.5
##### 5. https://mvnrepository.com/artifact/org.mybatis/mybatis
##### - MyBatis 3.5.6
##### 6. https://mvnrepository.com/artifact/org.mybatis/mybatis-spring
##### - myBatis-spring
##### 7. https://mvnrepository.com/artifact/javax.servlet/jstl
##### 자바 버전(Java-Version): OpenJDK-14.0.2 (https://openjdk.java.net/) // Version 1.8
##### 8. amazon-corretto-8.265.01.1-windows-x64-jdk.zip
##### 9. Spring-TX
##### - https://mvnrepository.com/artifact/org.springframework/spring-tx
##### 10. Spring-AOP
##### 11. Aspectjweaver, Aspectj
##### 12. json-simple
##### 13. commons-fileupload
##### 14. commons-io

### 1. 소개(Description)
##### 1. 해당 프로젝트는 MyBatis, HikariCP, XML 설정방식(XML Mapper) 회원 AOP, 게시판 구현(방식1) resource 폴더의 applicationContext.xml으로 구현하였다.
#####    (The project was implemented as MyBatis, HikariCP, XML setting method (XML Mapper) member AOP, bulletin board implementation (method 1) applicationContext.xml in the resource folder.)
##### 2. 다중 게시판이 적용된 Spring Framework, MyBatis 기반으로 작성하였다.
#####    (It was written based on Spring Framework, MyBatis, where multiple bulletin boards were applied.)
##### 3. 다중 파일 업로드 기능을 추가하였다.
#####    (Added multi-file upload function.)
##### 4. JSON-Simple을 이용하여 json을 구현하였다.
#####    (Json was implemented using JSON-Simple.)
##### 5. 스프링 인터셉터를 활용하여 맴버십 보안 기능을 적용하였다.
#####    (Membership security function was applied using Spring Interceptor.)
##### 6. 스프링 인터셉터로 preHandler, postHandler로 페이지를 관리하였다.
#####    (Pages were managed with preHandler and postHandler with Spring Interceptor.)

### 2. 시연(Practice)
##### 1. 

### 3. 참고자료(References)
##### 1. MyBatis - 마이바티스 3 | 매퍼 XML 파일, https://mybatis.org/mybatis-3/ko/sqlmap-xml.html, Accessed by 2020-10-12, Last Modified 2020-07-10.

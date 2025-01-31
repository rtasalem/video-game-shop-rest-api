# RESTful API for a Video Game Shop
### Acknowledgements 
Thank you to the IT consultants (Prem, Etisham, and Madihah) who asked me to plan and deliver this tutorial to help their understanding of Java and RESTful APIs.
## Description
A simple RESTful API I built during a demo & tutorial for IT consultants at FDM Group with various backgrounds in TechOps, data engineering, and cloud engineering. The demo served to teach IT consultants more complex areas of software development to understand how RESTful APIs are built and function. Topics covered included RESTful architecture (controllers, services, repositories, models, and exceptions), HTTP requests (GET, POST, PUT, and DELETE), and testing HTTP requests in Postman. The API utilises CRUD operations to add, retrieve, update, and delete video games from a H2 (in-memory) database.
## Libraries & Technologies
The project was set up as a Maven project using [Spring Initializr](https://start.spring.io/). The following dependencies were added into the project during initial set up:
- **Spring Boot DevTools:** Makes the development process more efficient by automatically restarting the applications whenever code changes are made.
- **Spring Web:** Used to handle HTTP requests.
- **Spring Reactive Web:** An extension of the Spring Framework to allow for increased scalability and responsiveness when building web applications.
- **Spring Data JPA:** Helps to simplify interactions with relational databases and enables entities to be defined/modeled through Java classes (hence the VideoGame class in the model package).
- **H2 Database:** H2 (or Hibernate) is an in-memory database that makes it easier to map Java objects to tables.
- **Validation:** Used to implement data integrity. Validation sets standards for user inputs to ensure they meet specific criteria (e.g. in the VideoGame entity in the model package, some columns are marked with @NotBlank and @Size annotations to specificy that a column should not be left null and what minimun and maximum number of characters that a String should have).

Listed below are additional libraries/frameworks/technologies used to complete this project:
- **Swagger Documentation:** Used to document this API, specifically at the controller layer.
- **Postman:** An external application used to test the CRUD operations and HTTP requests that make up this REST API.
- **SLF4J (Simple Logging Facade for Java):** Info logs are used in the controller and service layers to declare entry and exit into the methods found in these layers.
- **JUnit:** Tests have been written for both the controller and service layers of this API using the JUnit testing framework.
- **Mockito:** Another testing framework also used in the tests created for the API's controller and service layers. This allows for the creation of mock objects to isolate the code that is being tested.
## Documentation
Swagger documentation has been implemented for the controller of the API. After importing the project as a Maven project and running as a Spring Boot Application, enter the following URL into the browser: http://localhost:8088/swagger-ui/index.html to read and test the documentation.
## Learning Milestones
Learning to properly implement Swagger documentation for APIs was an essential learning experience. Not only are Swagger docs automated, but also allow for sophisticated presentation of the requests and responses that an API is capable of. This eliminates the need for developers and potentially even clients from having to look through the source code to understand what an API is able to achieve. 
<br/><br/>
Another key learning curve from this project was teaching. As this API was developed as part of a live demo to colleagues, I wanted my friends to get the most from the demo that I could offer. Overall, delivering a live demo was an enjoyable experience that helped to further consolidate my own knowledge of REST APIs. 
## Getting Started
After cloning the repository and importing the API as a Maven project, it's recommended to use another client such as Postman to see how the functionality of the API itself performs. 

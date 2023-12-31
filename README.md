<h1 style="font-size: 42px;">COOK RECIPE APP</h1>

![RECIPE!](images/img_1.png)
# Summary
CookRecipeApp is a REST application that allows you to create new recipes, update existing ones, delete or retrieve recipes by ID, create new versions of a specific recipe, get a list of all recipes by description and creation date, and retrieve all versions of a specific recipe. This service also supports security: authorization and authentication, division into Customer and Manager roles and, accordingly, different access to endpoints depending on the user's role.

# Endpoints
<h1 style="font-size: 14px;">Not a registered user endpoints:</h1>

- POST: /register - register a new user (with the CUSTOMER role by default).
- POST: /login - login to your user account.

<h1 style="font-size: 14px;">MANAGER and CUSTOMER endpoints:</h1>

- GET: /recipes{id} - get a recipe by id.
- GET: /recipes/versions/{id} - retrieve all versions of a specific recipe.
- GET: /recipes/by-date-and-description - retrieve all recipes by description and creation date.

<h1 style="font-size: 14px;">MANAGER endpoints:</h1>

- POST: /recipes - create a new recipe.
- POST: /recipes/{parentId} - create a new recipe based on the previous one.
- DEL: /recipes{id} - delete a recipe by id.
- PUT: /recipes{id} - update a recipe and create a new version of it.
- PATH: /users{id} - update the user role (CUSTOMER or MANAGER).

# Project structure
- src/main/java: contains all the source code for the application.
- src/main/resources: contains configuration files and resources.
- checkstyle/checkstyle.xml - is a configuration file for the checkstyle tool, which is used to check the code style. It contains settings for various checkstyle modules that perform various code checks for compliance with style standards.
- pom.xml - used to configure and create a Maven project, add the necessary dependencies.

# Technologies used
- JDK 17
- SpringBoot 3.1.3
- MySQL 8.0.33
- Hibernate 6.2.7.Final
- Swagger 2.1.0
- Maven 4.0.0
- SpringSecurity 6.1.2
- Mapstruct 1.4.2

# How to run the application
In order to launch this project, you need to take the following steps:
1. Clone this project from GitHub to your local machine.
2. Install the following software:
- MySQL version 8.0 or higher;
- IntelliJ IDEA (IDE) to run the application.
- Install Postman for sending requests, or you can use Swagger UI and you need follow this link to test the application - localhost:8080/swagger-ui.html
3. Open the project in IntelliJ IDEA.
4. Configure the database connection settings in the application.properties file.
5. Build the project using Maven: mvn clean package.
6. Once the configuration is complete, click the "Run" button in IntelliJ IDEA to start the application. You can choose either normal mode or debug mode.
7. If all the steps have been followed correctly, the server will start successfully.
8. Use Postman or a web browser to interact with the endpoints and test the application.
   Please follow these instructions carefully to launch the project.

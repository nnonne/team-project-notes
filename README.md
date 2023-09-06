# Project Initialization and Setup Guide

This guide provides instructions on how to initialize and set up a Spring Boot project following best practices for code organization and code conventions. It also includes details on how to run the program with different profiles for local development and production deployment.


## Project Initialization

1. Start by initializing your Spring Boot project using the [Spring Initializr](https://start.spring.io/). Choose the latest LTS version of Java.

2. Organize your code following the "package-as-feature" approach. 

3. Ensure that the code is structured logically within each feature package to maintain a clean and organized project structure.

---

## Code Formatting

Adhere to the [Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) to format your code correctly. Ensure that the code you push to the `main` branch is clean and free of unnecessary comments or warnings.

IntelliJ IDEA should not display any warnings or errors in your code.

---

## Running the Program

To run the program, follow these steps:

1. The program runs on port 9999.

2. Define two profiles for your application: `default` and `prod`. You can read more about Spring profiles [here](https://www.baeldung.com/spring-profiles).

3. The `default` profile runs with an in-memory H2 database, while the `prod` profile uses PostgreSQL. Configure the database connection details (e.g., database URL, username, password) using environment variables. For example:

    ```properties
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    spring.datasource.url=${DB_URL}
    ```

   Make sure to document these environment variables in your `readme.md` file.

4. With this structure, you can run the program locally and test it in a web browser at [http://localhost:9999](http://localhost:9999). The `default` profile will use the in-memory H2 database for local development.

5. For production deployment, use the `prod` profile. Configure the production database connection details using the appropriate environment variables for your production environment.

---

![Project Structure](images/project-structure.png)

*Example project structure for reference.*

---

That's it! You now have a structured Spring Boot project with organized code, proper formatting, and different profiles for local and production deployment. Refer to this `readme.md` file for instructions on setting up and running the project.

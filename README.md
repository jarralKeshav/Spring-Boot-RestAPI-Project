Here is a minimal Spring Boot template with H2 database, JWT authentication, ReactJS frontend, and Swagger UI for API documentation:
1. Project Structure Overview

    1. Backend: Spring Boot (Java 17)
    2. Frontend: ReactJS
    3. Database: H2 (in-memory)
    4. Authentication: JWT Token (Spring Security)
    5. API Testing: Swagger UI

2. Installation and Setup Guide
Backend (Spring Boot)
2.1 Install Java & Maven:

Ensure that Java 17 and Maven are installed on your system.

bash:

    java -version  # Ensure Java 17 is installed
    mvn -version   # Ensure Maven is installed

2.2 Clone the repository:

bash:

    git clone https://github.com/your-username/spring-boot-h2-jwt-template.git
    cd spring-boot-h2-jwt-template

2.3 Configure Maven:

If necessary, configure Maven settings to resolve dependencies:

bash:

    mvn clean install

2.4 Run the Spring Boot application:

bash:

    mvn spring-boot:run

Frontend (React)
2.5 Install Node.js and npm:

Node version used: v20.16.0

Ensure Node.js is installed.

bash:

    node -v  # Check if Node.js is installed
    v20.16.0
    npm -v   # Check if npm is installed
    10.8.1

2.6 Navigate to the ReactJS frontend folder:

bash:

    cd frontend
    npm install

2.7 Run the React app:

bash:

    npm start

3. Project Configuration
3.1. Backend: Spring Boot Application Configuration

        src/main/resources/application.properties

application.properties:


        spring.application.name=SpringRestDemo


        server.port=8080

        spring.datasource.url=jdbc:h2:file:./db/<Database Name>
        spring.datasource.driver-class-name=org.h2.Driver
        spring.datasource.username=<Database username>
        spring.datasource.password=<Database password>
        #spring.jpa.database-platform=org.hibernate.dialect.H2Dialect


        spring.h2.console.enabled=true
        spring.h2.console.path=/db-console
        spring.h2.console.settings.web-allow-others=false

        spring.jpa.hibernate.ddl-auto=create
        spring.jpa.open-in-view=false

        spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true



        #loggin settings

        logging.level.com.bishnah.springrestdemo=debug
        logging.pattern.console=%d [%level] %c{1.} {%t} %m%n
        logging.file.name=appLog.log
        logging.pattern.file=%d [%level] %c{1.} {%t} %m%n


        #File Settings

        spring.servlet.multipart.max-file-size=10MB
        spring.servlet.multipart.max-request-size=10MB
        spring.mvc.static-path-pattern=/resources/static/**


3.2. Swagger UI Configuration

    src/main/java/com/com/bishnah/springrestdemo/config/SwaggerConfig.java



SwaggerConfig.Java

    @Configuration
    @OpenAPIDefinition(info = @Info(
            title = "Demo Api",
            version = "Version 1.0",
            contact = @Contact(
                    name = "<YOUR-NAME>" , email = "<YOUR-EMAIL>", url = "<URL (if available)>"
            ),
            license = @License(
                    name = "Apache 2.0" ,url = "https://www.apache.org/licenses/LICENSE-2.0"
            ),
            termsOfService = "https://www.apache.org/licenses/LICENSE-2.0.txt",
            description = "Spring Boot RestFul API Demo by Keshav Jrall"
    )
    )




    public class SwaggerConfig {
    }


3.3.

   Run spring boot backend

    Port: 8080
    8080 will be used to run swaggerUI

Swagger UI will be available at:

    http://localhost:8080/swagger-ui/index.html#/

Screenshots:(API)
![Screenshot_20240910_210123](https://github.com/user-attachments/assets/a6312351-2692-48a1-bd94-03c3d42f642d)
![Screenshot_20240910_210222](https://github.com/user-attachments/assets/cf4d7ff6-2437-421e-bdc2-cd7717fea947)

Schemas:

![Screenshot_20240910_210251](https://github.com/user-attachments/assets/0c6af689-6a44-4c0b-89e2-85a04b00a8d4)

FRONTEND:
Login & Logout:

![Screenshot_20240910_210500](https://github.com/user-attachments/assets/2f90e7ad-4ecb-457b-84c7-73a670f8154c)
![Screenshot_20240910_210511](https://github.com/user-attachments/assets/b8a4d220-c8bb-44be-a963-e7540468b9cd)

Add Album:

![Screenshot_20240910_210548](https://github.com/user-attachments/assets/7df7f977-a22f-4b45-b46d-9f30f61f8ec5)

Sample Albums:

![Screenshot_20240910_210636](https://github.com/user-attachments/assets/d3a779fc-8f58-49d4-afbe-a0e5f5a0141a)

Photo Upload:


![Screenshot_20240910_210709](https://github.com/user-attachments/assets/ca2adab3-eb3c-4727-9487-b2a7d6c8db57)

![Screenshot_20240910_210758](https://github.com/user-attachments/assets/d28f36fa-246f-48b8-ba6d-38e92a9be58f)



4. Minimal Authentication Flow

    Users will authenticate using JWT.
    Only authenticated users can create, update, delete, or view their albums and photos.
    Use JWT token in the Authorization header to authorize requests.



4.1. Enable JWT in Spring Security

src/main/java/com/example/security/SecurityConfig.java

java

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2
                                    .jwt(Customizer
                                    .withDefaults()))
                                    .sessionManagement(session -> session
                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));;
        }
    }

5. CRUD Endpoints

      Albums:
6.
        /albums
        /albums/{album_id}
        /albums/{album_id}/upload-photos
        albums/{album_id}/photos/{photo_id}/download-photo
        /albums/add
        /albums/{album_id}/update
        /albums/{album_id}/photos/{photo_id}/update
        /albums/{album_id}/delete
        /albums/{album_id}/photos/{photo_id}/delete

    Swagger can be used to test the endpoints. Ensure to authenticate first and pass the JWT token for protected routes.

6. Running the Project

    Start the Spring Boot backend:

    bash

        mvn spring-boot:run

Start the React frontend:

bash

    npm start

You can access the Swagger UI at http://localhost:8080/swagger-ui/index.html#/

7. Extra Considerations

    Exception Handling: Create a global exception handler for better API error responses.
    CORS Configuration: Add CORS support for the React frontend.
    File Upload Handling: Use a service for handling file uploads to link photos with albums.

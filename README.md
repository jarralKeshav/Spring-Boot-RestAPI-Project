Here is a minimal Spring Boot template with H2 database, JWT authentication, ReactJS frontend, and Swagger UI for API documentation:
1. Project Structure Overview

    Backend: Spring Boot (Java 17)
    Frontend: ReactJS
    Database: H2 (in-memory)
    Authentication: JWT Token (Spring Security)
    API Testing: Swagger UI

2. Installation and Setup Guide
Backend (Spring Boot)
2.1 Install Java & Maven:

Ensure that Java 17 and Maven are installed on your system.

bash

java -version  # Ensure Java 17 is installed
mvn -version   # Ensure Maven is installed

2.2 Clone the repository:

bash

git clone https://github.com/your-username/spring-boot-h2-jwt-template.git
cd spring-boot-h2-jwt-template

2.3 Configure Maven:

If necessary, configure Maven settings to resolve dependencies:

bash

mvn clean install

2.4 Run the Spring Boot application:

bash

mvn spring-boot:run

Frontend (React)
2.5 Install Node.js and npm:

Ensure Node.js is installed.

bash

node -v  # Check if Node.js is installed
npm -v   # Check if npm is installed

2.6 Navigate to the ReactJS frontend folder:

bash

cd frontend
npm install

2.7 Run the React app:

bash

npm start

3. Project Configuration
3.1. Backend: Spring Boot Application Configuration

src/main/resources/application.yml

yaml

spring:
  datasource:
    url: jdbc:h2:file:./data/db
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
jwt:
  secret: your-jwt-secret-key
  expiration: 3600

3.2. Swagger UI Configuration

src/main/java/com/example/swagger/SwaggerConfig.java

java

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build();
    }
}

Swagger UI will be available at: http://localhost:8080/swagger-ui.html
4. Minimal Authentication Flow

    Users will authenticate using JWT.
    Only authenticated users can create, update, delete, or view their albums and photos.
    Use JWT token in the Authorization header to authorize requests.

4.1. Basic JWT Configuration

src/main/java/com/example/security/JwtAuthenticationFilter.java

java

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Minimal JWT token extraction and validation logic here.
        filterChain.doFilter(request, response);
    }
}

4.2. Enable JWT in Spring Security

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
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }
}

5. CRUD Endpoints

    Albums: /api/albums
    Photos: /api/photos
    Swagger can be used to test the endpoints. Ensure to authenticate first and pass the JWT token for protected routes.

6. Running the Project

    Start the Spring Boot backend:

    bash

mvn spring-boot:run

Start the React frontend:

bash

    npm start

You can access the Swagger UI at http://localhost:8080/swagger-ui.html.
7. Extra Considerations

    Exception Handling: Create a global exception handler for better API error responses.
    CORS Configuration: Add CORS support for the React frontend.
    File Upload Handling: Use a service for handling file uploads to link photos with albums.

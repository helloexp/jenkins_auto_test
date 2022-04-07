fr_mkpf_backend

# Setup local environment for dev

## Prepare database

1. Clone DDL repo

```
$ git clone git@github.com:fastretailing/fr-cdu-mpf-ddl.git
```

2. PostgreSQL server

2.1 Download and install as local service

```
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
```

2.2. Using docker

```
$ cd fr-cdu-mpf-ddl
$ docker-compose up
```

Connection info
- Hostname: 127.0.0.1
- Port: 5432
- Database name: fr_mkpf
- Username: postgres
- Password: postgres

3. Migrate database

```
- Open folderfr-cdu-mpf-ddl -> Open terminal
- Run this command:
  $ migrate.sh 127.0.0.1 5432 postgres postgres fr_mkpf create_database
  $ migrate.sh 127.0.0.1 5432 postgres postgres fr_mkpf create_table
```

## Prepare project

1. Clone repo

```
$ git clone git@github.com:fastretailing/fr-cdu-mpf-backend.git
```

2. IDE

2.1. IntelliJ

Import project
- File -> New -> Project From Existing Sources...
- Choose "fr_mkpf_backend" folder
- Choose "Import Project..." -> Choose "Maven" -> Finish
- Wait for IntelliJ to import project and download the necessary libraries

2.2 Eclipse

Import project
- File -> Import -> Existing Maven Projects
- Chose project folder fr-cdu-mpf-backend -> Finish

2.3 Maven command line

```
$ ./mvnw clean compile eclipse:eclipse
```

4. Run project

```
On terminal, run command:
```

$ ./mvnw spring-boot:run

```
Open browser and go to this URL: http://localhost:8080/api/marketingpf/v1/fr/jp/healthcheck
If don't see any error, the project is setup successfully
```

# Configuration

## application.properties and profiles
If no profile is set, the following file is used
- /src/main/resources/application.properties

Other profile properties (dev, id, stg, prd)
- /src/main/resources/application-dev.properties
- /src/main/resources/application-id.properties
- /src/main/resources/application-stg.properties
- /src/main/resources/application-prd.properties

For active profile, pass parameter -Dspring.profiles.active=${dev|it|stg|prd}

```
$ ./mvnw -Dspring.profiles.active=dev clean test
```

Most configurable values is binded to /src/main/java/com/fastretailing/marketingpf/configs/Config.java

## Security

Check /src/main/java/com/fastretailing/marketingpf/configs/security/WebSecurityConfig.java

In case to bypass Spring Security, override method configure

```
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeRequests().anyRequest().permitAll();
    }
```

## Domain relate configuration

URI prefix is /api/marketingpf/v1/fr/jp. Any controller that extends BaseController will affect

```
@RequestMapping("${mkpf.uriPrefix}")
    public class BaseController {
```

```
mkpf.baseUrl=http://localhost
mkpf.uriPrefix=/api/marketingpf/v1/fr/jp                                           # Change this value with caution (also change front-end redirect value)
mkpf.frontendAuthCallbackUrl=${mkpf.baseUrl}/#/ad-account/oauth/callback
mkpf.apiUrl=${mkpf.baseUrl}${mkpf.uriPrefix}
mkpf.platformAuthCallbackUrl=${mkpf.apiUrl}/auth/api_auth_info_create              # Set on ads platform app (GOOLGE/FACEBOOK) redirect url
azure.activedirectory.redirect-uri-template=${mkpf.apiUrl}/login/oauth2/code/      # Set on AZURE app redirect uri
```

# Error handler
Theorical, all errors would be forward/fallback to this controller
/src/main/java/com/fastretailing/marketingpf/controllers/error/ErrorController.java

For every catching Exception, there is corresponding handler.
But the following handlers are special.

handleException is for not-handled exception. It means that no handler for that type of exception.

```
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        String requestId = (String) request.getAttribute("requestId", 0);
        logger.error("Unhandle error occurs", StructuredArguments.keyValue("XID", requestId),
                StructuredArguments.keyValue("message", e.getMessage()),
                StructuredArguments.keyValue("trace", e.getStackTrace()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E00500, requestId));
    }
```

handleDefaultUncatchError is for out-of-Spring-error. It is usually for servlet error.

Path is config by server.error.path

```
server.error.path=${mkpf.uriPrefix}/error
```

```
    @GetMapping("/error")
    public ResponseEntity<Object> handleDefaultUncatchError(HttpServletRequest request) {
        String requestId = (String) request.getAttribute("requestId");
        logger.error("Fallback error occurs", StructuredArguments.keyValue("XID", requestId));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ERROR_CODE.E00500, requestId));
    }
```

# Unit test

```
$ ./mvnw test
```

With spring profile

```
$ ./mvnw -Dspring.profiles.active=dev clean test
```

Override environment variables

```
$ export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF8"; \
      export RDB_HOST="localhost"; \
      export RDB_PORT="5432"; \
      export RDB_DATABASE="fr_mkpf"; \
      export RDB_USER="postgres"; \
      export RDB_PASSWORD="postgres"; \
      ./mvnw -Dspring.profiles.active=dev clean test
```


# CI test using Dockerfile

Build image

```
$ docker build -t fr-cdu-mpf-backend-ci --target ci .
```

Execute test

```
$ docker run --rm \
    -v ${PWD}:/workspace \
    -v /root/.m2:/root/.m2 \
    -w=/workspace \
    --net=host
    -e RDB_HOST='host.docker.internal' \
    -e RDB_PORT='5432' \
    -e RDB_DATABASE='fr_mkpf' \
    -e RDB_USER='postgres' \
    -e RDB_PASSWORD='postgres' \
    fr-cdu-mpf-backend-ci \
    bash -c "sed -i -e 's/\r$//' ./mvnw && ./mvnw -Dspring.profiles.active=dev clean test | tee /workspace/target/result.txt || true"
```

> host.docker.internal is special value which allow service to access host machine service

Check result at target/result.txt





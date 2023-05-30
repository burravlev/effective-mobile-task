# Effective mobile backend task
## Server setup
### DB configuration
```yaml
# spring-social/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:5432/social_network
    username: <YOUR_DB_USERNAME>
    password: <YOUR_DB_PASSWORD>
```
### Local image storage configuration
```yaml
#Image folder
application:
  files:
    dir: src/main/resources/static
```
### Image url configuration
```yaml
application:
  host: http://localhost:8080
  files:
    image:
      uri: ${application.host}/api/files/img/
```
# Challenge Fórum Hub
Projeto realizado em Java + Spring Boot como challenge do curso Oracle One Next Education + Alura.

# End Points
  >**Em todas as rotas é necessária a autenticação via JWT, exceto na rota de criação de usuário.**


- [User Endpoints](#user-endpoints)
    - [Create User](#create-user)
    - [Get User By ID](#get-user-by-id)
    - [Get Users](#get-users)
    - [Update User](#update-user)
    - [Delete User](#delete-user)
- [Login Endpoint](#login-endpoint)
    - [User Login](#user-login)
- [Topics Endpoints](#topics-endpoints)
    - [Get All Topics](#get-all-topics)
    - [Create Topic](#create-topic)
    - [Update Topic](#update-topic)
    - [Delete Topic](#delete-topic)
- [Courses Endpoints](#courses-endpoints)
    - [Get All Courses](#get-all-courses)
    - [Create Course](#create-course)
    - [Update Course](#update-course)
    - [Delete Course](#delete-course)

## User Endpoints

### Create User

- **URL:** `/user`
- **Method:** `POST`
- **Description:** Cria novo usuário.
- **Request Body:**
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }

### Get User By ID

- **URL:** `/user`
- **Method:** `GET`
- **Description:** Obtém usuário pelo id.
- **Response Body:**
  ```json
  {
    "id": "number",
    "name": "string"
  }

### Get Users

- **URL:** `/user`
- **Method:** `GET`
- **Description:** Obtém lista de usuários.
  - **Response Body:**
    ```json
    {
      "totalPages": "number",
      "totalElements": "number",
      "size": 10,
      "content": [
          {
              "id": "number",
              "name": "string"
          }
      ],
      "number": "number",
      "sort": {
          "empty": "boolean",
          "sorted": "boolean",
          "unsorted": "boolean"
      },
      "first": "boolean",
      "last": "boolean",
      "numberOfElements": "number",
      "pageable": {
          "pageNumber": "number",
          "pageSize": 10,
          "sort": {
              "empty": "boolean",
              "sorted": "boolean",
              "unsorted": "boolean"
          },
          "offset": "number",
          "paged": "boolean",
          "unpaged": "boolean"
      },
      "empty": "boolean"
    }
  >Nessa rota é possível utilizar query params para a paginação, como, por exemplo: *?size=2&page=2*

### Update User

- **URL:** `/user/id`
- **Method:** `PUT`
- **Description:** Atualiza o usuário.
- **Request Body:**
> [!NOTE]
> Os campos de atualização são opcionais.
  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }

- **Response Body:**
  ```json
  {
    "id": "number",
    "name": "string"
  }

### Delete User

- **URL:** `/user/id`
- **Method:** `DELETE`
- **Description:** Deleta o usuário.
- **Request Body:**
  > [!TIP]
  > Não requer corpo de requisição; a resposta é um 204 No Content.

## Login Endpoint

### Login

- **URL:** `/login`
- **Method:** `POST`
- **Description:** Realiza o login.
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
- **Response Body:**
  ```json
  {
    "token": "string"
  }

# Diagrama do Banco de Dados
![img.png](img.png)



### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#io.validation)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#web.security)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


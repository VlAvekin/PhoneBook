# Phone Book

Control systems versions: Maven;

Framework: Spring (Boot, MVC, Data, Security);

Database: PostgreSQL, JPA, Hibernate, flyway;

Json: Gson;

WEB: Freemarker, HTML, Bootstrap;

Test: Mockito, JUnit.
<hr>
Create DataBase PostgreSQL

```
drop database phonebook; create database phonebook;	
\c phonebook
CREATE EXTENSION pgcrypto;
```
<hr>
Create Test DataBase PostgreSQL

```
CREATE USER root WITH password 'root'; 
GRANT ALL ON DATABASE phonebooktest TO root;
drop database phonebooktest; create database phonebooktest;	
\c phonebooktest
CREATE EXTENSION pgcrypto;
```
<hr>
--spring.profiles.active=json
<hr>
API

```
GET /

   Response:
         list data source db
```

```
GET /?data=url

   Request:
      param data=url
      
   Response:
      list data source db
```

```
Post /

  Body: 
     

   Response:
      
      
```
<hr>

![Linc](images/1.png)

![Linc](images/2.png)

![Linc](images/3.png)

![Linc](images/4.png)

![Linc](images/5.png)

![Linc](images/6.png)

![Linc](images/7.png)

![Linc](images/8.png)

![Linc](images/9.png)

![Linc](images/10.png)

![Linc](images/11.png)

![Linc](images/12.png)

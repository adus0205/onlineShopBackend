## Online Shop
This is a fullstack online store project. The application includes the basic functions of an online store, as well as those that are a bit more advanced. There is integration with a payment gateway in this application, sending emails and much more.

The application in a nutshell has such functionalities:

On the administrative side:

Add, edit and delete products.
Add, edit and delete categories
Order management, including exporting orders to CSV file and viewing sales statistics in the form of a chart
Moderation of user reviews (approval, deletion)

From the user side:

Viewing products
Searching for products by category
Creating orders (adding products to cart, deleting, ordering)
Adding product reviews
Viewing the list of your orders
Email notifications
In a nutshell, this is the outline of the application. There is also a mechanism for registration, login and password reminders.

I don't know how long the application will run, but there is a demo on display, available at: https://shopfrontend-production-f14c.up.railway.app/

And for the administrator: https://shopfrontend-production-f14c.up.railway.app/admin

The default login is: admin
Default password is: test
Table of Contents
Technologies Used
Screenshots
Setup
Contact

# Technologies Used
Java - version 17.0.3.1
Spring Boot (with Spring Reactive Web, Spring Security, Spring Web, Spring Data JPA, Spring Test and other) - version 3.1.0
MySQL - version 8.0.33
Jjwt - version 4.0.0
Liquibase - version 4.23.0
Maven - version 4.0.0
Lombok - version 1.18.28

# How this it works:
Clone this repository to your IDE (https://github.com/jakubknap/ShopBackend.git)
The project uses Maven as a build tool. It already contains .mvn wrapper script, so there's no need to install maven.
Manually create the database at: localhost:3306
 CREATE DATABASE shop
Manually create a directory 'data\productImages' in the root directory of the application
To take full advantage of the potential of ap liking you must run it with the following parameters:
<this is for sending email>.

--spring.mail.username=<your email to gmail>
--spring.mail.password=<your password to gmail>

<If we set this propertis then we will have the "sending" of the email in the logs>.

--app.email.sender=fakeEmailService

<Needed for jwt>.

--jwt.secret=<your secret jwt string>
--springdoc.api-docs.enabled=true

<Here are the credentials for the payment gateway przelewy24, if you don't fill it in, just the online payment won't work>.

--app.payments.p24.merchantId=<your merchant id>
--app.payments.p24.posId=<your pos id>
--app.payments.p24.crc=<your crc>
--app.payments.p24.secretKey=<your secret key>
--app.payments.p24.testCrc=<your test crc>
--app.payments.p24.testSecretKey=<your test secret key>
If something is not configured, the functionality will not work

To build and run the project execute the following command:
  mvn spring-boot:run
You can check the functionality of the backend itself using swagger: http://localhost:8080/swagger-ui.html Important! Remember about authentication

The client application runs on localhost:8080. The administrative application is available at localhost:8080/admin

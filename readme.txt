This is a Rest Api Project written in Java using Spring Boot and Swagger2
This Project consumes the rest api provided by DWP and returns a list of valid users that satisfy the criteria 
This project is build using Maven.
use the below instructions to run this APP.
#Run the below cmd to clean and Install the application 
mvn clean install 
#Run the below cmd to run the application
mvn exec:java -Dexec.mainClass=com.prakash.example.rest.consumer.ConsumingRestApiApplication

The application can be tested by running the below Swagger http request in the browser 
http://localhost:8888/swagger-ui.html

Also we can check the end point http://localhost:8888/api/prakash/validusers which returns data in json format.

# GameOfThreeApp

How to Run: 

-Build project jar  - mvn clean install
-Launch one application using command : 
java -jar target/GameOfThreeServer-0.0.1-SNAPSHOT.jar 

-Launch second application using command:
java -jar target/GameOfThreeServer-0.0.1-SNAPSHOT.jar --spring.config.name=application2

To start the game, hit below curl from postman: 
1-For Auto Mode : 
curl --location --request POST 'http://localhost:8082/gameofthree/start' \
--data-raw ''


2-For Interactive Mode:
curl --location --request POST 'http://localhost:8082/gameofthree/start?mode=INTERACTIVE' \
--header 'Content-Type: application/json' \
--data-raw '{
    "playerName":"Asra",
    "number" : 1922
}'

-User have to provide number when mode=INTERACTIVE.

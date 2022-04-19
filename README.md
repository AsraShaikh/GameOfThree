# GameOfThreeApp


### Game Instructions: 
* Supported Modes : There are 2 modes supported by this game: 

        1) AUTO 
        2) INTERACTIVE 
        
      In AUTO mode, the game will be played automatically between both player. 
      In INTERACTIVE mode, one of the user must have to provide number while starting game

* Both players must be active in order to play game. If one of the player is not available, the game will simply be __terminated__.

### Pre-Requisites: 
- Java 8
- Maven


## How to Run Application: 
-> Go to project directory and Build Project jar using maven command:   
- mvn clean install

-> Launch application instance for first player using command : 
- java -jar target/GameOfThreeServer-0.0.1-SNAPSHOT.jar 

-> Launch application instance for seconde player using command :
- java -jar target/GameOfThreeServer-0.0.1-SNAPSHOT.jar --spring.config.name=application2


## How to Start Game: 
To start the game, hit below curl command: 
 1) For Auto Mode : 
curl --location --request POST 'http://localhost:8082/gameofthree/start' \
--data-raw ''


 2) For Interactive Mode:
curl --location --request POST 'http://localhost:8082/gameofthree/start?mode=INTERACTIVE&number=8495' \
--data-raw ''

##### Note: 
-User MUST provide number when mode=INTERACTIVE. Number, if provided with mode=AUTO will be ignored.

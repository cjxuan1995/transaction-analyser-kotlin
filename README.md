# Transaction Analyser
This is a functional Kotlin application implemented based on the requirements specified in the coding challenge document.

## How to run/build/test the app
Dependencies: JDK11

- To build the app: run `./gradlew clean jar` inside the project directory.
  The generated jar file will be `<project root>/build/libs/transaction-analyser-1.0-SNAPSHOT.jar`.

- To run the app: In the project directory, run `java -jar build/libs/transaction-analyser-1.0-SNAPSHOT.jar` and enter input parameters into the terminal.

- To run all the unit tests: In the project directory, run `./gradlew clean test`

## Results
- Execution results:
    ```
    jixuancao@Jixuans-MacBook-Pro transaction-analyser % java -jar target/transaction-analyser-1.0-SNAPSHOT.jar
    accountId:
    ACC334455
    from:
    20/10/2018 12:00:00
    to:
    20/10/2018 19:00:00
    Relative balance for the period is: -25.00
    Number of transactions included is: 1
    ```
- Test results:
    ```
    me803159@Jixuans-MBP-2 transaction-analyser-kotlin % ./gradlew clean test
  BUILD SUCCESSFUL in 3s
  7 actionable tasks: 7 executed
    ```
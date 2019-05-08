Following RESTFul Endpoints created :

1.  Health check
    GET http://IP:PORT/statement/isAlive

2.  To Generate Report on failed transactions
    POST http://IP:PORT/statement/reports

    Request:
    {
    	"csvFileLocation":"Input CSV file location",
    	"xmlFileLocation":"Input XML file location",
    	"outputReportLocation":"Output CSV file location"
    }

How to run the project

1. Build the project
   mvn clean install

2. Run the Project
   java -jar  ./target/statementprocessor-0.0.1-SNAPSHOT.jar

By default it will run at Port 8080
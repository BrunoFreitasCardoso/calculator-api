## RESTful Calculator API

This project provides a RESTful calculator API built with Spring Boot. It includes two modules: rest (handles the API endpoints) and calculator (performs the calculations). The project is designed to work with Apache Kafka for message production and consumption, and Docker Compose to run everything together.

### Building the Project

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd <repository_name>

2. Build the project using Maven:
    ```bash
   mvn clean install

This will compile and package both rest and calculator modules into executable JAR files.

### Running the Project

1. **Start the services using Docker Compose:** 
   -   In the project root directory, run the following command to start all services (including Kafka, your rest, and calculator modules):
        ```bash
        docker-compose up --build

2. **Services will be available as follows**:
   - **Calculator Service** (via Kafka) for message production/consumption.
   - **REST API** to handle incoming HTTP requests.


3. Once the services are up, you can visit the API using the following endpoint:
    - ```
      http://localhost:8080

### Example API Usage

1.  **Basic Calculation (e.g., add two numbers)**
    ```bash
        curl -X GET "http://localhost:8080/api/add?a=7&b=02"
    ```
    This will return:
    ```
        {
            "result": 15
        }
    ```


### Stoping the Services
To stop the services, use:
```
docker-compose down
```
This will stop and remove the containers but keep the Docker volumes.
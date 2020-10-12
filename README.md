# Issue Reporting
Examples for reported issues

####Start local environment with docker-compose

This will bring up:
* LocalStack
* PostgreSQL
* Redis

Windows & Linux
```
docker-compose up --build --remove-orphans
```
MacOS
```
TMPDIR=/private$TMPDIR docker-compose up --build --remove-orphans
```

####Start Application Locally

Windows
```
gradlew clean bootRun
```
Linux & MacOS
```
./gradlew clean bootRun
```

The service will start up running the ***local*** profile and will be running at http://localhost:8080
You can verify that everything is connected properly by accessing the health check at http://localhost:8080/actuator/health

####Run Scenario to Cause Issue

```
curl --location --request GET 'http://localhost:8080/issue/generate'
```

####Stop Local Environment

```
docker-compose down --rmi local -v
```
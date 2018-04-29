# Account API 

##Purpose
Account API application is a spring boot based application used create, find and delete accounts.

###Prerequisites

- JDK 8

##Database
The application comes packaged with a Mongo database. To import some test data run the following command from the root of the project:
```
mongoimport -d account-api -c accounts src/test/resources/test-data.json --jsonArray

```

##Building and deploying the application
  
###Building the application

To build the project execute the following command:

```
./gradlew build
```

### Running the application

Run the application by executing:

```
./gradlew bootRun
```

###Unit tests

To run all unit tests execute the following command:

```
./gradlew test
```

##API
###Create
To create an account:

`POST` to the `/accounts` endpoint:

```
{
    "firstName" : "Amelia",
    "secondName" : "Ephgrave", 
    "accountNumber" : "4324234"
}
```

###Find all
To find all accounts:

`GET` to the `/accounts` endpoint:

###Delete
To delete an account:
`DELETE` to the `/accounts/{$id}` endpoint using the id of the account to delete
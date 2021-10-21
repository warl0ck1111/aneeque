#Aneeque
#### Coding Challenge
Using spring boot, create a service that exposes the following endpoints.\
•	Signup API\
•	Login API\
•	List users API\
Include\
•	A Caching engine using Redis\
•	Unit testing\
•	Integration testing\
•	DockerFile for containerization\


###  REQUEST
#### POST http://localhost:8989/api/v1/auth/user/signup
Content-Type: application/json

{\
    "firstName" : "latiifa",\
    "lastName":"okala",\
    "userEmail": "latiifa@rocketmail.com",\
    "password":"password",\
    "confirmPassword": "password"
}


### RESPONSE:
{
"httpStatus": "OK",\
"timestamp": "2021-10-21T20:26:19.995+00:00",\
"statusCode": 200,\
"status": "true",\
"data": {
"firstName": "latifa",\
"lastName": "okala",\
"userEmail": "latiifa@rocketmail.com",\
"appUserRole": {
"name": "USER"
}
},\
"message": "login successful"
}




### REQUEST
#### POST http://localhost:8989/api/v1/auth/user/login
Content-Type: application/json

{\
"email":"latiifa@rocketmail.com",\
"password":"password"\
}

### RESPONSE:
{\
"httpStatus": "OK",\
"timestamp": "2021-10-21T20:26:19.995+00:00",\
"statusCode": 200,\
"status": "true",\
"data": {
"firstName": "latifa",\
"lastName": "okala",\
"userEmail": "latiifa@rocketmail.com",\
"appUserRole": {
"name": "USER"
}

},\
"message": "login successful"\
}



### REQUEST
#### GET http://localhost:8989/api/v1/auth/get-all?page=1&size=1


### RESPONSE:
{\
"httpStatus": "OK",\
"timestamp": "2021-10-21T20:29:53.448+00:00",\
"statusCode": 200,\
"status": "true",\
"data": [\
{\
"firstName": "ibrahim",\
"lastName": "okala",\
"userEmail": "arome@rocketmail.com",\
"appUserRole": {
"name": "USER"
}
}
],\
"message": "see object for details "
}

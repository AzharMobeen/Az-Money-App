# Az-Money-App
It's Jersey RESTfull Api for Money transfer. I'm using Embedded Tomcat (maven plugin).



**How to Run**
* To run all test use: **mvn test**
* To run project use:	**mvn clean install tomcat7:run**
* Please use User, Account, Transaction samples.
* Dummy data already inserted after application start.
  
**Application Resources:**
	
* Fetch all users: 	
	[http://localhost:8080/users](http://localhost:8080/users)
* Fetch user by id:	
	[http://localhost:8080/users/user/1](http://localhost:8080/users/user/1)
* Create User:
	[http://localhost:8080/users](http://localhost:8080/users)
* Update User:	[http://localhost:8080/users/user/1](http://localhost:8080/users/user/1)


**How to use:**
* Create user.
* Create at least two User accounts.
* Do Transaction and verify transaction.


**Sample Data**

* User

```
{
	"email": "AzharMobeen@gmail.com",
	"fullName": "Azhar Mobeen",    
	"userName": "Az"
}
```

* Account

```
{
	"IBAN": "AE111222334455502",
	"USERID": 1,	
	"accountType": "AED",
	"balance": 1000
}
```

* Transaction

```
{
	"fromIBAN": "PK112233445501",
	"toIBAN": "AE112233445501",
	"amount": 50
}
```
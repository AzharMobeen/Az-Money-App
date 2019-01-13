# Az-Money-App
It's Jersey RESTfull Api for Money transfer.

#How to use:
* User should be registered.
* 


#Sample Data

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
	"accountHolder": "Az",	
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
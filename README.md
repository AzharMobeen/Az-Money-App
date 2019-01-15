# Az-Money-App
It's Jersey RESTfull Api for Money transfer.

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
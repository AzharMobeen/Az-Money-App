DROP TABLE IF EXISTS User;

CREATE TABLE User (USERID LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
 USERNAME VARCHAR(45) NOT NULL,
 FULLNAME VARCHAR(45) NOT NULL,
 EMAIL VARCHAR(45) NOT NULL);

INSERT INTO User (USERNAME, FULLNAME, EMAIL) VALUES ('Malik','Malik Adeel','malik@az.com');
INSERT INTO User (USERNAME, FULLNAME, EMAIL) VALUES ('Shah','Shah Wasif','shah@az.com');
INSERT INTO User (USERNAME, FULLNAME, EMAIL) VALUES ('Mughal','Mughal Adil','mughal@az.com');
INSERT INTO User (USERNAME, FULLNAME, EMAIL) VALUES ('MaYo','MaYo Az','mayo@az.com');

DROP TABLE IF EXISTS Account;

CREATE TABLE Account (ACCOUNTID LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
IBAN VARCHAR(45) NOT NULL,
USERID LONG NOT NULL,
ACCOUNT_TYPE VARCHAR(15) NOT NULL,
BALANCE DECIMAL(19,4) NOT NULL);


INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('PK112233445501',1,'PKR',100);
INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('AE112233445501',1,'AED',100);

INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('PK111222334455502',2,'PKR',3000);
INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('AE111222334455502',2,'AED',100);
INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('US111222334455502',2,'USD',100);

INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('PK445566779903',3,'PKR',1200);

INSERT INTO Account (IBAN, USERID, ACCOUNT_TYPE, BALANCE) VALUES ('AE333444555604',4,'AED',30000);

DROP TABLE IF EXISTS Transaction;

CREATE TABLE Transaction (TRANSACTIONID LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
FROM_IBAN VARCHAR(45) NOT NULL,
TO_IBAN VARCHAR(45) NOT NULL,
AMOUNT DECIMAL(19,4) NOT NULL,
TRANSACTION_FEE VARCHAR(15) NOT NULL);

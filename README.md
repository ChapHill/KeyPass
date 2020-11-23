# KeyPass
A Password generator and password storage for websites and applications

## Features
* Ability to generate a random password that adheres to most password requirements for websites (i.e. 1 uppercase, 1 symbol..)
* Stores passwords in a local SQL database table
* Retrieve password for website or application from the table
* Update passwords
* Set your own password if you prefer to not generate a random one
* Any password that is created or retrieved from the table is then copied to your clipboard

## Requirements
A user generated SQL table must be created before running the program. If a table is not created or not found, the program will throw an error and terminate. 
The code below reads as follows: ```new Database("database locataion", "database username", "password");```

``` Database db = new Database("jdbc:mysql://localhost:3306/keypass","root", "root"); ```

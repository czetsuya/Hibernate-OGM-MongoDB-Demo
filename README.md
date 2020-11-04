# Hibernate OGM with MongoDB

Author: Edward P. Legaspi
Email: czetsuya@gmail.com

## Database

Needless to say download or install the latest copy of MongoDB :-).

Here are the commands I've executed in prepping the database for connection.

```ssh
use test
db.member.insert({"firstName": "Edward"});

db.createUser ({
	"user":"edward",
	"pwd":"edward",
	"roles": [
		{ "role":"readWrite", "db":"test" }
	]
})

// you may want to check if the data is inserted
db.Member.find();
```

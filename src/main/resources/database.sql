Drop Table if exists Words;

Create Table Words(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	word VARCHAR(64) NOT NULL,
	category VARCHAR(64) NOT NULL
);


/*
	Category -- name
	!! atencao a letra minuscula
*/

Insert Into Words(word, category) Values ("david", "name");
Insert Into Words(word, category) Values ("diogo", "name");
Insert Into Words(word, category) Values ("filipe", "name");
Insert Into Words(word, category) Values ("tiago", "name");

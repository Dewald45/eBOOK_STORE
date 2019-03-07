CREATE TABLE books (
id int,
Title varchar(100),
Author varchar(30), 
Qty int
);

insert into books values(3001, 'A Tale of Two Cities', 'Charles Dickens', 30);
insert into books values(3002, 'Harry Potter and the Philosopher''s Stone' , 'J.K Rowling' ,40);
insert into books values(3003, 'The Lion, the Witch and the Wardrobe' , 'C.S Lewis', 25);
insert into books values(3004, 'The Lord of The Rings' , 'J.R.R Tolkein', 37);
insert into books values(3005, 'Alice in Wonderland' , 'Lewis Carroll', 12);

select * from books;	
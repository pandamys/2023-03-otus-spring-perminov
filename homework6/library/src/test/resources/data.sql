insert into authors (`name`, `surname`) values ('Test', 'Testov');
insert into authors (`name`, `surname`) values ('Tester', 'Testerov');

insert into genres (`name`) values ('Test_Genre');

insert into books (`name`, `author_id`, `genre_id`) values ('TestBook1', 1, 1);
insert into books (`name`, `author_id`, `genre_id`) values ('TestBook2', 2, 1);

insert into comments (`text`, `book_id`) values ('Test cool book', 1);
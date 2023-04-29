insert into authors (id, `author_name`, `author_surname`) values (1, 'Sergey', 'Tarmashev');
insert into authors (id, `author_name`, `author_surname`) values (2, 'Dmitriy', 'GLuhovskii');
insert into authors (id, `author_name`, `author_surname`) values (3, 'John', 'De Chensi');
insert into authors (id, `author_name`, `author_surname`) values (4, 'Mikhail', 'Bulgakov');

insert into genres (id, `genre_name`) values (1, 'Fantastic');
insert into genres (id, `genre_name`) values (2, 'Fantasy');
insert into genres (id, `genre_name`) values (3, 'Classic');

insert into books (id, `name`, `author`, `genre`) values (1, 'Drevnii', 1, 1);
insert into books (id, `name`, `author`, `genre`) values (2, 'Brodiachiy Zamok', 3, 2);
insert into books (id, `name`, `author`, `genre`) values (3, 'Metro 2033', 2, 1);
insert into books (id, `name`, `author`, `genre`) values (4, 'Master and Margarita', 4, 3);
insert into books (id, `name`, `author`, `genre`) values (5, 'Arial', 1, 1);
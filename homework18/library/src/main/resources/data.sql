insert into authors (name, surname) values ('Sergey', 'Tarmashev');
insert into authors (name, surname) values ('Dmitriy', 'GLuhovskii');
insert into authors (name, surname) values ('John', 'De Chensi');
insert into authors (name, surname) values ('Mikhail', 'Bulgakov');

insert into genres (name) values ('Fantastic');
insert into genres (name) values ('Fantasy');
insert into genres (name) values ('Classic');

insert into books (name, author_id, genre_id) values ('Drevnii', 1, 1);
insert into books (name, author_id, genre_id) values ('Brodiachiy Zamok', 3, 2);
insert into books (name, author_id, genre_id) values ('Metro 2033', 2, 1);
insert into books (name, author_id, genre_id) values ('Master and Margarita', 4, 3);
insert into books (name, author_id, genre_id) values ('Arial', 1, 1);

insert into comments (text, book_id) values ('Cool book', 1);
insert into comments (text, book_id) values ('Interesting book', 1);
insert into comments (text, book_id) values ('Bad book', 2);

insert into roles (role_name) values ('ROLE_USER');
insert into roles (role_name) values ('ROLE_ADMIN');

insert into users (username, password) values ('user1', 'passwd');
insert into users (username, password) values ('admin', '123');

insert into users_roles (user_id, roles_id) values (1, 1), (2, 2);
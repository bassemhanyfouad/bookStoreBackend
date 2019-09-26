create sequence hibernate_sequence start 1 increment 1;
create table book
(
    book_id       int8   not null,
    created_by    text,
    created_date  timestamp,
    last_mod_by   text,
    last_mod_date timestamp,
    version       int4,
    details       text   not null,
    image         text,
    name          text   not null,
    price         float8 not null,
    primary key (book_id)
);
create table book_similarity
(
    similarity_score    float8 not null,
    first_book_book_id  int8   not null
        constraint fk_book_sim_first_book_id references book,
    second_book_book_id int8   not null
        constraint fk_book_sim_second_book_id references book,
    primary key (first_book_book_id, second_book_book_id)
);
create table person
(
    id            int8 not null,
    created_by    text,
    created_date  timestamp,
    last_mod_by   text,
    last_mod_date timestamp,
    version       int4,
    active        boolean,
    email         text,
    first_name    text,
    last_name     text,
    mobile_number text,
    password      text not null,
    user_name     text not null,
    primary key (id)
);
create table user_view
(
    book_id int8 not null
        constraint fk_user_view_book_id references book,
    user_id int8 not null
        constraint fk_user_view_user_id references person,
    primary key (book_id, user_id)
);
create table user_viewed_books
(
    user_id              int8 not null
        constraint fk_user_viewed_books_user_id
            references person,
    viewed_books_book_id int8 not null
        constraint fk_user_viewed_books_book_id
            references book,
    primary key (user_id, viewed_books_book_id)
);
alter table if exists person
    add constraint UK_user_email unique (email);
alter table if exists person
    add constraint UK_user_mobile_number unique (mobile_number);
alter table if exists person
    add constraint UK_user_name unique (user_name);
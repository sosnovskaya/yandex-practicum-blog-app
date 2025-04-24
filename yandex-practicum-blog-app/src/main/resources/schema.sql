-- Таблица с постами
create table if not exists posts
(
    id         bigint primary key,
    title      varchar(256) not null,
    text       varchar(256) not null,
    imagePath  varchar(256) not null,
    likesCount integer default 0
);

-- Таблица с комментариями
create table if not exists comments
(
    id      bigint primary key,
    post_id bigint       not null,
    text    varchar(256) not null,
    foreign key (post_id) references posts (id)
);

-- Таблица с тегами
create table if not exists tags
(
    id   bigint primary key,
    name varchar(256) not null,
);

-- Связующая таблица для связи многие-ко-многим между постами и тегами
create table if not exists post_tags
(
    post_id bigint not null,
    tag_id  bigint not null,
    primary key (post_id, tag_id),
    foreign key (post_id) references posts (id),
    foreign key (tag_id) references tags (id)
);
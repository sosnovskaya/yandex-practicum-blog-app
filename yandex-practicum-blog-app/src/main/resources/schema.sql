-- Таблица с постами
create table if not exists posts
(
    id         bigint primary key auto_increment,
    title      varchar(256) not null,
    text       text not null,
    image_path  varchar(512) not null,
    likes_count integer      not null default 0
);

-- Таблица с комментариями
create table if not exists comments
(
    id      bigint primary key auto_increment,
    post_id bigint not null references posts (id),
    text    text not null
);

-- Таблица с тегами
create table if not exists tags
(
    id   bigint primary key auto_increment,
    name varchar(256) not null unique
);

-- Связующая таблица для связи многие-ко-многим между постами и тегами
create table if not exists post_tags
(
    post_id bigint not null,
    tag_id  bigint not null,
    primary key (post_id, tag_id),
    foreign key (post_id) references posts (id) on delete cascade,
    foreign key (tag_id) references tags (id) on delete cascade
);
create table student
(
    id       int auto_increment comment 'student id'
        primary key,
    name     varchar(256) not null comment 'student name',
    email    varchar(256) not null comment 'student email',
    password varchar(512) not null comment 'student password'
)
    comment 'student info';



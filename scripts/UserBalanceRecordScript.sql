create table UserBalanceRecord
(
    userId                varchar(256)      not null comment 'student account id'
        primary key,
    FAQTokenBalance       int default 10000 not null comment 'FAQ token balance of the student',
    recommendTokenBalance int default 0     not null comment 'recommend token balance of the student',
    recommendFreeUses     int default 3     not null comment 'recommend uses for free'
)
    comment 'store the user balance';



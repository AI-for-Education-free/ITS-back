create table StudentAnswerRecord
(
    id                varchar(256) not null comment 'student only id'
        primary key,
    userId            varchar(256) not null comment 'student account id',
    questionId        varchar(256) not null comment 'question id',
    answerCorrectness tinyint(1)   not null comment 'answer is correct or not',
    answerTimestamp   bigint       not null comment 'time of student commit answer',
    answerTimeConsume int          null comment 'time consume of answering',
    hintCount         int          null comment 'times of hint',
    codeErrorType     varchar(128) null comment 'code error type',
    codeErrorInfo     varchar(256) null comment 'code error info'
);



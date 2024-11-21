create user 'springUser'@'localhost' identified by 'spring';

create database springUserfs character set=utf8;

grant all privileges on springUserfs.* to 'springUser'@'localhost';

create table springUserfs.MEMBER(
    ID int auto_increment primary key,
    EMAIL varchar(100),
    PASSWORD varchar(100),
    NAME varchar(100),
    REGDATE datetime,
    unique key (EMAIL)
) engine=innoDB character set = utf8;
alter table member convert to charset UTF8

UPDATE MEMBER SET PASSWORD = '1234' where EMAIL = 'madvirus@naver.com'

select * from member


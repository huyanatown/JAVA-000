--	h2-schame.sql
drop table if exists student ;

-- 创建表
create table student(
	id int  primary key auto_increment,
	name varchar(20)
);
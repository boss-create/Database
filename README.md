# Database
Java course design

登录名：dbms
密码：dbms123

1.暂时未处理视图
2.聚合函数未实现

由于时间的原因,方法也没有怎么重构

测试样例:
create table sc(sno int primary key not null,cno int not null,grade double);

create table person(sno int primary key unique not null,sname char(10) not null,age int);
drop table student;

insert into person(sno,sname,age) values(2012,'pp1',19);
insert into person(sno,sname,age) values(2013,'pp3',20);
insert into person(sno,sname,age) values(2014,'pp2',18);
insert into person(sno,sname,age) values(2015,'pp4',21);

alter table student add columntest int;

alter table student drop column columntest;

insert into student (sno,sname,sdept,ssex,sage) values (2013,'jack1','IS1','男1',21);
insert into student (sno,sname,sdept,ssex,sage) values (2014,'jack2','IS2','男2',22);
insert into student (sno,sname,sdept,ssex,sage) values (2015,'jack3','IS3','男3',23);
insert into student (sno,sname,sdept,ssex,sage) values (2016,'jack4','IS4','男4',24);
insert into student (sno,sname,sdept,ssex,sage) values (2017,'jack5','IS5','男5',25);
insert into student (sno,sname,sdept,ssex,sage) values (2018,'jack6','IS6','男6',26);

insert into sc(sno,cno,grade) values(2013,01,88.5);
insert into sc(sno,cno,grade) values(2014,02,91.5);
insert into sc(sno,cno,grade) values(2016,04,92.5);
insert into sc(sno,cno,grade) values(2015,05,93.5);
insert into sc(sno,cno,grade) values(2018,03,92.5);

insert into student (sname,sdept,ssex,sage) values ('jack','IS','男',21);   // 违背约束,主键为空
select sname,sage from student;
delete from student where sage=19;

update student set sdept='cs',ssex='男' where sno=2016;

update student set sname='null',ssex='女' where sno=2015;

select * from student;

select * from student where sage between 20 and 21;
select * from student where sage=19;
select * from student where sage=21 order by sno;
select * from student,student1 where sage>=20 and sage<=21 order by sno,sage desc;
select * from student where sage>=20 and sage<=21 or sdept='IS';
select sno,sname from student;


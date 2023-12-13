# JDBC
## h2 설치전 버전확인
![h2.png](src%2Fmain%2Fresources%2Ffiles%2Fh2.png)
## h2 설치 https://www.h2database.com
## h2 버전확인 https://www.h2database.com/html/download-archive.html
## h2 실행 
![h2 sart.png](src%2Fmain%2Fresources%2Ffiles%2Fh2%20sart.png)

## 테이블 생성
create table member (
member_id varchar(10),
money integer not null default 0,
primary key (member_id)
);

insert into member(member_id, money) values ('hi1',10000);
insert into member(member_id, money) values ('hi2',20000);

## 데이터값 확인
select * From member;
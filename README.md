## H2 설치 전 버전 확인
![h2.png](src%2Fmain%2Fresources%2Ffiles%2Fh2.png)

## H2 설치
[H2 Database](https://www.h2database.com)에서 설치하세요.

## H2 버전 확인
[H2 Version Archive](https://www.h2database.com/html/download-archive.html)에서 확인하세요.

## H2 실행
![h2 start.png](src%2Fmain%2Fresources%2Ffiles%2Fh2%20start.png)

## 테이블 생성
```sql
create table member (
    member_id varchar(10),
    money integer not null default 0,
    primary key (member_id)
);

insert into member(member_id, money) values ('hi1', 10000);
insert into member(member_id, money) values ('hi2', 20000);
```
## 데이터 값 확인
```sql
select * from member;
```
# 트랜잭션
## 스키마 생성
```sql
drop table member if exists;
create table member (
    member_id varchar(10),
    money integer not null default 0,
    primary key (member_id)
);
```
## 자동 커밋 설정

```sql
set autocommit true; //자동 커밋 모드 설정
insert into member(member_id, money) values ('data1',10000); //자동 커밋
insert into member(member_id, money) values ('data2',10000); //자동 커밋
```
## 수동 커밋 설정
```sql
set autocommit false; //수동 커밋 모드 설정
insert into member(member_id, money) values ('data3',10000);
insert into member(member_id, money) values ('data4',10000);
commit; //수동 커밋
```

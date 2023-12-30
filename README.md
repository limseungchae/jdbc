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
```yaml
트랜잭션(Transaction)은 데이터베이스에서 논리적인 작업의 단위를 의미합니다. 
트랜잭션은 데이터베이스에서 여러 작업을 묶어서 원자성, 일관성, 고립성, 지속성(ACID)이라는 속성을 보장하며, 
이를 통해 데이터베이스의 일관성을 유지하고 데이터의 무결성을 보장합니다.

ACID 속성은 다음과 같습니다:

원자성 (Atomicity): 트랜잭션의 모든 작업은 원자 단위로 실행되어야 합니다. 
                    즉, 트랜잭션 내의 어떠한 작업도 중간에 실패하면 트랜잭션 전체가 실패해야 합니다. 
                    성공하면 트랜잭션 전체가 성공한 것으로 간주됩니다.

일관성 (Consistency): 트랜잭션이 실행을 마치면 데이터베이스는 일관된 상태로 유지되어야 합니다. 
                      트랜잭션이 일관성을 깨뜨리는 일이 있으면 해당 트랜잭션은 롤백되어야 합니다.

고립성 (Isolation): 둘 이상의 트랜잭션이 동시에 실행되더라도 각각의 트랜잭션은 다른 트랜잭션의 작업에 영향을 받지 않아야 합니다. 
                    트랜잭션 간의 간섭이 없도록 격리된 환경을 제공합니다.

지속성 (Durability): 트랜잭션이 성공적으로 완료되면 그 결과는 영구적으로 저장되어야 합니다. 
                     시스템 장애 또는 다시 시작되더라도 데이터베이스는 트랜잭션의 결과를 유지해야 합니다.

트랜잭션은 보통 다음과 같은 기본 작업을 수행합니다:

시작 (Begin): 트랜잭션을 시작합니다.
종료 (Commit): 트랜잭션이 성공적으로 끝났을 때, 모든 변경사항을 영구적으로 저장합니다.
롤백 (Rollback): 트랜잭션이 실패하거나 중단되면, 모든 변경사항을 취소하고 이전 상태로 되돌립니다.
트랜잭션은 데이터베이스 시스템에서 중요한 개념이며, 데이터의 일관성과 무결성을 보장하는 데 핵심적인 역할을 합니다.
```
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

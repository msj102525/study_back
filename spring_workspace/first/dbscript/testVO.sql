-- ajax 테스트용 test 테이블 작성
DROP TABLE test CASCADE CONSTRAINTS;

CREATE TABLE test (
    name varchar2(30) unique not null,
    age number not null
);

INSERT INTO TEST VALUES ('홍길동', 30);
INSERT INTO TEST VALUES ('김길동', 25);
INSERT INTO TEST VALUES ('박길동', 15);

commit;
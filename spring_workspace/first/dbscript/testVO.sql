-- ajax �׽�Ʈ�� test ���̺� �ۼ�
DROP TABLE test CASCADE CONSTRAINTS;

CREATE TABLE test (
    name varchar2(30) unique not null,
    age number not null
);

INSERT INTO TEST VALUES ('ȫ�浿', 30);
INSERT INTO TEST VALUES ('��浿', 25);
INSERT INTO TEST VALUES ('�ڱ浿', 15);

commit;
use spring5;

CREATE TABLE tbl_score (
                           stu_num INT(8) PRIMARY KEY AUTO_INCREMENT,
                           stu_name VARCHAR(255) NOT NULL,
                           kor INT(3) NOT NULL,
                           eng INT(3) NOT NULL,
                           math INT(3) NOT NULL,
                           total INT(3),
                           average FLOAT(5, 2),
  grade CHAR(1)
);
select * from tbl_score;

# select stu_num, stu_name, kor, eng, total, A.rank, A.cnt
# from (select *,
#              RANK() OVER (order by average desc) AS rank,
#               COUNT(*) OVER() AS cnt
#       from tbl_score) A
# where stu_num = 2;



CREATE TABLE tbl_person (
                            id INT(6) PRIMARY KEY,
                            person_name VARCHAR(255) NOT NULL,
                            person_age INT(3)
);
truncate tbl_person;
SELECT * FROM tbl_person
# ORDER BY id DESC
;

# title, content, writer 데이터만 받아오면 됨
CREATE TABLE tbl_board (
    board_no INT(8) PRIMARY KEY auto_increment,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    writer VARCHAR(100) NOT NULL,
    view_count INT(8) default 0,
    reg_date_time DATETIME default current_timestamp
);

SELECT A.stu_num, A.rank, A.cnt
FROM (SELECT *,
             RANK() OVER (ORDER BY average DESC) AS "rank",
             COUNT(*) OVER() AS cnt
      FROM tbl_score) A
WHERE A.stu_num = 1;

SELECT *,
       RANK() OVER (ORDER BY average DESC) AS "rank",
       COUNT(*) OVER() AS cnt
from tbl_score;

SELECT *,
       RANK() OVER (ORDER BY average DESC) AS "rank",
       COUNT(*) OVER() AS cnt
from tbl_score
WHERE stu_num = 1;

# 페이징
truncate table tbl_board;
select * from tbl_board
order by board_no desc
limit 30, 10
;
select * from tbl_board
WHERE title LIKE'%3%'
    OR content LIKE CONCAT('%', '3' ,'%')
order by board_no desc
limit 0, 6
;

# 댓글 테이블 생성
CREATE TABLE tbl_reply (
  reply_no INT(8) PRIMARY KEY AUTO_INCREMENT,
  reply_text VARCHAR(1000) NOT NULL,
  reply_writer VARCHAR(100) NOT NULL,
  reply_date DATETIME default current_timestamp,
  board_no INT(8),
  constraint fk_reply
  FOREIGN KEY (board_no)
  references tbl_board (board_no)
  on delete cascade
);

select * from tbl_board;
select * from tbl_reply;
drop table tbl_reply;
truncate table tbl_reply;


select count(*) from tbl_reply
WHERE board_no = 3
;

SELECT B.board_no,
       B.title,
       B.content,
       B.writer,
       B.reg_date_time,
       B.view_count,
       COUNT(R.reply_no) AS reply_count
FROM tbl_board B
LEFT JOIN tbl_reply R
ON B.board_no = R.board_no
group by B.board_no
order by B.board_no DESC
;

select * from tbl_reply
where reply_no = 2;

select * from tbl_reply
where board_no = 101;

select * from tbl_reply
where board_no = 100;

-- 좋아요, 싫어요 기능을 위한 테이블
CREATE TABLE tbl_reaction (
      reaction_id INT(8) PRIMARY KEY AUTO_INCREMENT,
      board_no INT(8) NOT NULL,
      account VARCHAR(50) NOT NULL,
      reaction_type ENUM('LIKE', 'DISLIKE') NOT NULL,
      reaction_date DATETIME DEFAULT current_timestamp,
      CONSTRAINT fk_reaction_board FOREIGN KEY (board_no) REFERENCES tbl_board(board_no),
      CONSTRAINT fk_reaction_member FOREIGN KEY (account) REFERENCES tbl_member(account)
);


-- 회원 관리 테이블
CREATE TABLE tbl_member (
         account VARCHAR(50),
         password VARCHAR(150) NOT NULL,
         name VARCHAR(50) NOT NULL,
         email VARCHAR(100) NOT NULL UNIQUE,
         auth VARCHAR(20) DEFAULT 'COMMON',
         reg_date DATETIME DEFAULT current_timestamp,
         CONSTRAINT pk_member PRIMARY KEY (account)
);
select * from tbl_member;

-- 게시글 테이블과 댓글테이블에 회원 PK 컬럼 추가
ALTER TABLE tbl_board
ADD (account VARCHAR(50));

ALTER TABLE tbl_reply
ADD (account VARCHAR(50));

select * from tbl_board;
select * from tbl_reply;

UPDATE tbl_member
SET auth = 'ADMIN'
WHERE account = 'admin';

commit;
select * from tbl_member;

UPDATE tbl_board
SET account = 'admin'
WHERE account IS NULL
;
UPDATE tbl_reply
SET account = 'admin'
WHERE account IS NULL
;
commit;
ALTER TABLE tbl_board
ADD CONSTRAINT fk_board_member
FOREIGN KEY (account)
REFERENCES tbl_member (account)
;
SELECT *
FROM tbl_board B
LEFT OUTER JOIN tbl_member M
ON B.account = M.account
;
select
    B.board_no,
    B.title,
    B.content,
    M.name as writer,
    B.reg_date_time,
    M.account
from tbl_board B
LEFT OUTER JOIN tbl_member M
ON B.account = M.account
where board_no = 98
;

SELECT
    B.board_no,
    B.title,
    B.content,
    B.writer,
    B.reg_date_time,
    B.view_count,
    COUNT(R.reply_no) AS reply_count,
    B.account
FROM tbl_board B
         LEFT OUTER JOIN tbl_reply R
                         ON B.board_no = R.board_no
GROUP BY B.board_no
ORDER BY board_no DESC
LIMIT 0, 6
;
-- 자동로그인 관련 컬럼 추가
-- 쿠키에 저장한 값, 자동로그인 만료시간
ALTER TABLE tbl_member
ADD (session_id VARCHAR(255) default 'none');

ALTER TABLE tbl_member
ADD (limit_time DATETIME default current_timestamp);

select * from tbl_member;

-- 조회수 기록 관리 테이블
CREATE TABLE view_log (
    id INT PRIMARY KEY auto_increment,
    account VARCHAR(50),
    board_no INT,
    view_time DATETIME
);
ALTER TABLE view_log
ADD CONSTRAINT fk_member_viewlog
FOREIGN KEY (account)
REFERENCES tbl_member (account);

ALTER TABLE view_log
ADD CONSTRAINT fk_board_viewlog
FOREIGN KEY (board_no)
REFERENCES tbl_board (board_no);

select *
from view_log;

-- 좋아요 기록 관리 테이블
CREATE TABLE like_log (
          id INT PRIMARY KEY auto_increment,
          account VARCHAR(50),
          board_no INT
);
ALTER TABLE like_log
ADD CONSTRAINT fk_member_like_log
FOREIGN KEY (account)
REFERENCES tbl_member (account);

ALTER TABLE like_log
ADD CONSTRAINT fk_board_like_log
FOREIGN KEY (board_no)
REFERENCES tbl_board (board_no);

ALTER TABLE like_log
ADD reaction_date DATETIME DEFAULT current_timestamp;

# ALTER TABLE like_log
# RENAME COLUMN lik_date TO like_date;

select *
from like_log;

-- account(admin) 따라 댓글 작성자 변경
UPDATE tbl_reply
SET reply_writer = (SELECT name FROM tbl_member WHERE account = tbl_reply.account)
WHERE tbl_reply.account IS NOT NULL
;

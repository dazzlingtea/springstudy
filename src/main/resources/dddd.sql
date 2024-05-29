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

select stu_num, stu_name, kor, eng, total, A.rank, A.cnt
from (select *,
             RANK() OVER (order by average desc) AS rank,
              COUNT(*) OVER() AS cnt
      from tbl_score) A
where stu_num = 2;



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

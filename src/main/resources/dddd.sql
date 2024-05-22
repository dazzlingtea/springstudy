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

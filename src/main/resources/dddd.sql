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
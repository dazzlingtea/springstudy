package com.study.springstudy.springmvc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void test() {
        Student student = new Student();
        student.setName("홍길동");
        student.setAge(20);
        student.setGrade(3);
        System.out.println("student = " + student);
    }
}
package com.study.springstudy.core.chap04;

//@Component
public class AsianRestaurant implements Restaurant {


    private Chef chef;

    private Course course;

    public AsianRestaurant(Chef chef, Course course) {
        this.chef = chef;
        this.course = course;
    }

    @Override
    public void order() {
        System.out.println("아시안 요리를 주문합니다.");
        course.combineMenu();
        chef.cook();
    }
}

package com.study.springstudy.core.chap04;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class HotelSpringDITest {

    @Test
    void test() {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(HotelConfig.class);

        Hotel hotel = context.getBean(Hotel.class);
//        System.out.println("hotel = " + hotel);
        hotel.inform();
    }
}
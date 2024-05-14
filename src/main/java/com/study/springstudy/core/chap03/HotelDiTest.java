package com.study.springstudy.core.chap03;

import com.study.springstudy.core.chap03.config.HotelManager;
import org.junit.Test;

public class HotelDiTest {

    @Test
    public void diTest() {
//        Hotel hotel = new Hotel(
//                new AsianRestaurant(
//                        new KimuraChef(),
//                        new SushiCourse()),
//                new KimuraChef());
//        hotel.inform();

        HotelManager manager = new HotelManager();
        Hotel hotel = manager.hotel();
        hotel.inform();
    }

}
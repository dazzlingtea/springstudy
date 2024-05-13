package com.study.springstudy.chap02;

/*
 * @solution
 * - 먼저 DIP 해결하기 위해 구체적인 객체 대신
 * 추상적인 역할에 의존하게 코드를 개선
 *
 * @problem - 추상화를 했지만 여전히 의존객체를 바꾸려면
 *          코드를 직접 변경해야 한다.
 */

public class Hotel {

    // 레스토랑
//    public WesternRestaurant restaurant = new WesternRestaurant();
//    public AsianRestaurant restaurant = new AsianRestaurant();
    public Restaurant restaurant = new AsianRestaurant();

    // 헤드쉐프
//    public JannChef headChef = new JannChef();
//    public KimuraChef headChef = new KimuraChef();
    public Chef headChef = new KimuraChef();


    // 호텔을 소개하는 기능
    public void inform() {
        System.out.printf("우리 호텔의 레스토랑은 %s입니다. " +
                        "그리고 헤드쉐프는 %s입니다.\n"
                , restaurant.getClass().getSimpleName()
                , headChef.getClass().getSimpleName());

        restaurant.order();
    }
}


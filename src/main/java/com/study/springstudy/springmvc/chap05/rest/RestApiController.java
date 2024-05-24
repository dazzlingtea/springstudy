package com.study.springstudy.springmvc.chap05.rest;

import com.study.springstudy.database.chap01.Person;
import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/rest")
//@Controller
//@ResponseBody
@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final BoardService boardService;

    @GetMapping("/hello")
//    @ResponseBody // 서버가 클라이언트에게 순수한 데이터를 전달할 때 사용
    public String hello() {
        return "안녕안녕 😁";
    }

    @GetMapping("/hobby")
//    @ResponseBody
    public List<String> hobby() {
        List<String> hobbies = List.of("태권도", "장기", "댄스");
        return hobbies;
    }
    @GetMapping(value ="/person", produces = "application/json")
//    @ResponseBody
    public Person person() {
        Person p = new Person(100, "핑구", 10);
        return p;
    }

    @GetMapping("/board")
    public Map<String, Object> board() {

        List<BoardListResponseDto> list = boardService.findList(new Search());

        Map<String, Object> map = new HashMap<>();
        map.put("articles", list);
        map.put("page", new PageMaker(new Page(), boardService.getCount(new Search())));

        return map;
    }

}

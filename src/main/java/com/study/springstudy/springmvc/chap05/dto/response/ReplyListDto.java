package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap04.common.PageMaker;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
    {
       "replies" : [
            {}, {}, {}
       ]
    }
 */
public class ReplyListDto {

    /*
        [
            {RDD}, {}, {}
        ]
     */

    @Setter
    private LoginUserInfoDto loginUser;
    private PageMaker pageInfo;
    private List<ReplyDetailDto> replies;
}

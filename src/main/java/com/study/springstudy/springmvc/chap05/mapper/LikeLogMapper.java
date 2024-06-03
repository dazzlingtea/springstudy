package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.entity.LikeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeLogMapper {

    // 좋아요 추가
    void insertLike(LikeLog likeLog);

    // 좋아요 취소
    void deleteLike(@Param("account") String account,
                    @Param("bno") long bno);

    // 로그인 계정의 좋아요 조회
    LikeLog findLikeAccount(
                    @Param("account") String account,
                    @Param("bno") long bno);

    int countBoardLike();
}

package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;

    // 댓글 목록 전체조회
    public List<ReplyDetailDto> getReplies(long boardNo) {
        List<Reply> replies = replyMapper.findAll(boardNo);
        return replies.stream()
                .map(r -> new ReplyDetailDto(r))
                .collect(Collectors.toList());
    }

    // 댓글 입력
    public void register() {

    }

    // 댓글 수정
    public void modify() {}

    // 댓글 삭제
    public void delete() {}
}

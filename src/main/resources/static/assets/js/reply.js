
import { fetchReplies, replyPageClickEvent } from "./getReply.js";
import { fetchReplyPost } from "./postReply.js";
import { replyDeleteClickEvent } from "./deleteReply.js";


//===== 전역 변수 ======
export const BASE_URL = 'http://localhost:8383/api/v1/replies';


//===== 실행 코드 ======

// 댓글 목록 서버에서 불러오기
fetchReplies();

// 댓글 작성 이벤트 등록
document.getElementById('replyAddBtn').addEventListener('click', e => {
  // 댓글 등록 로직
  fetchReplyPost();
}); 

// 댓글 페이지 클릭이벤트 등록
replyPageClickEvent();
replyDeleteClickEvent();
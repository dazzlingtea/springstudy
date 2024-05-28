
import { BASE_URL } from "./reply.js";

function getRelativeTime(createAt) {
  // 현재 시간 구하기 (한국 표준시)
  const now = new Date();
  // 등록 시간 날짜타입으로 변환
  const past = new Date(createAt);

  // 사이 시간 구하기
  const diff = now - past;
  // console.log(diff);

  const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(diff / 1000 / 60);
    const hours = Math.floor(diff / 1000 / 60 / 60);
    const days = Math.floor(diff / 1000 / 60 / 60 / 24);
    const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
    const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);

    if (seconds < 60) {
        return '방금 전';
    } else if (minutes < 60) {
        return `${minutes}분 전`;
    } else if (hours < 24) {
        return `${hours}시간 전`;
    } else if (days < 7) {
        return `${days}일 전`;
    } else if (weeks < 52) {
        return `${weeks}주 전`;
    } else {
        return `${years}년 전`;
    }
}

export function renderReplies(replies) {

  // 댓글 수 렌더링
  document.getElementById('replyCnt').textContent = replies.length;

  // 댓글 목록 렌더링
  let tag = '';
  if(replies && replies.length > 0) {
    replies.forEach(({rno, writer, text, createAt}) => {
      tag += `
      <div id='replyContent' class='card-body' data-reply-id='${rno}'>
          <div class='row user-block'>
              <span class='col-md-3'>
                  <b>${writer}</b>
              </span>
              <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(createAt)}</b></span>
          </div><br>
          <div class='row'>
              <div class='col-md-9'>${text}</div>
              <div class='col-md-3 text-right'>
                  <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
                  <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
              </div>
          </div>
      </div>
      `;
      
    });
  } else {
    tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
  }
  document.getElementById('replyData').innerHTML = tag;
}

export async function fetchReplies(pageNo=1) {

  const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호
// console.log('글번호: ', bno);

  const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
  const replies = await res.json();
  
  // 댓글 목록 렌더링
  renderReplies(replies);

}



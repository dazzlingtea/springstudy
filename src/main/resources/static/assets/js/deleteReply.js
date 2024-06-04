import { fetchReplies, renderReplies, fetchInfScrollReplies } from "./getReply.js";
import { BASE_URL } from "./reply.js";
import {callApi} from "./api";

// 댓글 삭제 처리 이벤트 등록 함수

const fetchReplyDelete = async (rno) => {

  const res = await callApi(`${BASE_URL}/${rno}`, 'DELETE')
  // const res = await fetch(`${BASE_URL}/${rno}`, {
  //   method: 'DELETE'
  // });
  // if(res.status !== 200) {
  //   alert('삭제에 실패했습니다!');
  //   return;
  // }
  // const replies = await res.json();

  // append 는 2페이지부터라서... fetchInfScrollReplies로 변경
  // renderReplies(replies);
  fetchInfScrollReplies();
  window.scrollTo(0, 0); // 삭제 후 페이지 상단으로 이동
  // await fetchInfScrollReplies();
}

export function replyDeleteClickEvent() {

  const $modal = document.getElementById('replyModifyModal');

  $modal.addEventListener('show.bs.modal', function (event) {
    // const button = e.target.mathces('#replyModBtn') ? e.target : e.target.getElementById('replyModBtn');
    const button = event.relatedTarget; // 모달을 트리거한 버튼
    const $div = button.closest('#replyContent');
    const oldText = $div.querySelector('.col-md-9').textContent.trim();
    const $textArea = document.getElementById('modReplyText');
    $textArea.value = oldText;
  });

  document.getElementById('replyData').addEventListener('click', e => {
    
    console.log(e.target);
    e.preventDefault();

    if(!e.target.matches('#replyDelBtn')) return;
    if(!confirm('정말 삭제할까요??')) return;
  
      const rno = e.target.closest('#replyContent').dataset.replyId; 
      fetchReplyDelete(rno);

     /* 
    else if(e.target.matches('#replyModBtn')) {
      // 모달을 열기 전에 버튼에 데이터 설정
      const $div = e.target.closest('#replyContent');
      // 모달이 열릴 때 사용할 버튼을 설정
      $modal.relatedTarget = e.target;
      // Bootstrap 모달을 열기 (Bootstrap JavaScript가 필요)
      new bootstrap.Modal($modal).show();
      
    } */
    // else return;
  });

}
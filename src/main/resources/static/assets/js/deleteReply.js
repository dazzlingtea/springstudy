import { fetchReplies, renderReplies } from "./getReply.js";
import { BASE_URL } from "./reply.js";

const fetchReplyDelete = async (rno) => {

  const res = await fetch(`${BASE_URL}/${rno}`, {
    method: 'DELETE'
  });

  console.log('res: ', res);
  const replies = await res.json();
  console.log('json: ', replies);

  renderReplies(replies);
}

export function replyDeleteClickEvent() {
  document.getElementById('replyData').addEventListener('click', e => {
    e.preventDefault();
    
    const rno = e.target.closest('#replyContent').dataset.replyId; 
    fetchReplyDelete(rno);

  });

}
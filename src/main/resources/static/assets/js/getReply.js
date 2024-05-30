
import { BASE_URL } from "./reply.js";
import { showSpinner, hideSpinner } from "./spinner.js";

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

/*
function renderPageNow(pageNo) {
  const $pageUl = document.querySelector('.pagination');
  const pageList = Array.from($pageUl.childNodes);
  pageList.forEach(li => {
    const $aTag = li.firstElementChild

    if($aTag.textContent !== 'prev' && $aTag.textContent !== 'next' && $aTag.getAttribute('href') === ''+ pageNo){
      li.classList.add('p-active');
    };
  })
}
*/

function renderPage({ begin, end, pageInfo, prev, next }) {

  let tag = '';
  if(prev) tag += `<li class='page-item'><a class='page-link page-custom' href='${begin - 1}'>prev</a></li>`;
  
  // 페이지 번호 태그 만들기
  for (let i = begin; i <= end; i++) {
    let active = '';
    if(pageInfo.pageNo === i) active = 'p-active';
    tag += `
      <li class='page-item ${active}'>
        <a class='page-link page-custom' href='${i}'>${i}</a>
      </li>`; 
  }
  if(next) tag += `<li class='page-item'><a class='page-link page-custom' href='${end + 1}'>next</a></li>`;
  
  // 페이지 태그 ul에 붙이기
  const $pageUl = document.querySelector('.pagination');
  $pageUl.innerHTML = tag;
}

export function renderReplies({pageInfo, replies}) {

  // 댓글 수 렌더링
  document.getElementById('replyCnt').textContent = pageInfo.totalCount;

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

  // 페이지 태그 렌더링
  renderPage(pageInfo);

}

// 서버에서 댓글 목록 가져오는 비동기 요청 함수
export async function fetchReplies(pageNo=1) {

  const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호
// console.log('글번호: ', bno);

  const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
  const replyResponse = await res.json();
  // {pageInfo: {}, replies: [] }
  // 댓글 목록 렌더링
  renderReplies(replyResponse);
  // renderPageNow(pageNo);
}

// 페이지 클릭 이벤트 생성 함수
export function replyPageClickEvent() {
  document.querySelector('.pagination').addEventListener('click', e => {
    e.preventDefault();

    // console.log(e.target.getAttribute('href'));
    fetchReplies(e.target.getAttribute('href'));
  });
}

//=============== 무한 스크롤 전용 함수 ===============//

let currentPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetching = false; // 데이터 불러오는 중에는 더 가져오지 않게 제어하기 위한 논리변수
let totalReplies = 0;  // 총 댓글 수
let loadedReplies = 0;  // 로딩된 댓글 수


function appendReplies({ replies }) {

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
  document.getElementById('replyData').innerHTML += tag;

  // 로드된 댓글 수 업데이트
  loadedReplies += replies.length;
}


// 서버에서 데이터를 페칭
export async function fetchInfScrollReplies(pageNo=1) {

  if(isFetching) return; // 서버에서 데이터를 가져오는 중이면 return;
  isFetching = true;
  
  console.log("무한스크롤 fetch 중");

  const bno = document.getElementById('wrap').dataset.bno; // 게시물 글번호  
  const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
  const replyResponse = await res.json();

  if(pageNo === 1) {
    // 총 댓글 수 전역변수 값 세팅
    totalReplies = replyResponse.pageInfo.totalCount
    loadedReplies = 0; // 댓글 입력, 삭제시 다시 1페이지 로딩시 초기값으로 만들어주기
    // 댓글 수 렌더링
    document.getElementById('replyCnt').textContent = totalReplies;
    // 초기 댓글 reset
    document.getElementById('replyData').innerHTML = '';

    setupInfiniteScroll();
  }

  // 댓글 목록 렌더링
  appendReplies(replyResponse);
  currentPage = pageNo;

  isFetching = false; // 데이터를 다 가져온 후 false로 돌려놓음
  hideSpinner();
  console.log("스크롤 fetch 완료");

  // 댓글을 전부 가져왔다면 스크롤 이벤트 제거하기
  if(loadedReplies >= totalReplies) {
    window.removeEventListener('scroll', scrollHandler);
  }
}

// 스크롤 이벤트 핸들러 함수
async function scrollHandler(e) {
  // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
  // 현재창에 보이는 세로길이 + 스크롤을 내린 길이 >= 브라우저 전체 세로길이
  if(
    window.innerHeight+window.scrollY>= document.body.offsetHeight + 200
    && !isFetching
  ) {
    // console.log(e);
    // 서버에서 데이터를 비동기로 불러와야 함
    // 2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함
    console.log("스크롤 이벤트 핸들러 함수 실행"); 
    showSpinner();
    await new Promise(resolve => setTimeout(resolve, 700));
    fetchInfScrollReplies(currentPage + 1);
  }
  
}

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
  console.log("스크롤이벤트 생성 함수 실행");
  window.addEventListener('scroll', scrollHandler)
}
document.addEventListener("DOMContentLoaded", function () {
    bindEventListeners(); // 초기 이벤트 등록
});

function bindEventListeners() {
 

    // 검색 버튼 클릭 이벤트 등록
    const searchBtn = document.getElementById("searchBtn");
    if (searchBtn) {
        searchBtn.addEventListener("click", handleSearch);
    }

    // 페이지네이션 버튼 클릭 이벤트 등록
    const paginationButtons = document.querySelectorAll(".pagination-btn");
    paginationButtons.forEach(button => {
        button.addEventListener("click", handlePagination);
    });
}


function handleSearch() {
    const searchInput = document.getElementById("searchInput");
    const sortSelect = document.getElementById("sortSelect");
    const query = searchInput ? searchInput.value.trim() : "";
    let sort = sortSelect ? sortSelect.value : "";

    if (sort === "birth") {
        sort += ",desc";
    } else if (sort === "name") {
        sort += ",asc";
    }

    const url = `/admin/frontend/members?name=${query}&sort=${sort}`;
    console.log(url);
    loadPage(url); // 페이지 로드
}

function handlePagination(event) {
    event.preventDefault(); // 기본 버튼 동작 방지
    const page = event.target.getAttribute("data-page");
    if (page) {
        const currentUrl = new URL(window.location.href); // 현재 URL 가져오기
        const params = new URLSearchParams(currentUrl.search); // URL의 쿼리 파라미터 가져오기

        // 'page' 파라미터를 새로운 페이지 값으로 업데이트
        params.set('page', page-1);

        // 정렬 및 검색 쿼리 파라미터가 있을 경우 그대로 유지
        const url = `${currentUrl.pathname}?${params.toString()}`;
        loadPage(url); // 페이지 로드
    }
}


function loadPage(url) {
    window.location.href = url;
}


document.addEventListener("DOMContentLoaded", function () {
    // fetch("header.html")
    //     .then((response) => response.text())
    //     .then((data) => {
    //         document.getElementById("header-placeholder").innerHTML = data;
    //     })
    //     .catch((error) => console.error("Error loading header:", error));
    //
    // fetch("footer.html")
    //     .then((response) => response.text())
    //     .then((data) => {
    //         document.getElementById("footer-placeholder").innerHTML = data;
    //     })
    //     .catch((error) => console.error("Error loading footer:", error));

    const jwtToken = localStorage.getItem('jwt_token');  // localStorage에서 jwt-token을 가져옴
    const loginBtn = document.getElementById('login-btn');
    const logoutBtn = document.getElementById('logout-btn');

    if (jwtToken && jwtToken !== 'null' && jwtToken !== '') {
        // 로그인 상태일 때
        loginBtn.style.display = 'none';  // 로그인 버튼 숨기기
        logoutBtn.style.display = 'inline-block';  // 로그아웃 버튼 보이기
    } else {
        loginBtn.style.display = 'inline-block';  // 로그인 버튼 보이기
        logoutBtn.style.display = 'none';  // 로그아웃 버튼 숨기기
    }

    // 로그아웃 버튼 클릭 시 POST 요청 보내기
    logoutBtn.addEventListener('click', function (event) {
        axios.post('/backend/auth/logout', {})
            .then(response => {
                if (response.status === 200) {
                    // 로그아웃 성공 시 처리
                    localStorage.removeItem('jwt_token');  // localStorage에서 jwt_token 제거
                    loginBtn.style.display = 'inline-block';  // 로그인 버튼 보이기
                    logoutBtn.style.display = 'none';  // 로그아웃 버튼 숨기기
                    alert("로그아웃 성공!");
                    window.location.href = "/main";  // 페이지 새로고침
                }
            })
    });
});
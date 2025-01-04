
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
    const logoutBtn = document.getElementById('logout-btn');

    // 로그아웃 버튼 클릭 시 POST 요청 보내기
    logoutBtn.addEventListener('click', function (event) {
        axios.post('/auth/logout', {})
            .then(response => {
                if (response.status === 200) {
                    window.location.href = "/frontend/main";  // 페이지 새로고침
                }
            })
    });
});
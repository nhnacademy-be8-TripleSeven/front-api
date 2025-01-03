document.addEventListener('DOMContentLoaded', function () {
    const loginBtn = document.getElementById('login-post-btn');
    if (loginBtn) {
        loginBtn.addEventListener('click', handleLogin);
    }

});

// JWT 토큰 저장 함수
function setToken(token) {
    localStorage.setItem('jwt_token', token);
}

function handleLogin(event) {
    event.preventDefault();  // 기본 폼 제출을 막음

    const loginId = document.getElementById('user-id').value;
    const password = document.getElementById('user-password').value;

    // axios로 로그인 요청을 보냄
    axios.post('/auth/admin/login', {
        loginId: loginId,
        password: password
    })
        .then(response => {
            // 로그인 성공 후 처리
            console.log('로그인 성공', response);
            window.location.href = "/admin/frontend/members"
        })
}

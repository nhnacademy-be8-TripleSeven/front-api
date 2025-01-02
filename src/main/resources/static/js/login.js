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
    axios.post('/auth/login', {
        loginId: loginId,
        password: password
    })
        .then(response => {
            // 로그인 성공 후 처리
            console.log('로그인 성공', response);
            setToken(response.data.accessToken);  // 받은 토큰을 로컬 스토리지에 저장
            window.location.href = '/frontend/main';  // 로그인 후 리디렉션
        })
}

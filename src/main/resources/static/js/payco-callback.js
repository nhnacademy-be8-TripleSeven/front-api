document.addEventListener('DOMContentLoaded', function () {
    // HTML에서 clientId를 읽어옴
    const clientIdElement = document.getElementById('loginId');
    const clientId = clientIdElement.getAttribute('data-login-id');

    if (clientId) {
        axios.post(`/auth/payco/login`, null, {

            params: {
                clientId : clientId
            }
        })
            .then(response => {
                const tokenInfo = response.data;

                // JWT를 로컬스토리지에 저장
                setToken(tokenInfo.accessToken);
                // 메인 페이지로 리디렉션
                window.location.href = '/frontend/main';
            })
            .catch(error => {
                console.error('로그인 요청 중 오류 발생:', error);
                window.location.href = '/frontend/login'; // 실패 시 로그인 페이지로 리디렉션
            });
    } else {
        alert('클라이언트 ID가 제공되지 않았습니다. 로그인 페이지로 돌아갑니다.');
        window.location.href = '/frontend/login';
    }
});

// JWT 토큰 저장 함수
function setToken(token) {
    localStorage.setItem('jwt_token', token);
}

document.addEventListener('DOMContentLoaded', function () {
    const loginBtn = document.getElementById('login-post-btn');
    if (loginBtn) {
        loginBtn.addEventListener('click', handleLogin);
    }
});


function showTab(tabId) {
    const tabs = document.querySelectorAll('.tab');
    const contents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => tab.classList.remove('active'));
    contents.forEach(content => content.classList.remove('active'));

    document.querySelector(`.tab[onclick="showTab('${tabId}')"]`).classList.add('active');
    document.getElementById(tabId).classList.add('active');
}

// 로그인 핸들러 함수 (async 함수로 변경)
async function handleLogin(event) {
    event.preventDefault();  // 기본 폼 제출을 막음

    const loginId = document.getElementById('user-id').value;
    const password = document.getElementById('user-password').value;

    try {
        // axios로 로그인 요청을 보냄
        const response = await axios.post('/auth/login', {
            loginId: loginId,
            password: password
        });

        // 로그인 성공 시 리디렉션
        window.location.href = '/frontend/main';

    } catch (error) {
        const errorMessage = error.response.data.message;

        // 휴면 계정인 경우
        if (errorMessage && errorMessage.includes("휴면 계정입니다")) {
            try {
                // 휴면 계정 해제 요청을 보냄
                const unlockResponse = await axios.post("/members/unlock/account", {}, {
                    params: {
                        loginId: loginId
                    }
                });

                // 성공적으로 인증코드 발송된 경우
                alert(error.response.data.message + " 이메일에 발송된 인증코드를 확인해주세요");
            } catch (unlockError) {
                // 휴면 계정 해제 요청 실패 처리
                console.error("휴면 계정 해제 요청 실패", unlockError);
                alert("휴면 계정 해제 요청에 실패했습니다.");
            }
        } else if (errorMessage && errorMessage.includes("탈퇴")) {
            alert(errorMessage);
        }

        else {
            // 다른 에러 처리
            console.error("로그인 에러", error);
            alert("로그인에 실패했습니다.");
        }
    }
}

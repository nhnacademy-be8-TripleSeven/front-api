function showTab(tabId) {
    const tabs = document.querySelectorAll('.tab');
    const contents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => tab.classList.remove('active'));
    contents.forEach(content => content.classList.remove('active'));

    document.querySelector(`.tab[onclick="showTab('${tabId}')"]`).classList.add('active');
    document.getElementById(tabId).classList.add('active');
}

function findAccountFromEmail(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    const email = document.getElementById('email').value;

    if (!email) {
        alert("이메일 주소를 입력해주세요.");
        return;
    }

    // Axios GET 요청
    axios.get('/members/account-id/email', {
        params: {
            email: email
        }
    })
        .then(response => {
            // 성공적으로 아이디를 찾은 경우 처리
            console.log(response);

            alert(`아이디: ` + response.data.loginId + " 입니다.");
            window.location.href = '/frontend/login';
        });
}

function findAccountFromPhone(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    const phoneNumber = document.getElementById('phoneNumber').value;

    if (!phoneNumber) {
        alert("핸드폰 번호를 입력해주세요.");
        return;
    }

    // Axios GET 요청
    axios.get('/members/account-id/phone', {
        params: {
            phoneNumber: phoneNumber
        }
    })
        .then(response => {
            // 성공적으로 아이디를 찾은 경우 처리
            console.log(response);
            alert(`아이디: ` + response.data.loginId + " 입니다.");
            window.location.href = '/frontend/login';
        });
}

// 비밀번호 찾기 폼 제출 이벤트 처리
function resetPassword(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    const email = document.getElementById('email').value;
    const loginId = document.getElementById('loginId').value;

    if (!email || !loginId) {
        alert("이메일 주소와 로그인 아이디를 모두 입력해주세요.");
        return;
    }

    // Axios POST 요청
    axios.post('/members/reset/password', null, {
        params : {
        email: email,
        loginId: loginId
    }})
        .then(response => {
            // 비밀번호 변경 요청 성공
            alert("이메일 발송 성공! 이메일을 확인해주세요.");
            window.location.href = '/frontend/login'; // 로그인 페이지로 리다이렉션
        })
        .catch(error => {
            console.error('비밀번호 찾기 실패:', error);
            alert("비밀번호 찾기 요청에 실패했습니다. 입력 정보를 확인해주세요.");
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const emailForm = document.getElementById('findForm');
    if (emailForm) {
        emailForm.addEventListener('submit', findAccountFromEmail);
    }

    const phoneForm = document.getElementById('emailFindForm');
    if (phoneForm) {
        phoneForm.addEventListener('submit', findAccountFromPhone);
    }

    // 비밀번호 찾기 폼 이벤트 처리
    const resetPasswordForm = document.getElementById('resetPasswordForm');
    if (resetPasswordForm) {
        resetPasswordForm.addEventListener('submit', resetPassword);
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const togglePassword = document.getElementById('toggle-password');
    const toggleConfirmPassword = document.getElementById('toggle-confirm-password');
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirm-password');

    const toggleVisibility = (field, icon) => {
        const isPassword = field.type === 'password';
        field.type = isPassword ? 'text' : 'password';
        icon.src = isPassword ? '/image/icons/Eye.png' : '/image/icons/EyeSlash.png';
    };

    togglePassword.addEventListener('click', () =>
        toggleVisibility(passwordField, togglePassword.querySelector('img'))
    );

    toggleConfirmPassword.addEventListener('click', () =>
        toggleVisibility(confirmPasswordField, toggleConfirmPassword.querySelector('img'))
    );

    // 이메일 인증번호 발송 버튼 클릭 시 호출
    const sendVerificationBtn = document.getElementById('send-verification-btn');
    sendVerificationBtn.addEventListener('click', sendVerificationCode);

    // 인증번호 확인 버튼 클릭 시 호출
    const verifyBtn = document.getElementById('verify-btn');
    verifyBtn.addEventListener('click', verifyCode);
});

// 이메일 인증번호 발송
function sendVerificationCode() {
    const email = document.getElementById('email').value;

    // 이메일 유효성 검사 (선택적)
    if (!validateEmail(email)) {
        alert('잘못된 이메일 형식입니다.');
        return;
    }

    console.log("test11")

    // axios 요청으로 변경
    axios.post(`/gateway/members/verify/emails/${encodeURIComponent(email)}`)
        .then(response => {
            alert('인증번호가 이메일로 전송되었습니다.');
        })
        .catch(error => {
            if (error.response) {
                if (error.response.status === 400) {
                    alert('잘못된 이메일 형식입니다.');
                } else if (error.response.status === 409) {
                    alert('이미 가입된 이메일입니다.');
                } else {
                    alert('서버 오류가 발생했습니다.');
                }
            } else {
                alert('네트워크 오류가 발생했습니다.');
            }
        });
}

// 이메일 형식 검사 함수
function validateEmail(email) {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(String(email).toLowerCase());
}

// 인증번호 확인
function verifyCode() {
    const email = document.getElementById('email').value;
    const verificationCode = document.getElementById('verification-code').value;

    // axios 요청으로 변경
    axios.post(`/gateway/members/verify/emails/${encodeURIComponent(email)}/${verificationCode}`)
        .then(response => {
            alert('인증번호가 일치합니다.');
        })
        .catch(error => {
            if (error.response) {
                if (error.response.status === 400) {
                    alert('잘못된 이메일 또는 인증 코드입니다.');
                } else if (error.response.status === 401) {
                    alert('인증 코드가 일치하지 않습니다.');
                } else {
                    alert('서버 오류가 발생했습니다.');
                }
            } else {
                alert('네트워크 오류가 발생했습니다.');
            }
        });
}

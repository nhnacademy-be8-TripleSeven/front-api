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

    // 회원가입 버튼 클릭 시 호출
    const signupForm = document.getElementById('signup-form');
    signupForm.addEventListener('submit', handleSignUp);
});

// 이메일 인증번호 발송
function sendVerificationCode() {
    const email = document.getElementById('email').value;

    // 이메일 유효성 검사 (선택적)
    if (!validateEmail(email)) {
        alert('잘못된 이메일 형식입니다.');
        return;
    }

    // axios 요청으로 변경
    axios.post(`/backend/members/verify/emails/${encodeURIComponent(email)}`)
        .then(response => {
            alert('인증번호가 이메일로 전송되었습니다.');
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
    axios.post(`/backend/members/verify/emails/${encodeURIComponent(email)}/${verificationCode}`)
        .then(response => {
            alert('인증번호가 일치합니다.');
        });
}

// 회원가입 처리
function handleSignUp(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    // FormData 객체를 사용하여 폼 데이터를 가져옵니다
    const formData = new FormData(event.target);

    // birth 값 포맷팅 (yyyy-mm-dd)
    const birth = formData.get("birthdate");
    const formattedBirth = formatDate(birth); // 포맷팅된 날짜를 가져옵니다

    const requestBody = {
        loginId: formData.get("username"),
        password: formData.get("password"),
        name: formData.get("name"),
        email: formData.get("email"),
        phoneNumber: formData.get("phoneNumber"),
        birth: formattedBirth, // 포맷팅된 날짜를 넣습니다
        gender: formData.get("gender"),
    };

    // axios 요청
    axios.post("/backend/members", requestBody, {
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            if (response.status === 201) {
                alert("회원가입이 성공적으로 완료되었습니다.");
                window.location.href = "/login"; // 로그인 페이지로 이동
            }
        })
}

// 날짜 포맷 변환 함수
function formatDate(dateString) {
    const date = new Date(dateString);

    // yyyy-mm-dd 형식으로 변환
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
}

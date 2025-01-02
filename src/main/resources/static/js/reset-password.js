document.addEventListener('DOMContentLoaded', function () {
    const resetPasswordForm = document.getElementById('resetPasswordForm');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    resetPasswordForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 비밀번호 확인
        if (newPasswordInput.value !== confirmPasswordInput.value) {
            alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
            return;
        }

        // 폼 데이터 준비
        const formData = {
            email: document.getElementById('email').value,
            code: document.getElementById('code').value,
            newPassword: newPasswordInput.value
        };

        // Axios PUT 요청
        axios.put('/backend/members/reset/password', formData)
            .then(response => {
                alert("비밀번호가 성공적으로 변경되었습니다.");
                window.location.href = '/login'; // 로그인 페이지로 리디렉션
            })
    });
});

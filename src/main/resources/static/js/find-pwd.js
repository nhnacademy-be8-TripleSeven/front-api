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
    axios.get('/backend/members/account-id/email', {
        params: {
            email: email
        }
    })
        .then(response => {
            // 성공적으로 아이디를 찾은 경우 처리
            console.log(response)

            alert(`아이디: ` + response.data.loginId + " 입니다.");
            window.location.href='/login'
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
    axios.get('/backend/members/account-id/phone', {
        params: {
            phoneNumber: phoneNumber
        }
    })
        .then(response => {
            // 성공적으로 아이디를 찾은 경우 처리
            console.log(response)
            alert(`아이디: ` + response.data.loginId + " 입니다.");
            window.location.href='/login'
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
});
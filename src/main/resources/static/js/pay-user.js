document.getElementById("pay-button").addEventListener("click", function () {
    const recipient = document.getElementById("recipient").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;
    const paymentMethod = document.getElementById("payment-method").value;
    const cardNumber = document.getElementById("card-number").value;

    if (!recipient || !phone || !address || !cardNumber) {
        alert("모든 필드를 채워주세요.");
        return;
    }

    alert(
        `결제 정보 확인\n\n받는 사람: ${recipient}\n전화번호: ${phone}\n주소: ${address}\n결제 수단: ${paymentMethod}\n카드 번호: ${cardNumber}`
    );
});
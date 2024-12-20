// HTML이 로드된 후 실행
document.addEventListener("DOMContentLoaded", () => {
    const numberBox = document.getElementById("number-box");

    // max 제한 동작 확인
    numberBox.addEventListener("input", () => {
        if (numberBox.value > numberBox.max) {
            numberBox.value = numberBox.max; // 최대값으로 고정
        }
        if (numberBox.value < numberBox.min) {
            numberBox.value = numberBox.min; // 최소값으로 고정
        }
    });
});
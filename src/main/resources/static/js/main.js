let slideIndex = 0; // 현재 슬라이드 인덱스

function nextSlide(sliderId) {
    const container = document.querySelector(`#${sliderId} .slides`);
    const slides = container.querySelectorAll('.slide');
    const slideWidth = slides[0].clientWidth;

    slideIndex++;
    container.style.transition = "transform 0.5s ease-in-out";
    container.style.transform = `translateX(-${slideIndex * slideWidth}px)`;

    // 끝에 도달하면 처음으로 돌아가기
    if (slideIndex === slides.length - 3) {
        setTimeout(() => {
            container.style.transition = "none";
            slideIndex = 0; // 첫 번째 슬라이드로 이동
            container.style.transform = `translateX(-${slideIndex * slideWidth}px)`;
        }, 500); // 애니메이션 지속 시간 (0.5초) 후 실행
    }
}

function prevSlide(sliderId) {
    const container = document.querySelector(`#${sliderId} .slides`);
    const slides = container.querySelectorAll('.slide');
    const slideWidth = slides[0].clientWidth;

    slideIndex--;
    container.style.transition = "transform 0.5s ease-in-out";
    container.style.transform = `translateX(-${slideIndex * slideWidth}px)`;

    // 처음에 도달하면 끝으로 돌아가기
    if (slideIndex < 0) {
        setTimeout(() => {
            container.style.transition = "none";
            slideIndex = slides.length - 4; // 마지막 슬라이드로 이동
            container.style.transform = `translateX(-${slideIndex * slideWidth}px)`;
        }, 500); // 애니메이션 지속 시간 (0.5초) 후 실행
    }
}
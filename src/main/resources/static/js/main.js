document.addEventListener('DOMContentLoaded', () => {
    // 슬라이드 초기화
    const sliders = document.querySelectorAll('.slider');
    sliders.forEach((slider) => {
        let slideIndex = 0;
        const slides = slider.querySelector('.slides');
        const slideItems = slides.querySelectorAll('.slide');
        const slideWidth = slideItems[0].clientWidth;

        const nextSlide = () => {
            slideIndex++;
            if (slideIndex >= slideItems.length - 3) {
                slideIndex = 0; // 처음으로 돌아가기
            }
            slides.style.transition = "transform 0.5s ease-in-out";
            slides.style.transform = `translateX(-${slideIndex * slideWidth}px)`;
        };

        const prevSlide = () => {
            slideIndex--;
            if (slideIndex < 0) {
                slideIndex = slideItems.length - 4; // 마지막으로 돌아가기
            }
            slides.style.transition = "transform 0.5s ease-in-out";
            slides.style.transform = `translateX(-${slideIndex * slideWidth}px)`;
        };

        slider.querySelector('.arrow.right').addEventListener('click', nextSlide);
        slider.querySelector('.arrow.left').addEventListener('click', prevSlide);
    });

    // 탭 기능
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');

    tabs.forEach((tab, index) => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            tab.classList.add('active');
            tabContents[index].classList.add('active');
        });
    });
});
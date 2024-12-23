document.addEventListener('DOMContentLoaded', () => {
    // 초기 슬라이더 로드
    initializeSlider('slider1', '/api/books/monthly');
    initializeSlider('slider2', '/api/books/recommendations');

    // 초기 활성 탭 로드
    const activeTabButton = document.querySelector('.tab.active');
    if (activeTabButton) {
        const type = getTypeFromTab(activeTabButton.dataset.tab);
        const contentElement = document.querySelector(`#${type}`);
        if (contentElement) {
            fetchTabContent(type, contentElement);
        }
    }
});

// 전역 변수 선언
const sliderCache = {};
const tabCache = {};
let slideIndex = 0;

// 탭 ID와 타입 매핑
function getTypeFromTab(tabId) {
    const typeIdMap = {
        tab1: 'ITEMEDITORCHOICE',
        tab2: 'EBOOK',
        tab3: 'FOREIGN'
    };
    return typeIdMap[tabId] || '';
}

// 슬라이더 초기화
function initializeSlider(sliderId, apiUrl) {
    const sliderElement = document.querySelector(`#${sliderId} .slides`);
    if (!sliderElement) return;

    if (sliderCache[sliderId]) {
        renderSlides(sliderElement, sliderCache[sliderId]);
        updateSliderButtons(sliderId, slideIndex);
        return;
    }

    fetch(apiUrl)
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        sliderCache[sliderId] = data; // data는 배열로 반환됨
        renderSlides(sliderElement, data);
        updateSliderButtons(sliderId, slideIndex);
    })
    .catch(error => {
        console.error(`Error loading slider data from ${apiUrl}:`, error);
        sliderElement.innerHTML = '<p>슬라이더 데이터를 불러오는 데 실패했습니다.</p>';
    });
}

// 슬라이더 버튼 상태 업데이트
function updateSliderButtons(sliderId, slideIndex) {
    const slides = document.querySelectorAll(`#${sliderId} .slide`);
    const prevButton = document.querySelector(`#${sliderId} .arrow.left`);
    const nextButton = document.querySelector(`#${sliderId} .arrow.right`);

    prevButton.disabled = slideIndex === 0;
    nextButton.disabled = slideIndex === slides.length - 1;
}

// 슬라이더 콘텐츠 렌더링
function renderSlides(sliderElement, books) {
    sliderElement.innerHTML = books.length === 0
        ? '<p>도서 정보가 없습니다.</p>'
        : '';
    books.forEach(book => {
        const slide = document.createElement('div');
        slide.classList.add('slide');
        slide.innerHTML = createBookHTML(book);
        sliderElement.appendChild(slide);
    });
}

// 탭 콘텐츠 로드
function fetchTabContent(type, contentElement) {
    if (tabCache[type]) {
        renderTabContent(contentElement, tabCache[type]);
        return;
    }

    fetch(`/api/books/type/${type}`)
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        tabCache[type] = data; // data는 배열로 반환됨
        renderTabContent(contentElement, data);
    })
    .catch(error => {
        console.error(`Error loading tab content for type ${type}:`, error);
        contentElement.innerHTML = '<p>탭 데이터를 불러오는 데 실패했습니다.</p>';
    });
}

// 탭 콘텐츠 렌더링
function renderTabContent(contentElement, books) {
    contentElement.innerHTML = books.length === 0
        ? '<p>현재 도서 정보가 없습니다.</p>'
        : '';
    books.forEach(book => {
        const bookItem = document.createElement('div');
        bookItem.classList.add('book-item');
        bookItem.innerHTML = createBookHTML(book);
        contentElement.appendChild(bookItem);
    });
}

// 슬라이더 이동 함수
function prevSlide(sliderId) {
    const slider = document.querySelector(`#${sliderId} .slides`);
    if (slider && slider.children.length > 1) {
        slider.insertBefore(slider.lastElementChild, slider.firstElementChild);
    }
}

function nextSlide(sliderId) {
    const slider = document.querySelector(`#${sliderId} .slides`);
    if (slider && slider.children.length > 1) {
        slider.appendChild(slider.firstElementChild);
    }
}

// 공통 도서 HTML 생성 함수
function createBookHTML(book) {
    return `
        <a href="#"><img src="${book.coverUrl}" alt="${book.title}" onerror="this.src='/image/default-book-cover.png'"></a>
        <p>${book.title}</p>
        <p>출판사: ${book.publisher}</p>
        <p>가격: <span style="text-decoration: line-through;">${book.regularPrice.toLocaleString()}원</span>
        <strong>${book.salePrice.toLocaleString()}원</strong></p>
        <p>저자: ${book.creator.join(', ')}</p>
    `;
}

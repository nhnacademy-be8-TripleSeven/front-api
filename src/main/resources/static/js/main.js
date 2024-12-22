document.addEventListener('DOMContentLoaded', () => {
    // 초기 슬라이더 로드
    initializeSlider('slider1', '/books/monthly');
    initializeSlider('slider2', '/books/recommendations');

    // 초기 활성 탭 로드
    const activeTabButton = document.querySelector('.tab.active');
    if (activeTabButton) {
        const type = getTypeFromTab(activeTabButton.dataset.tab);
        const contentElement = document.querySelector(`#${type}`);
        if (contentElement) {
            fetch(`/books/type/${type}`)
            .then(response => response.json())
            .then(data => {
                tabCache[type] = data.content; // 캐싱
                renderTabContent(contentElement, data.content);
            })
            .catch(error => console.error('Error loading tab content:', error));
        }
    }
});

function getTypeFromTab(tabId) {
    const typeIdMap = {
        tab1: 'ITEMEDITORCHOICE',
        tab2: 'EBOOK',
        tab3: 'FOREIGN'
    };
    return typeIdMap[tabId] || '';
}

function initializeSlider(sliderId, apiUrl) {
    const sliderElement = document.querySelector(`#${sliderId} .slides`);
    if (!sliderElement) return;

    if (sliderCache[sliderId]) {
        renderSlides(sliderElement, sliderCache[sliderId]);
        updateSliderButtons(sliderId);
        return;
    }

    fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        sliderCache[sliderId] = data.content;
        renderSlides(sliderElement, data.content);
        updateSliderButtons(sliderId);
    })
    .catch(error => console.error('Error loading slider data:', error));
}

function updateSliderButtons(sliderId) {
    const slides = document.querySelectorAll(`#${sliderId} .slide`);
    const prevButton = document.querySelector(`#${sliderId} .arrow.left`);
    const nextButton = document.querySelector(`#${sliderId} .arrow.right`);
    const disable = slides.length <= 1;

    prevButton.disabled = disable;
    nextButton.disabled = disable;
}

function renderSlides(sliderElement, books) {
    sliderElement.innerHTML = '';
    books.forEach(book => {
        const slide = document.createElement('div');
        slide.classList.add('slide');
        slide.innerHTML = `
      <a href="#"><img src="${book.coverUrl}" alt="${book.title}" onerror="this.src='/image/default-book-cover.png'"></a>
      <p>${book.title}</p>
      <p>출판사: ${book.publisher}</p>
      <p>가격: <span style="text-decoration: line-through;">${book.regularPrice.toLocaleString()}원</span>
      <strong>${book.salePrice.toLocaleString()}원</strong></p>
    `;
        sliderElement.appendChild(slide);
    });
}

function renderTabContent(contentElement, books) {
    contentElement.innerHTML = '';
    books.forEach(book => {
        const bookItem = document.createElement('div');
        bookItem.classList.add('book-item');
        bookItem.innerHTML = `
      <a href="#"><img src="${book.coverUrl}" alt="${book.title}" onerror="this.src='/image/default-book-cover.png'"></a>
      <p class="title">${book.title}</p>
      <p>${book.publisher}</p>
      <p>가격: <span style="text-decoration: line-through;">${book.regularPrice.toLocaleString()}원</span>
      <strong>${book.salePrice.toLocaleString()}원</strong></p>
    `;
        contentElement.appendChild(bookItem);
    });
}
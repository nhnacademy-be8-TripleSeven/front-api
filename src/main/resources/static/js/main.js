


// document.addEventListener('DOMContentLoaded', () => {
//     // 초기 슬라이더 로드
//     initializeSlider('slider1', '/api/books/monthly');
//     initializeSlider('slider2', '/api/books/type/BLOGBEST');
//
//     // 초기 활성 탭 로드
//     const activeTabButton = document.querySelector('.tab.active');
//     if (activeTabButton) {
//         const type = getTypeFromTab(activeTabButton.dataset.tab);
//         const contentElement = document.querySelector(`#${type}`);
//         if (contentElement) {
//             fetchTabContent(type, contentElement);
//         }
//     }
//
//     // 탭 클릭 이벤트 핸들러 추가
//     const tabs = document.querySelectorAll('.tab');
//     tabs.forEach(tab => {
//         tab.addEventListener('click', () => {
//             // 모든 탭에서 active 클래스 제거
//             tabs.forEach(t => t.classList.remove('active'));
//
//             // 현재 클릭된 탭에 active 클래스 추가
//             tab.classList.add('active');
//
//             // 모든 탭 콘텐츠 숨기기
//             const allTabContents = document.querySelectorAll('.tab-content');
//             allTabContents.forEach(content => content.classList.remove('active'));
//
//             // 클릭된 탭과 연결된 콘텐츠 보이기
//             const tabType = getTypeFromTab(tab.dataset.tab);
//             const targetContent = document.getElementById(tabType);
//             if (targetContent) {
//                 targetContent.classList.add('active');
//
//                 // 해당 탭의 데이터를 로드
//                 fetchTabContent(tabType, targetContent);
//             }
//         });
//     });
// });
//
// // 전역 변수 선언
// const sliderCache = {};
// const tabCache = {};
// let slideIndex = 0;
//
// // 탭 ID와 타입 매핑
// function getTypeFromTab(tabId) {
//     const typeIdMap = {
//         tab1: 'ITEMEDITORCHOICE',
//         tab2: 'EBOOK',
//         tab3: 'FOREIGN'
//     };
//     return typeIdMap[tabId] || '';
// }
//
// // 슬라이더 초기화
// function initializeSlider(sliderId, apiUrl) {
//     const sliderElement = document.querySelector(`#${sliderId} .slides`);
//     if (!sliderElement) return;
//
//     if (sliderCache[sliderId]) {
//         renderSlides(sliderElement, sliderCache[sliderId]);
//         updateSliderButtons(sliderId, slideIndex);
//         return;
//     }
//
//     fetch(apiUrl)
//     .then(response => {
//         if (!response.ok) {
//             throw new Error(`HTTP error! Status: ${response.status}`);
//         }
//         return response.json();
//     })
//     .then(data => {
//         sliderCache[sliderId] = data; // data는 배열로 반환됨
//         renderSlides(sliderElement, data);
//         updateSliderButtons(sliderId, slideIndex);
//     })
//     .catch(error => {
//         console.error(`Error loading slider data from ${apiUrl}:`, error);
//         sliderElement.innerHTML = '<p>슬라이더 데이터를 불러오는 데 실패했습니다.</p>';
//     });
// }
//
// // 슬라이더 버튼 상태 업데이트
// function updateSliderButtons(sliderId, slideIndex) {
//     const slides = document.querySelectorAll(`#${sliderId} .slide`);
//     const prevButton = document.querySelector(`#${sliderId} .arrow.left`);
//     const nextButton = document.querySelector(`#${sliderId} .arrow.right`);
//
//     if (slides.length <= 1) {
//         prevButton.disabled = true;
//         nextButton.disabled = true;
//     } else {
//         prevButton.disabled = slideIndex === 0;
//         nextButton.disabled = slideIndex === slides.length - 1;
//     }
// }
//
//
// // 슬라이더 콘텐츠 렌더링
// function renderSlides(sliderElement, books) {
//     // 슬라이더 초기화
//     sliderElement.innerHTML = books.length === 0
//         ? '<p>도서 정보가 없습니다.</p>'
//         : '';
//
//     // 슬라이드 데이터 렌더링
//     if (books.length > 0) {
//         books.forEach(book => {
//             const slide = document.createElement('div');
//             slide.classList.add('slide');
//             slide.innerHTML = createBookHTML(book);
//             sliderElement.appendChild(slide);
//         });
//     }
// }
//
//
// // 탭 콘텐츠 로드
// function fetchTabContent(type, contentElement) {
//     const cache = tabCache[type];
//     const now = Date.now();
//
//     // 캐싱된 데이터가 유효한 경우 사용
//     if (cache && now - cache.timestamp < 5 * 60 * 1000) { // 캐시 유효기간: 5분
//         renderTabContent(contentElement, cache.data);
//         return;
//     }
//
//     // 로딩 중 메시지 표시
//     contentElement.innerHTML = '<p>로딩 중...</p>';
//
//     // API 호출
//     fetch(`/api/books/type/${type}`)
//     .then(response => {
//         if (!response.ok) {
//             throw new Error(`HTTP error! Status: ${response.status}`);
//         }
//         return response.json();
//     })
//     .then(data => {
//         // 캐싱
//         tabCache[type] = { data, timestamp: Date.now() };
//
//         // 콘텐츠 렌더링
//         renderTabContent(contentElement, data);
//     })
//     .catch(error => {
//         console.error(`Error loading tab content for type ${type}:`, error);
//         contentElement.innerHTML = '<p>탭 데이터를 불러오는 데 실패했습니다.</p>';
//     });
// }
//
//
//
// // 탭 콘텐츠 렌더링
// function renderTabContent(contentElement, books) {
//     // 콘텐츠 초기화
//     contentElement.innerHTML = books.length === 0
//         ? '<p>도서 정보가 없습니다.</p>'
//         : '';
//
//     // `book-grid` 생성 및 도서 데이터 추가
//     const bookGrid = document.createElement('div');
//     bookGrid.classList.add('book-grid');
//
//     books.forEach(book => {
//         const bookItem = document.createElement('div');
//         bookItem.classList.add('book-item');
//         bookItem.innerHTML = createBookHTML(book); // 도서 HTML 생성
//         bookGrid.appendChild(bookItem); // `book-grid`에 추가
//     });
//
//     // 생성된 `book-grid`를 콘텐츠 요소에 추가
//     contentElement.appendChild(bookGrid);
// }
//
//
// // 슬라이더 이동 함수
// function prevSlide(sliderId) {
//     const slider = document.querySelector(`#${sliderId} .slides`);
//     if (slider && slider.children.length > 1) {
//         // 마지막 요소를 첫 번째 요소 앞으로 이동
//         const lastChild = slider.lastElementChild;
//         const firstChild = slider.firstElementChild;
//
//         if (lastChild && firstChild) {
//             slider.insertBefore(lastChild, firstChild);
//             console.log(`Moved last slide to the front for slider: ${sliderId}`);
//         } else {
//             console.error('Unable to find last or first child elements in the slider.');
//         }
//     } else {
//         console.error('Slider is empty or does not have enough children.');
//     }
// }
//
//
// function nextSlide(sliderId) {
//     const slider = document.querySelector(`#${sliderId} .slides`);
//     if (slider && slider.children.length > 1) {
//         slider.appendChild(slider.firstElementChild);
//     }
// }
//
//
// // 공통 도서 HTML 생성 함수
// function createBookHTML(book) {
//     const title = book.title || '제목 없음';
//     const coverUrl = book.coverUrl || '/image/default-book-cover.png';
//     const publisher = book.publisher || '알 수 없음';
//     const regularPrice = book.regularPrice || 0;
//     const salePrice = book.salePrice || 0;
//     const creator = book.creator && book.creator.length > 0 ? book.creator.join(', ') : '알 수 없음';
//
//     return `
//         <a href="#"><img src="${coverUrl}" alt="${title}" onerror="this.src='/image/default-book-cover.png'"></a>
//         <p>${title}</p>
//         <p>출판사: ${publisher}</p>
//         <p>가격: <span style="text-decoration: line-through;">${regularPrice.toLocaleString()}원</span>
//         <strong>${salePrice.toLocaleString()}원</strong></p>
//         <p>저자: ${creator}</p>
//     `;
// }

document.addEventListener('DOMContentLoaded', function () {
  // Tab buttons and contents
  const tabs = document.querySelectorAll('.tab');
  const tabContents = document.querySelectorAll('.tab-content');

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      // Remove active class from all tabs and contents
      tabs.forEach(btn => btn.classList.remove('active'));
      tabContents.forEach(content => content.classList.remove('active'));

      // Add active class to clicked tab and corresponding content
      tab.classList.add('active');
      const targetTab = tab.getAttribute('data-tab');
      document.getElementById(targetTab).classList.add('active');
    });
  });
});


document.addEventListener('DOMContentLoaded', () => {
  const sliders = document.querySelectorAll('.slider');

  sliders.forEach(slider => {
    const slides = slider.querySelector('.slides');
    const slideItems = slider.querySelectorAll('.slide');
    const leftArrow = slider.querySelector('.arrow.left');
    const rightArrow = slider.querySelector('.arrow.right');

    let currentIndex = 0; // 현재 슬라이드 인덱스
    const totalSlides = slideItems.length;

    // 슬라이드 이동 함수
    function moveSlide(index) {
      // 슬라이드 전체 폭 계산
      const slideWidth = slideItems[0].clientWidth;
      slides.style.transform = `translateX(-${index * slideWidth}px)`;
    }

    // 이전 슬라이드로 이동
    leftArrow.addEventListener('click', () => {
      if (currentIndex > 0) {
        currentIndex--;
        moveSlide(currentIndex);
      }
    });

    // 다음 슬라이드로 이동
    rightArrow.addEventListener('click', () => {
      if (currentIndex < totalSlides - 1) {
        currentIndex++;
        moveSlide(currentIndex);
      }
    });

    // 초기 슬라이드 위치 설정
    moveSlide(currentIndex);
  });
});

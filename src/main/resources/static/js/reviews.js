document.addEventListener('DOMContentLoaded', () => {
    let currentPage = 0;
    const pageSize = 10;
    const bookId = document.getElementById('bookId').value;
    const reviewList = document.getElementById('reviewList');
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');
    const currentPageDisplay = document.getElementById('currentPageDisplay');
    const totalPagesDisplay = document.getElementById('totalPagesDisplay');

    // 모달 관련 요소
    const modal = document.getElementById('imageModal');
    const modalImage = document.getElementById('modalImage');
    const closeModal = document.querySelector('.close');

    // 모달 닫기 이벤트
    closeModal.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    // 리뷰 데이터를 가져오는 함수
    const fetchReviews = async (page) => {
        try {
            const response = await axios.get(`/frontend/reviews/${bookId}`, {
                params: {
                    page,
                    size: pageSize,
                },
            });
            const data = response.data;
            renderReviews(data.content);
            updatePagination(data);
        } catch (error) {
            console.error('Error fetching reviews:', error);
        }
    };

    // 리뷰를 렌더링하는 함수
    const renderReviews = (reviews) => {
        reviewList.innerHTML = ''; // 기존 리뷰 목록 초기화
        if (reviews.length === 0) {
            reviewList.innerHTML = '<p>등록된 리뷰가 없습니다.</p>';
            return;
        }
        reviews.forEach((review) => {
            const reviewItem = document.createElement('div');
            reviewItem.classList.add('review-item');

            reviewItem.innerHTML = `
        <div class="review-content">
          ${
                review.imageUrl
                    ? `<div class="review-image">
                   <img src="${review.imageUrl}" alt="리뷰 이미지" style="max-width: 150px; max-height: 150px; border-radius: 5px; object-fit: cover; cursor: pointer;" class="clickable-image">
                 </div>`
                    : ''
            }
          <div class="review-text">
            <p><strong>작성자:</strong> ${review.userId}</p>
            <p><strong>평점:</strong> <span class="rating-stars">${'★'.repeat(review.rating)}${'☆'.repeat(5 - review.rating)}</span></p>
            <p><strong>내용:</strong> ${review.text}</p>
            <p><strong>작성일:</strong> ${new Date(review.createdAt).toLocaleString('ko-KR')}</p>
          </div>
        </div>
      `;

            // 이미지 클릭 이벤트 추가
            const image = reviewItem.querySelector('.clickable-image');
            if (image) {
                image.addEventListener('click', () => {
                    modal.style.display = 'block';
                    modalImage.src = review.imageUrl;
                });
            }

            reviewList.appendChild(reviewItem);
        });
    };

    // 페이지네이션 업데이트 함수
    const updatePagination = (data) => {
        currentPage = data.number;
        currentPageDisplay.textContent = currentPage + 1;
        totalPagesDisplay.textContent = data.totalPages;

        prevPageButton.disabled = currentPage === 0;
        nextPageButton.disabled = currentPage + 1 >= data.totalPages;
    };

    // 이전 버튼 클릭 핸들러
    prevPageButton.addEventListener('click', () => {
        if (currentPage > 0) {
            fetchReviews(currentPage - 1);
        }
    });

    // 다음 버튼 클릭 핸들러
    nextPageButton.addEventListener('click', () => {
        fetchReviews(currentPage + 1);
    });

    // 초기 데이터 로드
    fetchReviews(currentPage);

    // 모달 닫기 - 배경 클릭 시
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });
});

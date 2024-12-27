document.addEventListener('DOMContentLoaded', () => {
    let currentPage = 0;
    const pageSize = 10;
    const bookId = document.getElementById('bookId').value; // Hidden input으로 bookId 가져오기
    const reviewList = document.getElementById('reviewList');
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');
    const currentPageDisplay = document.getElementById('currentPageDisplay');
    const totalPagesDisplay = document.getElementById('totalPagesDisplay');

    // Fetch and render reviews
    const fetchReviews = (page) => {
        fetch(`/api/reviews/${bookId}?page=${page}&size=${pageSize}`)
            .then((response) => response.json())
            .then((data) => {
                renderReviews(data.content);
                currentPage = data.number;
                updatePagination(data);
            })
            .catch((error) => console.error('Error fetching reviews:', error));
    };

    // Render reviews
    const renderReviews = (reviews) => {
        reviewList.innerHTML = '';
        if (reviews.length === 0) {
            reviewList.innerHTML = '<p>등록된 리뷰가 없습니다.</p>';
            return;
        }
        reviews.forEach((review) => {
            const reviewItem = document.createElement('div');
            reviewItem.classList.add('review-item');
            reviewItem.innerHTML = `
        <p><strong>평점:</strong> ${review.rating}</p>
        <p><strong>내용:</strong> ${review.text}</p>
        <p><strong>작성일:</strong> ${new Date(review.createdAt).toLocaleDateString()}</p>
      `;
            reviewList.appendChild(reviewItem);
        });
    };

    // Update pagination buttons
    const updatePagination = (data) => {
        currentPageDisplay.textContent = data.number + 1;
        totalPagesDisplay.textContent = data.totalPages;
        prevPageButton.disabled = data.number === 0;
        nextPageButton.disabled = data.number + 1 >= data.totalPages;
    };

    // Button click handlers
    prevPageButton.addEventListener('click', () => {
        if (currentPage > 0) fetchReviews(currentPage - 1);
    });

    nextPageButton.addEventListener('click', () => {
        fetchReviews(currentPage + 1);
    });

    // Initial load
    fetchReviews(currentPage);
});

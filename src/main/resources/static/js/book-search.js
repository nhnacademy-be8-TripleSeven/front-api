document.addEventListener("DOMContentLoaded", () => {
    const books = [
        { id: 1, title: "창의적인 사고", category: "국내 도서", img: "/image/book.png", price: "22,000원", detailsUrl: "/details/1" },
        { id: 2, title: "2025 세계대전망", category: "경제/경영", img: "/image/book.png", price: "20,700원", detailsUrl: "/details/2" },
        { id: 3, title: "미래 사회의 방향", category: "자기계발", img: "/image/book.png", price: "21,500원", detailsUrl: "/details/3" },
        { id: 4, title: "반 고흐, 인생의 그림들", category: "소설", img: "/image/book.png", price: "18,900원", detailsUrl: "/details/4" },
        { id: 5, title: "기술과 미래의 전망", category: "경제/경영", img: "/image/book.png", price: "25,000원", detailsUrl: "/details/5" },
        { id: 6, title: "인간과 자연의 공존", category: "자기계발", img: "/image/book.png", price: "23,000원", detailsUrl: "/details/6" },
        { id: 7, title: "역사와 철학의 만남", category: "국내 도서", img: "/image/book.png", price: "20,000원", detailsUrl: "/details/7" },
        { id: 8, title: "과학으로 보는 세상", category: "소설", img: "/image/book.png", price: "19,800원", detailsUrl: "/details/8" },
        { id: 9, title: "지구와 환경의 미래", category: "과학", img: "/image/book.png", price: "22,800원", detailsUrl: "/details/9" },
        { id: 10, title: "행복한 삶의 기술", category: "자기계발", img: "/image/book.png", price: "21,200원", detailsUrl: "/details/10" },
        { id: 11, title: "세계 경제의 흐름", category: "경제/경영", img: "/image/book.png", price: "24,000원", detailsUrl: "/details/11" },
        { id: 12, title: "문학의 아름다움", category: "소설", img: "/image/book.png", price: "19,500원", detailsUrl: "/details/12" },
        { id: 13, title: "사회와 미래의 변화", category: "사회과학", img: "/image/book.png", price: "23,500원", detailsUrl: "/details/13" },
        { id: 14, title: "창의적 문제 해결법", category: "국내 도서", img: "/image/book.png", price: "20,400원", detailsUrl: "/details/14" },
        { id: 15, title: "예술과 감성의 조화", category: "예술", img: "/image/book.png", price: "26,000원", detailsUrl: "/details/15" },
        { id: 16, title: "고전의 재발견", category: "고전", img: "/image/book.png", price: "18,800원", detailsUrl: "/details/16" },
    ];

    const bookList = document.getElementById("book-list");
    const resultCount = document.getElementById("result-count");
    const pagination = document.querySelector(".pagination");
    const searchInput = document.getElementById("search-keyword");
    const searchButton = document.getElementById("search-button");
    const categoryInput = document.getElementById("category-keyword");
    const categoryButton = document.getElementById("category-search-button");
    const sortOrder = document.getElementById("sort-order");

    let currentPage = 1;
    const itemsPerPage = 8;

    let likedBooks = JSON.parse(localStorage.getItem("likedBooks")) || [];
    let cartBooks = JSON.parse(localStorage.getItem("cartBooks")) || [];

    let filteredBooks = books;

    // 도서 목록 렌더링
    const renderBooks = () => {
        bookList.innerHTML = "";
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const paginatedBooks = filteredBooks.slice(start, end);

        if (paginatedBooks.length === 0) {
            bookList.innerHTML = `<p>검색 결과가 없습니다.</p>`;
            resultCount.textContent = "총 0권의 검색 결과";
            return;
        }

        paginatedBooks.forEach((book) => {
            const bookItem = document.createElement("div");
            bookItem.className = "book-item";
            bookItem.innerHTML = `
                <a href="${book.detailsUrl}">
                    <img src="${book.img}" alt="${book.title}">
                    <h4>${book.title}</h4>
                </a>
                <p>${book.price}</p>
                <div class="actions">
                    <img class="icon like-icon" src="/image/icons/like.png" alt="Like" data-id="${book.id}">
                    <img class="icon cart-icon" src="/image/icons/cart.png" alt="Cart" data-id="${book.id}">
                </div>
            `;
            bookList.appendChild(bookItem);
        });

        resultCount.textContent = `총 ${filteredBooks.length}권의 검색 결과`;
        renderPagination(filteredBooks.length);
        addEventListeners();
    };

    // 좋아요와 장바구니 이벤트 추가
    const addEventListeners = () => {
        document.querySelectorAll(".like-icon").forEach((btn) =>
            btn.addEventListener("click", (event) => {
                const bookId = parseInt(event.target.dataset.id);
                if (!likedBooks.includes(bookId)) {
                    likedBooks.push(bookId);
                    localStorage.setItem("likedBooks", JSON.stringify(likedBooks));
                    alert("좋아요에 추가되었습니다!");
                } else {
                    alert("이미 좋아요에 추가된 책입니다!");
                }
            })
        );

        document.querySelectorAll(".cart-icon").forEach((btn) =>
            btn.addEventListener("click", (event) => {
                const bookId = parseInt(event.target.dataset.id);
                if (!cartBooks.includes(bookId)) {
                    cartBooks.push(bookId);
                    localStorage.setItem("cartBooks", JSON.stringify(cartBooks));
                    alert("장바구니에 추가되었습니다!");
                } else {
                    alert("이미 장바구니에 추가된 책입니다!");
                }
            })
        );
    };

    // 페이지네이션 렌더링
    const renderPagination = (totalItems) => {
        pagination.innerHTML = "";
        const totalPages = Math.ceil(totalItems / itemsPerPage);

        const prevButton = document.createElement("button");
        prevButton.className = "page-btn";
        prevButton.textContent = "이전";
        prevButton.disabled = currentPage === 1;
        prevButton.addEventListener("click", () => {
            currentPage -= 1;
            renderBooks();
        });
        pagination.appendChild(prevButton);

        const pageInfo = document.createElement("span");
        pageInfo.id = "page-info";
        pageInfo.textContent = `${currentPage} / ${totalPages}`;
        pagination.appendChild(pageInfo);

        const nextButton = document.createElement("button");
        nextButton.className = "page-btn";
        nextButton.textContent = "다음";
        nextButton.disabled = currentPage === totalPages;
        nextButton.addEventListener("click", () => {
            currentPage += 1;
            renderBooks();
        });
        pagination.appendChild(nextButton);
    };

    // 키워드 검색
    const searchBooks = () => {
        const keyword = searchInput.value.trim().toLowerCase();
        currentPage = 1;
        filteredBooks = books.filter((book) => book.title.toLowerCase().includes(keyword));
        renderBooks();
    };

    // 목차에서 검색
    const searchByCategory = () => {
        const categoryKeyword = categoryInput.value.trim().toLowerCase();
        currentPage = 1;
        filteredBooks = books.filter((book) => book.category.toLowerCase().includes(categoryKeyword));
        renderBooks();
    };

    // 정렬
    const sortBooks = () => {
        const order = sortOrder.value;
        if (order === "newest") {
            filteredBooks.sort((a, b) => b.id - a.id);
        } else {
            filteredBooks.sort((a, b) => a.id - b.id);
        }
        renderBooks();
    };

    // 초기 렌더링
    renderBooks();

    // 이벤트 리스너 추가
    searchButton.addEventListener("click", searchBooks);
    searchInput.addEventListener("keypress", (event) => {
        if (event.key === "Enter") {
            searchBooks();
        }
    });

    categoryButton.addEventListener("click", searchByCategory);
    categoryInput.addEventListener("keypress", (event) => {
        if (event.key === "Enter") {
            searchByCategory();
        }
    });

    sortOrder.addEventListener("change", sortBooks);
});
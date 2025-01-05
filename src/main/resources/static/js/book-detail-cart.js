document.addEventListener('DOMContentLoaded', () => {
    const cartBtn = document.getElementById('addToCartBtn');

    if (cartBtn) {
        cartBtn.addEventListener('click', async (event) => {
            event.preventDefault(); // 기본 동작 방지

            const bookId = cartBtn.getAttribute('data-book-id');

            const response = await axios.post('/cart',{} ,{
                params : {
                bookId: bookId,
                quantity: 1,
            }});

            // 서버 응답 처리
            if (response.status === 200) {
                alert('장바구니에 상품이 추가되었습니다.');
            } else {
                alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
            }

        });
    }
});

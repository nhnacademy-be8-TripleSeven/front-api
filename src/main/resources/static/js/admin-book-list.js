document.addEventListener('DOMContentLoaded', function () {
  const deleteButtons = document.querySelectorAll('.delete-btn');

  // 도서 삭제 버튼 이벤트 추가
  deleteButtons.forEach(button => {
    button.addEventListener('click', function () {
      const bookId = this.getAttribute('data-id');

      if (confirm('정말 이 도서를 삭제하시겠습니까?')) {
        handleDeleteBook(bookId);
      }
    });
  });
});

// 도서 삭제 함수
function handleDeleteBook(bookId) {
  axios.delete(`/admin/books/delete?bookId=${bookId}`)
  .then(response => {
    alert('도서가 성공적으로 삭제되었습니다.');
    window.location.reload(); // 삭제 후 페이지 새로고침
  })
  .catch(error => {
    console.error('도서 삭제 실패:', error);
    alert('도서 삭제 중 오류가 발생했습니다.');
  });
}

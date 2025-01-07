document.addEventListener('DOMContentLoaded', function () {
  const createForm = document.getElementById('category-create-form'); // 잘못된 셀렉터 수정
  const deleteForm = document.getElementById('category-delete-id-form'); // 잘못된 셀렉터 수정

  // [1] 카테고리 등록 처리
  if (createForm) {
    createForm.addEventListener('submit', handleCreateCategory);
  }

  // [2] 카테고리 삭제 처리
  if (deleteForm) {
    deleteForm.addEventListener('submit', handleDeleteCategory);
  }

});

// [1] 카테고리 등록 함수
function handleCreateCategory(event) {
  event.preventDefault(); // 기본 폼 제출 방지

  const name = document.getElementById('name').value.trim();
  const level = document.getElementById('levels').value;

  if (!name || !level) {
    alert('카테고리 이름과 레벨을 모두 입력해주세요.');
    return;
  }

  const categoryData = { name, level };

  axios.post('/admin/books/categoryCreate', categoryData)
  .then(response => {
    alert('카테고리가 성공적으로 등록되었습니다.');
    window.location.reload(); // 페이지 새로고침
  })
  .catch(error => {
    console.error('카테고리 등록 실패:', error);
    alert('카테고리 등록 중 오류가 발생했습니다.');
  });
}

function handleDeleteCategory(event) {
  event.preventDefault(); // 기본 폼 제출 방지

  const categoryId = document.getElementById('categoryId').value;

  if (!categoryId) {
    alert('유효한 카테고리 ID를 입력해주세요.');
    return;
  }

  // axios GET 요청으로 id를 쿼리 파라미터로 전달
  axios.post(`/admin/books/categoryDelete?id=` + categoryId)
  .then(response => {
    alert('카테고리가 성공적으로 삭제되었습니다.');
    window.location.reload(); // 페이지 새로고침
  })
  .catch(error => {
    console.error('카테고리 삭제 실패:', error);
    alert('카테고리 삭제 중 오류가 발생했습니다.');
  });


}


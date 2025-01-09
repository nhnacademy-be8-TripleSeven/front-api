

document.addEventListener('DOMContentLoaded', function () {
  const createForm = document.getElementById('category-create-form');
  const deleteForm = document.getElementById('category-delete-id-form');

  // [1] 카테고리 등록 처리
  if (createForm) {
    createForm.addEventListener('submit', function (event) {
      event.preventDefault();
      createCategory();
    });
  }

  // [2] 카테고리 삭제 처리
  if (deleteForm) {
    deleteForm.addEventListener('submit', function (event) {
      event.preventDefault();
      handleDeleteCategory();
    });
  }
});

// Axios로 카테고리 생성 요청 보내기
async function createCategory() {
  const levelSelect = document.getElementById('levelSelect');
  const categorySelect = document.getElementById('categorySelect');
  const categoryNameInput = document.getElementById('categoryName');

  const categoryData = {
    name: categoryNameInput.value,
    level: parseInt(levelSelect.value, 10),
    parentCategoryId: categorySelect.value || null,
  };

  try {
    const response = await axios.post('/admin/books/categoryCreate', categoryData);
    alert('카테고리가 성공적으로 생성되었습니다.');
    location.reload(); // 페이지 새로고침
  } catch (error) {
    console.error('Error creating category:', error);
    alert('카테고리 생성 중 문제가 발생했습니다.');
  }
}

async function handleDeleteCategory() {
  const categoryIdInput = document.getElementById('categoryId');
  const categoryIdValue = categoryIdInput ? categoryIdInput.value.trim() : null;

  if (!categoryIdValue || isNaN(categoryIdValue)) {
    alert('유효한 숫자 형식의 카테고리 ID를 입력해주세요.');
    return;
  }

  const categoryId = parseInt(categoryIdValue, 10);

  if (!confirm(`정말로 ID가 ${categoryId}인 카테고리를 삭제하시겠습니까?`)) {
    return;
  }

  axios.post('/admin/books/categoryDelete', null, {
    params: { id: categoryId }
  })
  .then(response => {
    alert('카테고리가 성공적으로 삭제되었습니다.');
    window.location.reload(); // 삭제 후 페이지 새로고침
  })
  .catch(error => {
    console.error('카테고리 삭제 실패:', error);
    if (error.response && error.response.data && error.response.data.message) {
      alert(`카테고리 삭제 중 오류가 발생했습니다: ${error.response.data.message}`);
    } else {
      alert('카테고리 삭제 중 오류가 발생했습니다.');
    }
  });
}



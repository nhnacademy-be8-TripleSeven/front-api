

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

async function fetchCategoriesByLevel() {
  const levelSelect = document.getElementById('levelSelect');
  const categorySelect = document.getElementById('categorySelect');
  const level = parseInt(levelSelect.value, 10);

  // 카테고리 Select 박스를 초기화
  categorySelect.innerHTML = '<option value="" disabled selected>상위 카테고리를 선택하세요</option>';
  categorySelect.disabled = true;  // 기본적으로 disabled 처리

  // 1레벨을 선택한 경우 상위 카테고리 박스를 숨김
  if (level === 1) {
    categorySelect.disabled = true;
    return;
  }

  try {
    // 레벨에 따른 카테고리 리스트를 가져옴
    const response = await axios.get('/admin/books/categoryLevelList');
    const data = response.data;

    let categories = [];
    switch (level) {
      case 2:
        categories = data.level1; // Level 2는 Level 1을 부모로 갖기 때문
        break;
      case 3:
        categories = data.level2; // Level 3은 Level 2를 부모로 갖기 때문
        break;
      case 4:
        categories = data.level3; // Level 4는 Level 3을 부모로 갖기 때문
        break;
      case 5:
        categories = data.level4; // Level 5는 Level 4를 부모로 갖기 때문
        break;
      default:
        break;
    }

    // 상위 카테고리가 있을 경우 활성화
    if (categories.length > 0) {
      categorySelect.disabled = false;
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.textContent = category.name;
        categorySelect.appendChild(option);
      });
    }

  } catch (error) {
    console.error('Error fetching categories:', error);
    alert('카테고리를 불러오는 중 문제가 발생했습니다.');
  }
}


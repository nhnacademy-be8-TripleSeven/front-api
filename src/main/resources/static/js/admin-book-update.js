// DOMContentLoaded 이벤트 리스너
function initEventListeners() {
  const updateBookBtn = document.getElementById('book-update-btn');
  if (updateBookBtn) {
    updateBookBtn.addEventListener('click', handleUpdateBook);
  }

  const categorySelectors = document.querySelectorAll('[name^="categories.level"]');
  categorySelectors.forEach((selector, index) => {
    selector.addEventListener('change', function () {
      handleCategoryChange(this.value, index, categorySelectors);
    });
  });

  resetCategoryLevels(1, categorySelectors);
}

document.addEventListener('DOMContentLoaded', initEventListeners);

// 업데이트 처리 함수
function handleUpdateBook(event) {
  event.preventDefault();

  const bookData = collectBookData();
  const formData = prepareFormData(bookData);

  axios.post('/admin/books/updateBook', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
  .then((response) => {
    console.log('도서 수정 성공:', response);
    alert('도서가 성공적으로 수정되었습니다.');
    window.location.href = '/admin/frontend/books';
  })
  .catch((error) => {
    console.error('도서 수정 실패:', error);
    alert('도서 수정 중 오류가 발생했습니다.');
  });
}

// 도서 데이터 수집
function collectBookData() {
  return {
    id: document.getElementById('book-id').value,
    title: document.getElementById('book-title').value,
    isbn: document.getElementById('book-isbn').value,
    publishedDate: document.getElementById('published-date').value,
    description: document.getElementById('description').value,
    regularPrice: document.getElementById('regular-price').value,
    salePrice: document.getElementById('sale-price').value,
    index: document.getElementById('index').value,
    page: document.getElementById('page').value,
    stock: document.getElementById('stock').value,
    categories: collectNestedInputValues('categories', true),
    bookTypes: collectNestedInputValues('bookTypes'),
    authors: collectNestedInputValues('authors'),
  };
}

// FormData 생성
function prepareFormData(bookData) {
  const formData = new FormData();
  formData.append('bookUpdateDTO', new Blob([JSON.stringify(bookData)], { type: 'application/json' }));

  appendFilesToFormData(formData, 'cover-images', 'coverImage');
  appendFilesToFormData(formData, 'detail-images', 'detailImage');

  return formData;
}

// 파일 추가 헬퍼 함수
function appendFilesToFormData(formData, inputId, formKey) {
  const files = document.getElementById(inputId).files;
  if (files.length > 0) {
    Array.from(files).forEach((file) => formData.append(formKey, file));
  } else {
    formData.append(`${formKey}s`, new Blob([], { type: 'application/octet-stream' }), '');
  }
}

// 중첩 필드 수집
function collectNestedInputValues(fieldName, isCategory = false) {
  const inputs = document.querySelectorAll(`[name^="${fieldName}"]`);
  const values = [];

  inputs.forEach((input) => {
    const match = input.name.match(isCategory ? /\.(.+)/ : /\[(\d+)\]\.(.+)/);
    if (match) {
      if (isCategory) {
        const level = parseInt(match[1].replace(/[^\d]/g, ''), 10);
        const selectedOption = input.options[input.selectedIndex];
        if (selectedOption && selectedOption.value) {
          values.push({ level: level, name: selectedOption.text.trim() });
        }
      } else {
        const index = parseInt(match[1], 10);
        const key = match[2];

        if (!values[index]) {
          values[index] = {};
        }
        values[index][key] = input.value.trim();
      }
    }
  });

  return values;
}

// 카테고리 변경 처리
function handleCategoryChange(selectedValue, index, categorySelectors) {
  const nextLevel = categorySelectors[index + 1];
  if (nextLevel) {
    if (selectedValue) {
      fetchCategoriesForLevel(selectedValue, index + 2).then((options) => {
        nextLevel.innerHTML = '<option value="" disabled selected>선택</option>';
        options.forEach((option) => {
          nextLevel.innerHTML += `<option value="${option.id}">${option.name}</option>`;
        });
        nextLevel.disabled = false;
      });
    } else {
      resetCategoryLevels(index + 1, categorySelectors);
    }
  }
}

// 하위 카테고리 초기화
function resetCategoryLevels(startIndex, categorySelectors) {
  for (let i = startIndex; i < categorySelectors.length; i++) {
    categorySelectors[i].innerHTML = '<option value="" disabled selected>선택</option>';
    categorySelectors[i].disabled = true;
  }
}

// 카테고리 데이터 요청
async function fetchCategoriesForLevel(parentCategoryId, level) {
  try {
    const response = await axios.get('/admin/books/categoriesParentList', {
      params: { parent: parentCategoryId, level: level },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching categories:', error);
    return [];
  }
}

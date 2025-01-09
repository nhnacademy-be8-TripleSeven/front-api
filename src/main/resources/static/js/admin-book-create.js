document.addEventListener('DOMContentLoaded', function () {
  const createBookBtn = document.getElementById('book-create-btn');
  if (createBookBtn) {
    createBookBtn.addEventListener('click', handleCreateBook);
  }
});

function handleCreateBook(event) {
  event.preventDefault(); // 기본 제출 동작 방지

  // 입력 필드 값 수집
  const bookData = {
    title: document.getElementById('book-name').value,
    isbn: document.getElementById('isbn').value,
    publisherName: document.getElementById('publisher-name').value, // 추가

    // categories도 collectNestedInputValues로 수정
    categories: categoryNestInputValue('categories'),
    bookTypes: collectNestedInputValues('bookTypes'),
    authors: collectNestedInputValues('authors'),
    publishedDate: document.getElementById('published-date').value,
    description: document.getElementById('description').value,
    regularPrice: document.getElementById('regular-price').value,
    salePrice: document.getElementById('sale-price').value,
    index: document.getElementById('index').value,
    page: document.getElementById('page').value,
    stock: document.getElementById('stock').value,
  };

  // FormData 생성
  const formData = new FormData();

// JSON 데이터 추가
  formData.append('bookCreatDTO', new Blob([JSON.stringify(bookData)], { type: 'application/json' }));

// 파일 데이터 추가
  const coverFiles = document.getElementById('cover-images').files;
  const detailFiles = document.getElementById('detail-images').files;

  // 표지 이미지 추가
  if (coverFiles.length > 0) {
    Array.from(coverFiles).forEach(file => formData.append('coverImages', file));
  } else {
    formData.append('coverImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // 상세 이미지 추가
  if (detailFiles.length > 0) {
    Array.from(detailFiles).forEach(file => formData.append('detailImages', file));
  } else {
    formData.append('detailImages', new Blob([], { type: 'application/octet-stream' }), ''); // 빈 파일 추가
  }

  // Axios 요청
  axios.post('/admin/books/createBook', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
  .then(response => {
    console.log('도서 생성 성공:', response);
    alert('도서가 성공적으로 생성되었습니다.');
    window.location.href = '/admin/frontend/books/create'; // 성공 시 리다이렉트
  })
  .catch(error => {
    console.error('도서 생성 실패:', error);
    alert('도서 생성 중 오류가 발생했습니다.');
  });
}

// 카테고리, 도서 타입, 저자 등 중첩 필드 수집 함수
function collectNestedInputValues(fieldName) {
  const inputs = document.querySelectorAll(`[name^="${fieldName}"]`);
  const values = [];

  inputs.forEach(input => {
    const match = input.name.match(/\[(\d+)\]\.(.+)/);
    if (match) {
      const index = parseInt(match[1], 10);
      const key = match[2];

      if (!values[index]) {
        values[index] = {};
      }

      values[index][key] = input.value.trim();
    }
  });

  return values;
}

// 카테고리, 도서 타입, 저자 등 중첩 필드 수집 함수 (카테고리는 select만 처리)
function categoryNestInputValue(fieldName) {
  const selectElements = document.querySelectorAll(`select[name^="${fieldName}"]`);
  const values = [];

  selectElements.forEach(select => {
    if (select.value) {
      values.push(select.value);
    }
  });

  return values;
}
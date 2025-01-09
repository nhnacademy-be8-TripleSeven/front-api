// 트리 확장/축소 기능
function toggleCategory(event) {
  const button = event.target;
  const ul = button.parentElement.querySelector('ul');
  if (ul) {
    if (ul.style.display === 'none') {
      ul.style.display = 'block';
      button.textContent = '-';
    } else {
      ul.style.display = 'none';
      button.textContent = '+';
    }
  }
}

document.addEventListener('DOMContentLoaded', function () {
  const toggleButtons = document.querySelectorAll('.toggle-button');
  toggleButtons.forEach(button => {
    button.addEventListener('click', toggleCategory);
  });
});
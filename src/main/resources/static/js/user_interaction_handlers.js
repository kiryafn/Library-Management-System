// Показ формы для ввода email
const showEmailForm = () => {
  const emailForm = document.getElementById('email-form');
  emailForm.style.display = 'flex';
};

// Переход в режим библиотекаря
const enterAsLibrarian = () => {
  window.location.href = '/tablesr'; // Редирект на маршрут для библиотекаря
};

// Редирект к пользователю по email
const redirectToUser = async (event) => {
  event.preventDefault(); // Предотвратить перезагрузку страницы при отправке формы

  const emailInput = document.getElementById('email');
  const email = emailInput.value.trim(); // Убираем лишние пробелы

  if (!validateEmail(email)) {
    alert('Пожалуйста, введите корректный email.');
    emailInput.focus();
    return;
  }

  try {
    // Создаем тело запроса в формате x-www-form-urlencoded
    const formData = new URLSearchParams();
    formData.append('email', email);

    // Отправляем POST-запрос на сервер
    const response = await fetch('/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: formData.toString(), // Преобразуем тело запроса в строку
    });

    // Анализируем ответ сервера
    if (response.redirected) {
      // Если сервер отправил редирект, перенаправляем пользователя
      window.location.href = response.url;
    } else if (response.ok) {
      const responseText = await response.text(); // Для обработки других возможных ответов (например, в случае ошибки)
      console.log('Ответ сервера:', responseText);
      alert('Ошибка: ' + responseText);
    } else {
      alert('Пользователь с указанным email не найден. Попробуйте снова.');
    }
  } catch (error) {
    console.error('Ошибка при выполнении запроса:', error);
    alert('Что-то пошло не так. Пожалуйста, попробуйте позже.');
  }
};

// Валидация email
const validateEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Простое регулярное выражение для проверки email
  return emailRegex.test(email);
};

// Привязывание обработчиков событий к кнопкам
document.addEventListener('DOMContentLoaded', () => {
  const librarianBtn = document.querySelector('.tables');
  const userBtn = document.querySelector('.user');
  const emailForm = document.getElementById('email-form');

  if (librarianBtn) {
    librarianBtn.addEventListener('click', enterAsLibrarian);
  } else {
    console.error('Кнопка "Режим Библиотекаря" не найдена.');
  }

  if (userBtn && emailForm) {
    emailForm.addEventListener('submit', redirectToUser);
  } else {
    console.error('Кнопка "Режим Пользователя" или форма не найдена.');
  }
});
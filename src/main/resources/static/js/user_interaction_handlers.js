// Показ формы для ввода email
const showEmailForm = () => {
  const emailForm = document.getElementById('email-form');
  emailForm.style.display = 'flex';
};

// Переход в режим библиотекаря
const enterAsLibrarian = () => {
  window.location.href = '/tables';
};

// Редирект к пользователю по email
const redirectToUser = async (event) => {
  event.preventDefault(); // Предотвратить перезагрузку страницы при отправке формы

  const emailInput = document.getElementById('email');
  const email = emailInput.value.trim(); // Убираем лишние пробелы

  if (!validateEmail(email)) {
    alert('Please enter a valid email.');
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
      console.log('Server`s responce:', responseText);
      alert('Error: ' + responseText);
    } else {
      alert('A user with the specified email was not found. Please try again.');
    }
  } catch (error) {
    console.error('Error executing the request:', error);
    alert('Something went wrong. Please try again later.');
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
    console.error('The \'Librarian Mode\' button was not found.');
  }

  if (userBtn && emailForm) {
    emailForm.addEventListener('submit', redirectToUser);
  } else {
    console.error('The \'User Mode\' button or form was not found.');
  }
});
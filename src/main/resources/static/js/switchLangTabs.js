
    // Активация вкладок переводов
    document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function (e) {
        e.preventDefault();

        // Скрыть все вкладки
        document.querySelectorAll('.translation-tab').forEach(tab => {
            tab.classList.remove('active');
        });

        // Показать выбранную
        const targetTab = document.querySelector(this.getAttribute('href'));
        if (targetTab) {
            targetTab.classList.add('active');
        }

        // Активировать ссылку
        document.querySelectorAll('.nav-link').forEach(navLink => {
            navLink.classList.remove('active');
        });
        this.classList.add('active');
    });
});

    // Предварительный просмотр изображения
    document.querySelector('input[name="image"]').addEventListener('change', function (e) {
    const file = e.target.files[0];
    if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
    let preview = document.getElementById('image-preview');
    if (!preview) {
    preview = document.createElement('img');
    preview.id = 'image-preview';
    preview.className = 'img-thumbnail mt-2';
    preview.style.maxWidth = '200px';
    e.target.parentNode.appendChild(preview);
}
    preview.src = e.target.result;
};
    reader.readAsDataURL(file);
}
});

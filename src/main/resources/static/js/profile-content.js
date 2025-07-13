document.addEventListener("DOMContentLoaded", function () {
    const container = document.querySelector(".container");
    if (!container) {
        console.error("Контейнер с классом .container не найден");
        return;
    }

    const userId = container.getAttribute("data-user-id");
    if (!userId) {
        console.error("Атрибут data-user-id отсутствует");
        return;
    }

    const contentList = document.getElementById("userContentList");
    if (!contentList) {
        console.error("Элемент #userContentList не найден");
        return;
    }

    contentList.innerHTML = `
        <div class="text-center text-muted py-5">
            <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
            <p>Загрузка контента...</p>
        </div>`;

    function loadAllContent() {
        const types = ['post', 'blog', 'event', 'book'];
        const urls = types.map(type => `/api/users/content?type=${type}&creatorId=${userId}&page=0&size=10`);

        Promise.all(urls.map(url =>
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
        ))
            .then(results => {
                let html = '<div class="row g-3">';
                let hasContent = false;

                results.forEach((result, index) => {
                    const type = types[index];
                    if (result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            hasContent = true;
                            html += `
                                    <div class="col-md-4 col-6">
                                        <a href="/${type === 'post' ? 'posts' : type}/${item.id}" class="d-block border rounded overflow-hidden">
                                            <img src="/api/files/${item.mainImageId}/view" alt="${type} Image" class="img-fluid">
                                        </a>
                                    </div>`;
                        });
                    }
                });

                html += '</div>';

                if (!hasContent) {
                    contentList.innerHTML = `
                        <div class="no-content text-center py-5">
                            <svg width="120" height="120" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <rect x="12" y="14" width="40" height="36" rx="3" ry="3" fill="#eaeaea" stroke="#888" stroke-width="2"/>
                                <line x1="20" y1="20" x2="44" y2="20" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="26" x2="44" y2="26" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="32" x2="44" y2="32" stroke="#bbb" stroke-width="1.5"/>
                                <line x1="20" y1="38" x2="44" y2="38" stroke="#bbb" stroke-width="1.5"/>
                            </svg>
                            <p class="text-muted fs-5 mt-3">
                                У вас пока нет ни одного поста, блога, события или книги.
                            </p>
                        </div>`;
                } else {
                    contentList.innerHTML = html;
                    const contentDiv = contentList.querySelector('div.row');
                    if (contentDiv) {
                        contentDiv.classList.add('fade-in');
                    }
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
            });
    }

    loadAllContent();
});

let switchOnUserButton = document.getElementById('users-tab');
let userContentList = document.getElementById('usersContentList');
switchOnUserButton.onclick = function () {
    doFetch('/api/users');
}


const csrfToken = document.querySelector('input[name="_csrf"]')?.value ||
    document.querySelector('input[name="csrf"]')?.value ||
    document.querySelector('meta[name="_csrf"]')?.getAttribute('content');

const searchButton = document.getElementById('usersSearchBtn');
searchButton.onclick = function () {
    let searchInputValue = document.getElementById('usersSearch').value;
    let url = "/api/users"
    if (searchInputValue) {
        url = "/api/users?query=" + searchInputValue
    }
    doFetch(url);
}

function renderPagination(currentPage, totalPages, url) {
    // document.getElementById('usersPagination').style.display = 'block';
    const paginationContainer = document.querySelector('.pagination');
    console.log(paginationContainer);
    paginationContainer.innerHTML = '';

    const createPageItem = (page, text = null, active = false, disabled = false) => {
        const li = document.createElement('li');
        li.className = `page-item${active ? ' active' : ''}${disabled ? ' disabled' : ''}`;

        const a = document.createElement('a');
        a.className = 'page-link';
        a.href = '#';
        a.textContent = text || page;
        a.dataset.page = page;

        li.appendChild(a);
        return li;
    };
    paginationContainer.appendChild(
        createPageItem(currentPage - 1, 'Предыдущая', false, currentPage === 1)
    );
    for (let i = 1; i <= totalPages; i++) {
        paginationContainer.appendChild(
            createPageItem(i, (i).toString(), i === currentPage)
        );
    }
    paginationContainer.appendChild(
        createPageItem(currentPage + 1, 'Следующая', false, currentPage === totalPages)
    );

    paginationContainer.querySelectorAll('a.page-link').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const page = parseInt(e.target.dataset.page);
            if (!isNaN(page)) {
                doFetch(url, page)
            }
        });
    });
}

function doFetch(url, page = 1) {
    const connector = url.includes('?') ? '&' : '?';
    fetch(`${url}${connector}page=${page}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('Ответ сервера:', data);
            if (data.length === 0) {

            } else {
                addUserToTable(data.content)
                renderPagination(data.number + 1, data.totalPages, `${url}${connector}`)
            }

        })
        .catch(error => {
        });
}

//
function addUserToTable(users) {
    userContentList.innerHTML = "<div class=\"table-responsive\">\n" +
        "        <table class=\"table table-hover table-striped\">\n" +
        "            <thead>\n" +
        "            <tr>\n" +
        "                <th>ID</th>\n" +
        "                <th>Имя пользователя</th>\n" +
        "                <th>Email</th>\n" +
        "                <th>Роль</th>\n" +
        "                <th>Статус</th>\n" +
        "                <th>Действия</th>\n" +
        "            </tr>\n" +
        "            </thead>\n" +
        "            <tbody id=\"resultTableUsers\">\n" +
        "            </tbody>\n" +
        "        </table>\n" +
        "    </div>";
    let resultTableUsers = document.getElementById('resultTableUsers')
    users.forEach(user => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
  <td>${user.id}</td>
  <td>${user.name}</td>
  <td>${user.email}</td>
  <td><span class="badge bg-secondary">${user.role.name}</span></td>
  <td>
    <span class="${user.enabled ? 'user-status-active' : 'user-status-blocked'}">
      ${user.enabled ? 'Активен' : 'Неактивен'}
    </span>
  </td>
  <td>
    <div class="dropdown">
      <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        Действия
      </button>
      <ul class="dropdown-menu">
        <li>
          <a class="dropdown-item edit-button" 
             href="#" 
             data-bs-toggle="modal" 
             data-bs-target="#editUserModal"
             data-user-id="${user.id}" 
             data-user-name="${user.name}" 
             data-user-surname="${user.surname}" 
             data-user-role="${user.role.name}" 
             data-user-type="${user.type?.id || ''}">
            ✏️ Изменить
          </a>
        </li>
        <li>
          <a class="dropdown-item delete-button" 
             href="#" 
             data-bs-toggle="modal" 
             data-bs-target="#deleteUserModal"
             data-user-id="${user.id}">
            🗑️ Удалить
          </a>
        </li>
        <li>
          <a class="dropdown-item block-button text-warning" 
             href="#" 
             data-user-id="${user.id}">
            ${user.enabled ? '🔒 Заблокировать' : '🔓 Разблокировать'}
          </a>
        </li>
      </ul>
    </div>
  </td>
`;


        resultTableUsers.appendChild(tr);

    });
    initDropdownBehavior();
    // console.log('changeEditModal')
    changeEditModal();
}

function initDropdownBehavior() {
    document.querySelectorAll('.dropdown').forEach(dropdown => {
        const toggleButton = dropdown.querySelector('[data-bs-toggle="dropdown"]');
        if (!toggleButton) return;

        toggleButton.addEventListener('click', () => {
            setTimeout(() => {
                const rect = dropdown.getBoundingClientRect();
                const menuHeight = 150;
                const spaceBelow = window.innerHeight - rect.bottom;
                const spaceAbove = rect.top;

                if (spaceBelow < menuHeight && spaceAbove > menuHeight) {
                    dropdown.classList.add('dropup');
                } else {
                    dropdown.classList.remove('dropup');
                }
            }, 10);
        });
    });
}

let createUserBtn = document.getElementById('createUserBtn');
createUserBtn.onclick = function () {
    const form = document.getElementById('createNewUser');
    sendForm(form, '/api/users', 'POST', 'createUserModal');
}

async function handleUserAction(method, successMessage, errorMessage, userId, modalId) {
    try {
        const response = await fetch(`/api/users/${userId}`, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            console.log(successMessage);
            if (modalId) {
                const modalEl = document.getElementById(modalId);
                const modal = bootstrap.Modal.getInstance(modalEl);
                modal.hide();

            }


            doFetch('/api/users');
        } else {
            const errorData = await response.json();
            throw new Error(errorData.message || errorMessage);
        }
    } catch (error) {
        console.error('Ошибка:', error.message);
    }
}

//
function changeEditModal() {
    document.getElementById('resultTableUsers').addEventListener('click', async function (event) {
        const button = event.target;
        const userId = button.dataset.userId;
        if (button.classList.contains('edit-button')) {
            document.getElementById('editUserId').value = userId;
            document.getElementById('editUserName').value = button.dataset.userName;
            document.getElementById('editUserSurname').value = button.dataset.userSurname;
            document.getElementById('editUserRole').value = button.dataset.userRole;
            document.getElementById('editUserType').value = button.dataset.userType;
        } else if (button.classList.contains('delete-button')) {
            let deleteUserModal = document.getElementById('deleteUserModalBody');
            let deleteUserInput = document.getElementById('deleteUserInput')
            let deleteUserBtn = document.getElementById('deleteUserBtn');
            deleteUserBtn.dataset.userId = userId
            deleteUserInput.value = userId;
            console.log(deleteUserInput);
            deleteUserModal.textContent = 'Вы уверены что хотите удалить пользователя с ID:' + userId;

        } else if (button.classList.contains('block-button')) {
            await handleUserAction('PATCH',
                `Пользователь с id=${userId} успешно заблокирован/изменён`,
                'Ошибка блокировки/изменения пользователя', userId);
        }
    });
}

let editUserBtn = document.getElementById('editUserBtn');
editUserBtn.onclick = function () {
    let editUserForm = document.getElementById('editUserForm');
    let userId = document.getElementById('editUserId').value
    sendForm(editUserForm, `/api/users/${userId}`, 'PUT', 'editUserModal')
}
//
let deleteUserBtn = document.getElementById('deleteUserBtn');
deleteUserBtn.onclick = async function () {
    let userId = deleteUserBtn.dataset.userId
    await handleUserAction('DELETE',
        `Пользователь с id=${userId} успешно удалён`,
        'Ошибка удаления пользователя', userId, 'deleteUserModal');
}

function sendForm(form, fetchUrl, method, modalId) {
    let selectElement;
    console.log(form);
    selectElement = form.querySelector('select[name="type"]');
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    data.role = {name: data.role};
    if (modalId === 'createUserModal') {
        let type;
        if (selectedOption.value && selectedOption.dataset.name) {
            type = {id: selectedOption.value, name: selectedOption.dataset.name}
        } else {
            type = null;
        }
        data.type = type;
    } else {
        data.typeId = selectedOption.value
    }
    console.log(data);
    fetch(fetchUrl, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data),
        // credentials: "include"
    })
        .then(async response => {
            if (!response.ok) {
                const errorBody = await response.json();
                const errors = errorBody.response;
                console.log(errors);

                form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
                form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

                for (const fieldName in errors) {
                    const messages = errors[fieldName];
                    const field = form.querySelector(`[name="${fieldName}"]`);
                    if (field) {
                        field.classList.add("is-invalid");

                        messages.forEach(msg => {
                            const div = document.createElement("div");
                            div.classList.add("invalid-feedback");
                            div.innerText = msg;
                            field.parentElement.appendChild(div);
                        });
                    }
                }
            } else {
                if (modalId) {
                    const modalEl = document.getElementById(modalId);
                    const modal = bootstrap.Modal.getInstance(modalEl);
                    modal.hide();

                }
                form.reset();
                form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
                form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
                doFetch('/api/users');
            }
        })


// ==========================================================================================
}


document.addEventListener("DOMContentLoaded", function () {
    const contentList = document.getElementById("moderationContentList");
    const modal = new bootstrap.Modal(document.getElementById('contentModal'));
    const modalContent = document.getElementById('modalContent');
    const modalApproveBtn = document.getElementById('modalApproveBtn');
    const modalRejectBtn = document.getElementById('modalRejectBtn');
    let currentItem = null;

    // Получение CSRF токена
    function getCsrfToken() {
        return document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || '';
    }

    function getCsrfHeader() {
        return document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || 'X-CSRF-TOKEN';
    }

    function showLoading() {
        contentList.innerHTML = `
            <div class="text-center text-muted py-5">
                <i class="fas fa-spinner fa-spin fa-2x mb-3"></i>
                <p>Загрузка контента...</p>
            </div>`;
    }

    function getTypeName(type) {
        const typeNames = {
            'post': 'Пост',
            'blog': 'Блог',
            'event': 'Событие',
            'book': 'Книга'
        };
        return typeNames[type] || type;
    }

    function getContentTitle(item, type) {
        // For different content types, get title from different fields
        switch (type) {
            case 'event':
                return item.eventTranslations && item.eventTranslations[0] && item.eventTranslations[0].title
                    ? item.eventTranslations[0].title
                    : 'Без заголовка';
            case 'blog':
                return item.blogTranslations && item.blogTranslations[0] && item.blogTranslations[0].title
                    ? item.blogTranslations[0].title
                    : item.title || 'Без заголовка';
            case 'post':
                // Both blog and post use postTranslations structure
                return item.postTranslations && item.postTranslations[0] && item.postTranslations[0].title
                    ? item.postTranslations[0].title
                    : item.title || 'Без заголовка';
            case 'book':
                return item.name || 'Без заголовка';
            default:
                return item.title || 'Без заголовка';
        }
    }

    function getContentDescription(item, type) {
        switch (type) {
            case 'event':
                return item.eventTranslations && item.eventTranslations[0] && item.eventTranslations[0].description
                    ? item.eventTranslations[0].description
                    : 'Нет описания';
            case 'blog':
                return item.blogTranslations && item.blogTranslations[0] && item.blogTranslations[0].content
                    ? item.blogTranslations[0].content
                    : item.content || 'Нет контента';
            case 'post':
                // Both blog and post use postTranslations structure with content field
                return item.postTranslations && item.postTranslations[0] && item.postTranslations[0].content
                    ? item.postTranslations[0].content
                    : item.content || 'Нет контента';
            case 'book':
                return item.description || 'Нет описания';
            default:
                return item.content || 'Нет контента';
        }
    }

    function formatEventDateTime(startDate, endDate) {
        if (!startDate) return '';

        const start = new Date(startDate);
        const startFormatted = start.toLocaleDateString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });

        if (endDate) {
            const end = new Date(endDate);
            const endFormatted = end.toLocaleDateString('ru-RU', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
            return `${startFormatted} - ${endFormatted}`;
        }

        return startFormatted;
    }

    function getEventLocation(item) {
        if (item.isOffline) {
            return item.address || 'Адрес не указан';
        } else {
            return item.url || 'Ссылка не указана';
        }
    }

    function showContentDetails(item, type) {
        currentItem = { ...item, type };

        const title = getContentTitle(item, type);
        const content = getContentDescription(item, type);
        const author = item.creator && item.creator.name || 'Неизвестен';
        const date = new Date(item.createdAt || Date.now()).toLocaleDateString('ru-RU');

        let additionalInfo = '';

        // Add type-specific information
        if (type === 'event') {
            const eventDateTime = formatEventDateTime(item.startDate, item.endDate);
            const location = getEventLocation(item);
            const locationType = item.isOffline ? 'Офлайн' : 'Онлайн';

            additionalInfo = `
                <div class="mb-3">
                    <strong>Дата и время:</strong> ${eventDateTime}
                </div>
                <div class="mb-3">
                    <strong>Тип:</strong> ${locationType}
                </div>
                <div class="mb-3">
                    <strong>${item.isOffline ? 'Адрес' : 'Ссылка'}:</strong> ${location}
                </div>
            `;
        } else if (type === 'book') {
            additionalInfo = `
                ${item.authors ? `<div class="mb-3"><strong>Авторы:</strong> ${item.authors}</div>` : ''}
                ${item.isbn ? `<div class="mb-3"><strong>ISBN:</strong> ${item.isbn}</div>` : ''}
                ${item.category && item.category.name ? `<div class="mb-3"><strong>Категория:</strong> ${item.category.name}</div>` : ''}
            `;
        } else if (type === 'post' || type === 'blog') {
            console.log('Debug blog/post item:', item);
            additionalInfo = `
                ${item.postFiles && item.postFiles.length > 0 ? `<div class="mb-3"><strong>Файлы:</strong> ${item.postFiles.length} файл(ов)</div>` : ''}
            `;
        }

        modalContent.innerHTML = `
            <div class="mb-3">
                <img src="/api/files/${item.mainImageId || (item.mainImage && item.mainImage.id)}/view" 
                     alt="Изображение" class="modal-image" 
                     onerror="this.src='/static/images/img.png'">
            </div>
            <div class="mb-3">
                <strong>Загаловок:</strong> ${title}
            </div>
            <div class="mb-3">
                <strong>Тип:</strong> <span class="badge bg-secondary">${getTypeName(type)}</span>
            </div>
            <div class="mb-3">
                <strong>Автор:</strong> ${author}
            </div>
            <div class="mb-3">
                <strong>Дата создания:</strong> ${date}
            </div>
            ${additionalInfo}
            <div class="mb-3">
                <strong>${type === 'book' ? 'Описание' : (type === 'event' ? 'Описание' : 'Контент')}:</strong>
                <div class="publication-text collapsed" id="modalPostContent">${content}</div>
                <div class="show-more-btn" id="modalToggleButton">Показать ещё</div>
            </div>
        `;

        const toggleBtn = document.getElementById('modalToggleButton');
        const postContent = document.getElementById('modalPostContent');

        toggleBtn.addEventListener('click', function () {
            if (postContent.classList.contains('collapsed')) {
                postContent.classList.remove('collapsed');
                toggleBtn.textContent = 'Скрыть';
            } else {
                postContent.classList.add('collapsed');
                toggleBtn.textContent = 'Показать ещё';
            }
        });

        modal.show();
    }

    function showContentDetailsById(id, type) {
        const endpoint = getApiEndpoint(type);
        const headers = {
            'Content-Type': 'application/json'
        };

        // Добавляем CSRF токен в заголовки
        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        fetch(`${endpoint}/${id}`, {
            method: 'GET',
            headers: headers
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка: ${response.status}`);
                }
                return response.json();
            })
            .then(item => {
                showContentDetails(item, type);
            })
            .catch(error => {
                console.log(`${endpoint}/${id}`);
                console.error('Ошибка при загрузке деталей:', error);
                showNotification('Ошибка при загрузке деталей контента', 'error');
            });
    }

    function getApiEndpoint(type) {
        const endpoints = {
            'post': '/api/users/posts',
            'blog': '/api/users/blogs',
            'event': '/api/users/events',
            'book': '/api/users/books'
        };
        return endpoints[type] || '/api/users/content';
    }

    function performModerationActionById(action, id, type) {
        currentItem = { id, type };
        performModerationAction(action);
    }

    // ИСПРАВЛЕНО: Новая функция для выполнения действий модерации
    function performModerationAction(action) {
        if (!currentItem) return;

        // Используем новые endpoints из AdminController
        const url = `/api/admin/${action}/${currentItem.type}/${currentItem.id}`;

        // Подготавливаем заголовки с CSRF токеном
        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        fetch(url, {
            method: 'POST',
            headers: headers
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка: ${response.status}`);
                }
                return response.text().then(text => {
                    // Пытаемся распарсить JSON, если не получается - возвращаем пустой объект
                    try {
                        return text ? JSON.parse(text) : {};
                    } catch (e) {
                        return { message: 'Действие выполнено успешно' };
                    }
                });
            })
            .then(result => {
                modal.hide();

                const cardElement = document.querySelector(`[data-item-id="${currentItem.id}"][data-item-type="${currentItem.type}"]`);
                if (cardElement) {
                    cardElement.classList.add('removing');
                    setTimeout(() => {
                        cardElement.remove();
                        const remainingCards = contentList.querySelectorAll('.content-card');
                        if (remainingCards.length === 0) {
                            showNoContent();
                        }
                    }, 300);
                }

                const actionMessage = action === 'approve' ? 'одобрен' : 'отклонён';
                showNotification(
                    result.message || `Контент ${actionMessage}`,
                    'success'
                );
            })
            .catch(error => {
                console.error('Ошибка при выполнении действия:', error);
                showNotification('Ошибка при выполнении действия', 'error');
            });
    }

    function showNotification(message, type) {
        const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
        const alertHtml = `
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        `;

        const alertContainer = document.createElement('div');
        alertContainer.innerHTML = alertHtml;
        document.body.insertBefore(alertContainer, document.body.firstChild);

        setTimeout(() => {
            alertContainer.remove();
        }, 5000);
    }

    function showNoContent() {
        contentList.innerHTML = `
            <div class="no-content">
                <svg width="120" height="120" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <rect x="12" y="14" width="40" height="36" rx="3" ry="3" fill="#eaeaea" stroke="#888" stroke-width="2"/>
                    <line x1="20" y1="20" x2="44" y2="20" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="26" x2="44" y2="26" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="32" x2="44" y2="32" stroke="#bbb" stroke-width="1.5"/>
                    <line x1="20" y1="38" x2="44" y2="38" stroke="#bbb" stroke-width="1.5"/>
                </svg>
                <p class="fs-5">Нет контента на модерации</p>
            </div>
        `;
    }

    function createContentCard(item, type) {
        const title = getContentTitle(item, type);
        const author = item.creator && item.creator.name || 'Неизвестен';
        const date = new Date(item.createdAt || Date.now()).toLocaleDateString('ru-RU');
        const imageId = item.mainImageId || (item.mainImage && item.mainImage.id) || '';

        return `
            <div class="content-card d-flex" data-item-id="${item.id}" data-item-type="${type}">
                <div class="p-3">
                    <img src="/api/files/${imageId}/view" 
                         alt="${getTypeName(type)} Image" 
                         class="content-image" 
                         onclick="showContentDetailsById(${item.id}, '${type}')"
                         onerror="this.src='/static/images/img.png'">
                </div>
                <div class="content-info">
                    <div class="content-title" onclick="showContentDetailsById(${item.id}, '${type}')">${title}</div>
                    <div class="content-meta">${author} • ${date}</div>
                    <span class="badge bg-info me-2">${getTypeName(type)}</span>
                    <span class="status-badge status-pending">Ожидает</span>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-approve" onclick="performModerationActionById('approve', ${item.id}, '${type}')">
                        <i class="fas fa-check"></i> Принять
                    </button>
                    <button class="btn btn-reject" onclick="performModerationActionById('reject', ${item.id}, '${type}')">
                        <i class="fas fa-times"></i> Отклонить
                    </button>
                </div>
            </div>
        `;
    }

    function loadAllContent() {
        showLoading();

        const types = ['post', 'blog', 'event', 'book'];
        const urls = types.map(type => `/api/users/moder?type=${type}&page=0&size=10`);

        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        Promise.all(urls.map(url =>
            fetch(url, {
                method: 'GET',
                headers: headers
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Ошибка загрузки: ${response.status}`);
                    }
                    return response.json();
                })
                .catch(error => {
                    console.warn(`Ошибка загрузки для ${url}:`, error);
                    return { content: [] };
                })
        ))
            .then(results => {
                let html = '';
                let hasContent = false;

                results.forEach((result, index) => {
                    const type = types[index];
                    if (result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            hasContent = true;
                            html += createContentCard(item, type);
                        });
                    }
                });

                if (!hasContent) {
                    showNoContent();
                } else {
                    contentList.innerHTML = html;
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
            });
    }

    window.showContentDetailsById = showContentDetailsById;
    window.performModerationActionById = performModerationActionById;

    modalApproveBtn.addEventListener('click', () => performModerationAction('approve'));
    modalRejectBtn.addEventListener('click', () => performModerationAction('reject'));

    loadAllContent();
});


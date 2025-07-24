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
    const paginationNav = document.getElementById('usersPagination');
    paginationNav.style.display = 'block';
    const paginationContainer = document.querySelector('#usersPagination .pagination');
    paginationContainer.innerHTML = '';

    const createPageItem = (page, text = null, active = false, disabled = false, isNavigation = false) => {
        const li = document.createElement('li');
        li.className = `page-item${active ? ' active' : ''}${disabled ? ' disabled' : ''}`;

        const a = document.createElement('a');
        a.className = `page-link${isNavigation ? ' nav-link' : ''}`;
        a.href = '#';
        a.textContent = text || page;

        if (!disabled) {
            a.dataset.page = page;
        }

        li.appendChild(a);
        return li;
    };

    if (currentPage > 1) {
        paginationContainer.appendChild(
            createPageItem(currentPage - 1, 'Назад', false, false, true)
        );
    }

    const maxVisiblePages = 5;
    let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
        startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }

    if (startPage > 1) {
        paginationContainer.appendChild(createPageItem(1, '1', 1 === currentPage));
        if (startPage > 2) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            const span = document.createElement('span');
            span.className = 'page-link';
            span.textContent = '...';
            li.appendChild(span);
            paginationContainer.appendChild(li);
        }
    }

    for (let i = startPage; i <= endPage; i++) {
        paginationContainer.appendChild(
            createPageItem(i, i.toString(), i === currentPage)
        );
    }

    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            const span = document.createElement('span');
            span.className = 'page-link';
            span.textContent = '...';
            li.appendChild(span);
            paginationContainer.appendChild(li);
        }
        paginationContainer.appendChild(
            createPageItem(totalPages, totalPages.toString(), totalPages === currentPage)
        );
    }

    if (currentPage < totalPages) {
        paginationContainer.appendChild(
            createPageItem(currentPage + 1, 'Вперед', false, false, true)
        );
    }

    paginationContainer.querySelectorAll('a.page-link[data-page]').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const page = parseInt(e.target.dataset.page);
            if (!isNaN(page) && page !== currentPage && page >= 1 && page <= totalPages) {
                doFetch(url, page);
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
            if (data.length === 0 || !data.content || data.content.length === 0) {
                document.getElementById('usersPagination').style.display = 'none';
                userContentList.innerHTML = '<div class="alert alert-info text-center">Пользователи не найдены</div>';
            } else {
                addUserToTable(data.content);
                renderPagination(data.number + 1, data.totalPages, `${url}${connector}`);
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
            // Скрываем пагинацию при ошибке
            document.getElementById('usersPagination').style.display = 'none';
            userContentList.innerHTML = '<div class="alert alert-danger text-center">Произошла ошибка при загрузке данных</div>';
        });
}

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

    let resultTableUsers = document.getElementById('resultTableUsers');
    users.forEach(user => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
  <td>${user.id}</td>
  <td>${user.name || ''}</td>
  <td>${user.email || ''}</td>
  <td><span class="badge bg-secondary">${user.role?.name || 'Не указано'}</span></td>
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
             data-user-name="${user.name || ''}" 
             data-user-surname="${user.surname || ''}" 
             data-user-role="${user.role?.name || ''}" 
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
    const typeFilter = document.getElementById("moderationTypeFilter");
    const searchInput = document.getElementById("moderationSearch");
    const searchBtn = document.getElementById("moderationSearchBtn");
    const paginationContainer = document.getElementById("moderationPagination");
    let currentItem = null;
    let currentPage = 0;
    let currentSize = 10;
    let currentType = 'all';
    let currentQuery = '';
    let totalPages = 0;
    let totalElements = 0;

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
                <strong>Заголовок:</strong> ${title}
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

    function performModerationAction(action) {
        if (!currentItem) return;

        const url = `/api/admin/${action}/${currentItem.type}/${currentItem.id}`;

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
                        if (remainingCards.length === 0 && totalElements > currentSize) {
                            // Если элементов больше чем на одной странице, но текущая страница пуста
                            if (currentPage > 0) {
                                currentPage--;
                            }
                            loadContent();
                        } else if (remainingCards.length === 0) {
                            // Если это последние элементы
                            showNoContent();
                        }
                    }, 300);
                }

                const actionMessage = action === 'approve' ? 'одобрен' : 'отклонён';
                showNotification(
                    result.message || `Контент ${actionMessage}`,
                    'success'
                );

                updateModerationCount();
            })
            .catch(error => {
                console.error('Ошибка при выполнении действия:', error);
                showNotification('Ошибка при выполнении действия', 'error');
            });
    }

    function showNotification(message, type) {
        const toastContainerId = 'toastContainer';

        let toastContainer = document.getElementById(toastContainerId);
        if (!toastContainer) {
            toastContainer = document.createElement('div');
            toastContainer.id = toastContainerId;
            toastContainer.className = 'position-fixed top-0 end-0 p-4';
            toastContainer.style.zIndex = '1080';
            document.body.appendChild(toastContainer);
        }

        const toastId = `toast-${Date.now()}`;
        const bgColor = type === 'success' ? 'rgba(40, 167, 69, 0.85)' : 'rgba(220, 53, 69, 0.85)';

        const toastHtml = `
        <div id="${toastId}" class="toast fade show shadow-lg" role="alert" aria-live="assertive" aria-atomic="true"
             style="
                 min-width: 320px;
                 max-width: 420px;
                 margin-bottom: 1rem;
                 background-color: ${bgColor};
                 color: #fff;
                 border: none;
                 border-radius: 12px;
                 backdrop-filter: blur(8px);
                 font-size: 1rem;
                 padding: 1rem 1.5rem;
                 transition: opacity 0.5s ease-in-out;
             ">
            <div class="d-flex justify-content-between align-items-center">
                <div class="toast-body">${message}</div>
                <button type="button" class="btn-close btn-close-white ms-3" data-bs-dismiss="toast" aria-label="Закрыть"></button>
            </div>
        </div>
        `;

        toastContainer.insertAdjacentHTML('beforeend', toastHtml);

        const toastElement = document.getElementById(toastId);
        const bsToast = new bootstrap.Toast(toastElement, { delay: 5000 });
        bsToast.show();

        toastElement.addEventListener('hidden.bs.toast', () => {
            toastElement.remove();
        });
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
        paginationContainer.style.display = 'none';
    }

    function createContentCard(item, type) {
        const title = getContentTitle(item, type);
        const author = item.creator && item.creator.name || 'Неизвестен';
        const date = new Date(item.createdAt || Date.now()).toLocaleDateString('ru-RU');
        const imageId = item.mainImageId || (item.mainImage && item.mainImage.id) || '';

        return `
            <div class="content-card" data-item-id="${item.id}" data-item-type="${type}">
                <div class="moderation-image-wrapper">
                    <img src="/api/files/${imageId}/view" 
                         alt="${getTypeName(type)} Image" 
                         class="moderation-image" 
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

    function buildPagination(currentPageNum, totalPagesNum) {
        if (totalPagesNum <= 1) {
            paginationContainer.style.display = 'none';
            return;
        }

        paginationContainer.style.display = 'block';

        // Создаем контейнер пагинации если его нет
        let pagination = paginationContainer.querySelector('.pagination');
        if (!pagination) {
            pagination = document.createElement('nav');
            pagination.innerHTML = '<ul class="pagination justify-content-center"></ul>';
            paginationContainer.appendChild(pagination);
            pagination = pagination.querySelector('.pagination');
        }

        pagination.innerHTML = '';

        // Предыдущая страница
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPageNum === 0 ? 'disabled' : ''}`;
        prevLi.innerHTML = `
            <a class="page-link" href="#" data-page="${currentPageNum - 1}" ${currentPageNum === 0 ? 'tabindex="-1"' : ''}>
                <i class="fas fa-chevron-left"></i>
            </a>
        `;
        pagination.appendChild(prevLi);

        const maxVisiblePages = 5;
        let startPage = Math.max(0, currentPageNum - Math.floor(maxVisiblePages / 2));
        let endPage = Math.min(totalPagesNum - 1, startPage + maxVisiblePages - 1);

        if (endPage - startPage < maxVisiblePages - 1) {
            startPage = Math.max(0, endPage - maxVisiblePages + 1);
        }

        if (startPage > 0) {
            const firstLi = document.createElement('li');
            firstLi.className = 'page-item';
            firstLi.innerHTML = `<a class="page-link" href="#" data-page="0">1</a>`;
            pagination.appendChild(firstLi);

            if (startPage > 1) {
                const dotsLi = document.createElement('li');
                dotsLi.className = 'page-item disabled';
                dotsLi.innerHTML = '<span class="page-link">...</span>';
                pagination.appendChild(dotsLi);
            }
        }

        for (let i = startPage; i <= endPage; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === currentPageNum ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#" data-page="${i}">${i + 1}</a>`;
            pagination.appendChild(li);
        }

        if (endPage < totalPagesNum - 1) {
            if (endPage < totalPagesNum - 2) {
                const dotsLi = document.createElement('li');
                dotsLi.className = 'page-item disabled';
                dotsLi.innerHTML = '<span class="page-link">...</span>';
                pagination.appendChild(dotsLi);
            }

            const lastLi = document.createElement('li');
            lastLi.className = 'page-item';
            lastLi.innerHTML = `<a class="page-link" href="#" data-page="${totalPagesNum - 1}">${totalPagesNum}</a>`;
            pagination.appendChild(lastLi);
        }

        // Следующая страница
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPageNum === totalPagesNum - 1 ? 'disabled' : ''}`;
        nextLi.innerHTML = `
            <a class="page-link" href="#" data-page="${currentPageNum + 1}" ${currentPageNum === totalPagesNum - 1 ? 'tabindex="-1"' : ''}>
                <i class="fas fa-chevron-right"></i>
            </a>
        `;
        pagination.appendChild(nextLi);

        // Добавляем обработчики событий для всех ссылок
        pagination.addEventListener('click', function(e) {
            if (e.target.matches('a[data-page], a[data-page] *')) {
                e.preventDefault();
                const link = e.target.closest('a[data-page]');
                const page = parseInt(link.dataset.page);

                if (!link.closest('.page-item').classList.contains('disabled') &&
                    page >= 0 && page < totalPagesNum && page !== currentPageNum) {
                    currentPage = page;
                    loadContent();
                }
            }
        });
    }

    function loadContent() {
        showLoading();
        updateModerationCount();

        if (currentType === 'all') {
            loadAllContent();
        } else {
            const type = currentType === 'posts' ? 'post' : currentType;
            loadSpecificContent(type);
        }
    }

    function loadAllContent() {
        const types = ['post', 'blog', 'event', 'book'];

        const promises = types.map(type => loadTypeContent(type, 0, 1000)); // Большой размер для получения всех элементов

        Promise.all(promises)
            .then(results => {
                let allItems = [];

                results.forEach((result, index) => {
                    if (result && result.content && Array.isArray(result.content)) {
                        result.content.forEach(item => {
                            allItems.push({ item, type: types[index] });
                        });
                    }
                });

                // Сортируем по дате создания
                allItems.sort((a, b) => {
                    const dateA = new Date(a.item.createdAt || 0);
                    const dateB = new Date(b.item.createdAt || 0);
                    return dateB - dateA;
                });

                // Применяем пагинацию
                totalElements = allItems.length;
                totalPages = Math.ceil(totalElements / currentSize);

                // Проверяем, не превышает ли текущая страница общее количество страниц
                if (currentPage >= totalPages && totalPages > 0) {
                    currentPage = totalPages - 1;
                }

                const startIndex = currentPage * currentSize;
                const endIndex = startIndex + currentSize;
                const pageItems = allItems.slice(startIndex, endIndex);

                if (pageItems.length === 0) {
                    showNoContent();
                } else {
                    let html = '';
                    pageItems.forEach(({ item, type }) => {
                        html += createContentCard(item, type);
                    });
                    contentList.innerHTML = html;
                    buildPagination(currentPage, totalPages);
                }
            })
            .catch(error => {
                console.error("Ошибка при загрузке контента: ", error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
                paginationContainer.style.display = 'none';
            });
    }

    function loadSpecificContent(type) {
        loadTypeContent(type, currentPage, currentSize)
            .then(result => {
                if (!result || !result.content || !Array.isArray(result.content)) {
                    showNoContent();
                    return;
                }

                totalPages = result.totalPages || 0;
                totalElements = result.totalElements || result.content.length;

                // Проверяем, не превышает ли текущая страница общее количество страниц
                if (currentPage >= totalPages && totalPages > 0) {
                    currentPage = totalPages - 1;
                    // Перезагружаем с исправленной страницей
                    loadSpecificContent(type);
                    return;
                }

                if (result.content.length === 0) {
                    showNoContent();
                } else {
                    let html = '';
                    result.content.forEach(item => {
                        html += createContentCard(item, type);
                    });
                    contentList.innerHTML = html;
                    buildPagination(currentPage, totalPages);
                }
            })
            .catch(error => {
                console.error(`Ошибка при загрузке ${type} контента:`, error);
                contentList.innerHTML = `<p class="text-danger text-center py-5">Ошибка загрузки контента.</p>`;
                paginationContainer.style.display = 'none';
            });
    }

    function loadTypeContent(type, page, size) {
        const url = `/api/users/moder?type=${type}&page=${page}&size=${size}${currentQuery ? `&query=${encodeURIComponent(currentQuery)}` : ''}`;

        const headers = {
            'Content-Type': 'application/json'
        };

        const csrfToken = getCsrfToken();
        const csrfHeader = getCsrfHeader();
        if (csrfToken) {
            headers[csrfHeader] = csrfToken;
        }

        return fetch(url, {
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
                console.warn(`Ошибка загрузки для ${type}:`, error);
                return { content: [], totalPages: 0, totalElements: 0 };
            });
    }

    function performSearch() {
        currentQuery = searchInput.value.trim();
        currentPage = 0; // Сбрасываем на первую страницу при поиске
        loadContent();
    }

    function resetFilters() {
        currentQuery = '';
        currentPage = 0;
        currentType = 'all';
        searchInput.value = '';
        typeFilter.value = 'all';
        loadContent();
    }

    // Обработчики событий
    typeFilter.addEventListener('change', (e) => {
        currentType = e.target.value;
        currentPage = 0; // Сбрасываем на первую страницу при смене фильтра
        loadContent();
    });

    searchBtn.addEventListener('click', performSearch);

    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            performSearch();
        }
    });

    let searchTimeout;
    searchInput.addEventListener('input', () => {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            if (searchInput.value.trim() !== currentQuery) {
                performSearch();
            }
        }, 500);
    });

    window.showContentDetailsById = showContentDetailsById;
    window.performModerationActionById = performModerationActionById;

    modalApproveBtn.addEventListener('click', () => performModerationAction('approve'));
    modalRejectBtn.addEventListener('click', () => performModerationAction('reject'));

    // Инициализация
    loadContent();
});

function updateModerationCount() {
    fetch('/api/users/count')
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при получении данных');
            }
            return response.text();
        })
        .then(countStr => {
            const badge = document.getElementById('moderationCount');
            if (badge) {
                badge.textContent = countStr;
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}





let usersTab = document.getElementById('users-tab');
let userContentList = document.getElementById('usersContentList');
usersTab.addEventListener('shown.bs.tab', function (e) {
    doFetch('/api/users', -1, addUserToTable, changeEditModal, () => showEmptyMessage('usersContentList'));
});



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
    doFetch(url, -1, addUserToTable, changeEditModal, () => showEmptyMessage('usersContentList'));

}
function renderPagination(currentPage, totalPages, url) {
    // document.getElementById('usersPagination').style.display = 'block';
    const paginationContainer = document.querySelector('.pagination');
    // console.log(paginationContainer);
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
function doFetch(
    url,
    page = 1,
    onSuccess = addUserToTable,
    changeModals = changeEditModal,
    emptyContentHandler = showEmptyMessage
) {
    if (page < 1) {
        page = 1;
    }
    const connector = url.includes('?') ? '&' : '?';

    fetch(`${url}${connector}page=${page}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка сети: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            const contentArray = Array.isArray(data.content)
                ? data.content
                : Array.isArray(data)
                    ? data
                    : [];
            console.log(contentArray);

            if (contentArray.length === 0) {
                emptyContentHandler();
                return;
            } else {
                onSuccess(data);
                changeModals();
                initDropdownBehavior();
            }
        })
        .catch(error => {
            console.error("Ошибка загрузки данных:", error);
        });
}



function showEmptyMessage(containerId = 'usersContentList', message = '😕 Ничего не найдено') {
    const container = document.getElementById(containerId);
    console.log(container);
    container.innerHTML = `
        <div class="alert alert-warning text-center mt-4" role="alert">
            <h5 class="mb-0">${message}</h5>
            <p class="mb-0">Попробуйте изменить параметры поиска или фильтрации.</p>
        </div>
    `;
}


//
function addUserToTable(content) {
    const users = content.content;

    if (!Array.isArray(users) || users.length === 0) {
        showEmptyMessage('usersContentList');
        return;
    }

    userContentList.innerHTML = `
        <div class="table-responsive" style="max-height: 400px; overflow-y: auto;">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Имя пользователя</th>
                        <th>Email</th>
                        <th>Роль</th>
                        <th>Статус</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody id="resultTableUsers"></tbody>
            </table>
        </div>
    `;

    const resultTableUsers = document.getElementById('resultTableUsers');
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
    sendForm(
        form,
        '/api/users',
        'POST',
        'createUserModal',
        getModelFromFormCreateUpdateUser(form, 'createUserModal'),
        onUserSaveError,
        () => doFetch('/api/users', -1, addUserToTable, changeEditModal, () => showEmptyMessage('usersContentList'))
    );

}

async function handleUserAction(method, successMessage, errorMessage, url, modalId, successUrlFetch) {
    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            if (modalId) {
                const modalEl = document.getElementById(modalId);
                const modal = bootstrap.Modal.getInstance(modalEl);
                modal.hide();
            }
            
            clearErrorMessage(modalId);

            if (typeof successUrlFetch === 'string') {
                doFetch(successUrlFetch);
            } else if (typeof successUrlFetch === 'object') {
                const { url, onSuccess, changeModals } = successUrlFetch;
                doFetch(url, -1, onSuccess, changeModals);
            }

        } else {
            const errorData = await response.json();
            const errorMsg = errorData.message || errorMessage || 'Что-то пошло не так.';

            showErrorMessage(modalId, errorMsg);

            throw new Error(errorMsg);
        }
    } catch (error) {
        console.error('Ошибка:', error.message);
        showErrorMessage(modalId, 'Что-то пошло не так.');
    }
}

function showErrorMessage(modalId, message) {
    if (!modalId) return;

    const modalEl = document.getElementById(modalId);
    if (!modalEl) return;

    let errorBox = modalEl.querySelector('#errorMessageBox');

    if (!errorBox) {
        errorBox = document.createElement('div');
        errorBox.id = 'errorMessageBox';
        errorBox.style.color = 'red';
        errorBox.style.marginBottom = '1rem';
        errorBox.style.fontWeight = 'bold';

        const modalBody = modalEl.querySelector('.modal-body');
        if (modalBody) {
            modalBody.prepend(errorBox);
        } else {
            modalEl.prepend(errorBox);
        }
    }

    errorBox.textContent = message;
}

function clearErrorMessage(modalId) {
    if (!modalId) return;

    const modalEl = document.getElementById(modalId);
    if (!modalEl) return;

    const errorBox = modalEl.querySelector('#errorMessageBox');
    if (errorBox) {
        errorBox.remove();
    }
}


//
function changeEditModal() {
    document.getElementById('resultTableUsers').addEventListener('click', async function(event) {
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
            // console.log(deleteUserInput);
            deleteUserModal.textContent = 'Вы уверены что хотите удалить пользователя с ID:' + userId;

        } else if (button.classList.contains('block-button')) {
            await handleUserAction(
                'PATCH',
                `Пользователь с id=${userId} успешно заблокирован/изменён`,
                'Ошибка блокировки/изменения пользователя',
                `/api/users/${userId}`,
                null,
                {
                    url: '/api/users',
                    onSuccess: addUserToTable,
                    changeModals: changeEditModal
                }
            );

        }
    });
}

let editUserBtn = document.getElementById('editUserBtn');
editUserBtn.onclick = function () {
    let editUserForm = document.getElementById('editUserForm');
    let userId = document.getElementById('editUserId').value
    sendForm(
        editUserForm,
        `/api/users/${userId}`,
        'PUT',
        'editUserModal',
        getModelFromFormCreateUpdateUser(editUserForm, 'editUserModal'),
        onUserSaveError,
        () => doFetch('/api/users', -1, addUserToTable, changeEditModal, () => showEmptyMessage('usersContentList'))
    );

}
//
let deleteUserBtn = document.getElementById('deleteUserBtn');
deleteUserBtn.onclick = async function () {
    let userId = deleteUserBtn.dataset.userId
    let typeId = deleteUserBtn.dataset.typeId
    let url, successUrl, onSuccess, changeModals;
    if (userId) {
        url = `/api/users/${userId}`
        successUrl = '/api/users'
        onSuccess = addUserToTable;
        changeModals = changeEditModal;
    }
    if (typeId) {
        let contentType = deleteUserBtn.dataset.contentType
        url = `/api/${contentType}/${typeId}`;
        successUrl = `/api/${contentType}`;
        onSuccess = addContentInList;
        changeModals = changeModalForTypes;
    }
    await handleUserAction(
        'DELETE',
        `Объект успешно удалён`,
        'Ошибка удаления',
        url,
        'deleteUserModal',
        {
            url: successUrl,
            onSuccess,
            changeModals
        }
    );
}

function getModelFromFormCreateUpdateUser(form, modalId) {
    let selectElement;
    // console.log(form);
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
    return data;
}

function sendForm(form, fetchUrl, method, modalId, data, onError, onSuccessFetch) {
    fetch(fetchUrl, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data),
    })
        .then(async response => {
            if (!response.ok) {
                onError(response, form)
            } else {
                if (modalId) {
                    const modalEl = document.getElementById(modalId);
                    let modal = bootstrap.Modal.getInstance(modalEl);
                    if (!modal) {
                        modal = new bootstrap.Modal(modalEl);
                    }
                    modal.hide();
                }

                form.reset();
                form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
                form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

                if (typeof onSuccessFetch === 'function') {
                    onSuccessFetch();
                }
            }
        });
}


function onUserSaveError(response, form) {
    response.json().then(errorBody => {
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
    });
}


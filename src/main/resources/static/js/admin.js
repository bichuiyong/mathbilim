const csrfToken = document.querySelector('input[name="_csrf"]')?.value ||
    document.querySelector('input[name="csrf"]')?.value ||
    document.querySelector('meta[name="_csrf"]')?.getAttribute('content');

window.onload = function () {
    doFetch('api/users');
    changeEditModal()
}


const searchButton = document.getElementById('searchButton');
searchButton.onclick = function () {
    let searchInputValue = document.getElementById('searchInput').value;
    let url = "api/users"
    if (searchInputValue) {
        url = "api/users?query=" + searchInputValue
    }
    doFetch(url);
}
function renderPagination(currentPage, totalPages, url) {
    const paginationContainer = document.querySelector('.pagination');
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
        .catch(error => {});
}

function addUserToTable(users) {
    let resultTable = document.getElementById('resultTable');
    resultTable.innerHTML = "";
    users.forEach(user => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.email}</td>
    <td><span class="badge bg-secondary">${user.role.name}</span></td>
      <td><span class="${user.enabled ? 'user-status-active' : 'user-status-blocked'}">
                ${user.enabled ? 'Активен' : 'Неактивен'}</span></td>
      <td>
    <td>
      <button class="edit-button btn btn-sm btn-info me-1" data-bs-toggle="modal" data-bs-target="#editUserModal" data-user-id="${user.id}" data-user-name="${user.name}" data-user-surname="${user.surname}" data-user-role="${user.role.name}" data-user-type="${user.type.id}">Изменить</button>
      <button class="delete-button btn btn-sm btn-danger me-1" data-bs-toggle="modal" data-bs-target="#deleteUserModal" data-user-id="${user.id}">Удалить</button>
      <button class="block-button btn btn-sm btn-warning" data-user-id="${user.id}">${user.enabled ? 'Заблокировать' : 'Разблокировать'}</button>
    </td>
  `;
        resultTable.appendChild(tr);
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

function changeEditModal() {
    document.getElementById('resultTable').addEventListener('click', async function(event) {
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
    let type;
    if (selectedOption.value && selectedOption.dataset.name) {
        type = {id: selectedOption.value, name: selectedOption.dataset.name}
    } else {
        type = null;
    }
    data.type = type
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
                const modalEl = document.getElementById(modalId);
                const modal = bootstrap.Modal.getInstance(modalEl);
                modal.hide();
                doFetch('/api/users');
            }
        })




}

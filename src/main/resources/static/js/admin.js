const csrfToken = document.querySelector('input[name="_csrf"]')?.value ||
    document.querySelector('input[name="csrf"]')?.value ||
    document.querySelector('meta[name="_csrf"]')?.getAttribute('content');

window.onload = function () {
    doFetch('api/users');

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
function doFetch(url) {
    fetch(url)
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
                changeEditModal()
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

async function handleUserAction(method, successMessage, errorMessage, userId) {
    try {
        const response = await fetch(`/api/users/${userId}`, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            console.log(successMessage);
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
            document.getElementById('deleteUserInput').value = userId;
            deleteUserModal.textContent = 'Вы уверены что хотите удалить пользователя с ID:' + userId;

        } else if (button.classList.contains('block-button')) {
            await handleUserAction('PATCH',
                `Пользователь с id=${userId} успешно заблокирован/изменён`,
                'Ошибка блокировки/изменения пользователя', userId);
            doFetch('/api/users')
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
    let userId = document.getElementById('deleteUserInput').value
    await handleUserAction('DELETE',
        `Пользователь с id=${userId} успешно удалён`,
        'Ошибка удаления пользователя', userId);
    doFetch('/api/users')
}

function sendForm(form, fetchUrl, method, modalId) {
    let selectElement;
    if (form.id === 'createNewUser') {
        selectElement = document.getElementById('type');
    } else {
        selectElement = document.getElementById('editUserType');
    }
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    data.role = {name: data.role};
    data.type = {id: selectedOption.value, name: selectedOption.dataset.name}
    console.log(data);
    fetch(fetchUrl, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                console.log(response);
                throw new Error('Ошибка запроса');
            }
            let myModalEl = document.getElementById(modalId);
            let modal = bootstrap.Modal.getInstance(myModalEl);
            modal.hide();
            doFetch('api/users');
        })

}
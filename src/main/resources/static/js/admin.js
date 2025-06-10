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
      <button class="btn btn-sm btn-danger me-1" data-bs-toggle="modal" data-bs-target="#deleteUserModal">Удалить</button>
      <button class="btn btn-sm btn-warning">Заблокировать</button>
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

function changeEditModal() {
    document.getElementById('resultTable').addEventListener('click', function(event) {
        if (event.target.classList.contains('edit-button')) {
            const button = event.target;
            const userId = button.dataset.userId;
            const userName = button.dataset.userName;
            const userSurname = button.dataset.userSurname
            const userRole = button.dataset.userRole;
            const userType = button.dataset.userType;

            document.getElementById('editUserId').value = userId;
            document.getElementById('editUserName').value = userName;
            document.getElementById('editUserSurname').value = userSurname;
            document.getElementById('editUserRole').value = userRole;
            document.getElementById('editUserType').value = userType;
        }
    });
}

let editUserBtn = document.getElementById('editUserBtn');
editUserBtn.onclick = function () {
    let editUserForm = document.getElementById('editUserForm');
    let userId = document.getElementById('editUserId').value
    sendForm(editUserForm, `/api/users/${userId}`, 'PUT', 'editUserModal')
}

function sendForm(form, fetchUrl, method, modalId) {
    let selectElement;
    if (form.id === 'createNewUser') {
        selectElement = document.getElementById('type');
    } else {
        selectElement = document.getElementById('editUserType');
    }
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    // const form = document.getElementById('createNewUser');
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
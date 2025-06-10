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
      <button class="btn btn-sm btn-info me-1" data-bs-toggle="modal" data-bs-target="#editUserModal">Изменить</button>
      <button class="btn btn-sm btn-danger me-1" data-bs-toggle="modal" data-bs-target="#deleteUserModal">Удалить</button>
      <button class="btn btn-sm btn-warning">Заблокировать</button>
    </td>
  `;
        resultTable.appendChild(tr);
    });
}
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}



let createUserBtn = document.getElementById('createUserBtn');
createUserBtn.onclick = function () {
    const form = document.getElementById('createNewUser');
    const formData = new FormData(form);
    // const csrfToken = getCookie('XSRF-TOKEN');
    const data = Object.fromEntries(formData.entries());
    data.role = {name: data.role};
    console.log(data);
    fetch('/api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            // 'XSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data),
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                console.log(response);
                throw new Error('Ошибка запроса');
            }
            return response.json();
        })
        .then(result => {
            console.log('Успех:', result);
            alert('Данные отправлены!');
        })
}
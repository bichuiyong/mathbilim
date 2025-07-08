let usersTab = document.getElementById('users-tab');
let userContentList = document.getElementById('usersContentList');
usersTab.addEventListener('shown.bs.tab', function (e) {
    doFetch('/api/users');
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
    doFetch(url);
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
function doFetch(url, page = 1, onSuccess = addUserToTable) {
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
            // console.log('Ответ сервера:', data);
            if (data.length === 0) {

            } else {
                onSuccess(data)
                // addUserToTable(data.content)
                renderPagination(data.number + 1, data.totalPages, `${url}${connector}`)
            }

        })
        .catch(error => {});
}
//
function addUserToTable(content) {
    let users = content.content
    userContentList.innerHTML =  "<div class=\"table-responsive\" style=\"max-height: 400px; overflow-y: auto;\">\n" +
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
    sendForm(form, '/api/users', 'POST', 'createUserModal', getModelFromFormCreateUpdateUser(form, 'createUserModal'), onUserSaveError, "/api/users");
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
            // console.log(successMessage);
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
    sendForm(editUserForm, `/api/users/${userId}`, 'PUT', 'editUserModal', getModelFromFormCreateUpdateUser(editUserForm, 'editUserModal'))
}
//
let deleteUserBtn = document.getElementById('deleteUserBtn');
deleteUserBtn.onclick = async function () {
    let userId = deleteUserBtn.dataset.userId
    await handleUserAction('DELETE',
        `Пользователь с id=${userId} успешно удалён`,
        'Ошибка удаления пользователя', userId, 'deleteUserModal');
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

function sendForm(form, fetchUrl, method, modalId, data, onError, onSuccessFetchUrl) {
    // console.log(data);
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
            // console.log(response)
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
                doFetch(onSuccessFetchUrl);
            }
        })




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


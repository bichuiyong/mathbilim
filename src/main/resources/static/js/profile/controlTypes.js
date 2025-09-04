let categoryTab = document.getElementById('category-tab');
let typeContentList = document.getElementById('typeContentList');
let staticTypeSortBy = document.getElementById('staticTypeSortBy');
let createTypeBtn = document.getElementById('createTypeBtn');
let currentLocale = document.documentElement.lang || 'ru';

const translations = {
    en: { actions: "Actions", edit: "✏️ Edit", delete: "🗑️ Delete" },
    ru: { actions: "Действия", edit: "✏️ Изменить", delete: "🗑️ Удалить" },
    ky: { actions: "Иш-аракеттер", edit: "✏️ Өзгөртүү", delete: "🗑️ Өчүрүү" }
};

function t(key) {
    return translations[currentLocale]?.[key] || key;
}

document.addEventListener('DOMContentLoaded', function() {
    document.body.addEventListener('click', function(event) {
        const editButton = event.target.closest('.edit-button');
        const deleteButton = event.target.closest('.delete-button');

        if (editButton) {
            handleEditButton(editButton);
        } else if (deleteButton) {
            handleDeleteButton(deleteButton);
        }
    });
});

function handleEditButton(editButton) {
    const typeId = editButton.getAttribute('data-type-id');
    const nameRu = editButton.getAttribute('data-type-ru');
    const nameEn = editButton.getAttribute('data-type-en');
    const nameKy = editButton.getAttribute('data-type-ky');

    document.getElementById('nameRu').value = nameRu || '';
    document.getElementById('nameEn').value = nameEn || '';
    document.getElementById('nameKy').value = nameKy || '';
    document.getElementById('typeIdForChange').value = typeId || '';

    const selectWrapper = document.getElementById('selectWrapper');
    const staticType = document.getElementById('staticType');
    if (selectWrapper) selectWrapper.style.display = 'none';
    if (staticType) staticType.disabled = true;

    const staticTypeHidden = document.getElementById('staticTypeValue');
    if (staticTypeHidden && staticTypeSortBy) {
        staticTypeHidden.value = staticTypeSortBy.value;
    }
}

function handleDeleteButton(deleteButton) {
    console.log('Delete button clicked:', deleteButton);

    const typeId = deleteButton.getAttribute('data-type-id');
    console.log('Type ID from attribute:', typeId);

    if (!typeId) {
        console.error('Type ID is null or undefined');
        console.log('Button HTML:', deleteButton.outerHTML);
        return;
    }

    const deleteUserModal = document.getElementById('deleteUserModalBody');
    const deleteUserInput = document.getElementById('deleteUserInput');
    const deleteUserBtn = document.getElementById('deleteUserBtn');

    if (deleteUserBtn) {
        deleteUserBtn.setAttribute('data-type-id', typeId);
        if (staticTypeSortBy) {
            deleteUserBtn.setAttribute('data-content-type', staticTypeSortBy.value);
        }
    }

    if (deleteUserInput) {
        deleteUserInput.value = typeId;
    }

    if (deleteUserModal) {
        deleteUserModal.textContent = 'Вы уверены, что хотите удалить объект с ID: ' + typeId;
    }
}

document.getElementById('typeSearchBtn').addEventListener('click', function () {
    const search = document.getElementById('typeSearch').value;
    const type = document.getElementById('staticTypeSortBy').value;
    const lang = document.getElementById('languageTypeSortBy').value;
    doFetch(`/api/${type}?language=${lang}&name=${search}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'));
});

createTypeBtn.onclick = function () {
    const typeId = document.getElementById('typeIdForChange').value;
    const form = document.getElementById('categoryForm');
    const selectedType = document.getElementById('staticTypeValue').value;
    const isEdit = !!typeId;
    const method = isEdit ? 'PUT' : 'POST';

    const errorId = 'staticTypeError';
    const oldError = document.getElementById(errorId);
    if (oldError) {
        oldError.remove();
    }

    if (!selectedType) {
        const selectWrapper = document.getElementById('selectWrapper');
        const errorElem = document.createElement('div');
        errorElem.id = errorId;
        errorElem.style.color = 'red';
        errorElem.style.marginTop = '5px';
        errorElem.textContent = 'Пожалуйста, выберите тип.';

        selectWrapper.appendChild(errorElem);
        return;
    }

    let url = getLinkByName(selectedType);
    if (isEdit) {
        url += `/${typeId}`;
    }

    sendForm(
        form,
        url,
        method,
        'createTypeModal',
        getTranslationsFromForm(form),
        onCreateTypeError,
        () => doFetch(`/api/${staticTypeSortBy.value}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'))
    );
};

function onCreateTypeError(response) {
    let form = document.getElementById('categoryForm');

    response.json().then(errorBody => {
        const errors = errorBody.response;
        console.log(errors);

        form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
        form.querySelectorAll('.invalid-feedback').forEach(el => el.remove());

        const inputs = [
            { id: 'nameRu', message: 'Поле обязательно (Русский)' },
            { id: 'nameEn', message: 'Поле обязательно (English)' },
            { id: 'nameKy', message: 'Поле обязательно (Кыргызча)' }
        ];

        inputs.forEach(inputData => {
            const input = document.getElementById(inputData.id);
            if (input && !input.value.trim()) {
                const div = document.createElement('div');
                div.classList.add('invalid-feedback');
                div.innerText = inputData.message;
                input.classList.add('is-invalid');
                input.after(div);
            }
        });
    });
}

function getTranslationsFromForm(form) {
    const translations = [
        {
            translation: form.querySelector('#nameRu').value.trim(),
            languageCode: 'ru'
        },
        {
            translation: form.querySelector('#nameEn').value.trim(),
            languageCode: 'en'
        },
        {
            translation: form.querySelector('#nameKy').value.trim(),
            languageCode: 'ky'
        }
    ];
    console.log(translations);

    return {translations};
}

function getLinkByName(name) {
    if (name === 'categories') {
        return '/api/categories';
    } else if (name === 'eventTypes') {
        return '/api/eventTypes';
    } else if (name === 'postTypes') {
        return '/api/postTypes';
    } else if (name === 'userTypes') {
        return '/api/userTypes';
    }
}

if (categoryTab) {
    categoryTab.addEventListener('show.bs.tab', function () {
        if (staticTypeSortBy) {
            doFetch(`/api/${staticTypeSortBy.value}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'));
        }
    });
}

function openCreateModal() {
    const categoryForm = document.getElementById('categoryForm');
    const typeIdForChange = document.getElementById('typeIdForChange');
    const selectWrapper = document.getElementById('selectWrapper');
    const staticType = document.getElementById('staticType');
    const staticTypeValue = document.getElementById('staticTypeValue');

    if (categoryForm) categoryForm.reset();
    if (typeIdForChange) typeIdForChange.value = '';
    if (selectWrapper) selectWrapper.style.display = 'block';
    if (staticType) staticType.disabled = false;

    if (staticType && staticTypeValue) {
        staticType.addEventListener('change', () => {
            staticTypeValue.value = staticType.value;
        });
        staticTypeValue.value = staticType.value;
    }
}

function changeModalForTypes() {
    // Эта функция теперь не нужна, так как используется глобальный обработчик
    console.log('Modal prepared for types');
}

if (staticTypeSortBy) {
    staticTypeSortBy.addEventListener('change', function () {
        const value = this.value;
        doFetch(`/api/${value}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'));
    });
}

function addContentInList(content) {
    if (!typeContentList) {
        console.error('typeContentList element not found');
        return;
    }

    typeContentList.innerHTML = `<div class="table-responsive" style="max-height: 400px; overflow-y: auto;">
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Перевод</th>
                    <th>Локаль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody id="resultTableContent">
            </tbody>
        </table>
    </div>`;

    let resultTableContent = document.getElementById('resultTableContent');

    if (!content || !Array.isArray(content)) {
        console.error('Invalid content provided to addContentInList');
        return;
    }

    content.forEach(c => {
        if (!c.id) {
            console.warn('Item without ID found:', c);
            return;
        }


        const ru = c.translations?.find(t => t.languageCode === 'ru')?.translation || '';
        const en = c.translations?.find(t => t.languageCode === 'en')?.translation || '';
        const ky = c.translations?.find(t => t.languageCode === 'ky')?.translation || '';
        const firstLang = c.translations?.[0]?.languageCode || '';
        const firstTranslation = c.translations?.[0]?.translation || '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${c.id}</td>
            <td>${firstTranslation}</td>
            <td>${firstLang}</td>
            <td>
                <div class="dropdown">
                    <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                        ${t("actions")}
                    </button>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item edit-button" 
                               href="#" 
                               data-bs-toggle="modal" 
                               data-bs-target="#createTypeModal"
                               data-type-id="${c.id}" 
                               data-type-ru="${ru}" 
                               data-type-en="${en}" 
                               data-type-ky="${ky}">
                                ${t("edit")}
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item delete-button" 
                               href="#" 
                               data-bs-toggle="modal" 
                               data-bs-target="#deleteUserModal"
                               data-type-id="${c.id}">
                               ${t("delete")}
                            </a>
                        </li>
                    </ul>
                </div>    
            </td>
        `;

        resultTableContent.appendChild(tr);
    });
}
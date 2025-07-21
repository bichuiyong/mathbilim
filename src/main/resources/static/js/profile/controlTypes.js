let categoryTab = document.getElementById('category-tab');
let typeContentList = document.getElementById('typeContentList');
let staticTypeSortBy = document.getElementById('staticTypeSortBy');
let createTypeBtn = document.getElementById('createTypeBtn');



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
            if (!input.value.trim()) {
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
    // const typeId = document.getElementById('typeIdForChange').value;

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
        return '/api/eventTypes'
    } else if (name === 'postTypes') {
        return '/api/postTypes'
    } else if (name === 'userTypes') {
        return '/api/userTypes'
    }
}



categoryTab.addEventListener('show.bs.tab', function () {
    doFetch(`/api/${staticTypeSortBy.value}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'))
});

function openCreateModal() {
    document.getElementById('categoryForm').reset();
    document.getElementById('typeIdForChange').value = ''; // очистить скрытое поле
    document.getElementById('selectWrapper').style.display = 'block';
    document.getElementById('staticType').disabled = false;

    const staticType = document.getElementById('staticType');
    const hiddenInput = document.getElementById('staticTypeValue');
    staticType.addEventListener('change', () => {
        hiddenInput.value = staticType.value;
    });

    hiddenInput.value = staticType.value;
}



function changeModalForTypes() {
    document.getElementById('resultTableContent').addEventListener('click', function (event) {
        const editButton = event.target.closest('.edit-button');
        const deleteButton = event.target.closest('.delete-button');

        if (editButton) {
            const typeId = editButton.dataset.typeId;
            const nameRu = editButton.dataset.typeRu;
            const nameEn = editButton.dataset.typeEn;
            const nameKy = editButton.dataset.typeKy;

            document.getElementById('nameRu').value = nameRu;
            document.getElementById('nameEn').value = nameEn;
            document.getElementById('nameKy').value = nameKy;
            document.getElementById('typeIdForChange').value = typeId;

            const selectWrapper = document.getElementById('selectWrapper');
            const staticType = document.getElementById('staticType');
            if (selectWrapper) selectWrapper.style.display = 'none';
            if (staticType) staticType.disabled = true;

            const staticTypeHidden = document.getElementById('staticTypeValue');
            staticTypeHidden.value = staticTypeSortBy.value;
        } else if (deleteButton) {
            const typeId = deleteButton.dataset.typeId;
            const deleteUserModal = document.getElementById('deleteUserModalBody');
            const deleteUserInput = document.getElementById('deleteUserInput');
            const deleteUserBtn = document.getElementById('deleteUserBtn');

            deleteUserBtn.dataset.typeId = typeId;
            deleteUserBtn.dataset.contentType = staticTypeSortBy.value
            deleteUserInput.value = typeId;
            deleteUserModal.textContent = 'Вы уверены, что хотите удалить объект с ID: ' + typeId;
        }
    });
}




staticTypeSortBy.addEventListener('change', function () {
    const value = this.value;
    doFetch(`/api/${value}`, -1, addContentInList, changeModalForTypes, () => showEmptyMessage('typeContentList'));
})

function addContentInList(content) {
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

    content.forEach(c => {
        const ru = c.translations.find(t => t.languageCode === 'ru')?.translation || '';
        const en = c.translations.find(t => t.languageCode === 'en')?.translation || '';
        const ky = c.translations.find(t => t.languageCode === 'ky')?.translation || '';
        const firstLang = c.translations[0]?.languageCode || '';
        const firstTranslation = c.translations[0]?.translation || '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
  <td>${c.id}</td>
  <td>${firstTranslation}</td>
  <td>${firstLang}</td>
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
             data-bs-target="#createTypeModal"
             data-type-id="${c.id}" 
             data-type-ru="${ru}" 
             data-type-en="${en}" 
             data-type-ky="${ky}">
            ✏️ Изменить
          </a>
        </li>
        <li>
          <a class="dropdown-item delete-button" 
             href="#" 
             data-bs-toggle="modal" 
             data-bs-target="#deleteUserModal"
             data-type-id="${c.id}">
            🗑️ Удалить
          </a>
        </li>
      </ul>
    </div>
  </td>
`;
        resultTableContent.appendChild(tr);
    });
}











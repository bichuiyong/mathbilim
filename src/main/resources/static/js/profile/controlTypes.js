let categoryTab = document.getElementById('category-tab');
let typeContentList = document.getElementById('typeContentList');
let staticTypeSortBy = document.getElementById('staticTypeSortBy');
let createTypeBtn = document.getElementById('createTypeBtn');


createTypeBtn.onclick = function () {
    const form = document.getElementById('categoryForm');
    const selectedType = document.getElementById('staticType')
    selectedType.disabled;
    sendForm(form, getLinkByName(selectedType.value), 'POST', 'createTypeModal', getTranslationsFromForm(form), onCreateTypeError, getLinkByName(selectedType.value))

}

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
            { id: 'nameKg', message: 'Поле обязательно (Кыргызча)' }
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
            translation: form.querySelector('#nameKg').value.trim(),
            languageCode: 'kg'
        }
    ];

    return { translations };
}


function getLinkByName(name) {
    if (name === 'categories') {
        return '/api/categories';
    } else if (name === 'event_type') {
        return '/api/eventTypes'
    } else if (name === 'post_type') {
        return '/api/postTypes'
    } else if (name === 'user_type') {
        return '/api/userTypes'
    }
}



categoryTab.addEventListener('show.bs.tab', function () {
    doFetch(`/api/dict/${staticTypeSortBy.value}`, -1, addContentInList)
});


staticTypeSortBy.addEventListener('change', function () {
    const value = this.value;
    doFetch(`/api/dict/${value}`, -1, addContentInList);
})

function addContentInList(content) {
    typeContentList.innerHTML = "<div class=\"table-responsive\" style=\"max-height: 400px; overflow-y: auto;\">\n" +
        "    <table class=\"table table-hover table-striped\">\n" +
        "        <thead>\n" +
        "            <tr>\n" +
        "                <th>ID</th>\n" +
        "                <th>Перевод</th>\n" +
        "                <th>Локаль</th>\n" +
        "                <th>Действия</th>\n" +
        "            </tr>\n" +
        "        </thead>\n" +
        "        <tbody id=\"resultTableContent\">\n" +
        "        </tbody>\n" +
        "    </table>\n" +
        "</div>";

    let resultTableContent = document.getElementById('resultTableContent')
    content.forEach(c => {
        console.log(`Элемент ${c}`);
        const tr = document.createElement('tr');
        tr.innerHTML = `
  <td>${c.id}</td>
  <td>${c.translations[0].translation}</td>
  <td>${c.translations[0].languageCode}</td>
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
             data-c-id="${c.id}" 
             data-c-ru="${c.translations[0].translation}" 
             data-c-en="${c.translations[1].translation}" 
             data-c-kg="${c.translations[2].translation}">
            ✏️ Изменить
          </a>
        </li>
        <li>
          <a class="dropdown-item delete-button" 
             href="#" 
             data-bs-toggle="modal" 
             data-bs-target="#deleteUserModal"
             data-c-id="${c.id}">
            🗑️ Удалить
          </a>
        </li>
      </ul>
    </div>
  </td>
`;
        resultTableContent.appendChild(tr);
})}










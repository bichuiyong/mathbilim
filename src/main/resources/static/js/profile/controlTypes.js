let categoryTab = document.getElementById('category-tab');
let typeContentList = document.getElementById('typeContentList');
let staticTypeSortBy = document.getElementById('staticTypeSortBy');
categoryTab.addEventListener('show.bs.tab', function () {
    doFetch(`/api/dict/${staticTypeSortBy.value}`, -1, addContentInList)
});

function addContentInList(content) {
    typeContentList.innerHTML = "<div class=\"table-responsive\" style=\"max-height: 400px; overflow-y: auto;\">\n" +
        "    <table class=\"table table-hover table-striped\">\n" +
        "        <thead>\n" +
        "            <tr>\n" +
        "                <th>ID</th>\n" +
        "                <th>Локаль</th>\n" +
        "                <th>Перевод</th>\n" +
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










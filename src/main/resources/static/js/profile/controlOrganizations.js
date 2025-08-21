document.addEventListener('DOMContentLoaded', () => {
    const list = document.getElementById('organizationsContentList');
    if (!list) {
        return;
    }

    const paginationNav = document.getElementById('organizationsPagination');
    const paginationUl = paginationNav.querySelector('ul.pagination');
    const searchBtn = document.getElementById('organizationsSearchBtn');
    const searchInput = document.getElementById('organizationsSearch');

    let currentPage = 0;
    let currentQuery = '';
    const pageSize = 10;

    function renderOrganizations(organizations) {
        if (!organizations || organizations.length === 0) {
            list.innerHTML = `<div class="text-center text-muted py-5">Ничего не найдено</div>`;
            paginationNav.style.display = 'none';
            return;
        }

        list.innerHTML = '';
        organizations.forEach(org => {
            const card = document.createElement('div');
            card.className = 'content-card p-3';
            card.innerHTML = `
                <div class="content-info">
                    <h5 class="content-title mb-0">${org.name}</h5>
                </div>
            `;
            list.appendChild(card);
        });
    }

    function renderPagination(totalPages, page) {
        paginationUl.innerHTML = '';
        if (totalPages <= 1) {
            paginationNav.style.display = 'none';
            return;
        }
        paginationNav.style.display = 'block';
        for (let i = 0; i < totalPages; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === page ? 'active' : ''}`;
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = (i + 1).toString();
            a.addEventListener('click', (e) => {
                e.preventDefault();
                loadPage(i);
            });
            li.appendChild(a);
            paginationUl.appendChild(li);
        }
    }

    function loadPage(page) {
        currentPage = page;
        let url = `/api/organizations/page?page=${page}&size=${pageSize}`;
        if (currentQuery) {
            url += `&name=${encodeURIComponent(currentQuery)}`;
        }
        fetch(url)
            .then(res => res.json())
            .then(data => {
                renderOrganizations(data.content);
                renderPagination(data.totalPages, data.number);
            })
            .catch(err => console.error('Error loading organizations', err));
    }

    searchBtn.addEventListener('click', () => {
        currentQuery = searchInput.value.trim();
        loadPage(0);
    });

    loadPage(0);
});
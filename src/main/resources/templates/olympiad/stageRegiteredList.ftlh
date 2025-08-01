<#import "../layout.ftlh" as main>
<@main.layout>

    <style>
        .heightControl{
            min-height: 450px;
        }

        .table-container {
            margin-top: 10px;
            overflow-x: auto;
            max-width: 90%;
            margin-left: auto;
            margin-right: auto;
        }

        .sortable {
            cursor: pointer;
            position: relative;
            padding-right: 20px;
        }

        .sortable:hover {
            background-color: #f5f5f5;
        }

        .sortable::after {
            content: "↕";
            position: absolute;
            right: 8px;
            opacity: 0.3;
        }

        .sortable.asc::after {
            content: "↑";
            opacity: 1;
        }

        .sortable.desc::after {
            content: "↓";
            opacity: 1;
        }

        .search-container, .pagination-container {
            max-width: 90%;
            margin: 20px auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search-container input {
            width: 300px;
        }

        #noDataMessage {
            display: none;
            text-align: center;
            margin-top: 40px;
            font-size: 18px;
            color: #777;
        }

        #noDataMessage svg {
            width: 80px;
            height: 80px;
            margin-bottom: 15px;
            fill: #999;
        }
    </style>
    <div class="heightControl">
    <div class="search-container">
        <input type="text" id="searchInputTop" class="form-control" placeholder="Поиск...">
        <nav>
            <ul id="paginationTop" class="pagination justify-content-center mb-0"></ul>
        </nav>
    </div>

    <div class="table-container">
        <table class="table table-bordered table-hover" id="dataTable">
            <thead id="tableHead">
            <tr>
                <th class="sortable" data-sort="fullName">ФИО</th>
                <th class="sortable" data-sort="email">Email</th>
                <th class="sortable" data-sort="phoneNumber">Телефон</th>
                <th class="sortable" data-sort="telegram">Telegram</th>
                <th class="sortable" data-sort="school">Школа</th>
                <th class="sortable" data-sort="classNumber">Класс</th>
                <th class="sortable" data-sort="classTeacherFullName">Учитель</th>
                <th class="sortable" data-sort="region">Регион</th>
                <th class="sortable" data-sort="district">Район</th>
                <th class="sortable" data-sort="locality">Город</th>
                <th class="sortable" data-sort="parentFullName">Родитель</th>
                <th class="sortable" data-sort="parentEmail">Email Родителя</th>
                <th class="sortable" data-sort="parentPhoneNumber">Телефон Родителя</th>
                <th class="sortable" data-sort="olympiadName">Олимпиада</th>
                <th class="sortable" data-sort="userName">Пользователь</th>
            </tr>
            </thead>
            <tbody id="registrationBody"></tbody>
        </table>

        <div style="margin-top: 100px" id="noDataMessage">
            <svg width="100" height="100" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="11" cy="11" r="7" stroke="#808080" stroke-width="2" />
                <path d="M16 16L21 21" stroke="#808080" stroke-width="2" stroke-linecap="round" />
            </svg>
            <div>Данные не найдены</div>
        </div>
    </div>

    <div class="pagination-container" id="paginationContainer">
        <nav>
            <ul id="pagination" class="pagination justify-content-center"></ul>
        </nav>
    </div>
    </div>
    <script>
        let stageId = ${stageId!0};
        let currentPage = ${page!0};
        let pageSize = ${size!10};
        let sortBy = "${sortBy!'fullName'}";
        let direction = "${direction!'asc'}";
        <#noparse>
        function updateSortIcons() {
            document.querySelectorAll('.sortable').forEach(header => {
                header.classList.remove('asc', 'desc');
                if (header.dataset.sort === sortBy) {
                    header.classList.add(direction);
                }
            });
        }

        function getMaxVisiblePages() {
            const w = window.innerWidth;
            if (w > 1400) return 20;
            else if (w > 1000) return 15;
            else if (w > 768) return 10;
            else return 5;
        }

        async function loadData(page = 0, keyword = "") {
            try {
                const url = `/api/olymp/stage/list?stageId=${stageId}&page=${page}&keyword=${encodeURIComponent(keyword)}&size=${pageSize}&sortBy=${sortBy}&direction=${direction}`;
                const response = await fetch(url);
                if (!response.ok) throw new Error('Network response was not ok');
                const data = await response.json();

                const tbody = document.getElementById('registrationBody');
                const noDataMessage = document.getElementById('noDataMessage');
                const table = document.getElementById('dataTable');
                const tableHead = document.getElementById('tableHead');
                const paginationContainer = document.getElementById('paginationContainer');

                if (!data.content || data.content.length === 0) {
                    tbody.innerHTML = '';
                    table.style.display = 'none';
                    paginationContainer.style.display = 'none';
                    noDataMessage.style.display = 'block';
                } else {
                    noDataMessage.style.display = 'none';
                    table.style.display = '';
                    paginationContainer.style.display = '';

                    tbody.innerHTML = '';
                    data.content.forEach(reg => {
                        const row = `<tr>
                            <td>${reg.fullName || ''}</td>
                            <td>${reg.email || ''}</td>
                            <td>${reg.phoneNumber || ''}</td>
                            <td>${reg.telegram || ''}</td>
                            <td>${reg.school || ''}</td>
                            <td>${reg.classNumber || ''}</td>
                            <td>${reg.classTeacherFullName || ''}</td>
                            <td>${reg.region || ''}</td>
                            <td>${reg.district || ''}</td>
                            <td>${reg.locality || ''}</td>
                            <td>${reg.parentFullName || ''}</td>
                            <td>${reg.parentEmail || ''}</td>
                            <td>${reg.parentPhoneNumber || ''}</td>
                            <td>${reg.olympiadName || ''}</td>
                            <td>${reg.userName || ''}</td>
                        </tr>`;
                        tbody.insertAdjacentHTML('beforeend', row);
                    });


                    const pagination = document.getElementById('pagination');
                    const paginationTop = document.getElementById('paginationTop');
                    pagination.innerHTML = '';
                    paginationTop.innerHTML = '';

                    const maxVisiblePages = getMaxVisiblePages();
                    const totalPages = data.totalPages;
                    const current = data.number;

                    let startPage = Math.max(0, current - Math.floor(maxVisiblePages / 2));
                    let endPage = startPage + maxVisiblePages - 1;

                    if (endPage >= totalPages) {
                        endPage = totalPages - 1;
                        startPage = Math.max(0, endPage - maxVisiblePages + 1);
                    }

                    for (let i = startPage; i <= endPage; i++) {
                        const active = (i === current) ? 'active' : '';
                        const li = `<li class="page-item ${active}">
                            <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                        </li>`;
                        pagination.innerHTML += li;
                        paginationTop.innerHTML += li;
                    }

                    attachPaginationEvents();
                }
            } catch (error) {
                console.error('Error loading data:', error);
                alert('Произошла ошибка при загрузке данных.');
            }
        }

        function attachPaginationEvents() {
            document.querySelectorAll('#pagination a, #paginationTop a').forEach(link => {
                link.addEventListener('click', e => {
                    e.preventDefault();
                    currentPage = parseInt(e.target.getAttribute('data-page'));
                    loadData(currentPage, document.getElementById('searchInputTop').value);
                });
            });
        }

        document.getElementById('searchInputTop').addEventListener('input', () => {
            currentPage = 0;
            loadData(currentPage, document.getElementById('searchInputTop').value);
        });

        document.querySelectorAll('.sortable').forEach(header => {
            header.addEventListener('click', () => {
                const newSortBy = header.dataset.sort;
                if (sortBy === newSortBy) {
                    direction = (direction === 'asc') ? 'desc' : 'asc';
                } else {
                    sortBy = newSortBy;
                    direction = 'asc';
                }
                currentPage = 0;
                updateSortIcons();
                loadData(currentPage, document.getElementById('searchInputTop').value);
            });
        });

        window.addEventListener('resize', () => {
            loadData(currentPage, document.getElementById('searchInputTop').value);
        });

        window.onload = () => {
            updateSortIcons();
            loadData(currentPage);
        };
    </script>
    </#noparse>
</@main.layout>

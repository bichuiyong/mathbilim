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

        .excel-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-left: 5px;
            width: 70px;
            height: 49px;
            background-color: #38993e;
            color: #ffffff;
            text-decoration: none;
            border-radius: 8px;
            font-family: Arial, sans-serif;
            font-size: 16px;
            font-weight: 500;
        }

        @media (max-width: 825px) {
            .search-container .d-flex {
                flex-direction: column;
                align-items: flex-start;
                width: 100%;
            }

            .search-container input {
                width: 80%;
                margin-bottom: 10px;
            }

            .excel-btn {
                margin-left: 0;
                width: 100%;
                max-width: 100px;
            }
        }
    </style>

    <div class="heightControl">
        <div class="search-container flex">
            <div class="d-flex align-items-center">
                <input type="text" id="searchInputTop" class="form-control" placeholder="<@spring.message "registration.search.placeholder">Поиск...</@spring.message>">
                <a href="скачать в формате excel"
                   onclick="window.location.href='/api/excel/download/registered?stageId=${stageId}'; return false;"
                   id="excel" class="excel-btn">
                    <@spring.message "registration.button.excel">Excel</@spring.message>
                </a>
            </div>
            <nav>
                <ul id="paginationTop" class="pagination justify-content-center mb-0"></ul>
            </nav>
        </div>

        <div class="table-container">
            <table class="table table-bordered table-hover" id="dataTable">
                <thead id="tableHead">
                <tr>
                    <th class="sortable" data-sort="fullName"><@spring.message "registration.table.fullName">ФИО</@spring.message></th>
                    <th class="sortable" data-sort="email"><@spring.message "registration.table.email">Email</@spring.message></th>
                    <th class="sortable" data-sort="phoneNumber"><@spring.message "registration.table.phoneNumber">Телефон</@spring.message></th>
                    <th class="sortable" data-sort="telegram"><@spring.message "registration.table.telegram">Telegram</@spring.message></th>
                    <th class="sortable" data-sort="school"><@spring.message "registration.table.school">Школа</@spring.message></th>
                    <th class="sortable" data-sort="classNumber"><@spring.message "registration.table.classNumber">Класс</@spring.message></th>
                    <th class="sortable" data-sort="classTeacherFullName"><@spring.message "registration.table.classTeacher">Учитель</@spring.message></th>
                    <th class="sortable" data-sort="region"><@spring.message "registration.table.region">Регион</@spring.message></th>
                    <th class="sortable" data-sort="district"><@spring.message "registration.table.district">Район</@spring.message></th>
                    <th class="sortable" data-sort="locality"><@spring.message "registration.table.locality">Город</@spring.message></th>
                    <th class="sortable" data-sort="parentFullName"><@spring.message "registration.table.parentName">Родитель</@spring.message></th>
                    <th class="sortable" data-sort="parentEmail"><@spring.message "registration.table.parentEmail">Email Родителя</@spring.message></th>
                    <th class="sortable" data-sort="parentPhoneNumber"><@spring.message "registration.table.parentPhone">Телефон Родителя</@spring.message></th>
                    <th class="sortable" data-sort="olympiadName"><@spring.message "registration.table.olympiadName">Олимпиада</@spring.message></th>
                    <th class="sortable" data-sort="userName"><@spring.message "registration.table.userName">Пользователь</@spring.message></th>
                </tr>
                </thead>
                <tbody id="registrationBody"></tbody>
            </table>

            <div style="margin-top: 100px" id="noDataMessage">
                <svg width="256px" height="256px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" transform="matrix(1, 0, 0, 1, 0, 0)"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="#CCCCCC" stroke-width="0.048"></g><g id="SVGRepo_iconCarrier"> <path opacity="0.5" d="M22 10.5V12C22 16.714 22 19.0711 20.5355 20.5355C19.0711 22 16.714 22 12 22C7.28595 22 4.92893 22 3.46447 20.5355C2 19.0711 2 16.714 2 12C2 7.28595 2 4.92893 3.46447 3.46447C4.92893 2 7.28595 2 12 2H13.5" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round"></path> <path d="M7 14H16" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round"></path> <path d="M7 17.5H13" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round"></path> <path d="M22 2L17 6.99998M17 1.99998L22 6.99996" stroke="#1C274C" stroke-width="1.5" stroke-linecap="round"></path> </g></svg>
                <div><@spring.message "registration.noData">Данные не найдены</@spring.message></div>
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
                const excelButton = document.getElementById('excel');
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
                    excelButton.classList.remove('btn-success');
                    excelButton.classList.add('btn-secondary', 'disabled');
                    excelButton.setAttribute('tabindex', '-1');
                    excelButton.setAttribute('aria-disabled', 'true');
                    excelButton.style.pointerEvents = 'none';
                } else {
                    noDataMessage.style.display = 'none';
                    table.style.display = '';
                    paginationContainer.style.display = '';
                    excelButton.classList.remove('btn-secondary', 'disabled');
                    excelButton.classList.add('btn-success');
                    excelButton.removeAttribute('tabindex');
                    excelButton.removeAttribute('aria-disabled');
                    excelButton.style.pointerEvents = '';

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

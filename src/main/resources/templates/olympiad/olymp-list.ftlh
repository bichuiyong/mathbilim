<#import "../layout.ftlh" as main>
<@main.layout>
    <h2 style="margin: 30px 0 45px; color: #666666; text-align: center">Страница олимпиад</h2>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <main class="col-12 px-md-4">
                <div style="max-width: 1200px; margin: 0 auto; position: relative;">
                    <div id="olympiad-container" class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-4" style="min-height: 200px;">

                    </div>

                    <div id="no-olympiads" class="text-center" style="display: none; min-height: 300px; align-items: center; justify-content: center; flex-direction: column;">
                        <svg width="120" height="120" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <circle cx="12" cy="12" r="10" stroke="#e9ecef" stroke-width="2" fill="#f8f9fa"/>
                            <path d="M12 8v4l3 3" stroke="#6c757d" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M8 12h.01" stroke="#6c757d" stroke-width="2" stroke-linecap="round"/>
                            <path d="M16 12h.01" stroke="#6c757d" stroke-width="2" stroke-linecap="round"/>
                            <circle cx="12" cy="16" r="1" fill="#6c757d"/>
                        </svg>
                        <div class="mt-3">
                            <h5 class="text-muted mb-2">Олимпиады не найдены</h5>
                            <p class="text-muted small">На этой странице пока нет олимпиад</p>
                        </div>
                    </div>

                    <div id="loading-spinner" class="text-center" style="display: none; position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 10; background: rgba(255, 255, 255, 0.8);">
                        <div class="spinner"></div>
                    </div>
                </div>

                <nav aria-label="Page navigation">
                    <ul id="pagination" class="pagination justify-content-center" style="display: none;">
                    </ul>
                </nav>

                <style>
                    .card {
                        transition: transform 0.2s ease;
                        border-radius: 8px;
                        overflow: hidden;
                        background: #f8f9fa;
                    }

                    .card:hover {
                        transform: translateY(-5px);
                    }

                    .pagination {
                        display: flex;
                        padding-left: 0;
                        list-style: none;
                        gap: 8px;
                        margin: 50px 0 10px;
                    }

                    .page-item.active .page-link {
                        background-color: #0d6efd;
                        color: white;
                        cursor: default;
                    }

                    .page-item.disabled .page-link {
                        color: #6c757d;
                        pointer-events: none;
                        background-color: #fff;
                        border-color: #ddd;
                    }

                    .page-link {
                        position: relative;
                        display: block;
                        padding: 8px 16px;
                        text-decoration: none;
                        color: #333;
                        background-color: #fff;
                        border: 1px solid #ddd;
                        border-radius: 6px;
                        transition: all 0.2s ease;
                    }

                    .page-link:focus {
                        outline: none;
                        box-shadow: none;
                    }

                    .page-link:hover:not(.disabled) {
                        background-color: #f0f0f0;
                    }

                    .spinner {
                        width: 40px;
                        height: 40px;
                        border: 4px solid #ffffff;
                        border-top: 4px solid #007bff;
                        border-radius: 50%;
                        animation: spin 1s linear infinite;
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -50%);
                    }

                    @keyframes spin {
                        0% { transform: translate(-50%, -50%) rotate(0deg); }
                        100% { transform: translate(-50%, -50%) rotate(360deg); }
                    }
                </style>
            </main>
        </div>
    </div>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    const currentPage = ${page!"0"};
    const defaultSize = ${size!"8"};
    <#noparse>
    const actualSize = urlParams.get('size') || defaultSize;
    function loadPage(page) {
        const container = document.getElementById('olympiad-container');
        const emptyBlock = document.getElementById('no-olympiads');
        const pagination = document.getElementById('pagination');
        const loadingSpinner = document.getElementById('loading-spinner');

        loadingSpinner.style.display = 'block';
        emptyBlock.style.display = 'none';
        pagination.style.display = 'none';
        container.innerHTML = '';
        container.style.display = 'flex';

        fetch(`/api/olymp/all?page=${page}&size=${actualSize}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                const olympiads = data.content || [];

                if (!olympiads || olympiads.length === 0) {
                    emptyBlock.style.display = 'flex';
                    container.style.display = 'none';
                } else {
                    emptyBlock.style.display = 'none';
                    container.style.display = 'flex';

                    olympiads.forEach(olympiad => {
                        const block = `
                            <div class="col">
                                <div class="card h-100 shadow-sm border-0">
                                    <img src="/api/files/${olympiad.fileId}/view" class="card-img-top p-3"
                                         alt="olymp-photo" style="height: 200px; object-fit: contain;">
                                    <div class="card-body d-flex flex-column">
                                        <h5 class="card-title">${olympiad.title}</h5>
                                        <div class="mt-auto text-start">
                                            <a href="/olympiad/details?id=${olympiad.id}" class="text-primary text-decoration-none">Подробнее</a>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                        container.insertAdjacentHTML('beforeend', block);
                    });
                }

                if (data.totalPages > 1) {
                    pagination.style.display = 'flex';
                    pagination.innerHTML = '';

                    const maxPagesToShow = 5;
                    let startPage = Math.max(0, page - Math.floor(maxPagesToShow / 2));
                    let endPage = Math.min(data.totalPages - 1, startPage + maxPagesToShow - 1);

                    if (endPage - startPage + 1 < maxPagesToShow) {
                        startPage = Math.max(0, endPage - maxPagesToShow + 1);
                    }

                    const prevItem = document.createElement('li');
                    prevItem.className = `page-item ${page <= 0 ? 'disabled' : ''}`;
                    const prevLink = document.createElement('a');
                    prevLink.className = 'page-link';
                    prevLink.href = '#';
                    prevLink.innerHTML = '«';
                    prevLink.onclick = (e) => {
                        e.preventDefault();
                        if (page > 0) {
                            loadPage(page - 1);
                        }
                    };
                    prevItem.appendChild(prevLink);
                    pagination.appendChild(prevItem);

                    for (let i = startPage; i <= endPage; i++) {
                        const pageItem = document.createElement('li');
                        pageItem.className = `page-item ${i === page ? 'active' : ''}`;
                        const pageLink = document.createElement('a');
                        pageLink.className = 'page-link';
                        pageLink.href = '#';
                        pageLink.innerHTML = i + 1;
                        pageLink.onclick = (e) => {
                            e.preventDefault();
                            if (i !== page) {
                                loadPage(i);
                            }
                        };
                        pageItem.appendChild(pageLink);
                        pagination.appendChild(pageItem);
                    }

                    const nextItem = document.createElement('li');
                    nextItem.className = `page-item ${page >= data.totalPages - 1 ? 'disabled' : ''}`;
                    const nextLink = document.createElement('a');
                    nextLink.className = 'page-link';
                    nextLink.href = '#';
                    nextLink.innerHTML = '»';
                    nextLink.onclick = (e) => {
                        e.preventDefault();
                        if (page < data.totalPages - 1) {
                            loadPage(page + 1);
                        }
                    };
                    nextItem.appendChild(nextLink);
                    pagination.appendChild(nextItem);
                } else {
                    pagination.style.display = 'none';
                }

                updateUrl(page);
            })
            .catch(error => {
                emptyBlock.style.display = 'flex';
                container.style.display = 'none';
                pagination.style.display = 'none';
            })
            .finally(() => {
                loadingSpinner.style.display = 'none';
            });
    }

    function updateUrl(page) {
        const newUrl = new URL(window.location);
        newUrl.searchParams.set('page', page);
        newUrl.searchParams.set('size', actualSize);
        window.history.pushState({}, '', newUrl);
    }

    document.addEventListener('DOMContentLoaded', () => {
        loadPage(currentPage);
    });
</script>
</#noparse>
</@main.layout>
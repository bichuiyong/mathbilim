<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <main class="col-12 px-md-4">
                <h1 class="h2 mb-4">Олимпиады</h1>

                <div style="max-width: 1200px; margin: 0 auto">
                    <div id="olympiad-container" class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-4">
                        <div id="no-olympiads" class="text-center text-muted my-5" style="display: none;">
                            Нет доступных олимпиад.
                        </div>
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

                    .page-item.active .page-link:hover {
                        background-color: #0d6efd;
                        color: white;
                        cursor: default;
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

                    .page-link:hover {
                        background-color: #f0f0f0;
                    }
                </style>
            </main>
        </div>
    </div>

    <script>
        function loadPage(page) {
            fetch(`/api/olymp/all?page=${page}&size=${size}`)
                <#noparse>
                .then(response => response.json())
                .then(data => {
                    const container = document.getElementById('olympiad-container');
                    const emptyBlock = document.getElementById('no-olympiads');
                    const pagination = document.getElementById('pagination');

                    container.innerHTML = '';
                    emptyBlock.style.display = 'none';
                    pagination.innerHTML = '';

                    const olympiads = data.content || [];
                    if (olympiads.length === 0) {
                        emptyBlock.style.display = 'block';
                        return;
                    }

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

                    if (data.totalPages > 1) {
                        pagination.style.display = 'flex';
                        for (let i = 0; i < data.totalPages; i++) {
                            const pageItem = document.createElement('li');
                            pageItem.className = `page-item ${i === page ? 'active' : ''}`;
                            pageItem.innerHTML = `<a class="page-link" href="#" onclick="loadPage(${i}); return false;">${i + 1}</a>`;
                            pagination.appendChild(pageItem);
                        }
                    }
                })
                .catch(error => {
                    console.error('Error loading olympiads:', error);
                    document.getElementById('no-olympiads').style.display = 'block';
                });
        }
        document.addEventListener('DOMContentLoaded', () => loadPage(0));
        </#noparse>
    </script>
</@main.layout>
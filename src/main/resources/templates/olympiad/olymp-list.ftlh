<#import "../layout.ftlh" as main>
<@main.layout>
    <h2 style="margin: 30px 0 45px; color: #666666; text-align: center">Страница олимпиад</h2>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <main class="col-12 px-md-4">
                <div style="max-width: 1200px; margin: 0 auto; position: relative;">
                    <div id="olympiad-container" class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-4" style="min-height: 200px; position: relative;">
                        <div id="no-olympiads" class="text-center" style="display: none; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 100%; height: 100%; align-items: center; justify-content: center; flex-direction: column; z-index: 5; background: rgba(255, 255, 255, 0.9);">
                            <svg width="150" height="150" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M21 7H3V9H21V7ZM21 11H3V13H21V11ZM21 15H3V17H21V15Z" fill="#6c757d"/>
                                <path d="M3 19H21V21H3V19Z" fill="#6c757d"/>
                                <path d="M7 3H17V5H7V3Z" fill="#6c757d"/>
                            </svg>
                            <p class="text-muted mt-2">Нет олимпиад на этой странице</p>
                        </div>
                        <div id="loading-spinner" class="text-center" style="display: none; position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 10;">
                            <div class="spinner"></div>
                        </div>
                    </div>

                    <form action="/notifications/${(isBlogSubscribed!false)?then('unsubscribe', 'subscribe')}" method="post">
                        <@main.csrf/>
                        <input type="hidden" name="type" value="OLYMPIAD">
                        <#if (isAuthenticated!false) && (isBlogSubscribed!false)>
                            <button class="btn btn-outline-danger">🔕 Отписаться от уведомлений</button>
                        <#else>
                            <button class="btn btn-outline-primary">📩 Подписаться на уведомления</button>
                        </#if>
                    </form>

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
        const currentPage = ${page!"0"};
        const size = ${size!"8"};

        function loadPage(page) {
            console.log(`Loading page: ${page}`);
            const container = document.getElementById('olympiad-container');
            const emptyBlock = document.getElementById('no-olympiads');
            const pagination = document.getElementById('pagination');
            const loadingSpinner = document.getElementById('loading-spinner');

            loadingSpinner.style.display = 'block';
            emptyBlock.style.display = 'none';
            pagination.style.display = 'none';

            fetch(`/api/olymp/all?page=${page}&size=${size}`)
            <#noparse>
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('API Response:', data);
                    container.innerHTML = '';
                    pagination.innerHTML = '';

                    const olympiads = data.content || [];
                    if (page >= data.totalPages || page < 0) {
                        emptyBlock.style.display = 'flex';
                        updateUrl(page);
                        return;
                    }

                    if (!olympiads || olympiads.length === 0) {
                        emptyBlock.style.display = 'flex';
                    } else {
                        emptyBlock.style.display = 'none';
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
                        const maxPagesToShow = 5;
                        let startPage = Math.max(0, page - Math.floor(maxPagesToShow / 2));
                        let endPage = Math.min(data.totalPages - 1, startPage + maxPagesToShow - 1);

                        if (endPage - startPage + 1 < maxPagesToShow) {
                            startPage = Math.max(0, endPage - maxPagesToShow + 1);
                        }

                        const prevItem = document.createElement('li');
                        prevItem.className = `page-item ${page === 0 ? 'disabled' : ''}`;
                        prevItem.innerHTML = `<a class="page-link" href="?page=${page - 1}" onclick="loadPage(${page - 1}); return false;">«</a>`;
                        pagination.appendChild(prevItem);

                        for (let i = startPage; i <= endPage; i++) {
                            const pageItem = document.createElement('li');
                            pageItem.className = `page-item ${i === page ? 'active' : ''}`;
                            pageItem.innerHTML = `<a class="page-link" href="?page=${i}" onclick="loadPage(${i}); return false;">${i + 1}</a>`;
                            pagination.appendChild(pageItem);
                        }

                        const nextItem = document.createElement('li');
                        nextItem.className = `page-item ${page === data.totalPages - 1 ? 'disabled' : ''}`;
                        nextItem.innerHTML = `<a class="page-link" href="?page=${page + 1}" onclick="loadPage(${page + 1}); return false;">»</a>`;
                        pagination.appendChild(nextItem);
                    } else {
                        pagination.style.display = 'none';
                    }

                    updateUrl(page);
                })
                .catch(error => {
                    emptyBlock.style.display = 'flex';
                    pagination.style.display = 'none';
                })
                .finally(() => {
                    loadingSpinner.style.display = 'none';
                });
        }

        function updateUrl(page) {
            const newUrl = new URL(window.location);
            newUrl.searchParams.set('page', page);
            window.history.pushState({}, '', newUrl);
        }

        document.addEventListener('DOMContentLoaded', () => {
            loadPage(currentPage);
        });

        // Olympiad subscription handler
        document.addEventListener('DOMContentLoaded', function() {
            const subscriptionForm = document.querySelector('form[action="/notifications/subscribe"]');
            const subscriptionButton = subscriptionForm.querySelector('button');

            subscriptionForm.addEventListener('submit', async function(e) {
                e.preventDefault();

                const formData = new FormData(subscriptionForm);
                const isSubscribed = subscriptionButton.textContent.includes('Подписаться');

                try {
                    if (isSubscribed) {
                        subscriptionForm.action = '/notifications/subscribe';
                        formData.set('type', 'OLYMPIAD');
                    } else {
                        subscriptionForm.action = '/notifications/unsubscribe';
                        formData.set('type', 'OLYMPIAD');
                    }

                    subscriptionButton.disabled = true;
                    subscriptionButton.textContent = '⏳ Обработка...';

                    const response = await fetch(subscriptionForm.action, {
                        method: 'POST',
                        body: formData
                    });

                    if (response.status === 401 || response.status === 403) {
                        window.location.href = '/auth/login';
                        return;
                    }

                    if (response.ok) {
                        if (isSubscribed) {
                            showNotification('✅ Вы успешно подписались на уведомления олимпиад!', 'success');
                            updateButtonToUnsubscribe();
                        } else {
                            showNotification('❌ Вы отписались от уведомлений олимпиад', 'info');
                            updateButtonToSubscribe();
                        }
                    } else {
                        throw new Error('Network response was not ok');
                    }

                } catch (error) {
                    console.error('Error:', error);
                    showNotification('❌ Произошла ошибка. Попробуйте позже.', 'error');
                    if (isSubscribed) {
                        updateButtonToSubscribe();
                    } else {
                        updateButtonToUnsubscribe();
                    }
                }
            });

            function updateButtonToUnsubscribe() {
                subscriptionButton.textContent = '🔕 Отписаться от уведомлений';
                subscriptionButton.className = 'btn btn-outline-secondary';
                subscriptionButton.disabled = false;
                subscriptionForm.action = '/notifications/unsubscribe';
            }

            function updateButtonToSubscribe() {
                subscriptionButton.textContent = '📩 Подписаться на уведомления';
                subscriptionButton.className = 'btn btn-outline-primary';
                subscriptionButton.disabled = false;
                subscriptionForm.action = '/notifications/subscribe';
            }

            function showNotification(message, type = 'info') {
                const existingNotifications = document.querySelectorAll('.notification-toast');
                existingNotifications.forEach(notification => notification.remove());

                const notification = document.createElement('div');
                let alertType = 'info';
                if (type === 'success') {
                    alertType = 'success';
                } else if (type === 'error') {
                    alertType = 'danger';
                }
                notification.className = 'notification-toast alert alert-' + alertType + ' alert-dismissible fade show';
                notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1050;
            min-width: 300px;
            max-width: 500px;
            animation: slideInRight 0.3s ease-out;
        `;

                notification.innerHTML =
                    message +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>';

                if (!document.querySelector('#notification-styles')) {
                    const style = document.createElement('style');
                    style.id = 'notification-styles';
                    style.textContent = `
                @keyframes slideInRight {
                    from {
                        transform: translateX(100%);
                        opacity: 0;
                    }
                    to {
                        transform: translateX(0);
                        opacity: 1;
                    }
                }

                @keyframes slideOutRight {
                    from {
                        transform: translateX(0);
                        opacity: 1;
                    }
                    to {
                        transform: translateX(100%);
                        opacity: 0;
                    }
                }
            `;
                    document.head.appendChild(style);
                }

                document.body.appendChild(notification);

                setTimeout(() => {
                    if (notification.parentNode) {
                        notification.style.animation = 'slideOutRight 0.3s ease-in';
                        setTimeout(() => {
                            if (notification.parentNode) {
                                notification.remove();
                            }
                        }, 300);
                    }
                }, 5000);

                const closeButton = notification.querySelector('.btn-close');
                if (closeButton) {
                    closeButton.addEventListener('click', () => {
                        notification.remove();
                    });
                }
            }
        });
    </script>
    </#noparse>
</@main.layout>
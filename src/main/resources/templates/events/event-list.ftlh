<#import "../layout.ftlh" as main>
<@main.layout>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../static/scss/main.css">

    <section class="hero-section py-5">
        <div class="container text-center">
            <h1 class="display-3 fw-bold mb-3">Математические мероприятия</h1>
        </div>
    </section>

    <div class="container py-4">
        <div class="d-flex flex-column flex-sm-row justify-content-center align-items-center gap-3 mb-4">
            <a href="/events/create" class="btn btn-add-event text-white rounded-pill px-4 py-2">
                <i class="fas fa-plus me-2"></i>Добавить мероприятие
            </a>
        </div>

        <div class="row mb-5">
            <div class="col-12">
                <div class="card event-card featured-event shadow-lg"
                     style="background-image: url('https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1200&q=80');"
                     onclick="window.location.href='/events/event/1'">
                    <div class="badge event-date px-3 py-2">20 июня</div>
                    <div class="badge event-type-badge badge-олимпиада px-3 py-1">Олимпиада</div>
                    <div class="event-content p-4">
                        <h2 class="event-title mb-3">Республиканская олимпиада по математике</h2>
                        <div class="mb-2 opacity-75">
                            <i class="fas fa-user me-2"></i>Министерство образования КР
                        </div>
                        <div class="opacity-75">
                            <i class="fas fa-map-marker-alt me-2"></i>АУЦА, Бишкек
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <h3 class="h4 fw-semibold mb-4 text-dark">Ближайшие мероприятия</h3>

        <div class="row g-4">
            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     style="background-image: url('https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?auto=format&fit=crop&w=800&q=80');"
                     onclick="window.location.href='/events/event/1'">
                    <div class="badge event-date px-2 py-1">25 июня</div>
                    <div class="badge event-type-badge badge-семинар px-2 py-1">Семинар</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Современные методы преподавания математики</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>Ассоциация учителей математики
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-laptop me-1"></i>Онлайн
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     style="background-image: url('https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80');"
                     onclick="window.location.href='/events/event/1'">
                    <div class="badge event-date px-2 py-1">28 июня</div>
                    <div class="badge event-type-badge badge-лекция px-2 py-1">Лекция</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Лекция: Теория вероятностей</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>Проф. Иванов А.И.
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-globe me-1"></i>Zoom
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     onclick="window.location.href='/events/event/1'">
                    <div class="badge event-date px-2 py-1">1 июля</div>
                    <div class="badge event-type-badge badge-олимпиада px-2 py-1">Олимпиада</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Международная студенческая олимпиада</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>ИСОМО
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-map-marker-alt me-1"></i>Казань, Россия
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     onclick="window.location.href='/events/event/1'">
                    <div class="badge event-date px-2 py-1">5 июля</div>
                    <div class="badge event-type-badge badge-семинар px-2 py-1">Семинар</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Математика в программировании</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>CodeMath KG
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-laptop me-1"></i>Гибридный формат
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <form action="/notifications/${(isBlogSubscribed!false)?then('unsubscribe', 'subscribe')}" method="post">
        <@main.csrf/>
        <input type="hidden" name="type" value="EVENT">
        <#if (isAuthenticated!false) && (isBlogSubscribed!false)>
            <button class="btn btn-outline-danger">🔕 Отписаться от уведомлений</button>
        <#else>
            <button class="btn btn-outline-primary">📩 Подписаться на уведомления</button>
        </#if>
    </form>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
    <script>// News subscription handler
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
                        formData.set('type', 'EVENT');
                    } else {
                        subscriptionForm.action = '/notifications/unsubscribe';
                        formData.set('type', 'EVENT');
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
                            showNotification('✅ Вы успешно подписались на уведомления мероприятий!', 'success');
                            updateButtonToUnsubscribe();
                        } else {
                            showNotification('❌ Вы отписались от уведомлений мероприятий', 'info');
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
</@main.layout>

<#import "../layout.ftlh" as main>
<@main.layout>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/event-detail.css">
    <section class="hero-section py-5">
        <div class="container">
            <div class="hero-content">
                <div class="mb-4">
                    <a href="/events" class="btn btn-back">
                        <i class="fas fa-arrow-left me-2"></i>Назад к мероприятиям
                    </a>
                </div>
                <h1 class="display-4 fw-bold mb-3">Республиканская Олимпиада по Математике 2025</h1>
                <p class="lead mb-0">Главное математическое событие года для школьников и студентов Кыргызстана</p>
            </div>
        </div>
    </section>

    <div class="container pb-5">
        <div class="event-detail-card">
            <div class="event-header">
                <div class="event-badge badge-олимпиада">Олимпиада</div>
                <h2 class="h3 fw-bold mb-3">Добро пожаловать на Республиканскую Олимпиаду по Математике 2025!</h2>

                <div class="event-meta">
                    <div class="meta-item">
                        <i class="fas fa-calendar-alt"></i>
                        <div>
                            <strong>15 июня 2025</strong><br>
                            <small>Суббота, 10:00 - 17:00</small>
                        </div>
                    </div>
                    <div class="meta-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <div>
                            <strong>Кыргызский Национальный Университет</strong><br>
                            <small>Бишкек, Кыргызстан</small>
                        </div>
                    </div>
                    <div class="meta-item">
                        <i class="fas fa-users"></i>
                        <div>
                            <strong>Оффлайн</strong><br>
                            <small>До 300 участников</small>
                        </div>
                    </div>
                    <div class="meta-item">
                        <i class="fas fa-user-tie"></i>
                        <div>
                            <strong>Ассоциация Математиков Кыргызстана</strong><br>
                            <small>Организатор</small>
                        </div>
                    </div>
                </div>
            </div>

            <div class="event-content">
                <div class="source-info">
                    <i class="fas fa-info-circle me-2"></i>
                    <strong>Источник:</strong> Министерство образования и науки КР
                </div>

                <div class="event-description">
                    <p>Республиканская Олимпиада по Математике — это ежегодное интеллектуальное соревнование для
                        талантливых школьников и студентов со всей страны. В 2025 году олимпиада пройдёт в Бишкеке на
                        базе КНУ.</p>

                    <h3>Что вас ждет:</h3>
                    <ul>
                        <li><strong>3 конкурсных тура</strong>: алгебра, геометрия, комбинаторика и теория чисел</li>
                        <li><strong>Мастер-классы</strong> от известных математиков Кыргызстана</li>
                        <li><strong>Награждение победителей</strong> дипломами, медалями и ценными призами</li>
                        <li><strong>Сертификаты участия</strong> всем финалистам</li>
                    </ul>

                    <h3>Категории участников:</h3>
                    <ul>
                        <li>7-9 классы</li>
                        <li>10-11 классы</li>
                        <li>Студенты 1-2 курсов</li>
                    </ul>

                    <h3>Почему стоит участвовать:</h3>
                    <ul>
                        <li>Проверка своих знаний среди лучших математиков страны</li>
                        <li>Возможность попасть в сборную Кыргызстана на международные олимпиады</li>
                        <li>Приятные бонусы: книги, грамоты, памятные подарки</li>
                    </ul>

                    <p><strong>Участие бесплатное!</strong> Регистрация обязательна до 10 июня 2025 года.</p>

                    <p>Организаторы обеспечивают участников питанием, а также проездом для финалистов из регионов.</p>
                </div>
            </div>

            <div class="register-section">
                <h3 class="h4 fw-bold mb-3">Готовы проверить свои математические способности?</h3>
                <p class="mb-4">Зарегистрируйтесь сейчас и станьте участником главной олимпиады Кыргызстана!</p>
                <a href="#" class="btn btn-register" target="_blank">
                    <i class="fas fa-user-plus me-2"></i>Зарегистрироваться
                </a>
                <div class="mt-3">
                    <small class="text-muted">
                        <i class="fas fa-clock me-1"></i>
                        Регистрация займет не более 2 минут
                    </small>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
</@main.layout>

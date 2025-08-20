<#import "layout.ftlh" as main>
<@main.layout title="Главная">
    <div class="container mt-4">
        <div class="hero-section mb-5" id="hero-container">
        </div>

        <div class="alert alert-warning mb-5">
            <div class="d-flex align-items-center">
                <div class="announcement-icon me-3">
                    <i class="fas fa-trophy"></i>
                </div>
                <div class="flex-grow-1">
                    <h4 class="mb-1">🏆 Национальная математическая олимпиада 2025</h4>
                    <p class="mb-0">Регистрация открыта до 15 июля 2025. Покажи свои знания на республиканском
                        уровне!</p>
                </div>
                <div class="announcement-action">
                    <a href="/olympiads/national-2025" class="btn btn-primary">Подать заявку</a>
                </div>
            </div>
        </div>

        <!-- Новости - Main Content -->
        <div class="row g-4 mb-5">
            <!-- Featured News Carousel -->
            <div class="col-lg-8">
                <div class="section-header mb-3">
                    <h3>Последние новости</h3>
                </div>
                <div id="newsCarousel" class="carousel slide card" data-bs-ride="carousel">
                    <div class="carousel-indicators" id="news-carousel-indicators">
                        <!-- Индикаторы будут загружены динамически -->
                    </div>
                    <div class="carousel-inner" id="news-carousel-inner">
                        <!-- Слайды будут загружены динамически -->
                        <div class="carousel-item active d-flex justify-content-center align-items-center"
                             style="height: 400px;">
                            <div class="spinner-border text-primary" role="status">
                                <span class="visually-hidden">Загрузка новостей...</span>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#newsCarousel"
                            data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#newsCarousel"
                            data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>

            <!-- Latest News Sidebar -->
            <div class="col-lg-4">
                <div class="news-section">
                    <div class="section-header">
                        <h3>Все новости</h3>
                    </div>
                    <div class="news-container" id="news-container">
                        <div class="d-flex justify-content-center p-3">
                            <div class="spinner-border text-primary" role="status">
                                <span class="visually-hidden">Загрузка...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Публикации -->
        <div class="row g-4 mb-5">
            <!-- Latest Publications Sidebar -->
            <div class="col-lg-4">
                <div class="news-section">
                    <div class="section-header">
                        <h3>Все публикации</h3>
                    </div>
                    <div class="news-container" id="post-container">
                        <div class="d-flex justify-content-center p-3">
                            <div class="spinner-border text-primary" role="status">
                                <span class="visually-hidden">Загрузка...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Featured Publications Carousel -->
            <div class="col-lg-8">
                <div class="section-header mb-3">
                    <h3>Последние публикации</h3>
                </div>
                <div id="publicationsCarousel" class="carousel slide card" data-bs-ride="carousel">
                    <div class="carousel-indicators" id="publications-carousel-indicators">
                        <!-- Индикаторы будут загружены динамически -->
                    </div>
                    <div class="carousel-inner" id="publications-carousel-inner">
                        <!-- Слайды публикаций будут загружены динамически -->
                        <div class="carousel-item active d-flex justify-content-center align-items-center"
                             style="height: 400px;">
                            <div class="spinner-border text-primary" role="status">
                                <span class="visually-hidden">Загрузка публикаций...</span>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#publicationsCarousel"
                            data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#publicationsCarousel"
                            data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
        </div>

        <!-- Upcoming Events -->
        <div class="events-section mb-5">
            <div class="section-header mb-4">
                <h3>Предстоящие соревнования</h3>
            </div>
            <div class="events-scroll-container">
                <div class="events-scroll-wrapper" id="eventsScroll">
                    <div class="event-card-wrapper">
                        <div class="card event-card">
                            <div class="event-image">
                                <img src="https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=400&h=300&fit=crop"
                                     alt="Вебинар по подготовке к ОРТ">
                                <div class="event-date-overlay">
                                    <span class="event-day">25</span>
                                    <span class="event-month">ИЮН</span>
                                </div>
                            </div>
                            <div class="event-content">
                                <h5>Вебинар по подготовке к ОРТ</h5>
                            </div>
                        </div>
                    </div>

                    <div class="event-card-wrapper">
                        <div class="card event-card">
                            <div class="event-image">
                                <img src="https://images.unsplash.com/photo-1477281765962-ef34e8bb0967?w=400&h=300&fit=crop"
                                     alt="Летняя математическая школа">
                                <div class="event-date-overlay">
                                    <span class="event-day">2</span>
                                    <span class="event-month">ИЮЛ</span>
                                </div>
                            </div>
                            <div class="event-content">
                                <h5>Летняя математическая школа</h5>
                            </div>
                        </div>
                    </div>

                    <div class="event-card-wrapper">
                        <div class="card event-card">
                            <div class="event-image">
                                <img src="https://images.unsplash.com/photo-1543269865-cbf427effbad?w=400&h=300&fit=crop"
                                     alt="Городская олимпиада">
                                <div class="event-date-overlay">
                                    <span class="event-day">15</span>
                                    <span class="event-month">ИЮЛ</span>
                                </div>
                            </div>
                            <div class="event-content">
                                <h5>Городская олимпиада</h5>
                            </div>
                        </div>
                    </div>

                    <div class="event-card-wrapper">
                        <div class="card event-card">
                            <div class="event-image">
                                <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=400&h=300&fit=crop"
                                     alt="Мастер-класс по комбинаторике">
                                <div class="event-date-overlay">
                                    <span class="event-day">28</span>
                                    <span class="event-month">ИЮЛ</span>
                                </div>
                            </div>
                            <div class="event-content">
                                <h5>Мастер-класс по комбинаторике</h5>
                            </div>
                        </div>
                    </div>

                    <div class="event-card-wrapper">
                        <div class="card event-card">
                            <div class="event-image">
                                <img src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=300&fit=crop"
                                     alt="Республиканская олимпиада">
                                <div class="event-date-overlay">
                                    <span class="event-day">5</span>
                                    <span class="event-month">АВГ</span>
                                </div>
                            </div>
                            <div class="event-content">
                                <h5>Республиканская олимпиада</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Quick Access Section -->
        <div class="quick-access-section mb-5">
            <div class="section-header mb-4">
                <h3>Быстрый доступ</h3>
            </div>
            <div class="row g-4">
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-calculator"></i>
                            </div>
                            <h4>Тесты</h4>
                            <p class="h-25">Проверьте свои знания в различных разделах математики</p>
                            <a href="/tests" class="btn btn-primary mt-3">Начать тест</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-trophy"></i>
                            </div>
                            <h4>Олимпиады</h4>
                            <p class="h-25">Участвуйте в олимпиадах разного уровня</p>
                            <a href="/olympiads" class="btn btn-primary mt-3">Участвовать</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-book"></i>
                            </div>
                            <h4>Книги</h4>
                            <p class="h-25">Изучайте математику по качественным учебникам</p>
                            <a href="/books" class="btn btn-primary mt-3">Изучать</a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-users"></i>
                            </div>
                            <h4>Сообщество</h4>
                            <p class="h-25">Общайтесь с единомышленниками и преподавателями</p>
                            <a href="/community" class="btn btn-primary mt-3">Присоединиться</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Blog Section -->
        <div class="blog-section mb-5">
            <div class="section-header mb-4">
                <h3>Блог</h3>
            </div>
            <div class="blog-masonry-grid"></div>

            <div class="text-center mt-4">
                <a href="/blog" class="btn btn-outline-primary">Все статьи блога</a>
            </div>
        </div>

    </div>

    <script>
        function createCarouselItem(item, index, carouselId, itemUrlPrefix, defaultImage) {
            const imageUrl = item.mainImageId ? `/api/files/`+item.mainImageId+`/view` : defaultImage;

            let title, description;
            if (item.newsTranslations && item.newsTranslations.length > 0) {
                title = truncateText(item.newsTranslations[0].title || 'Заголовок', 10);
                description = truncateText(item.newsTranslations[0].content, 20);
            } else if (item.postTranslations && item.postTranslations.length > 0) {
                title = truncateText(item.postTranslations[0].title || 'Заголовок', 10);
                description = truncateText(item.postTranslations[0].content, 20);
            } else {
                title = truncateText(item.title || 'Заголовок', 10);
                description = truncateText(
                    item.description || (item.content ? item.content : 'Подробнее...'),
                    20
                );
            }


            const indicator = document.createElement('button');
            indicator.type = 'button';
            indicator.setAttribute('data-bs-target', `#`+carouselId+``);
            indicator.setAttribute('data-bs-slide-to', index.toString());
            indicator.setAttribute('aria-label', `Slide `+index + 1+``);
            if (index === 0) {
                indicator.classList.add('active');
                indicator.setAttribute('aria-current', 'true');
            }

            // Создание слайда
            const carouselItem = document.createElement('div');
            carouselItem.className = index === 0 ? 'carousel-item active' : 'carousel-item';
            carouselItem.innerHTML =
                '<img src="' + imageUrl + '" ' +
                'class="d-block w-100 carousel-image" ' +
                'alt="' + title + '" ' +
                'style="height: 400px; object-fit: cover;">' +
                '<div class="carousel-caption">' +
                '<div class="caption-content">' +
                '<h5>' + title + '</h5>' +
                '<p>' + description + '</p>' +
                '<a href="' + itemUrlPrefix + '/' + item.id + '" class="btn btn-primary btn-sm">Подробнее</a>' +
                '</div>' +
                '</div>';


            return { indicator, carouselItem };
        }

        function truncateText(text, maxLength) {
            if (!text) return '';
            return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
        }


        async function loadNewsCarousel() {
            try {
                const response = await fetch('/api/news/main');
                const news = await response.json();

                const carouselInner = document.getElementById('news-carousel-inner');
                const carouselIndicators = document.getElementById('news-carousel-indicators');

                if (news && news.length > 0) {
                    carouselInner.innerHTML = '';
                    carouselIndicators.innerHTML = '';

                    news.forEach((item, index) => {
                        const { indicator, carouselItem } = createCarouselItem(
                            item,
                            index,
                            'newsCarousel',
                            '/news',
                            'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=800&h=400&fit=crop'
                        );

                        carouselIndicators.appendChild(indicator);
                        carouselInner.appendChild(carouselItem);
                    });
                } else {
                    carouselInner.innerHTML = `
                        <div class="carousel-item active">
                            <img src="https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Математика">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Добро пожаловать!</h5>
                                    <p>Здесь будут отображаться последние новости</p>
                                    <a href="/news" class="btn btn-primary btn-sm">Все новости</a>
                                </div>
                            </div>
                        </div>
                    `;
                }
            } catch (error) {
                console.error('Ошибка загрузки новостей для карусели:', error);
            }
        }

        // Загрузка публикаций для карусели
        async function loadPublicationsCarousel() {
            try {
                const response = await fetch('/api/posts/main');
                const publications = await response.json();

                const carouselInner = document.getElementById('publications-carousel-inner');
                const carouselIndicators = document.getElementById('publications-carousel-indicators');

                if (publications && publications.length > 0) {
                    carouselInner.innerHTML = '';
                    carouselIndicators.innerHTML = '';

                    publications.forEach((item, index) => {
                        const { indicator, carouselItem } = createCarouselItem(
                            item,
                            index,
                            'publicationsCarousel',
                            '/posts',
                            'https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=800&h=400&fit=crop'
                        );

                        carouselIndicators.appendChild(indicator);
                        carouselInner.appendChild(carouselItem);
                    });
                } else {
                    carouselInner.innerHTML = `
                        <div class="carousel-item active">
                            <img src="https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Публикации">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Публикации</h5>
                                    <p>Здесь будут отображаться последние публикации</p>
                                    <a href="/posts" class="btn btn-primary btn-sm">Все публикации</a>
                                </div>
                            </div>
                        </div>
                    `;
                }
            } catch (error) {
                console.error('Ошибка загрузки публикаций для карусели:', error);
            }
        }

        // Универсальная функция для загрузки элементов в боковую панель
        async function loadLatestItems(apiUrl, containerId, itemUrlPrefix, fallbackImage) {
            try {
                const response = await fetch(apiUrl);
                if (!response.ok) throw new Error(`Ошибка HTTP: `+response.status+``);

                const items = await response.json();
                const container = document.getElementById(containerId);

                if (Array.isArray(items) && items.length > 0) {
                    container.innerHTML = items.map(item => {
                        const imgSrc = item.mainImageId
                            ? `/api/files/`+item.mainImageId+`/view`
                            : fallbackImage;

                        const dateStr = item.createdAt
                            ? new Date(item.createdAt).toLocaleDateString('ru-RU', {
                                year: 'numeric',
                                month: 'long',
                                day: 'numeric'
                            })
                            : '';

                        return '' +
                            '<div class="news-item mb-3">' +
                            '<div class="row g-2">' +
                            '<div class="col-4">' +
                            '<img src="' + imgSrc + '" ' +
                            'alt="' + item.title + '" ' +
                            'class="img-fluid rounded" ' +
                            'style="height: 60px; object-fit: cover; width: 100%;">' +
                            '</div>' +
                            '<div class="col-8">' +
                            '<h6 class="mb-1">' +
                            '<a href="' + itemUrlPrefix + '/' + item.id + '" class="text-decoration-none">' +
                            item.title +
                            '</a>' +
                            '</h6>' +
                            '<small class="text-muted">' + dateStr + '</small>' +
                            '</div>' +
                            '</div>' +
                            '</div>';

                    }).join('');
                } else {
                    container.innerHTML = '<p class="text-muted">Нет данных для отображения</p>';
                }
            } catch (error) {
                console.error(`Ошибка загрузки данных с `+apiUrl+`:`, error);
                document.getElementById(containerId).innerHTML = '<p class="text-muted">Ошибка загрузки данных</p>';
            }
        }

        // Инициализация при загрузке страницы
        document.addEventListener('DOMContentLoaded', function () {
            // Загрузка каруселей
            loadNewsCarousel();
            loadPublicationsCarousel();

            // Загрузка боковых панелей
            loadLatestItems(
                '/api/news/main',
                'news-container',
                '/news',
                'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=150&h=100&fit=crop'
            );

            loadLatestItems(
                '/api/posts/main',
                'publications-container',
                '/posts',
                'https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=150&h=100&fit=crop'
            );
        });

    </script>

    <script src="/static/js/main.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</@main.layout>
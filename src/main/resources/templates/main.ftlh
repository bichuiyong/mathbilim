<#import "layout.ftlh" as main>
<@main.layout title="Главная">
    <div class="container mt-4">
        <!-- Hero Section -->
        <div class="hero-section mb-5">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="hero-title">Развиваем математическое мышление в <span class="text-primary">Кыргызстане</span></h1>
                    <p class="hero-description">Платформа для подготовки к олимпиадам, тестам и развития математических навыков. Присоединяйтесь к сообществу математиков!</p>
                    <div class="hero-buttons">
                        <a href="/tests" class="btn btn-primary me-3">Начать тестирование</a>
                        <a href="/olympiads" class="btn btn-outline-primary">Олимпиады</a>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="hero-image">
                        <img src="https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=600&h=400&fit=crop"
                             alt="Математика" class="img-fluid border-radius">
                    </div>
                </div>
            </div>
        </div>

        <!-- Announcement Banner -->
        <div class="alert alert-warning mb-5">
            <div class="d-flex align-items-center">
                <div class="announcement-icon me-3">
                    <i class="fas fa-trophy"></i>
                </div>
                <div class="flex-grow-1">
                    <h4 class="mb-1">🏆 Национальная математическая олимпиада 2025</h4>
                    <p class="mb-0">Регистрация открыта до 15 июля 2025. Покажи свои знания на республиканском уровне!</p>
                </div>
                <div class="announcement-action">
                    <a href="/olympiads/national-2025" class="btn btn-primary">Подать заявку</a>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div class="row g-4 mb-5">
            <!-- Featured Content Carousel -->
            <div class="col-lg-8">
                <div id="featuredCarousel" class="carousel slide card" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="0"
                                class="active" aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="1"
                                aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="2"
                                aria-label="Slide 3"></button>
                    </div>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Алгебра">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Основы алгебры</h5>
                                    <p>Полный курс алгебры для школьников и абитуриентов</p>
                                    <a href="/books/algebra-basics" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1509228468518-180dd4864904?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Геометрия">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Геометрия в задачах</h5>
                                    <p>Решение олимпиадных задач по геометрии</p>
                                    <a href="/books/geometry-problems" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1611224923853-80b023f02d71?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Анализ">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Математический анализ</h5>
                                    <p>Введение в анализ для студентов</p>
                                    <a href="/books/calculus-intro" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#featuredCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#featuredCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>

            <!-- Latest News Sidebar with Scroll -->
            <div class="col-lg-4">
                <div class="news-section">
                    <div class="section-header">
                        <h3>Последние новости</h3>
                    </div>
                    <div class="news-container">
                        <div class="news-item">
                            <div class="news-date">12 июня 2025 • 11:30 <span class="news-stats">👁 234</span></div>
                            <div class="news-content">Кыргызские школьники завоевали золото на Балканской математической олимпиаде</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">10 июня 2025 • 14:15 <span class="news-stats">👁 189</span></div>
                            <div class="news-content">Открыта регистрация на летнюю математическую школу в Иссык-Куле</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">8 июня 2025 • 09:30 <span class="news-stats">👁 156</span></div>
                            <div class="news-content">Новый сборник задач по комбинаторике доступен для скачивания</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">5 июня 2025 • 16:45 <span class="news-stats">👁 142</span></div>
                            <div class="news-content">Интервью с победителем Международной математической олимпиады</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">3 июня 2025 • 12:20 <span class="news-stats">👁 98</span></div>
                            <div class="news-content">Результаты республиканской олимпиады по математике для 9-11 классов</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">1 июня 2025 • 15:10 <span class="news-stats">👁 167</span></div>
                            <div class="news-content">Стартовала подготовка к международной олимпиаде по информатике</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">30 мая 2025 • 11:55 <span class="news-stats">👁 123</span></div>
                            <div class="news-content">Математический кружок для младших школьников открывает новые группы</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">28 мая 2025 • 17:30 <span class="news-stats">👁 201</span></div>
                            <div class="news-content">Кыргызстанские студенты показали отличные результаты на международном конкурсе по алгебре</div>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="/news" class="btn btn-outline-primary">Все новости</a>
                    </div>
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

        <div class="row g-4 mb-5">

            <!-- Latest News Sidebar with Scroll -->
            <div class="col-lg-4">
                <div class="news-section">
                    <div class="section-header">
                        <h3>Последние публикации</h3>
                    </div>
                    <div class="news-container">
                        <div class="news-item">
                            <div class="news-date">12 июня 2025 • 11:30 <span class="news-stats">👁 234</span></div>
                            <div class="news-content">Кыргызские школьники завоевали золото на Балканской математической олимпиаде</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">10 июня 2025 • 14:15 <span class="news-stats">👁 189</span></div>
                            <div class="news-content">Открыта регистрация на летнюю математическую школу в Иссык-Куле</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">8 июня 2025 • 09:30 <span class="news-stats">👁 156</span></div>
                            <div class="news-content">Новый сборник задач по комбинаторике доступен для скачивания</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">5 июня 2025 • 16:45 <span class="news-stats">👁 142</span></div>
                            <div class="news-content">Интервью с победителем Международной математической олимпиады</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">3 июня 2025 • 12:20 <span class="news-stats">👁 98</span></div>
                            <div class="news-content">Результаты республиканской олимпиады по математике для 9-11 классов</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">1 июня 2025 • 15:10 <span class="news-stats">👁 167</span></div>
                            <div class="news-content">Стартовала подготовка к международной олимпиаде по информатике</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">30 мая 2025 • 11:55 <span class="news-stats">👁 123</span></div>
                            <div class="news-content">Математический кружок для младших школьников открывает новые группы</div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">28 мая 2025 • 17:30 <span class="news-stats">👁 201</span></div>
                            <div class="news-content">Кыргызстанские студенты показали отличные результаты на международном конкурсе по алгебре</div>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="/news" class="btn btn-outline-primary">Все публикации</a>
                    </div>
                </div>
            </div>
            <!-- Featured Content Carousel -->
            <div class="col-lg-8">
                <div id="featuredCarousel" class="carousel slide card" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="0"
                                class="active" aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="1"
                                aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#featuredCarousel" data-bs-slide-to="2"
                                aria-label="Slide 3"></button>
                    </div>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Алгебра">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Основы алгебры</h5>
                                    <p>Полный курс алгебры для школьников и абитуриентов</p>
                                    <a href="/books/algebra-basics" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1509228468518-180dd4864904?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Геометрия">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Геометрия в задачах</h5>
                                    <p>Решение олимпиадных задач по геометрии</p>
                                    <a href="/books/geometry-problems" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1611224923853-80b023f02d71?w=800&h=400&fit=crop"
                                 class="d-block w-100 carousel-image" alt="Анализ">
                            <div class="carousel-caption">
                                <div class="caption-content">
                                    <h5>Математический анализ</h5>
                                    <p>Введение в анализ для студентов</p>
                                    <a href="/books/calculus-intro" class="btn btn-primary btn-sm">Изучить</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#featuredCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#featuredCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
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
            <div class="blog-masonry-grid">
                <!-- Large item -->
                <a href="/blog/mathematical-thinking" class="blog-item blog-item-large">
                    <img src="https://images.unsplash.com/photo-1509228468518-180dd4864904?w=800&h=600&fit=crop"
                         alt="Математическое мышление">
                    <div class="blog-overlay">
                        <span class="blog-category">Образование</span>
                        <h4 class="blog-title">Развитие математического мышления у школьников</h4>
                        <p class="blog-excerpt">Методики и подходы к формированию аналитического мышления через решение нестандартных задач</p>
                        <span class="blog-date">10 июня 2025</span>
                    </div>
                </a>

                <a href="/blog/olympiad-preparation" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1543269865-cbf427effbad?w=600&h=400&fit=crop"
                         alt="Подготовка к олимпиадам">
                    <div class="blog-overlay">
                        <span class="blog-category">Олимпиады</span>
                        <h4 class="blog-title">Как готовиться к математическим олимпиадам</h4>
                        <p class="blog-excerpt">Пошаговый план подготовки от региональных до международных соревнований</p>
                        <span class="blog-date">8 июня 2025</span>
                    </div>
                </a>

                <!-- Small items -->
                <a href="/blog/math-in-life" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1596495578065-6e0763fa1178?w=400&h=300&fit=crop"
                         alt="Математика в жизни">
                    <div class="blog-overlay">
                        <span class="blog-category">Применение</span>
                        <h4 class="blog-title">Математика в повседневной жизни</h4>
                        <p class="blog-excerpt">Примеры использования математических знаний</p>
                        <span class="blog-date">3 июня 2025</span>
                    </div>
                </a>

                <a href="/blog/future-of-education" class="blog-item blog-item-tall">
                    <img src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=400&h=600&fit=crop"
                         alt="Будущее образования">
                    <div class="blog-overlay">
                        <span class="blog-category">Инновации</span>
                        <h4 class="blog-title">Будущее математического образования</h4>
                        <p class="blog-excerpt">Как технологии меняют подход к изучению математики в XXI веке</p>
                        <span class="blog-date">28 мая 2025</span>
                    </div>
                </a>

                <a href="/blog/geometry-beauty" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1635070041078-e363dbe005cb?w=600&h=400&fit=crop"
                         alt="Красота геометрии">
                    <div class="blog-overlay">
                        <span class="blog-category">Геометрия</span>
                        <h4 class="blog-title">Красота геометрических построений</h4>
                        <p class="blog-excerpt">Исследование эстетики математических форм и их применение в искусстве</p>
                        <span class="blog-date">5 июня 2025</span>
                    </div>
                </a>

                <a href="/blog/famous-mathematicians" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1611224923853-80b023f02d71?w=400&h=300&fit=crop"
                         alt="Великие математики">
                    <div class="blog-overlay">
                        <span class="blog-category">История</span>
                        <h4 class="blog-title">Великие математики Кыргызстана</h4>
                        <p class="blog-excerpt">Вклад кыргызских ученых в мировую науку</p>
                        <span class="blog-date">1 июня 2025</span>
                    </div>
                </a>

                <a href="/blog/problem-solving" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=400&h=300&fit=crop"
                         alt="Решение задач">
                    <div class="blog-overlay">
                        <span class="blog-category">Методика</span>
                        <h4 class="blog-title">Искусство решения задач</h4>
                        <p class="blog-excerpt">Стратегии подхода к сложным задачам</p>
                        <span class="blog-date">30 мая 2025</span>
                    </div>
                </a>

                <a href="/blog/problem-solving" class="blog-item blog-item-small">
                    <img src="https://images.unsplash.com/photo-1434030216411-0b793f4b4173?w=400&h=300&fit=crop"
                         alt="Решение задач">
                    <div class="blog-overlay">
                        <span class="blog-category">Методика</span>
                        <h4 class="blog-title">Искусство решения задач</h4>
                        <p class="blog-excerpt">Стратегии подхода к сложным задачам</p>
                        <span class="blog-date">30 мая 2025</span>
                    </div>
                </a>
            </div>

            <div class="text-center mt-4">
                <a href="/blog" class="btn btn-outline-primary">Все статьи блога</a>
            </div>
        </div>

    </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</@main.layout>
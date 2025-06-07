<#import "layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="../static/css/main-style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
    <div class="container">
        <div class="header">Последние мероприятия</div>

        <div class="event-card">
            <div class="event-date">29 мая 2025 09:00</div>
            <h2>🎯 КИТ ФОРУМ 2025 принимает заявки от спикеров!</h2>
            <div class="event-details">
                <div class="event-detail">
                    <div class="event-detail-label">Организатор</div>
                    <div>Парк Высоких Технологий КР</div>
                </div>
                <div class="event-detail">
                    <div class="event-detail-label">Локация</div>
                    <div>МУК, Льва Толстого 17А/1, КР</div>
                </div>
            </div>
        </div>

        <div class="main-content">
            <div class="row g-4">
                <div class="col-lg-8">
                    <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-indicators">
                            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0"
                                    class="active"
                                    aria-current="true" aria-label="Slide 1"></button>
                            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                                    aria-label="Slide 2"></button>
                            <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                                    aria-label="Slide 3"></button>
                        </div>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="https://images.unsplash.com/photo-1531297484001-80022131f5a1?w=800&h=500&fit=crop"
                                     class="d-block w-100" alt="Технологии">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="caption-inner">
                                        <h5>First slide label</h5>
                                        <p>Some representative placeholder content for the first slide.</p>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=800&h=500&fit=crop"
                                     class="d-block w-100" alt="Образование">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="caption-inner">
                                        <h5>First slide label</h5>
                                        <p>Some representative placeholder content for the first slide.</p>
                                    </div>
                                </div>
                            </div>
                            <div class="carousel-item">
                                <img src="https://images.unsplash.com/photo-1524178232363-1fb2b075b655?w=800&h=500&fit=crop"
                                     class="d-block w-100" alt="Наука">
                                <div class="carousel-caption d-none d-md-block">
                                    <div class="caption-inner">
                                        <h5>First slide label</h5>
                                        <p>Some representative placeholder content for the first slide.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                                data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                                data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="news-section">
                        <h3>Последние новости</h3>
                        <div class="news-container">
                            <div class="news-item">
                                <div class="news-date">20 мая 2025 • 11:30 <span class="news-stats">👁 65</span></div>
                                <div class="news-content">ЖАЛПЫ БИЛИМ БЕРҮҮ УЮМДАРЫНЫҢ ЖЕТЕКЧИЛЕРИНИН БОШ КЫЗМАТ
                                    ОРУНДАРЫНА
                                    ӨТКӨРҮЛГӨН КОНКУРСТА ЖЕҢҮҮЧҮЛӨРҮ АНЫКТАЛДЫ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">19 мая 2025 • 17:40 <span class="news-stats">👁 171</span></div>
                                <div class="news-content">НАЧАЛСЯ ПРИЁМ ДОКУМЕНТОВ В ПЕКИНСКИЙ СПОРТИВНЫЙ УНИВЕРСИТЕТ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">19 мая 2025 • 17:18 <span class="news-stats">👁 149</span></div>
                                <div class="news-content">ПРЕДСТАВИТЕЛИ КР И КЫРГЫЗСТАНА И МИД ВЕЛИКОБРИТАНИИ ОБСУДИЛИ
                                    БУДУЩИЕ
                                    ОБРАЗОВАТЕЛЬНЫЕ ИНИЦИАТИВЫ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">15 мая 2025 • 14:45 <span class="news-stats">👁 153</span></div>
                                <div class="news-content">ШКОЛЬНИКИ ИЗ КЫРГЫЗСТАНА ЗАНЯЛИ ПЕРВОЕ КОМАНДНОЕ МЕСТО НА
                                    МЕЖДУНАРОДНОЙ
                                    ОЛИМПИАДЕ ПО БИОЛОГИИ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">14 мая 2025 • 12:30 <span class="news-stats">👁 89</span></div>
                                <div class="news-content">НОВАЯ ПРОГРАММА СТИПЕНДИЙ ДЛЯ СТУДЕНТОВ IT-СПЕЦИАЛЬНОСТЕЙ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">13 мая 2025 • 16:15 <span class="news-stats">👁 203</span></div>
                                <div class="news-content">МЕЖДУНАРОДНАЯ КОНФЕРЕНЦИЯ ПО ЦИФРОВОМУ ОБРАЗОВАНИЮ ПРОЙДЕТ В
                                    БИШКЕКЕ
                                </div>
                            </div>
                            <div class="news-item">
                                <div class="news-date">12 мая 2025 • 10:45 <span class="news-stats">👁 127</span></div>
                                <div class="news-content">ОТКРЫТИЕ НОВОГО ТЕХНОПАРКА В ОШЕ</div>
                            </div>
                        </div>
                        <div class="text-center">
                                <a href="/media/news" class="btn btn-outline-secondary">Все новости</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="my-5">
            <h3 class="mb-4">Предстоящие соревнования</h3>
            <div class="carousel-wrapper position-relative">
                <button class="carousel-nav-btn carousel-btn-prev"
                        type="button"
                        data-bs-target="#competitionsCarousel"
                        data-bs-slide="prev">
                    ‹
                </button>

                <div id="competitionsCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">

                    <div class="carousel-item active">
                        <div class="row g-3">
                            <div class="col-12 col-md-4">
                                <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                    <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                    <h5 class="mt-2">TSI Programming Contest</h5>
                                    <div class="card-footer mt-auto">
                                        <small class="competition-link">Подробнее →</small>
                                    </div>
                                </div>
                            </div>

                                <div class="col-12 col-md-4 d-none d-md-block">
                                    <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                        <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                        <h5 class="mt-2">IT Кубок Кыргызстана 2025</h5>
                                        <div class="card-footer mt-auto">
                                            <small class="competition-link">Подробнее →</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12 col-md-4 d-none d-md-block">
                                    <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                        <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                        <h5 class="mt-2">Республиканская Олимпиада</h5>
                                        <div class="card-footer mt-auto">
                                            <small class="competition-link">Подробнее →</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    <div class="carousel-item">
                        <div class="row g-3">
                            <div class="col-12 col-md-4">
                                <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                    <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                    <h5 class="mt-2">ACM ICPC Regional</h5>
                                    <div class="card-footer mt-auto">
                                        <small class="competition-link">Подробнее →</small>
                                    </div>
                                </div>
                            </div>

                                <div class="col-12 col-md-4 d-none d-md-block">
                                    <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                        <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                        <h5 class="mt-2">ML Challenge</h5>
                                        <div class="card-footer mt-auto">
                                            <small class="competition-link">Подробнее →</small>
                                        </div>
                                    </div>
                                </div>

                            <div class="col-12 col-md-4 d-none d-md-block">
                                <div class="competition-card d-flex flex-column h-100 border p-2 rounded">
                                    <img src="../static/images/photo_logo1.webp" class="card-img-top" alt="">
                                    <h5 class="mt-2">Web Dev Championship</h5>
                                    <div class="card-footer mt-auto">
                                        <small class="competition-link">Подробнее →</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <button class="carousel-nav-btn carousel-btn-next"
                    type="button"
                    data-bs-target="#competitionsCarousel"
                    data-bs-slide="next">
                ›
            </button>
        </div>
    </div>

    <div class="main-content">
        <div class="row g-4">
            <div class="col-lg-8">
                <div id="carouselExampleCaptionsPublication" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#carouselExampleCaptionsPublication"
                                data-bs-slide-to="0"
                                class="active"
                                aria-current="true" aria-label="Slide 1"></button>
                        <button type="button" data-bs-target="#carouselExampleCaptionsPublication"
                                data-bs-slide-to="1"
                                aria-label="Slide 2"></button>
                        <button type="button" data-bs-target="#carouselExampleCaptionsPublication"
                                data-bs-slide-to="2"
                                aria-label="Slide 3"></button>
                    </div>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="https://images.unsplash.com/photo-1531297484001-80022131f5a1?w=800&h=500&fit=crop"
                                 class="d-block w-100" alt="Технологии">
                            <div class="carousel-caption d-none d-md-block">
                                <div class="caption-inner">
                                    <h5>Цифровое образование</h5>
                                    <p>Развитие IT-образования в Кыргызстане</p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?w=800&h=500&fit=crop"
                                 class="d-block w-100" alt="Образование">
                            <div class="carousel-caption d-none d-md-block">
                                <div class="caption-inner">
                                    <h5>Образовательные программы</h5>
                                    <p>Новые возможности для студентов</p>
                                </div>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="https://images.unsplash.com/photo-1524178232363-1fb2b075b655?w=800&h=500&fit=crop"
                                 class="d-block w-100" alt="Наука">
                            <div class="carousel-caption d-none d-md-block">
                                <div class="caption-inner">
                                    <h5>Научные достижения</h5>
                                    <p>Международные олимпиады и конкурсы</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button"
                            data-bs-target="#carouselExampleCaptionsPublication" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button"
                            data-bs-target="#carouselExampleCaptionsPublication" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>

            <div class="col-lg-4">
                <div class="news-section">
                    <h3>Последние Публикации</h3>
                    <div class="news-container">
                        <div class="news-item">
                            <div class="news-date">20 мая 2025 • 11:30 <span class="news-stats">👁 65</span></div>
                            <div class="news-content">ЖАЛПЫ БИЛИМ БЕРҮҮ УЮМДАРЫНЫҢ ЖЕТЕКЧИЛЕРИНИН БОШ КЫЗМАТ
                                ОРУНДАРЫНА
                                ӨТКӨРҮЛГӨН КОНКУРСТА ЖЕҢҮҮЧҮЛӨРҮ АНЫКТАЛДЫ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">19 мая 2025 • 17:40 <span class="news-stats">👁 171</span></div>
                            <div class="news-content">НАЧАЛСЯ ПРИЁМ ДОКУМЕНТОВ В ПЕКИНСКИЙ СПОРТИВНЫЙ УНИВЕРСИТЕТ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">19 мая 2025 • 17:18 <span class="news-stats">👁 149</span></div>
                            <div class="news-content">ПРЕДСТАВИТЕЛИ КР И КЫРГЫЗСТАНА И МИД ВЕЛИКОБРИТАНИИ ОБСУДИЛИ
                                БУДУЩИЕ
                                ОБРАЗОВАТЕЛЬНЫЕ ИНИЦИАТИВЫ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">15 мая 2025 • 14:45 <span class="news-stats">👁 153</span></div>
                            <div class="news-content">ШКОЛЬНИКИ ИЗ КЫРГЫЗСТАНА ЗАНЯЛИ ПЕРВОЕ КОМАНДНОЕ МЕСТО НА
                                МЕЖДУНАРОДНОЙ
                                ОЛИМПИАДЕ ПО БИОЛОГИИ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">14 мая 2025 • 12:30 <span class="news-stats">👁 89</span></div>
                            <div class="news-content">НОВАЯ ПРОГРАММА СТИПЕНДИЙ ДЛЯ СТУДЕНТОВ IT-СПЕЦИАЛЬНОСТЕЙ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">13 мая 2025 • 16:15 <span class="news-stats">👁 203</span></div>
                            <div class="news-content">МЕЖДУНАРОДНАЯ КОНФЕРЕНЦИЯ ПО ЦИФРОВОМУ ОБРАЗОВАНИЮ ПРОЙДЕТ В
                                БИШКЕКЕ
                            </div>
                        </div>
                        <div class="news-item">
                            <div class="news-date">12 мая 2025 • 10:45 <span class="news-stats">👁 127</span></div>
                            <div class="news-content">ОТКРЫТИЕ НОВОГО ТЕХНОПАРКА В ОШЕ</div>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="/media/publication" class="btn btn-outline-secondary">Все публикации</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="blog-section">
        <div class="section-title mb-4 mt-5">
            <h2>БЛОГ</h2>
        </div>
        <div class="row g-4">
            <div class="col-lg-6">
                <div class="blog-main">
                    <div class="blog-image"></div>
                    <div class="blog-date">20.05.2025</div>
                    <div class="blog-title">Как Нурсултан из Оша абсолютно бесплатно поступил в университет, где
                        учились
                        Илон Маск и Дональд Трамп
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="blog-sidebar">
                    <div class="blog-sidebar-item">
                        <div class="blog-date">20.05.2025</div>
                        <div class="blog-title">От схем к системам: как Сардор помогает развивать крупнейшую
                            цифровую
                            экосистему Узбекистана
                        </div>
                    </div>
                    <div class="blog-sidebar-item">
                        <div class="blog-date">16.05.2025</div>
                        <div class="blog-title">Британские бординговые школы — старт успешного будущего для вашего
                            ребенка
                        </div>
                    </div>
                    <div class="blog-sidebar-item">
                        <div class="blog-date">15.05.2025</div>
                        <div class="blog-title">Стажировки в Meta, TikTok и работа в небольшой американской компании
                            с
                            выручкой $5,8 млрд: как Нурсултан нашел свое место в IT-индустри
                        </div>
                    </div>
                    <div class="blog-sidebar-item">
                        <div class="blog-date">15.05.2025</div>
                        <div class="blog-title">Школьники из Кыргызстана завоевали бронзу на Международной
                            Менделеевской
                            олимпиаде по химии
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</@main.layout>
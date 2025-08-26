<#import "layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout title="Главная">
    <div class="container mt-4">
        <div class="hero-section mb-5">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="hero-title"><@spring.message "main.message1"/> <span class="text-primary"><@spring.message "main.kyrgyz"/></span></h1>
                    <p class="hero-description"><@spring.message "main.message7"/></p>
                    <div class="hero-buttons">
                        <a href="/tests" class="btn btn-primary me-3"><@spring.message "main.readyTest"/></a>
                        <a href="/olympiad" class="btn btn-outline-primary"><@spring.message "main.olympiads"/></a>
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
        <span id="currentLocale" style="display: none;">${.locale}</span>

        <div class="alert alert-warning mb-5">
        </div>

            <div class="row g-4 mb-5">
                <div class="col-lg-8">
                    <div class="section-header mb-3">
                        <h3><@spring.message "main.lastNews"/></h3>
                    </div>
                    <div id="newsCarousel" class="carousel slide card" data-bs-ride="carousel">
                        <div class="carousel-indicators" id="news-carousel-indicators">
                        </div>
                        <div class="carousel-inner" id="news-carousel-inner">
                            <div class="carousel-item active d-flex justify-content-center align-items-center"
                                 style="height: 400px;">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden"><@spring.message "main.loadNews"/>...</span>
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

                <div class="col-lg-4">
                    <div class="news-section">
                        <div class="section-header">
                            <h3><@spring.message "main.allNews"/></h3>
                        </div>
                        <div class="news-container" id="news-container">
                            <div class="d-flex justify-content-center p-3">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden"><@spring.message "main.loading"/>...</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row g-4 mb-5">
                <div class="col-lg-4">
                    <div class="news-section">
                        <div class="section-header">
                            <h3><@spring.message "main.allPublications"/></h3>
                        </div>
                        <div class="news-container" id="post-container">
                            <div class="d-flex justify-content-center p-3">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden"><@spring.message "main.loading"/>...</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-8">
                    <div class="section-header mb-3">
                        <h3><@spring.message "main.lastPosts"/></h3>
                    </div>
                    <div id="publicationsCarousel" class="carousel slide card" data-bs-ride="carousel">
                        <div class="carousel-indicators" id="publications-carousel-indicators">
                        </div>
                        <div class="carousel-inner" id="publications-carousel-inner">
                            <div class="carousel-item active d-flex justify-content-center align-items-center"
                                 style="height: 400px;">
                                <div class="spinner-border text-primary" role="status">
                                    <span class="visually-hidden"><@spring.message "main.loadPosts"/></span>
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

        <div class="events-section mb-5">
            <div class="section-header mb-4">
                <h3><@spring.message "main.wannaOLYMMP"/></h3>
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

        <div class="quick-access-section mb-5">
            <div class="section-header mb-4">
                <h3><@spring.message "main.fast"/></h3>
            </div>
            <div class="row g-4">
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-calculator"></i>
                            </div>
                            <h4><@spring.message "main.TeSt"/></h4>
                            <p class="h-25"><@spring.message"main.message2"/></p>
                            <a href="/tests" class="btn btn-primary mt-3"><@spring.message"main.readyTest"/></a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-trophy"></i>
                            </div>
                            <h4><@spring.message "main.olympiads"/></h4>
                            <p class="h-25"><@spring.message "main.message3"/></p>
                            <a href="/olympiad" class="btn btn-primary mt-3"><@spring.message "main.regis"/></a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-book"></i>
                            </div>
                            <h4><@spring.message "main.book"/></h4>
                            <p class="h-25"><@spring.message "main.message4"/></p>
                            <a href="/books" class="btn btn-primary mt-3"><@spring.message "main.learn"/></a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="card text-center h-100">
                        <div class="card-body">
                            <div class="card-icon mb-3">
                                <i class="fas fa-calendar-alt"></i>
                            </div>
                            <h4><@spring.message "main.events"/></h4>
                            <p class="h-25"><@spring.message "main.message5"/></p>
                            <a href="/events" class="btn btn-primary mt-3"><@spring.message "main.learnMore"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="blog-section mb-5">
            <div class="section-header mb-4">
                <h3><@spring.message "main.BLogs"/></h3>
            </div>
            <div class="blog-masonry-grid"></div>

            <div class="text-center mt-4">
                <a href="/blog" class="btn btn-outline-primary"><@spring.message "main.allBlogs"/></a>
            </div>
        </div>

    </div>

    <script>
        const localeSpan = document.getElementById('currentLocale');
        const currentLocale = localeSpan ? localeSpan.textContent : 'ru';
        console.log(currentLocale)

        const i18n = {
            locale: currentLocale,
            carousel: {
                defaultTitle: "<@spring.message 'news.carousel.default.title'/>",
                defaultDescription: "<@spring.message 'news.carousel.default.description'/>",
                slideLabel: "<@spring.message 'news.carousel.slide.label'/>",
                detailsButton: "<@spring.message 'news.carousel.details.button'/>",
                loadError: "<@spring.message 'news.carousel.load.error'/>",
                noData: "<@spring.message 'news.carousel.no.data'/>",
                loadDataError: "<@spring.message 'news.carousel.load.data.error'/>",
                newsLoadError: "<@spring.message 'news.carousel.news.load.error'/>",
                publicationsLoadError: "<@spring.message 'news.carousel.publications.load.error'/>",
                httpError: "<@spring.message 'news.carousel.http.error'/>",
                dataLoadError: "<@spring.message 'news.carousel.data.load.error'/>"
            },
            publications: {
                loadError: "<@spring.message 'publications.carousel.load.error'/>"
            }
        };
        function createCarouselItem(item, index, carouselId, itemUrlPrefix, defaultImage) {
            const imageUrl = item.mainImageId ? `/api/files/`+item.mainImageId+`/view` : defaultImage;

            let title, description;
            if (item.newsTranslations && item.newsTranslations.length > 0) {
                title = truncateText(item.newsTranslations[0].title || i18n.carousel.defaultTitle, 10);
                description = truncateText(item.newsTranslations[0].content, 20);
            } else if (item.postTranslations && item.postTranslations.length > 0) {
                title = truncateText(item.postTranslations[0].title || i18n.carousel.defaultTitle, 10);
                description = truncateText(item.postTranslations[0].content, 20);
            } else {
                title = truncateText(item.title || i18n.carousel.defaultTitle, 10);
                description = truncateText(
                    item.description || (item.content ? item.content :  i18n.carousel.defaultDescription),
                    20
                );
            }


            const indicator = document.createElement('button');
            indicator.type = 'button';
            indicator.setAttribute('data-bs-target', `#`+carouselId+``);
            indicator.setAttribute('data-bs-slide-to', index.toString());
            indicator.setAttribute('aria-label', i18n.carousel.slideLabel + ` `+(index + 1)+``);
            if (index === 0) {
                indicator.classList.add('active');
                indicator.setAttribute('aria-current', 'true');
            }

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
                '<a href="' + itemUrlPrefix + '/' + item.id + '" class="btn btn-primary btn-sm">' + i18n.carousel.detailsButton + '</a>' +
                '</div>' +
                '</div>';


            return { indicator, carouselItem };
        }

        function truncateText(text, maxLength) {
            if (!text) return '';
            return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
        }



        function toggleSectionVisibility(sectionSelector, isVisible) {
            const section = document.querySelector(sectionSelector);
            if (section) {
                section.style.display = isVisible ? 'block' : 'none';
            }
        }

        async function loadNewsCarousel() {
            try {
                const response = await fetch('/api/news/main');
                const news = await response.json();

                const carouselInner = document.getElementById('news-carousel-inner');
                const carouselIndicators = document.getElementById('news-carousel-indicators');

                if (news && news.length > 0) {
                    // Показываем секцию новостей
                    toggleSectionVisibility('.col-lg-8:has(#newsCarousel)', true);

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
                    // Скрываем секцию новостей если нет контента
                    toggleSectionVisibility('.col-lg-8:has(#newsCarousel)', false);
                }
            } catch (error) {
                console.error(i18n.carousel.newsLoadError + ':', error);
                toggleSectionVisibility('.col-lg-8:has(#newsCarousel)', false);
            }
        }

        async function loadPublicationsCarousel() {
            try {
                const response = await fetch('/api/posts/main');
                const publications = await response.json();

                const carouselInner = document.getElementById('publications-carousel-inner');
                const carouselIndicators = document.getElementById('publications-carousel-indicators');

                if (publications && publications.length > 0) {
                    // Показываем секцию публикаций
                    toggleSectionVisibility('.col-lg-8:has(#publicationsCarousel)', true);

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
                    // Скрываем секцию публикаций если нет контента
                    toggleSectionVisibility('.col-lg-8:has(#publicationsCarousel)', false);
                }
            } catch (error) {
                console.error(i18n.carousel.newsLoadError + ':', error);
                // Скрываем секцию при ошибке
                toggleSectionVisibility('.col-lg-8:has(#publicationsCarousel)', false);
            }
        }

        // Альтернативный вариант если :has() не поддерживается в старых браузерах
        function loadNewsCarouselAlternative() {
            fetch('/api/news/main')
                .then(response => response.json())
                .then(news => {
                    const newsSection = document.querySelector('#newsCarousel').closest('.col-lg-8');
                    const carouselInner = document.getElementById('news-carousel-inner');
                    const carouselIndicators = document.getElementById('news-carousel-indicators');

                    if (news && news.length > 0) {
                        // Показываем секцию
                        newsSection.style.display = 'block';

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
                        // Скрываем секцию
                        newsSection.style.display = 'none';
                    }
                })
                .catch(error => {
                    console.error('Ошибка загрузки новостей для карусели:', error);
                    const newsSection = document.querySelector('#newsCarousel').closest('.col-lg-8');
                    newsSection.style.display = 'none';
                });
        }

        function loadPublicationsCarouselAlternative() {
            fetch('/api/posts/main')
                .then(response => response.json())
                .then(publications => {
                    const publicationsSection = document.querySelector('#publicationsCarousel').closest('.col-lg-8');
                    const carouselInner = document.getElementById('publications-carousel-inner');
                    const carouselIndicators = document.getElementById('publications-carousel-indicators');

                    if (publications && publications.length > 0) {
                        publicationsSection.style.display = 'block';

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
                        publicationsSection.style.display = 'none';
                    }
                })
                .catch(error => {
                    console.error(i18n.carousel.publicationsLoadError + ':', error);
                    const publicationsSection = document.querySelector('#publicationsCarousel').closest('.col-lg-8');
                    publicationsSection.style.display = 'none';
                });
        }

        async function loadLatestItems(apiUrl, containerId, itemUrlPrefix, fallbackImage) {
            try {
                const response = await fetch(apiUrl);
                if (!response.ok) throw new Error(i18n.carousel.httpError + `: `+response.status+``);

                const items = await response.json();
                const container = document.getElementById(containerId);

                if (Array.isArray(items) && items.length > 0) {
                    container.innerHTML = items.map(item => {
                        const imgSrc = item.mainImageId
                            ? `/api/files/`+item.mainImageId+`/view`
                            : fallbackImage;

                        const dateStr = item.createdAt
                            ? new Date(item.createdAt).toLocaleDateString(currentLocale, {
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
                            'alt="' + (item.title || i18n.carousel.defaultTitle) + '" ' +
                            'class="img-fluid rounded" ' +
                            'style="height: 60px; object-fit: cover; width: 100%;">' +
                            '</div>' +
                            '<div class="col-8">' +
                            '<h6 class="mb-1">' +
                            '<a href="' + itemUrlPrefix + '/' + item.id + '" class="text-decoration-none">'
                            +
                            (item.title || i18n.carousel.defaultTitle) +
                            '</a>' +
                            '</h6>' +
                            '<small class="text-muted">' + dateStr + '</small>' +
                            '</div>' +
                            '</div>' +
                            '</div>';

                    }).join('');
                }
                else {
                    container.innerHTML = '<p class="text-muted">' + i18n.carousel.noData + '</p>';
                }
            } catch (error) {
                console.error(i18n.carousel.dataLoadError + ` `+apiUrl+`:`, error);
                document.getElementById(containerId).innerHTML = '<p class="text-muted">' + i18n.carousel.loadDataError + '</p>';
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            loadNewsCarousel();
            loadPublicationsCarousel();

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
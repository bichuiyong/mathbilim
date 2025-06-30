<#macro layout title="">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} - MathBilim.kg</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit"
                async defer>
        </script>
        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token!}"/>
            <meta name="_csrf_header" content="${_csrf.headerName!}"/>
        </#if>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="/static/css/main.css">
    </head>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        user = SPRING_SECURITY_CONTEXT.authentication.principal
        >
    </#if>

    <body>
    <nav class="navbar navbar-expand-lg navbar-light navbar-container">
        <div class="container-fluid">
            <!-- Мобильная версия header -->
            <div class="d-flex justify-content-between align-items-center w-100 d-lg-none">
                <a href="/" class="logo">mathbilim</a>
                <div class="d-flex align-items-center">
                    <div class="language-switcher-mobile me-3">
                        <a href="?lang=ky" class="lang-btn-mobile">КЫР</a>
                        <a href="?lang=ru" class="lang-btn-mobile active">РУС</a>
                        <a href="?lang=en" class="lang-btn-mobile">EN</a>
                    </div>

                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
            </div>

            <!-- Десктопная версия -->
            <div class="d-none d-lg-block w-100">
                <div class="navbar-top">
                    <div class="row align-items-center">
                        <div class="col-lg-3 d-flex align-items-center">
                            <a href="/" class="logo">mathbilim</a>
                        </div>

                        <div class="col-lg-6">
                            <div class="d-flex justify-content-center">
                                <a href="/news" class="nav-link">Новости</a>
                                <a href="/events" class="nav-link">Мероприятия</a>
                                <a href="/blog" class="nav-link">Блог</a>
                                <a href="/posts" class="nav-link">Публикации</a>
                            </div>
                        </div>

                        <div class="col-lg-3">
                            <div class="d-flex justify-content-end auth-buttons">
                                <#if user??>
                                    <a href="/users/${user.authId}"  class="btn-icon">
                                        <i class="fas fa-user "></i>
                                    </a>
                                    <#if _csrf??>
                                        <form action="/logout" method="post">
                                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                                   value="${(_csrf.token)!'--no-token--'}"/>
                                            <button type="submit" class="btn-icon">
                                                <i class="fas fa-sign-out-alt"></i>
                                            </button>
                                        </form>
                                    <#else>
                                        <a href="/logout" class="btn-icon">
                                            <i class="fas fa-sign-out-alt"></i>
                                        </a>
                                    </#if>
                                <#else>
                                    <a href="/auth/login" class="btn-icon" title="Войти">
                                        <i class="fas fa-sign-in-alt"></i>
                                    </a>
                                    <a href="/auth/register" class="btn-icon" title="Регистрация">
                                        <i class="fas fa-user-plus"></i>
                                    </a>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="navbar-bottom">
                    <div class="row align-items-center">
                        <div class="col-lg-3"></div>

                        <div class="col-lg-6">
                            <div class="d-flex justify-content-center">
                                <a href="/tests" class="nav-link">Тесты</a>
                                <a href="/olympiads" class="nav-link">Олимпиады</a>
                                <a href="/books" class="nav-link">Книги</a>
                            </div>
                        </div>

                        <div class="col-lg-3">
                            <div class="d-flex justify-content-end language-switcher">
                                <a href="?lang=ky" class="lang-btn">КЫР</a>
                                <a href="?lang=ru" class="lang-btn active">РУС</a>
                                <a href="?lang=en" class="lang-btn">EN</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Мобильное меню (collapse) -->
            <div class="collapse navbar-collapse d-lg-none" id="navbarContent">
                <div class="navbar-mobile-content">
                    <div class="mobile-menu-section">
                        <h6 class="mobile-menu-title">Основные разделы</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/tests">Тесты</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/olympiads">Олимпиады</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/books">Книги</a>
                            </li>
                        </ul>
                    </div>

                    <div class="mobile-menu-section">
                        <h6 class="mobile-menu-title">Информация</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/news">Новости</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/events">Мероприятия</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/blog">Блог</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/publications">Публикации</a>
                            </li>
                        </ul>
                    </div>

                    <div class="mobile-menu-section">
                        <div class="d-flex gap-2">
                            <#if user??>
                                <a href="/users/${user.authId}" class="btn btn-outline-primary w-50">
                                    <i class="fas fa-user  me-2"></i>Профиль
                                </a>
                                <#if _csrf??>
                                    <form action="/logout" method="post">
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                               value="${(_csrf.token)!'--no-token--'}"/>
                                        <button type="submit" class="btn btn-outline-primary w-50">
                                            <i class="fas fa-sign-out-alt me-2"></i>Выйти
                                        </button>
                                    </form>
                                <#else>
                                    <a href="/logout" class="btn btn-outline-primary w-50">
                                        <i class="fas fa-sign-out-alt me-2"></i>Выйти
                                    </a>
                                </#if>
                            <#else>
                                <a href="/auth/login" class="btn btn-outline-primary w-50">
                                    <i class="fas fa-sign-in-alt me-2"></i>Войти
                                </a>
                                <a href="/auth/register" class="btn btn-primary w-50">
                                    <i class="fas fa-user-plus me-2"></i>Регистрация
                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <!-- Основной контент -->
    <main>
        <#nested>
    </main>

    <!-- Футер -->
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4">
                    <h5>О проекте MathBilim.kg</h5>
                    <p class="mb-3">Платформа для развития математического образования в Кыргызстане. Объединяем ресурсы
                        для подготовки к олимпиадам, тестам и общего математического развития.</p>
                    <p class="mb-0"><strong>Математическое Образовательное Сообщество «Mathaniyat»</strong></p>
                </div>

                <div class="col-lg-2 col-md-6 mb-4">
                    <h5>Разделы</h5>
                    <ul class="list-unstyled">
                        <li class="mb-2"><a href="/tests" class="footer-link">Тесты</a></li>
                        <li class="mb-2"><a href="/olympiads" class="footer-link">Олимпиады</a></li>
                        <li class="mb-2"><a href="/books" class="footer-link">Книги</a></li>
                        <li class="mb-2"><a href="/publications" class="footer-link">Публикации</a></li>
                        <li class="mb-2"><a href="/blog" class="footer-link">Блог</a></li>
                        <li class="mb-2"><a href="/events" class="footer-link">Мероприятия</a></li>
                        <li class="mb-2"><a href="/news" class="footer-link">Новости</a></li>
                    </ul>
                </div>

                <div class="col-lg-3 col-md-6 mb-4">
                    <h5>Информация</h5>
                    <ul class="list-unstyled">
                        <li class="mb-2"><a href="/about" class="footer-link">О нас</a></li>
                        <li class="mb-2"><a href="/privacy" class="footer-link">Политика конфиденциальности</a></li>
                        <li class="mb-2"><a href="/terms" class="footer-link">Условия использования</a></li>
                        <li class="mb-2"><a href="/help" class="footer-link">Помощь</a></li>
                    </ul>
                </div>

                <div class="col-lg-3 col-md-6 mb-4">
                    <h5>Контакты</h5>
                    <p class="mb-2">
                        <i class="fas fa-envelope me-2"></i>
                        <a href="mailto:info@mathbilim.kg" class="footer-link">info@mathbilim.kg</a>
                    </p>
                    <p class="mb-2">
                        <i class="fas fa-phone me-2"></i>
                        <a href="tel:+996555123456" class="footer-link">+996 555 123 456</a>
                    </p>
                    <p class="mb-3">
                        <i class="fas fa-map-marker-alt me-2"></i>
                        <span class="footer-link">Бишкек, Кыргызстан</span>
                    </p>

                    <div class="social-links">
                        <a href="#" class="social-link" title="Facebook">
                            <i class="fab fa-facebook-f"></i>
                        </a>
                        <a href="#" class="social-link" title="Instagram">
                            <i class="fab fa-instagram"></i>
                        </a>
                        <a href="#" class="social-link" title="Telegram">
                            <i class="fab fa-telegram-plane"></i>
                        </a>
                        <a href="#" class="social-link" title="YouTube">
                            <i class="fab fa-youtube"></i>
                        </a>
                    </div>
                </div>
            </div>

            <div class="footer-bottom">
                <p class="mb-0">&copy; 2025 MathBilim.kg. Все права защищены. Математическое Образовательное Сообщество
                    «Mathaniyat»</p>
            </div>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const langBtns = document.querySelectorAll('.lang-btn, .lang-btn-mobile');
            const currentLang = new URLSearchParams(window.location.search).get('lang') || 'ru';

            langBtns.forEach(btn => {
                btn.classList.remove('active');
                if (btn.href.includes('lang=' + currentLang)) {
                    btn.classList.add('active');
                }
            });
        });

        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth'
                    });
                }
            });
        });

        document.querySelectorAll('.navbar-collapse .nav-link').forEach(link => {
            link.addEventListener('click', () => {
                const navbarCollapse = document.querySelector('.navbar-collapse');
                if (navbarCollapse.classList.contains('show')) {
                    const bsCollapse = new bootstrap.Collapse(navbarCollapse, {
                        toggle: false
                    });
                    bsCollapse.hide();
                }
            });
        });
    </script>
    </body>
    </html>
</#macro>

<#macro csrf>
    <#if _csrf??>
        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
               value="${(_csrf.token)!'csrf-token'}"/>
    </#if>
</#macro>
<#import "spring.ftl" as spring>
<#macro layout>

    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Навигация edu.gov.kg</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
              crossorigin="anonymous">
        <link rel="stylesheet" href="../static/css/layout.css">
    </head>
    <body class="bg-light">

    <nav class="navbar navbar-expand-lg navbar-custom sticky-top">
        <div class="container-fluid" style="max-width: 1400px;">
            <div class="d-flex align-items-center me-3">
                <a href="/"
                   class="logo-custom d-flex align-items-center justify-content-center text-white fw-bold fs-5 text-decoration-none me-3">🎓</a>
                <div class="text-dark fw-semibold lh-sm d-none d-md-block" style="font-size: 12px; max-width: 200px;">
                    МИНИСТЕРСТВО ОБРАЗОВАНИЯ<br>И НАУКИ КЫРГЫЗСКОЙ РЕСПУБЛИКИ
                </div>
            </div>

            <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom active px-3 py-2" href="#">Олимпиады</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">SAT</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Книги</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Публикации</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Блог</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Мероприятия</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Новости</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">О проекте</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-custom px-3 py-2" href="#">Контакты</a>
                    </li>

                    <li class="nav-item">
                        <a class="btn btn-primary btn-auth px-4 py-2" href="#">Войти</a>
                    </li>

                </ul>
            </div>

        </div>
    </nav>

    <div class="container-fluid" style="max-width: 1400px;">
        <#nested spring>
    </div>

    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="logo-section">
                    <a href="#" class="logo">
                        <div class="logo-icon">N</div>
                        Notion
                    </a>
                    <div class="social-links">
                        <a href="#" class="social-link">📷</a>
                        <a href="#" class="social-link">✕</a>
                        <a href="#" class="social-link">💼</a>
                        <a href="#" class="social-link">📘</a>
                        <a href="#" class="social-link">▶</a>
                    </div>
                </div>

                <div class="footer-column">
                    <h6>Company</h6>
                    <ul>
                        <li><a href="#">About us</a></li>
                        <li><a href="#">Careers</a></li>
                        <li><a href="#">Security</a></li>
                        <li><a href="#">Status</a></li>
                        <li><a href="#">Terms & privacy</a></li>
                    </ul>
                </div>

                <div class="footer-column">
                    <h6>Download</h6>
                    <ul>
                        <li><a href="#">iOS & Android</a></li>
                        <li><a href="#">Mac & Windows</a></li>
                        <li><a href="#">Calendar</a></li>
                        <li><a href="#">Web Clipper</a></li>
                    </ul>
                </div>

                <div class="footer-column">
                    <h6>Resources</h6>
                    <ul>
                        <li><a href="#">Help center</a></li>
                        <li><a href="#">Pricing</a></li>
                        <li><a href="#">Blog</a></li>
                        <li><a href="#">Community</a></li>
                        <li><a href="#">Integrations</a></li>
                        <li><a href="#">Templates</a></li>
                        <li><a href="#">Affiliates</a></li>
                    </ul>
                </div>

                <div class="footer-column">
                    <h6>Notion for</h6>
                    <ul>
                        <li><a href="#">Enterprise</a></li>
                        <li><a href="#">Small business</a></li>
                        <li><a href="#">Personal</a></li>
                    </ul>
                </div>

                <div class="explore-more">
                    Explore more →
                </div>
            </div>

            <div class="footer-bottom">
                <div class="footer-bottom-left">
                    <button class="language-selector">
                        🌐 English ▼
                    </button>
                    <span class="copyright">© 2025 Notion Labs, Inc.</span>
                </div>
                <div class="footer-links">
                    <a href="#" class="footer-link">Do Not Sell or Share My Info</a>
                    <a href="#" class="footer-link">Cookie settings</a>
                </div>
            </div>
        </div>
    </footer>


    <script src="../static/js/nav-bar.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

    </body>
    </html>

</#macro>
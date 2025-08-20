<#macro layout title="">
    <#import "/spring.ftl" as spring>
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
        <script src="https://unpkg.com/react@18/umd/react.development.js"></script>
        <script src="https://unpkg.com/react-dom@18/umd/react-dom.development.js"></script>
        <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>

        <style>
            .navbar {
                transition: transform 0.2s ease-in-out;
                position: sticky;
                top: 0;
                z-index: 1000;
            }
        </style>
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

            <div class="d-none d-lg-block w-100">
                <div class="navbar-top">
                    <div class="row align-items-center">
                        <div class="col-lg-3 d-flex align-items-center">
                            <a href="/" class="logo">mathbilim</a>
                        </div>

                        <div class="col-lg-6">
                            <div class="d-flex justify-content-center">
                                <a href="/news" class="nav-link"><@spring.message "navbar.news"/></a>
                                <a href="/events" class="nav-link"><@spring.message "navbar.events"/></a>
                                <a href="/blog" class="nav-link"><@spring.message "navbar.blog"/></a>
                                <a href="/posts" class="nav-link"><@spring.message "navbar.publications"/></a>
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
                                <a href="/tests" class="nav-link"><@spring.message "navbar.tests"/></a>
                                <a href="/olympiad" class="nav-link"><@spring.message "navbar.olympiads"/></a>
                                <a href="/books" class="nav-link"><@spring.message "navbar.books"/></a>
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

            <div class="collapse navbar-collapse d-lg-none" id="navbarContent">
                <div class="navbar-mobile-content">
                    <div class="mobile-menu-section">
                        <h6 class="mobile-menu-title">Основные разделы</h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/tests"><@spring.message "navbar.tests"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/olympiad"><@spring.message "navbar.olympiads"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/books"><@spring.message "navbar.books"/></a>
                            </li>
                        </ul>
                    </div>

                    <div class="mobile-menu-section">
                        <h6 class="mobile-menu-title"><@spring.message "footer.information"/></h6>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/news"><@spring.message "navbar.news"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/events"><@spring.message "navbar.events"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/blog"><@spring.message "navbar.blog"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/publications"><@spring.message "navbar.publications"/></a>
                            </li>
                        </ul>
                    </div>

                    <div class="mobile-menu-section">
                        <div class="d-flex gap-2">
                            <#if user??>
                                <a href="/users/${user.authId}" class="btn btn-outline-primary w-50">
                                    <i class="fas fa-user  me-2"></i><@spring.message "navbar.profile"/>
                                </a>
                                <#if _csrf??>
                                    <form action="/logout" method="post">
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                               value="${(_csrf.token)!'--no-token--'}"/>
                                        <button type="submit" class="btn btn-outline-primary w-50">
                                            <i class="fas fa-sign-out-alt me-2"></i><@spring.message "navbar.logout"/>

                                        </button>
                                    </form>
                                <#else>
                                    <a href="/logout" class="btn btn-outline-primary w-50">
                                        <i class="fas fa-sign-out-alt me-2"></i><@spring.message "navbar.logout"/>

                                    </a>
                                </#if>
                            <#else>
                                <a href="/auth/login" class="btn btn-outline-primary w-50">
                                    <i class="fas fa-sign-in-alt me-2"></i><@spring.message "navbar.login"/>
                                </a>
                                <a href="/auth/register" class="btn btn-primary w-50">
                                    <i class="fas fa-user-plus me-2"></i><@spring.message "navbar.register"/>

                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <main>
        <#nested>
    </main>

    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-6 mb-4">
                    <h5><@spring.message "footer.about"/></h5>
                    <p class="mb-3"><@spring.message "footer.description"/></p>
                    <p class="mb-0"><strong><@spring.message "footer.community"/></strong></p>
                </div>

                <div class="col-lg-2 col-md-6 mb-4">
                    <h5><@spring.message "footer.sections"/></h5>
                    <ul class="list-unstyled">
                        <li class="mb-2"><a href="/tests" class="footer-link"><@spring.message "navbar.tests"/></a></li>
                        <li class="mb-2"><a href="/olympiads" class="footer-link"><@spring.message "navbar.olympiads"/></a></li>
                        <li class="mb-2"><a href="/books" class="footer-link"><@spring.message "navbar.books"/></a></li>
                        <li class="mb-2"><a href="/publications" class="footer-link"><@spring.message "navbar.publications"/></a></li>
                        <li class="mb-2"><a href="/blog" class="footer-link"><@spring.message "navbar.blog"/></a></li>
                        <li class="mb-2"><a href="/events" class="footer-link"><@spring.message "navbar.events"/></a></li>
                        <li class="mb-2"><a href="/news" class="footer-link"><@spring.message "navbar.news"/></a></li>
                    </ul>
                </div>

                <div class="col-lg-3 col-md-6 mb-4">
                    <h5><@spring.message "footer.information"/></h5>
                    <ul class="list-unstyled">
                        <li class="mb-2"><a href="/about" class="footer-link"><@spring.message "footer.about"/></a></li>
                        <li class="mb-2"><a href="/privacy" class="footer-link"><@spring.message "footer.privacy"/></a></li>
                        <li class="mb-2"><a href="/terms" class="footer-link"><@spring.message "footer.terms"/></a></li>
                        <li class="mb-2"><a href="/help" class="footer-link"><@spring.message "footer.help"/></a></li>
                    </ul>
                </div>

                <div class="col-lg-3 col-md-6 mb-4">
                    <h5><@spring.message "footer.contacts"/></h5>
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
                <p class="mb-0"><@spring.message "footer.copyright"/></p>
            </div>
        </div>
    </footer>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const navbar = document.querySelector(".navbar");
            if (!navbar) return;

            if (window.location.pathname.includes("/tests/") && window.location.pathname.includes("/pass")) {
                window.addEventListener("scroll", () => {
                    if (window.scrollY === 0) {
                        navbar.style.transform = "translateY(0)";
                    } else {
                        navbar.style.transform = "translateY(-100%)";
                    }
                });
            }

            function updateLanguageLinks() {
                const currentUrl = new URL(window.location);
                const currentParams = currentUrl.searchParams;

                const langLinks = document.querySelectorAll('.lang-btn, .lang-btn-mobile');

                langLinks.forEach(link => {
                    const href = link.getAttribute('href');

                    const langMatch = href.match(/lang=([^&]+)/);
                    if (langMatch) {
                        const langCode = langMatch[1];

                        const newParams = new URLSearchParams(currentParams);
                        newParams.set('lang', langCode);

                        link.setAttribute('href', '?' + newParams.toString());
                    }
                });
            }

            updateLanguageLinks();
            window.addEventListener('popstate', updateLanguageLinks);
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="/static/js/layout.js"></script>
    </body>
    </html>
</#macro>

<#macro csrf>
    <#if _csrf??>
        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
               value="${(_csrf.token)!'csrf-token'}"/>
    </#if>
</#macro>
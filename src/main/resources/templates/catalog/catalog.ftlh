<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Limon.kg Catalog</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome (for icons) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <link rel="stylesheet" href="/static/css/catalog.css">

</head>

<body>

<!-- Header -->
<header class="bg-white border-bottom d-none d-lg-block mb-3">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center">

            <!-- Left: Social icons -->
            <ul class="list-inline mb-0">
                <li class="list-inline-item me-2"><a href="https://www.facebook.com/limonkg/" class="text-dark"><i class="fab fa-facebook-f"></i></a></li>
                <li class="list-inline-item me-2"><a href="https://twitter.com/LimonKG" class="text-dark"><i class="fab fa-twitter"></i></a></li>
                <li class="list-inline-item me-2"><a href="https://www.instagram.com/limon_kg/" class="text-dark"><i class="fab fa-instagram"></i></a></li>
                <li class="list-inline-item me-2"><a href="https://www.youtube.com/" class="text-dark"><i class="fab fa-youtube"></i></a></li>
                <li class="list-inline-item"><a href="https://wa.me/996554202981" class="text-dark"><i class="fab fa-whatsapp"></i></a></li>
            </ul>

            <!-- Center: Logo -->
            <a href="/ru/" class="mx-auto">
                <img src="/static/images/Screenshot 2025-05-20 at 23.12.43.png" alt="Limon.kg" height="40">
            </a>

            <!-- Right: Lang + Auth -->
            <div class="d-flex align-items-center gap-3">
                <!-- Language -->
                <div class="locale_switcher d-flex">
                    <button class="btn btn-link text-dark px-1 is-selected" disabled>рус</button>
                    <button class="btn btn-link text-dark px-1">кырг</button>
                </div>

                <!-- Auth Links -->
                <div>
                    <a class="btn btn-link text-dark px-1" href="#">Вход</a>
                    <a class="btn btn-link text-dark px-1" href="#">Регистрация</a>
                </div>
            </div>
        </div>
    </div>
</header>
<nav class="navbar d-flex justify-content-between align-items-center px-3 mb-3">
    <div class="burger d-flex align-items-center" onclick="toggleMenu()">
        <i class="fa fa-bars"></i>
    </div>

    <div class="menu d-flex gap-3 align-items-center">
        <a href="/">Главная</a>
        <a href="/ru/catalog/?cat=1">Стартапы и Бизнес</a>
        <a href="/ru/catalog/?cat=45">IT</a>
        <a href="/ru/catalog/?cat=55">Образование</a>
        <a href="/ru/catalog/?cat=14">Карьера</a>
        <a href="/ru/catalog/?cat=84">Новости</a>
        <a href="/ru/catalog/?cat=91">Новости партнеров</a>
        <a href="/ru/catalog/?cat=243">Блоги</a>
    </div>
</nav>



<div class="mobile-menu" id="mobileMenu">
    <a href="/">Главная</a>
    <a href="/ru/catalog/?cat=1">Стартапы и Бизнес</a>
    <a href="/ru/catalog/?cat=45">IT</a>
    <a href="/ru/catalog/?cat=55">Образование</a>
    <a href="/ru/catalog/?cat=14">Карьера</a>
    <a href="/ru/catalog/?cat=84">Новости</a>
    <a href="/ru/catalog/?cat=91">Новости партнеров</a>
    <a href="/ru/catalog/?cat=243">Блоги</a>
</div>

<div class="container">


    <!-- Repeat this .row 12 times -->
    <div class="row">
        <div class="main-block">
            <img src="../static/images/Screenshot 2025-05-17 at 00.14.40.png" alt="Main image" class="content-image">
            <div class="content-body">
                <div class="category">НОВОСТИ · 21.05.2025</div>
                <a href="#" class="title">
                    inDrive упростил поездки по Кыргызстану — теперь можно уехать без автовокзала и маршруток
                </a>
            </div>
        </div>

        <div class="side-block">
            <img src="../static/images/Screenshot 2025-05-20 at 23.12.43.png" alt="Side image" class="content-image">
            <div class="content-body">
                <div class="category">ОБРАЗОВАНИЕ · 20.05.2025</div>
                <a href="#" class="title">
                    Как Нурсултан из Оша поступил в университет, где учились Илон Маск и Дональд Трамп
                </a>
            </div>
        </div>
    </div>

    <!-- Repeat the above .row block 11 more times -->

</div>



<!-- Footer -->
<footer class="bg-light text-dark pt-5 border-top">
    <div class="container">
        <div class="row flex-nowrap">
            <!-- Контакты -->
            <div class=" col-12 col-md-6 mb-4">
                <h5 class="text-primary">КОНТАКТЫ</h5>
                <p><strong>Бишкек ш., Чуйков көч. 93</strong></p>
                <p><a href="mailto:limonmediakg@gmail.com" class="text-dark">limonmediakg@gmail.com</a></p>
                <p><a href="tel:+996555202981" class="text-dark">Тел.: 0554 20 29 81</a></p>
                <p><a href="#" class="text-dark">Рекламодателям</a></p>
                <p><a href="#" class="text-dark">Политика конфиденциальности</a></p>
                <p><a href="#" class="text-dark">Договор оферты</a></p>
            </div>

            <!-- Рубрики -->
            <div class=" col-12 col-md-3 mb-4">
                <h5 class="text-primary">РУБРИКИ</h5>
                <ul class="list-unstyled">
                    <li><a href="#" class="text-dark">Стартапы и Бизнес</a></li>
                    <li><a href="#" class="text-dark">IT</a></li>
                    <li><a href="#" class="text-dark">Образование</a></li>
                    <li><a href="#" class="text-dark">Карьера</a></li>
                    <li><a href="#" class="text-dark">Новости</a></li>
                    <li><a href="#" class="text-dark">Новости партнеров</a></li>
                    <li><a href="#" class="text-dark">Блоги</a></li>
                </ul>
            </div>

            <!-- Соцсети -->
            <div class=" col-12 col-md-3 mb-4">
                <h5 class="text-black mb-3">СОЦСЕТИ</h5>
                <ul class="list-unstyled">
                    <li class="mb-2">
                        <a href="#" class="text-black text-decoration-none d-flex align-items-center">
                            <i class="fab fa-facebook fa-lg me-2"></i> Facebook
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="text-black text-decoration-none d-flex align-items-center">
                            <i class="fab fa-twitter fa-lg me-2"></i> Twitter
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="text-black text-decoration-none d-flex align-items-center">
                            <i class="fab fa-instagram fa-lg me-2"></i> Instagram
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="text-black text-decoration-none d-flex align-items-center">
                            <i class="fab fa-youtube fa-lg me-2"></i> YouTube
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" class="text-black text-decoration-none d-flex align-items-center">
                            <i class="fab fa-whatsapp fa-lg me-2"></i> WhatsApp
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="text-center pt-3 border-top">
            <p class="mb-0">© 2025 Все права защищены &nbsp;|&nbsp; 16+</p>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/static/js/catalog.js"></script>
</body>
</html>

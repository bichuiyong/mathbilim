

<#import "../layout.ftlh" as main>


<@main.layout>
    <style>
        * {
            box-sizing: border-box;
        }

        .text-gray {
            color: #6c757d;
        }
        a.text-black:hover {
            color: #555 !important;
        }

        .header_logo img {
            max-height: 60px;
        }

        .top_pannel_menu ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            gap: 10px;
        }

        .top_pannel_menu li a {
            color: #000;
            font-size: 1.2rem;
        }

        header {
            z-index: 1030;
        }

        header ul.list-inline i {
            font-size: 16px;
            transition: color 0.2s ease-in-out;
        }

        header ul.list-inline a:hover {
            color: #6b5b95;
        }

        .locale_switcher .btn.is-selected {
            font-weight: bold;
            cursor: default;
            text-decoration: underline;
        }

        body {
            margin: 0;
            padding: 20px;
            font-family: sans-serif;
            background: white;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 30px;
        }

        /* === Main & Side Blocks === */
        .main-block,
        .side-block {
            height: 300px;
            background-color: white;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
            border-radius: 8px;
            overflow: hidden;
        }

        /*=== Main Block ===*/
        .main-block {
            position: relative;
            flex: 1 1 calc(66.66% - 15px); /* Adjusted for gap */
            min-width: 300px; /* Minimum width before wrapping */
        }

        .side-block{
            position: relative;
            flex: 1 1 calc(33.33% - 15px); /* Adjusted for gap */
            min-width: 250px; /* Minimum width before wrapping */
        }

        .main-block .content-image {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
            border-radius: 8px;
            transition: transform 0.3s ease;
            z-index: 1;
        }

        .main-block .content-image:hover {
            transform: scale(1.05);
            cursor: pointer;
        }

        .main-block .content-body {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 15px;
            background: linear-gradient(to top, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0));
            border-bottom-left-radius: 8px;
            border-bottom-right-radius: 8px;
            z-index: 2;
        }

        .main-block .content-body * {
            color: #fff !important;
        }

        /* === Side Block === */
        .side-block {
            display: flex;
            flex-direction: column;
        }

        .side-block .content-image {
            width: 100%;
            height: 180px;
            object-fit: cover;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            flex-shrink: 0;
            transition: transform 0.3s ease;
        }

        .side-block .content-body {
            flex-grow: 1;
            padding: 15px;
            background-color: #fff;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
        }

        .side-block .category,
        .side-block .title {
            color: #000 !important;
        }

        .side-block .content-image:hover {
            transform: scale(1.05);
            cursor: pointer;
        }

        .content-body {
            padding: 15px;
        }

        .category {
            font-size: 12px;
            margin-bottom: 5px;
            text-transform: uppercase;
            font-weight: 500;
        }

        .title {
            font-size: 18px;
            font-weight: bold;
            text-decoration: none;
            line-height: 1.4;
            margin: 0;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .title:hover {
            text-decoration: underline;
        }

        .mobile-menu {
            position: fixed;
            top: 0;
            left: 0;
            height: 100vh;
            width: 25vw;
            max-width: 300px;
            background-color: #fff;
            padding: 1rem;
            box-shadow: 2px 0 5px rgba(0,0,0,0.2);
            transform: translateX(-100%);
            transition: transform 0.3s ease-in-out;
            z-index: 1050;
            display: flex;
            flex-direction: column;
        }

        .mobile-menu .close-btn {
            align-self: flex-end;
            background: none;
            border: none;
            font-size: 1.5rem;
            color: #000;
            cursor: pointer;
            margin-bottom: 1rem;
        }

        .mobile-menu.show {
            transform: translateX(0);
        }

        .menu {
            margin-left: auto;
            width: auto;
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        a {
            text-decoration: none !important;
            color: black;
        }

        footer ul li {
            margin-bottom: 0.5rem;
        }

        .text-purple {
            color: #5a42bc !important;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .main-block,
            .side-block {
                flex: 1 1 100%;
                min-width: unset;
                height: auto;
                min-height: 300px;
            }

            .main-block .content-image,
            .side-block .content-image {
                height: 200px;
            }

            .main-block .content-body,
            .side-block .content-body {
                padding: 12px;
            }

            .title {
                font-size: 16px;
                -webkit-line-clamp: 2;
            }

            .category {
                font-size: 11px;
            }

            .row {
                gap: 15px;
            }
        }

        @media (max-width: 480px) {
            body {
                padding: 10px;
            }

            .main-block .content-body,
            .side-block .content-body {
                padding: 10px;
            }

            .title {
                font-size: 14px;
                -webkit-line-clamp: 2;
            }

            .category {
                font-size: 10px;
                margin-bottom: 3px;
            }

            .row {
                gap: 10px;
            }
        }


        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 40px 0;
            gap: 8px;
        }

        .pagination-btn {
            background: none;
            border: 1px solid #ddd;
            color: #666;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.2s ease;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            min-width: 40px;
            height: 36px;
        }

        .pagination-btn:hover {
            background-color: #f5f5f5;
            border-color: #999;
        }

        .pagination-btn.active {
            background-color: #007bff;
            border-color: #007bff;
            color: white;
        }

        .pagination-btn.disabled {
            color: #ccc;
            cursor: not-allowed;
            border-color: #eee;
        }

        .pagination-btn.disabled:hover {
            background: none;
            border-color: #eee;
        }

        .pagination-dots {
            color: #666;
            padding: 0 8px;
            font-size: 14px;
        }

        @media (max-width: 480px) {
            .pagination {
                gap: 4px;
                margin: 30px 0;
            }

            .pagination-btn {
                padding: 6px 8px;
                min-width: 32px;
                height: 32px;
                font-size: 12px;
            }
        }

    </style>
<div class="container">
<!-- Catalog Filter/Search Section -->
<h3 class="text-gray">Каталог</h3>
<div class="row mb-4">
    <div class="col-12">
        <input type="text" class="form-control" placeholder="">
    </div>

    <div class="d-flex gap-2 mb-3">
        <select class="form-select w-50">
            <option selected disabled>Категория</option>
            <option>Стартапы и Бизнес</option>
            <option>IT</option>
            <option>Образование</option>
            <option>Карьера</option>
            <option>Новости</option>
            <option>Новости партнеров</option>
            <option>Блоги</option>
        </select>

        <select class="form-select w-50">
            <option selected disabled>Сортировать</option>
            <option>Сначала новые</option>
            <option>Сначала старые</option>
            <option>По популярности</option>
        </select>
    </div>



</div>




<div class="container">
    <div class="row">
        <div class="main-block">
            <img src="https://via.placeholder.com/600x300/4a90e2/ffffff?text=Main+Article" alt="Main image" class="content-image">
            <div class="content-body">
                <div class="category">НОВОСТИ · 21.05.2025</div>
                <a href="#" class="title">
                    inDrive упростил поездки по Кыргызстану — теперь можно уехать без автовокзала и маршруток
                </a>
            </div>
        </div>

        <div class="side-block">
            <img src="https://via.placeholder.com/400x200/7b68ee/ffffff?text=Side+Article" alt="Side image" class="content-image">
            <div class="content-body">
                <div class="category">ОБРАЗОВАНИЕ · 20.05.2025</div>
                <a href="#" class="title">
                    Как Нурсултан из Оша поступил в университет, где учились Илон Маск и Дональд Трамп
                </a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="main-block">
            <img src="https://via.placeholder.com/600x300/4a90e2/ffffff?text=Main+Article" alt="Main image" class="content-image">
            <div class="content-body">
                <div class="category">НОВОСТИ · 21.05.2025</div>
                <a href="#" class="title">
                    inDrive упростил поездки по Кыргызстану — теперь можно уехать без автовокзала и маршруток
                </a>
            </div>
        </div>

        <div class="side-block">
            <img src="https://via.placeholder.com/400x200/7b68ee/ffffff?text=Side+Article" alt="Side image" class="content-image">
            <div class="content-body">
                <div class="category">ОБРАЗОВАНИЕ · 20.05.2025</div>
                <a href="#" class="title">
                    Как Нурсултан из Оша поступил в университет, где учились Илон Маск и Дональд Трамп
                </a>
            </div>
        </div>
    </div>
    <!-- Repeat the above .row block 11 more times -->

    <div class="pagination">
        <button class="pagination-btn disabled">‹</button>
        <button class="pagination-btn active">1</button>
        <button class="pagination-btn">2</button>
        <button class="pagination-btn">3</button>
        <button class="pagination-btn">4</button>
        <button class="pagination-btn">5</button>
        <span class="pagination-dots">...</span>
        <button class="pagination-btn">12</button>
        <button class="pagination-btn">›</button>
    </div>
</div>


    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/static/js/catalog.js"></script>


<script>
    function exitMobileMenu(event) {
        if (event) event.preventDefault();
        document.getElementById('mobileMenu').classList.remove('show');
        setTimeout(() => {
            window.location.href = '/catalog';
        }, 300);
    }

    function toggleMenu(event) {
        if (event) event.preventDefault();
        const menu = document.getElementById('mobileMenu');
        menu.classList.toggle('show');
    }
</script>
</@main.layout>
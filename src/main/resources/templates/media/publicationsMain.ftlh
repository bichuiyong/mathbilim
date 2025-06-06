<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="/static/css/publications.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.css" rel="stylesheet">


    <div class="container py-4">
        <div class="row">
            <aside class="col-md-3">
                <div class="sidebar custom-sidebar p-3 rounded shadow-sm bg-white">
                    <h3>Публикации по подразделениям</h3>
                    <a href="#">Центральный аппарат</a>
                    <a href="#">Управление науки и инноваций</a>
                    <a href="#">Управление высшего образования</a>
                    <a href="#">Управление начального образования</a>
                    <a href="#">Управление школьного образования</a>
                    <a href="#">Управление дошкольного образования</a>
                    <a href="#">Управление финансов</a>
                </div>
            </aside>

            <section class="col-md-9 publications-list">
                <a href="#" class="text-decoration-none">
                    <div class="card mb-4 shadow-sm d-flex flex-row align-items-center p-3">
                        <img src="/static/images/photo_logo1.webp" alt="Публикация" class="news-card-img me-3">
                        <div>
                            <h4>Ученики Кыргызстана поступили в университеты Лиги Плюща</h4>
                            <p>Сегодня, 5 июня, министр образования встретилась с выпускниками, добившимися выдающегося
                                успеха. Трое из них зачислены в университеты Лиги Плюща...</p>
                            <div class="news-meta">
                                <span>Отдел инфополитики</span>
                                <span>5 июня 2025</span>
                                <span>👁️ 97</span>
                            </div>
                        </div>
                    </div>
                </a>
            </section>
        </div>
    </div>
</@main.layout>

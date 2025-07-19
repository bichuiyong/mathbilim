<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="../static/scss/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.css" rel="stylesheet">


    <div class="container py-4" style="margin-bottom: 250px; margin-top: 50px">
        <div class="row">
            <aside class="col-md-3 mb-3">
                <div class="bg-white p-3 rounded shadow-sm sidebar">
                    <h3>Новости от подразделений</h3>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle-publication w-100" type="button" id="departmentDropdown"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            Выбрать подразделение
                        </button>
                        <ul class="dropdown-menu w-100" aria-labelledby="departmentDropdown">
                            <li><a class="dropdown-item" href="#">Центральный аппарат</a></li>
                            <li><a class="dropdown-item" href="#">Управление науки</a></li>
                            <li><a class="dropdown-item" href="#">Управление высшего</a></li>
                            <li><a class="dropdown-item" href="#">Управление начального</a></li>
                            <li><a class="dropdown-item" href="#">Управление школьного</a></li>
                            <li><a class="dropdown-item" href="#">Управление дошкольного</a></li>
                            <li><a class="dropdown-item" href="#">Управление финансов</a></li>
                        </ul>
                    </div>
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

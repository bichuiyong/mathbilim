<#import "../layout.ftlh" as main>
<@main.layout>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/main.css">

    <section class="hero-section py-5">
        <div class="container text-center">
            <h1 class="display-3 fw-bold mb-3">Математические мероприятия</h1>
        </div>
    </section>

    <div class="container py-4">
        <div class="d-flex flex-column flex-sm-row justify-content-center align-items-center gap-3 mb-4">
            <a href="/event/add" class="btn btn-add-event text-white rounded-pill px-4 py-2">
                <i class="fas fa-plus me-2"></i>Добавить мероприятие
            </a>
        </div>

        <div class="row mb-5">
            <div class="col-12">
                <div class="card event-card featured-event shadow-lg"
                     style="background-image: url('https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=1200&q=80');"
                     onclick="window.location.href='/event/details'">
                    <div class="badge event-date px-3 py-2">20 июня</div>
                    <div class="badge event-type-badge badge-олимпиада px-3 py-1">Олимпиада</div>
                    <div class="event-content p-4">
                        <h2 class="event-title mb-3">Республиканская олимпиада по математике</h2>
                        <div class="mb-2 opacity-75">
                            <i class="fas fa-user me-2"></i>Министерство образования КР
                        </div>
                        <div class="opacity-75">
                            <i class="fas fa-map-marker-alt me-2"></i>АУЦА, Бишкек
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <h3 class="h4 fw-semibold mb-4 text-dark">Ближайшие мероприятия</h3>

        <div class="row g-4">
            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     style="background-image: url('https://images.unsplash.com/photo-1517077304055-6e89abbf09b0?auto=format&fit=crop&w=800&q=80');"
                     onclick="window.location.href='/event/details'">
                    <div class="badge event-date px-2 py-1">25 июня</div>
                    <div class="badge event-type-badge badge-семинар px-2 py-1">Семинар</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Современные методы преподавания математики</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>Ассоциация учителей математики
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-laptop me-1"></i>Онлайн
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     style="background-image: url('https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=800&q=80');"
                     onclick="window.location.href='/event/details'">
                    <div class="badge event-date px-2 py-1">28 июня</div>
                    <div class="badge event-type-badge badge-лекция px-2 py-1">Лекция</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Лекция: Теория вероятностей</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>Проф. Иванов А.И.
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-globe me-1"></i>Zoom
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     onclick="window.location.href='/event/details'">
                    <div class="badge event-date px-2 py-1">1 июля</div>
                    <div class="badge event-type-badge badge-олимпиада px-2 py-1">Олимпиада</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Международная студенческая олимпиада</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>ИСОМО
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-map-marker-alt me-1"></i>Казань, Россия
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 col-xl-4">
                <div class="card event-card shadow"
                     onclick="window.location.href='/event/details'">
                    <div class="badge event-date px-2 py-1">5 июля</div>
                    <div class="badge event-type-badge badge-семинар px-2 py-1">Семинар</div>
                    <div class="event-content p-3">
                        <h4 class="event-title h5 mb-2">Математика в программировании</h4>
                        <div class="small mb-1 opacity-75">
                            <i class="fas fa-user me-1"></i>CodeMath KG
                        </div>
                        <div class="small opacity-75">
                            <i class="fas fa-laptop me-1"></i>Гибридный формат
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
</@main.layout>

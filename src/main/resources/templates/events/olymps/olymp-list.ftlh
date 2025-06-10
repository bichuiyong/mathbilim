<#import "../../layout.ftlh" as main>
<@main.layout>
    <div class="container-fluid mt-4" style="margin-bottom: 50px">
        <div class="row">
            <main class="col-12 px-md-4">
                <h1 class="h2 mb-4">Олимпиады</h1>


                <div style="max-width: 1200px; margin: 0 auto">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-4">


                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <img src="https://api.olympiads.kg/Files/Contests/2066/tsi-2025.jpeg"
                                     class="card-img-top p-3"
                                     alt="TSI"
                                     style="height: 200px; object-fit: contain;">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">TSI Programming Contest - Олимпиада TSI по программированию</h5>
                                    <div class="mt-auto text-start">
                                        <a href="#" class="text-primary text-decoration-none">Подробнее</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <img src="https://api.olympiads.kg/Files/Contests/33/IOI.png"
                                     class="card-img-top p-3"
                                     alt="IOI"
                                     style="height: 200px; object-fit: contain;">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">Информатика. IV Национальная олимпиада</h5>
                                    <div class="mt-auto text-start">
                                        <a href="#" class="text-primary text-decoration-none">Подробнее</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <img src="https://api.olympiads.kg/Files/Contests/1049/KRSU-logo240.png"
                                     class="card-img-top p-3"
                                     alt="KRSU"
                                     style="height: 200px; object-fit: contain;">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">Функциональное программирование</h5>
                                    <div class="mt-auto text-start">
                                        <a href="#" class="text-primary text-decoration-none">Подробнее</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col">
                            <div class="card h-100 shadow-sm border-0">
                                <img src="https://api.olympiads.kg/Files/Contests/35/itcup.png"
                                     class="card-img-top p-3"
                                     alt="IT Cup"
                                     style="height: 200px; object-fit: contain;">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">IT Кубок Кыргызстана 2023</h5>
                                    <div class="mt-auto text-start">
                                        <a href="#" class="text-primary text-decoration-none">Подробнее</a>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>



                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Назад</a>
                        </li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">Вперед</a>
                        </li>
                    </ul>
                </nav>

                <style>

                    .card {
                        transition: transform 0.2s ease;
                        border-radius: 8px;
                        overflow: hidden;
                        background: #f8f9fa;
                    }

                    .card:hover {
                        transform: translateY(-5px);
                    }

                    .pagination {
                        display: flex;
                        padding-left: 0;
                        list-style: none;
                        gap: 8px;
                        margin: 50px 0 10px;
                    }

                    .page-item.active .page-link:hover {
                        background-color: #0d6efd;
                        color: white;
                        cursor: default;
                    }

                    .page-link {
                        position: relative;
                        display: block;
                        padding: 8px 16px;
                        text-decoration: none;
                        color: #333;
                        background-color: #fff;
                        border: 1px solid #ddd;
                        border-radius: 6px;
                        transition: all 0.2s ease;
                    }

                    .page-link:focus {
                        outline: none;
                        box-shadow: none;
                    }

                    .page-link:hover {
                        background-color: #f0f0f0;
                    }

                </style>
            </main>
        </div>
    </div>
</@main.layout>
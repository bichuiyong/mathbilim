<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        .news-item {
            border-bottom: 1px solid #dee2e6;
            padding: 10px 0;
        }

        .news-meta {
            font-size: 0.875rem;
            color: #6c757d;
        }

        .news-icon {
            width: 18px;
            height: 18px;
            margin-right: 5px;
        }

        .news-block {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            border-radius: 10px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
        }
    </style>
    <div class="bg-light">
        <div class="news-block">
            <h5 class="mb-4">📰 Новости</h5>
        <#if news?has_content>
            <#list news as news>
                <div class="news-item">
                    <div>
                        <a href="/news/detail?id=${news.id}" class="text-decoration-none">
                            ${news.newsTranslations[0].title}
                        </a>
                    </div>
                    <div class="news-meta">${news.formattedDate} • 👁 ${news.viewCount}</div>
                </div>
            </#list>
        </#if>
            <div class="news-item">
                <div>В Москве активизировались «квартирные туристы»: они интересуются жильем, но ничего не покупают</div>
                <div class="news-meta">16.05 • 👁 101</div>
            </div>

            <div class="news-item">
                <div>«Водоходъ-экспресс» открыл летний сезон пассажирской навигации на Черном море</div>
                <div class="news-meta">16.05 • 👁 4</div>
            </div>

            <div class="news-item">
                <div>Garmin представила смарт-часы Forerunner 970 и 570 с новыми метриками бега. Цена — от 549 до 749 $
                </div>
                <div class="news-meta">16.05 • 👁 20</div>
            </div>

            <div class="news-item">
                <div>Sony представила наушники WH-1000XM6 с улучшенным шумоподавлением и складным дизайном. Цена — 449 $
                </div>
                <div class="news-meta">16.05 • 👁 29</div>
            </div>

            <div class="news-item">
                <div>Huawei выпустила смарт-часы Watch 5 с мониторингом девяти показателей здоровья. Цена — от 39 999 ₽
                </div>
                <div class="news-meta">15.05 • 👁 26</div>
            </div>

            <div class="news-item">
                <div>Центральный университет открыл набор на бесплатную ИТ-смену в «Артек»</div>
                <div class="news-meta">15.05 • 👁 1</div>
            </div>

            <div class="news-item">
                <div>«Дом.рф» опубликовал обзор ипотечного рынка в 2025 году — он стремительно падает</div>
                <div class="news-meta">15.05 • 👁 85</div>
            </div>

            <div class="news-item">
                <div>LG показала безрамочный монитор Zero Bezel Micro LED — два таких объединяются в один большой дисплей
                </div>
                <div class="news-meta">15.05 • 👁 4</div>
            </div>

            <div class="news-item">
                <div>«Лукойл» выплатит дивиденды за 2024 год — по 541 ₽ на акцию</div>
                <div class="news-meta">15.05 • 👁 5</div>
            </div>

            <div class="text-primary mt-3" style="cursor: pointer;">Смотреть все →</div>
        </div>
    </div>
</@main.layout>
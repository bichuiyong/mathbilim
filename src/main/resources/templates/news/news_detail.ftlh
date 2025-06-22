<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <!-- Заголовок новости -->
                <h1 class="mb-3">${newsDto.newsTranslations[0].title}</h1>

                <!-- Дата публикации -->
                <p class="text-muted mb-4">Опубликовано: ${newsDto.formattedDate}</p>

                <!-- Изображение новости -->
                <#if newsDto.mainImage??>
                    <img src="${newsDto.mainImage.s3Link}" alt="Изображение новости"
                         class="img-fluid rounded mb-4" />
                </#if>


                <!-- Контент новости -->
                <div class="news-content fs-5">
                    <p>
                        ${newsDto.newsTranslations[0].content}
                    </p>
                </div>

                <!-- Кнопка назад -->
                <a href="/news" class="btn btn-primary mt-4">← Вернуться к новостям</a>
            </div>
        </div>
    </div>
</@main.layout>
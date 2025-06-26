<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background-color: #fafafa;
            color: #262626;
            line-height: 1.4;
        }
        .content-wrapper {
            max-width: 470px;
            margin: 0 auto;
            padding: 20px 0;
        }
        .publication-item {
            background: white;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            margin-bottom: 24px;
        }
        .publication-header {
            display: flex;
            align-items: center;
            padding: 16px;
            border-bottom: 1px solid #efefef;
        }
        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(45deg, #fd5949, #d6249f, #285aeb);
            padding: 2px;
            margin-right: 12px;
        }
        .avatar img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid white;
        }
        .publication-author {
            font-weight: 600;
            font-size: 15px;
            color: #262626;
            text-decoration: none;
        }

        .publication-carousel {
            height: 470px;
            background: #f8f8f8;
        }

        .publication-carousel .carousel-inner {
            height: 100%;
            border-radius: 0;
        }

        .publication-carousel .carousel-item {
            height: 100%;
        }

        .publication-carousel .carousel-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .publication-carousel .carousel-control-prev,
        .publication-carousel .carousel-control-next {
            width: 40px;
            height: 40px;
            background: rgba(0, 0, 0, 0.5);
            border-radius: 50%;
            top: 50%;
            transform: translateY(-50%);
            opacity: 1;
        }

        .publication-carousel .carousel-control-prev {
            left: 12px;
        }

        .publication-carousel .carousel-control-next {
            right: 12px;
        }

        .publication-carousel .carousel-control-prev:hover,
        .publication-carousel .carousel-control-next:hover {
            background: rgba(0, 0, 0, 0.7);
        }

        .publication-carousel .carousel-control-prev-icon,
        .publication-carousel .carousel-control-next-icon {
            width: 16px;
            height: 16px;
        }

        .publication-carousel .carousel-indicators {
            bottom: 12px;
            margin-bottom: 0;
        }

        .publication-carousel .carousel-indicators [data-bs-target] {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.4);
            border: none;
            margin: 0 3px;
        }

        .publication-carousel .carousel-indicators .active {
            background-color: white;
            transform: scale(1.2);
        }

        .image-counter {
            position: absolute;
            top: 12px;
            right: 12px;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
            z-index: 10;
        }

        .single-image .carousel-control-prev,
        .single-image .carousel-control-next,
        .single-image .carousel-indicators,
        .single-image .image-counter {
            display: none !important;
        }

        .publication-content {
            padding: 12px 16px;
        }
        .publication-title {
            font-weight: 600;
            font-size: 18px;
            margin-bottom: 8px;
            line-height: 1.3;
        }
        .publication-text {
            font-size: 15px;
            color: #262626;
            margin-bottom: 12px;
            line-height: 1.5;
            white-space: pre-wrap;
            word-break: break-word;
            overflow-wrap: break-word;
            overflow: hidden;
            transition: max-height 0.3s ease;
        }

        .publication-text.collapsed {
            max-height: 120px;
            position: relative;
        }

        .show-more-btn {
            display: inline-block;
            margin-top: 4px;
            color: #0095f6;
            font-weight: 600;
            cursor: pointer;
            user-select: none;
        }
        .publication-meta {
            font-size: 13px;
            color: #8e8e8e;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 12px;
            flex-wrap: wrap;
        }

        .meta-item {
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .back-btn {
            display: block;
            text-align: center;
            margin: 16px 0;
            padding: 12px 24px;
            background: linear-gradient(135deg, #0095f6, #0074cc);
            color: white;
            font-weight: 600;
            border-radius: 8px;
            text-decoration: none;
            transition: all 0.2s ease;
            box-shadow: 0 2px 4px rgba(0, 149, 246, 0.3);
        }
        .back-btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 149, 246, 0.4);
            text-decoration: none;
            color: white;
        }

        @media (max-width: 480px) {
            .content-wrapper {
                padding: 0;
            }

            .publication-item {
                border-radius: 0;
                border-left: none;
                border-right: none;
                margin-bottom: 0;
            }

            .publication-carousel {
                height: 400px;
            }

            .publication-carousel .carousel-control-prev,
            .publication-carousel .carousel-control-next {
                width: 32px;
                height: 32px;
            }

            .publication-carousel .carousel-control-prev {
                left: 8px;
            }

            .publication-carousel .carousel-control-next {
                right: 8px;
            }

            .back-btn {
                margin: 12px;
                border-radius: 8px;
            }
        }

        .publication-carousel .carousel-item img {
            user-select: none;
            -webkit-user-drag: none;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
        }
    </style>

    <div class="content-wrapper">
        <article class="publication-item">
            <header class="publication-header">
                <div class="avatar">
                </div>
                <a href="/profile/info/${newsDto.creator.id}" class="publication-author">${newsDto.creator.name}</a>
            </header>

            <#assign allImages = []>
            <#if newsDto.mainImage??>
                <#assign allImages = allImages + [{"url": "/api/files/${newsDto.mainImageId}/view", "alt": "Main Image"}]>
            </#if>
            <#if newsDto.newsFiles?? && newsDto.newsFiles?size gt 0>
                <#list newsDto.newsFiles as file>
                    <#assign allImages = allImages + [{"url": "/api/files/${file.id}/view", "alt": "Post Image " + (file_index + 1)}]>
                </#list>
            </#if>

            <#if allImages?size gt 0>
                <div id="postCarousel" class="carousel slide publication-carousel <#if allImages?size == 1>single-image</#if>" data-bs-ride="false" data-bs-touch="true">
                    <#if allImages?size gt 1>
                        <div class="image-counter">
                            <span id="currentSlide">1</span>/${allImages?size}
                        </div>

                        <div class="carousel-indicators">
                            <#list 0..(allImages?size-1) as i>
                                <button type="button" data-bs-target="#postCarousel" data-bs-slide-to="${i}"
                                        <#if i == 0>class="active" aria-current="true"</#if>
                                        aria-label="Slide ${i + 1}"></button>
                            </#list>
                        </div>
                    </#if>

                    <div class="carousel-inner">
                        <#list allImages as image>
                            <div class="carousel-item <#if image_index == 0>active</#if>">
                                <img src="${image.url}" alt="${image.alt}" class="d-block">
                            </div>
                        </#list>
                    </div>

                    <#if allImages?size gt 1>
                        <button class="carousel-control-prev" type="button" data-bs-target="#postCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#postCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </#if>
                </div>
            </#if>

            <div class="publication-content">
                <#assign translation = newsDto.newsTranslations[0]>
                <#if translation.title?? && translation.title?has_content>
                    <div class="publication-title">${translation.title}</div>
                </#if>
                <#if translation.content?? && translation.content?has_content>
                    <div class="publication-text collapsed" id="postContent">${translation.content}</div>
                    <div class="show-more-btn" id="toggleButton">Показать ещё</div>
                </#if>
                <div class="publication-meta">
                    <div class="meta-item">
                        📅 ${newsDto.formattedDate}
                    </div>
                    <div class="meta-item">
                        👁 ${newsDto.viewCount!0}
                    </div>
                    <#if newsDto.shareCount?? && newsDto.shareCount gt 0>
                        <div class="meta-item">
                            📤 ${newsDto.shareCount}
                        </div>
                    </#if>
                    <#if allImages?size gt 1>
                        <div class="meta-item">
                            📷 ${allImages?size} фото
                        </div>
                    </#if>
                </div>
            </div>
        </article>

        <a href="/posts" class="back-btn">← Вернуться к публикациям</a>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const carouselElement = document.getElementById('postCarousel');
            const currentSlideSpan = document.getElementById('currentSlide');

            if (carouselElement && currentSlideSpan) {
                const carousel = new bootstrap.Carousel(carouselElement, {
                    interval: false,
                    wrap: false,
                    touch: true
                });

                carouselElement.addEventListener('slid.bs.carousel', function(event) {
                    const activeIndex = event.to;
                    if (currentSlideSpan) {
                        currentSlideSpan.textContent = activeIndex + 1;
                    }
                });

                document.addEventListener('keydown', function(e) {
                    if (e.key === 'ArrowLeft') {
                        carousel.prev();
                        e.preventDefault();
                    } else if (e.key === 'ArrowRight') {
                        carousel.next();
                        e.preventDefault();
                    }
                });
            }
        });


        document.addEventListener('DOMContentLoaded', function() {
            const contentElement = document.getElementById('postContent');
            const toggleButton = document.getElementById('toggleButton');

            if (contentElement && toggleButton) {
                if (contentElement.scrollHeight <= 120) {
                    toggleButton.style.display = 'none';
                }
                toggleButton.addEventListener('click', function() {
                    contentElement.classList.toggle('collapsed');
                    if (contentElement.classList.contains('collapsed')) {
                        toggleButton.textContent = 'Показать ещё';
                    } else {
                        toggleButton.textContent = 'Скрыть';
                    }
                });
            }
        });
    </script>
</@main.layout>
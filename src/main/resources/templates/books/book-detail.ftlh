<#import "../layout.ftlh" as main>
<@main.layout>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: #ffffff;
            color: #1e293b;
            line-height: 1.6;
            overflow-x: hidden;
        }

        .book-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            animation: fadeIn 1s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .book-header {
            text-align: center;
            margin-bottom: 3rem;
            position: relative;
        }

        .book-header::after {
            content: '';
            position: absolute;
            bottom: -1rem;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            border-radius: 2px;
            animation: expandLine 1.5s ease-out;
        }

        @keyframes expandLine {
            from { width: 0; }
            to { width: 100px; }
        }

        .book-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 1rem;
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            animation: titleGlow 2s ease-in-out infinite alternate;
        }

        @keyframes titleGlow {
            from { filter: brightness(1); }
            to { filter: brightness(1.1); }
        }

        .book-card {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 3rem;
            background: #ffffff;
            border-radius: 16px;
            padding: 2.5rem;
            box-shadow: 0 10px 40px rgba(37, 99, 235, 0.1);
            border: 1px solid rgba(37, 99, 235, 0.1);
            animation: slideUp 0.8s ease-out 0.3s both;
            transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
        }

        .book-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 60px rgba(37, 99, 235, 0.15);
            border-color: #2563eb;
        }

        @keyframes slideUp {
            from { opacity: 0; transform: translateY(50px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .book-image-section {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .book-image-container {
            position: relative;
            overflow: hidden;
            border-radius: 12px;
            height: 400px;
            background: linear-gradient(135deg, #f8fafc, #e2e8f0);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .book-image {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 12px;
            transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .book-image-container:hover .book-image {
            transform: scale(1.05);
        }

        .book-image-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(135deg, transparent, rgba(37, 99, 235, 0.1));
            opacity: 0;
            transition: opacity 0.4s ease;
            border-radius: 12px;
            z-index: 1;
        }

        .book-image-container:hover::before {
            opacity: 1;
        }

        .read-button-container {
            text-align: center;
        }

        .book-details {
            display: flex;
            flex-direction: column;
            gap: 2rem;
            position: relative;
        }

        .book-actions-dropdown {
            position: absolute;
            top: 0;
            right: 0;
            z-index: 10;
        }

        .book-meta {
            display: grid;
            gap: 1rem;
        }

        .meta-item {
            display: flex;
            align-items: center;
            padding: 0.75rem 0;
            border-bottom: 1px solid rgba(37, 99, 235, 0.1);
            animation: slideInLeft 0.6s ease-out;
            animation-delay: calc(var(--delay) * 0.1s);
            opacity: 0;
            animation-fill-mode: both;
            transition: all 0.3s ease;
        }

        .meta-item:hover {
            background: linear-gradient(135deg, #f8fafc, #e2e8f0);
            transform: translateX(10px);
            padding-left: 1rem;
            border-radius: 8px;
        }

        @keyframes slideInLeft {
            from { opacity: 0; transform: translateX(-20px); }
            to { opacity: 1; transform: translateX(0); }
        }

        .meta-label {
            font-weight: 600;
            color: #2563eb;
            min-width: 120px;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .meta-value {
            color: #475569;
            font-weight: 400;
            word-break: break-word;
        }

        .book-stats {
            display: flex;
            gap: 2rem;
            justify-content: center;
        }

        .stat-item {
            text-align: center;
            padding: 1rem;
            border-radius: 10px;
            background: linear-gradient(135deg, #f8fafc, #e2e8f0);
            border: 1px solid rgba(37, 99, 235, 0.1);
            min-width: 120px;
            animation: popIn 0.6s ease-out;
            animation-delay: calc(var(--stat-delay) * 0.1s);
            opacity: 0;
            animation-fill-mode: both;
            transition: transform 0.3s ease;
        }

        .stat-item:hover {
            transform: scale(1.05);
        }

        @keyframes popIn {
            0% { opacity: 0; transform: scale(0.8); }
            100% { opacity: 1; transform: scale(1); }
        }

        .stat-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #2563eb;
            display: block;
        }

        .stat-label {
            font-size: 0.9rem;
            color: #64748b;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .book-description {
            background: linear-gradient(135deg, #f8fafc, #f1f5f9);
            padding: 1.5rem;
            border-radius: 12px;
            border-left: 4px solid #2563eb;
            font-size: 1.1rem;
            line-height: 1.8;
            color: #334155;
            animation: fadeInUp 0.8s ease-out 0.6s both;
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Dropdown styles */
        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-toggle {
            background: transparent;
            border: none;
            font-size: 1.5rem;
            color: #64748b;
            cursor: pointer;
            padding: 0.5rem;
            border-radius: 50%;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
        }

        .dropdown-toggle:hover {
            background: #f1f5f9;
            color: #2563eb;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            right: 0;
            top: 100%;
            background: white;
            min-width: 200px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.15);
            border-radius: 12px;
            overflow: hidden;
            z-index: 1000;
            border: 1px solid rgba(37, 99, 235, 0.1);
            animation: dropdownSlide 0.3s ease-out;
        }

        @keyframes dropdownSlide {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .dropdown.active .dropdown-menu {
            display: block;
        }

        .dropdown-item {
            padding: 0.75rem 1rem;
            display: block;
            text-decoration: none;
            color: #1e293b;
            transition: background 0.2s ease;
            font-size: 0.95rem;
        }

        .dropdown-item:hover {
            background: #f8fafc;
            color: #2563eb;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 1rem 2rem;
            border-radius: 10px;
            font-weight: 600;
            text-decoration: none;
            font-size: 1rem;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: none;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }

        .btn-primary {
            background: linear-gradient(135deg, #2563eb, #1d4ed8);
            color: white;
            box-shadow: 0 4px 20px rgba(37, 99, 235, 0.3);
        }

        .btn-primary:hover {
            background: linear-gradient(135deg, #1d4ed8, #1e40af);
            transform: translateY(-2px);
            box-shadow: 0 8px 30px rgba(37, 99, 235, 0.4);
        }

        .btn-secondary {
            background: white;
            color: #2563eb;
            border: 2px solid #2563eb;
        }

        .btn-secondary:hover {
            background: #2563eb;
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 10px 30px rgba(37, 99, 235, 0.3);
        }

        @media (max-width: 768px) {
            .book-card {
                grid-template-columns: 1fr;
                gap: 2rem;
            }

            .book-title {
                font-size: 2rem;
            }

            .book-stats {
                flex-direction: column;
                gap: 1rem;
            }

            .book-container {
                padding: 1rem;
            }

            .book-actions-dropdown {
                position: relative;
                top: auto;
                right: auto;
                text-align: right;
                margin-top: 1rem;
            }
        }

        .floating-particles {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            pointer-events: none;
            z-index: -1;
        }

        .particle {
            position: absolute;
            width: 4px;
            height: 4px;
            background: #2563eb;
            border-radius: 50%;
            opacity: 0.1;
            animation: float 15s infinite linear;
        }

        @keyframes float {
            0% { transform: translateY(100vh) rotate(0deg); }
            100% { transform: translateY(-100vh) rotate(360deg); }
        }
    </style>

    <div class="floating-particles">
        <div class="particle" style="left: 10%; animation-delay: 0s;"></div>
        <div class="particle" style="left: 20%; animation-delay: 2s;"></div>
        <div class="particle" style="left: 30%; animation-delay: 4s;"></div>
        <div class="particle" style="left: 40%; animation-delay: 6s;"></div>
        <div class="particle" style="left: 50%; animation-delay: 8s;"></div>
        <div class="particle" style="left: 60%; animation-delay: 10s;"></div>
        <div class="particle" style="left: 70%; animation-delay: 12s;"></div>
        <div class="particle" style="left: 80%; animation-delay: 14s;"></div>
        <div class="particle" style="left: 90%; animation-delay: 16s;"></div>
    </div>

    <div class="book-container">
        <div class="book-header">
            <h1 class="book-title">${book.name}</h1>
        </div>

        <div class="book-card">
            <!-- Левая колонка: Изображение и кнопка чтения -->
            <div class="book-image-section">
                <div class="book-image-container">
                    <img src="/api/files/${book.mainImageId}/view" alt="${book.name}" class="book-image">
                </div>

                <div class="read-button-container d-flex gap-2 mt-3 justify-content-center">
                    <a href="/books/${book.file.id}/pdf-reader" class="btn btn-primary">
                        📖 Начать читать
                    </a>
                    <a href="/api/files/${book.file.id}/download" class="btn btn-primary" title="Скачать">
                        <i class="bi bi-download"></i>
                    </a>
                </div>
            </div>

            <!-- Правая колонка: Информация о книге -->
            <div class="book-details">
                <!-- Меню с тремя точками -->
                <#if currentUser?? && (currentUser.name == book.creator.name || currentUser.role == "ADMIN")>
                    <div class="book-actions-dropdown">
                        <div class="dropdown">
                            <button class="dropdown-toggle" type="button">⋮</button>
                            <div class="dropdown-menu">
                                <a href="/books/update/${book.id}" class="dropdown-item">✏️ Редактировать</a>
                                <a href="/books/delete/${book.id}" class="dropdown-item" onclick="return confirm('Вы уверены, что хотите удалить эту книгу? Это действие нельзя отменить.')">🗑️ Удалить</a>
                            </div>
                        </div>
                    </div>
                </#if>

                <!-- Метаинформация -->
                <div class="book-meta">
                    <div class="meta-item" style="--delay: 1;">
                        <span class="meta-label">Дата:</span>
                        <span class="meta-value">${book.formattedDate}</span>
                    </div>
                    <div class="meta-item" style="--delay: 2;">
                        <span class="meta-label">Авторы:</span>
                        <span class="meta-value">${book.authors}</span>
                    </div>
                    <div class="meta-item" style="--delay: 3;">
                        <span class="meta-label">ISBN:</span>
                        <span class="meta-value">${book.isbn}</span>
                    </div>
                    <div class="meta-item" style="--delay: 4;">
                        <span class="meta-label">Создатель:</span>
                        <span class="meta-value">${book.creator.name}</span>
                    </div>
                    <div class="meta-item" style="--delay: 5;">
                        <span class="meta-label">Категория:</span>
                        <span class="meta-value">${book.category.translations[0].translation}</span>
                    </div>
                </div>

                <!-- Статистика -->
                <div class="book-stats">
                    <div class="stat-item" style="--stat-delay: 8;">
                        <span class="stat-value">${book.viewCount}</span>
                        <span class="stat-label">Просмотров</span>
                    </div>
                </div>

                <!-- Описание -->
                <div class="book-description">
                    <p>${book.description}</p>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Анимация счетчика просмотров
            const viewCountEl = document.querySelector('.stat-value');
            if (viewCountEl && viewCountEl.textContent) {
                const target = parseInt(viewCountEl.textContent.replace(/,/g, ''));
                if (!isNaN(target)) {
                    animateCounter(viewCountEl, target);
                }
            }

            // Обработчик для dropdown меню
            const dropdownToggle = document.querySelector('.dropdown-toggle');
            const dropdown = document.querySelector('.dropdown');

            if (dropdownToggle && dropdown) {
                dropdownToggle.addEventListener('click', function(e) {
                    e.preventDefault();
                    e.stopPropagation();
                    dropdown.classList.toggle('active');
                });

                // Закрытие dropdown при клике вне его
                document.addEventListener('click', function(e) {
                    if (!dropdown.contains(e.target)) {
                        dropdown.classList.remove('active');
                    }
                });
            }

            // Анимация hover эффектов для meta-item
            document.querySelectorAll('.meta-item').forEach((item) => {
                item.addEventListener('mouseenter', function() {
                    this.style.background = 'linear-gradient(135deg, #f8fafc, #e2e8f0)';
                    this.style.transform = 'translateX(10px)';
                    this.style.paddingLeft = '1rem';
                    this.style.borderRadius = '8px';
                });

                item.addEventListener('mouseleave', function() {
                    this.style.background = 'transparent';
                    this.style.transform = 'translateX(0)';
                    this.style.paddingLeft = '0';
                    this.style.borderRadius = '0';
                });
            });
        });

        function animateCounter(element, target) {
            if (isNaN(target)) return;

            let current = 0;
            const increment = target / 100;
            const timer = setInterval(() => {
                current += increment;
                if (current >= target) {
                    element.textContent = target.toLocaleString();
                    clearInterval(timer);
                } else {
                    element.textContent = Math.floor(current).toLocaleString();
                }
            }, 20);
        }
    </script>
</@main.layout>
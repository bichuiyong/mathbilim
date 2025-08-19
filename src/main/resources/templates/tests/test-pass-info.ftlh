<#import "../layout.ftlh" as main>
<@main.layout>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #ffffff;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .result-header {
            text-align: center;
            margin-bottom: 3rem;
            animation: fadeInDown 0.5s ease forwards;
        }

        @keyframes fadeInDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .stat-card {
            border-radius: 15px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            background-color: #ffffff;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .stat-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.12);
        }

        .progress {
            height: 20px;
            border-radius: 10px;
            overflow: hidden;
            margin-top: 0.5rem;
            background-color: #d6e4ff;
        }

        .progress-bar {
            width: 0%;
            transition: width 0.8s ease;
        }

        .category-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.5rem 0;
            border-bottom: 1px solid #cfdffe;
            opacity: 0;
            transform: translateX(-20px);
            transition: transform 0.5s ease, opacity 0.5s ease;
        }


        .category-row:last-child {
            border-bottom: none;
        }

        .btn-repeat {
            padding: 0.75rem 2.5rem;
            font-size: 1.2rem;
            transition: transform 0.15s ease;
        }

        .btn-repeat:hover {
            transform: scale(1.05);
        }

        .bi {
            color: #6ea8ff;
        }

        .custom-btn {
            display: inline-block;
            padding: 0.5rem 1rem;
            margin: 0 0.3rem;
            font-size: 1.1rem;
            font-weight: 500;
            color: #fff;
            background-color: #388dff;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
            outline: none;
            border: none;
            cursor: pointer; /
        }

        .custom-btn:hover {
            background-color: #2878e6;
            color: #fff;
            text-decoration: none;
        }

        .custom-btn.excel {
            background-color: #28a745;
        }

        .custom-btn.excel:hover {
            background-color: #1e7e34;
        }
    </style>

    <div class="container py-5">
        <div class="result-header">
            <h1>Результаты теста: ${result.testName!"Тест"}</h1>
            <p class="lead">Вы прошли тест ${result.finished!"??.??.????"} и получили следующие результаты:</p>
        </div>

        <div class="row mb-4">
            <div class="col-md-4">
                <div class="stat-card text-center">
                    <i class="bi bi-list-task fs-2 mb-2"></i>
                    <h5>Общее количество вопросов</h5>
                    <p class="fs-4 fw-bold" id="totalQuestions">${result.questionCount}</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card text-center">
                    <i class="bi bi-check2-circle fs-2 mb-2"></i>
                    <h5>Правильных ответов</h5>
                    <p class="fs-4 fw-bold">
                        <span id="correctAnswers">${result.correctAnswersCount} / ${result.questionCount}</span>
                    </p>
                    <div class="progress">
                        <div class="progress-bar bg-primary" id="correctProgress" style="width:0%"></div>
                    </div>
                    <small id="correctPercent">${result.totalPercentage?string["0.##"]}%</small>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card text-center">
                    <i class="bi bi-star-fill fs-2 mb-2"></i>
                    <h5>Общий балл</h5>
                    <p class="fs-4 fw-bold" id="totalScore">${result.totalScoreCount} / ${result.maxScoreCount}</p>
                </div>
            </div>
        </div>

        <div class="stat-card">
            <h5 class="mb-3">Результаты по категориям вопросов</h5>

            <#list result.topicResultDtoList as topic>
                <div class="category-row"
                     data-correct="${topic.totalScoreCount?int}"
                     data-total="${topic.maxScoreCount?int}">
                    <span>${topic.topicName}</span>
                    <span>${topic.totalScoreCount?int} / ${topic.maxScoreCount?int}
                    (<span class="category-percent">${topic.percentage?string["0.##"]}%</span>)</span>
                    <div class="progress" style="width: 50%;">
                        <div class="progress-bar bg-info" style="width:${topic.percentage?int}%"></div>
                    </div>
                </div>
            </#list>
        </div>

        <div class="text-center mt-4">
            <a href="/tests/${result.testId}" class="custom-btn">Назад</a>
            <a href="/tests/${result.testId}/pass" class="custom-btn">Пройти тест снова</a>
            <a href="/api/excel/download/result?attemptId=${result.attemptId}" class="custom-btn excel">Excel</a>
        </div>
    </div>

    <script>
        window.addEventListener('DOMContentLoaded', () => {
            const correctBar = document.getElementById('correctProgress');
            const totalPercent = Math.round(${result.totalPercentage?string["0"]});

            setTimeout(() => {
                correctBar.style.width = totalPercent + '%';
            }, 100);
        });

        window.addEventListener('DOMContentLoaded', () => {
            const categories = document.querySelectorAll('.category-row');
            categories.forEach((row, index) => {
                const bar = row.querySelector('.progress-bar');
                const correct = parseInt(row.getAttribute('data-correct'));
                const total = parseInt(row.getAttribute('data-total'));
                const percent = Math.round((correct / total) * 100);

                const percentElem = row.querySelector('.category-percent');
                let currentPercent = 0;

                setTimeout(() => {
                    row.style.opacity = 1;
                    row.style.transform = 'translateX(0)';
                    bar.style.width = percent + '%';

                    const interval = setInterval(() => {
                        currentPercent++;
                        percentElem.textContent = currentPercent + '%';
                        if (currentPercent >= percent) clearInterval(interval);
                    }, 8);
                }, index * 150);
            });
        });
    </script>
</@main.layout>

<#import "../layout.ftlh" as main>
<@main.layout>
    <style>
        h3 {
            text-align: center;
            margin-bottom: 5px;
        }

        #pdf-container {
            border: 1px solid #ccc;
            background: white;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            margin: 20px auto;
            border-radius: 8px;
            overflow: hidden;
        }

        canvas {
            display: block;
            margin: auto;
        }

        .nav-buttons {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin-bottom: 25px;
        }

        button {
            background: #007bff;
            color: white;
            border: none;
            padding: 8px 16px;
            font-size: 14px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.2s;
        }

        button:hover {
            background: #0056b3;
        }

        .answers-wrapper {
            display: flex;
            justify-content: center;
            gap: 30px;
            margin-top: 20px;
            flex-wrap: nowrap;
            overflow-x: auto;
            padding-bottom: 10px;
        }

        .column {
            display: flex;
            flex-direction: column;
            align-items: center;
            background: white;
            border-radius: 8px;
            padding: 10px 15px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            min-width: 180px;
        }

        .col-header {
            display: grid;
            grid-template-columns: 30px repeat(4, 30px);
            font-weight: bold;
            margin-bottom: 8px;
            color: #444;
            text-align: center;
        }

        .answer-row {
            display: grid;
            grid-template-columns: 30px repeat(4, 30px);
            align-items: center;
            margin-bottom: 5px;
            text-align: center;
            position: relative;
        }

        .number {
            font-weight: bold;
            font-size: 14px;
        }

        input[type="radio"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
            position: relative;
        }

        #timer-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 20px 0;
        }

        #timer-label {
            font-size: 16px;
            color: #333;
            margin-bottom: 6px;
            font-weight: 500;
        }

        #timer {
            font-size: 22px;
            font-weight: bold;
            color: #0d6efd;
            background: #fff;
            border: 2px solid #0d6efd;
            padding: 10px 25px;
            border-radius: 10px;
        }

        .frozen {
            opacity: 0.6;
            pointer-events: none;
        }

        .past {
            position: relative;
        }

        .past::after {
            content: '';
            display: inline-block;
            width: 12px;
            height: 12px;
            background: #0d6efd;
            border-radius: 50%;
            position: absolute;
            top: 3px;
            left: 3px;
        }
    </style>

    <h3 class="text-center mt-5">${test.name}</h3>
    <p class="text-center">Количество заданий: ${test.questionCount} — Время: ${test.timeLimit} минут</p>

    <div id="timer-container">
        <div id="timer-label">⏳ Осталось времени:</div>
        <div id="timer"></div>
    </div>

    <div id="pdf-container">
        <canvas id="pdf-render"></canvas>
    </div>

    <div class="nav-buttons">
        <button id="prev-page">← Назад</button>
        <span>Страница: <span id="page-num"></span> / <span id="page-count"></span></span>
        <button id="next-page">Вперед →</button>
    </div>

    <h3 class="text-center">${test.name} - бланк ответов</h3>
    <div>
        <#assign totalQuestions = test.questionCount>
        <#assign perColumn = 5>
        <#assign columns = ((totalQuestions - 1) / perColumn + 1)>
        <#assign index = 0>
        <form action="/tests/${test.id}/pass" method="post" class="answers-wrapper" id="sendAnswersForm">
            <@main.csrf/>
            <#list 0..(columns-1) as col>
                <div class="column">
                    <div class="col-header">
                        <div></div>
                        <div>A</div>
                        <div>B</div>
                        <div>C</div>
                        <div>D</div>
                    </div>
                    <#list test.questionDtoList as question>
                        <#assign questionNum = question.numberOrder>
                        <#if questionNum <= totalQuestions>
                            <div class="answer-row" data-question="${questionNum}">
                                <div class="number">${questionNum}</div>
                                <input type="hidden" name="attemptAnswerDtoList[${index}].questionNumber"
                                       value="${questionNum}">
                                <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="A"
                                            data-page="${question.testPageNumber}"></div>
                                <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="B"
                                            data-page="${question.testPageNumber}"></div>
                                <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="C"
                                            data-page="${question.testPageNumber}"></div>
                                <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="D"
                                            data-page="${question.testPageNumber}"></div>
                                <#assign index = index + 1>
                            </div>
                        </#if>
                    </#list>
                </div>
            </#list>
        </form>
        <div class="text-center mt-5">
            <button type="submit" class="btn btn-primary" form="sendAnswersForm">Завершить</button>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.14.305/pdf.min.js"></script>
    <script>
        const url = '${test.file.s3Link?js_string}';
        let pdfDoc = null, pageNum = 1, pageIsRendering = false, pageNumIsPending = null;
        const qualityBoost = 2;
        const canvas = document.getElementById('pdf-render'), ctx = canvas.getContext('2d');
        let timeOver = false;

        const renderPage = num => {
            pageIsRendering = true;
            pdfDoc.getPage(num).then(page => {
                const windowHeight = window.innerHeight - 20;
                const viewportTemp = page.getViewport({scale: 1});
                const scale = windowHeight / viewportTemp.height;
                const viewport = page.getViewport({scale});
                canvas.width = viewport.width * qualityBoost;
                canvas.height = viewport.height * qualityBoost;
                canvas.style.width = viewport.width + 'px';
                canvas.style.height = viewport.height + 'px';
                const renderContext = {
                    canvasContext: ctx,
                    viewport,
                    transform: [qualityBoost, 0, 0, qualityBoost, 0, 0]
                };
                page.render(renderContext).promise.then(() => {
                    pageIsRendering = false;
                    if (pageNumIsPending !== null) {
                        renderPage(pageNumIsPending);
                        pageNumIsPending = null;
                    }
                });
                document.getElementById('page-num').textContent = num;
                updateRadioAvailability(num);
            });
        };

        const queueRenderPage = num => {
            if (pageIsRendering) {
                pageNumIsPending = num
            } else {
                renderPage(num);
            }
        };
        const showPrevPage = () => {
            if (pageNum <= 1) return;
            pageNum--;
            queueRenderPage(pageNum);
        };
        const showNextPage = () => {
            if (pageNum >= pdfDoc.numPages) return;
            pageNum++;
            queueRenderPage(pageNum);
        };
        pdfjsLib.getDocument(url).promise.then(pdfDoc_ => {
            pdfDoc = pdfDoc_;
            document.getElementById('page-count').textContent = pdfDoc.numPages;
            renderPage(pageNum);
        });
        document.getElementById('prev-page').addEventListener('click', showPrevPage);
        document.getElementById('next-page').addEventListener('click', showNextPage);
        window.addEventListener('resize', () => renderPage(pageNum));

        const form = document.getElementById("sendAnswersForm");
        const timerElement = document.getElementById("timer");
        let timeLeft = 10;

        const chosenAnswers = {};
        const allRadios = form.querySelectorAll('input[type="radio"]');

        allRadios.forEach(radio => {
            <#noparse>
            radio.addEventListener('click', e => {
                const question = radio.name;

                if (!chosenAnswers[question]) {
                    chosenAnswers[question] = radio.value;
                } else {

                    const oldRadio = form.querySelector(`input[name="${question}"][value="${chosenAnswers[question]}"]`);
                    if (oldRadio) oldRadio.classList.add('past');
                    chosenAnswers[question] = radio.value;
                }
            });
            </#noparse>
        });

        function updateRadioAvailability(currentPage) {
            allRadios.forEach(r => {
                if (timeOver) {
                    r.classList.add('frozen');
                } else {
                    if (parseInt(r.dataset.page) === currentPage) {
                        r.classList.remove('frozen');
                    } else {
                        r.classList.add('frozen');
                    }
                }
            });
        }

        <#noparse>
        const timer = setInterval(() => {
            let minutes = Math.floor(timeLeft / 60);
            let seconds = timeLeft % 60;
            timerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

            if (timeLeft <= 0) {
                clearInterval(timer);
                timeOver = true;
                [...form.elements].forEach(el => {
                    if (el.type !== "submit" && el.name !== "_csrf") {
                        el.classList.add('frozen');
                        if (el.tagName === "INPUT" && (el.type === "radio" || el.type === "checkbox")) {
                            el.addEventListener("click", e => e.preventDefault());
                        }
                        if (el.tagName === "INPUT" || el.tagName === "TEXTAREA") {
                            el.readOnly = true;
                        }
                    }
                });
                timerElement.textContent = "⏰ Время вышло!";
            }
            timeLeft--;
        }, 1000);
        </#noparse>
    </script>

</@main.layout>

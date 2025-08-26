<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
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
            width: 100%;
            max-width: 100%;
        }

        .nav-buttons {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin-bottom: 25px;
            flex-wrap: wrap;
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
            flex-direction: row;
            align-items: center;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
            padding: 0 10px;
        }

        .column {
            display: flex;
            flex-direction: column;
            align-items: center;
            background: white;
            border-radius: 8px;
            padding: 10px 15px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 300px;
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
            opacity: 0.1;
            pointer-events: none;
            filter: grayscale(90%);
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

        input[type="radio"]:checked {
            background-color: #0d6efd;
            border-radius: 50%;
        }

        input[type="radio"].frozen {
            opacity: 0.1;
            cursor: not-allowed;
            filter: grayscale(90%);
        }

        input[type="text"] {
            width: 100%;
            padding: 5px 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="text"]:read-only {
            background-color: #e8ecef;
            cursor: not-allowed;
            opacity: 0.3;
            filter: grayscale(80%);
        }

        @media (max-width: 768px) {
            .answers-wrapper {
                gap: 15px;
                padding: 0 5px;
            }

            .column {
                display: none;
                max-width: 100%;
            }

            .column.active {
                display: flex;
            }

            .answer-row {
                display: none;
            }

            .answer-row.active {
                display: grid;
            }

            .nav-buttons {
                gap: 10px;
                padding: 0 10px;
            }

            button {
                padding: 6px 12px;
                font-size: 13px;
            }

            #pdf-container {
                margin: 10px;
            }

            .answers-wrapper {
                display: flex;
                flex-direction: column;
            }
        }
    </style>

    <h3 class="text-center mt-5">${test.name}</h3>
    <p class="text-center"><@spring.message"test.pass.count"/> ${test.questionCount}
        <#if test.hasLimit> — <@spring.message"test.pass.time"/> ${test.timeLimit} <@spring.message"test.pass.min"/></p>
</#if>
    <#if test.hasLimit>
        <div id="timer-container">
            <div id="timer-label">⏳ <@spring.message"test.pass.left"/></div>
            <div id="timer"></div>
        </div>
    </#if>

    <div id="pdf-container">
        <canvas id="pdf-render"></canvas>
    </div>

    <div class="nav-buttons">
        <button id="prev-page">← <@spring.message"test.pass.back"/></button>
        <span><@spring.message"test.pass.page"/> <span id="page-num"></span> / <span id="page-count"></span></span>
        <button id="next-page"><@spring.message"test.pass.next"/> →</button>
    </div>

    <h3 class="text-center">${test.name} - <@spring.message"test.pass.results"/></h3>
    <div>
        <#assign totalQuestions = test.questionCount>
        <#assign perColumn = 5>
        <#assign columns = ((totalQuestions - 1) / perColumn + 1)?floor>
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
                        <#if questionNum <= totalQuestions && (questionNum > (col * perColumn)) && (questionNum <= ((col + 1) * perColumn))>
                            <div class="answer-row" data-question="${questionNum}" data-page="${question.testPageNumber}">
                                <div class="number">${questionNum}</div>
                                <input type="hidden" name="attemptAnswerDtoList[${index}].questionNumber"
                                       value="${questionNum}">
                                <#if question.textFormat?? && question.textFormat>
                                    <input type="text"
                                           name="attemptAnswerDtoList[${index}].chosenAnswer"
                                           data-page="${question.testPageNumber}"
                                           style="grid-column: span 4; width: 100%; padding: 4px;">
                                <#else>
                                    <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="A"
                                                data-page="${question.testPageNumber}"></div>
                                    <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="B"
                                                data-page="${question.testPageNumber}"></div>
                                    <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="C"
                                                data-page="${question.testPageNumber}"></div>
                                    <div><input type="radio" name="attemptAnswerDtoList[${index}].chosenAnswer" value="D"
                                                data-page="${question.testPageNumber}"></div>
                                </#if>
                                <#assign index = index + 1>
                            </div>
                        </#if>
                    </#list>
                </div>
            </#list>
        </form>
        <div class="text-center mt-5">
            <button type="submit" class="btn btn-primary" form="sendAnswersForm"><@spring.message"test.pass.finish"/></button>
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
        const chosenAnswers = {};
        const allRadios = form.querySelectorAll('input[type="radio"]');

        <#noparse>
        const answerClicks = {};

        allRadios.forEach(radio => {
            radio.addEventListener('click', e => {
                const question = radio.name;

                if (!answerClicks[question]) answerClicks[question] = 0;

                if (answerClicks[question] < 2) {
                    chosenAnswers[question] = radio.value;
                    answerClicks[question]++;

                    if (answerClicks[question] === 2) {
                        const radiosInGroup = form.querySelectorAll(`input[name="${question}"]`);
                        radiosInGroup.forEach(r => {
                            r.disabled = true;
                            r.classList.add("frozen");
                        });
                    }
                } else {
                    e.preventDefault();
                }
            });
        });
        </#noparse>

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

            const textInputs = form.querySelectorAll('input[type="text"][data-page]');
            textInputs.forEach(inp => {
                if (timeOver) {
                    inp.readOnly = true;
                } else {
                    inp.readOnly = parseInt(inp.dataset.page) !== currentPage;
                }
            });

            const answerRows = form.querySelectorAll('.answer-row');
            answerRows.forEach(row => {
                if (window.innerWidth <= 768) {
                    if (parseInt(row.dataset.page) === currentPage) {
                        row.classList.add('active');
                    } else {
                        row.classList.remove('active');
                    }
                } else {
                    row.classList.add('active');
                }
            });

            const columns = form.querySelectorAll('.column');
            columns.forEach(column => {
                const activeRows = column.querySelectorAll('.answer-row.active');
                if (window.innerWidth <= 768) {
                    if (activeRows.length > 0) {
                        column.classList.add('active');
                    } else {
                        column.classList.remove('active');
                    }
                } else {
                    column.classList.add('active');
                }
            });
        }

        <#if test.hasLimit>
        const timerElement = document.getElementById("timer");
        let timeLeft = ${test.timeLimit} * 60;
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
        </#if>
    </script>
</@main.layout>
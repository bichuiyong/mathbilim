<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <style>
        :root {
            --tc-primary: #3b82f6;
            --tc-primary-light: #60a5fa;
            --tc-primary-dark: #2563eb;
            --tc-bg: #f1f5f9;
            --tc-card: #ffffff;
            --tc-text: #1e293b;
            --tc-text-light: #6b7280;
            --tc-border: #e5e7eb;
            --tc-error: #ef4444;
            --tc-highlight-bg: #eff6ff;
            --tc-highlight-border: #bfdbfe;
            --tc-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -2px rgba(0, 0, 0, 0.05);
            --tc-transition: all 0.2s ease-in-out;
        }

        .tc-page-wrapper {
            background: linear-gradient(180deg, var(--tc-bg) 0%, #e5e7eb 100%);
            color: var(--tc-text);
            line-height: 1.6;
            padding: 1rem;
            min-height: 100vh;
            font-family: 'Inter', system-ui, sans-serif;
            margin: 0;
        }

        .tc-container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: var(--tc-card);
            border-radius: 1.25rem;
            box-shadow: var(--tc-shadow);
            overflow: hidden;
        }

        .tc-header {
            padding: 1.5rem;
            border-bottom: 1px solid var(--tc-border);
            background: linear-gradient(90deg, #f8fafc, #ffffff);
        }

        .tc-title {
            font-size: 1.75rem;
            font-weight: 700;
            color: var(--tc-primary);
        }

        .tc-form-content {
            padding: 1.5rem;
        }

        .tc-form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 1.5rem;
        }

        @media (max-width: 768px) {
            .tc-page-wrapper { padding: 0.5rem; }
            .tc-container { border-radius: 0.75rem; }
            .tc-header { padding: 1rem; }
            .tc-title { font-size: 1.5rem; }
            .tc-form-content { padding: 1rem; }
            .tc-form-grid { grid-template-columns: 1fr; gap: 1rem; }
            .tc-form-group.tc-full-width { grid-column: span 1; }
        }

        @media (max-width: 480px) {
            .tc-page-wrapper { padding: 0.25rem; }
            .tc-container { border-radius: 0.5rem; margin: 0; }
            .tc-header { padding: 0.75rem; }
            .tc-title { font-size: 1.25rem; }
            .tc-form-content { padding: 0.75rem; }
        }

        .tc-form-group {
            margin-bottom: 1.75rem;
        }

        .tc-form-group.tc-full-width {
            grid-column: span 2;
        }

        @media (max-width: 768px) { .tc-form-group { margin-bottom: 1.25rem; } }
        @media (max-width: 480px) { .tc-form-group { margin-bottom: 1rem; } }

        .tc-label {
            display: block;
            margin-bottom: 0.5rem;
            font-size: 0.9rem;
            font-weight: 600;
            color: var(--tc-text);
            letter-spacing: -0.01em;
        }

        .tc-label.tc-required::after {
            content: '*';
            color: var(--tc-error);
            margin-left: 0.3rem;
        }

        .tc-input,
        .tc-select,
        .tc-textarea,
        .tc-file-input {
            width: 100%;
            padding: 0.875rem 1.25rem;
            border: 1px solid var(--tc-border);
            border-radius: 0.75rem;
            font-size: 0.9rem;
            transition: var(--tc-transition);
            background-color: #fff;
            box-sizing: border-box;
        }

        .tc-file-input { padding: 0.5rem 1.25rem; }

        @media (max-width: 768px) {
            .tc-input, .tc-select, .tc-textarea, .tc-file-input { padding: 0.75rem 1rem; font-size: 0.875rem; }
            .tc-file-input { padding: 0.5rem 1rem; }
        }

        @media (max-width: 480px) {
            .tc-input, .tc-select, .tc-textarea, .tc-file-input { padding: 0.625rem 0.875rem; font-size: 0.875rem; border-radius: 0.5rem; }
            .tc-file-input { padding: 0.375rem 0.875rem; }
        }

        .tc-input:focus,
        .tc-select:focus,
        .tc-textarea:focus,
        .tc-file-input:focus {
            outline: none;
            border-color: var(--tc-primary);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
        }

        .tc-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.875rem 1.5rem;
            border-radius: 0.75rem;
            font-size: 0.9rem;
            font-weight: 500;
            cursor: pointer;
            transition: var(--tc-transition);
            border: none;
        }

        @media (max-width: 768px) { .tc-btn { padding: 0.75rem 1.25rem; font-size: 0.875rem; } }
        @media (max-width: 480px) { .tc-btn { padding: 0.625rem 1rem; font-size: 0.875rem; border-radius: 0.5rem; width: 100%; margin-bottom: 0.5rem; } }

        .tc-btn-primary {
            background-color: var(--tc-primary);
            color: white;
        }

        .tc-btn-primary:hover {
            background-color: var(--tc-primary-dark);
            transform: translateY(-1px);
            box-shadow: var(--tc-shadow);
        }

        .tc-btn-outline {
            background-color: transparent;
            border: 1px solid var(--tc-border);
            color: var(--tc-text);
        }

        .tc-btn-outline:hover {
            background-color: #f8fafc;
            border-color: var(--tc-primary-light);
            color: var(--tc-primary);
        }

        .tc-questions-container {
            margin-top: 2rem;
            border-top: 1px solid var(--tc-border);
            padding-top: 2rem;
        }

        @media (max-width: 768px) { .tc-questions-container { margin-top: 1.5rem; padding-top: 1.5rem; } }
        @media (max-width: 480px) { .tc-questions-container { margin-top: 1rem; padding-top: 1rem; } }

        .tc-question-card {
            padding: 1rem;
            border: 1px solid var(--tc-border);
            border-radius: 0.75rem;
            margin-bottom: 1rem;
            background-color: white;
            transition: var(--tc-transition);
        }

        .tc-question-card:hover { box-shadow: var(--tc-shadow); }

        .tc-question-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }

        .tc-question-number {
            font-weight: 600;
            color: var(--tc-primary-dark);
            font-size: 0.9rem;
        }

        .tc-remove-question {
            color: var(--tc-error);
            background: none;
            border: none;
            cursor: pointer;
            font-size: 1.2rem;
            padding: 0.25rem;
            border-radius: 0.25rem;
            transition: var(--tc-transition);
        }

        .tc-remove-question:hover {
            background-color: rgba(239, 68, 68, 0.1);
        }

        .tc-question-inline {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr auto;
            gap: 1rem;
            align-items: end;
        }

        @media (max-width: 768px) {
            .tc-question-inline { grid-template-columns: 1fr 1fr; gap: 0.75rem; }
            .tc-compact-group:nth-child(3), .tc-compact-group:nth-child(4) { grid-column: span 2; }
        }

        @media (max-width: 480px) {
            .tc-question-inline { grid-template-columns: 1fr; gap: 0.5rem; }
            .tc-compact-group, .tc-answer-options, .tc-text-answer { grid-column: span 1 !important; }
        }

        .tc-compact-group {
            display: flex;
            flex-direction: column;
        }

        .tc-compact-group label {
            font-size: 0.8rem;
            margin-bottom: 0.25rem;
            color: var(--tc-text-light);
        }

        .tc-compact-group input,
        .tc-compact-group select {
            padding: 0.5rem;
            font-size: 0.85rem;
        }

        @media (max-width: 768px) { .tc-compact-group input, .tc-compact-group select { padding: 0.5rem 0.75rem; font-size: 0.8rem; } }
        @media (max-width: 480px) { .tc-compact-group input, .tc-compact-group select { padding: 0.5rem 0.625rem; font-size: 0.8rem; } }

        .tc-checkbox-label {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.85rem;
            cursor: pointer;
            padding: 0.5rem;
            border-radius: 0.5rem;
            transition: var(--tc-transition);
        }

        .tc-checkbox-label:hover { background: rgba(59, 130, 246, 0.1); }

        .tc-checkbox-label input[type="checkbox"] {
            width: 1rem;
            height: 1rem;
            accent-color: var(--tc-primary);
        }

        .tc-answer-options {
            grid-column: span 4;
            margin-top: 0.5rem;
            padding: 1rem;
            background: #f8fafc;
            border-radius: 0.5rem;
            border: 1px solid var(--tc-border);
        }

        .tc-option-row {
            display: flex;
            align-items: center;
            margin-bottom: 0.5rem;
        }

        .tc-option-row:last-child { margin-bottom: 0; }

        .tc-option-row label {
            margin-left: 0.5rem;
            font-size: 0.85rem;
        }

        .tc-option-row input[type="radio"] { accent-color: var(--tc-primary); }

        .tc-text-answer {
            grid-column: span 4;
            margin-top: 0.5rem;
        }

        .tc-hidden { display: none; }

        .tc-file-info {
            margin-top: 0.5rem;
            font-size: 0.85rem;
            color: var(--tc-text-light);
        }

        .tc-page-input:invalid {
            border-color: var(--tc-error);
            background-color: rgba(239, 68, 68, 0.05);
        }

        .tc-page-input:invalid+.tc-page-error { display: block; }

        .tc-page-error {
            display: none;
            color: var(--tc-error);
            font-size: 0.75rem;
            margin-top: 0.25rem;
        }

        .tc-submit-btn {
            padding: 1rem 2rem;
            font-size: 1rem;
        }

        @media (max-width: 480px) { .tc-submit-btn { padding: 0.875rem 1.5rem; font-size: 0.9rem; } }
    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.16.105/pdf.min.js"></script>
    <div class="tc-page-wrapper">
        <div class="tc-container">
            <div class="tc-header">
                <h1 class="tc-title"><@spring.message "test.create.title"/></h1>
            </div>

            <div class="tc-form-content">
                <#if errors??>
                    <div class="tc-page-error">
                        <#list errors as error>
                            ${error.defaultMessage}<br>
                        </#list>
                    </div>
                </#if>
                <form id="testForm" action="/tests/create" method="POST" enctype="multipart/form-data">
                    <div class="tc-form-grid">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="tc-form-group">
                            <label for="testName" class="tc-label tc-required"><@spring.message "test.create.name"/></label>
                            <input type="text" id="testName" name="name" class="tc-input" required maxlength="50">
                            <div class="tc-page-error" id="testNameError"><@spring.message "test.create.name.error"/></div>
                        </div>

                        <div class="tc-form-group">
                            <label for="description" class="tc-label tc-required"><@spring.message "test.create.description"/></label>
                            <input type="text" id="description" name="description" class="tc-input" required maxlength="100">
                            <div class="tc-page-error" id="descriptionError"><@spring.message "test.create.description.error"/></div>
                        </div>

                        <div class="tc-form-group">
                            <label for="timeLimit" class="tc-label tc-required"><@spring.message "test.create.timeLimit"/></label>
                            <input type="number" id="timeLimit" name="timeLimit" class="tc-input" min="1">
                            <label class="tc-checkbox-label">
                                <input type="hidden" name="_hasLimit" value="false">
                                <input type="checkbox" id="hasLimit" name="hasLimit" value="true" checked onchange="testCreator.toggleTimeLimit()">
                                <@spring.message "test.create.timeLimit.set"/>
                            </label>
                        </div>

                        <div class="tc-form-group tc-full-width">
                            <label for="pdfFile" class="tc-label tc-required"><@spring.message "test.create.file"/></label>
                            <input type="file" id="pdfFile" name="file" class="tc-file-input" accept=".pdf" required>
                            <div class="tc-file-info tc-hidden" id="fileInfo"><@spring.message "test.create.file.info"/></div>
                        </div>

                        <div class="tc-form-group">
                            <label for="pageCount" class="tc-label"><@spring.message "test.create.pageCount"/></label>
                            <input type="number" id="pageCount" class="tc-input" readonly>
                        </div>
                    </div>

                    <div class="tc-questions-container">
                        <h2><@spring.message "test.create.questions"/></h2>
                        <div id="questionsList"></div>
                        <div class="tc-page-error" id="questionsError"><@spring.message "test.create.questions.error"/></div>

                        <button type="button" id="addQuestionBtn" class="tc-btn tc-btn-primary">
                            <@spring.message "test.create.questions.add"/>
                        </button>
                    </div>

                    <div class="tc-form-group tc-full-width" style="margin-top: 2rem;">
                        <button type="submit" class="tc-btn tc-btn-primary tc-submit-btn">
                            <@spring.message "test.create.save"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.16.105/pdf.worker.min.js';
        const topics = [
            <#list topics as topic>
            { id: ${topic.id}, name: "${topic.name}" }<#if topic_has_next>,</#if>
            </#list>
        ];
        const i18n = {
            fileSelected: "<@spring.message 'test.file.selected'/>",
            fileError: "<@spring.message 'test.file.error'/>",
            question: "<@spring.message 'test.question'/>",
            pageLabel: "<@spring.message 'test.question.page'/>",
            pageError: "<@spring.message 'test.question.page.error'/>",
            coefficient: "<@spring.message 'test.question.coefficient'/>",
            topic: "<@spring.message 'test.question.topic'/>",
            selectTopic: "<@spring.message 'test.question.topic.select'/>",
            textAnswer: "<@spring.message 'test.question.textAnswer'/>",
            correct: "<@spring.message 'test.question.correct'/>",
            optionA: "<@spring.message 'test.question.optionA'/>",
            optionB: "<@spring.message 'test.question.optionB'/>",
            optionC: "<@spring.message 'test.question.optionC'/>",
            optionD: "<@spring.message 'test.question.optionD'/>",
            answerLabel: "<@spring.message 'test.question.answer.label'/>",
            answerPlaceholder: "<@spring.message 'test.question.answer.placeholder'/>",
            removeConfirm: "<@spring.message 'test.question.remove.confirm'/>",
            noQuestions: "<@spring.message 'test.question.error'/>",
            textAnswerRequired: "<@spring.message 'test.question.textAnswer.required'/>"
        };
        <#noparse>
        class TestCreator {
            constructor() {
                this.questionCount = 0;
                this.questionsList = document.getElementById('questionsList');
                this.addQuestionBtn = document.getElementById('addQuestionBtn');
                this.pdfFileInput = document.getElementById('pdfFile');
                this.pageCountInput = document.getElementById('pageCount');
                this.fileInfo = document.getElementById('fileInfo');
                this.testNameInput = document.getElementById('testName');
                this.timeLimitInput = document.getElementById('timeLimit');
                this.hasLimitCheckbox = document.getElementById('hasLimit');
                this.form = document.getElementById('testForm');

                this.init();
            }

            init() {
                this.addQuestionBtn.addEventListener('click', () => this.addQuestion());
                this.pdfFileInput.addEventListener('change', (e) => this.handleFileUpload(e));
                this.testNameInput.addEventListener('input', () => this.validateTestName());
                this.hasLimitCheckbox.addEventListener('change', () => this.toggleTimeLimit());
                this.form.addEventListener('submit', (e) => this.handleSubmit(e));
                this.toggleTimeLimit();
            }

            toggleTimeLimit() {
                if (this.hasLimitCheckbox.checked) {
                    this.timeLimitInput.disabled = false;
                    this.timeLimitInput.required = true;
                    if (!this.timeLimitInput.value) {
                        this.timeLimitInput.value = 1;
                    }
                } else {
                    this.timeLimitInput.disabled = true;
                    this.timeLimitInput.required = false;
                    this.timeLimitInput.value = '';
                }
            }

            validateTestName() {
                const testName = this.testNameInput.value;
                const error = document.getElementById('testNameError');
                if (testName.length < 1 || testName.length > 100) {
                    this.testNameInput.setCustomValidity('Invalid');
                    error.style.display = 'block';
                } else {
                    this.testNameInput.setCustomValidity('');
                    error.style.display = 'none';
                }
            }

            async handleFileUpload(e) {
                if (e.target.files.length > 0) {
                    const file = e.target.files[0];
                    this.fileInfo.textContent = `${i18n.fileSelected} ${file.name}`;
                    this.fileInfo.classList.remove('tc-hidden');

                    try {
                        const arrayBuffer = await file.arrayBuffer();
                        const pdf = await pdfjsLib.getDocument(arrayBuffer).promise;
                        const pageCount = pdf.numPages;
                        this.pageCountInput.value = pageCount;
                        this.updatePageInputsMax(pageCount);
                    } catch (error) {
                        console.error('Ошибка при чтении PDF:', error);
                        this.fileInfo.textContent = `${i18n.fileError} ${file.name}`;
                        this.pageCountInput.value = '';
                        this.updatePageInputsMax(999);
                    }
                } else {
                    this.fileInfo.classList.add('tc-hidden');
                    this.pageCountInput.value = '';
                    this.updatePageInputsMax(999);
                }
            }

            addQuestion() {
                const questionId = `question_${this.questionCount}`;
                const index = this.questionCount;

                const questionCard = document.createElement('div');
                questionCard.className = 'tc-question-card';
                questionCard.id = questionId;

                questionCard.innerHTML = this.getQuestionHTML(questionId, index);
                this.questionsList.appendChild(questionCard);

                this.questionCount++;
                this.updateQuestionNumbers();
                document.getElementById('questionsError').style.display = 'none';

                const maxPages = this.pageCountInput.value;
                if (maxPages) {
                    const newPageInput = document.getElementById(`${questionId}_page`);
                    if (newPageInput) {
                        newPageInput.setAttribute('max', maxPages);
                    }
                }

                this.toggleAnswerType(questionId, index);
            }

            getQuestionHTML(questionId, index) {
                const maxPages = this.pageCountInput.value || 999;

                return `
    <div class="tc-question-header">
        <span class="tc-question-number">${i18n.question} ${index + 1}</span>
        <button type="button" class="tc-remove-question" onclick="testCreator.removeQuestion('${questionId}')">
            ×
        </button>
    </div>

    <div class="tc-question-inline">
        <div class="tc-compact-group">
            <input type="hidden" name="questions[${index}].numberOrder" value="${index + 1}">
            <label for="${questionId}_page">${i18n.pageLabel}</label>
            <input type="number" id="${questionId}_page" name="questions[${index}].testPageNumber" class="tc-input tc-page-input" min="1" max="${maxPages}" required>
            <div class="tc-page-error">${i18n.pageError.replace('{0}', maxPages)}</div>
        </div>

        <div class="tc-compact-group">
            <label for="${questionId}_coefficient">${i18n.coefficient}</label>
            <input type="number" id="${questionId}_coefficient" name="questions[${index}].weight" class="tc-input" min="0.1" max="10" step="0.1" required value="1.0">
        </div>

        <div class="tc-compact-group">
            <label for="${questionId}_topicId">${i18n.topic}</label>
            <select id="${questionId}_topicId" name="questions[${index}].topicId" class="tc-select" required>
                <option value="">${i18n.selectTopic}</option>
                ${topics.map(topic => `<option value="${topic.id}">${topic.name}</option>`).join('')}
            </select>
        </div>

        <div class="tc-compact-group">
            <label class="tc-checkbox-label">
                <input type="checkbox" id="${questionId}_isTextFormat" name="questions[${index}].textFormat" value="true" onchange="testCreator.toggleAnswerType('${questionId}', ${index})">
                ${i18n.textAnswer}
            </label>
        </div>

        <div class="tc-answer-options" id="${questionId}_multipleOptions">
            <div style="font-size: 0.8rem; margin-bottom: 0.5rem; color: var(--tc-text-light);">${i18n.correct}</div>
            <div class="tc-option-row">
                <input type="radio" id="${questionId}_option_a" name="questions[${index}].correctAnswer" value="A" checked required>
                <label for="${questionId}_option_a">${i18n.optionA}</label>
            </div>
            <div class="tc-option-row">
                <input type="radio" id="${questionId}_option_b" name="questions[${index}].correctAnswer" value="B">
                <label for="${questionId}_option_b">${i18n.optionB}</label>
            </div>
            <div class="tc-option-row">
                <input type="radio" id="${questionId}_option_c" name="questions[${index}].correctAnswer" value="C">
                <label for="${questionId}_option_c">${i18n.optionC}</label>
            </div>
            <div class="tc-option-row">
                <input type="radio" id="${questionId}_option_d" name="questions[${index}].correctAnswer" value="D">
                <label for="${questionId}_option_d">${i18n.optionD}</label>
            </div>
        </div>

        <div class="tc-text-answer tc-hidden" id="${questionId}_textAnswer">
            <label for="${questionId}_correctAnswer" style="font-size: 0.8rem; color: var(--tc-text-light);">${i18n.answerLabel}</label>
            <input type="text" id="${questionId}_correctAnswer" name="questions[${index}].correctAnswer" class="tc-input" placeholder="${i18n.answerPlaceholder}" disabled>
        </div>
    </div>
    `;
            }

            toggleAnswerType(questionId, index) {
                const isTextFormat = document.getElementById(`${questionId}_isTextFormat`).checked;
                const multipleOptions = document.getElementById(`${questionId}_multipleOptions`);
                const textAnswer = document.getElementById(`${questionId}_textAnswer`);
                const correctAnswerInput = document.getElementById(`${questionId}_correctAnswer`);
                const radioInputs = document.querySelectorAll(`input[name="questions[${index}].correctAnswer"][type="radio"]`);

                if (isTextFormat) {
                    multipleOptions.classList.add('tc-hidden');
                    textAnswer.classList.remove('tc-hidden');
                    if (correctAnswerInput) {
                        correctAnswerInput.disabled = false;
                        correctAnswerInput.required = true;
                    }
                    radioInputs.forEach(radio => {
                        radio.disabled = true;
                        radio.required = false;
                    });
                } else {
                    multipleOptions.classList.remove('tc-hidden');
                    textAnswer.classList.add('tc-hidden');
                    if (correctAnswerInput) {
                        correctAnswerInput.disabled = true;
                        correctAnswerInput.required = false;
                        correctAnswerInput.value = '';
                    }
                    radioInputs.forEach(radio => {
                        radio.disabled = false;
                        radio.required = true;
                    });
                }
            }

            removeQuestion(questionId) {
                if (confirm(i18n.removeConfirm)) {
                    document.getElementById(questionId).remove();
                    this.updateQuestionNumbers();
                    this.reindexQuestions();
                }
            }

            updateQuestionNumbers() {
                const questionCards = this.questionsList.querySelectorAll('.tc-question-card');
                questionCards.forEach((card, index) => {
                    const questionNumber = card.querySelector('.tc-question-number');
                    if (questionNumber) {
                        questionNumber.textContent = `Вопрос #${index + 1}`;
                    }
                });
            }

            reindexQuestions() {
                const questionCards = this.questionsList.querySelectorAll('.tc-question-card');
                questionCards.forEach((card, index) => {
                    const questionId = card.id;
                    const inputs = card.querySelectorAll('input, select');
                    inputs.forEach(input => {
                        if (input.name && input.name.includes('questions[')) {
                            input.name = input.name.replace(/questions\[\d+\]/, `questions[${index}]`);
                        }
                    });
                    const numberOrderInput = card.querySelector(`input[name="questions[${index}].numberOrder"]`);
                    if (numberOrderInput) {
                        numberOrderInput.value = index + 1;
                    }
                    const textFormatCheckbox = card.querySelector(`input[id="${questionId}_isTextFormat"]`);
                    if (textFormatCheckbox) {
                        textFormatCheckbox.setAttribute('onchange', `testCreator.toggleAnswerType('${questionId}', ${index})`);
                    }
                });
            }

            updatePageInputsMax(maxPages) {
                const pageInputs = document.querySelectorAll('.tc-page-input');
                pageInputs.forEach(input => {
                    input.setAttribute('max', maxPages);
                    if (input.value && parseInt(input.value) > maxPages) {
                        input.value = maxPages;
                    }
                    const error = input.nextElementSibling;
                    if (error && error.classList.contains('tc-page-error')) {
                        error.textContent = `Страница должна быть от 1 до ${maxPages}`;
                    }
                });
            }

            handleSubmit(e) {
                const questionCards = this.questionsList.querySelectorAll('.tc-question-card');
                if (questionCards.length === 0) {
                    e.preventDefault();
                    document.getElementById('questionsError').style.display = 'block';
                    return;
                }
                for (let card of questionCards) {
                    const index = card.id.split('_')[1];
                    const textFormatCheckbox = card.querySelector(`input[id="question_${index}_isTextFormat"]`);
                    const correctAnswerInput = card.querySelector(`input[id="question_${index}_correctAnswer"]`);
                    if (textFormatCheckbox.checked && (!correctAnswerInput.value || correctAnswerInput.value.trim() === '')) {
                        e.preventDefault();
                        correctAnswerInput.setCustomValidity(i18n.textAnswerRequired);
                        correctAnswerInput.reportValidity();
                        return;
                    } else {
                        correctAnswerInput.setCustomValidity('');
                    }
                }
                this.reindexQuestions();
            }
        }

        const testCreator = new TestCreator();
    </script>
</#noparse>
</@main.layout>
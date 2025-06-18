<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>

    <div class="container mt-4 bg-white p-4 rounded shadow-sm" style="color: #0d3b66;">

        <form action="/olympiad/new" method="post" novalidate>
            <@main.csrf/>
            <div class="form-group mb-3">
                <label for="title" class="fw-bold text-primary">Название олимпиады</label>
                <@spring.formInput "olympiad.title" 'id="title" class="form-control form-control-lg"'/>
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <div class="form-group mb-3">
                <label for="info" class="fw-bold text-primary">Описание</label>
                <@spring.formInput "olympiad.info" 'id="info" class="form-control form-control-lg"' />
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <div class="form-group mb-3">
                <label for="rules" class="fw-bold text-primary">Правила</label>
                <@spring.formInput "olympiad.rules" 'id="rules" class="form-control form-control-lg"' />
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <div class="form-group mb-3">
                <label for="subject" class="fw-bold text-primary">Предмет</label>
                <@spring.formInput "olympiad.subject" 'id="subject" class="form-control form-control-lg"' />
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <div class="form-group mb-3">
                <label for="startDate" class="fw-bold text-primary">Дата начала</label>
                <@spring.formInput "olympiad.startDate" 'id="startDate" class="form-control form-control-lg"' />
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <div class="form-group mb-4">
                <label for="endDate" class="fw-bold text-primary">Дата окончания</label>
                <@spring.formInput "olympiad.endDate" 'id="endDate" class="form-control form-control-lg"' />
                <div class="error-block text-danger small">
                    <@spring.showErrors "<br>" "custom-error"/>
                </div>
            </div>

            <hr class="mb-4"/>

            <h3 class="text-primary mb-3">Этапы олимпиады</h3>

            <div id="stagesContainer">
                <#if olympiad.stages?? && (olympiad.stages?size > 0)>
                    <#list olympiad.stages as stage, stageIndex>
                        <div class="stage-block border border-primary rounded p-3 mb-4" style="background-color: #e8f0fe;" data-stage-index="${stageIndex}">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h4 class="text-primary mb-0">Этап ${stageIndex + 1}</h4>
                                <button type="button" class="btn btn-outline-danger btn-sm remove-stage-btn">
                                    <i class="fas fa-trash"></i> Удалить
                                </button>
                            </div>

                            <div class="form-group mb-3">
                                <label for="stages${stageIndex}_name" class="fw-semibold text-primary">Название этапа</label>
                                <@spring.formInput "olympiad.stages[${stageIndex}].name" 'id="stages${stageIndex}_name" class="form-control"' />
                                <div class="error-block text-danger small">
                                    <@spring.showErrors "<br>" "custom-error"/>
                                </div>
                            </div>

                            <div class="form-group mb-3">
                                <label for="stages${stageIndex}_stageOrder" class="fw-semibold text-primary">Порядковый номер</label>
                                <@spring.formInput "olympiad.stages[${stageIndex}].stageOrder" 'id="stages${stageIndex}_stageOrder" class="form-control"' />
                                <div class="error-block text-danger small">
                                    <@spring.showErrors "<br>" "custom-error"/>
                                </div>
                            </div>

                            <div class="form-group mb-3">
                                <label for="stages${stageIndex}_description" class="fw-semibold text-primary">Описание этапа</label>
                                <@spring.formInput "olympiad.stages[${stageIndex}].description" 'id="stages${stageIndex}_description" class="form-control"' />
                                <div class="error-block text-danger small">
                                    <@spring.showErrors "<br>" "custom-error"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="stages${stageIndex}_registrationStart" class="fw-semibold text-primary">Начало регистрации</label>
                                        <@spring.formInput "olympiad.stages[${stageIndex}].registrationStart" 'id="stages${stageIndex}_registrationStart" class="form-control" type="date"' />
                                        <div class="error-block text-danger small">
                                            <@spring.showErrors "<br>" "custom-error"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="stages${stageIndex}_registrationEnd" class="fw-semibold text-primary">Окончание регистрации</label>
                                        <@spring.formInput "olympiad.stages[${stageIndex}].registrationEnd" 'id="stages${stageIndex}_registrationEnd" class="form-control" type="date"' />
                                        <div class="error-block text-danger small">
                                            <@spring.showErrors "<br>" "custom-error"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="stages${stageIndex}_eventStartDate" class="fw-semibold text-primary">Дата начала этапа</label>
                                        <@spring.formInput "olympiad.stages[${stageIndex}].eventStartDate" 'id="stages${stageIndex}_eventStartDate" class="form-control" type="date"' />
                                        <div class="error-block text-danger small">
                                            <@spring.showErrors "<br>" "custom-error"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group mb-3">
                                        <label for="stages${stageIndex}_eventEndDate" class="fw-semibold text-primary">Дата окончания этапа</label>
                                        <@spring.formInput "olympiad.stages[${stageIndex}].eventEndDate" 'id="stages${stageIndex}_eventEndDate" class="form-control" type="date"' />
                                        <div class="error-block text-danger small">
                                            <@spring.showErrors "<br>" "custom-error"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group mb-3">
                                <label for="stages${stageIndex}_location" class="fw-semibold text-primary">Место проведения</label>
                                <@spring.formInput "olympiad.stages[${stageIndex}].location" 'id="stages${stageIndex}_location" class="form-control"' />
                                <div class="error-block text-danger small">
                                    <@spring.showErrors "<br>" "custom-error"/>
                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                    <p id="noStagesMessage" class="text-secondary fst-italic">Пока нет этапов. Нажмите кнопку ниже, чтобы добавить.</p>
                </#if>
            </div>

            <button type="button" id="addStageBtn" class="btn btn-outline-primary mb-3">
                <i class="fas fa-plus"></i> Добавить этап
            </button>

            <div>
                <button type="submit" class="btn btn-primary">Создать олимпиаду</button>
            </div>
        </form>

    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            let stageCounter = <#if olympiad.stages??>${olympiad.stages?size}<#else>0</#if>;
            const stagesContainer = document.getElementById('stagesContainer');
            const addStageBtn = document.getElementById('addStageBtn');
            const noStagesMessage = document.getElementById('noStagesMessage');

            console.log('Инициализация: stageCounter =', stageCounter);

            // Функция для создания HTML блока этапа
            function createStageHTML(index) {
                return `
                    <div class="stage-block border border-primary rounded p-3 mb-4" style="background-color: #e8f0fe;" data-stage-index="${index}">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4 class="text-primary mb-0">Этап ${index + 1}</h4>
                            <button type="button" class="btn btn-outline-danger btn-sm remove-stage-btn">
                                <i class="fas fa-trash"></i> Удалить
                            </button>
                        </div>

                        <div class="form-group mb-3">
                            <label for="stages${index}.name" class="fw-semibold text-primary">Название этапа</label>
                            <input type="text" id="stages${index}.name" name="stages[${index}].name" class="form-control" value="" />
                            <div class="error-block text-danger small"></div>
                        </div>

                        <div class="form-group mb-3">
                            <label for="stages${index}.stageOrder" class="fw-semibold text-primary">Порядковый номер</label>
                            <input type="number" id="stages${index}.stageOrder" name="stages[${index}].stageOrder" class="form-control" value="${index + 1}" />
                            <div class="error-block text-danger small"></div>
                        </div>

                        <div class="form-group mb-3">
                            <label for="stages${index}.description" class="fw-semibold text-primary">Описание этапа</label>
                            <textarea id="stages${index}.description" name="stages[${index}].description" class="form-control" rows="3"></textarea>
                            <div class="error-block text-danger small"></div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="stages${index}.registrationStart" class="fw-semibold text-primary">Начало регистрации</label>
                                    <input type="date" id="stages${index}.registrationStart" name="stages[${index}].registrationStart" class="form-control" />
                                    <div class="error-block text-danger small"></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="stages${index}.registrationEnd" class="fw-semibold text-primary">Окончание регистрации</label>
                                    <input type="date" id="stages${index}.registrationEnd" name="stages[${index}].registrationEnd" class="form-control" />
                                    <div class="error-block text-danger small"></div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="stages${index}.eventStartDate" class="fw-semibold text-primary">Дата начала этапа</label>
                                    <input type="date" id="stages${index}.eventStartDate" name="stages[${index}].eventStartDate" class="form-control" />
                                    <div class="error-block text-danger small"></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group mb-3">
                                    <label for="stages${index}.eventEndDate" class="fw-semibold text-primary">Дата окончания этапа</label>
                                    <input type="date" id="stages${index}.eventEndDate" name="stages[${index}].eventEndDate" class="form-control" />
                                    <div class="error-block text-danger small"></div>
                                </div>
                            </div>
                        </div>

                        <div class="form-group mb-3">
                            <label for="stages${index}.location" class="fw-semibold text-primary">Место проведения</label>
                            <input type="text" id="stages${index}.location" name="stages[${index}].location" class="form-control" />
                            <div class="error-block text-danger small"></div>
                        </div>
                    </div>
                `;
            }

            // Функция для переиндексации всех этапов
            function reindexStages() {
                const stageBlocks = stagesContainer.querySelectorAll('.stage-block');
                console.log('Переиндексация этапов. Найдено блоков:', stageBlocks.length);

                stageBlocks.forEach((block, newIndex) => {
                    console.log('Обновляем блок с индексом', newIndex);

                    // Обновляем data-атрибут
                    block.setAttribute('data-stage-index', newIndex);

                    // Обновляем заголовок
                    const title = block.querySelector('h4');
                    if (title) {
                        title.textContent = `Этап ${newIndex + 1}`;
                    }

                    // Обновляем все input поля
                    const inputs = block.querySelectorAll('input, textarea');
                    inputs.forEach(input => {
                        if (input.name && input.name.includes('stages[')) {
                            const fieldName = input.name.replace(/stages\[\d+\]/, `stages[${newIndex}]`);
                            input.name = fieldName;
                            console.log('Обновлено имя поля:', fieldName);
                        }

                        if (input.id && input.id.includes('stages')) {
                            const fieldId = input.id.replace(/stages\d+\./, `stages${newIndex}.`);
                            input.id = fieldId;
                        }
                    });

                    // Обновляем labels
                    const labels = block.querySelectorAll('label');
                    labels.forEach(label => {
                        if (label.getAttribute('for') && label.getAttribute('for').includes('stages')) {
                            const newFor = label.getAttribute('for').replace(/stages\d+\./, `stages${newIndex}.`);
                            label.setAttribute('for', newFor);
                        }
                    });

                    // Обновляем порядковый номер
                    const orderInput = block.querySelector('input[name*="stageOrder"]');
                    if (orderInput) {
                        orderInput.value = newIndex + 1;
                    }
                });

                stageCounter = stageBlocks.length;
                console.log('Переиндексация завершена. Новый stageCounter:', stageCounter);
            }

            // Добавление нового этапа
            addStageBtn.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('Добавление нового этапа. Текущий stageCounter:', stageCounter);

                // Скрываем сообщение "нет этапов"
                if (noStagesMessage) {
                    noStagesMessage.style.display = 'none';
                }

                // Создаем новый блок этапа
                const stageHTML = createStageHTML(stageCounter);
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = stageHTML;
                const newStageBlock = tempDiv.firstElementChild;

                // Добавляем в контейнер
                stagesContainer.appendChild(newStageBlock);
                console.log('Новый этап добавлен с индексом:', stageCounter);

                stageCounter++;

                // Анимация появления
                newStageBlock.style.opacity = '0';
                newStageBlock.style.transform = 'translateY(-10px)';
                setTimeout(() => {
                    newStageBlock.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
                    newStageBlock.style.opacity = '1';
                    newStageBlock.style.transform = 'translateY(0)';
                }, 10);
            });

            // Удаление этапа
            stagesContainer.addEventListener('click', function(e) {
                if (e.target.closest('.remove-stage-btn')) {
                    e.preventDefault();

                    const stageBlock = e.target.closest('.stage-block');
                    const stageIndex = stageBlock.getAttribute('data-stage-index');
                    console.log('Удаление этапа с индексом:', stageIndex);

                    // Анимация исчезновения
                    stageBlock.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
                    stageBlock.style.opacity = '0';
                    stageBlock.style.transform = 'translateY(-10px)';

                    setTimeout(() => {
                        stageBlock.remove();

                        // Переиндексируем оставшиеся этапы
                        reindexStages();

                        const remainingStages = stagesContainer.querySelectorAll('.stage-block');
                        if (remainingStages.length === 0 && noStagesMessage) {
                            noStagesMessage.style.display = 'block';
                        }

                        console.log('Этап удален. Осталось этапов:', remainingStages.length);
                    }, 300);
                }
            });

            console.log('Инициализация завершена');
        });
    </script>

</@main.layout>
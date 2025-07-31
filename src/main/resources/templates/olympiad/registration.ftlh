<#import "../layout.ftlh" as main>
<@main.layout>
    <script src="https://unpkg.com/react@18.2.0/umd/react.production.min.js"></script>
    <script src="https://unpkg.com/react-dom@18.2.0/umd/react-dom.production.min.js"></script>
    <script src="https://unpkg.com/@babel/standalone@7.22.9/babel.min.js"></script>
    <style>
        :root {
            --olympiad-primary: #4361ee;
            --olympiad-primary-hover: #3f56d0;
            --olympiad-secondary: #f8f9fa;
            --olympiad-border: #e0e3e7;
            --olympiad-text: #2d3748;
            --olympiad-light-text: #718096;
            --olympiad-error: #e53e3e;
            --olympiad-success: #38a169;
            --olympiad-warning: #dd6b20;
            --olympiad-form-width: 1000px;
            --olympiad-radius: 12px;
            --olympiad-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
            --olympiad-transition: all 0.3s ease;
        }

        .olympiad-form-container {
            width: 100%;
            max-width: var(--olympiad-form-width);
            margin: 20px auto;
            background: white;
            border-radius: var(--olympiad-radius);
            box-shadow: var(--olympiad-shadow);
            overflow: hidden;
            transition: var(--olympiad-transition);
        }

        .olympiad-form-header {
            background: linear-gradient(135deg, var(--olympiad-primary), #3a56d4);
            color: white;
            padding: 25px;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .olympiad-form-header::before {
            content: '';
            position: absolute;
            top: -50px;
            right: -50px;
            width: 150px;
            height: 150px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
        }

        .olympiad-form-header::after {
            content: '';
            position: absolute;
            bottom: -80px;
            left: -30px;
            width: 200px;
            height: 200px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 50%;
        }

        .olympiad-form-header h1 {
            font-size: 28px;
            margin-bottom: 8px;
            font-weight: 700;
            position: relative;
            z-index: 1;
        }

        .olympiad-form-header p {
            font-size: 16px;
            opacity: 0.9;
            position: relative;
            z-index: 1;
        }

        .olympiad-form-body {
            padding: 30px;
            display: flex;
            flex-wrap: wrap;
            gap: 30px;
        }

        @media (max-width: 768px) {
            .olympiad-form-body {
                padding: 20px;
                gap: 20px;
            }
        }

        .olympiad-form-column {
            flex: 1;
            min-width: 300px;
        }

        .olympiad-form-section {
            margin-bottom: 30px;
            padding-bottom: 25px;
            border-bottom: 1px solid var(--olympiad-border);
        }

        .olympiad-form-section:last-child {
            border-bottom: none;
            margin-bottom: 0;
            padding-bottom: 0;
        }

        .olympiad-section-title {
            color: var(--olympiad-primary);
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 20px;
            padding-bottom: 8px;
            border-bottom: 2px solid var(--olympiad-primary);
            display: inline-block;
            position: relative;
        }

        .olympiad-section-title::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 50px;
            height: 2px;
            background: var(--olympiad-primary);
        }

        .olympiad-form-group {
            margin-bottom: 20px;
            position: relative;
        }

        .olympiad-form-row {
            display: flex;
            gap: 15px;
        }

        @media (max-width: 600px) {
            .olympiad-form-row {
                flex-direction: column;
                gap: 0;
            }
        }

        .olympiad-form-row .olympiad-form-group {
            flex: 1;
        }

        .olympiad-label {
            display: block;
            font-weight: 500;
            margin-bottom: 8px;
            color: var(--olympiad-text);
            font-size: 15px;
        }

        .olympiad-label-optional {
            font-weight: normal;
            color: var(--olympiad-light-text);
            font-size: 0.85em;
        }

        .olympiad-input,
        .olympiad-select {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid var(--olympiad-border);
            border-radius: 8px;
            font-size: 15px;
            transition: var(--olympiad-transition);
            background-color: #fff;
            color: var(--olympiad-text);
        }

        .olympiad-input:focus,
        .olympiad-select:focus {
            outline: none;
            border-color: var(--olympiad-primary);
            box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.2);
        }

        .olympiad-input::placeholder {
            color: #a0aec0;
            opacity: 1;
        }

        .olympiad-select {
            appearance: none;
            background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
            background-repeat: no-repeat;
            background-position: right 12px center;
            background-size: 1em;
        }

        .olympiad-checkbox-group {
            display: flex;
            align-items: flex-start;
            margin: 25px 0;
        }

        .olympiad-checkbox-group input {
            width: auto;
            margin-right: 12px;
            margin-top: 3px;
        }

        .olympiad-checkbox-group label {
            margin-bottom: 0;
            font-weight: normal;
            line-height: 1.5;
        }

        .olympiad-submit-btn {
            width: 100%;
            padding: 16px;
            background: var(--olympiad-primary);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: var(--olympiad-transition);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .olympiad-submit-btn:hover {
            background: var(--olympiad-primary-hover);
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(67, 97, 238, 0.2);
        }

        .olympiad-submit-btn:disabled {
            background: #a0aec0;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

        .olympiad-error {
            color: var(--olympiad-error);
            font-size: 14px;
            margin-top: 6px;
            display: none;
            font-weight: 500;
        }

        .olympiad-error.show {
            display: block;
        }

        .olympiad-form-footer {
            margin-top: 20px;
            width: 100%;
        }

        .olympiad-success-message {
            text-align: center;
            padding: 40px 30px;
        }

        .olympiad-success-message h2 {
            color: var(--olympiad-success);
            margin-bottom: 15px;
            font-size: 24px;
        }

        .olympiad-success-message p {
            color: var(--olympiad-text);
            margin-bottom: 25px;
            font-size: 16px;
        }

        .olympiad-success-icon {
            font-size: 60px;
            color: var(--olympiad-success);
            margin-bottom: 20px;
        }

        .olympiad-loading-spinner {
            display: inline-block;
            width: 20px;
            height: 20px;
            border: 3px solid rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            border-top-color: white;
            animation: olympiad-spin 1s ease-in-out infinite;
            margin-right: 10px;
        }

        @keyframes olympiad-spin {
            to {
                transform: rotate(360deg);
            }
        }

        .olympiad-required-field::after {
            content: '*';
            color: var(--olympiad-error);
            margin-left: 4px;
        }
    </style>

    <div id="olympiad-root"></div>
    <script type="text/babel">
        const regions = [
            { id: 1, name: "Чуйская область" },
            { id: 2, name: "Бишкек" },
            { id: 3, name: "Джалал-Абадская область" },
            { id: 4, name: "Иссык-кульская область" },
            { id: 5, name: "Нарынская область" },
            { id: 6, name: "Ош" },
            { id: 7, name: "Ошская область" },
            { id: 8, name: "Таласская область" },
            { id: 9, name: "Баткенская область" }
        ];

        const Alldistricts = [
            "Ак-Суйский район",
            "Ак-Талинский район",
            "Аксыйский район",
            "Ала-Букинский район",
            "Алайский район",
            "Аламудунский район",
            "Араванский район",
            "Ат-Башинский район",
            "Базар-Коргонский район",
            "Бакай-Атинский район",
            "Баткенский район",
            "Бишкек",
            "Жайыльский район",
            "Жети-Огузский район",
            "Жумгальский район",
            "Иссык-Кульский район",
            "Кадамжайский район",
            "Кара-Бууринский район",
            "Кара-Кульджинский район",
            "Кара-Сууский район",
            "Кеминский район",
            "Кочкорский район",
            "Лейлекский район",
            "Манасский район",
            "Московский район",
            "Нарынский район",
            "Ноокатский район",
            "Ноокенский район",
            "Ош",
            "Панфиловский район",
            "Сокулукский район",
            "Сузакский район",
            "Таласский район",
            "Тогуз-Тороуский район",
            "Токтогульский район",
            "Тонский район",
            "Тюпский район",
            "Узгенский район",
            "Чаткальский район",
            "Чон-Алайский район",
            "Чуйский район",
            "Ыссык-Атинский район"
        ];

        function RegistrationForm() {
            const [formData, setFormData] = React.useState({
                userId: '',
                olympiadId: '',
                email: '',
                region: '',
                district: '',
                fullName: '',
                phoneNumber: '',
                school: '',
                telegram: '',
                className: '',
                locality: '',
                classTeacherFullName: '',
                parentFullName: '',
                parentPhoneNumber: '',
                parentEmail: '',
                consent: false
            });

            const [error, setError] = React.useState('');
            const [isSubmitting, setIsSubmitting] = React.useState(false);
            const [submitSuccess, setSubmitSuccess] = React.useState(false);

            const handleChange = (e) => {
                const { name, value, type, checked } = e.target;
                setFormData(prev => ({
                    ...prev,
                    [name]: type === 'checkbox' ? checked : value
                }));
            };

            const handleSubmit = async (e) => {
                e.preventDefault();

                if (!formData.consent) {
                    setError('Необходимо согласиться на обработку персональных данных');
                    window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
                    return;
                }

                setError('');
                setIsSubmitting(true);

                <#noparse>
                try {
                    await new Promise(resolve => setTimeout(resolve, 1500));

                    // const response = await fetch('/olympiad/registration', {
                    //     method: 'POST',
                    //     headers: {
                    //         'Content-Type': 'application/json',
                    //         'X-CSRF-TOKEN': csrfToken,
                    //     },
                    //     body: JSON.stringify(formData),
                    // });

                    // if (!response.ok) {
                    //     throw new Error(`Ошибка ${response.status}`);
                    // }

                    // const result = await response.json();

                    setSubmitSuccess(true);
                } catch (err) {
                    setError('Произошла ошибка при отправке формы. Пожалуйста, попробуйте позже.');
                    console.error('Ошибка:', err);
                } finally {
                    setIsSubmitting(false);
                }
            };
            </#noparse>
            if (submitSuccess) {
                return (
                    <div className="olympiad-form-container">
                        <div className="olympiad-form-header">
                            <h1>Регистрация завершена</h1>
                        </div>
                        <div className="olympiad-success-message">
                            <div className="olympiad-success-icon">✓</div>
                            <h2>Спасибо за регистрацию!</h2>
                            <button
                                className="olympiad-submit-btn"
                                onClick={() => setSubmitSuccess(false)}
                                style={{ maxWidth: '300px', margin: '0 auto' }}
                            >
                                Вернуться к форме
                            </button>
                        </div>
                    </div>
                );
            }

            return (
                <div className="olympiad-form-container">
                    <div className="olympiad-form-header">
                        <h1>Форма регистрации</h1>
                        <p>Заполните все необходимые поля</p>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div className="olympiad-form-body">
                            <div className="olympiad-form-column">
                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">Основная информация</h2>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-required-field">Email</label>
                                        <input
                                            className="olympiad-input"
                                            type="email"
                                            name="email"
                                            value={formData.email}
                                            onChange={handleChange}
                                            required
                                            placeholder="example@mail.com"
                                        />
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-required-field">Область</label>
                                            <select
                                                className="olympiad-select"
                                                name="region"
                                                value={formData.region}
                                                onChange={handleChange}
                                                required
                                            >
                                                <option value="">Выберите область</option>
                                                {regions.map(region => (
                                                    <option key={region.name} value={region.name}>{region.name}</option>
                                                ))}
                                            </select>
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-required-field">Район</label>
                                            <select
                                                className="olympiad-select"
                                                name="district"
                                                value={formData.district}
                                                onChange={handleChange}
                                                required
                                            >
                                                <option value="">Выберите район</option>
                                                {Alldistricts.map(district => (
                                                    <option key={district} value={district}>{district}</option>
                                                ))}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-required-field">ФИО</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="fullName"
                                            value={formData.fullName}
                                            onChange={handleChange}
                                            required
                                            placeholder="Иванов Иван Иванович"
                                        />
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Номер телефона</label>
                                            <input
                                                className="olympiad-input"
                                                type="tel"
                                                name="phoneNumber"
                                                value={formData.phoneNumber}
                                                onChange={handleChange}
                                                placeholder="+996 __ ___ __ __"
                                            />
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Telegram</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="telegram"
                                                value={formData.telegram}
                                                onChange={handleChange}
                                                placeholder="@username"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="olympiad-form-column">
                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">Дополнительная информация</h2>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Школа</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="school"
                                                value={formData.school}
                                                onChange={handleChange}
                                                placeholder="Название школы"
                                            />
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Класс</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="className"
                                                value={formData.className}
                                                onChange={handleChange}
                                                placeholder="11A"
                                            />
                                        </div>
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">Населённый пункт</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="locality"
                                            value={formData.locality}
                                            onChange={handleChange}
                                            placeholder="Город/село"
                                        />
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">ФИО классного руководителя</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="classTeacherFullName"
                                            value={formData.classTeacherFullName}
                                            onChange={handleChange}
                                            placeholder="Петрова Мария Сергеевна"
                                        />
                                    </div>
                                </div>

                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">Информация о родителе</h2>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">ФИО родителя</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="parentFullName"
                                            value={formData.parentFullName}
                                            onChange={handleChange}
                                            placeholder="Иванова Анна Петровна"
                                        />
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Номер телефона родителя</label>
                                            <input
                                                className="olympiad-input"
                                                type="tel"
                                                name="parentPhoneNumber"
                                                value={formData.parentPhoneNumber}
                                                onChange={handleChange}
                                                placeholder="+996 __ ___ __ __"
                                            />
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">Email родителя</label>
                                            <input
                                                className="olympiad-input"
                                                type="email"
                                                name="parentEmail"
                                                value={formData.parentEmail}
                                                onChange={handleChange}
                                                placeholder="parent@mail.com"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className="olympiad-form-footer">
                            <div className="olympiad-form-body">
                                <div className="olympiad-checkbox-group">
                                    <input
                                        type="checkbox"
                                        name="consent"
                                        id="consent"
                                        checked={formData.consent}
                                        onChange={handleChange}
                                        required
                                    />
                                    <label htmlFor="consent">
                                        Я согласен на обработку персональных данных в соответствии с политикой конфиденциальности
                                    </label>
                                </div>
                                {error && <div className="olympiad-error show">{error}</div>}
                                <button
                                    className="olympiad-submit-btn"
                                    type="submit"
                                    disabled={isSubmitting}
                                >
                                    {isSubmitting && <span className="olympiad-loading-spinner"></span>}
                                    {isSubmitting ? 'Отправка...' : 'Зарегистрироваться'}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            );
        }

        const root = ReactDOM.createRoot(document.getElementById('olympiad-root'));
        root.render(<RegistrationForm />);
    </script>
</@main.layout>
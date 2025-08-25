<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring/>
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

        .olympiad-notification {
            position: fixed;
            top: 20px;
            right: 20px;
            background: rgba(229, 62, 62, 0.85);
            color: white;
            padding: 16px 24px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(229, 62, 62, 0.3);
            z-index: 1000;
            animation: slideIn 0.4s ease-out, fadeOut 0.4s ease-in 4.6s forwards;
            font-size: 15px;
            font-weight: 500;
            max-width: 320px;
            backdrop-filter: blur(4px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        @keyframes slideIn {
            from {
                transform: translateX(120%);
                opacity: 0;
            }

            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes fadeOut {
            to {
                opacity: 0;
                transform: translateX(120%);
            }
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
            "Ак-Суйский район", "Ак-Талинский район", "Аксыйский район", "Ала-Букинский район",
            "Алайский район", "Аламудунский район", "Араванский район", "Ат-Башинский район",
            "Базар-Коргонский район", "Бакай-Атинский район", "Баткенский район", "Бишкек",
            "Жайыльский район", "Жети-Огузский район", "Жумгальский район", "Иссык-Кульский район",
            "Кадамжайский район", "Кара-Бууринский район", "Кара-Кульджинский район", "Кара-Сууский район",
            "Кеминский район", "Кочкорский район", "Лейлекский район", "Манасский район",
            "Московский район", "Нарынский район", "Ноокатский район", "Ноокенский район", "Ош",
            "Панфиловский район", "Сокулукский район", "Сузакский район", "Таласский район",
            "Тогуз-Тороуский район", "Токтогульский район", "Тонский район", "Тюпский район",
            "Узгенский район", "Чаткальский район", "Чон-Алайский район", "Чуйский район", "Ыссык-Атинский район"
        ];

        function RegistrationForm() {
            const messages = {
                title: '<@spring.message "registration.title" />',
                fillRequired: '<@spring.message "registration.fillRequired" />',
                successTitle: '<@spring.message "registration.successTitle" />',
                successMessage: '<@spring.message "registration.successMessage" />',
                redirectMessage: '<@spring.message "registration.redirectMessage" />',
                email: '<@spring.message "registration.email" />',
                region: '<@spring.message "registration.region" />',
                selectRegion: '<@spring.message "registration.selectRegion" />',
                district: '<@spring.message "registration.district" />',
                selectDistrict: '<@spring.message "registration.selectDistrict" />',
                fullName: '<@spring.message "registration.fullName" />',
                phone: '<@spring.message "registration.phone" />',
                telegram: '<@spring.message "registration.telegram" />',
                school: '<@spring.message "registration.school" />',
                classNumber: '<@spring.message "registration.class" />',
                locality: '<@spring.message "registration.locality" />',
                classTeacherFullName: '<@spring.message "registration.classTeacher" />',
                parentInfo: '<@spring.message "registration.parentInfo" />',
                parentFullName: '<@spring.message "registration.parentFullName" />',
                parentPhoneNumber: '<@spring.message "registration.parentPhone" />',
                parentEmail: '<@spring.message "registration.parentEmail" />',
                consent: '<@spring.message "registration.consent" />',
                consentError: '<@spring.message "registration.consentError" />',
                submit: '<@spring.message "registration.submit" />',
                submitting: '<@spring.message "registration.submitting" />',
                onceNotice: '<@spring.message "registration.onceNotice" />',
                additionalInfo: '<@spring.message "registration.additionalInfo"/>',
                basicInfo: '<@spring.message "registration.basicInfo"/>'
            };

            const serverErrors = {
                <#if (registrationDto?? && errors??)>
                <#list errors.fieldErrors as error>
                "${error.field}": "<@spring.messageText "${error.code}" "${error.defaultMessage}" />",
                </#list>
                </#if>
            };

            const [formData, setFormData] = React.useState({
                userId: '${user.id}',
                olympiadStageId: '${stageId}',
                email: '${(registrationDto.email)!""}',
                region: '${(registrationDto.region)!""}',
                district: '${(registrationDto.district)!""}',
                fullName: '${(registrationDto.fullName)!""}',
                phoneNumber: '${(registrationDto.phoneNumber)!""}',
                school: '${(registrationDto.school)!""}',
                telegram: '${(registrationDto.telegram)!""}',
                classNumber: '${(registrationDto.classNumber)!""}',
                locality: '${(registrationDto.locality)!""}',
                classTeacherFullName: '${(registrationDto.classTeacherFullName)!""}',
                parentFullName: '${(registrationDto.parentFullName)!""}',
                parentPhoneNumber: '${(registrationDto.parentPhoneNumber)!""}',
                parentEmail: '${(registrationDto.parentEmail)!""}'});

            const [error, setError] = React.useState('');
            const [isSubmitting, setIsSubmitting] = React.useState(false);
            const [submitSuccess, setSubmitSuccess] = React.useState(false);
            const [showNotification, setShowNotification] = React.useState(true);

            React.useEffect(() => {
                const timer = setTimeout(() => {
                    setShowNotification(false);
                }, 6000);
                return () => clearTimeout(timer);
            }, []);

            const handleChange = (e) => {
                const { name, value, type, checked } = e.target;
                setFormData(prev => ({
                    ...prev,
                    [name]: type === 'checkbox' ? checked : value
                }));
            };

            const handleSubmit = (e) => {
                e.preventDefault();

                if (!formData.consent) {
                    setError(messages.consentError);
                    window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
                    return;
                }

                setError('');
                setIsSubmitting(true);

                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '/olympiad/registration?stageId=${stageId}';
                form.style.display = 'none';

                const csrfInput = document.createElement('input');
                csrfInput.type = 'hidden';
                csrfInput.name = '_csrf';
                csrfInput.value = '${_csrf.token}';
                form.appendChild(csrfInput);

                for (const key in formData) {
                    if (key !== 'consent') {
                        const input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = key;
                        input.value = formData[key];
                        form.appendChild(input);
                    }
                }

                const consentInput = document.createElement('input');
                consentInput.type = 'hidden';
                consentInput.name = 'consent';
                consentInput.value = formData.consent ? 'true' : 'false';
                form.appendChild(consentInput);

                document.body.appendChild(form);
                form.submit();
            };

            if (submitSuccess) {
                return (
                    <div className="olympiad-form-container">
                        <div className="olympiad-form-header">
                            <h1>{messages.successTitle}</h1>
                        </div>
                        <div className="olympiad-success-message">
                            <div className="olympiad-success-icon">✓</div>
                            <h2>{messages.successMessage}</h2>
                            <p>{messages.redirectMessage}</p>
                        </div>
                    </div>
                );
            }

            return (
                <div className="olympiad-form-container">
                    <#if user.role.name != "ADMIN" && user.role.name != "MODER">
                    {showNotification && (
                        <div className="olympiad-notification">
                            {messages.onceNotice}
                        </div>
                    )}
                    </#if>
                    <div className="olympiad-form-header">
                        <h1>{messages.title}</h1>
                        <p>{messages.fillRequired}</p>
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div className="olympiad-form-body">
                            <div className="olympiad-form-column">
                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">{messages.basicInfo}</h2>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-required-field">{messages.email}</label>
                                        <input
                                            className="olympiad-input"
                                            type="email"
                                            name="email"
                                            value={formData.email}
                                            onChange={handleChange}
                                            required
                                            placeholder="example@gmail.com"
                                        />
                                        {serverErrors.email && <div className="olympiad-error show">{serverErrors.email}</div>}
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-required-field">{messages.region}</label>
                                            <select
                                                className="olympiad-select"
                                                name="region"
                                                value={formData.region}
                                                onChange={handleChange}
                                                required
                                            >
                                                <option value="">{messages.selectRegion}</option>
                                                {regions.map(region => (
                                                    <option key={region.name} value={region.name}>{region.name}</option>
                                                ))}
                                            </select>
                                            {serverErrors.region && <div className="olympiad-error show">{serverErrors.region}</div>}
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-required-field">{messages.district}</label>
                                            <select
                                                className="olympiad-select"
                                                name="district"
                                                value={formData.district}
                                                onChange={handleChange}
                                                required
                                            >
                                                <option value="">{messages.selectDistrict}</option>
                                                {Alldistricts.map(d => (
                                                    <option key={d} value={d}>{d}</option>
                                                ))}
                                            </select>
                                            {serverErrors.district && <div className="olympiad-error show">{serverErrors.district}</div>}
                                        </div>
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-required-field">{messages.fullName}</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="fullName"
                                            value={formData.fullName}
                                            onChange={handleChange}
                                            required
                                            placeholder="Иванов Азамат Иванович"
                                        />
                                        {serverErrors.fullName && <div className="olympiad-error show">{serverErrors.fullName}</div>}
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.phone}</label>
                                            <input
                                                className="olympiad-input"
                                                type="tel"
                                                name="phoneNumber"
                                                value={formData.phoneNumber}
                                                onChange={handleChange}
                                                required
                                                placeholder="+996 ___ ___ ___"
                                            />
                                            {serverErrors.phoneNumber && <div className="olympiad-error show">{serverErrors.phoneNumber}</div>}
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.telegram}</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="telegram"
                                                value={formData.telegram}
                                                onChange={handleChange}
                                                placeholder="@username"
                                            />
                                            {serverErrors.telegram && <div className="olympiad-error show">{serverErrors.telegram}</div>}
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div className="olympiad-form-column">
                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">{messages.additionalInfo}</h2>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.school}</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="school"
                                                value={formData.school}
                                                onChange={handleChange}
                                                required
                                                placeholder="Название школы"
                                            />
                                            {serverErrors.school && <div className="olympiad-error show">{serverErrors.school}</div>}
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.classNumber}</label>
                                            <input
                                                className="olympiad-input"
                                                type="text"
                                                name="classNumber"
                                                value={formData.classNumber}
                                                onChange={handleChange}
                                                required
                                                placeholder="10А"
                                            />
                                            {serverErrors.classNumber && <div className="olympiad-error show">{serverErrors.classNumber}</div>}
                                        </div>
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">{messages.locality}</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="locality"
                                            value={formData.locality}
                                            onChange={handleChange}
                                            required
                                            placeholder="Город/село"
                                        />
                                        {serverErrors.locality && <div className="olympiad-error show">{serverErrors.locality}</div>}
                                    </div>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">{messages.classTeacherFullName}</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="classTeacherFullName"
                                            value={formData.classTeacherFullName}
                                            onChange={handleChange}
                                            required
                                            placeholder="ФИО"
                                        />
                                        {serverErrors.classTeacherFullName && <div className="olympiad-error show">{serverErrors.classTeacherFullName}</div>}
                                    </div>
                                </div>

                                <div className="olympiad-form-section">
                                    <h2 className="olympiad-section-title">{messages.parentInfo}</h2>
                                    <div className="olympiad-form-group">
                                        <label className="olympiad-label">{messages.parentFullName}</label>
                                        <input
                                            className="olympiad-input"
                                            type="text"
                                            name="parentFullName"
                                            value={formData.parentFullName}
                                            onChange={handleChange}
                                            required
                                            placeholder="ФИО"
                                        />
                                        {serverErrors.parentFullName && <div className="olympiad-error show">{serverErrors.parentFullName}</div>}
                                    </div>
                                    <div className="olympiad-form-row">
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.parentPhoneNumber}</label>
                                            <input
                                                className="olympiad-input"
                                                type="tel"
                                                name="parentPhoneNumber"
                                                value={formData.parentPhoneNumber}
                                                onChange={handleChange}
                                                required
                                                placeholder="+996 ___ ___ ___"
                                            />
                                            {serverErrors.parentPhoneNumber && <div className="olympiad-error show">{serverErrors.parentPhoneNumber}</div>}
                                        </div>
                                        <div className="olympiad-form-group">
                                            <label className="olympiad-label">{messages.parentEmail}</label>
                                            <input
                                                className="olympiad-input"
                                                type="email"
                                                name="parentEmail"
                                                value={formData.parentEmail}
                                                onChange={handleChange}
                                                placeholder="parent@mail.com"
                                            />
                                            {serverErrors.parentEmail && <div className="olympiad-error show">{serverErrors.parentEmail}</div>}
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
                                        {messages.consent}
                                    </label>
                                </div>
                                {error && <div className="olympiad-error show">{error}</div>}
                                {serverErrors.consent && <div className="olympiad-error show">{serverErrors.consent}</div>}
                                <button
                                    className="olympiad-submit-btn"
                                    type="submit"
                                    disabled={isSubmitting}
                                >
                                    {isSubmitting && <span className="olympiad-loading-spinner"></span>}
                                    {isSubmitting ? messages.submitting : messages.submit}
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
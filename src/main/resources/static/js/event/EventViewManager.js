class EventViewManager extends BaseContentViewManager {
    constructor() {
        super({
            contentType: 'event',
            socialShareOptions: {
                contentType: 'event',
                apiEndpoint: '/api'
            },
            authorManagerOptions: {
                authorContainerSelector: '.event-author-compact'
            }
        });
    }

    setupSpecificFeatures() {
        this.initEventCountdown();
        this.initEventCalendar();
        this.initEventLocation();
        this.setupEventReminders();
    }

    initEventCountdown() {
        const eventDateElement = document.querySelector('[data-event-start]');
        if (eventDateElement) {
            const eventDate = new Date(eventDateElement.getAttribute('data-event-start'));
            this.startCountdown(eventDate);
        }
    }

    startCountdown(eventDate) {
        const countdownElement = document.createElement('div');
        countdownElement.className = 'event-countdown';
        countdownElement.innerHTML = `
            <div class="countdown-timer">
                <div class="countdown-item">
                    <span class="countdown-number" id="days">00</span>
                    <span class="countdown-label">дней</span>
                </div>
                <div class="countdown-item">
                    <span class="countdown-number" id="hours">00</span>
                    <span class="countdown-label">часов</span>
                </div>
                <div class="countdown-item">
                    <span class="countdown-number" id="minutes">00</span>
                    <span class="countdown-label">минут</span>
                </div>
            </div>
        `;

        const eventHeader = document.querySelector('.event-header, .event-meta');
        if (eventHeader) {
            eventHeader.appendChild(countdownElement);
        }

        const updateCountdown = () => {
            const now = new Date();
            const timeLeft = eventDate - now;

            if (timeLeft > 0) {
                const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
                const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));

                document.getElementById('days').textContent = days.toString().padStart(2, '0');
                document.getElementById('hours').textContent = hours.toString().padStart(2, '0');
                document.getElementById('minutes').textContent = minutes.toString().padStart(2, '0');
            } else {
                countdownElement.innerHTML = '<div class="event-started">Мероприятие началось!</div>';
            }
        };

        updateCountdown();
        setInterval(updateCountdown, 60000);
    }

    initEventCalendar() {
        const addToCalendarBtn = document.createElement('button');
        addToCalendarBtn.className = 'btn btn-outline-primary';
        addToCalendarBtn.innerHTML = '<i class="fas fa-calendar-plus me-2"></i>Добавить в календарь';

        addToCalendarBtn.addEventListener('click', () => {
            this.addToCalendar();
        });

        const eventActions = document.querySelector('.event-actions, .blog-actions');
        if (eventActions) {
            eventActions.appendChild(addToCalendarBtn);
        }
    }

    addToCalendar() {
        const eventData = this.extractEventDataForCalendar();
        if (eventData) {
            const calendarUrl = this.generateCalendarUrl(eventData);
            window.open(calendarUrl, '_blank');
        }
    }

    extractEventDataForCalendar() {
        const title = document.querySelector('.event-title, h1')?.textContent || '';
        const description = document.querySelector('.event-description, .event-content')?.textContent || '';
        const startDate = document.querySelector('[data-event-start]')?.getAttribute('data-event-start');
        const endDate = document.querySelector('[data-event-end]')?.getAttribute('data-event-end');
        const location = document.querySelector('.event-location')?.textContent || '';

        if (!startDate) return null;

        return { title, description, startDate, endDate, location };
    }

    generateCalendarUrl(eventData) {
        const formatDate = (dateString) => {
            return new Date(dateString).toISOString().replace(/[-:]/g, '').split('.')[0] + 'Z';
        };

        const params = new URLSearchParams({
            action: 'TEMPLATE',
            text: eventData.title,
            dates: eventData.endDate ?
                `${formatDate(eventData.startDate)}/${formatDate(eventData.endDate)}` :
                formatDate(eventData.startDate),
            details: eventData.description,
            location: eventData.location
        });

        return `https://calendar.google.com/calendar/render?${params}`;
    }

    initEventLocation() {
        const locationElement = document.querySelector('.event-location[data-address]');
        if (locationElement) {
            const address = locationElement.getAttribute('data-address');
            if (address) {
                const mapButton = document.createElement('a');
                mapButton.href = `https://maps.google.com/maps?q=${encodeURIComponent(address)}`;
                mapButton.target = '_blank';
                mapButton.className = 'btn btn-sm btn-outline-secondary ms-2';
                mapButton.innerHTML = '<i class="fas fa-map-marker-alt"></i>';
                mapButton.title = 'Показать на карте';

                locationElement.appendChild(mapButton);
            }
        }
    }

    setupEventReminders() {
        const reminderButton = document.createElement('button');
        reminderButton.className = 'btn btn-warning';
        reminderButton.innerHTML = '<i class="fas fa-bell me-2"></i>Напомнить мне';

        reminderButton.addEventListener('click', () => {
            this.setupReminder();
        });

        const eventActions = document.querySelector('.event-actions, .blog-actions');
        if (eventActions) {
            eventActions.appendChild(reminderButton);
        }
    }

    setupReminder() {
        const eventId = this.options.contentData?.id;
        const eventTitle = this.options.contentData?.title;
        const eventDate = document.querySelector('[data-event-start]')?.getAttribute('data-event-start');

        if (eventId && eventDate) {
            const reminders = JSON.parse(localStorage.getItem('eventReminders') || '[]');
            const reminder = {
                id: eventId,
                title: eventTitle,
                date: eventDate,
                created: new Date().toISOString()
            };

            reminders.push(reminder);
            localStorage.setItem('eventReminders', JSON.stringify(reminders));

            this.showNotification('Напоминание добавлено!', 'success');
        }
    }

    showNotification(message, type) {
        if (this.socialShareManager) {
            this.socialShareManager.showNotification(message, type);
        }
    }
}

tinymce.init({
    selector: '#content',
    height: 400,
    menubar: false,
    plugins: [
        'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
        'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
        'insertdatetime', 'media', 'table', 'help', 'wordcount'
    ],
    toolbar: 'undo redo | blocks | bold italic forecolor | alignleft aligncenter ' +
        'alignright alignjustify | bullist numlist outdent indent | ' +
        'removeformat | help',
    content_style: 'body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; font-size: 14px }',
    language: 'ru',
    branding: false,
    setup: function (editor) {
        editor.on('change', function () {
            editor.save();
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    let startDatePicker, endDatePicker;

    const flatpickrConfig = {
        enableTime: true,
        dateFormat: "Y-m-d H:i",
        time_24hr: true,
        locale: "ru",
        minDate: "today",
        allowInput: false,
        clickOpens: true,
        altInput: true,
        altFormat: "d.m.Y H:i"
    };

    startDatePicker = flatpickr("#startDate", {
        ...flatpickrConfig,
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0) {
                endDatePicker.set('minDate', selectedDates[0]);

                const endDate = endDatePicker.selectedDates[0];
                if (endDate && endDate < selectedDates[0]) {
                    endDatePicker.clear();
                    alert('Дата окончания была сброшена');
                }
            }
        }
    });

    endDatePicker = flatpickr("#endDate", {
        ...flatpickrConfig,
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0 && startDatePicker.selectedDates.length > 0) {
                const startDate = startDatePicker.selectedDates[0];
                const endDate = selectedDates[0];

                if (endDate < startDate) {
                    instance.clear();
                    alert('Дата окончания не может быть раньше даты начала');
                }
            }
        }
    });
});

document.getElementById('mainImage').addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
        if (file.size > 5 * 1024 * 1024) {
            alert('Размер файла не должен превышать 5 МБ');
            this.value = '';
            return;
        }

        const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
        if (!allowedTypes.includes(file.type)) {
            alert('Поддерживаются только изображения (JPG, PNG, GIF, WebP)');
            this.value = '';
            return;
        }
    }
});

document.getElementById('attachments').addEventListener('change', function () {
    const files = Array.from(this.files);
    const maxSize = 10 * 1024 * 1024; // 10 МБ
    const allowedTypes = [
        'application/pdf',
        'application/msword',
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        'application/vnd.ms-excel',
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        'image/jpeg',
        'image/jpg',
        'image/png'
    ];

    for (let file of files) {
        if (file.size > maxSize) {
            alert(`Файл "${file.name}" превышает максимальный размер 10 МБ`);
            this.value = '';
            return;
        }

        if (!allowedTypes.includes(file.type)) {
            alert(`Файл "${file.name}" имеет неподдерживаемый формат`);
            this.value = '';
            return;
        }
    }
});
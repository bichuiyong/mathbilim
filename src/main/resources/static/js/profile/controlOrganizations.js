// Глобальные переменные и функции
let currentPage = 0;
let currentQuery = '';
const pageSize = 10;

// Функции для показа уведомлений (глобальные)
function showSuccessMessage(message) {
    showToast(message, 'success');
}

function showErrorMessage(message) {
    showToast(message, 'danger');
}

function showToast(message, type) {
    const toast = document.createElement('div');
    toast.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    toast.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    document.body.appendChild(toast);

    // Автоматически скрываем через 5 секунд
    setTimeout(() => {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 5000);
}

// Функция для экранирования HTML (глобальная)
function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Функция валидации URL (глобальная)
function isValidUrl(string) {
    try {
        new URL(string);
        return true;
    } catch (_) {
        return false;
    }
}

// Основной код инициализации
document.addEventListener('DOMContentLoaded', () => {
    const list = document.getElementById('organizationsContentList');
    if (!list) {
        console.log('organizationsContentList not found');
        return;
    }

    const paginationNav = document.getElementById('organizationsPagination');
    const paginationUl = paginationNav?.querySelector('ul.pagination');
    const searchBtn = document.getElementById('organizationsSearchBtn');
    const searchInput = document.getElementById('organizationsSearch');
    const organizationsTab = document.getElementById('organizations-tab');

    // Загружаем организации при клике на вкладку
    if (organizationsTab) {
        organizationsTab.addEventListener('click', () => {
            setTimeout(() => loadPage(0), 100);
        });
    }

    // Функция загрузки страницы (доступна глобально через window)
    window.loadPage = function(page) {
        currentPage = page;

        // ИЗМЕНЕНО: используем API эндпоинт
        let url = `/api/organizations/page?page=${page}&size=${pageSize}`;
        if (currentQuery) {
            url += `&name=${encodeURIComponent(currentQuery)}`;
        }

        console.log('Loading organizations from:', url);

        const list = document.getElementById('organizationsContentList');
        if (!list) {
            console.error('organizationsContentList not found');
            return;
        }

        // Показать загрузку
        list.innerHTML = `
        <div class="text-center py-5">
            <div class="spinner-border text-primary mb-3" role="status">
                <span class="visually-hidden">Загрузка...</span>
            </div>
            <p class="text-muted">Загрузка организаций...</p>
        </div>
    `;

        fetch(url, {
            method: 'GET',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Accept': 'application/json'
            },
            credentials: 'same-origin'
        })
            .then(response => {
                console.log('Response status:', response.status);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Organizations data:', data);
                renderOrganizations(data.content);
                renderPagination(data.totalPages, data.number);
            })
            .catch(err => {
                console.error('Error loading organizations:', err);
                list.innerHTML = `
            <div class="text-center py-5">
                <div class="mb-3">
                    <i class="fas fa-exclamation-triangle text-danger" style="font-size: 3rem;"></i>
                </div>
                <h5 class="text-danger">Ошибка загрузки</h5>
                <p class="text-muted">${err.message}</p>
                <button class="btn btn-outline-primary" onclick="window.loadPage(${page})">
                    <i class="fas fa-refresh me-2"></i>Повторить попытку
                </button>
            </div>
        `;
            });
    };

    function renderOrganizations(organizations) {
        if (!organizations || organizations.length === 0) {
            list.innerHTML = `
                <div class="text-center py-5">
                    <div class="mb-4">
                        <i class="fas fa-building text-muted" style="font-size: 4rem; opacity: 0.3;"></i>
                    </div>
                    <h5 class="text-muted">Организации не найдены</h5>
                    <p class="text-muted">Попробуйте изменить критерии поиска</p>
                </div>
            `;
            if (paginationNav) paginationNav.style.display = 'none';
            return;
        }

        list.innerHTML = '';
        organizations.forEach(org => {
            const card = document.createElement('div');
            card.className = 'card mb-3 shadow-sm border-0';
            card.style.transition = 'all 0.3s ease';

            // Получаем имя создателя (только имя)
            const creatorName = getCreatorName(org.creator);

            card.innerHTML = `
                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-auto">
                            <div class="organization-avatar">
                                ${org.avatar ?
                `<img src="/api/files/${org.avatar.id}/download" 
                                         alt="${escapeHtml(org.name)}" 
                                         class="rounded-circle" 
                                         style="width: 60px; height: 60px; object-fit: cover;">` :
                `<div class="bg-primary rounded-circle d-flex align-items-center justify-content-center" 
                                          style="width: 60px; height: 60px;">
                                        <i class="fas fa-building text-white fa-lg"></i>
                                    </div>`
            }
                            </div>
                        </div>
                        <div class="col">
                            <div class="organization-info">
                                <h5 class="card-title mb-1 text-dark">${escapeHtml(org.name)}</h5>
                                <p class="card-text text-muted mb-2" style="font-size: 0.9rem;">
                                    ${org.description ?
                (org.description.length > 100 ?
                    escapeHtml(org.description.substring(0, 100)) + '...' :
                    escapeHtml(org.description)) :
                'Описание отсутствует'}
                                </p>
                                ${org.url ?
                `<a href="${escapeHtml(org.url)}" target="_blank" class="text-primary text-decoration-none">
                                        <i class="fas fa-external-link-alt me-1"></i>Перейти на сайт
                                    </a><br>` : ''}
                                <small class="text-muted">
                                    <i class="fas fa-user me-1"></i>Создатель: ${creatorName}
                                </small>
                            </div>
                        </div>
                        <div class="col-auto">
                            <div class="btn-group" role="group">
                                <button type="button" 
                                        class="btn btn-outline-primary btn-sm px-3 edit-org-btn" 
                                        data-org-id="${org.id}"
                                        data-org-name="${escapeHtml(org.name)}"
                                        title="Редактировать">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button type="button" 
                                        class="btn btn-outline-danger btn-sm px-3 delete-org-btn" 
                                        data-org-id="${org.id}"
                                        data-org-name="${escapeHtml(org.name)}"
                                        title="Удалить">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `;

            // Добавляем hover эффект
            card.addEventListener('mouseenter', () => {
                card.style.transform = 'translateY(-2px)';
                card.style.boxShadow = '0 4px 20px rgba(0,0,0,0.1)';
            });

            card.addEventListener('mouseleave', () => {
                card.style.transform = 'translateY(0)';
                card.style.boxShadow = '0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)';
            });

            list.appendChild(card);
        });

        // Добавляем обработчики событий для кнопок ПОСЛЕ создания карточек
        addButtonEventListeners();
    }

    // Функция для получения только имени создателя
    function getCreatorName(creator) {
        return creator?.name || 'Неизвестно';
    }

    // Функция для добавления обработчиков событий к кнопкам
    function addButtonEventListeners() {
        // Кнопки редактирования
        const editButtons = list.querySelectorAll('.edit-org-btn');
        editButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                const orgId = this.getAttribute('data-org-id');
                const orgName = this.getAttribute('data-org-name');
                console.log('Edit clicked:', orgId, orgName);
                editOrganization(orgId, orgName);
            });
        });

        // Кнопки удаления
        const deleteButtons = list.querySelectorAll('.delete-org-btn');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                e.stopPropagation();
                const orgId = this.getAttribute('data-org-id');
                const orgName = this.getAttribute('data-org-name');
                console.log('Delete clicked:', orgId, orgName);
                deleteOrganization(orgId, orgName);
            });
        });
    }

    function renderPagination(totalPages, page) {
        if (!paginationUl) return;

        paginationUl.innerHTML = '';
        if (totalPages <= 1) {
            if (paginationNav) paginationNav.style.display = 'none';
            return;
        }

        if (paginationNav) paginationNav.style.display = 'block';

        // Кнопка "Предыдущая"
        if (page > 0) {
            const prevLi = document.createElement('li');
            prevLi.className = 'page-item';
            const prevA = document.createElement('a');
            prevA.className = 'page-link';
            prevA.href = '#';
            prevA.innerHTML = '<i class="fas fa-chevron-left"></i>';
            prevA.addEventListener('click', (e) => {
                e.preventDefault();
                loadPage(page - 1);
            });
            prevLi.appendChild(prevA);
            paginationUl.appendChild(prevLi);
        }

        // Номера страниц
        const startPage = Math.max(0, page - 2);
        const endPage = Math.min(totalPages - 1, page + 2);

        for (let i = startPage; i <= endPage; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === page ? 'active' : ''}`;
            const a = document.createElement('a');
            a.className = 'page-link';
            a.href = '#';
            a.textContent = (i + 1).toString();
            a.addEventListener('click', (e) => {
                e.preventDefault();
                loadPage(i);
            });
            li.appendChild(a);
            paginationUl.appendChild(li);
        }

        // Кнопка "Следующая"
        if (page < totalPages - 1) {
            const nextLi = document.createElement('li');
            nextLi.className = 'page-item';
            const nextA = document.createElement('a');
            nextA.className = 'page-link';
            nextA.href = '#';
            nextA.innerHTML = '<i class="fas fa-chevron-right"></i>';
            nextA.addEventListener('click', (e) => {
                e.preventDefault();
                loadPage(page + 1);
            });
            nextLi.appendChild(nextA);
            paginationUl.appendChild(nextLi);
        }
    }

    // Поиск
    if (searchBtn) {
        searchBtn.addEventListener('click', () => {
            currentQuery = searchInput?.value?.trim() || '';
            loadPage(0);
        });
    }

    // Поиск по Enter
    if (searchInput) {
        searchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                currentQuery = searchInput.value.trim();
                loadPage(0);
            }
        });
    }
});

// Глобальные функции для редактирования и удаления (вне DOMContentLoaded)

// Функция редактирования организации
function editOrganization(id, name) {
    console.log('Edit organization called:', id, name);

    if (typeof bootstrap === 'undefined') {
        alert('Bootstrap не загружен. Функция редактирования временно недоступна.');
        return;
    }

    // ИЗМЕНЕНО: используем API эндпоинт
    fetch(`/api/organizations/${id}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Не удалось загрузить данные организации');
            }
            return response.json();
        })
        .then(orgData => {
            showEditModal(orgData);
        })
        .catch(error => {
            console.error('Error loading organization:', error);
            showErrorMessage('Ошибка загрузки данных организации');
        });
}

function showEditModal(orgData) {
    // Создаем модальное окно для редактирования
    const modalHtml = `
        <div class="modal fade" id="editOrgModal" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            <i class="fas fa-edit me-2"></i>Редактирование организации
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form id="editOrgForm">
                            <div class="mb-3">
                                <label for="editOrgName" class="form-label">Название организации *</label>
                                <input type="text" class="form-control" id="editOrgName" 
                                       value="${escapeHtml(orgData.name || '')}" required>
                                <div class="invalid-feedback">
                                    Пожалуйста, введите название организации
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="editOrgDescription" class="form-label">Описание</label>
                                <textarea class="form-control" id="editOrgDescription" 
                                          rows="4" placeholder="Введите описание организации">${escapeHtml(orgData.description || '')}</textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label for="editOrgUrl" class="form-label">Веб-сайт</label>
                                <input type="url" class="form-control" id="editOrgUrl" 
                                       value="${escapeHtml(orgData.url || '')}" 
                                       placeholder="https://example.com">
                                <div class="invalid-feedback">
                                    Пожалуйста, введите корректный URL
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Текущий аватар</label>
                                <div class="current-avatar">
                                    ${orgData.avatar ?
        `<img src="/api/files/${orgData.avatar.id}/download" 
                                             alt="Текущий аватар" 
                                             class="rounded border" 
                                             style="width: 100px; height: 100px; object-fit: cover;">` :
        `<div class="bg-light border rounded d-flex align-items-center justify-content-center" 
                                              style="width: 100px; height: 100px;">
                                            <i class="fas fa-building text-muted fa-2x"></i>
                                        </div>`
    }
                                </div>
                                <small class="text-muted">Для изменения аватара обратитесь к администратору</small>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-2"></i>Отмена
                        </button>
                        <button type="button" class="btn btn-primary" id="saveOrgBtn">
                            <i class="fas fa-save me-2"></i>Сохранить изменения
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // Удаляем существующее модальное окно, если есть
    const existingModal = document.getElementById('editOrgModal');
    if (existingModal) {
        existingModal.remove();
    }

    // Добавляем новое модальное окно
    document.body.insertAdjacentHTML('beforeend', modalHtml);

    const modal = new bootstrap.Modal(document.getElementById('editOrgModal'));
    modal.show();

    // Обработчик кнопки сохранения
    document.getElementById('saveOrgBtn').addEventListener('click', function() {
        saveOrganization(orgData.id, this, modal);
    });

    // Удаляем модальное окно после закрытия
    document.getElementById('editOrgModal').addEventListener('hidden.bs.modal', function() {
        this.remove();
    });
}

function saveOrganization(orgId, saveBtn, modal) {
    const form = document.getElementById('editOrgForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    const formData = {
        name: document.getElementById('editOrgName').value.trim(),
        description: document.getElementById('editOrgDescription').value.trim(),
        url: document.getElementById('editOrgUrl').value.trim()
    };

    if (formData.url && !isValidUrl(formData.url)) {
        document.getElementById('editOrgUrl').classList.add('is-invalid');
        return;
    }

    saveBtn.disabled = true;
    const originalText = saveBtn.innerHTML;
    saveBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Сохранение...';

    // ИЗМЕНЕНО: используем API эндпоинт
    fetch(`/api/organizations/${orgId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            modal.hide();
            showSuccessMessage('Организация успешно обновлена');
            window.loadPage(currentPage);
        })
        .catch(error => {
            console.error('Error saving organization:', error);
            showErrorMessage('Ошибка при сохранении изменений');
        })
        .finally(() => {
            saveBtn.disabled = false;
            saveBtn.innerHTML = originalText;
        });
}

// Функция удаления организации
function deleteOrganization(id, name) {
    console.log('Delete organization called:', id, name);

    // Проверяем, что у нас есть Bootstrap
    if (typeof bootstrap === 'undefined') {
        if (confirm(`Вы действительно хотите удалить организацию "${name}"?`)) {
            performDelete(id);
        }
        return;
    }

    // Создаем модальное окно подтверждения
    const modalHtml = `
        <div class="modal fade" id="deleteOrgModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header border-0">
                        <h5 class="modal-title text-danger">
                            <i class="fas fa-exclamation-triangle me-2"></i>Подтвердите удаление
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <p>Вы действительно хотите удалить организацию?</p>
                        <div class="alert alert-warning">
                            <strong>"${escapeHtml(name)}"</strong>
                        </div>
                        <p class="text-muted small">
                            <i class="fas fa-info-circle me-1"></i>
                            Это действие нельзя отменить. Организация будет удалена только если к ней не привязаны события или олимпиады.
                        </p>
                    </div>
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-2"></i>Отмена
                        </button>
                        <button type="button" class="btn btn-danger" id="confirmDeleteBtn">
                            <i class="fas fa-trash me-2"></i>Удалить
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // Удаляем существующее модальное окно, если есть
    const existingModal = document.getElementById('deleteOrgModal');
    if (existingModal) {
        existingModal.remove();
    }

    // Добавляем новое модальное окно
    document.body.insertAdjacentHTML('beforeend', modalHtml);

    const modal = new bootstrap.Modal(document.getElementById('deleteOrgModal'));
    modal.show();

    // Обработчик кнопки подтверждения
    document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
        this.disabled = true;
        const originalText = this.innerHTML;
        this.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Удаление...';

        performDelete(id, modal, this, originalText);
    });

    // Удаляем модальное окно после закрытия
    document.getElementById('deleteOrgModal').addEventListener('hidden.bs.modal', function() {
        this.remove();
    });
}

// Функция выполнения удаления
function performDelete(id, modal = null, button = null, originalButtonText = '') {
    console.log('Attempting to delete organization with ID:', id);

    // ИЗМЕНЕНО: используем API эндпоинт
    fetch(`/api/organizations/${id}`, {
        method: 'DELETE',
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            console.log('Delete response status:', response.status);

            if (response.ok) {
                if (modal) modal.hide();
                showSuccessMessage('Организация успешно удалена');
                window.loadPage(currentPage);
            } else if (response.status === 409) {
                if (modal) modal.hide();
                showErrorMessage('Невозможно удалить организацию, так как к ней привязаны события или олимпиады');
            } else if (response.status === 403) {
                if (modal) modal.hide();
                showErrorMessage('У вас нет прав для удаления этой организации');
            } else if (response.status === 404) {
                if (modal) modal.hide();
                showErrorMessage('Организация не найдена');
            } else {
                throw new Error(`Ошибка сервера: ${response.status}`);
            }
        })
        .catch(error => {
            console.error('Error deleting organization:', error);
            if (modal) modal.hide();
            showErrorMessage(`Произошла ошибка при удалении организации: ${error.message}`);
        })
        .finally(() => {
            if (button && originalButtonText) {
                button.disabled = false;
                button.innerHTML = originalButtonText;
            }
        });
}
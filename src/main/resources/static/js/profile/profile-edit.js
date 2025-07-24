class ProfileEditManager {
    constructor() {
        this.isEditingSelf = false;
        this.isAdmin = false;
        this.userId = null;
        this.currentTypeId = null;

        this.init();
    }

    init() {
        const container = document.querySelector('[data-profile-edit]');
        if (container) {
            this.isEditingSelf = container.dataset.isEditingSelf === 'true';
            this.isAdmin = container.dataset.isAdmin === 'true';
            this.userId = parseInt(container.dataset.userId);
            this.currentTypeId = parseInt(container.dataset.currentTypeId) || 0;
        }

        this.setupEventListeners();
        this.loadUserTypes();
    }

    setupEventListeners() {
        if (!this.isEditingSelf && this.isAdmin) {
            this.setupRoleChangeHandler();
            this.setupStatusToggleHandler();
            this.setupDeleteUserHandler();
            this.setupAdminFormSubmit();
        }

        if (this.isEditingSelf) {
            this.setupAvatarHandlers();
        }
    }

    setupRoleChangeHandler() {
        const roleSelect = document.getElementById('roleId');
        if (roleSelect) {
            roleSelect.addEventListener('change', () => {
                const userTypeSection = document.getElementById('userTypeSection');
                const typeSelect = document.getElementById('typeId');

                if (roleSelect.value === '2' || roleSelect.value === '3') {
                    userTypeSection.style.display = 'none';
                    typeSelect.removeAttribute('required');
                } else {
                    userTypeSection.style.display = 'block';
                    typeSelect.setAttribute('required', 'required');
                }
            });
        }
    }

    loadUserTypes() {
        const typeSelect = document.getElementById('typeId');
        if (!typeSelect) {
            return;
        }

        fetch('/api/dict/user-types')
            .then(response => response.json())
            .then(types => {
                typeSelect.innerHTML = '<option value="">Выберите тип</option>';
                types.forEach(type => {
                    const option = document.createElement('option');
                    option.value = type.id;
                    option.textContent = type.userTypeTranslations[0].translation;
                    if (this.currentTypeId === type.id) {
                        option.selected = true;
                    }
                    typeSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Ошибка загрузки типов:', error));
    }

    setupStatusToggleHandler() {
        const toggleBtn = document.getElementById('toggleStatusBtn');
        if (toggleBtn) {
            toggleBtn.addEventListener('click', async () => {
                const action = toggleBtn.textContent.includes('Заблокировать') ? 'заблокировать' : 'разблокировать';

                if (!confirm(`Вы уверены, что хотите ${action} этого пользователя?`)) return;

                try {
                    const response = await fetch(`/api/users/${this.userId}`, {
                        method: 'PATCH',
                        headers: {
                            'X-CSRF-TOKEN': this.getCSRFToken()
                        }
                    });

                    if (response.ok) {
                        alert(`Пользователь успешно ${action === 'заблокировать' ? 'заблокирован' : 'разблокирован'}`);
                        window.location.reload();
                    } else {
                        alert('Ошибка при изменении статуса пользователя');
                    }
                } catch (error) {
                    console.error('Ошибка:', error);
                    alert('Произошла ошибка при изменении статуса');
                }
            });
        }
    }

    setupDeleteUserHandler() {
        const deleteBtn = document.getElementById('deleteUserBtn');
        if (deleteBtn) {
            deleteBtn.addEventListener('click', async () => {
                if (!confirm('Вы уверены, что хотите удалить этого пользователя? Это действие необратимо!')) return;

                try {
                    const response = await fetch(`/api/users/${this.userId}`, {
                        method: 'DELETE',
                        headers: {
                            'X-CSRF-TOKEN': this.getCSRFToken()
                        }
                    });

                    if (response.ok) {
                        alert('Пользователь успешно удален');
                        window.location.href = '/profile#users';
                    } else {
                        alert('Ошибка при удалении пользователя');
                    }
                } catch (error) {
                    console.error('Ошибка:', error);
                    alert('Произошла ошибка при удалении пользователя');
                }
            });
        }
    }

    setupAvatarHandlers() {
        const avatarForm = document.getElementById('avatarForm');
        const avatarFile = document.getElementById('avatarFile');
        const avatarPreview = document.getElementById('avatarPreview');

        if (avatarFile) {
            avatarFile.addEventListener('change', (e) => {
                const file = e.target.files[0];
                if (file && file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = (e) => {
                        const img = avatarPreview.querySelector('img');
                        if (img) {
                            img.src = e.target.result;
                            avatarPreview.style.display = 'block';
                        }
                    };
                    reader.readAsDataURL(file);
                }
            });
        }

        if (avatarForm) {
            avatarForm.addEventListener('submit', async (e) => {
                e.preventDefault();

                const file = avatarFile.files[0];
                if (!file) return;

                const formData = new FormData();
                formData.append('file', file);

                try {
                    const response = await fetch('/api/files?context=avatars', {
                        method: 'POST',
                        body: formData,
                        headers: {
                            'X-CSRF-TOKEN': this.getCSRFToken()
                        }
                    });

                    if (response.ok) {
                        const fileData = await response.json();
                        alert('Аватар успешно загружен!');
                        window.location.reload();
                    } else {
                        alert('Ошибка при загрузке аватара');
                    }
                } catch (error) {
                    console.error('Ошибка:', error);
                    alert('Произошла ошибка при загрузке аватара');
                }
            });
        }
    }

    setupAdminFormSubmit() {
        const editForm = document.getElementById('editProfileForm');
        if (editForm) {
            editForm.addEventListener('submit', async (e) => {
                e.preventDefault();

                const formData = new FormData(editForm);
                const userData = {
                    name: formData.get('name'),
                    surname: formData.get('surname'),
                    role: { id: parseInt(formData.get('role.id')) }
                };

                const typeSection = document.getElementById('userTypeSection');
                if (typeSection.style.display !== 'none' && formData.get('typeId')) {
                    userData.typeId = parseInt(formData.get('typeId'));
                }

                try {
                    const response = await fetch(`/api/users/${this.userId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': this.getCSRFToken()
                        },
                        body: JSON.stringify(userData)
                    });

                    if (response.ok) {
                        alert('Данные пользователя успешно обновлены!');
                        window.location.href = '/profile#users';
                    } else {
                        const error = await response.text();
                        alert('Ошибка при обновлении данных: ' + error);
                    }
                } catch (error) {
                    console.error('Ошибка:', error);
                    alert('Произошла ошибка при обновлении данных');
                }
            });
        }
    }

    getCSRFToken() {
        return document.querySelector('meta[name="_csrf"]')?.content || '';
    }
}

// Инициализация при загрузке DOM
document.addEventListener('DOMContentLoaded', () => {
    new ProfileEditManager();
});
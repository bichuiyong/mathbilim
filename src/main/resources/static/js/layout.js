function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

document.addEventListener('DOMContentLoaded', function () {
    const urlLang = new URLSearchParams(window.location.search).get('lang');
    const cookieLang = getCookie('lang');
    const currentLang = urlLang || cookieLang || 'ru';

    const langBtns = document.querySelectorAll('.lang-btn, .lang-btn-mobile');
    langBtns.forEach(btn => {
        btn.classList.remove('active');
        if (btn.href.includes('lang=' + currentLang)) {
            btn.classList.add('active');
        }
    });

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({behavior: 'smooth'});
            }
        });
    });


    document.querySelectorAll('.navbar-collapse .nav-link').forEach(link => {
        link.addEventListener('click', () => {
            const navbarCollapse = document.querySelector('.navbar-collapse');
            if (navbarCollapse.classList.contains('show')) {
                const bsCollapse = new bootstrap.Collapse(navbarCollapse, {
                    toggle: false
                });
                bsCollapse.hide();
            }
        });
    });
});

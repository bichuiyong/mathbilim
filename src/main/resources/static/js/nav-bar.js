document.addEventListener("DOMContentLoaded", function () {
    const toggleButton = document.querySelector(".navbar-toggler");
    const navbarNav = document.getElementById("navbarNav");
    const secondaryNav = document.getElementById("secondaryNav");
    const centeringStyles = document.createElement('style');
    centeringStyles.textContent = `
        @media (max-width: 991.98px) {
            #navbarNav .navbar-nav.mx-auto {
                text-align: center;
                width: 100%;
            }
            
            #navbarNav .navbar-nav.mx-auto .nav-item {
                text-align: center;
            }
            #secondaryNav .navbar-nav.left-padding {
                text-align: center;
                justify-content: center !important;
                padding-left: 0 !important;
                width: 100%;
            }
            
            #secondaryNav .navbar-nav.left-padding .nav-item {
                text-align: center;
            }
            #navbarNav .navbar-nav.ms-auto {
                text-align: center;
                align-items: center !important;
                margin-top: 15px;
                border-top: 1px solid #dee2e6;
                padding-top: 15px;
            }
            
            #navbarNav .navbar-nav.ms-auto .btn,
            #secondaryNav .navbar-nav.ms-auto .btn {
                width: 100%;
                margin: 5px 0;
            }
            
            #secondaryNav .navbar-nav.ms-auto {
                text-align: center;
                align-items: center !important;
                justify-content: center !important;
                margin-top: 15px;
                border-top: 1px solid #dee2e6;
                padding-top: 15px;
            }
        }
    `;
    document.head.appendChild(centeringStyles);

    function closeMenu() {
        navbarNav.classList.remove("show");
        secondaryNav.classList.remove("show");
    }

    toggleButton.addEventListener("click", function (e) {
        e.stopPropagation();
        navbarNav.classList.toggle("show");
        secondaryNav.classList.toggle("show");
    });

    const allNavLinks = document.querySelectorAll('#navbarNav .nav-link, #secondaryNav .nav-link');
    allNavLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            if (!this.classList.contains('dropdown-toggle')) {
                closeMenu();
            }
        });
    });


<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<@main.layout>
    <div class="pdf-reader-container">
        <div class="pdf-toolbar">
            <div class="toolbar-left">
                <a href="/books" class="btn btn-outline-light btn-sm back-btn">
                    <i class="fas fa-arrow-left me-1"></i> <@spring.message "pdf.back"/>
                </a>
                <div class="document-info">
                    <h5 class="mb-0 text-white ms-3 text-truncate"
                        style="max-width: 300px;"
                        title="${(fileDto.filename)!'<@spring.message "pdf.noTitleShort"/>'}">
                        ${(fileDto.filename)!'<@spring.message "pdf.noTitle"/>'}
                    </h5>
                </div>
            </div>

            <div class="toolbar-center">
                <div class="navigation-controls">
                    <button id="prevPage" class="btn btn-outline-light btn-sm" title="<@spring.message 'pdf.prev'/> (←)">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div class="page-info">
                        <input type="number" id="pageInput" class="page-input" value="1" min="1">
                        <span class="page-count">
                        <@spring.message "pdf.of"/> <span id="totalPages">0</span>
                    </span>
                    </div>
                    <button id="nextPage" class="btn btn-outline-light btn-sm" title="<@spring.message 'pdf.next'/> (→)">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </div>

            <div class="toolbar-right">
                <div class="toolbar-group">
                    <div class="zoom-controls">
                        <button id="zoomOut" class="btn btn-outline-light btn-sm" title="<@spring.message 'pdf.zoomOut'/> (-)">
                            <i class="fas fa-search-minus"></i>
                        </button>
                        <select id="zoomSelect" class="form-select form-select-sm">
                            <option value="0.5">50%</option>
                            <option value="0.75">75%</option>
                            <option value="1" selected>100%</option>
                            <option value="1.25">125%</option>
                            <option value="1.5">150%</option>
                            <option value="2">200%</option>
                            <option value="fit"><@spring.message "pdf.fitWidth"/></option>
                        </select>
                        <button id="zoomIn" class="btn btn-outline-light btn-sm" title="<@spring.message 'pdf.zoomIn'/> (+)">
                            <i class="fas fa-search-plus"></i>
                        </button>
                    </div>

                    <div class="divider"></div>

                    <button id="downloadBtn"
                            class="btn btn-outline-light btn-sm"
                            title="<@spring.message 'pdf.download'/>"
                            data-file-id="${(fileDto.id)!0}">
                        <i class="fas fa-download"></i>
                    </button>

                    <button id="fullscreen" class="btn btn-outline-light btn-sm" title="<@spring.message 'pdf.fullscreen'/> (F11)">
                        <i class="fas fa-expand"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="pdf-viewer-container">
            <div class="pdf-viewer" id="pdfViewer">
                <div class="loading-state">
                    <div class="spinner-grow text-primary" role="status">
                        <span class="visually-hidden"><@spring.message "pdf.loading"/></span>
                    </div>
                    <h5 class="mt-3"><@spring.message "pdf.loadingDocument"/></h5>
                    <p class="text-muted"><@spring.message "pdf.pleaseWait"/></p>
                </div>
            </div>
        </div>
    </div>


    <style>
        :root {
            --toolbar-height: 64px;
            --primary-color: #4e73df;
            --dark-bg: #2c3e50;
            --light-bg: #f8f9fa;
        }

        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
        }

        .pdf-reader-container {
            display: flex;
            flex-direction: column;
            height: 100vh;
            background-color: var(--light-bg);
        }

        .pdf-toolbar {
            background: linear-gradient(to right, #283e51, #485563);
            color: white;
            padding: 0.6rem 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.25);
            height: var(--toolbar-height);
            position: sticky;
            top: 0;
            z-index: 100;
            backdrop-filter: blur(4px);
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .pdf-toolbar .btn,
        .pdf-toolbar .form-select-sm {
            transition: background-color 0.2s ease, box-shadow 0.2s ease;
        }

        .pdf-toolbar .btn:hover {
            background-color: rgba(255, 255, 255, 0.1);
            box-shadow: 0 0 6px rgba(255, 255, 255, 0.3);
        }

        .pdf-toolbar .btn:active {
            background-color: rgba(255, 255, 255, 0.15);
        }

        .pdf-toolbar .btn i {
            font-size: 1rem;
            transition: transform 0.2s ease;
        }

        .pdf-toolbar .btn:hover i {
            transform: scale(1.15);
        }

        .toolbar-left {
            display: flex;
            align-items: center;
            gap: 1rem;
            min-width: 250px;
        }

        .back-btn {
            border-radius: 20px;
            padding: 0.4rem 1rem;
            font-weight: 500;
        }

        .document-info h5 {
            margin: 0;
            font-size: 1rem;
            font-weight: 600;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 300px;
            color: white;
        }

        .toolbar-center {
            flex: 1;
            display: flex;
            justify-content: center;
        }

        .navigation-controls {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .page-info {
            display: flex;
            align-items: center;
            background: rgba(255, 255, 255, 0.07);
            border-radius: 20px;
            padding: 0.25rem 0.75rem;
            box-shadow: inset 0 0 2px rgba(255, 255, 255, 0.1);
        }

        .page-input {
            width: 50px;
            background: transparent;
            border: none;
            color: white;
            text-align: center;
            font-weight: 600;
            padding: 0.25rem;
        }

        .page-input:focus {
            outline: none;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 4px;
        }

        .page-count {
            font-size: 0.9rem;
            opacity: 0.8;
            color: white;
        }

        .toolbar-right {
            display: flex;
            align-items: center;
            gap: 1rem;
            min-width: 300px;
            justify-content: flex-end;
        }

        .toolbar-group {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .divider {
            width: 1px;
            height: 24px;
            background: rgba(255, 255, 255, 0.2);
            margin: 0 0.5rem;
        }

        .zoom-controls {
            display: flex;
            align-items: center;
            gap: 0.25rem;
        }

        .form-select-sm {
            width: 90px;
            background-color: rgba(255, 255, 255, 0.1);
            border: none;
            color: white;
            padding: 0.25rem 0.5rem;
            border-radius: 20px;
            box-shadow: inset 0 0 2px rgba(255, 255, 255, 0.2);
        }

        .pdf-viewer-container {
            flex: 1;
            overflow: hidden;
            position: relative;
        }

        .pdf-viewer {
            height: 100%;
            overflow: auto;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 1.5rem;
            background: #525659;
        }

        .loading-state {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100%;
            color: white;
        }

        .spinner-grow {
            width: 3rem;
            height: 3rem;
        }

        .pdf-page-container {
            background: white;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
            margin-bottom: 1.5rem;
            border-radius: 4px;
            overflow: hidden;
        }

        .pdf-canvas {
            display: block;
            max-width: 100%;
            height: auto;
        }

        @media (max-width: 992px) {
            .document-title {
                max-width: 200px;
            }

            .toolbar-right {
                min-width: auto;
            }

            .form-select-sm {
                width: 80px;
            }
        }

        @media (max-width: 768px) {
            .pdf-toolbar {
                flex-wrap: wrap;
                height: auto;
                padding: 0.5rem;
            }

            .toolbar-left, .toolbar-center, .toolbar-right {
                width: 100%;
                justify-content: center;
                padding: 0.25rem 0;
            }

            .document-title {
                max-width: 100%;
            }

            .pdf-viewer {
                padding: 0.5rem;
            }
        }

        .pdf-reader-container:fullscreen {
            background: #000;
            width: 100%;
            height: 100%;
        }

        .pdf-reader-container:fullscreen .pdf-viewer {
            background: #000;
        }

        .pdf-reader-container:fullscreen .pdf-page-container {
            box-shadow: none;
        }
    </style>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>
    <script>
        class PDFReader {
            constructor(pdfUrl) {
                this.pdfDoc = null;
                this.currentPage = 1;
                this.totalPages = 0;
                this.scale = 1;
                this.canvas = null;
                this.context = null;
                this.pageContainer = null;
                this.pdfUrl = pdfUrl;
                this.pageCache = new Map();
                this.maxCacheSize = 5;
                this.renderingTask = null;

                this.initElements();
                this.bindEvents();

                pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js';

                this.loadPDF();
            }

            initElements() {
                this.prevBtn = document.getElementById('prevPage');
                this.nextBtn = document.getElementById('nextPage');
                this.pageInput = document.getElementById('pageInput');
                this.totalPagesSpan = document.getElementById('totalPages');
                this.zoomSelect = document.getElementById('zoomSelect');
                this.zoomInBtn = document.getElementById('zoomIn');
                this.zoomOutBtn = document.getElementById('zoomOut');
                this.fullscreenBtn = document.getElementById('fullscreen');
                this.viewer = document.getElementById('pdfViewer');
            }

            bindEvents() {
                this.prevBtn.addEventListener('click', () => this.previousPage());
                this.nextBtn.addEventListener('click', () => this.nextPage());
                this.pageInput.addEventListener('change', (e) => this.goToPage(parseInt(e.target.value)));
                this.pageInput.addEventListener('keypress', (e) => {
                    if (e.key === 'Enter') {
                        this.goToPage(parseInt(e.target.value));
                    }
                });
                this.zoomSelect.addEventListener('change', (e) => this.setZoom(e.target.value));
                this.zoomInBtn.addEventListener('click', () => this.zoomIn());
                this.zoomOutBtn.addEventListener('click', () => this.zoomOut());
                this.fullscreenBtn.addEventListener('click', () => this.toggleFullscreen());

                document.addEventListener('keydown', (e) => {
                    if (e.target.tagName === 'INPUT') return;

                    switch(e.key) {
                        case 'ArrowLeft':
                            e.preventDefault();
                            this.previousPage();
                            break;
                        case 'ArrowRight':
                            e.preventDefault();
                            this.nextPage();
                            break;
                        case 'F11':
                            e.preventDefault();
                            this.toggleFullscreen();
                            break;
                        case '+':
                        case '=':
                            e.preventDefault();
                            this.zoomIn();
                            break;
                        case '-':
                            e.preventDefault();
                            this.zoomOut();
                            break;
                    }
                });

                let lastWheelTime = 0;
                this.viewer.addEventListener('wheel', (e) => {
                    const now = Date.now();
                    if (Math.abs(e.deltaX) > Math.abs(e.deltaY)) return;

                    const isZoomGesture = e.ctrlKey || e.metaKey || (navigator.platform.includes('Mac') && e.altKey);

                    if (isZoomGesture || Math.abs(e.deltaY) > 50) {
                        e.preventDefault();
                        if (now - lastWheelTime < 100) return;
                        lastWheelTime = now;

                        if (e.deltaY < 0) {
                            this.zoomIn();
                        } else {
                            this.zoomOut();
                        }
                    }
                }, { passive: false });
            }

            showLoading() {
                this.viewer.innerHTML = `
                    <div class="loading-state">
                        <div class="spinner-grow text-primary" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                        <h5 class="mt-3">Loading document</h5>
                        <p class="text-muted">Please wait...</p>
                    </div>
                `;
            }

            async loadPDF() {
                this.showLoading();

                try {
                    const loadingTask = pdfjsLib.getDocument({
                        url: this.pdfUrl,
                        disableAutoFetch: false,
                        disableStream: false,
                        disableRange: false,
                        cMapPacked: true,
                        standardFontDataUrl: 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/standard_fonts/',
                        maxImageSize: 16777216,
                        disableFontFace: false,
                        useSystemFonts: true
                    });

                    this.pdfDoc = await loadingTask.promise;
                    this.totalPages = this.pdfDoc.numPages;
                    this.currentPage = 1;

                    this.updateUI();
                    await this.renderPage();
                } catch (error) {
                    console.error('Error loading PDF:', error);
                }
            }

            updateUI() {
                this.totalPagesSpan.textContent = this.totalPages;
                this.pageInput.value = this.currentPage;
                this.pageInput.max = this.totalPages;

                this.prevBtn.disabled = this.currentPage <= 1;
                this.nextBtn.disabled = this.currentPage >= this.totalPages;

                this.prevBtn.classList.toggle('disabled', this.currentPage <= 1);
                this.nextBtn.classList.toggle('disabled', this.currentPage >= this.totalPages);
            }

            getCacheKey(pageNum, scale) {
                return pageNum + '-' + scale;
            }

            addToCache(pageNum, scale, imageData) {
                const key = this.getCacheKey(pageNum, scale);

                if (this.pageCache.size >= this.maxCacheSize) {
                    const firstKey = this.pageCache.keys().next().value;
                    this.pageCache.delete(firstKey);
                }

                this.pageCache.set(key, imageData);
            }

            getFromCache(pageNum, scale) {
                const key = this.getCacheKey(pageNum, scale);
                return this.pageCache.get(key);
            }

            clearCache() {
                this.pageCache.clear();
            }

            async renderPage() {
                if (!this.pdfDoc) return;

                if (this.renderingTask) {
                    this.renderingTask.cancel();
                }

                try {
                    const page = await this.pdfDoc.getPage(this.currentPage);

                    if (!this.canvas) {
                        this.viewer.innerHTML = '<div class="pdf-page-container"></div>';
                        this.pageContainer = this.viewer.querySelector('.pdf-page-container');
                        this.canvas = document.createElement('canvas');
                        this.canvas.className = 'pdf-canvas';
                        this.context = this.canvas.getContext('2d');
                        this.pageContainer.appendChild(this.canvas);
                    }

                    let viewport = page.getViewport({ scale: this.scale });

                    if (this.zoomSelect.value === 'fit') {
                        const containerWidth = this.viewer.clientWidth - 80;
                        this.scale = containerWidth / viewport.width;
                        viewport = page.getViewport({ scale: this.scale });
                    }

                    const cachedData = this.getFromCache(this.currentPage, this.scale);
                    if (cachedData) {
                        this.canvas.width = cachedData.width;
                        this.canvas.height = cachedData.height;
                        this.context.putImageData(cachedData.imageData, 0, 0);
                        this.viewer.scrollTop = 0;
                        return;
                    }

                    const maxCanvasSize = 4096;
                    if (viewport.width > maxCanvasSize || viewport.height > maxCanvasSize) {
                        const scaleFactor = Math.min(maxCanvasSize / viewport.width, maxCanvasSize / viewport.height);
                        viewport = page.getViewport({ scale: this.scale * scaleFactor });
                    }

                    this.canvas.width = viewport.width;
                    this.canvas.height = viewport.height;

                    this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);

                    const renderContext = {
                        canvasContext: this.context,
                        viewport: viewport,
                        intent: 'display',
                        renderInteractiveForms: false,
                        transform: null,
                        imageLayer: null,
                        canvasFactory: null,
                        background: 'rgba(255,255,255,1.0)'
                    };

                    this.renderingTask = page.render(renderContext);
                    await this.renderingTask.promise;

                    try {
                        const imageData = this.context.getImageData(0, 0, this.canvas.width, this.canvas.height);
                        this.addToCache(this.currentPage, this.scale, {
                            imageData: imageData,
                            width: this.canvas.width,
                            height: this.canvas.height
                        });
                    } catch (e) {
                        console.warn('Failed to cache page:', e);
                    }

                    this.viewer.scrollTop = 0;
                    this.renderingTask = null;
                    page.cleanup();

                } catch (error) {
                    if (error.name === 'RenderingCancelledException') {
                        console.log('Rendering cancelled');
                        return;
                    }
                    console.error('Error rendering page:', error);
                }
            }

            previousPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                    this.updateUI();
                    this.renderPage();
                }
            }

            nextPage() {
                if (this.currentPage < this.totalPages) {
                    this.currentPage++;
                    this.updateUI();
                    this.renderPage();
                }
            }

            goToPage(pageNum) {
                if (pageNum >= 1 && pageNum <= this.totalPages) {
                    this.currentPage = pageNum;
                    this.updateUI();
                    this.renderPage();
                }
            }

            setZoom(value) {
                this.clearCache();

                if (value === 'fit') {
                    this.renderPage();
                } else {
                    this.scale = parseFloat(value);
                    this.renderPage();
                }
            }

            zoomIn() {
                const currentScale = this.scale;
                const scales = [0.5, 0.75, 1, 1.25, 1.5, 2];
                const currentIndex = scales.findIndex(s => s >= currentScale);

                if (currentIndex < scales.length - 1) {
                    this.clearCache();
                    this.scale = scales[currentIndex + 1];
                    this.zoomSelect.value = this.scale.toString();
                    this.renderPage();
                }
            }

            zoomOut() {
                const currentScale = this.scale;
                const scales = [0.5, 0.75, 1, 1.25, 1.5, 2];
                const currentIndex = scales.findIndex(s => s >= currentScale);

                if (currentIndex > 0) {
                    this.clearCache();
                    this.scale = scales[currentIndex - 1];
                    this.zoomSelect.value = this.scale.toString();
                    this.renderPage();
                }
            }

            toggleFullscreen() {
                if (!document.fullscreenElement) {
                    document.documentElement.requestFullscreen().catch(err => {
                        console.error('Error attempting to enable fullscreen:', err);
                    });
                } else {
                    document.exitFullscreen().catch(err => {
                        console.error('Error attempting to exit fullscreen:', err);
                    });
                }
            }

            destroy() {
                if (this.renderingTask) {
                    this.renderingTask.cancel();
                }
                this.clearCache();
                if (this.pdfDoc) {
                    this.pdfDoc.destroy();
                }
            }
        }

        document.addEventListener('DOMContentLoaded', function() {
            const pdfUrl = '${pdfUrl}';
            const pdfReader = new PDFReader(pdfUrl);
            window.pdfReader = pdfReader;

            window.addEventListener('beforeunload', () => {
                if (window.pdfReader) {
                    window.pdfReader.destroy();
                }
            });
        });

        document.getElementById('downloadBtn').addEventListener('click', function () {
            const fileId = this.getAttribute('data-file-id');
            if (!fileId || fileId === "0") {
                alert("Файл не найден для загрузки.");
                return;
            }

            const link = document.createElement('a');
            link.href = `/api/files/` + fileId + `/download`;
            link.download = '';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        });
    </script>
</@main.layout>
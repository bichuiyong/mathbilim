<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.4.120/pdf.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        #controls {
            margin-bottom: 10px;
        }

        #pdf-container {
            border: 1px solid #ccc;
            margin-top: 10px;
        }

        #pdf-canvas {
            display: block;
            margin: 0 auto;
            border: 1px solid #ddd;
        }

        #page-info {
            text-align: center;
            margin: 10px 0;
        }

        #search-box {
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div id="controls">
    <button id="prev-page">Предыдущая</button>
    <button id="next-page">Следующая</button>
    <span id="page-info">Страница: <span id="page-num">1</span> / <span id="page-count">0</span></span>
    <input type="number" id="page-jump" min="1" value="1" style="width: 50px;">
    <button id="go-to-page">Перейти</button>
    <select id="zoom-select">
        <option value="0.5">50%</option>
        <option value="0.75">75%</option>
        <option value="1" selected>100%</option>
        <option value="1.5">150%</option>
        <option value="2">200%</option>
    </select>
</div>



<div id="pdf-container">
    <canvas id="pdf-canvas"></canvas>
</div>

<script>

    pdfjsLib.GlobalWorkerOptions.workerSrc =
        'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.4.120/pdf.worker.min.js';

    let pdfDoc = null,
        pageNum = 1,
        pageRendering = false,
        pageNumPending = null,
        scale = 1.0,
        canvas = document.getElementById('pdf-canvas'),
        ctx = canvas.getContext('2d'),
        searchText = '',
        searchMatches = [],
        currentMatch = 0;

    const url = '/proxy/pdfUrl';

    function renderPage(num) {
        pageRendering = true;

        pdfDoc.getPage(num).then(function (page) {
            const viewport = page.getViewport({
                scale: scale
            });
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            const renderContext = {
                canvasContext: ctx,
                viewport: viewport
            };

            const renderTask = page.render(renderContext);

            renderTask.promise.then(function () {
                pageRendering = false;
                if (pageNumPending !== null) {
                    renderPage(pageNumPending);
                    pageNumPending = null;
                }
            });
        });

        document.getElementById('page-num').textContent = num;
    }

    function queueRenderPage(num) {
        if (pageRendering) {
            pageNumPending = num;
        } else {
            renderPage(num);
        }
    }

    document.getElementById('prev-page').addEventListener('click', function () {
        if (pageNum <= 1) return;
        pageNum--;
        queueRenderPage(pageNum);
    });

    document.getElementById('next-page').addEventListener('click', function () {
        if (pageNum >= pdfDoc.numPages) return;
        pageNum++;
        queueRenderPage(pageNum);
    });

    document.getElementById('go-to-page').addEventListener('click', function () {
        const page = parseInt(document.getElementById('page-jump').value);
        if (page >= 1 && page <= pdfDoc.numPages) {
            pageNum = page;
            queueRenderPage(pageNum);
        }
    });

    document.getElementById('zoom-select').addEventListener('change', function () {
        scale = parseFloat(this.value);
        queueRenderPage(pageNum);
    });


    pdfjsLib.getDocument(url).promise.then(function (pdf) {
        pdfDoc = pdf;
        document.getElementById('page-count').textContent = pdf.numPages;
        document.getElementById('page-jump').max = pdf.numPages;

        renderPage(pageNum);
    });
</script>
</body>

</html>
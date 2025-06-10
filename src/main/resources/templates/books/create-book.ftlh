<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container my-5">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0">Create New Book</h3>
            </div>
            <div class="card-body">
                <form action="/books/create" method="post" enctype="multipart/form-data">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>

                    <div class="mb-3">
                        <label for="title" class="form-label">Book Title</label>
                        <input type="text" class="form-control" id="title" name="name" placeholder="Enter book title" required>
                    </div>

                    <div class="mb-3">
                        <label for="authors" class="form-label">Authors (comma-separated)</label>
                        <input type="text" class="form-control" id="authors" name="author" placeholder="Enter author names, e.g., John Doe, Jane Smith" required>
                    </div>

                    <div class="mb-3">
                        <label for="attachments" class="form-label">Attachments</label>
                        <input type="file" class="form-control" id="attachments" name="attachments" multiple
                               accept=".pdf,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.zip,.rar,.7z,.jpg,.jpeg,.png,.gif,.bmp,.svg,.webp,.mp3,.wav,.flac,.mp4,.avi,.mkv,.mov">
                        <div class="form-text">
                            📎 Supported files: documents, images, archives, audio, video<br>
                            💡 Images can be inserted via editor or attached as files
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-select" id="category" name="category">
                            <option value="" selected disabled>Select a category</option>
                            <#if categories?? && categories?size gt 0>
                                <#list categories as category>
                                    <option value="${category.name}">${category.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>

                    <!-- Metadata Section -->
                    <div class="mb-3">
                        <h5 class="mb-3">Additional Metadata</h5>
                        <div id="metadataFields">
                            <div class="row g-3 mb-2 metadata-field">
                                <div class="col-md-5">
                                    <input type="text" class="form-control" name="metadataKeys" placeholder="Key (e.g., genre)">
                                </div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" name="metadataValues" placeholder="Value">
                                </div>
                                <div class="col-md-2">
                                    <button type="button" class="btn btn-danger w-100" onclick="removeMetadataField(this)">Remove</button>
                                </div>
                            </div>
                        </div>
                        <button type="button" class="btn btn-secondary mt-2" onclick="addMetadataField()">
                            Add More Metadata
                        </button>
                    </div>

                    <button type="submit" class="btn btn-success w-100">Create Book</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        function addMetadataField() {
            const container = document.getElementById('metadataFields');
            const newField = document.createElement('div');
            newField.className = 'row g-3 mb-2 metadata-field';
            newField.innerHTML = `
                <div class="col-md-5">
                    <input type="text" class="form-control" name="metadataKeys" placeholder="Key (e.g., edition)">
                </div>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="metadataValues" placeholder="Value">
                </div>
                <div class="col-md-2">
                    <button type="button" class="btn btn-danger w-100" onclick="removeMetadataField(this)">Remove</button>
                </div>
            `;
            container.appendChild(newField);
        }

        function removeMetadataField(button) {
            button.closest('.metadata-field').remove();
        }
    </script>
</@main.layout>
<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring/>

<@main.layout>
    <div class="container my-5">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0">Update the Book</h3>
            </div>
            <div class="card-body">
                <form action="/books/update" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${book.id}"/>
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </#if>

                    <div class="mb-3">
                        <label for="name" class="form-label">Book Title</label>
                        <@spring.formInput "book.name" 'class="form-control" id="name" placeholder="Enter book title"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
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
                        <#assign categoryOptions = {}>
                        <#list categories as category>
                            <#assign categoryOptions = categoryOptions + {category.id?string: category.translations[0].translation}>
                        </#list>


                        <label for="category" class="form-label">Category</label>
                        <@spring.formSingleSelect
                        "book.category.id",
                        categoryOptions,
                        'class="form-select" id="category"'/>
                        <@spring.showErrors '<br>',"text-danger small"/>
                    </div>


                    <!-- Metadata Section -->
                    <div class="mb-3">
                        <h5 class="mb-3">Additional Metadata</h5>
                        <div id="metadataFields">
                            <div class="row g-3 mb-2 metadata-field">
                                <div class="col-md-5">
                                    <select class="form-select metadata-key-select" name="metadataKeys">
                                        <#list metadataKeysEnum as key>
                                            <option value="${key}">${key.label}</option>
                                        </#list>
                                    </select>

                                </div>


                                <div class="col-md-5">
                                    <input type="text" class="form-control" name="metadataValues" placeholder="Value">
                                </div>
                                <div class="col-md-2">
                                    <button type="button" class="btn btn-danger w-100"
                                            onclick="removeMetadataField(this)">Remove
                                    </button>
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

    <template id="metadata-key-template">
        <select class="form-select metadata-key-select" name="metadataKeys">
            <#list metadataKeysEnum as key>
                <option value="${key}">${key.label}</option>
            </#list>
        </select>
    </template>



    <script>
        function updateMetadataKeyOptions() {
            const allSelects = document.querySelectorAll('.metadata-key-select');
            const selectedValues = new Set();

            allSelects.forEach(select => {
                const value = select.value;
                if (value) selectedValues.add(value);
            });

            allSelects.forEach(select => {
                const currentValue = select.value;
                Array.from(select.options).forEach(option => {
                    if (option.value !== currentValue && selectedValues.has(option.value)) {
                        option.disabled = true;
                    } else {
                        option.disabled = false;
                    }
                });
            });
        }

        function addMetadataField() {
            const container = document.getElementById('metadataFields');

            const keyTemplate = document.getElementById('metadata-key-template');
            const keySelect = keyTemplate.content.cloneNode(true).querySelector('select');

            keySelect.addEventListener('change', updateMetadataKeyOptions);

            const newField = document.createElement('div');
            newField.className = 'row g-3 mb-2 metadata-field';
            newField.innerHTML = `
            <div class="col-md-5 key-holder"></div>
            <div class="col-md-5">
                <input type="text" class="form-control" name="metadataValues" placeholder="Value">
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-danger w-100" onclick="removeMetadataField(this)">Remove</button>
            </div>
        `;

            newField.querySelector('.key-holder').appendChild(keySelect);
            container.appendChild(newField);

            updateMetadataKeyOptions();
        }

        function removeMetadataField(button) {
            button.closest('.metadata-field').remove();
            updateMetadataKeyOptions();
        }

        document.querySelectorAll('.metadata-key-select').forEach(select => {
            select.addEventListener('change', updateMetadataKeyOptions);
        });

        updateMetadataKeyOptions();
    </script>


</@main.layout>
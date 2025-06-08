package kg.edu.mathbilim.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FileType {
    PDF(1, "PDF Document", "application/pdf", "pdf", FileCategory.DOCUMENT),
    JPEG(2, "JPEG Image", "image/jpeg", "jpg", FileCategory.IMAGE),
    PNG(3, "PNG Image", "image/png", "png", FileCategory.IMAGE),
    MP4(4, "MP4 Video", "video/mp4", "mp4", FileCategory.VIDEO),
    MP3(5, "MP3 Audio", "audio/mpeg", "mp3", FileCategory.AUDIO),
    ZIP(6, "ZIP Archive", "application/zip", "zip", FileCategory.ARCHIVE),
    DOC(7, "Microsoft Word", "application/msword", "doc", FileCategory.DOCUMENT),
    XLS(8, "Microsoft Excel", "application/vnd.ms-excel", "xls", FileCategory.DOCUMENT),
    TXT(9, "Text Document", "text/plain", "txt", FileCategory.DOCUMENT),
    WEBP(10, "WebP Image", "image/webp", "webp", FileCategory.IMAGE),
    GIF(11, "GIF Image", "image/gif", "gif", FileCategory.IMAGE),
    SVG(12, "SVG Image", "image/svg+xml", "svg", FileCategory.IMAGE),
    DOCX(13, "Microsoft Word DOCX", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx", FileCategory.DOCUMENT),
    XLSX(14, "Microsoft Excel XLSX", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx", FileCategory.DOCUMENT),
    PPT(15, "Microsoft PowerPoint", "application/vnd.ms-powerpoint", "ppt", FileCategory.DOCUMENT),
    PPTX(16, "Microsoft PowerPoint PPTX", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx", FileCategory.DOCUMENT),
    AVI(17, "AVI Video", "video/avi", "avi", FileCategory.VIDEO),
    MOV(18, "MOV Video", "video/quicktime", "mov", FileCategory.VIDEO),
    WAV(19, "WAV Audio", "audio/wav", "wav", FileCategory.AUDIO),
    RAR(20, "RAR Archive", "application/x-rar-compressed", "rar", FileCategory.ARCHIVE),
    JSON(21, "JSON File", "application/json", "json", FileCategory.OTHER),
    CSV(22, "CSV File", "text/csv", "csv", FileCategory.OTHER);


    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("mimeType")
    private final String mimeType;

    @JsonProperty("extension")
    private final String extension;

    @JsonIgnore
    private final FileCategory category;

    FileType(Integer id, String name, String mimeType, String extension, FileCategory category) {
        this.id = id;
        this.name = name;
        this.mimeType = mimeType;
        this.extension = extension;
        this.category = category;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("mimeType")
    public String getMimeType() {
        return mimeType;
    }

    @JsonProperty("extension")
    public String getExtension() {
        return extension;
    }

    @JsonIgnore
    public FileCategory getCategory() {
        return category;
    }

    @JsonIgnore
    public String getCategoryName() {
        return category.name().toLowerCase();
    }

    public static FileType fromDatabaseId(Integer id) {
        return Arrays.stream(values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElse(null);
    }

    public static FileType fromMimeType(String mimeType) {
        return Arrays.stream(values())
                .filter(type -> type.mimeType.equalsIgnoreCase(mimeType))
                .findFirst()
                .orElse(null);
    }

    public static FileType fromExtension(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.stream(values())
                .filter(type -> type.extension.equalsIgnoreCase(extension))
                .findFirst()
                .orElse(null);
    }

    public static FileType determineFileType(String mimeType, String filename) {
        FileType byMime = fromMimeType(mimeType);
        return byMime != null ? byMime : fromExtension(filename);
    }

    @JsonCreator
    public static FileType fromJson(@JsonProperty("id") Integer id) {
        return fromDatabaseId(id);
    }

    public static List<FileType> getAllPublicTypes() {
        return Arrays.stream(values())
                .filter(Objects::nonNull)
                .toList();
    }
}

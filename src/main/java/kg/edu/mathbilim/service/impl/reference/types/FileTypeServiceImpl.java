package kg.edu.mathbilim.service.impl.reference.types;

import kg.edu.mathbilim.dto.reference.types.FileTypeDto;
import kg.edu.mathbilim.exception.nsee.FileTypeNotFoundException;
import kg.edu.mathbilim.mapper.reference.types.FileTypeMapper;
import kg.edu.mathbilim.model.reference.types.FileType;
import kg.edu.mathbilim.repository.reference.types.FileTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.types.FileTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileTypeServiceImpl implements FileTypeService {
    private final FileTypeRepository fileTypeRepository;
    private final FileTypeMapper fileTypeMapper;

    @Override
    @Cacheable("allFileTypes")
    public List<FileTypeDto> getAllTypes() {
        return fileTypeRepository.findAll()
                .stream()
                .map(fileTypeMapper::toDto)
                .toList();
    }


    @Cacheable("fileTypesByMime")
    @Override
    public FileTypeDto findByMimeType(String mimeType) {
        FileType fileType = fileTypeRepository.findByMimeTypeIgnoreCase(mimeType)
                .orElseThrow(() -> new FileTypeNotFoundException("File type not found: " + mimeType));
        return fileTypeMapper.toDto(fileType);
    }

    @Cacheable("fileTypesByExtension")
    @Override
    public FileTypeDto findByExtension(String extension) {
        String cleanExtension = extension.startsWith(".") ? extension.substring(1) : extension;
        FileType fileType = fileTypeRepository.findByExtensionIgnoreCase(cleanExtension.toLowerCase())
                .orElseThrow(() -> new FileTypeNotFoundException("File type not found: " + cleanExtension));
        return fileTypeMapper.toDto(fileType);
    }

    @Override
    public FileType determineFileTypeEntity(String mimeType, String filename) {
        return fileTypeMapper.toEntity(determineFileType(mimeType, filename));
    }

    @Override
    public FileTypeDto determineFileType(String mimeType, String filename) {
        log.debug("Определяем тип файла для MIME: {} и имени: {}", mimeType, filename);

        try {
            return findByMimeType(mimeType);
        } catch (NoSuchElementException e) {
            return findByExtension(filename);
        }
    }

    public boolean isImageType(String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public boolean isDocumentType(String mimeType) {
        if (mimeType == null) return false;

        return mimeType.equals("application/pdf") ||
                mimeType.contains("word") ||
                mimeType.contains("excel") ||
                mimeType.contains("powerpoint") ||
                mimeType.equals("text/plain") ||
                mimeType.equals("application/rtf");
    }

    public boolean isVideoType(String mimeType) {
        return mimeType != null && mimeType.startsWith("video/");
    }

    public boolean isAudioType(String mimeType) {
        return mimeType != null && mimeType.startsWith("audio/");
    }

    public boolean isArchiveType(String mimeType) {
        if (mimeType == null) return false;

        return mimeType.equals("application/zip") ||
                mimeType.equals("application/x-rar-compressed") ||
                mimeType.equals("application/x-7z-compressed") ||
                mimeType.equals("application/gzip") ||
                mimeType.equals("application/x-tar");
    }

    @Override
    public String getFileCategory(String mimeType) {
        if (isImageType(mimeType)) return "image";
        if (isDocumentType(mimeType)) return "document";
        if (isVideoType(mimeType)) return "video";
        if (isAudioType(mimeType)) return "audio";
        if (isArchiveType(mimeType)) return "archive";
        return "other";
    }
}

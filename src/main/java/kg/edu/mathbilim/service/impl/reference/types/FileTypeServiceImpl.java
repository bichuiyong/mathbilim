package kg.edu.mathbilim.service.impl.reference.types;

import kg.edu.mathbilim.dto.reference.types.FileTypeDto;
import kg.edu.mathbilim.mapper.reference.types.FileTypeMapper;
import kg.edu.mathbilim.repository.reference.types.FileTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.types.FileTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileTypeServiceImpl implements FileTypeService {
    private final FileTypeRepository fileTypeRepository;
    private final FileTypeMapper fileTypeMapper;

    @Override
    public List<FileTypeDto> getAllTypes() {
        return fileTypeRepository.findAll()
                .stream()
                .map(fileTypeMapper::toDto)
                .toList();
    }
}

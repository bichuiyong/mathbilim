package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.mapper.FileMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.repository.FileRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    private File getEntityById(Long id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    @Override
    public FileDto getById(Long id){
        return fileMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<FileDto> getFilePage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if (query == null || query.isEmpty()) {
            return getPage(() -> fileRepository.findAll(pageable));
        }
        return getPage(() -> fileRepository.findByQuery(query, pageable));
    }

    @Override
    public void delete(Long id){
        fileRepository.deleteById(id);
        log.info("Deleted file: {}", id);
    }

    private Page<FileDto> getPage(Supplier<Page<File>> supplier, String notFoundMessage) {
        Page<File> filePage = supplier.get();
        if (filePage.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        log.info("Получено {} файлов на странице", filePage.getSize());
        return filePage.map(fileMapper::toDto);
    }

    private Page<FileDto> getPage(Supplier<Page<File>> supplier) {
        return getPage(supplier, "Файлы не были найдены");
    }

}

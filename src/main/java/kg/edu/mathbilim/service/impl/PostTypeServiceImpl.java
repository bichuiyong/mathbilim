package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.PostTypeDto;
import kg.edu.mathbilim.mapper.PostTypeMapper;
import kg.edu.mathbilim.repository.PostTypeRepository;
import kg.edu.mathbilim.service.interfaces.PostTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostTypeServiceImpl implements PostTypeService {
    private final PostTypeRepository postTypeRepository;
    private final PostTypeMapper postTypeMapper;

    @Override
    public List<PostTypeDto> getAllTypes() {
        return postTypeRepository.findAll()
                .stream()
                .map(postTypeMapper::toDto)
                .toList();
    }
}

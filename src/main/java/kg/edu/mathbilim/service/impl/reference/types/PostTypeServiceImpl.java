package kg.edu.mathbilim.service.impl.reference.types;

import kg.edu.mathbilim.dto.reference.types.PostTypeDto;
import kg.edu.mathbilim.mapper.reference.types.PostTypeMapper;
import kg.edu.mathbilim.repository.reference.types.PostTypeRepository;
import kg.edu.mathbilim.service.interfaces.reference.types.PostTypeService;
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

package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.model.PostType;
import kg.edu.mathbilim.repository.PostTypeRepository;
import kg.edu.mathbilim.service.interfaces.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostTypeServiceImpl implements PostTypeService {
    private final PostTypeRepository postTypeRepository;

    @Override
    public PostType getPostTypeByName(String name) {
        return postTypeRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("such postType not found"));
    }

    @Override
    public List<PostType> getAllPostTypes() {
        return postTypeRepository.findAll();
    }
}

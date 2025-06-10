package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.PostTypeDto;
import kg.edu.mathbilim.mapper.PostTypeMapper;
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
    private final PostTypeMapper postTypeMapper;

    @Override
    public PostTypeDto getPostTypeByName(String name) {
        PostType postType =  postTypeRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("such postType not found"));
        return postTypeMapper.toDto(postType);
    }

    @Override
    public List<PostTypeDto> getAllPostTypes() {
        return postTypeRepository.findAll()
                .stream()
                .map(postTypeMapper::toDto)
                .toList();
    }

    @Override
    public boolean existByPostType(String postType) {
        return postTypeRepository.findByName(postType).isPresent();
    }

    @Override
    public PostTypeDto createPostType(PostTypeDto postTypeDto) {
        return postTypeMapper.toDto(postTypeRepository.save(postTypeMapper.toEntity(postTypeDto)));
    }

    @Override
    public void deletePostType(Integer postType) {
        PostType postType1 = postTypeRepository.findById(postType).orElseThrow(()-> new NoSuchElementException("not found"));
        postTypeRepository.delete(postType1);
    }

    @Override
    public PostTypeDto updatePostType(PostTypeDto postTypeDto) {
        return postTypeMapper.toDto(postTypeRepository.save(postTypeMapper.toEntity(postTypeDto)));
    }
}

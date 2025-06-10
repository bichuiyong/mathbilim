package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.exception.nsee.FileNotFoundException;
import kg.edu.mathbilim.exception.nsee.PostNotFoundException;
import kg.edu.mathbilim.mapper.PostMapper;
import kg.edu.mathbilim.model.Post;
import kg.edu.mathbilim.repository.PostRepository;
import kg.edu.mathbilim.service.interfaces.PostService;
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
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private Post getEntityById(Long id){
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public PostDto getById(Long id){
        return postMapper.toDto(getEntityById(id));
    }

    @Override
    public Page<PostDto> getPostPage(String query, int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        if(query == null || query.isEmpty()){
            return getPage(() -> postRepository.findAll(pageable));
        }
        return getPage(() -> postRepository.findByQuery(query, pageable));
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
        log.info("Deleted post: {}", id);
    }

    private Page<PostDto> getPage(Supplier<Page<Post>> supplier, String notFoundMessage) {
        Page<Post> page = supplier.get();
        if (page.isEmpty()) {
            throw new FileNotFoundException(notFoundMessage);
        }
        log.info("Получено {} постов на странице", page.getSize());
        return page.map(postMapper::toDto);
    }

    private Page<PostDto> getPage(Supplier<Page<Post>> supplier) {
        return getPage(supplier, "Посты не были найдены");
    }
    
}

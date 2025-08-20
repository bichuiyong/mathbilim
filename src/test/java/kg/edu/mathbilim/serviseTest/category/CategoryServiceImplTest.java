package kg.edu.mathbilim.serviseTest.category;

import kg.edu.mathbilim.mapper.reference.CategoryMapper;
import kg.edu.mathbilim.repository.reference.CategoryRepository;
import kg.edu.mathbilim.service.impl.reference.CategoryServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;




}


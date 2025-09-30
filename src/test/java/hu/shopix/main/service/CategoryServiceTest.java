package hu.shopix.main.service;

import hu.shopix.main.dto.CategoryResponse;
import hu.shopix.main.exception.ResourceNotFoundException;
import hu.shopix.main.mapper.CategoryMapper;
import hu.shopix.main.model.Category;
import hu.shopix.main.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryResponse response;

    @BeforeEach
    void setup() {
        category = Category.builder().id(1L).name("Electronics").build();
        response = CategoryResponse.builder().id(1L).name("Electronics").build();
    }

    @Test
    void testGetAllCategories() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Category> page = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(mapper.toResponse(category)).thenReturn(response);

        Page<CategoryResponse> result = categoryService.getAllCategories(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Electronics", result.getContent().get(0).getName());
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    void testGetCategoryById_Found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toResponse(category)).thenReturn(response);

        CategoryResponse result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(99L));
    }
}


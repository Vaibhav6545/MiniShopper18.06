
package com.example.demo.Service.impl;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {CategoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    /**
     * Method under test: {@link CategoryServiceImpl#fetchAllCategories()}
     */
    @Test
    void testFetchAllCategories() {
        // Arrange
        ArrayList<Category> categoryList = new ArrayList<>();
        when(categoryRepository.findAll()).thenReturn(categoryList);
        // Act
        List<Category> actualFetchAllCategoriesResult = categoryServiceImpl.fetchAllCategories();
        // Assert
        verify(categoryRepository).findAll();
        assertTrue(actualFetchAllCategoriesResult.isEmpty());
        assertSame(categoryList, actualFetchAllCategoriesResult);
    }
    /**
     * Method under test: {@link CategoryServiceImpl#fetchCategoryById(String)}
     */
    @Test
    void testFetchCategoryById() {
        // Arrange
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        when(categoryRepository.findByCategoryId(Mockito.<String>any())).thenReturn(category);
        // Act
        Category actualFetchCategoryByIdResult = categoryServiceImpl.fetchCategoryById("42");
        // Assert
        verify(categoryRepository).findByCategoryId(eq("42"));
        assertSame(category, actualFetchCategoryByIdResult);
    }
}
 
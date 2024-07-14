
package minishopper.Controller;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import minishopper.Entity.Category;
import minishopper.exception.ResourceNotFoundException;
import minishopper.Service.CategoryService;
import minishopper.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ProductService productService;
    
    @Test
    void testGetAllCategories() throws Exception {
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryService.fetchAllCategories()).thenReturn(categoryList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(categoryController).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"categoryId\":\"42\",\"categoryTitle\":\"Dr\",\"description\":\"The characteristics of someone or"
                                        + " something\"}]"));
    }
  
    @Test
    void testGetCategoryById() throws Exception {
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        when(categoryService.fetchCategoryById(Mockito.<String>any())).thenReturn(category);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/{categoryId}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(categoryController).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"categoryId\":\"42\",\"categoryTitle\":\"Dr\",\"description\":\"The characteristics of someone or something\"}"));
    }
   
    @Test
    void testGetCategoryById2() throws Exception {
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryService.fetchAllCategories()).thenReturn(categoryList);
        when(categoryService.fetchCategoryById(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/{categoryId}", "",
                "Uri Variables");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(categoryController).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"categoryId\":\"42\",\"categoryTitle\":\"Dr\",\"description\":\"The characteristics of someone or"
                                        + " something\"}]"));
    }
   
    @Test
    void testGetProductsByCategoryId() throws Exception {
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        when(categoryService.fetchCategoryById(Mockito.<String>any())).thenReturn(category);
        when(productService.getAllProductsByCategory(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/{categoryId}/products",
                "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(categoryController).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
 
 
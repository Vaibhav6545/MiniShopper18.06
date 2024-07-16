
package minishopper.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import minishopper.Entity.Category;
import minishopper.Service.CategoryService;
import minishopper.Service.ProductService;
import minishopper.exception.ResourceNotFoundException;

@ContextConfiguration(classes = { CategoryController.class })
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryControllerTest {
	@Autowired
	private CategoryController categoryController;
	@MockBean
	private CategoryService categoryService;
	@MockBean
	private ProductService productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllCategories_CategoriesFound() throws ResourceNotFoundException {
		// Mocking a list of categories
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(new Category("abc", "Category 1", "xyz"));
		categoryList.add(new Category("abc1", "Category 2", "xyzr"));
		// Mocking categoryService behavior
		when(categoryService.fetchAllCategories()).thenReturn(categoryList);
		// Test controller method
		ResponseEntity<List<Category>> response = categoryController.getAllCategories();
		// Assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(categoryList, response.getBody());
	}

	@Test
	public void testGetAllCategories_NoCategoriesFound() {
		// Mocking categoryService behavior for an empty list of categories
		List<Category> emptyList = new ArrayList<>();
		when(categoryService.fetchAllCategories()).thenReturn(emptyList);
		// Test controller method
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoryController.getAllCategories();
		});
		// Assert that ResourceNotFoundException is thrown
		assertEquals("Categories Not Found", exception.getMessage());
	}

	

	@Test
	public void testGetCategoryById_CategoryFound() throws ResourceNotFoundException {

		String categoryId = "1";

		Category category = new Category("abc", "Category 1", "xyz");

		when(categoryService.fetchCategoryById(categoryId)).thenReturn(category);

		ResponseEntity<Category> response = categoryController.getCategoryById(categoryId);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(category, response.getBody());
	}

	@Test
	public void testGetCategoryById_CategoryNotFound() {

		String categoryId = "2";

		when(categoryService.fetchCategoryById(categoryId)).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoryController.getCategoryById(categoryId);

		});
		assertEquals("Category Not Found", exception.getMessage());
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
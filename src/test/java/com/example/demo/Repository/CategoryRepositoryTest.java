package com.example.demo.Repository;

	import com.example.demo.Entity.Category;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
	import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
	import org.springframework.test.context.ContextConfiguration;
	@ContextConfiguration(classes = {CategoryRepository.class})
	@EnableAutoConfiguration
	@EntityScan(basePackages = {"com.example.demo.Entity"})
	@DataJpaTest
	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
	public class CategoryRepositoryTest {
	    @Autowired
	    private CategoryRepository categoryRepository;
	    /**
	     * Method under test: {@link CategoryRepository#findByCategoryId(String)}
	     */
	    @Test
	    void testFindByCategoryId() {
	        // Arrange
	        // TODO: Populate arranged inputs
	        String categoryId = "";
	        // Act
	        Category actualFindByCategoryIdResult = this.categoryRepository.findByCategoryId(categoryId);
	        // Assert
	        // TODO: Add assertions on result
	    }
	}
	 
	
	
	
	
	
	
	
	
	
	


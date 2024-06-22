package com.example.demo.Repository;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CartRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.demo.Entity"})
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {

	
	
	
	 @Autowired
	    private CartRepository cartRepository;

	    /**
	     * Method under test: {@link CartRepository#findByUser(User)}
	     */
	    @Test
	    void testFindByUser() {
	        // Arrange
	        // TODO: Populate arranged inputs
	        User user = null;

	        // Act
	        Cart actualFindByUserResult = this.cartRepository.findByUser(user);

	        // Assert
	        // TODO: Add assertions on result
	    }

	    /**
	     * Method under test: {@link CartRepository#findByCartId(String)}
	     */
	    @Test
	    void testFindByCartId() {
	        // Arrange
	        // TODO: Populate arranged inputs
	        String cartId = "";

	        // Act
	        Cart actualFindByCartIdResult = this.cartRepository.findByCartId(cartId);

	        // Assert
	        // TODO: Add assertions on result
	    }
	}

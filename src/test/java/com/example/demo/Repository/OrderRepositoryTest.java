
	package com.example.demo.Repository;
	import static org.mockito.Mockito.mock;
	import com.example.demo.Entity.Order;
	import com.example.demo.Entity.User;
	import java.sql.Date;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.List;
	import org.junit.jupiter.api.Disabled;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
	import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
	import org.springframework.test.context.ContextConfiguration;
	@ContextConfiguration(classes = {OrderRepository.class})
	@EnableAutoConfiguration
	@EntityScan(basePackages = {"com.example.demo.Entity"})
	@DataJpaTest
	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
	public class OrderRepositoryTest {
	    @Autowired
	    private OrderRepository orderRepository;
	    /**
	     * Method under test: {@link OrderRepository#findOrderByOrderId(String)}
	     */
	    @Test
	    void testFindOrderByOrderId() {
	        // Arrange
	        // TODO: Populate arranged inputs
	        String orderId = "";
	        // Act
	        Order actualFindOrderByOrderIdResult = this.orderRepository.findOrderByOrderId(orderId);
	        // Assert
	        // TODO: Add assertions on result
	    }
	    /**
	     * Method under test: {@link OrderRepository#findByUser(User)}
	     */
	    @Test
	    void testFindByUser() {
	        // Arrange
	        // TODO: Populate arranged inputs
	        User user = null;
	        // Act
	        List<Order> actualFindByUserResult = this.orderRepository.findByUser(user);
	        // Assert
	        // TODO: Add assertions on result
	    }
	}
	 


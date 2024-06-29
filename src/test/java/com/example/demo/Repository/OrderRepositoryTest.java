
	package minishopper.Repository;
	import static org.mockito.Mockito.mock;

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

import minishopper.Entity.Order;
import minishopper.Entity.User;
import minishopper.Repository.OrderRepository;
	@ContextConfiguration(classes = {OrderRepository.class})
	@EnableAutoConfiguration
	@EntityScan(basePackages = {"minishopper.Entity"})
	@DataJpaTest
	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
	public class OrderRepositoryTest {
	    @Autowired
	    private OrderRepository orderRepository;
	   
	    @Test
	    void testFindOrderByOrderId() {
	       
	        String orderId = "";
	        
	        Order actualFindOrderByOrderIdResult = this.orderRepository.findOrderByOrderId(orderId);
	     
	    }
	   
	    @Test
	    void testFindByUser() {
	       
	        User user = null;
	        
	        List<Order> actualFindByUserResult = this.orderRepository.findByUser(user);
	     
	    }
	}
	 


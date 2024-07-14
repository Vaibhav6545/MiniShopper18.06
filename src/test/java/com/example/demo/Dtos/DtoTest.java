package minishopper.Dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.jupiter.MockitoExtension;

import minishopper.dtos.ChangeOrderStatusDto;
import minishopper.dtos.CreateOrderRequestDto;
import minishopper.dtos.ExcelOrderDto;
import minishopper.dtos.JwtResponseDto;
import minishopper.dtos.LoginDto;
import minishopper.dtos.OrderDto;
import minishopper.dtos.OrderItemDto;
import minishopper.dtos.ProductDto;
import minishopper.dtos.UpdateOrderItemDto;
import minishopper.dtos.UserDto;
import minishopper.Entity.Address;
import minishopper.Entity.Cart;
import minishopper.Entity.CartItem;
import minishopper.Entity.Category;
import minishopper.Entity.Items;
import minishopper.Entity.LoginData;
import minishopper.Entity.Order;
import minishopper.Entity.OrderItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.dtos.AddItemToCartDto;
import minishopper.dtos.CartDto;
import minishopper.dtos.CartItemDto;
import minishopper.dtos.CategoryDto;

@ExtendWith(MockitoExtension.class)
public class DtoTest {
	
	@Test
	@DisplayName("Test All Dto")
	void testAllDtos() {
		
		
		BeanTester beanTester = new BeanTester();
		beanTester.testBean( AddItemToCartDto.class);
		beanTester.testBean( CartDto.class);
		beanTester.testBean( CartItemDto.class);
		beanTester.testBean( CategoryDto.class);
		beanTester.testBean( ChangeOrderStatusDto.class);
		beanTester.testBean( CreateOrderRequestDto.class);
		beanTester.testBean( ExcelOrderDto.class);
		beanTester.testBean( JwtResponseDto.class);
		beanTester.testBean( LoginDto.class);
		//beanTester.testBean( OrderDto.class);
		beanTester.testBean( OrderItemDto.class);
		beanTester.testBean( ProductDto.class);
		beanTester.testBean( UpdateOrderItemDto.class);
		beanTester.testBean( UserDto.class);
		beanTester.testBean( Cart.class);
		beanTester.testBean( CartItem.class);
		beanTester.testBean( Category.class);
		beanTester.testBean( Items.class);
		//beanTester.testBean( LoginData.class);
		//beanTester.testBean( Order.class);
		//beanTester.testBean( OrderItem.class);
		beanTester.testBean( Product.class);
		beanTester.testBean( User.class);
		beanTester.testBean( Address.class);
		
		
		
		
	}

}

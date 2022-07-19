package com.mongo.Ecom.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongo.Ecom.exception.BusinessException;
import com.mongo.Ecom.exception.NoSuchObjectExistException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Product;
import com.mongo.Ecom.repository.ProductRepository;
import com.mongo.Ecom.service.ProductService;

@SpringBootTest(classes = ProductService.class)
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceMethodTest {
	@Mock
	ProductRepository repository;

	@InjectMocks
	ProductService service;

	@Test
	public void addProductTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		Product product1 = new Product("2", "Keyboard", 450, "Image logo", "Color=silver,wired", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		list.add(product1);
		repository.save(product);
		repository.save(product1);
		List<Product> list1 = new ArrayList<>();
		when(repository.findByBrandId("1")).thenReturn(list);
		Product prod3 = new Product("4", "sdfsdf", 322, "sdfas", "dfg", "1");
		when(repository.save(prod3)).thenReturn(prod3);
		assertEquals(prod3, service.addProduct(prod3));
	}

	@Test(expected = NullPointerException.class)
	public void addProductBusinessExceptionTest() {
		Product pro = new Product();
		List<Product> list = new ArrayList<>();
//		when(repository.findByBrandId(anyString())).thenReturn(list);
		assertEquals(new BusinessException("400", "Bad Request. Brand is not present for your product"),
				service.addProduct(pro));
	}

	@Test
	public void findAllTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		Product product1 = new Product("2", "Keyboard", 450, "Image logo", "Color=silver,wired", "2");
		List<Product> list = new ArrayList<>();
		list.add(product1);
		list.add(product);
		when(repository.findAll()).thenReturn(list);
		assertEquals(list, service.findAll());
	}

	@Test(expected = RuntimeException.class)
	public void findAllExceptionTest() {
		List<Product> list = new ArrayList<>();
		when(repository.findAll()).thenReturn(list);
		assertEquals(new RuntimeException(), service.findAll());
	}

	@Test
	public void getProductByIdTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		Optional<Product> optional = list.stream().findFirst();
		when(repository.findById(product.getId())).thenReturn(optional);
		Product prodFound = service.getProductById(product.getId());
		assertEquals(product, prodFound);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getProductByIdExceptionTest() {
		Product prod = new Product("1", "sdf", 44, "image", "desc", "1");
//		when(repository.findById("2")).thenReturn(Optional.empty());
		assertEquals(new ObjectNotFoundException("404", "The product with Id " + "2" + " is not present."),
				service.getProductById(prod.getId()));
	}

	@Test
	public void getProductByNameTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		Optional<Product> optional = list.stream().findFirst();
		when(repository.findByName(product.getName())).thenReturn(optional);
		Product prodFound = service.getProductByName(product.getName());
		assertEquals(product, prodFound);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getProductByNameExceptionTest() {
		when(repository.findByName("Sandal")).thenReturn(Optional.empty());
		assertEquals(new ObjectNotFoundException("404", "The product with Name " + "Sandal" + " is not present."),
				service.getProductByName("Sandal"));
	}

	@Test
	public void getProductByBrandIdTest() {
		List<Product> list = new ArrayList<>();
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		Product product1 = new Product("2", "Keyboard", 450, "Image logo", "Color=silver,wired", "1");
		list.add(product1);
		list.add(product);
		when(repository.findByBrandId(product.getBrandId())).thenReturn(list);
		assertEquals(list, service.getProductByBrandId(product.getBrandId()));
	}

	@Test(expected = RuntimeException.class)
	public void getProductByBrandIdExceptionTest() {
		List<Product> list = new ArrayList<>();
		when(repository.findByBrandId("1")).thenReturn(list);
		assertEquals(new RuntimeException("The product List you are requesting with brand Id " + "1" + " is empty."),
				service.getProductByBrandId("1"));
	}

	@Test
	public void updateProductByIdTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		product.setName("Keyboard");
		Optional<Product> optional = list.stream().findFirst();
		when(repository.findById(product.getId())).thenReturn(optional);
		assertEquals(product, service.updateProductById(product));
	}

	@Test(expected = NoSuchObjectExistException.class)
	public void updateProductByIdExceptionTest() {
		Product prod = new Product(null, "Watch", 789, "watch image", "gold coolor", "1");
		List<Product> list = new ArrayList<>();
		Optional<Product> optional = list.stream().findAny();
		when(repository.findById(prod.getId())).thenReturn(optional);
		assertEquals(new NoSuchObjectExistException("204", "Product You are requesting is not present"),
				service.updateProductById(prod));

	}

	@Test
	public void deleteProductByIdTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		repository.save(product);
		repository.deleteById(product.getId());
		Optional<Product> list1 = list.stream().findFirst();
		when(repository.findById(product.getId())).thenReturn(list1);
		assertEquals("Product Deleted Successfully", service.deleteProductById(product.getId()));
	}

	@Test(expected = RuntimeException.class)
	public void deleteProductByIdExceptionTest() {
		when(repository.findById("1")).thenReturn(Optional.empty());
		assertEquals(new RuntimeException("Product you want to delete is not present. Please check again."),
				service.deleteProductById("1"));
	}

	@Test
	public void deleteProductByNameTest() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		repository.save(product);
		Optional<Product> optional = list.stream().findFirst();
		when(repository.findByName(product.getName())).thenReturn(optional);
		repository.delete(product);
//		assertNull(service.deleteProductByName(product.getName()));
		assertEquals("Product with Name " + product.getName() + " is now deleted successfully",
				service.deleteProductByName(product.getName()));
	}

	@Test
	public void deleteProductByBrandId() {
		Product product = new Product("1", "Mouse", 500, "Dell", "color=black,USB", "1");
		List<Product> list = new ArrayList<>();
		list.add(product);
		repository.save(product);
		repository.delete(product);
		when(repository.findByBrandId(product.getBrandId())).thenReturn(list);
		assertEquals("The Products with BrandID " + product.getBrandId() + " removed successfully",
				service.deleteProductByBrandId(product.getBrandId()));
	}

	@Test(expected = RuntimeException.class)
	public void deleteProductByBrandIdExceptionTest() {
		List<Product> list = new ArrayList<>();
		when(repository.findByBrandId(anyString())).thenReturn(list);
		assertEquals(null, service.deleteProductByBrandId(anyString()));
	}

}

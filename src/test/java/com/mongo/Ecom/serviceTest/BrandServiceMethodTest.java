package com.mongo.Ecom.serviceTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongo.Ecom.exception.BusinessException;
import com.mongo.Ecom.exception.EmptyInputException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Brand;
import com.mongo.Ecom.repository.BrandRepository;
import com.mongo.Ecom.service.BrandService;

@SpringBootTest(classes = BrandService.class)
@RunWith(MockitoJUnitRunner.class)
public class BrandServiceMethodTest {
	@Mock
	BrandRepository repository;

	@InjectMocks
	BrandService service;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	public Brand sample() {
		Brand b1 = new Brand();
		b1.setId("1");
		b1.setCategoryId("1");
		b1.setLogo("logoImage");
		b1.setName("Realme");
		b1.setProducts(null);
		return b1;
	}

	@Test
	public void findAllTest_returnsList() {
		when(repository.findAll()).thenReturn(Stream
				.of(new Brand("1", "Nike", "imageLink", "1", null), new Brand("2", "Puma", "ImageLogo", "2", null))
				.collect(Collectors.toList()));
		assertNotNull(service.findAll());
		assertEquals(2, service.findAll().size());
	}

	@Test(expected = RuntimeException.class)
	public void findAllExceptionTest() {
		List<Brand> list = new ArrayList<>();
		when(repository.findAll()).thenReturn(list);
		assertEquals(new RuntimeException("The Brand List you are requesting is empty."), service.findAll());
	}

//	@Test
//	public void getBrandByIdTest() {
//		Brand brand = new Brand("1", "Nike", "logoimage", "1", null);
//		List<Brand> list = new ArrayList<>();
//		list.add(brand);
//		Optional<Brand> optional = list.stream().findFirst();
//		when(repository.findById("1")).thenReturn(optional);
//		Brand brandFound = service.getBrandById("1");
//		assertEquals(brand, brandFound);
//	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBrandByIdExceptionTest() {
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		assertEquals(new ObjectNotFoundException(), service.getBrandById(anyString()));
	}

	@Test
	public void getBrandByNameTest() {
		Brand brand = new Brand("1", "Nike", "logoimage", "1", null);
		List<Brand> list = new ArrayList<>();
		list.add(brand);
		Optional<Brand> optional = list.stream().findFirst();
		when(repository.findByName(brand.getName())).thenReturn(optional);
		Brand brandFound = service.getBrandByName(brand.getName());
		assertEquals(brand, brandFound);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBrandByNameExceptionTest() {
		Brand brand = new Brand();
		when(repository.findByName(anyString())).thenReturn(Optional.empty());
		System.out.println(service.getBrandByName("sf"));
		assertEquals(new Brand(), service.getBrandByName(anyString()));
	}

	@Test
	public void getBrandByCategoryIdTest() {
		Brand brand = new Brand("1", "Nike", "logoimage", "1", null);
		List<Brand> list = new ArrayList<>();
		list.add(brand);
		list.add(sample());
//		Optional<Brand> optional= list.stream().findFirst();
		when(repository.findByCategoryId(brand.getCategoryId())).thenReturn(list);
//		when(repository.findByCategoryId(brand.getCategoryId())).thenReturn(optional);
		assertEquals(service.getBrandByCategoryId(brand.getCategoryId()), list);
	}

	@Test(expected = RuntimeException.class)
	public void getBrandByCategoryIdExceptionTest() {
		List<Brand> list = new ArrayList<>();
		when(repository.findByCategoryId(anyString())).thenReturn(list);
		assertEquals(new RuntimeException(), service.getBrandByCategoryId(anyString()));
	}

	@Test
	public void updateBrandByIdTest() {
		Brand brand = new Brand("2", "Sundaram", "logoImage", "2", null);
		List<Brand> list = new ArrayList<>();
		brand.setName("Sparx");
		list.add(brand);
		Optional<Brand> optional = list.stream().findFirst();
		when(repository.findById(brand.getId())).thenReturn(optional);
		assertEquals(optional.get(), service.updateBrandById(brand));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void updateBrandByIdExceptionTest() {
//		when(repository.findById(anyString())).thenReturn(Optional.empty());
		assertEquals(new NoSuchElementException(), service.updateBrandById(new Brand()));
	}

	@Test
	public void deleteBrandByIdTest() {
		Brand brand = new Brand("2", "Sundaram", "logoImage", "2", null);
		List<Brand> list = new ArrayList<>();
		list.add(brand);
		repository.save(brand);
		repository.deleteById(brand.getId());
		assertNull(repository.findById(brand.getId()).orElse(null));
	}

	@Test
	public void deleteBrandByNameTest() {
		List<Brand> list = new ArrayList<>();
		Brand brand = new Brand("1", "Nike", "logoimage", "1", null);
		Brand brand1 = new Brand("2", "Sundaram", "logoImage", "2", null);
		list.add(brand);
		list.add(brand1);
		Optional<Brand> optional = list.stream().findFirst();
		when(repository.findByName(brand.getName())).thenReturn(optional);
		assertEquals("Brand Deleted Successfully", service.deleteBrandByName(brand.getName()));
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void deleteBrandByNameExceptionTest() {
		when(repository.findByName(anyString())).thenReturn(Optional.empty());
		assertEquals(new ObjectNotFoundException(), service.deleteBrandByName(anyString()));
	}

	@Test(expected = EmptyInputException.class)
	public void addBrandEmptyExceptionTest() {
		List<Brand> list = new ArrayList<>();
		Brand brand1 = new Brand("2", "Puma", "puma image", "3", null);
		Brand brand2 = new Brand("3", "Buffalo", "logo", "3", null);
		list.add(brand1);
		list.add(brand2);
		when(repository.findByCategoryId(anyString())).thenReturn(list);
		Brand brand = new Brand("", "adk", "logo", "1", null);
		when(service.addBrand(brand))
				.thenThrow(new EmptyInputException("204", "Input Field is Empty, please check again."));
		assertEquals(new EmptyInputException("204", "Input Field is Empty, please check again."),
				service.addBrand(brand));
	}
	
	@Test(expected = BusinessException.class)
	public void addBrandBusinessException() {
		List<Brand> list = new ArrayList<>();
//		when(repository.findByCategoryId(anyString())).thenReturn(list);
		assertEquals(new BusinessException(), service.addBrand(new Brand()));
		
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void addBrandObjectNotFoundExceptionTest() {
		List<Brand> list = new ArrayList<>();
		Brand brand1 = new Brand("2", "Puma", "puma image", "3", null);
		Brand brand2 = new Brand("3", "Buffalo", "logo", "3", null);
		list.add(brand1);
		list.add(brand2);
		Optional<Brand> optional = list.stream().findFirst();
		when(repository.findByCategoryId(anyString())).thenReturn(list);
		when(repository.findById(anyString())).thenReturn(optional);
		assertEquals(new ObjectNotFoundException(), service.addBrand(brand1));
	}

	@Test
	public void addBrandTest() {
		Brand brand = new Brand("1", "Sundaram", "image", "1", null);
		Brand brand1 = new Brand("2", "HP", "image", "1", null);
		List<Brand> list = new ArrayList<>();
		list.add(brand1);
		list.add(brand);
		when(repository.findByCategoryId("1")).thenReturn(list);
		Brand brand3 = new Brand("3", "Dell", "logo", "1", null);
		when(repository.save(brand3)).thenReturn(brand3);
		assertEquals(brand3, service.addBrand(brand3));
	}

}

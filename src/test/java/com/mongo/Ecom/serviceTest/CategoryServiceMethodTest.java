package com.mongo.Ecom.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.mongo.Ecom.exception.BusinessException;
import com.mongo.Ecom.exception.EmptyInputException;
import com.mongo.Ecom.exception.NoSuchObjectExistException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Category;
import com.mongo.Ecom.repository.CategoryRepository;
import com.mongo.Ecom.service.CategoryService;

//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceMethodTest {
	@Mock
	CategoryRepository repository;

	@InjectMocks
	CategoryService service;

	List<Category> lc = new ArrayList<>();

//	@Before
//	public void init() {
//
//		Category c = new Category();
//		c.setId("2");
//		c.setCategoryName("ABCD");
//		c.setCategoryEnabled(false);
//		c.setBrands(null);
//
//		lc.add(c);
//	}
//
//	@Before
//	public void initMocks() {
//		MockitoAnnotations.initMocks(this);
//	}

	public Category Sample() {

		Category c1 = new Category();
		c1.setId("2");
		c1.setCategoryName("Clothing");
		c1.setCategoryEnabled(false);
		c1.setBrands(null);

		return c1;
	}

	@Test
	public void findAllTest_thenCategoryListShouldBeReturned() {
		when(repository.findAll()).thenReturn(
				Stream.of(new Category("1", "Clothing", true, null), new Category("2", "Electronics", false, null))
						.collect(Collectors.toList()));
		assertNotNull(service.findAll());
		assertEquals(2, service.findAll().size());
	}

	@Test(expected = BusinessException.class)
	public void findAllTest_thenCategoryListShouldBeReturnedException() {
//		List<Category> category = new ArrayList<>();
//		when(repository.findAll()).thenReturn(category.isEmpty())).;
//		assertNotNull(service.findAll());
		when(service.findAll().isEmpty())
				.thenThrow(new BusinessException("404", "The Category you want to retrieve is not present"));
		assertEquals(0, service.findAll().size());
	}

	@Test
	public void getByIdTest() {
		Category cat = new Category();
		cat.setId("1");
		cat.setCategoryName("footab");
		cat.setCategoryEnabled(false);
		cat.setBrands(null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> c = list.stream().findFirst();

		when(repository.findById(cat.getId())).thenReturn(c);
		assertEquals(service.getCategoryById(cat.getId()), c);
	}

	@Test(expected = EmptyInputException.class)
	public void getCategoryByIdTest() {
		Category cat = new Category();
		List<Category> list = new ArrayList<>();
		Optional<Category> category = list.stream().findFirst();
		when(repository.findById(cat.getId())).thenReturn(category);
//		System.out.println("calue = "+service.getCategoryById(cat.getId()));
		assertEquals(EmptyInputException.class, service.getCategoryById(cat.getId()));
	}

	@Test
	public void getCategoryByNameTest() {
		Category cat = new Category();
		cat.setId("1");
		cat.setCategoryName("footab");
		cat.setCategoryEnabled(false);
		cat.setBrands(null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> c = list.stream().findFirst();

//		when(repository.findByCategoryName("footab")).thenReturn(cat);
		given(repository.findByCategoryName(cat.getCategoryName())).willReturn(c);
		assertEquals(service.getCategoryByCategoryName(cat.getCategoryName()), c.get());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getCategoryByCategoryNameExceptionTest() {
		when(repository.findByCategoryName("Electronics")).thenReturn(Optional.empty());
		assertEquals(new ObjectNotFoundException("404", "Category with this name " + "Electronics" + " is not present"),
				service.getCategoryByCategoryName("Electronics"));
	}

	@Test
	public void getAllCategoryByCategoryEnabledTest() {
		List<Category> lc = new ArrayList<>();
		lc.add(Sample());

		when(repository.findByCategoryEnabled(Sample().isCategoryEnabled())).thenReturn(lc);
		assertEquals(service.getAllCategoryByCategoryEnabled(Sample().isCategoryEnabled()), lc);
	}

	@Test(expected = RuntimeException.class)
	public void getAllCategoryByCategoryEnabledExceptionTest() {
		List<Category> list = new ArrayList<>();
		when(repository.findByCategoryEnabled(false)).thenReturn(list);
		assertEquals(new RuntimeException("The Category list you are requesting is empty."),
				service.getAllCategoryByCategoryEnabled(false));
	}

	@Test
	public void savedCategory() {
		Category cat = new Category();
		cat.setId("4");
		cat.setCategoryName("Logic");
		cat.setCategoryEnabled(true);
		cat.setBrands(null);
		lc.add(cat);
		service.addCategory(cat);
		when(repository.save(cat)).thenReturn(cat);
		assertEquals(repository.save(cat), cat);
//		for (Category category : lc) {
//			assertEquals(cat, lc.get(0));
//		}
	}

	@Test(expected = EmptyInputException.class)
	public void addCategoryEmptyExceptionTest() {
		Category cat = new Category("", "input", false, null);
		when(service.addCategory(cat))
				.thenThrow(new EmptyInputException("204", "Input field is Empty, Please check again."));
		assertEquals(new EmptyInputException("204", "Input field is Empty, Please check again."),
				service.addCategory(cat));
	}

	@Test(expected = ObjectNotFoundException.class)
	public void addCategoryExceptionTest() {
		Category cat = new Category("1", "footware", false, null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> optional = list.stream().findFirst();
		when(repository.findById(anyString())).thenReturn(optional);
		assertEquals(new ObjectNotFoundException(), service.addCategory(cat));
	}

	@Test
	public void updateCategoryByIdTest() {
		Category cat = new Category();
		cat.setId("1");
		cat.setCategoryName("footab");
		cat.setCategoryEnabled(false);
		cat.setBrands(null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> optional = list.stream().findFirst();
		given(repository.findById(cat.getId())).willReturn(optional);
		assertEquals(service.updateCategoryById(cat), optional.get());
	}

	@Test(expected = NoSuchObjectExistException.class)
	public void updateCategoryByIdExceptionTest() {
		assertEquals(new NoSuchObjectExistException("400", "Bad Request. Category Requesting is not present"),
				service.updateCategoryById(new Category()));

	}

	@Test
	public void deleteCategoryByIdTest_deletesTheCategory() {
		Category cat = new Category();
		cat.setId("1");
		cat.setCategoryName("footab");
		cat.setCategoryEnabled(false);
		cat.setBrands(null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> optional = list.stream().findFirst();
		given(repository.findById(cat.getId())).willReturn(optional);
		assertEquals(service.deleteCategoryById(cat.getId()), "Category Deleted Successfully");
	}

	@Test(expected = RuntimeException.class)
	public void deleteCategoryByIdExceptionTest() {
		when(repository.findById(anyString())).thenReturn(Optional.empty());
		assertEquals(new RuntimeException("Category You want to delete is not present.Please check again."),
				service.deleteCategoryById(anyString()));
	}

	@Test
	public void deleteCategoryByNameTest_deletesTheCategory() {
		Category cat = new Category();
		cat.setId("1");
		cat.setCategoryName("footab");
		cat.setCategoryEnabled(false);
		cat.setBrands(null);
		List<Category> list = new ArrayList<>();
		list.add(cat);
		Optional<Category> optional = list.stream().findFirst();
		given(repository.findByCategoryName(cat.getCategoryName())).willReturn(optional);
		assertEquals(service.deleteCategoryByName(cat.getCategoryName()), "Category deleted Successfully");
	}

	@Test(expected = RuntimeException.class)
	public void deleteCategoryByNameExceptionTest() {
		when(repository.findByCategoryName(anyString())).thenReturn(Optional.empty());
		assertEquals(new RuntimeException("Category you want to delete is not persent. please check again."),
				service.deleteCategoryByName(anyString()));
	}

	@Test
	public void deleteCategoryByCategoryEnabledTest_ReturnsListWithSameStatus() {
		List<Category> list = new ArrayList<>(Arrays.asList(new Category("1", "Electronics", false, null),
				new Category("2", "Sports", false, null), new Category("3", "Clothing", false, null)));
		given(repository.findByCategoryEnabled(false)).willReturn(list);
		assertEquals(service.deleteCategoryByCategoryEnabled(false), "Category deleted Successfully");
	}

	@Test(expected = RuntimeException.class)
	public void deleteCategoryByCategoryEnabledExceptionTest() {
		List<Category> list = new ArrayList<>();
		when(repository.findByCategoryEnabled(false)).thenReturn(list);
		assertEquals(new RuntimeException("Category you want to deleted is not present. please check again."),
				service.deleteCategoryByCategoryEnabled(false));
	}

}

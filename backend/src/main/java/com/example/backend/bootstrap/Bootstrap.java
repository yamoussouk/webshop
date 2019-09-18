package com.example.backend.bootstrap;

import com.example.backend.converter.ProductToProductCommand;
import com.example.backend.converter.UserToUserCommand;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Bootstrap implements CommandLineRunner {

	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final RoleRepository roleRepository;
	private final OrdersRepository ordersRepository;
	private final ProductToProductCommand productConverter;
	private final UserToUserCommand userConverter;
	private final AuthorityRepository authorityRepository;

	public Bootstrap(ProductRepository productRepository, UserRepository userRepository,
			AuthorityRepository authorityRepository, ImageRepository imageRepository, RoleRepository roleRepository,
			OrdersRepository ordersRepository, ProductToProductCommand productConverter,
			UserToUserCommand userConverter) {
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.roleRepository = roleRepository;
		this.ordersRepository = ordersRepository;
		this.productConverter = productConverter;
		this.userConverter = userConverter;
		this.authorityRepository = authorityRepository;
	}

	private void deleteAllData() {
		productRepository.deleteAll();
		userRepository.deleteAll();
		// orderRepository.deleteAll();
	}

	Image i1 = new Image();
	Image i2 = new Image();
	Image i3 = new Image();
	Image i4 = new Image();
	Image i5 = new Image();
	Image i6 = new Image();
	Image i7 = new Image();
	Image i8 = new Image();
	Image i9 = new Image();
	Image i10 = new Image();
	Image i11 = new Image();
	Image i12 = new Image();
	Image i13 = new Image();
	Image i14 = new Image();
	Image i15 = new Image();
	Image i16 = new Image();

	Authority admin = new Authority(AuthorityName.ROLE_ADMIN);
	Authority user = new Authority(AuthorityName.ROLE_USER);

	private List<Product> setProducts() {
		List<Product> allProducts = new ArrayList<>();

		// TODO
		List<User> allUsers = new ArrayList<>();
		User user = new User();
		user.setFirstname("joseph");
		user.setLastname("tester");
		user.setEmail("tester@gmail.com");
		user.setPasswd("12345");
		User productOwner = userRepository.save(user);

		Product bmw = new Product();
		Set<String> categories = new HashSet<String>();
		categories.add(Categories.AUTÓ.name());
		bmw.setCategory(categories);
		bmw.setShortDescription("Bmw kocsi új eladó");
		bmw.setLongDescription("Bmw kocsi új eladó");
		bmw.setName("Eladó új bmw");
		bmw.setPrice(99.00);
		bmw.setQuantity(1);
		bmw.setOneImage(i7);
		bmw.setOneImage(i8);
		bmw.setOneImage(i9);

		Product kutya = new Product();
		categories.clear();
		categories.add(Categories.ÁLLAT.name());
		kutya.setCategory(categories);
		kutya.setShortDescription("Eladó husky kutya");
		kutya.setLongDescription("Eladó husky kutya");
		kutya.setName("Eladó husky");
		kutya.setPrice(23.00);
		kutya.setQuantity(2);
		kutya.setOneImage(i11);
		kutya.setOneImage(i14);
		kutya.setOneImage(i16);

		Product pc = new Product();
		categories.clear();
		categories.add(Categories.MOBIL.name());
		pc.setCategory(categories);
		pc.setShortDescription("Eladó használt pc");
		pc.setLongDescription("Eladó használt pc");
		pc.setName("Eladó pc");
		pc.setPrice(55.00);
		pc.setQuantity(1);
		pc.setOneImage(i1);
		pc.setOneImage(i2);
		pc.setOneImage(i3);

		Product mobil = new Product();
		categories.clear();
		categories.add(Categories.MOBIL.name());
		mobil.setCategory(categories);
		mobil.setShortDescription("Eladó használt nokia");
		mobil.setLongDescription("Eladó használt nokia");
		mobil.setName("Eladó mobil");
		mobil.setPrice(12.00);
		mobil.setQuantity(3);
		mobil.setOneImage(i4);
		mobil.setOneImage(i5);
		mobil.setOneImage(i6);

		allProducts.add(bmw);
		allProducts.add(kutya);
		allProducts.add(pc);
		allProducts.add(mobil);

		productRepository.saveAll(allProducts);
		return allProducts;
	}

	private List<User> setUsers() {
		List<User> allUsers = new ArrayList<>();
		User user = new User();
		user.setFirstname("admin");
		user.setLastname("joe");
		user.setEmail("admin@admin.hu");
		user.setPasswd("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi");
		user.setAuthority(admin);
		user.setEnabled(true);
		Long id = userRepository.save(user).getId();
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		role.setUserid(id);
		roleRepository.save(role);
		allUsers.add(user);
		return allUsers;
	}

	private List<Orders> setOrders() {
		List<Orders> orders = new ArrayList<>();
		Orders order = new Orders();
		Product prod1 = productRepository.findById(1L).get();
		User user = userRepository.findById(1L).get();
		order.setOneProduct(prod1);
		order.setPrice(99.0);
		order.setUser(user);
		order.setPurchaseTime(new Date());
		ordersRepository.save(order);
		orders.add(order);
		return orders;
	}

	private List<Image> setImages() {
		List<Image> i = new ArrayList<>();
		i1.setImageUrl("excel.jpg");
		// i1.setOneProduct(productRepository.findById(1L).get());
		imageRepository.save(i1);
		i2.setImageUrl("linux.jpg");
		// i2.setOneProduct(productRepository.findById(1L).get());
		imageRepository.save(i2);
		i3.setImageUrl("mongodb.png");
		// i3.setOneProduct(productRepository.findById(1L).get());
		imageRepository.save(i3);
		i4.setImageUrl("linux.jpg");
		// i4.setOneProduct(productRepository.findById(2L).get());
		imageRepository.save(i4);
		i5.setImageUrl("auto.jpg");
		// i5.setOneProduct(productRepository.findById(3L).get());
		imageRepository.save(i5);
		i6.setImageUrl("mongodb.png");
		// i6.setOneProduct(productRepository.findById(4L).get());
		imageRepository.save(i6);
		i7.setImageUrl("1.jpg");
		imageRepository.save(i7);
		i8.setImageUrl("2.jpg");
		imageRepository.save(i8);
		i9.setImageUrl("3.jpg");
		imageRepository.save(i9);
		i10.setImageUrl("4.jpg");
		imageRepository.save(i10);
		i11.setImageUrl("5.jpg");
		imageRepository.save(i11);
		i12.setImageUrl("6.jpg");
		imageRepository.save(i12);
		i13.setImageUrl("7.jpg");
		imageRepository.save(i13);
		i14.setImageUrl("8.jpg");
		imageRepository.save(i14);
		i15.setImageUrl("9.jpg");
		imageRepository.save(i15);
		i16.setImageUrl("10.jpg");
		imageRepository.save(i16);
		i.add(i1);
		i.add(i2);
		i.add(i3);
		i.add(i4);
		i.add(i5);
		i.add(i6);
		i.add(i7);
		i.add(i8);
		i.add(i9);
		i.add(i10);
		i.add(i11);
		i.add(i12);
		i.add(i13);
		i.add(i14);
		i.add(i15);
		i.add(i16);
		return i;
	}

	@Override
	public void run(String... args) throws Exception {
		deleteAllData();
		setAuthorities();
		setUsers();
		setImages();
		setProducts();
		// setImages();
		// setOrders();
	}

	private void setAuthorities() {
		authorityRepository.save(admin);
		authorityRepository.save(user);
	}
}
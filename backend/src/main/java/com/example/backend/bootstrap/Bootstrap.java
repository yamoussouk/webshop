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
	private final CategoryRepository categoryRepository;

	public Bootstrap(ProductRepository productRepository, UserRepository userRepository,
			AuthorityRepository authorityRepository, ImageRepository imageRepository, RoleRepository roleRepository,
			OrdersRepository ordersRepository, ProductToProductCommand productConverter,
			UserToUserCommand userConverter, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.roleRepository = roleRepository;
		this.ordersRepository = ordersRepository;
		this.productConverter = productConverter;
		this.userConverter = userConverter;
		this.authorityRepository = authorityRepository;
		this.categoryRepository = categoryRepository;
	}

	private void deleteAllData() {
		categoryRepository.deleteAll();
		productRepository.deleteAll();
		userRepository.deleteAll();
		// orderRepository.deleteAll();
	}

	Image i1 = new Image();
	Image i2 = new Image();
	Image i3 = new Image();
	Image i4 = new Image();
	Image i5 = new Image();

	Authority admin = new Authority(AuthorityName.ROLE_ADMIN);
	Authority user = new Authority(AuthorityName.ROLE_USER);

	String mealPlannerDescription = "<p>a4, a5, letter size, printable, digital file, instant download</p>\n" + 
	"Printable complete Meal planner - 18 pages. \n" +
	"* 17 planner pages & 1 cover page * \n\n" +
	"with... \n" +
	"--> monthly meal planner (1 or 2-page layouts) \n" +
	"--> weekly meal planner (1 or 2-page layouts) -- 4 types of layouts \n" +
	"--> recipe card -- 2 types of layouts \n" +
	"--> weekly shopping list \n" +
	"--> shopping list by food categories \n" +
	"--> inventory trackers for: freezer, fridge, pantry \n" +
	"--> monthly finances tracker for groceries \n" +
	"--> favorite meals cards \n\n" +
	"-Easy to fill out. \n" +
	"-Simple and cute pastel blue color \n" +
	"-The 18 pages can be seen in the pictures above. \n" +
	"-All pages are separate PDF files so you can print only those you need easily!! \n" +
	"-Weeks starting on Monday or Sunday \n" +
	"BOTH versions included for all weekly & monthly layouts! \n\n" +
	"•NOTE!: \n" +
	"-This purchase is a digital file, no physical product will be sent to You via mail. \n\n" +
	"•What You will receive: 3 ZIP files \n" +
	"---1 ZIP file: all planner pages in A4 size (210x297 mm // 8.3x11.7 inches) in PDF format \n" +
	"---1 ZIP file: all planner pages in A5 size (148,5x210 mm // 5.8x8.3 inches) in PDF format \n" +
	"---1 ZIP file: all planner pages in US Letter size (216x279 mm // 8.5x11 inches) in PDF format \n\n" +
	"•How it works: \n" +
	"---Purchase \n" +
	"---Etsy sends You download link \n" +
	"---Download \n" +
	"---Print PDF \n" +
	"---Enjoy! \n\n" +
	"This printable is for personal use only! \n\n" +
	"*************************************************************************************** \n\n" +
	"Same planner with one weekly&monthly layout only, for a lower price: \n" +
	"Compact Meal Planner \n" +
	"https://www.etsy.com/listing/594740887/compact-meal-planner-printable-meal \n\n" +
	"***************************************************************************************";

	Category printablePlanners = new Category("Printable Planners");
	Category inserts = new Category("Inserts");
	Category lifestylePlanners = new Category("Lifestyle Planners");
	Category monthlyPlanners = new Category("Monthly Planners");
	Category weeklyPlanners = new Category("Weekly Planners");
	Category dailyPlanners = new Category("Daily Planners");
	Set<Category> allCategories = new HashSet<Category>();

	private Set<Category> setCategories() {
		allCategories.add(printablePlanners);
		allCategories.add(inserts);
		allCategories.add(lifestylePlanners);
		allCategories.add(monthlyPlanners);
		allCategories.add(weeklyPlanners);
		allCategories.add(dailyPlanners);
		categoryRepository.saveAll(allCategories);
		return allCategories;
	}

	private List<Product> setProducts() {
		List<Product> allProducts = new ArrayList<>();
		Set<Category> categories = setCategories();

		// TODO
		List<User> allUsers = new ArrayList<>();
		User user = new User();
		user.setFirstname("joseph");
		user.setLastname("tester");
		user.setEmail("tester@gmail.com");
		user.setPasswd("12345");
		User productOwner = userRepository.save(user);

		Product completeMealPlanner = new Product();
		Set<Category> categoriescmp = new HashSet<Category>();
		categoriescmp.add(printablePlanners);
		categoriescmp.add(inserts);
		completeMealPlanner.setId(new Long(66667));
		completeMealPlanner.setQuantity(1);
		completeMealPlanner.setCategory(categoriescmp);
		completeMealPlanner.setLongDescription(mealPlannerDescription);
		completeMealPlanner.setName("Complete meal planner");
		completeMealPlanner.setPrice(5.00);
		completeMealPlanner.setOneImage(i1);
		completeMealPlanner.setOneImage(i2);
		completeMealPlanner.setOneImage(i3);
		completeMealPlanner.setOneImage(i4);
		completeMealPlanner.setOneImage(i5);

		Product completeBusinessPlanner = new Product();
		Set<Category> categoriescbp = new HashSet<Category>();
		categoriescbp.add(printablePlanners);
		categoriescbp.add(monthlyPlanners);
		categoriescbp.add(weeklyPlanners);
		completeBusinessPlanner.setId(new Long(66671));
		completeBusinessPlanner.setCategory(categoriescbp);
		completeBusinessPlanner.setQuantity(1);
		completeBusinessPlanner.setLongDescription(mealPlannerDescription);
		completeBusinessPlanner.setName("Complete fitness planner");
		completeBusinessPlanner.setPrice(5.00);
		completeBusinessPlanner.setOneImage(i1);
		completeBusinessPlanner.setOneImage(i2);
		completeBusinessPlanner.setOneImage(i3);
		completeBusinessPlanner.setOneImage(i4);
		completeBusinessPlanner.setOneImage(i5);

		Product completeFinancialPlanner = new Product();
		Set<Category> categoriescbp2 = new HashSet<Category>();
		categoriescbp2.add(printablePlanners);
		categoriescbp2.add(lifestylePlanners);
		categoriescbp2.add(dailyPlanners);
		completeFinancialPlanner.setId(new Long(66668));
		completeFinancialPlanner.setCategory(categoriescbp2);
		completeFinancialPlanner.setQuantity(1);
		completeFinancialPlanner.setLongDescription(mealPlannerDescription);
		completeFinancialPlanner.setName("Complete business planner");
		completeFinancialPlanner.setPrice(5.00);
		completeFinancialPlanner.setOneImage(i1);
		completeFinancialPlanner.setOneImage(i2);
		completeFinancialPlanner.setOneImage(i3);
		completeFinancialPlanner.setOneImage(i4);
		completeFinancialPlanner.setOneImage(i5);

		Product dailyFinancialPlanner = new Product();
		Set<Category> categoriesdmp = new HashSet<Category>();
		categoriesdmp.add(printablePlanners);
		categoriesdmp.add(inserts);
		categoriesdmp.add(dailyPlanners);
		dailyFinancialPlanner.setId(new Long(66669));
		dailyFinancialPlanner.setCategory(categoriesdmp);
		dailyFinancialPlanner.setQuantity(1);
		dailyFinancialPlanner.setLongDescription(mealPlannerDescription);
		dailyFinancialPlanner.setName("Daily meal planner");
		dailyFinancialPlanner.setPrice(5.00);
		dailyFinancialPlanner.setOneImage(i1);
		dailyFinancialPlanner.setOneImage(i2);
		dailyFinancialPlanner.setOneImage(i3);
		dailyFinancialPlanner.setOneImage(i4);
		dailyFinancialPlanner.setOneImage(i5);

		Product dailyBusinessPlanner = new Product();
		Set<Category> categoriesdfp = new HashSet<Category>();
		categoriesdfp.add(printablePlanners);
		categoriesdfp.add(dailyPlanners);
		categoriesdfp.add(lifestylePlanners);
		dailyBusinessPlanner.setId(new Long(66670));
		dailyBusinessPlanner.setCategory(categoriesdfp);
		dailyBusinessPlanner.setQuantity(1);
		dailyBusinessPlanner.setLongDescription(mealPlannerDescription);
		dailyBusinessPlanner.setName("Daily fitness planner");
		dailyBusinessPlanner.setPrice(5.00);
		dailyBusinessPlanner.setOneImage(i1);
		dailyBusinessPlanner.setOneImage(i2);
		dailyBusinessPlanner.setOneImage(i3);
		dailyBusinessPlanner.setOneImage(i4);
		dailyBusinessPlanner.setOneImage(i5);

		allProducts.add(completeMealPlanner);
		allProducts.add(completeBusinessPlanner);
		allProducts.add(completeFinancialPlanner);
		allProducts.add(dailyBusinessPlanner);
		allProducts.add(dailyFinancialPlanner);

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
		i1.setImageUrl("1.jpg");
		imageRepository.save(i1);
		i2.setImageUrl("2.jpg");imageRepository.save(i2);
		i3.setImageUrl("3.jpg");imageRepository.save(i3);
		i4.setImageUrl("4.jpg");imageRepository.save(i4);
		i5.setImageUrl("5.jpg");imageRepository.save(i5);
		i.add(i1);
		i.add(i2);
		i.add(i3);
		i.add(i4);
		i.add(i5);
		return i;
	}

	@Override
	public void run(String... args) throws Exception {
		deleteAllData();
		setAuthorities();
		setUsers();
		setImages();
		setProducts();
		//setImages();
		// setOrders();
	}

	private void setAuthorities() {
		authorityRepository.save(admin);
		authorityRepository.save(user);
	}
}
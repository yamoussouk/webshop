package com.example.backend.bootstrap;

import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.converter.UserToUserCommand;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Bootstrap implements CommandLineRunner {

	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;
	private final CategoryRepository categoryRepository;
	private final PlannerRepository plannerRepository;
	private final LogoRepository logoRepository;

	public Bootstrap(UserRepository userRepository,
			AuthorityRepository authorityRepository, ImageRepository imageRepository, RoleRepository roleRepository,
			PlannerToPlannerCommand productConverter,
			UserToUserCommand userConverter, CategoryRepository categoryRepository, PlannerRepository plannerRepository,
			LogoRepository logoRepository) {
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.roleRepository = roleRepository;
		this.authorityRepository = authorityRepository;
		this.categoryRepository = categoryRepository;
		this.plannerRepository = plannerRepository;
		this.logoRepository = logoRepository;
	}

	private void deleteAllData() {
		// categoryRepository.deleteAll();
		plannerRepository.deleteAll();
		// userRepository.deleteAll();
		imageRepository.deleteAll();
		logoRepository.deleteAll();
	}

	Authority admin = new Authority(AuthorityName.ROLE_ADMIN);
	Authority user = new Authority(AuthorityName.ROLE_USER);
	

	Category printablePlanners = new Category("Printable Planners");
	Category lifestylePlanners = new Category("Lifestyle Planners");
	Category monthlyPlanners = new Category("Monthly Planners");
	Category weeklyPlanners = new Category("Weekly Planners");
	Category dailyPlanners = new Category("Daily Planners");
	Category singleLogo = new Category("Single Logo");
	Category logoAll = new Category("Logo");
	Category logoSet = new Category("Logo Set");
	Category customLogo = new Category("Custom Logo");
	Set<Category> allCategories = new HashSet<Category>();

	private Set<Category> setCategories() {
		allCategories.add(printablePlanners);
		allCategories.add(lifestylePlanners);
		allCategories.add(monthlyPlanners);
		allCategories.add(weeklyPlanners);
		allCategories.add(dailyPlanners);
		allCategories.add(singleLogo);
		allCategories.add(logoAll);
		allCategories.add(logoSet);
		allCategories.add(customLogo);
		categoryRepository.saveAll(allCategories);
		return allCategories;
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

	@Override
	public void run(String... args) throws Exception {
		// setCategories();
		// deleteAllData();
		// setAuthorities();
		// setUsers();
		// setImages();
		// setPlanners();
		// setLogos();
	}

	private void setAuthorities() {
		authorityRepository.save(admin);
		authorityRepository.save(user);
	}
}
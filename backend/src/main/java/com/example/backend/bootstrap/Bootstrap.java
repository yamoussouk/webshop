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

	public Bootstrap(UserRepository userRepository,
			AuthorityRepository authorityRepository, ImageRepository imageRepository, RoleRepository roleRepository,
			PlannerToPlannerCommand productConverter,
			UserToUserCommand userConverter, CategoryRepository categoryRepository, PlannerRepository plannerRepository,
			LogoRepository logoRepository) {
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
}
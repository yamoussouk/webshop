package com.example.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.backend.command.LogoCommand;
import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.LogoToLogoCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.model.Coupon;
import com.example.backend.model.Discount;
import com.example.backend.model.SignUpEmail;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.CouponService;
import com.example.backend.service.CouponTaskExecutorService;
import com.example.backend.service.DiscountService;
import com.example.backend.service.DiscountTaskExecutorService;
import com.example.backend.service.LogoService;
import com.example.backend.service.PlannerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminController {

    @Autowired
    private PlannerService plannerService;
    @Autowired
    private LogoService logoService;
    @Autowired
    private SignUpRepository signUpRepository;
    @Autowired
    private PlannerToPlannerCommand plannerToPlannerCommand;
    @Autowired
    private LogoToLogoCommand logoToLogoCommand;

    private final DiscountTaskExecutorService discountTaskExecutorService;
    private final CouponService couponService;
    private final DiscountService discountService;
    private final CouponTaskExecutorService couponTaskExecutorService;

    public AdminController(
        final CouponService couponService,
        final DiscountService discountService) {
        this.couponService = couponService;
        this.discountService = discountService;
        this.discountTaskExecutorService = new DiscountTaskExecutorService(discountService);
        this.couponTaskExecutorService = new CouponTaskExecutorService(couponService);
    }

    @GetMapping("admin/planners/all")
    public List<PlannerCommand> getAllPlanners() {
        return this.plannerService.getPlanners(false)
            .stream()
            .map(p -> plannerToPlannerCommand.convert(p))
            .collect(Collectors.toList());
    }

    @GetMapping("admin/logos/all")
    public List<LogoCommand> getAllLogos() {
        return this.logoService.getLogos(false)
            .stream()
            .map(p -> logoToLogoCommand.convert(p))
            .collect(Collectors.toList());
    }

    @PostMapping("/admin/add/new/planner")
    public void addNewPlanner(@RequestParam("product") final String product, @RequestParam("imagefile") final MultipartFile[] file) {
        this.plannerService.savePlannerPreparator(product, file);
    }

    @PostMapping("/admin/add/new/logo")
    public void addNewLogo(@RequestParam("product") final String product, @RequestParam("imagefile") final MultipartFile[] file) {
        this.logoService.saveLogoPreparator(product, file);
    }

    @GetMapping("/admin/delete/planner/{id}")
    public void deletePlannerById(@PathVariable(name = "id") final String id) {
        this.plannerService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/delete/logo/{id}")
    public void deleteLogoById(@PathVariable(name = "id") final String id) {
        this.logoService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/enable/planner/{id}")
    public ResponseEntity<?> setPlannerEnabled(@PathVariable(name = "id") final String id) {
        this.plannerService.enablePlanner(id);
        return ResponseEntity.ok("Enabled changed");
    }

    @GetMapping("/admin/enable/logo/{id}")
    public ResponseEntity<?> setLogoEnabled(@PathVariable(name = "id") final String id) {
        this.logoService.enableLogo(id);
        return ResponseEntity.ok("Enabled changed");
    }

    @PostMapping("/admin/update/planner/")
    public PlannerCommand updatePlanner(@RequestParam("product") final String product, 
        @RequestParam("imagefile") final MultipartFile[] files, 
        @RequestParam(value="removed", required = false) final List<String> removed) {
        return this.plannerService.updatePlannerPreparator(product, removed, files);
    }

    @PostMapping("/admin/update/logo/")
    public LogoCommand updateLogo(@RequestParam("product") final String product, 
        @RequestParam("imagefile") final MultipartFile[] files, 
        @RequestParam(value="removed", required = false) final List<String> removed) {
        return this.logoService.updateLogo(product, files, removed);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> signUpEmail(@RequestParam("email") final String email) {
		final Optional<SignUpEmail> mail = this.signUpRepository.findByEmail(email);
		if (mail.isPresent()) {
			return new ResponseEntity<>("User is already signed up!", HttpStatus.OK);
        } else {
        	final SignUpEmail emailToSave = new SignUpEmail(email);
        	this.signUpRepository.save(emailToSave);
        	return new ResponseEntity<>("User is signed up!", HttpStatus.OK);
        }
    }
    
    @GetMapping("/admin/subscribers/all")
    public List<SignUpEmail> getAllSubscribers() {
        final List<SignUpEmail> subscribers = new ArrayList<>();
        this.signUpRepository.findAll().iterator().forEachRemaining(subscribers::add);
        return subscribers;
    }

    @PostMapping("/admin/discount/set")
    public ResponseEntity<?> setDiscount(
        @RequestParam("name") final String name,
        @RequestParam("percent") final int percent,
        @RequestParam("enabled") final int enabled,
        @RequestParam("from") final String from, 
        @RequestParam("to") final String to,
        @RequestParam("products") final List<Integer> products) {
         if(discountService.getDiscountByName(name) == null) {
            boolean found = this.discountTaskExecutorService.hasAnyProductFutureDiscount(products);
            if (!found) {
                this.discountTaskExecutorService.createTaskPreparator(products, name, percent, from, to);
                return new ResponseEntity<>("Discount saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("On given product there is a set discount in the future.", HttpStatus.OK);
            }
         } else {
            return new ResponseEntity<>("Discount with the given name exists", HttpStatus.OK);
         }
    }

    @GetMapping("/admin/discount/all")
    public Map<String, Map<String, String>> getAllDiscount() {
        final List<Discount> discounts = this.discountService.getAll();
        final Map<String, Map<String, String>> v = this.discountTaskExecutorService.getTasks();
        Map<String, Map<String, String>> t = this.discountService.mergeDiscounts(discounts, v);
        return t;
    }

    @GetMapping("/admin/discount/cancel/{id}")
    public Map<String, Map<String, String>> cancelDiscount(@PathVariable(name = "id") final String id) {
        return this.discountTaskExecutorService.cancelTask(id);
    }

    @GetMapping("/admin/delete/discount/{id}")
    public void deleteDiscountById(@PathVariable(name = "id") final String id) {
        this.discountTaskExecutorService.cancelTask(id);
        this.discountService.deleteDiscountById(Long.parseLong(id));
    }

    @PostMapping("/admin/enable/discount/{id}")
    public ResponseEntity<?> setDiscountEnabled(@PathVariable(name = "id") final String id, 
     @RequestParam("type") final String type) {
        if (type.equals("range")) {
            this.discountTaskExecutorService.cancelTask(id);
        }
        this.discountService.enableDiscount(id);
        return ResponseEntity.ok("Discount enable saved");
    }

    @GetMapping("/admin/coupon/all")
    public Map<String, Map<String, String>> getAllCoupons() {
        final List<Coupon> coupons = this.couponService.getAll();
        final Map<String, Map<String, String>> v = this.couponTaskExecutorService.getTasks();
        Map<String, Map<String, String>> t = couponService.mergeCoupons(coupons, v);
        return t;
    }

    @PostMapping("/admin/enable/coupon/{id}")
    public ResponseEntity<?> setCouponEnabled(@PathVariable(name = "id") final String id, 
     @RequestParam("type") final String type) {
        if (type.equals("range")) {
            this.couponTaskExecutorService.cancelTask(id);
        }
        this.couponService.enableCoupon(id);
        return ResponseEntity.ok("Coupon enable saved");
    }

    @GetMapping("/admin/delete/coupon/{id}")
    public void deleteCouponById(@PathVariable(name = "id") final String id) {
        this.couponTaskExecutorService.cancelTask(id);
        this.couponService.deleteCouponById(Long.parseLong(id));
    }

    @PostMapping("/admin/coupon/save")
    public Map<String, Map<String, String>> saveCoupon(@RequestBody final Coupon coupon) {
        this.couponService.saveCoupon(coupon);
        return getAllCoupons();
    }

    @PostMapping("/admin/coupon/set")
    public void setCoupon(
     @RequestParam("name") final String name,
     @RequestParam("percent") final int percent,
     @RequestParam(value = "from", required = false) final String from, 
     @RequestParam(value = "to", required = false) final String to) {
        this.couponTaskExecutorService.createTaskPreparator(name, percent, from, to);
    }
}
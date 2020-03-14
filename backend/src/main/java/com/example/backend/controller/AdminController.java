package com.example.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.command.LogoCommand;
import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.LogoToLogoCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.dto.LogoDto;
import com.example.backend.dto.PlannerDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Coupon;
import com.example.backend.model.Discount;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.Planner;
import com.example.backend.model.SignUpEmail;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.DiscountRepository;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.CouponService;
import com.example.backend.service.DiscountService;
import com.example.backend.service.CouponTaskExecutorService;
import com.example.backend.service.ImageService;
import com.example.backend.service.LogoService;
import com.example.backend.service.PlannerService;
import com.example.backend.service.ProductServiceImpl;
import com.example.backend.service.DiscountTaskExecutorService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private final ImageService imageService;
    private final PlannerService PlannerService;
    private final LogoService logoService;
    private final SignUpRepository signUpRepository;
    private final CategoryRepository categoryRepository;
    private final PlannerToPlannerCommand PlannerToPlannerCommand = new PlannerToPlannerCommand();
    private final LogoToLogoCommand logoToLogoCommand = new LogoToLogoCommand();
    private final DiscountTaskExecutorService discountTaskExecutorService;
    private final CouponService couponService;
    private final DiscountService discountService;
    private final CouponTaskExecutorService couponTaskExecutorService;
    private final ProductServiceImpl productService;

    public AdminController(final ImageService imageService, final PlannerService PlannerService, 
    final SignUpRepository signUpRepository, final CategoryRepository categoryRepository, 
    final LogoService logoService, final CouponService couponService, 
    final CouponTaskExecutorService couponTaskExecutorService, final DiscountService discountService,
    DiscountRepository discountRepository) {
        this.imageService = imageService;
        this.PlannerService = PlannerService;
        this.signUpRepository = signUpRepository;
        this.categoryRepository = categoryRepository;
        this.logoService = logoService;
        this.couponService = couponService;
        this.couponTaskExecutorService = couponTaskExecutorService;
        this.discountService = discountService;
        this.productService = new ProductServiceImpl(PlannerService, logoService);
        this.discountTaskExecutorService = new DiscountTaskExecutorService(discountRepository, discountService, this.productService);
    }

    @GetMapping("admin/planners/all")
    public List<PlannerCommand> getAllPlanners() {
        final List<Planner> products =  this.PlannerService.getPlanners(false);
        final List<PlannerCommand> returnedValue = new ArrayList<PlannerCommand>();
        for (final Planner p : products) {
            returnedValue.add(PlannerToPlannerCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("admin/logos/all")
    public List<LogoCommand> getAllLogos() {
        final List<Logo> products =  this.logoService.getLogos(false);
        final List<LogoCommand> returnedValue = new ArrayList<LogoCommand>();
        for (final Logo p : products) {
            returnedValue.add(logoToLogoCommand.convert(p));
        }
        return returnedValue;
    }

    @PostMapping("/admin/add/new/planner")
    public void addNewPlanner(@RequestParam("product") final String product, @RequestParam("imagefile") final MultipartFile[] file) {
        final Planner p = mapPlannerDto(product, null);
        final List<Image> fileSet = new ArrayList<Image>();
        for (final MultipartFile i : file) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.PlannerService.savePlanner(p);
    }

    @PostMapping("/admin/add/new/logo")
    public void addNewLogo(@RequestParam("product") final String product, @RequestParam("imagefile") final MultipartFile[] file) {
        final Logo p = mapLogoDto(product, null);
        final List<Image> fileSet = new ArrayList<Image>();
        for (final MultipartFile i : file) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.logoService.saveLogo(p);
    }

    @GetMapping("/admin/delete/planner/{id}")
    public void deletePlannerById(@PathVariable(name = "id") final String id) {
        this.PlannerService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/delete/logo/{id}")
    public void deleteLogoById(@PathVariable(name = "id") final String id) {
        this.logoService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/enable/planner/{id}")
    public ResponseEntity<?> setPlannerEnabled(@PathVariable(name = "id") final String id) {
        final Planner p = this.PlannerService.findById(new Long(id));
        final int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        this.PlannerService.savePlanner(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @GetMapping("/admin/enable/logo/{id}")
    public ResponseEntity<?> setLogoEnabled(@PathVariable(name = "id") final String id) {
        final Logo p = this.logoService.findById(new Long(id));
        final int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        this.logoService.saveLogo(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @PostMapping("/admin/update/planner/")
    public PlannerCommand updatePlanner(@RequestParam("product") final String product, 
        @RequestParam("imagefile") final MultipartFile[] files, 
        @RequestParam(value="removed", required = false) final List<String> removed) {
        final Planner p = this.mapPlannerDto(product, removed);

        for (final MultipartFile i : files) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        this.PlannerService.savePlanner(p);
        return this.PlannerToPlannerCommand.convert(p);
    }

    @PostMapping("/admin/update/logo/")
    public LogoCommand updateLogo(@RequestParam("product") final String product, 
        @RequestParam("imagefile") final MultipartFile[] files, 
        @RequestParam(value="removed", required = false) final List<String> removed) {
        final Logo p = this.mapLogoDto(product, removed);

        for (final MultipartFile i : files) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        this.logoService.saveLogo(p);
        return this.logoToLogoCommand.convert(p);
    }

    private Planner mapPlannerDto(final String product, final List<String> removed) {
        final ObjectMapper mapper = new ObjectMapper();
        PlannerDto p = null;
        try {

            p = mapper.readValue(product, PlannerDto.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final Planner pr = convertdtoToPlanner(p, removed);
        return this.PlannerService.savePlanner(pr);
    }

    private Logo mapLogoDto(final String product, final List<String> removed) {
        final ObjectMapper mapper = new ObjectMapper();
        LogoDto p = null;
        try {

            p = mapper.readValue(product, LogoDto.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final Logo pr = convertdtoToLogo(p, removed);
        return this.logoService.saveLogo(pr);
    }

    private Planner convertdtoToPlanner (final PlannerDto dto, final List<String> removed) {
        final Planner p = new Planner();
        if (dto.getId() != 0) {
            p.setId(dto.getId());
        }
        p.setName(dto.getName());
        p.setShortDescription(dto.getShortDescription());
        p.setLongDescription(dto.getLongDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(1);
        p.setDownloadLink(dto.getDownloadLink());
        p.setEnabled(dto.getEnabled());
        p.setCategory(getCategories(dto.getCategories()));
        final List<Image> i = plannerImagesLeft(dto.getId(), removed);
        p.setImages(i);
        p.setSku(dto.getSku());
        return p;
    }

    private Logo convertdtoToLogo(final LogoDto dto, final List<String> removed) {
        final Logo p = new Logo();
        if (dto.getId() != 0) {
            p.setId(dto.getId());
        }
        p.setName(dto.getName());
        p.setShortDescription(dto.getShortDescription());
        p.setLongDescription(dto.getLongDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(1);
        p.setLogoText(dto.getLogoText());
        p.setEnabled(dto.getEnabled());
        p.setCategory(getCategories(dto.getCategories()));
        final List<Image> i = logoImagesLeft(dto.getId(), removed);
        p.setImages(i);
        p.setSku(dto.getSku());
        return p;
    }

    private List<Image> plannerImagesLeft(final Long productId, final List<String> removed) {
        try {
            final Planner p = this.PlannerService.findById(productId);
            final List<Image> productImages = p.getImages();
            if (removed != null) {
                for (final String imageId : removed) {
                    productImages.removeIf(obj -> obj.getId().equals(new Long(imageId)));
                    final Image img = imageService.findById(new Long(imageId));
                    imageService.deleteImage(p, img);
                }
            }
            return productImages;
        } catch (final NotFoundException e) {
            return new ArrayList<Image>();
        } catch (final NullPointerException e) {
            return new ArrayList<Image>();
        }
    }

    private List<Image> logoImagesLeft(final Long productId, final List<String> removed) {
        try {
            final Logo p = this.logoService.findById(productId);
            final List<Image> productImages = p.getImages();
            if (removed != null) {
                for (final String imageId : removed) {
                    productImages.removeIf(obj -> obj.getId().equals(new Long(imageId)));
                    final Image img = imageService.findById(new Long(imageId));
                    imageService.deleteImage(p, img);
                }
            }
            return productImages;
        } catch (final NotFoundException e) {
            return new ArrayList<Image>();
        } catch (final NullPointerException e) {
            return new ArrayList<Image>();
        }
    }

    private Set<Category> getCategories (final List<String> categories) {
        final Set<Category> cat = new HashSet<Category>();
        for (final String c : categories) {
            final Category temp = this.categoryRepository.findByName(c);
            cat.add(temp);
        }
        return cat;
    }
    @PostMapping("/admin/add/new/image")
    public List<Image> addNewImage(@RequestParam final MultipartFile[] files) {
        for (final MultipartFile file : files) {
            imageService.saveOneImage(file);
        }
        return imageService.getAllImages()
                .stream()
                .sorted((image1, image2) -> image2.getId().compareTo(image1.getId()))
                .collect(Collectors.toList());
    }

    @PostMapping("/admin/add/new2")
    public void addNewProduct(@RequestParam final MultipartFile[] files) {
        for (final MultipartFile file : files) {
            imageService.saveImageFile(1L, file);
        }
    }

    @PostMapping("/admin/add/new/photo")
    public void addNewPhotosToProduct(@RequestParam("file-1") final MultipartFile[] files, @RequestParam(value = "uid") final String uid) {
        for (final MultipartFile file : files) {
            imageService.saveImageFile(Long.valueOf(uid), file);
        }
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
        this.discountService.enableDiscount(id, type);
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
        this.couponService.enableCoupon(id, type);
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
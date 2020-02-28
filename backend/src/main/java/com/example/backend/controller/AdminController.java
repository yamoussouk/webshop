package com.example.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import com.example.backend.dto.TaskDetailsDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Coupon;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.Planner;
import com.example.backend.model.SignUpEmail;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.CouponService;
import com.example.backend.service.CouponTaskExecutorService;
import com.example.backend.service.ImageService;
import com.example.backend.service.LogoService;
import com.example.backend.service.PlannerService;
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
    private final DiscountTaskExecutorService DiscountTaskExecutorService;
    private final CouponService couponService;
    private final CouponTaskExecutorService couponTaskExecutorService;

    public AdminController(final ImageService imageService, final PlannerService PlannerService, 
    final SignUpRepository signUpRepository, final CategoryRepository categoryRepository, 
    final LogoService logoService, final CouponService couponService, final CouponTaskExecutorService couponTaskExecutorService) {
        this.imageService = imageService;
        this.PlannerService = PlannerService;
        this.signUpRepository = signUpRepository;
        this.categoryRepository = categoryRepository;
        this.logoService = logoService;
        this.DiscountTaskExecutorService = new DiscountTaskExecutorService(this.logoService, this.PlannerService);
        this.couponService = couponService;
        this.couponTaskExecutorService = couponTaskExecutorService;
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
    public Map<String, Map<String, String>> setDiscount(@RequestParam("percent") final double percent,
     @RequestParam("from") final String from, @RequestParam("to") final String to,
      @RequestParam("products") final List<Integer> products) {
        // create a task which stops the discount
        // and somehow make it reachable for later use
        // think about how to cancel runnable waiting to run
        // think about how to cancel runnable already run (finish reset task, reset products discount to zero)
        final List<Planner> planners = new ArrayList<>();
        final List<Logo> logos = new ArrayList<>();
        for (final Integer product : products) {
            final Logo l = this.logoService.findById(new Long(product));
            if (l == null) {
                final Planner pl = this.PlannerService.findById(new Long(product));
                planners.add(pl);
            } else {
                logos.add(l);
            }
        }
        final TaskDetailsDto dto = new TaskDetailsDto();
        dto.setFrom(new Long(from));
        dto.setTo(new Long(to));
        dto.setPercent(percent);
        dto.setPlanners(planners);
        dto.setLogos(logos);
        return this.DiscountTaskExecutorService.createTask(dto);
    }

    @GetMapping("/admin/discount/all")
    public Map<String, Map<String, String>> getAllDiscount() {
        return this.DiscountTaskExecutorService.getTasks();
    }

    @GetMapping("/admin/discount/cancel/{id}")
    public Map<String, Map<String, String>> cancelDiscount(@PathVariable(name = "id") final String id) {
        return this.DiscountTaskExecutorService.cancelTask(id);
    }

    @GetMapping("/admin/coupon/all")
    public Map<String, Map<String, String>> getAllCoupons() {
        final List<Coupon> coupons = this.couponService.getAll();
        final Map<String, Map<String, String>> v = this.couponTaskExecutorService.getTasks();
        Map<String, Map<String, String>> t = mergeCoupons(coupons, v);
        return t;
    }

    @GetMapping("/admin/enable/coupon/{id}")
    public ResponseEntity<?> setCouponEnabled(@PathVariable(name = "id") final String id) {
        final Coupon c = this.couponService.getCouponById(new Long(id));
        final int en = c.getEnabled();
        if (en == 0) {
            c.setEnabled(1);
        } else {
            c.setEnabled(0);
        }
        this.couponService.saveCoupon(c);
        return ResponseEntity.ok("Coupon enable saved");
    }

    @GetMapping("/admin/delete/coupon/{id}")
    public void deleteCouponById(@PathVariable(name = "id") final String id) {
        this.couponService.deleteCouponById(Long.parseLong(id));
    }

    @PostMapping("/admin/coupon/save")
    public void saveCoupon(@RequestBody final Coupon coupon) {
        this.couponService.saveCoupon(coupon);
    }

    @PostMapping("/admin/coupon/set")
    public void setCoupon(
     @RequestParam("name") final String name,
     @RequestParam("percent") final int percent,
     @RequestParam(value = "from", required = false) final String from, 
     @RequestParam(value = "to", required = false) final String to) {
        if (from == null || to == null) {
            final Coupon coupon = new Coupon(name, percent, 1);
            this.couponService.saveCoupon(coupon);
        } else {
            final Map<String, Object> details = new HashMap<>();
            details.put("name", name);
            details.put("percent", percent);
            details.put("enabled", 0);
            details.put("from", from);
            details.put("to", to);
            this.couponTaskExecutorService.createTask(details);
        }
    }

    private Map<String, Map<String, String>> mergeCoupons(final List<Coupon> coupons, final Map<String, Map<String, String>> tasks) {
        Map<String, Map<String, String>> allTogether = new HashMap<>();
        int keyCounter = 0;
        try {
            boolean isIn = false;
            for (final Coupon coupon : coupons) {
                String couponId = String.valueOf(coupon.getId());
                Iterator it = tasks.entrySet().iterator();
                String foundKey = "";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    Object t = ((Map) pair.getValue()).get("id");
                    if (t.equals(couponId)) {
                        isIn = true;
                        foundKey = pair.getKey().toString();
                    }
                }
                if (!isIn) {
                    Map<String, String> temp = new HashMap<>();
                    temp.put("id", String.valueOf(coupon.getId()));
                    temp.put("name", coupon.getName());
                    temp.put("percent", String.valueOf(coupon.getPercent()));
                    temp.put("enabled", String.valueOf(coupon.getEnabled()));
                    temp.put("from", "");
                    temp.put("to", "");
                    allTogether.put(String.valueOf(keyCounter), temp);
                } else {
                    allTogether.put(String.valueOf(keyCounter), tasks.get(foundKey));
                }
                keyCounter++;
            }
            return allTogether;
        } catch (NoSuchElementException e) {
            Map<String, Map<String, String>> converted = new HashMap<>();
            int counter = 0;
            for (Coupon c : coupons) {
                Map<String, String> temp = new HashMap<>();
                temp.put("id", String.valueOf(c.getId()));
                temp.put("name", c.getName());
                temp.put("percent", String.valueOf(c.getPercent()));
                temp.put("enabled", String.valueOf(c.getEnabled()));
                temp.put("from", "");
                temp.put("to", "");
                converted.put(String.valueOf(counter), temp);
                counter++;
            }
            return converted;
        }
    }
}


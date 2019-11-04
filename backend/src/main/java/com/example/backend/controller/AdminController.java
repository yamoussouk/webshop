package com.example.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.command.LogoCommand;
import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.LogoToLogoCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.dto.LogoDto;
import com.example.backend.dto.PlannerDto;
import com.example.backend.dto.ProductDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.Planner;
import com.example.backend.model.Product;
import com.example.backend.model.SignUpEmail;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.ImageService;
import com.example.backend.service.LogoService;
import com.example.backend.service.PlannerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    public AdminController(ImageService imageService, PlannerService PlannerService, SignUpRepository signUpRepository, CategoryRepository categoryRepository, LogoService logoService) {
        this.imageService = imageService;
        this.PlannerService = PlannerService;
        this.signUpRepository = signUpRepository;
        this.categoryRepository = categoryRepository;
        this.logoService = logoService;
    }

    @GetMapping("admin/planners/all")
    public List<PlannerCommand> getAllPlanners() {
        List<Planner> products =  this.PlannerService.getPlanners(false);
        List<PlannerCommand> returnedValue = new ArrayList<PlannerCommand>();
        for (Planner p : products) {
            returnedValue.add(PlannerToPlannerCommand.convert(p));
        }
        return returnedValue;
    }

    @GetMapping("admin/logos/all")
    public List<LogoCommand> getAllLogos() {
        List<Logo> products =  this.logoService.getLogos(false);
        List<LogoCommand> returnedValue = new ArrayList<LogoCommand>();
        for (Logo p : products) {
            returnedValue.add(logoToLogoCommand.convert(p));
        }
        return returnedValue;
    }

    @PostMapping("/admin/add/new/planner")
    public void addNewPlanner(@RequestParam("product") String product, @RequestParam("imagefile") MultipartFile[] file) {
        Planner p = mapPlannerDto(product, null);
        Set<Image> fileSet = new HashSet<Image>();
        for (MultipartFile i : file) {
            Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.PlannerService.savePlanner(p);
    }

    @PostMapping("/admin/add/new/logo")
    public void addNewLogo(@RequestParam("product") String product, @RequestParam("imagefile") MultipartFile[] file) {
        Logo p = mapLogoDto(product, null);
        Set<Image> fileSet = new HashSet<Image>();
        for (MultipartFile i : file) {
            Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.logoService.saveLogo(p);
    }

    @GetMapping("/admin/delete/planner/{id}")
    public void deletePlannerById(@PathVariable(name = "id") String id) {
        this.PlannerService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/delete/logo/{id}")
    public void deleteLogoById(@PathVariable(name = "id") String id) {
        this.logoService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/enable/planner/{id}")
    public ResponseEntity<?> setPlannerEnabled(@PathVariable(name = "id") String id) {
        Planner p = this.PlannerService.findById(new Long(id));
        int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        this.PlannerService.savePlanner(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @GetMapping("/admin/enable/logo/{id}")
    public ResponseEntity<?> setLogoEnabled(@PathVariable(name = "id") String id) {
        Logo p = this.logoService.findById(new Long(id));
        int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        this.logoService.saveLogo(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @PostMapping("/admin/update/planner/")
    public PlannerCommand updatePlanner(@RequestParam("product") String product, 
        @RequestParam("imagefile") MultipartFile[] files, 
        @RequestParam(value="removed", required = false) List<String> removed) {
        Planner p = this.mapPlannerDto(product, removed);

        for (MultipartFile i : files) {
            Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        this.PlannerService.savePlanner(p);
        return this.PlannerToPlannerCommand.convert(p);
    }

    @PostMapping("/admin/update/logo/")
    public LogoCommand updateLogo(@RequestParam("product") String product, 
        @RequestParam("imagefile") MultipartFile[] files, 
        @RequestParam(value="removed", required = false) List<String> removed) {
        Logo p = this.mapLogoDto(product, removed);

        for (MultipartFile i : files) {
            Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        this.logoService.saveLogo(p);
        return this.logoToLogoCommand.convert(p);
    }

    private Planner mapPlannerDto(String product, List<String> removed) {
        ObjectMapper mapper = new ObjectMapper();
        PlannerDto p = null;
        try {

            p = mapper.readValue(product, PlannerDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Planner pr = convertdtoToPlanner(p, removed);
        return this.PlannerService.savePlanner(pr);
    }

    private Logo mapLogoDto(String product, List<String> removed) {
        ObjectMapper mapper = new ObjectMapper();
        LogoDto p = null;
        try {

            p = mapper.readValue(product, LogoDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logo pr = convertdtoToLogo(p, removed);
        return this.logoService.saveLogo(pr);
    }

    private Planner convertdtoToPlanner (PlannerDto dto, List<String> removed) {
        Planner p = new Planner();
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
        Set<Image> i = plannerImagesLeft(dto.getId(), removed);
        p.setImages(i);
        return p;
    }

    private Logo convertdtoToLogo(LogoDto dto, List<String> removed) {
        Logo p = new Logo();
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
        Set<Image> i = logoImagesLeft(dto.getId(), removed);
        p.setImages(i);
        return p;
    }

    private Set<Image> plannerImagesLeft(Long productId, List<String> removed) {
        try {
            Planner p = this.PlannerService.findById(productId);
            Set<Image> productImages = p.getImages();
            if (removed != null) {
                for (String imageId : removed) {
                    productImages.removeIf(obj -> obj.getId().equals(new Long(imageId)));
                    Image img = imageService.findById(new Long(imageId));
                    imageService.deleteImage(p, img);
                }
            }
            return productImages;
        } catch (NotFoundException e) {
            return new HashSet<Image>();
        }
    }

    private Set<Image> logoImagesLeft(Long productId, List<String> removed) {
        try {
            Logo p = this.logoService.findById(productId);
            Set<Image> productImages = p.getImages();
            if (removed != null) {
                for (String imageId : removed) {
                    productImages.removeIf(obj -> obj.getId().equals(new Long(imageId)));
                    Image img = imageService.findById(new Long(imageId));
                    imageService.deleteImage(p, img);
                }
            }
            return productImages;
        } catch (NotFoundException e) {
            return new HashSet<Image>();
        }
    }

    private Set<Category> getCategories (List<String> categories) {
        Set<Category> cat = new HashSet<Category>();
        for (String c : categories) {
            Category temp = this.categoryRepository.findByName(c);
            cat.add(temp);
        }
        return cat;
    }
    @PostMapping("/admin/add/new/image")
    public List<Image> addNewImage(@RequestParam MultipartFile[] files) {
        for (MultipartFile file : files) {
            imageService.saveOneImage(file);
        }
        return imageService.getAllImages()
                .stream()
                .sorted((image1, image2) -> image2.getId().compareTo(image1.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/admin/categories")
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        for (Categories c : Categories.values()) {
            categories.add(c.name());
        }
        return categories;
    }

    @PostMapping("/admin/add/new2")
    public void addNewProduct(@RequestParam MultipartFile[] files) {
        for (MultipartFile file : files) {
            imageService.saveImageFile(1L, file);
        }
    }

    @PostMapping("/admin/add/new/photo")
    public void addNewPhotosToProduct(@RequestParam("file-1") MultipartFile[] files, @RequestParam(value = "uid") String uid) {
        for (MultipartFile file : files) {
            imageService.saveImageFile(Long.valueOf(uid), file);
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> signUpEmail(@RequestParam("email") String email) {
		Optional<SignUpEmail> mail = this.signUpRepository.findByEmail(email);
		if (mail.isPresent()) {
			return new ResponseEntity<>("User is already signed up!", HttpStatus.OK);
        } else {
        	SignUpEmail emailToSave = new SignUpEmail(email);
        	this.signUpRepository.save(emailToSave);
        	return new ResponseEntity<>("User is signed up!", HttpStatus.OK);
        }
    }
    
    @GetMapping("/admin/subscribers/all")
    public List<SignUpEmail> getAllSubscribers() {
        List<SignUpEmail> subscribers = new ArrayList<>();
        this.signUpRepository.findAll().iterator().forEachRemaining(subscribers::add);
        return subscribers;
    }
}


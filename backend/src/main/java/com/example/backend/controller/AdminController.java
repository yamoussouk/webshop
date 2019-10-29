package com.example.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.dto.ProductDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.model.SignUpEmail;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.ImageService;
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
    private final SignUpRepository signUpRepository;
    private final CategoryRepository categoryRepository;
    private final PlannerToPlannerCommand PlannerToPlannerCommand = new PlannerToPlannerCommand();

    public AdminController(ImageService imageService, PlannerService PlannerService, SignUpRepository signUpRepository, CategoryRepository categoryRepository) {
        this.imageService = imageService;
        this.PlannerService = PlannerService;
        this.signUpRepository = signUpRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("admin/products/all")
    public List<PlannerCommand> getAllProducts() {
        List<Product> products =  this.PlannerService.getProducts(false);
        List<PlannerCommand> returnedValue = new ArrayList<PlannerCommand>();
        for (Product p : products) {
            returnedValue.add(PlannerToPlannerCommand.convert(p));
        }
        return returnedValue;
    }

    @PostMapping("/admin/add/new/product")
    public void addNewProduct(@RequestParam("product") String product, @RequestParam("imagefile") MultipartFile[] file) {
        // Product p = convertdtoToProduct(product);
        Product p = mapProductDto(product, null);
        Set<Image> fileSet = new HashSet<Image>();
        for (MultipartFile i : file) {
            Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.PlannerService.saveProduct(p);
    }

    @GetMapping("/admin/delete/product/{id}")
    public void deleteProductById(@PathVariable(name = "id") String id) {
        this.PlannerService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/enable/product/{id}")
    public ResponseEntity<?> setProductEnabled(@PathVariable(name = "id") String id) {
        Product p = this.PlannerService.findById(new Long(id));
        p.setEnabled(!p.getEnabled());
        this.PlannerService.saveProduct(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @PostMapping("/admin/update/product/")
    public PlannerCommand updateProduct(@RequestParam("product") String product, 
        @RequestParam("imagefile") MultipartFile[] files, 
        @RequestParam(value="removed", required = false) List<String> removed) {
        Product p = this.mapProductDto(product, removed);

        for (MultipartFile i : files) {
            Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        this.PlannerService.saveProduct(p);
        return this.PlannerToPlannerCommand.convert(p);
    }

    private Product mapProductDto(String product, List<String> removed) {
        ObjectMapper mapper = new ObjectMapper();
        ProductDto p = null;
        try {

            p = mapper.readValue(product, ProductDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product pr = convertdtoToProduct(p, removed);
        return this.PlannerService.saveProduct(pr);
    }

    private Product convertdtoToProduct (ProductDto dto, List<String> removed) {
        Product p = new Product();
        // TODO: remove it when reset autogeneration
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setShortDescription(dto.getShortDescription());
        p.setLongDescription(dto.getLongDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(1);
        p.setDownloadLink(dto.getDownloadLink());
        p.setEnabled(dto.getEnabled());
        p.setCategory(getCategories(dto.getCategories()));
        Set<Image> i = imagesLeft(dto.getId(), removed);
        p.setImages(i);
        // continue with categories!!!!
        return p;
    }

    private Set<Image> imagesLeft(Long productId, List<String> removed) {
        try {
            Product p = this.PlannerService.findById(productId);
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


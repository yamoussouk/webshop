package com.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Product;
import com.example.backend.model.SignUpEmail;
import com.example.backend.command.ProductCommand;
import com.example.backend.dto.ProductDto;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.SignUpRepository;
import com.example.backend.service.ImageService;
import com.example.backend.service.ProductService;
import com.example.backend.converter.ProductToProductCommand;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final ImageService imageService;
    private final ProductService productService;
    private final SignUpRepository signUpRepository;
    private final CategoryRepository categoryRepository;
    private final ProductToProductCommand productToProductCommand = new ProductToProductCommand();

    public AdminController(ImageService imageService, ProductService productService, SignUpRepository signUpRepository, CategoryRepository categoryRepository) {
        this.imageService = imageService;
        this.productService = productService;
        this.signUpRepository = signUpRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("admin/products/all")
    public List<ProductCommand> getAllProducts() {
        List<Product> products =  this.productService.getProducts();
        List<ProductCommand> returnedValue = new ArrayList<ProductCommand>();
        for (Product p : products) {
            returnedValue.add(productToProductCommand.convert(p));
        }
        return returnedValue;
    }

    @PostMapping("/admin/add/new/product")
    public void addNewProduct(@RequestParam("product") String product, @RequestParam("imagefile") MultipartFile[] file) {
        // Product p = convertdtoToProduct(product);
        Product p = mapProduct(product);
        Set<Image> fileSet = new HashSet<Image>();
        for (MultipartFile i : file) {
            Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        this.productService.saveProduct(p);
    }

    @GetMapping("/admin/delete/product/{id}")
    public void deleteProductById(@PathVariable(name = "id") String id) {
        this.productService.deleteById(Long.parseLong(id));
    }

    @GetMapping("/admin/enable/product/{id}")
    public ResponseEntity<?> setProductEnabled(@PathVariable(name = "id") String id) {
        Product p = this.productService.findById(new Long(id));
        p.setEnabled(!p.getEnabled());
        this.productService.saveProduct(p);
        return ResponseEntity.ok("Enabled changed");
    }

    @PostMapping("/admin/update/product")
    public void updateProduct(@RequestParam("model") String product, @RequestParam("files") String[] files) {
        Product updateProduct = mapProduct(product);
        Product original = productService.findById(updateProduct.getId());
        original.setName(updateProduct.getName());
        original.setShortDescription(updateProduct.getShortDescription());
        original.setLongDescription(updateProduct.getLongDescription());
        original.setCategory(updateProduct.getCategory());
        original.setQuantity(updateProduct.getQuantity());
        original.setPrice(updateProduct.getPrice());
        //imageService.deleteAllByProductId(original.getId());
        //TODO: megnézni, hogy az originalból lekérhető-e egyáltalán a hozzátartozó kép és annak az id-je alapján törölni és cserélni!!
        Set<Image> sentImages = new HashSet<>();
        for (String id : files) {
            Image i = imageService.findById(Long.valueOf(id));
            sentImages.add(i);
        }
        Objects.requireNonNull(original).setImages(sentImages);
        productService.saveProduct(original);
    }

    private Product mapProduct(String product) {
        ObjectMapper mapper = new ObjectMapper();
        ProductDto p = null;
        try {

            p = mapper.readValue(product, ProductDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product pr = convertdtoToProduct(p);
        return this.productService.saveProduct(pr);
    }

    private Product convertdtoToProduct (ProductDto dto) {
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
        // continue with categories!!!!
        return p;
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

    /*@GetMapping("/admin/fragment/{fragment}")
    public String insertFragment(@PathVariable(name = "fragment") String fragment, Model model) {
        switch (fragment) {
            case "new":
                model.addAttribute("product", new Product());
                break;
            case "all":
                model.addAttribute("products", productService.getProducts());
                break;
            case "product":

        }
        model.addAttribute("fragment", fragment);
        return "admin/start";
    }*/
    
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
}


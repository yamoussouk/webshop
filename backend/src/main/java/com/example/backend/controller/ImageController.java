package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.service.ImageService;
import com.example.backend.service.PlannerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService, PlannerService PlannerService) {
        this.imageService = imageService;
    }

    /*@GetMapping("product/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("product", PlannerService.findCommandById(Long.valueOf(id)));
        return "product/imageuploadform";
    }*/

    @PostMapping("product/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/product/show/" + id;
    }

    @GetMapping("product/{id}/productimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        imageService.renderImage(Long.valueOf(id), null, response);
    }

    @GetMapping("product/{id}/{imageUrl}")
    public void renderImageFromDB2(@PathVariable String id, @PathVariable String imageUrl, HttpServletResponse response) throws IOException {
        imageService.renderImage(Long.valueOf(id), imageUrl, response);
    }

    @GetMapping("/admin/allimages")
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }
}


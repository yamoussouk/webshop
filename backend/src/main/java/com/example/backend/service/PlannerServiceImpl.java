package com.example.backend.service;

import com.example.backend.command.PlannerCommand;
import com.example.backend.converter.PlannerToPlannerCommand;
import com.example.backend.dto.PlannerDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Meta;
import com.example.backend.model.Planner;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.MetaRepository;
import com.example.backend.repository.PlannerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlannerServiceImpl implements PlannerService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MetaRepository metaRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PlannerRepository plannerRepository;
    @Autowired
    private PlannerToPlannerCommand converter;

    public PlannerServiceImpl() {}

    @Override
    public List<Planner> getPlanners(boolean enabled) {
        List<Planner> allPlanners = new ArrayList<>();
        this.plannerRepository.findAll().iterator().forEachRemaining(allPlanners::add);
        if(enabled) {
            return allPlanners.stream().filter(product -> product.getEnabled() == 1).collect(Collectors.toList());
        } else {
            return allPlanners;
        }
    }

    @Override
    public Planner findById(Long id) {
        Optional<Planner> planner = this.plannerRepository.findById(id);
        if (planner.isPresent()) {
            return planner.get();
        } else {
            throw new NotFoundException("Product with given id has not found!");
        }
    }

    @Override
    @Transactional
    public PlannerCommand findCommandById(Long id) {
        return converter.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        plannerRepository.deleteById(idToDelete);
    }

    @Override
    public Planner savePlanner(Planner product) {
        return plannerRepository.save(product);
    }

    @Override
    public Planner findByName(String name) {
        return plannerRepository.findByName(name);
    }

    @Override
    public void savePlannerPreparator(final String product, final MultipartFile[] file) {
        final Planner p = mapPlannerDto(product, null);
        final List<Image> fileSet = new ArrayList<Image>();
        for (final MultipartFile i : file) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        savePlanner(p);
    }

    @Override
    public void enablePlanner(final String id) {
        final Planner p = findById(new Long(id));
        final int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        savePlanner(p);
    }

    @Override
    public PlannerCommand updatePlannerPreparator(
        final String product, 
        final List<String> removed, 
        final MultipartFile[] files) {
        final Planner p = this.mapPlannerDto(product, removed);

        for (final MultipartFile i : files) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            p.setOneImage(img);
        }
        savePlanner(p);
        return this.converter.convert(p);
    }

    private Set<Category> getCategories (final List<String> categories) {
        final Set<Category> cat = new HashSet<Category>();
        for (final String c : categories) {
            final Category temp = this.categoryRepository.findByName(c);
            cat.add(temp);
        }
        return cat;
    }

    private List<Image> plannerImagesLeft(final Long productId, final List<String> removed) {
        try {
            final Planner p = findById(productId);
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
        p.setLastUpdated(String.valueOf(new Date().getTime()));
        Set<Meta> metas = new HashSet<>();
        for (Meta m : dto.getMetaTags()) {
            metas.add(this.metaRepository.save(m));
        }
        p.setMeta(metas);
        return p;
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
        return savePlanner(pr);
    }
}


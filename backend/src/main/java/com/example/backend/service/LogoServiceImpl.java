package com.example.backend.service;

import com.example.backend.command.LogoCommand;
import com.example.backend.converter.LogoToLogoCommand;
import com.example.backend.dto.LogoDto;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Category;
import com.example.backend.model.Image;
import com.example.backend.model.Logo;
import com.example.backend.model.Meta;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.LogoRepository;
import com.example.backend.repository.MetaRepository;
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
public class LogoServiceImpl implements LogoService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MetaRepository metaRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private LogoRepository LogoRepository;
    @Autowired
    private LogoToLogoCommand converter;

    public LogoServiceImpl() {}

    @Override
    public List<Logo> getLogos(boolean enabled) {
        List<Logo> allLogos = new ArrayList<>();
        this.LogoRepository.findAll().iterator().forEachRemaining(allLogos::add);
        if(enabled) {
            return allLogos.stream().filter(product -> product.getEnabled() == 1).collect(Collectors.toList());
        } else {
            return allLogos;
        }
    }

    @Override
    public Logo findById(Long id) {
        Optional<Logo> Logo = this.LogoRepository.findById(id);
        if (Logo.isPresent()) {
            return Logo.get();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public LogoCommand findCommandById(Long id) {
        return converter.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        LogoRepository.deleteById(idToDelete);
    }

    @Override
    public Logo saveLogo(Logo product) {
        return LogoRepository.save(product);
    }

    @Override
    public void saveLogoPreparator(final String product, final MultipartFile[] file) {
        final Logo p = mapLogoDto(product, null);
        final List<Image> fileSet = new ArrayList<Image>();
        for (final MultipartFile i : file) {
            final Image img = imageService.saveImageFile(p.getId(), i);
            fileSet.add(img);
        }
        p.setImages(fileSet);
        saveLogo(p);
    }

    @Override
    public void enableLogo(final String id) {
        final Logo p = findById(new Long(id));
        final int en = p.getEnabled();
        if (en == 0) {
            p.setEnabled(1);
        } else {
            p.setEnabled(0);
        }
        saveLogo(p);
    }

    @Override
    public LogoCommand updateLogo(
        final String product, 
        final MultipartFile[] files, 
        final List<String> removed) {
            final Logo p = this.mapLogoDto(product, removed);

            for (final MultipartFile i : files) {
                final Image img = imageService.saveImageFile(p.getId(), i);
                p.setOneImage(img);
            }
            saveLogo(p);
            return this.converter.convert(p);
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
        return saveLogo(pr);
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
        p.setLastUpdated(String.valueOf(new Date().getTime()));
        Set<Meta> metas = new HashSet<>();
        for (Meta m : dto.getMetaTags()) {
            metas.add(this.metaRepository.save(m));
        }
        p.setMeta(metas);
        return p;
    }

    private List<Image> logoImagesLeft(final Long productId, final List<String> removed) {
        try {
            final Logo p = findById(productId);
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
}


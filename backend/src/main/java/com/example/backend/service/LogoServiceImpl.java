package com.example.backend.service;

import com.example.backend.command.LogoCommand;
import com.example.backend.converter.LogoToLogoCommand;
import com.example.backend.model.Logo;
import com.example.backend.repository.LogoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LogoServiceImpl implements LogoService {

    private final LogoRepository LogoRepository;
    private final LogoToLogoCommand converter;

    public LogoServiceImpl(LogoRepository LogoRepository, LogoToLogoCommand converter) {
        this.LogoRepository = LogoRepository;
        this.converter = converter;
    }

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
}


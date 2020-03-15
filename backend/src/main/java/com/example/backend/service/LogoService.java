package com.example.backend.service;

import com.example.backend.command.LogoCommand;
import com.example.backend.model.Logo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LogoService {

    List<Logo> getLogos(boolean enabled);
    Logo findById(Long id);
    LogoCommand findCommandById(Long id);
    void deleteById(Long idToDelete);
    Logo saveLogo(Logo product);
    void saveLogoPreparator(final String product, final MultipartFile[] file);
    void enableLogo(final String id);
    public LogoCommand updateLogo(final String product, final MultipartFile[] files, final List<String> removed);
}

package com.example.backend.service;

import com.example.backend.command.LogoCommand;
import com.example.backend.model.Logo;

import java.util.List;
import java.util.Set;

public interface LogoService {

    List<Logo> getLogos(boolean enabled);
    Logo findById(Long id);
    LogoCommand findCommandById(Long id);
    void deleteById(Long idToDelete);
    Logo saveLogo(Logo product);
}

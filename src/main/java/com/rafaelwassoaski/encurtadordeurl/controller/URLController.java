package com.rafaelwassoaski.encurtadordeurl.controller;

import com.rafaelwassoaski.encurtadordeurl.DTO.CustomURLDTO;
import com.rafaelwassoaski.encurtadordeurl.domain.entity.CustomURL;
import com.rafaelwassoaski.encurtadordeurl.service.implementation.CustomURLServiceImplementation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/url")
public class URLController {
    
    private CustomURLServiceImplementation customURLService;

    public URLController(CustomURLServiceImplementation customURLService){
        this.customURLService = customURLService;
    }

    @PostMapping("/encurtar")
    public CustomURLDTO encurtarUrl(@RequestBody CustomURLDTO urlOriginal){
        CustomURL customURL = this.customURLService.salvarUrlEncurtada(urlOriginal.getUrlOriginal());

        CustomURLDTO customURLDTO = this.convertCustomURLToDTO(customURL);
        return customURLDTO;
    }

    @GetMapping("/{urlEncurtada}")
    public CustomURLDTO getUrlOriginal(@PathVariable String urlEncurtada){
        return customURLService.getUrlOriginal(urlEncurtada);
    }

    private CustomURLDTO convertCustomURLToDTO(CustomURL customURL){
        return new CustomURLDTO(customURL.getUrlOriginal(), customURL.getUrlEncurtada());
    }
}

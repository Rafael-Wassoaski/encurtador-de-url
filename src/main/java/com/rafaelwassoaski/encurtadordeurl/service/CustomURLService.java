package com.rafaelwassoaski.encurtadordeurl.service;

import com.rafaelwassoaski.encurtadordeurl.DTO.CustomURLDTO;
import com.rafaelwassoaski.encurtadordeurl.domain.entity.CustomURL;
import org.springframework.stereotype.Service;

public interface CustomURLService {

    public String encurtarUrl(String urlOriginal);

    public CustomURLDTO getUrlOriginal(String urlEncurtada);

    public CustomURL salvarUrlEncurtada(String urlOriginal);
}

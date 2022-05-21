package com.rafaelwassoaski.encurtadordeurl.domain.repository;

import com.rafaelwassoaski.encurtadordeurl.domain.entity.CustomURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomURLRepository extends JpaRepository<CustomURL, Integer>{
    public CustomURL findByUrlEncurtada(String urlEncurtada);

    public CustomURL findByUrlOriginal(String urlOriginal);
}

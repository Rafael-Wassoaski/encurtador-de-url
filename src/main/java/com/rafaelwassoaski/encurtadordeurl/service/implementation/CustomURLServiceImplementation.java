package com.rafaelwassoaski.encurtadordeurl.service.implementation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import com.mysql.cj.log.Log;
import com.rafaelwassoaski.encurtadordeurl.DTO.CustomURLDTO;
import com.rafaelwassoaski.encurtadordeurl.domain.entity.CustomURL;
import com.rafaelwassoaski.encurtadordeurl.domain.repository.CustomURLRepository;
import com.rafaelwassoaski.encurtadordeurl.service.CustomURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomURLServiceImplementation implements CustomURLService {

    @Autowired
    private CustomURLRepository customURLRepository;

    @Value("${customURL.expiration.time}")
    private String expiration;

    @Override
    public String encurtarUrl(String urlOriginal) {
        Integer numberOfCharacters = this.getRandomValueFromRange(5, 5);
        String urlEncuratada = "";

        for (int index = 0; index < numberOfCharacters; index++) {
            int randomValue = this.getRandomValueFromRange(65, 57);
            urlEncuratada += (char) randomValue;
        }

        return urlEncuratada;
    }

    @Override
    public CustomURLDTO getUrlOriginal(String urlEncurtada) throws ResponseStatusException {
        // TODO Auto-generated method stub
        CustomURL url = customURLRepository.findByUrlEncurtada(urlEncurtada);

        if (url == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não localizada");
        }

        LocalDateTime dateHour = LocalDateTime.now();
        LocalDate curentDate = dateHour.atZone(ZoneId.systemDefault()).toLocalDate();

        if (curentDate.isAfter(url.getDataDeExpiracao())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Esta URL encurtada já expirou");
        }

        return CustomURLDTO.builder()
                .urlEncurtada(url.getUrlEncurtada())
                .urlOriginal(url.getUrlOriginal())
                .build();
    }

    @Override
    public CustomURL salvarUrlEncurtada(String urlOriginal) throws ResponseStatusException {
        String urlEncurtada = this.encurtarUrl(urlOriginal);
        LocalDate expirationDate = this.calculateExpirationDate();
        CustomURL customURL = CustomURL.builder().dataDeExpiracao(expirationDate)
                .urlEncurtada(urlEncurtada).urlOriginal(urlOriginal).build();

        LocalDate extDate = customURL.getDataDeExpiracao();
        Boolean isAfter = LocalDate.now().isAfter(extDate);

        if (isAfter) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Você está tetando acessar uma url já expirada");
        }

        return customURLRepository.save(customURL);
    }

    private int getRandomValueFromRange(Integer min, Integer max) {
        Random rand = new Random();

        return rand.nextInt(min) + max;
    }

    private LocalDate calculateExpirationDate() {
        Long expStrinLong = Long.valueOf(this.expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusDays(expStrinLong);
        LocalDate expirationDate = dateHourExpiration.atZone(ZoneId.systemDefault()).toLocalDate();

        return expirationDate;
    }
}

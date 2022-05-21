package com.rafaelwassoaski.encurtadordeurl.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomURLDTO {
    private String urlOriginal;
    private String urlEncurtada;
}

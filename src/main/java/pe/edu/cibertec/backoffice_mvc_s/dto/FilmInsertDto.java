package pe.edu.cibertec.backoffice_mvc_s.dto;

import java.util.Date;
import java.util.Set;

public record FilmInsertDto(
        String title,
        String description,
        Integer releaseYear,
        Integer languageId,
        String languageName,
        Integer originalLanguageId,
        String originalLanguageName,
        Integer rentalDuration,
        Double rentalRate,
        Integer length,
        Double replacementCost,
        String rating,
        Set<String> specialFeatures,
        Date lastUpdate) {
}


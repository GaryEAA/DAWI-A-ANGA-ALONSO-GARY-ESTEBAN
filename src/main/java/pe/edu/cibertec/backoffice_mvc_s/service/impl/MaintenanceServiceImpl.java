package pe.edu.cibertec.backoffice_mvc_s.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmEditDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Film;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.repository.FilmRepository;
import pe.edu.cibertec.backoffice_mvc_s.repository.LanguageRepository;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Override
    public List<FilmDto> getAllFilms() {
        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            String originalLanguageName = (film.getOriginalLanguage() != null) ? film.getOriginalLanguage().getName() : "Not Assigned";
            FilmDto filmDto = new FilmDto(
                    film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    originalLanguageName,
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;
    }
    @Override
    public FilmDetailDto getFilmById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> {
            String originalLanguageName = (film.getOriginalLanguage() != null) ?
                    film.getOriginalLanguage().getName() : "Not Assigned";
            Integer originalLanguageId = (film.getOriginalLanguage() != null) ?
                    film.getOriginalLanguage().getLanguageId() : null;
            return new FilmDetailDto(
                    film.getFilmId(),
                    film.getTitle(),
                    film.getDescription(),
                    film.getReleaseYear(),
                    film.getLanguage().getLanguageId(),
                    film.getLanguage().getName(),
                    originalLanguageId,
                    originalLanguageName,
                    film.getRentalDuration(),
                    film.getRentalRate(),
                    film.getLength(),
                    film.getReplacementCost(),
                    film.getRating(),
                    film.getSpecialFeatures(),
                    film.getLastUpdate()
            );
        }).orElse(null);
    }
    @Override
    public FilmEditDto getFilmForEditById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> {
            String originalLanguageName = (film.getOriginalLanguage() != null) ?
                    film.getOriginalLanguage().getName() : "Not Assigned";
            Integer originalLanguageId = (film.getOriginalLanguage() != null) ?
                    film.getOriginalLanguage().getLanguageId() : null;
            Set<String> specialFeatures = new HashSet<>();
            if (film.getSpecialFeatures() != null && !film.getSpecialFeatures().isEmpty()) {
                specialFeatures = Arrays.stream(film.getSpecialFeatures().split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());
            }
            return new FilmEditDto(
                    film.getFilmId(),
                    film.getTitle(),
                    film.getDescription(),
                    film.getReleaseYear(),
                    film.getLanguage().getLanguageId(),
                    film.getLanguage().getName(),
                    originalLanguageId,
                    originalLanguageName,
                    film.getRentalDuration(),
                    film.getRentalRate(),
                    film.getLength(),
                    film.getReplacementCost(),
                    film.getRating(),
                    specialFeatures,
                    film.getLastUpdate()
            );
        }).orElse(null);
    }
    @CacheEvict(value = "films", allEntries = true)
    @Override
    public void editFilm(FilmEditDto filmEditDto) {
        Film film = filmRepository.findById(filmEditDto.filmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));
        Language language = languageRepository.findById(filmEditDto.languageId())
                .orElseThrow(() -> new RuntimeException("Language not found"));
        Language originalLanguage = null;
        if (filmEditDto.originalLanguageId() != null) {
            originalLanguage = languageRepository.findById(filmEditDto.originalLanguageId())
                    .orElseThrow(() -> new RuntimeException("Original Language not found"));
        }
        String specialFeatures = String.join(",", filmEditDto.specialFeatures());
        film.setTitle(filmEditDto.title());
        film.setDescription(filmEditDto.description());
        film.setReleaseYear(filmEditDto.releaseYear());
        film.setLanguage(language);
        film.setOriginalLanguage(originalLanguage);
        film.setRentalDuration(filmEditDto.rentalDuration());
        film.setRentalRate(filmEditDto.rentalRate());
        film.setLength(filmEditDto.length());
        film.setReplacementCost(filmEditDto.replacementCost());
        film.setRating(filmEditDto.rating());
        film.setSpecialFeatures(specialFeatures);
        film.setLastUpdate(new Date());
        filmRepository.save(film);
    }
    @Override
    public List<Language> getAllLanguages() {
        return (List<Language>) languageRepository.findAll();
    }
    @CacheEvict(value = "films", allEntries = true)
    @Override
    public void insertFilm(FilmInsertDto filmInsertDto) {
        Language language = languageRepository.findById(filmInsertDto.languageId())
                .orElseThrow(() -> new IllegalArgumentException("Idioma no válido"));
        Language originalLanguage = null;
        if (filmInsertDto.originalLanguageId() != null) {
            originalLanguage = languageRepository.findById(filmInsertDto.originalLanguageId())
                    .orElseThrow(() -> new IllegalArgumentException("Idioma original no válido"));
        }
        String specialFeatures = filmInsertDto.specialFeatures() != null && !filmInsertDto.specialFeatures().isEmpty()
                ? String.join(",", filmInsertDto.specialFeatures())
                : "";
        Film film = new Film();
        film.setTitle(filmInsertDto.title());
        film.setDescription(filmInsertDto.description());
        film.setReleaseYear(filmInsertDto.releaseYear());
        film.setLanguage(language);
        film.setOriginalLanguage(originalLanguage);
        film.setRentalDuration(filmInsertDto.rentalDuration());
        film.setRentalRate(filmInsertDto.rentalRate());
        film.setLength(filmInsertDto.length());
        film.setReplacementCost(filmInsertDto.replacementCost());
        film.setRating(filmInsertDto.rating());
        film.setSpecialFeatures(specialFeatures);
        film.setLastUpdate(new Date());
        filmRepository.save(film);
    }
    @CacheEvict(value = "films", allEntries = true)
    @Override
    public void deleteFilmById(int id) {
        filmRepository.deleteById(id);
    }
}

package pe.edu.cibertec.backoffice_mvc_s.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDetailDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmEditDto;
import pe.edu.cibertec.backoffice_mvc_s.dto.FilmInsertDto;
import pe.edu.cibertec.backoffice_mvc_s.entity.Language;
import pe.edu.cibertec.backoffice_mvc_s.service.MaintenanceService;

import java.util.*;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Autowired
    MaintenanceService maintenanceService;
    @GetMapping("/start")
    public String start(Model model) {
        List<FilmDto> films = maintenanceService.getAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.getFilmById(id);
        model.addAttribute("filmDetailDto", filmDetailDto);
        return "maintenance-detail";
    }
    @GetMapping("/edit/{id}")
    public String editFilmForm(@PathVariable Integer id, Model model) {
        FilmEditDto filmEditDto = maintenanceService.getFilmForEditById(id);
        List<Language> languages = maintenanceService.getAllLanguages();
        model.addAttribute("filmEditDto", filmEditDto);
        model.addAttribute("languages", languages);
        return "maintenance-edit";
    }
    @PostMapping("/edit")
    public String editFilm(FilmEditDto filmEditDto, RedirectAttributes redirectAttributes) {
        try {
            Integer id = filmEditDto.filmId();
            maintenanceService.editFilm(filmEditDto);
            redirectAttributes.addFlashAttribute("message", "La película " + id + " se ha actualizado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al editar la película.");
        }
        return "redirect:/maintenance/start";
    }
    @GetMapping("/insert")
    public String insertFilmForm(Model model) {
        String defaultTitle = "Título por defecto";
        String defaultDescription = "Descripción por defecto";
        Integer defaultLanguageId = 1;
        Integer defaultRentalDuration = 3;
        Double defaultRentalRate = 4.99;
        Double defaultReplacementCost = 19.99;
        String defaultRating = "G";
        Set<String> defaultSpecialFeatures = new HashSet<>();
        Date defaultLastUpdate = new Date();
        FilmInsertDto filmInsertDto = new FilmInsertDto(
                defaultTitle,           // title
                defaultDescription,     // description
                null,                   // releaseyear
                defaultLanguageId,      // languageid
                "",                     // languagename
                null,                   // originallanguageid
                "",                     // originallanguagename
                defaultRentalDuration,  // rentalduration
                defaultRentalRate,      // rentalrate
                null,                   // length
                defaultReplacementCost, // replacementcost
                defaultRating,          // rating
                defaultSpecialFeatures, // specialfeatures
                defaultLastUpdate       // lastupdate
        );
        List<Language> languages = maintenanceService.getAllLanguages();
        model.addAttribute("filmInsertDto", filmInsertDto);
        model.addAttribute("languages", languages);
        return "maintenance-insert";
    }
    @PostMapping("/insert")
    public String insertFilm(FilmInsertDto filmInsertDto, RedirectAttributes redirectAttributes) {
        try {
            String title = filmInsertDto.title();
            maintenanceService.insertFilm(filmInsertDto);
            redirectAttributes.addFlashAttribute("message", "La película " + title + " se ha añadido con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al añadir la película.");
        }
        return "redirect:/maintenance/start";
    }
    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            maintenanceService.deleteFilmById(id);
            redirectAttributes.addFlashAttribute("message", "La película " + id + " se ha eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar la película.");
        }
        return "redirect:/maintenance/start";
    }
}
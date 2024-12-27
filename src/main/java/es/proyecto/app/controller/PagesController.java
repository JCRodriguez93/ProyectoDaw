package es.proyecto.app.controller;

import es.proyecto.app.service.CategoriesService;
import es.swagger.codegen.models.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class PagesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        // Recuperar categorías y subcategorías desde el servicio
        List<Category> categories = categoriesService.getAllCategories();

        // Añadir categorías al modelo para Thymeleaf
        model.addAttribute("categories", categories);

        // Retornar el nombre de la plantilla Thymeleaf
        return "index"; // Thymeleaf buscará src/main/resources/templates/index.html
    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        // Retorna la página de login
//        return "login"; // Thymeleaf buscará src/main/resources/templates/login.html
//    }


}

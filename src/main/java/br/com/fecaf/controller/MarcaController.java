package br.com.fecaf.controller;

import br.com.fecaf.model.Marca;
import br.com.fecaf.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("marcas", marcaService.listarTodas());
        return "marca/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("marca", new Marca());
        return "marca/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Marca marca) {
        marcaService.salvar(marca);
        return "redirect:/marcas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("marca", marcaService.buscarPorId(id));
        return "marca/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        try {
            marcaService.deletar(id);
        } catch (RuntimeException e) {
            // Aqui você pode adicionar uma mensagem de erro na sessão
        }
        return "redirect:/marcas";
    }
}
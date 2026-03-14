package br.com.fecaf.controller;

import br.com.fecaf.model.Modelo;
import br.com.fecaf.service.MarcaService;
import br.com.fecaf.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("modelos", modeloService.listarTodos());
        return "modelo/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("modelo", new Modelo());
        model.addAttribute("marcas", marcaService.listarTodas());
        return "modelo/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Modelo modelo) {
        modeloService.salvar(modelo);
        return "redirect:/modelos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("modelo", modeloService.buscarPorId(id));
        model.addAttribute("marcas", marcaService.listarTodas());
        return "modelo/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        try {
            modeloService.deletar(id);
        } catch (RuntimeException e) {
            // tratar erro
        }
        return "redirect:/modelos";
    }
}
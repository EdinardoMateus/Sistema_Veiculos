package br.com.fecaf.controller;

import br.com.fecaf.model.StatusVeiculo;
import br.com.fecaf.model.Veiculo;
import br.com.fecaf.service.MarcaService;
import br.com.fecaf.service.ModeloService;
import br.com.fecaf.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("veiculos", veiculoService.listarTodos());
        return "veiculo/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        model.addAttribute("modelos", modeloService.listarTodos());
        model.addAttribute("statusList", StatusVeiculo.values());
        return "veiculo/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Veiculo veiculo) {
        veiculoService.salvar(veiculo);
        return "redirect:/veiculos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("veiculo", veiculoService.buscarPorId(id));
        model.addAttribute("modelos", modeloService.listarTodos());
        model.addAttribute("statusList", StatusVeiculo.values());
        return "veiculo/form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return "redirect:/veiculos";
    }

    @GetMapping("/consulta")
    public String consulta(Model model) {
        model.addAttribute("marcas", marcaService.listarTodas());
        model.addAttribute("statusList", StatusVeiculo.values());
        return "veiculo/consulta";
    }

    @GetMapping("/filtrar")
    public String filtrar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer anoMin,
            @RequestParam(required = false) Integer anoMax,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) StatusVeiculo status,
            Model model) {

        model.addAttribute("veiculos", veiculoService.filtrar(marca, modelo, anoMin, anoMax, precoMin, precoMax, status));
        model.addAttribute("marcas", marcaService.listarTodas());
        model.addAttribute("statusList", StatusVeiculo.values());

        // Repassar parâmetros para o formulário
        model.addAttribute("filtroMarca", marca);
        model.addAttribute("filtroModelo", modelo);
        model.addAttribute("filtroAnoMin", anoMin);
        model.addAttribute("filtroAnoMax", anoMax);
        model.addAttribute("filtroPrecoMin", precoMin);
        model.addAttribute("filtroPrecoMax", precoMax);
        model.addAttribute("filtroStatus", status);

        return "veiculo/consulta";
    }
}
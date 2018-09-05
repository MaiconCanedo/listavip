package br.com.alura.listavip;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.repository.ConvidadoRepository;
import br.com.alura.listavip.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConvidadoController {

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("listaconvidados")
    public String listaConvidados(Model model) {
        Iterable<Convidado> convidados = convidadoRepository.findAll();
        model.addAttribute("convidados", convidados);

        return "listaconvidados";
    }

    @RequestMapping(value = "salvar", method = RequestMethod.POST)
    public String salvar(@RequestParam("nome") String nome, @RequestParam("email") String email, @RequestParam("telefone") String telefone, Model model){
        Convidado convidado = new Convidado(nome, email, telefone);
        convidadoRepository.save(convidado);
        new EmailService().enviar(nome, email);
        return listaConvidados(model);
    }

    @RequestMapping(value = "pesquisa")
    public String pesquisa() {
        return "pesquisa";
    }

    @RequestMapping(value = "buscar", method = RequestMethod.POST)
    public String buscarConvidados(@RequestParam("pesquisa") String pesquisa, Model model){
        Iterable<Convidado> convidados = convidadoRepository.findCustomByNome(pesquisa);
        model.addAttribute("convidados2", convidados);
        return "pesquisa";
    }
}
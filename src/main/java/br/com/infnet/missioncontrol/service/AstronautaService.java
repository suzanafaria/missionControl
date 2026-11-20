package br.com.infnet.missioncontrol.service;

import br.com.infnet.missioncontrol.model.Astronauta;
import br.com.infnet.missioncontrol.model.Missao;
import br.com.infnet.missioncontrol.model.StatusAstronauta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AstronautaService {
    private final ArrayList<Astronauta> astronautas = new ArrayList<>();
    private Long proximoId = 4L;

    public AstronautaService() {
        astronautas.add(new Astronauta(
                1L, "Ana Silva", "Brasil", 34, 8,
                StatusAstronauta.DISPONIVEL));

        astronautas.add(new Astronauta(
                2L, "John Carter", "Estados Unidos", 41, 15,
                StatusAstronauta.EM_MISSAO));

        astronautas.add(new Astronauta(
                3L, "Mei Lin", "China", 29, 5,
                StatusAstronauta.AFASTADO));
    }

    public List<Astronauta> listarTodos(){
        return astronautas;
    }

    public List<Astronauta> filtrarPorStatus(StatusAstronauta status){
        List<Astronauta> resultado = new ArrayList<>();

        for (Astronauta astronauta : astronautas){
            if (astronauta.getStatus().equals(status)){
                resultado.add(astronauta);
            }
        }
        return resultado;
    }

    public Astronauta buscarPeloId(Long id){
        for (Astronauta astronauta : astronautas) {
            if (astronauta.getId().equals(id)){
                return astronauta;
            }
        }
        return null;
    }

    public void adicionar(Astronauta novoAstronauta){
        if (novoAstronauta.getNome() == null ||
                novoAstronauta.getNome().isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (novoAstronauta.getPais() == null ||
                novoAstronauta.getPais().isBlank()){
            throw new IllegalArgumentException("País é obrigatório.");
        }
        if (novoAstronauta.getIdade() <= 18){
            throw new IllegalArgumentException("Idade deve ser maior ou igual a 18 anos.");
        }
        if(novoAstronauta.getAnosExperiencia()<0){
            throw new IllegalArgumentException("Anos de experiência não pode ser negativo.");
        }
        if (novoAstronauta.getStatus() == null){
            throw new IllegalArgumentException("Status é obrigatório");
        }

        for (Astronauta astronauta : astronautas){
            if (astronauta.getNome().equalsIgnoreCase(novoAstronauta.getNome())){
                throw new IllegalArgumentException("Astronauta já existe.");
            }
        }
        novoAstronauta.setId(proximoId++);
        astronautas.add(novoAstronauta);
    }

    public boolean remover(Long id){
        boolean removeu = false;
        for(int i=0; i<astronautas.size(); i++){
            if(astronautas.get(i).getId().equals(id)){
                astronautas.remove(i);
                return true;
            }
        }
        return false;
    }

    public Astronauta atualizar (Long id, Astronauta novosDados){
        Astronauta astronauta = buscarPeloId(id);

        if (astronauta == null){
            return null;
        }
        if (novosDados.getNome() == null ||
                novosDados.getNome().isBlank()){
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (novosDados.getPais() == null ||
                novosDados.getPais().isBlank()){
            throw new IllegalArgumentException("País é obrigatório.");
        }
        if (novosDados.getIdade() <= 18){
            throw new IllegalArgumentException("Idade deve ser maior ou igual a 18 anos.");
        }
        if(novosDados.getAnosExperiencia()<0){
            throw new IllegalArgumentException("Anos de experiência não pode ser negativo.");
        }
        if (novosDados.getStatus() == null){
            throw new IllegalArgumentException("Status é obrigatório");
        }

        for (Astronauta outroAstronauta : astronautas){
            if (!outroAstronauta.getId().equals(id)
                    && outroAstronauta.getNome().equalsIgnoreCase(novosDados.getNome())){
                throw new IllegalArgumentException("Astronauta já cadastrado.");
            }
        }

        astronauta.setNome(novosDados.getNome());
        astronauta.setPais(novosDados.getPais());
        astronauta.setIdade(novosDados.getIdade());
        astronauta.setAnosExperiencia(novosDados.getAnosExperiencia());
        astronauta.setStatus(novosDados.getStatus());

        return astronauta;
    }



}

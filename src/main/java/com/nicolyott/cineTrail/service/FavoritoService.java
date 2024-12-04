package com.nicolyott.cineTrail.service;

import com.nicolyott.cineTrail.dto.FilmeDTO;
import com.nicolyott.cineTrail.entity.favorito.CategoriaFavorito;
import com.nicolyott.cineTrail.entity.favorito.Favorito;
import com.nicolyott.cineTrail.exception.CategoriaInvalidoException;
import com.nicolyott.cineTrail.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository repository;
    @Autowired
    private FilmeService service;

    public List<Favorito> obterFavoritos(){
        return repository.findAll();
    }

    public void adicionarAosFavoritos(Integer filmeId, CategoriaFavorito categoria){
       FilmeDTO filmeDTO = service.buscarFilmeId(filmeId);
       Favorito favorito = new Favorito(filmeDTO, categoria);
       repository.save(favorito);
    }

    public void deletarFavorito(Integer idTmdb){
        // verificar se favorito existe
        Favorito favorito = repository.findByIdTmdb(idTmdb);
        repository.delete(favorito);
    }

    public List<Favorito> buscarPorCategoria(@RequestParam String categoriaFavorito){
        try {
            CategoriaFavorito categoria = CategoriaFavorito.valueOf(categoriaFavorito.toUpperCase());
            return repository.findByCategoriaFavorito(categoria);
        } catch (IllegalArgumentException e) {
            throw new CategoriaInvalidoException("Categoria inválida: " + categoriaFavorito);
        }
    }

    public boolean validarCategoria(CategoriaFavorito categoriaFavorito){
        for(CategoriaFavorito categoria : CategoriaFavorito.values()) {
            if (categoriaFavorito.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

}

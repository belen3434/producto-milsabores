package milsabores.producto.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import milsabores.producto.model.Categoria;
import milsabores.producto.repository.CategoriaRepository;


@Service
public class CategoriaService {

@Autowired
private CategoriaRepository categoriaRepository;

public Categoria guardarCategoria(Categoria categoria){
    return categoriaRepository.save(categoria);}


    // Método Listar todos los productos
public List<Categoria> listarTodos(){
    return categoriaRepository.findAll();
}
}

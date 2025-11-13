package milsabores.producto.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import milsabores.producto.model.Inventario;
import milsabores.producto.repository.InventarioRepository;


@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public Inventario guardarStock(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

     public List<Inventario> listarTodos() {
        return inventarioRepository.findAll();
    }

}
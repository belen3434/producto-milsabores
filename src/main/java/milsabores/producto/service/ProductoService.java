package milsabores.producto.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import milsabores.producto.model.Producto;
import milsabores.producto.repository.ProductoRepository;



//Service – La lógica del negocio
//Aquí haces los cálculos o reglas de la app.
//Sirve para: hacer todo el “cerebro” de la app (precios, reglas, validaciones).
//Aquí decides cómo se gestionan los productos.

@Service
public class ProductoService {

    @Autowired //crea una instancia automatica manejada por spring
    private ProductoRepository productoRepository;

    // Save : Guarda el producto
    // Guarda un nuevo producto en la base de datos.

    public Optional <Producto> guardarProducto(Producto producto) {
        Producto productoGuardado = productoRepository.save(producto);// te devuelve lo que encuentra
        return productoRepository.findById(productoGuardado.getId()); // te devuelve nombre marca y categoria
    }

    // Método Listar todos los productos
    // Devuelve todos los productos guardados en la base de datos.
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // BUSCAR POR ID
    // Busca un producto por su id
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Eliminar
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    //listamos los productos destacados (17-11-25)
    public List<Producto> listarDestacados() {
        return productoRepository.findTop3ByOrderByIdDesc();
    }

    // Método para reducir stock después de una venta
    public boolean reducirStock(Long productoId, Integer cantidad) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isEmpty()) {
            throw new RuntimeException("Producto no encontrado");
        }

        //TODO: Implementar lógica de stock
        //Aqui manejaremos el stock correctamente
    
        return true; // Temporal - implementar lógica real con tabla inventario
    }

}


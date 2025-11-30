package milsabores.producto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import milsabores.producto.model.Inventario;
import milsabores.producto.model.Producto;

public interface InventarioRepository extends JpaRepository<Inventario,Long> {

    Optional<Inventario> findByProducto(Producto producto);
    Optional<Inventario> findByProductoId(Long productoId);

}
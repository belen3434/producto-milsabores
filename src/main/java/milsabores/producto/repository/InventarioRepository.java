package milsabores.producto.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import milsabores.producto.model.Inventario;



public interface InventarioRepository extends JpaRepository<Inventario,Long> {

}

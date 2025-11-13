package milsabores.producto.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import milsabores.producto.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

}
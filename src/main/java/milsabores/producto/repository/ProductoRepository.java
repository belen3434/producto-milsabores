package milsabores.producto.repository;



//Repository:para conectarse con la BD y hacer operaciones CRUD
// Una interfaz que hereda de *JPaRepository* incluye todas las
// operaciones básicas de acceso a datos:
//Buscar x ID,guardar,eliminar,listar,etc

// Sirve para: Para conectarse con la base de datos y 
//realizar operaciones CRUD 
//(crear, leer, actualizar, eliminar) sobre la entidad Producto.

//No necesitas escribir implementación; 
//Spring Data JPA lo hace automáticamente. 
//Tú solo defines la interfaz.

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import milsabores.producto.model.Producto;


//interfaz que se conecta a la base de datos sin que tengas que escribir código SQL.
public interface ProductoRepository extends JpaRepository<Producto,Long> { 
    
    //agrego consultas personalizadas
    List<Producto> findByCategoria_Nombre(String categoria); //nombre de categoria

    List<Producto> findTop3ByOrderByIdDesc(); //agregué metodo para devolver los ultimos 3 product. agregados(17-11-25)

    
}
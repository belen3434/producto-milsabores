package milsabores.producto.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import milsabores.producto.controller.ProductoController;
import milsabores.producto.model.Producto;

/**
 * Ensamblador HATEOAS para la entidad Producto.
 * Su función es envolver un objeto Producto dentro de un EntityModel que contiene enlaces navegables (hypermedia).
 * Esto permite que la API sea más autoexplicativa y orientada a acciones posibles sobre los recursos.
 */
@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    /**
     * Transforma un objeto Producto en un EntityModel que incluye enlaces HATEOAS.
     *
     * @param producto el objeto Producto que se va a transformar
     * @return un EntityModel con los datos del producto y enlaces útiles
     */
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            // Enlace al recurso actual (GET /productos/{id})
            linkTo(methodOn(ProductoController.class).obtenerProductoPorId(producto.getId())).withSelfRel(),

            // Enlace a la lista completa de productos (GET /productos)
            linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"),

            // Enlace para eliminar este producto (DELETE /productos/{id})
            linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("eliminar"));

            
    }
}
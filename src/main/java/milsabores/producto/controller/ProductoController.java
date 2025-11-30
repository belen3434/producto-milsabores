package milsabores.producto.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import milsabores.producto.assemblers.ProductoModelAssembler;
import milsabores.producto.model.Producto;
import milsabores.producto.service.ProductoService;

@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowCredentials = "true"
)
@RestController // Controlador REST
@RequestMapping("/api/v1/productos") // Ruta base para este recurso

public class ProductoController {

    @Autowired
    private ProductoModelAssembler assembler;

    @Autowired
    private ProductoService productoService;
    public Object listarProductos;

    @Operation(summary = "Guarda un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto guardado exitosamente", 
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "500", description = "El producto no se ha podido guardar,intente nuevamente...") })

    @PostMapping // RECIBE PETICIONES DE TIPO POST
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    public Optional<Producto> guardarProducto(@RequestBody Producto producto) { // todo lo del cuerpo de petición se lo
                                                                                // paso a la // variable producto
        return productoService.guardarProducto(producto);
    }

    @Operation(summary = "Obtiene el listado de todos los Productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida en forma exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Listado de productos no encontrado") })

    @GetMapping // RECIBE PETICIONES DE TIPO GET
    public List<Producto> listarProductos() { // Devuelve todos los productos
        return productoService.listarTodos();
    }



    @Operation(summary = "Obtiene un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID Producto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "ID no encontrado")
    })
    @GetMapping("/{id}") // GET POR ID
    public EntityModel<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return assembler.toModel(producto); // Busca un producto por su ID
    }

    //lista prod destacados
    @GetMapping("/destacados")
public List<Producto> productosDestacados() {
    return productoService.listarDestacados();
}

    @Operation(summary = "Actualiza un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoService.buscarPorId(id);
        if (productoExistente.isPresent()) {
            producto.setId(id);
            Optional<Producto> productoActualizado = productoService.guardarProducto(producto);
            return productoActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Elimina un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto Eliminado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado.")
    })
    @DeleteMapping("/{id}") // PETICIÓN DELETE
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id); // Elimina el producto por ID
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reducir stock de producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock reducido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente")
    })
    @PutMapping("/{id}/reducir-stock")
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEDOR')")
    public ResponseEntity<Producto> reducirStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        try {
            Optional<Producto> productoOpt = productoService.buscarPorId(id);
            if (productoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Producto producto = productoOpt.get();
            
            // Verificar stock suficiente
            if (producto.getStock() < cantidad) {
                return ResponseEntity.badRequest().build();
            }
            
            // Reducir stock
            producto.setStock(producto.getStock() - cantidad);
            
            // Si stock llega a 0, marcar como no disponible
            if (producto.getStock() <= 0) {
                producto.setDisponible(false);
            }
            
            Optional<Producto> productoActualizado = productoService.guardarProducto(producto);
            return productoActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
            
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
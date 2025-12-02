package milsabores.producto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import milsabores.producto.model.Categoria;
import milsabores.producto.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(
    origins = {
        "http://localhost:5173", 
        "http://mil-sabores-final.s3-website-us-east-1.amazonaws.com",
        "http://44.213.57.93",
        "http://44.213.57.93:5173"
    }, 
    allowedHeaders = "*", 
    allowCredentials = "true"
)
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Obtiene el listado de todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Listado de Categorías no encontrado") })
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listarTodos();
    }

    @Operation(summary = "Obtiene una categoría por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.obtenerPorId(id);
        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crea una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "500", description = "La Categoría no se ha guardado, intente nuevamente...") })
    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        if (categoria.getActiva() == null) {
            categoria.setActiva(true);
        }
        return categoriaService.guardarCategoria(categoria);
    }

    @Operation(summary = "Actualiza una categoría")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaService.obtenerPorId(id);
        if (categoriaExistente.isPresent()) {
            categoria.setId(id);
            Categoria categoriaActualizada = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(categoriaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Desactiva una categoría")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Categoria> desactivarCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaService.obtenerPorId(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setActiva(false);
            Categoria categoriaActualizada = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(categoriaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Activa una categoría")
    @PutMapping("/{id}/activate")
    public ResponseEntity<Categoria> activarCategoria(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaService.obtenerPorId(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setActiva(true);
            Categoria categoriaActualizada = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(categoriaActualizada);
        }
        return ResponseEntity.notFound().build();
    }
}

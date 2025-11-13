package milsabores.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Guarda una categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria guardada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "500", description = "La Categoria no se ha guardado, intente nuevamente...") })

    @PostMapping // RECIBE PETICIONES DE TIPO POST
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaService.guardarCategoria(categoria);
    }

    @Operation(summary = "Obtiene el listado de todas la categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido de forma exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Listado de Categorias no encontrado") })
    @GetMapping //// RECIBE PETICIONES DE TIPO GET
    public List<Categoria> listar() {
        return categoriaService.listarTodos();
    }

}
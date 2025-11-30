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
import milsabores.producto.model.Inventario;
import milsabores.producto.service.InventarioService;

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Guarda Stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock guardado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "500", description = "El Stock no se ha podido guardar, intente nuevamente...") })

    @PostMapping // RECIBE PETICIONES DE TIPO GET
    public Inventario guardarStock(@RequestBody Inventario inventario) {
        return inventarioService.guardarStock(inventario);
    }

    @Operation(summary = "Obtiene un listado de el Stock de cada producto ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida de forma exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "Listado de Stock no encontrado") })
    @GetMapping // RECIBE PETICIONES DE TIPO GET
    public List<Inventario> listar() {
        return inventarioService.listarTodos();
    }

}

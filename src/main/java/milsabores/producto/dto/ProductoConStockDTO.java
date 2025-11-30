package milsabores.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConStockDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private boolean disponible;
    private String rutaImagen;
    private Long categoriaId;
    private String categoriaNombre;
    private Integer stock;
}
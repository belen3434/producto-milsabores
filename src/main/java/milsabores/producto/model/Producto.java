package milsabores.producto.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // una tabla en la BD,cada atribut es una columna en la tabla
@Table(name = "productos") // Nombre de la tabla en la BD
@Data   //Getter and Setter 
@NoArgsConstructor //Constructor sin parametros
@AllArgsConstructor // Constructor con parametros



// Model – Los datos
//Aquí defines las clases que representan las cosas del mundo real
//Sirve para: decirle al sistema qué datos existen.

public class Producto {
    @Id //esta es la primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // incrementa automático ese id
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;


    @Column(length = 255, nullable = true)
    private String descripcion;

    //Relación con Categoría
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = true) //FK
    private Categoria categoria;



    @Column(nullable = false)
    private Double precio;
    private boolean disponible;

    @Column(nullable = false)
    private String rutaImagen;
}
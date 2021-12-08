package com.solmov.appmovillasparras.Modulos.Mantenimiento.Atencion;

public class ModeloAtencion {

    ModeloAtencion() {
    }
    ModeloAtencion(
            String nombre,
            String tipo_producto,
            String descripcion,
            String precio,
            String estado
    )

    {
        this.Nombre=nombre;
        this.Tipo_Producto=tipo_producto;
        this.Descripcion=descripcion;
        this.Precio=precio;
        this.Estado=estado;
    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo_Producto() {
        return Tipo_Producto;
    }

    public void setTipo_Producto(String tipo_Producto) {
        Tipo_Producto = tipo_Producto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    private String Nombre;
    private String Tipo_Producto;
    private String Descripcion;
    private String Precio;

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    private String Estado;


}

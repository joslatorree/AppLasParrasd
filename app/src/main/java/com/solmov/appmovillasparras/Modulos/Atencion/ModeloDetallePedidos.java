package com.solmov.appmovillasparras.Modulos.Atencion;

public class ModeloDetallePedidos {
    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    private String Cantidad;
    private String Nombre;
    private String Precio;

    ModeloDetallePedidos() {
    }
    ModeloDetallePedidos(
            String cantidad,
            String nombre,
            String precio
    )

    {
        this.Cantidad=cantidad;
        this.Nombre=nombre;
        this.Precio=precio;
    }
}

package com.solmov.appmovillasparras.Modulos.Atencion;

public class ModeloPedidos {

    ModeloPedidos() {
    }
    ModeloPedidos(
            String estado,
            String fecha,
            String hora,
            String preciototal,
            String usuario
    )

    {
        this.Estado=estado;
        this.Fecha=fecha;
        this.Hora=hora;
        this.PrecioTotal=preciototal;
        this.Usuario=usuario;
    }
    private String Estado;
    private String Fecha;

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getPrecioTotal() {
        return PrecioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        PrecioTotal = precioTotal;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    private String Hora;
    private String PrecioTotal;
    private String Usuario;


}

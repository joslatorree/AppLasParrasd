package com.solmov.appmovillasparras.Modulos.Mantenimiento.Usuarios;

public class ModeloUsuarios {

        ModeloUsuarios() {
        }
        ModeloUsuarios(
                       String apellidos,
                       String dni,
                       String email,
                       String estado,
                       String movil,
                       String nombre,
                       String password,
                       String sexo,
                       String tipo_usuario,
                       String usuario
        )

        {
            this.Apellidos=apellidos;
            this.DNI=dni;
            this.Email=email;
            this.Estado=estado;
            this.Movil=movil;
            this.Nombre=nombre;
            this.Password=password;
            this.Sexo=sexo;
            this.Tipo_Usuario=tipo_usuario;
            this.Usuario=usuario;
        }
        public String getApellidos() {
            return Apellidos;
        }

        public void setApellidos(String apellidos) {
            Apellidos = apellidos;
        }

        public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }

        public String getDNI() {
            return DNI;
        }

        public void setDNI(String DNI) {
            this.DNI = DNI;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getEstado() {
            return Estado;
        }

        public void setEstado(String estado) {
            Estado = estado;
        }

        public String getMovil() {
            return Movil;
        }

        public void setMovil(String movil) {
            Movil = movil;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getSexo() {
            return Sexo;
        }

        public void setSexo(String sexo) {
            Sexo = sexo;
        }

        public String getTipo_Usuario() {
            return Tipo_Usuario;
        }

        public void setTipo_Usuario(String tipo_Usuario) {
            Tipo_Usuario = tipo_Usuario;
        }

        public String getUsuario() {
            return Usuario;
        }

        public void setUsuario(String usuario) {
            Usuario = usuario;
        }

        private String Apellidos;
        private String Nombre;
        private String DNI;
        private String Email;
        private String Estado;
        private String Movil;
        private String Password;
        private String Sexo;
        private String Tipo_Usuario;
        private String Usuario;
 /*

    private int imageview1;


    ModeloUsuarios(int imageview1,
               String textview1,
               String textview2,
               String textview3,
               String divider)
    {
        this.imageview1=imageview1;
        this.textview1=textview1;
        this.textview2=textview2;
        this.textview3=textview3;
        this.divider=divider;
    }
    public int getImageview1() {
        return imageview1;
    }

    public void setImageview1(int imageview1) {
        this.imageview1 = imageview1;
    }

    public String getTextview1() {
        return textview1;
    }

    public void setTextview1(String textview1) {
        this.textview1 = textview1;
    }

    public String getTextview2() {
        return textview2;
    }

    public void setTextview2(String textview2) {
        this.textview2 = textview2;
    }

    public String getTextview3() {
        return textview3;
    }

    public void setTextview3(String textview3) {
        this.textview3 = textview3;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    private String textview1;
    private String textview2;
    private String textview3;
    private String divider;
   */
}


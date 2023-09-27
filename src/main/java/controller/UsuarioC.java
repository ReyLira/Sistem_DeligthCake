package controller;

import dao.UsuarioD;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.Cliente;

import lombok.Data;
import model.Empleado;
import services.EmailS;

@Data
@Named(value = "UsuarioC")
@SessionScoped
public class UsuarioC implements Serializable {

    Cliente cliente;
    UsuarioD dao;
    String usuario;
    String password;
    Empleado empleado;
    String email;
    boolean bloquear = false;
    int intentos = 0;
    int captcha = 0;
    int tiempo = 20;

    public UsuarioC() {
        cliente = new Cliente();
        empleado = new Empleado();
        dao = new UsuarioD();
    }

    public void login() {
        try {
            empleado = dao.loginRolEmpleado(usuario, password);
            this.loginRolEmpleado();
             if (dao.rol != null) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoEmpleado", empleado);
                    switch (dao.rol) {
                        case "A":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/vistas/Plantilla.xhtml");
                            break;
                        case "E":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/vistaTrabajador/Plantilla.xhtml");
                            break;
                        default:
                            break;
                    }
                }
             cliente = dao.loginCliente(usuario, password);
            if (dao.logueo == true) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoUsuario", cliente);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/vistaCliente/Producto.xhtml");      
            } else {
           
                intentos++;
                limpiar();

                if (intentos == 1) {
                    setIntentos(1);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
                }
                if (intentos == 2) {
                    setIntentos(2);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 1 INTENTOS", ""));
                }
                if (intentos == 3) {
                    setIntentos(3);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VUELVE A INTENTARLO", ""));
//                             bloquear = true;
//                             if (intentos == 3) {         
//                                  tiempoBloquear();
//                            }
                }
                if (intentos == 4) {
                    setIntentos(4);
                    setCaptcha(1);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
                }
                if (intentos == 5) {
                    setIntentos(5);
                    setCaptcha(1);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 1 INTENTOS", ""));
                }
                if (intentos == 6) {
                    setIntentos(0);
                    setCaptcha(0);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VUELVE A INTENTARLO", ""));
                    bloquear = true;
                    if (bloquear) {
                        tiempoBloquear();
                    }
//                    if (intentos == 6) {
//                        setIntentos(0);
//                        setCaptcha(0);
//                    }
                }
//                switch (intentos) {
//                        case 1:
//                            setIntentos(1);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
//                            break;
//                        case 2:
//                            setIntentos(2);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 1 INTENTOS", ""));
//                            break;
//                        case 3:
//                            setIntentos(3);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VUELVE A INTENTARLO", ""));
////                             bloquear = true;
////                             if (intentos == 3) {         
////                                  tiempoBloquear();
////                            }
//                            break;
//                        case 4:
//                            setIntentos(4);
//                            setCaptcha(1);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "1 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 2 INTENTOS", ""));
//                            break;
//                        case 5:
//                            setIntentos(5);
//                            setCaptcha(1);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "2 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "LE QUEDAN 1 INTENTOS", ""));
//                            break;
//                        case 6:
//                            setIntentos(6);
//                            System.out.println("intentos igual " + intentos);
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "3 INTENTO FALLIDO", "Usuario/Contraseña incorrectas"));
//                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "VUELVE A INTENTARLO", ""));
//                             bloquear = true;
//                              if (bloquear) {         
//                                  tiempoBloquear();
//                            }
//                         if (intentos == 6) {
//                             setIntentos(0);
//                             setCaptcha(0);
//                        }
//                            break;
//                        default:
//                            System.out.println("no ingresa");
//                            break;
//                    }
            }

        } catch (Exception e) {
            System.out.println("Error en el loginC" + e.getMessage());
        }

    }

    public void restablecerPwdCliente() throws Exception {
        try {
            String email = this.email;
            EmailS.restablecerPwdCliente(email);
            limpiar();
        } catch (Exception e) {
            System.out.println("Error en modificarPwdCli_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void limpiar() {
        cliente = new Cliente();
    }

    public void tiempoBloquear() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Error en delaySegundo_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void activarSesion() {
        tiempo = 20;
    }

    public void tiempoSesion() throws IOException {
        try {
            if (tiempo > 0) {
                tiempo--;
            } else if (tiempo == 0) {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml");
                System.out.println("SESIÓN CERRADA, TIEMPO SIN ACTIVIDAD");
            }
        } catch (Exception e) {
            System.out.println("Error en tiempoSesion_C " + e.getMessage());
        }
    }
    
    public void loginRolEmpleado() throws Exception {
        try {
            dao.loginRolEmpleado(usuario, password);
        } catch (Exception e) {
            System.out.println("Error en loginNivel_C" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Cliente obtenerObjetoSesionCliente() {
        return (Cliente) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objetoUsuario");
    }
    
    public static Empleado obtenerObjetoSesionEmpleado() {
        return (Empleado) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objetoEmpleado");
    }
    
    public void seguridadSesionCliente() throws IOException {
        if (obtenerObjetoSesionCliente()== null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml");
        }
    }
    
    public void seguridadSesionEmpleado() throws IOException {
        if (obtenerObjetoSesionEmpleado()== null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml");
        }
    }
    
    public void cerrarSesion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/Login.xhtml");
    }

//     public void login2() {
//        try {
//            cliente = dao.loginEmpleado(cliente);
////            empleado = dao.loginEmpleado(empleado);
//            if (dao.logueo == true) {
////                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoCliente", cliente);
////                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoEmpleado", empleado);
//                FacesContext.getCurrentInstance().getExternalContext().redirect("/AS211S3_T05_DELIGHTCAKE/faces/vistas/Plantilla.xhtml");
//            } else {
//                System.out.println("no puedes entrar");
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso al Sistema", "Usuario y/o contraseña incorrecta"));
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error en el loginC" + e.getMessage());
//        }
//
//    }
}

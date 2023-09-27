package controller;

import dao.UbigeoD;
import model.Ubigeo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "ubigeoC")
//@Dependent
@SessionScoped
public class UbigeoC implements Serializable {

    private Ubigeo ubigeo;
    private UbigeoD dao;
    private List<Ubigeo> listadoUbigeo;

    public UbigeoC() {
        ubigeo = new Ubigeo();
        dao = new UbigeoD();
        listadoUbigeo = new ArrayList<>();
    }

    public void listar() {
        try {
            listadoUbigeo = dao.listarTodos();
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "\"Error en ListarC ", e.getMessage());
        } finally {

        }
    }

    @PostConstruct
    public void construir() {
        listar();
    }

}

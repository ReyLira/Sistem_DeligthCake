package dao;

import java.util.List;

public interface ICRUD<Generica> {

    void guardar(Generica gen) throws Exception;

    void modificar(Generica gen) throws Exception;

    void eliminar(Generica gen) throws Exception;

    void restaurar(Generica gen) throws Exception;
    
    List<Generica> listarTodos(int tipo) throws Exception;
}

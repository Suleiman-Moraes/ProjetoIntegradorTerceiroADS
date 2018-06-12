package service;

import interfaces.ICrudService;
import java.sql.SQLException;
import java.util.Iterator;
import model.Passageiro;
import persistencia.PassageiroDao;

public class PassageiroService implements ICrudService<Passageiro>{

    @Override
    public void salvar(Passageiro t) throws Exception {
        if(t.getId() != 0){
            new PassageiroDao().alterar(t);
        }
        else{
            new PassageiroDao().inserir(t);
        }
    }

    @Override
    public void deletar(Object... object) throws SQLException {
        new PassageiroDao().deletar(object);
    }

    @Override
    public Passageiro visualizarUm(Object... object) throws SQLException {
        return new PassageiroDao().visualizarUm(object);
    }

    @Override
    public Iterator<Passageiro> visualizarAll() throws SQLException {
        return new PassageiroDao().visualizarAll().iterator();
    }
}

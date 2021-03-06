package service;

import enuns.StatusPassageiro;
import interfaces.BuscarPassandoLoginSenha;
import interfaces.ICrudService;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import model.Passageiro;
import persistencia.PassageiroDao;

public class PassageiroService implements ICrudService<Passageiro>, BuscarPassandoLoginSenha<Passageiro>{

    @Override
    public void salvar(Passageiro t) throws Exception {
        if(!ValidacaoComum.validaCPF(t.getCpf())){
            throw new Exception("CPF Inválido.");
        }
        if(!ValidacaoComum.isDateValid(t.getDataDeNascimento())){
            throw new Exception("Data de nascimento Inválida.");
        }
        if(!ValidacaoComum.validaCep(t.getEndereco().getCep())){
            throw new Exception("CEP Inválido.");
        }
        if(t.getId() != 0){
            new EnderecoService().salvar(t.getEndereco());
            new PassageiroDao().alterar(t);
        }
        else{
            List<Passageiro> aux = this.buscarPassandoQualquerCoisa("login", t.getLogin());
            if(aux != null && aux.size() != 0){
                throw new Exception("Login Inválido.");
            }
            aux = this.buscarPassandoQualquerCoisa("cpf", t.getCpf());
            if(aux != null && aux.size() != 0){
                throw new Exception("CPF Inválido.");
            }
            new EnderecoService().salvar(t.getEndereco());
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
    
    public Iterator<Passageiro> bucarPassageirosPassandoStatusEnum(StatusPassageiro statusPassageiro) throws SQLException {
        try {
            String condicao = " AND status_passageiro = '";
            condicao += statusPassageiro.getDescricao() + "'";
            return new PassageiroDao().bucarPassageirosPassandoParametros(condicao).iterator();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Passageiro> buscarPassandoLoginSenha(String login, String senha) throws SQLException {
        try {
            StringBuilder tudo = new StringBuilder("");
            tudo.append(" AND login = '");
            tudo.append(login);
            tudo.append("' AND senha = '");
            tudo.append(senha).append("'");
            return new PassageiroDao().bucarPassageirosPassandoParametros(tudo.toString());
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Passageiro> buscarPassandoQualquerCoisa(String coluna, String param) throws SQLException {
        try {
            StringBuilder tudo = new StringBuilder("");
            tudo.append(" AND ").append(coluna).append(" = '");
            tudo.append(param);
            tudo.append("'");
            return new PassageiroDao().bucarPassageirosPassandoParametros(tudo.toString());
        } catch (Exception e) {
            return null;
        }
    }
}

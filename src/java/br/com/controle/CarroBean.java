
package br.com.controle;

import br.com.DAO.CarroDAO;
import br.com.modelo.Carro;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean (name = "bean")
public class CarroBean {
    
    //criar os objetos 
    private Carro carro;
    private List<Carro> carros;
    private CarroDAO dao;
    
    //construtor
    public CarroBean(){
        carro = new Carro();
        dao = new CarroDAO();
        carros = dao.getListaCarros();
    }
    
    //get e set 

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }
    
    
    
    
    public void salvar(){
        dao.salvar(carro);
        carro = new Carro();
        carros = dao.getListaCarros();
    }
    
    public void alterar(){
        dao.alterar(carro);
        carro = new Carro();
        carros = dao.getListaCarros();
    }
    
    public void alterar(Carro car){
        carro = car;
    }
     
    public void remover(Carro car) {
        dao.excluir(car);
        car = new Carro();
        carros = dao.getListaCarros();
    }
    
}


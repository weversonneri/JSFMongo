package br.com.DAO;

import br.com.modelo.Carro;
import br.com.util.CarroCodec;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public class CarroDAO {
    private MongoClient cliente;
    private MongoDatabase bancoDeDados;
    
    
    public void salvar(Carro car) {
        abrirConexao();
        MongoCollection<Carro> carros = this.bancoDeDados.getCollection("carros", Carro.class);
        carros.insertOne(car);
        fecharConexao();
    }
    
    public void alterar(Carro car) {
        abrirConexao();
        MongoCollection<Carro> carros = this.bancoDeDados.getCollection("carros", Carro.class);
        
        Document query = new Document();
        query.append("_id",car.getId());
        
        Document setData = new Document();
        setData.append("placa", car.getPlaca()).append("modelo", car.getModelo()).append("marca", car.getMarca()).append("preco", car.getPreco());
        
        Document update = new Document();
        update.append("$set", setData);
       
        carros.updateOne(query, update);
        
        fecharConexao();
    }
    
    public void excluir(Carro car) {
        abrirConexao();
        MongoCollection<Carro> carros = this.bancoDeDados.getCollection("carros", Carro.class);
        carros.deleteOne(Filters.eq("_id",car.getId()));
        fecharConexao();
    }
    
    
    public List<Carro> getListaCarros() {
        abrirConexao();
        MongoCollection<Carro> carros = this.bancoDeDados.getCollection("carros", Carro.class);
        MongoCursor<Carro> resultado = carros.find().iterator();
        List<Carro> carrosEncontrados = new ArrayList<>();

        while (resultado.hasNext()) {
            Carro carro = resultado.next();
            carrosEncontrados.add(carro);
        }

        fecharConexao();
        return carrosEncontrados;

    }
    
    
     private void abrirConexao() {
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        CarroCodec carCodec = new CarroCodec(codec);

        CodecRegistry registro = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(carCodec));

        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();

        cliente = new MongoClient("localhost:27017", options);
        bancoDeDados = cliente.getDatabase("crudcarro");
    }

    private void fecharConexao() {
        this.cliente.close();
    }

}

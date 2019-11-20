package br.com.util;

import br.com.modelo.Carro;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;


public class CarroCodec  implements CollectibleCodec<Carro>{

    //criar um objeto codec do tipo Coded<Document>
    private Codec<Document> codec;
    
    //método construtor 
    public CarroCodec(Codec<Document> codec){
        this.codec  = codec;
    }
    
    @Override
    public Carro generateIdIfAbsentFromDocument(Carro car) {
        return documentHasId(car) ? car.criaId() : car;
    }

    @Override
    public boolean documentHasId(Carro car) {
        return car.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(Carro car) {
        if(!documentHasId(car)){
            throw new IllegalStateException("Esse Documento não tem id");
        }
            return new BsonString(car.getId().toHexString());
    
    }

    @Override
    public void encode(BsonWriter writer, Carro car, EncoderContext ec) {
        ObjectId id = car.getId();
        String placa = car.getPlaca();
        String modelo = car.getModelo();
        String marca = car.getMarca();
        Double preco = car.getPreco();
        
        Document documento = new Document();
        documento.put("_id", id);
        documento.put("placa",placa);
        documento.put("modelo", modelo);
        documento.put("marca", marca);
        documento.put("preco", preco);
        
        codec.encode(writer, documento, ec);
    }


    @Override
    public Class<Carro> getEncoderClass() {
        return Carro.class;
    }

    @Override
    public Carro decode(BsonReader reader, DecoderContext dc) {
        Document document = codec.decode(reader, dc);
        Carro car = new Carro();

        car.setId(document.getObjectId("_id"));
        car.setPlaca(document.getString("placa"));
        car.setModelo(document.getString("modelo"));
        car.setMarca(document.getString("marca"));
        car.setPreco(document.getDouble("preco"));
        return car;
    }
    
}

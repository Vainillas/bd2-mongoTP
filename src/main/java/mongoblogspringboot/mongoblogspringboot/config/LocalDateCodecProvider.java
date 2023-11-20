package mongoblogspringboot.mongoblogspringboot.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.LocalDate;

public class LocalDateCodecProvider implements CodecProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == LocalDate.class) {
            return (Codec<T>) new LocalDateCodec();
        }
        return null;
    }
}
package mongoblogspringboot.mongoblogspringboot.config;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class LocalDateCodec implements Codec<LocalDate> {

    @Override
    public void encode(BsonWriter writer, LocalDate value, EncoderContext encoderContext) {
        writer.writeDateTime(value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
    }

    @Override
    public LocalDate decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.DATE_TIME) {
            return Instant.ofEpochMilli(reader.readDateTime()).atZone(ZoneOffset.UTC).toLocalDate();
        } else if (reader.getCurrentBsonType() == BsonType.STRING) {
            return LocalDate.parse(reader.readString());
        } else {
            throw new UnsupportedOperationException("Cannot decode " + reader.getCurrentBsonType());
        }
    }

    @Override
    public Class<LocalDate> getEncoderClass() {
        return LocalDate.class;
    }

    public static Codec<LocalDate> newInstance() {
        return new LocalDateCodec();
    }

    public static Codec<LocalDate> newInstance(CodecRegistry registry) {
        return new LocalDateCodec();
    }
}

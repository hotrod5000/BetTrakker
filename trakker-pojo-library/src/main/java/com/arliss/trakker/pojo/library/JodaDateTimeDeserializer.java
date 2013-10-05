package com.arliss.trakker.pojo.library;

import com.google.gson.*;
import org.apache.commons.codec.binary.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 10/5/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
import java.lang.reflect.Type;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 *  GSON serialiser/deserialiser for converting Joda {@link DateTime} objects.
 */
public class JodaDateTimeDeserializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime>
{
    /**
     *  Gson invokes this call-back method during serialization when it encounters a field of the
     *  specified type. <p>
     *
     *  In the implementation of this call-back method, you should consider invoking
     *  {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     *  non-trivial field of the {@code src} object. However, you should never invoke it on the
     *  {@code src} object itself since that will cause an infinite loop (Gson will call your
     *  call-back method again).
     *
     *  @param src the object that needs to be converted to Json.
     *  @param typeOfSrc the actual type (fully genericized version) of the source object.
     *  @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context)
    {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateHourMinuteSecond();//.dateTime();
        return new JsonPrimitive(fmt.print(src));
    }

    /**
     *  Gson invokes this call-back method during deserialization when it encounters a field of the
     *  specified type. <p>
     *
     *  In the implementation of this call-back method, you should consider invoking
     *  {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     *  for any non-trivial field of the returned object. However, you should never invoke it on the
     *  the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     *  call-back method again).
     *
     *  @param json The Json data being deserialized
     *  @param typeOfT The type of the Object to deserialize to
     *  @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     *  @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        final DateTimeFormatter fmt = ISODateTimeFormat.dateHourMinuteSecond();
        return fmt.parseDateTime(json.getAsString());
    }
}

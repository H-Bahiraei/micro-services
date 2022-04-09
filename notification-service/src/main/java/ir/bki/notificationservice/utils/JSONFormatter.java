package ir.bki.notificationservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * We got this class from Paypal
 * JSONFormatter converts objects to JSON representation and vice-versa. This
 * class depends on Google's GSON library to do the transformation. This class
 * is not thread-safe.
 */
public final class JSONFormatter {
    public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXX";
    private static SimpleDateFormat sdf;

    /**
     * FieldNamingPolicy used by the underlying Gson library. Alter this
     * property to set a fieldnamingpolicy other than
     * LOWER_CASE_WITH_UNDERSCORES used by PayPal REST APIs
     */
    private static final FieldNamingPolicy FIELD_NAMING_POLICY = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
    /**
     * Gson
     */
    public static Gson GSON = new GsonBuilder()
              .setPrettyPrinting()
            .setFieldNamingPolicy(FIELD_NAMING_POLICY).create();

    public static Gson GSON_FULL = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FIELD_NAMING_POLICY).create();

    /*
     * JSONFormatter is coupled to the stubs generated using the SDK generator.
     * Since PayPal REST APIs support only JSON, this class is bound to the
     * stubs for their json representation.
     */
    private JSONFormatter() {
    }

    /**
     * Set a format for gson FIELD_NAMING_POLICY. See {@link FieldNamingPolicy}
     *
     * @param FIELD_NAMING_POLICY
     */
    public static final void setFIELD_NAMING_POLICY(
            FieldNamingPolicy FIELD_NAMING_POLICY) {
        GSON = new GsonBuilder().setPrettyPrinting()
                .setFieldNamingPolicy(FIELD_NAMING_POLICY).create();
    }

    /**
     * Converts a Raw Type to JSON String
     *
     * @param <T> Type to be converted
     * @param t   Object of the type
     * @return JSON representation
     */

    public static <T> String toJSONFull(T t) {
        if (t != null)
            return GSON_FULL.toJson(t);
        return "{}";
    }

    public static SimpleDateFormat getSDFInstance() {
        if (sdf == null) {
            sdf = new SimpleDateFormat(PATTERN);
        }
        return sdf;
    }

    public static <T> String toJSONFaster(T t) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, t);
        } catch (Exception exception) {
            return "Cant serialize with FatsterXml! we do it with GSON! " + toJSON(GSON);
        }
        return writer.toString();
    }


    /**
     * Converts a Raw Type to JSON String
     *
     * @param <T> Type to be converted
     * @param t   Object of the type
     * @return JSON representation
     */

    public static <T> String toJSON(T t) {
        return toJSON(GSON, t);
    }

    public static <T> String toJSON(Gson gson, T t) {
        if (t != null)
            return gson.toJson(t);
        return "{}";
    }

    public static <T> String toJSONNormal(T t) {
        Gson GSON = new GsonBuilder().create();
        if (t != null)
            return GSON.toJson(t);
        return "{}";
    }

    public static <T> String toJSON(T t, Type typeOfT) {
        return toJSON(GSON, t, typeOfT);
    }

    public static <T> String toJSON(Gson gson, T t, Type typeOfT) {
        if (t != null)
            return gson.toJson(t, typeOfT);
        return "{}";
    }

    /**
     * Converts a JSON String to object representation
     *
     * @param <T>        Type to be converted
     * @param jsonString JSON representation
     * @param clazz      Target class
     * @return Object of the target type
     */
    public static <T> T fromJSON(String jsonString, Class<T> clazz) {
        return fromJSON(GSON, jsonString, clazz);
    }

    public static <T> T fromJSON(Gson gson, String jsonString, Class<T> clazz) {
        T t = null;
        if (clazz.isAssignableFrom(jsonString.getClass())) {
            t = clazz.cast(jsonString);
        } else {
            t = gson.fromJson(jsonString, clazz);
        }
        return t;
    }

    public static <T> T fromJSON(Gson gson, String jsonString, Type typeOfT) throws JsonSyntaxException {
        if (jsonString == null) {
            return null;
        } else {
            StringReader reader = new StringReader(jsonString);
            T target = gson.fromJson(reader, typeOfT);
            return target;
        }
    }

    public static <T> T fromJSON(String jsonString, Type typeOfT) throws JsonSyntaxException {
        return fromJSON(GSON, jsonString, typeOfT);
    }

    public static <T> Map toMap(T t) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return GSON.fromJson(toJSON(t), type);
    }

    //ArrayList<Element> arrayList = new ArrayList<Element>(Arrays.asList(array));
//    public static <T> List<T> fromJSONArray(String jsonArray, Class<T> clazz) {
//        System.out.println("hiiiiiiiiii, i am in JSON Formatter");
//        Type listType = new TypeToken<ArrayList<T>>(){}.getType();
//        return  new Gson().fromJson(jsonArray, listType);
//    }
//    public static <T> T fromJSONArrayNew(String jsonArray, Class<T> clazz) {
//        T t = null;
//        if (clazz.isAssignableFrom(jsonArray.getClass())) {
//            t = clazz.cast(jsonArray);
//        } else {
//            t = GSON.fromJson(jsonArray, clazz);
//        }
//        return t;
//    }

    public static boolean isJsonValid(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(json);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

}

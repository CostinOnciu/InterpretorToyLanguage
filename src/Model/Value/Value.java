package Model.Value;

import Model.Type.Type;

import java.lang.reflect.InvocationTargetException;

public interface Value {
    Type getType();

    @SuppressWarnings("unchecked")
    static <V extends Value> V copy(V value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> val = value.getClass();
        return (V)val.getConstructor(val).newInstance(value);
    }
}

package Model.Type;

import Model.Value.Value;

import java.lang.reflect.InvocationTargetException;

public interface Type {
    Value defaultValue();

    @SuppressWarnings("unchecked")
    static <T extends Type> T copy(T type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> typ = type.getClass();
        return (T)typ.getConstructor(typ).newInstance(type);
    }
}

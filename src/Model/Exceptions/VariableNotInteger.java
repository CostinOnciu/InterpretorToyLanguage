package Model.Exceptions;

public class VariableNotInteger extends MyExceptions{
    public VariableNotInteger() {
    }

    public VariableNotInteger(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "VariableNotInteger{"+getMessage()+"}";
    }
}

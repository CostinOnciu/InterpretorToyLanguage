package Model.Exceptions;

public class VariableNotBoolean extends MyExceptions{
    public VariableNotBoolean() {
    }

    public VariableNotBoolean(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "VariableNotBoolean{"+getMessage()+"}";
    }
}

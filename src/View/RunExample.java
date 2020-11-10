package View;
import Controller.Controller;
import Model.Exceptions.MyExceptions;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try{
            ctr.allStep(0); }
        catch (MyExceptions error) {
            System.out.println("ERROR(" + error.getMessage() + ")");
        } //here you must treat the exceptions that can not be solved in the controller
    }
}

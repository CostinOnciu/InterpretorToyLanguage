package View;
import Controller.Controller;
import Model.Exceptions.MyExceptions;

import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;

public class View {
    Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void menu(){
        System.out.println("1.int v; v=2;Print(v)");
        System.out.println("2.int a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("3.bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        System.out.println("4.a=true;");
        System.out.println("5.int a; a=true;");
    }

    public void run() throws MyExceptions {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String operation = "x";
        menu();
        while(true) {
            try {
                operation = reader.readLine();
            }
            catch (java.io.IOException ex) {
                System.out.println(ex);
            }
            switch (operation){
                case "m":
                    menu();
                    break;
                case "x":
                    return;
                case "1":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                case "2":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                case "3":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                case "4":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                case "5":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                case "6":
                    controller.allStep(Integer.parseInt(operation));
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }
        }
    }
}

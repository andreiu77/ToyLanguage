package view;

import controller.IController;

import java.util.Scanner;

public class View implements IView{
    IController controller;

    public View(IController controller) {
        this.controller = controller;
    }

    @Override
    public int inputProgram() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter program:");
        System.out.println("1.Example1");
        System.out.println("2.Example2");
        System.out.println("3.Example3");
        System.out.println("4.Example4");
        return scanner.nextInt();
    }

    @Override
    public void completeExecution() {
        controller.allStep();
    }
}

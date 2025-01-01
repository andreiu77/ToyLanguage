package view;

import view.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command cmd){
        commands.put(cmd.getKey(), cmd);
    }

    private void printMenu(){
        System.out.println("----MENU----");
        for(Command cmd : commands.values()){
            String line = String.format("%4s : %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Option is: ");
            String line = scanner.nextLine();
            Command command = commands.get(line);
            if(command == null){
                System.out.println("Unknown command");
                continue;
            }
            command.execute();
        }
    }
}

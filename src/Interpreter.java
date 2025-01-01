import controller.Controller;
import controller.IController;
import importprgs.Programs;
import model.adt.*;
import model.state.PrgState;
import model.statements.*;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.RunExampleCommand;

import java.io.BufferedReader;
import java.text.MessageFormat;
import java.util.List;

public class Interpreter {
    public static void main(String[] args) {
        List<IStatement> prgStates = Programs.getPrgStates();
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        MyIDictionary<String, IValue> symTable1 = new MyDictionary<>();
        MyIStack<IStatement> execStack1 = new MyStack<>();
        MyIList<String> output1 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        MyIHeap heap1 = new MyHeap();
        IStatement ex1 = prgStates.get(0);
        try{
            ex1.typeCheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(symTable1, execStack1, output1, ex1, fileTable1, heap1);
            IRepository repo1 = new Repository("log1.txt");
            repo1.addPrgState(prg1);
            IController ctr1 = new Controller(repo1, true);
            menu.addCommand(new RunExampleCommand("1", ex1.toString(), (Controller) ctr1));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex1.toString()));
        }


        MyIDictionary<String, IValue> symTable2 = new MyDictionary<>();
        MyIStack<IStatement> execStack2 = new MyStack<>();
        MyIList<String> output2 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        MyIHeap heap2 = new MyHeap();
        IStatement ex2 = prgStates.get(1);
        try{
            ex2.typeCheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(symTable2, execStack2, output2, ex2, fileTable2, heap2);
            IRepository repo2 = new Repository("log2.txt");
            repo2.addPrgState(prg2);
            IController ctr2 = new Controller(repo2, true);
            menu.addCommand(new RunExampleCommand("2", ex2.toString(), (Controller) ctr2));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex2.toString()));
        }

        MyIDictionary<String, IValue> symTable3 = new MyDictionary<>();
        MyIStack<IStatement> execStack3 = new MyStack<>();
        MyIList<String> output3 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        MyIHeap heap3 = new MyHeap();
        IStatement ex3 = prgStates.get(2);
        try{
            ex3.typeCheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(symTable3, execStack3, output3, ex3, fileTable3, heap3);
            IRepository repo3 = new Repository("log3.txt");
            repo3.addPrgState(prg3);
            IController ctr3 = new Controller(repo3, true);
            menu.addCommand(new RunExampleCommand("3", ex3.toString(), (Controller) ctr3));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex3.toString()));
        }

        MyIDictionary<String, IValue> symTable4 = new MyDictionary<>();
        MyIStack<IStatement> execStack4 = new MyStack<>();
        MyIList<String> output4 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        MyIHeap heap4 = new MyHeap();
        IStatement ex4 = prgStates.get(3);
        try{
            ex4.typeCheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(symTable4, execStack4, output4, ex4, fileTable4, heap4);
            IRepository repo4 = new Repository("log4.txt");
            repo4.addPrgState(prg4);
            IController ctr4 = new Controller(repo4, true);
            menu.addCommand(new RunExampleCommand("4", ex4.toString(), (Controller) ctr4));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex4.toString()));
        }

        MyIDictionary<String, IValue> symTable5 = new MyDictionary<>();
        MyIStack<IStatement> execStack5 = new MyStack<>();
        MyIList<String> output5 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        MyIHeap heap5 = new MyHeap();
        IStatement ex5 = prgStates.get(4);
        try{
            ex5.typeCheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(symTable5, execStack5, output5, ex5, fileTable5, heap5);
            IRepository repo5 = new Repository("log5.txt");
            repo5.addPrgState(prg5);
            IController ctr5 = new Controller(repo5, true);
            menu.addCommand(new RunExampleCommand("5", ex5.toString(), (Controller) ctr5));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex5.toString()));
        }

        MyIDictionary<String, IValue> symTable6 = new MyDictionary<>();
        MyIStack<IStatement> execStack6 = new MyStack<>();
        MyIList<String> output6 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        MyIHeap heap6 = new MyHeap();
        IStatement ex6 = prgStates.get(5);
        try{
            ex6.typeCheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(symTable6, execStack6, output6, ex6, fileTable6, heap6);
            IRepository repo6 = new Repository("log6.txt");
            repo6.addPrgState(prg6);
            IController ctr6 = new Controller(repo6, true);
            menu.addCommand(new RunExampleCommand("6", ex6.toString(), (Controller) ctr6));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex6.toString()));
        }

        MyIDictionary<String, IValue> symTable7 = new MyDictionary<>();
        MyIStack<IStatement> execStack7 = new MyStack<>();
        MyIList<String> output7 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable7 = new MyDictionary<>();
        MyIHeap heap7 = new MyHeap();
        IStatement ex7 = prgStates.get(6);
        try{
            ex7.typeCheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(symTable7, execStack7, output7, ex7, fileTable7, heap7);
            IRepository repo7 = new Repository("log7.txt");
            repo7.addPrgState(prg7);
            IController ctr7 = new Controller(repo7, true);
            menu.addCommand(new RunExampleCommand("7", ex7.toString(), (Controller) ctr7));
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex7.toString()));
        }

        MyIDictionary<String, IValue> symTable8 = new MyDictionary<>();
        MyIStack<IStatement> execStack8 = new MyStack<>();
        MyIList<String> output8 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>();
        MyIHeap heap8 = new MyHeap();
        IStatement ex8 = prgStates.get(7);
        try{
            ex8.typeCheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(symTable8, execStack8, output8, ex8, fileTable8, heap8);
            IRepository repo8 = new Repository("log8.txt");
            repo8.addPrgState(prg8);
            IController ctr8 = new Controller(repo8, true);
            menu.addCommand(new RunExampleCommand("8", ex8.toString(), (Controller) ctr8));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex8.toString()));
        }

        MyIDictionary<String, IValue> symTable9 = new MyDictionary<>();
        MyIStack<IStatement> execStack9 = new MyStack<>();
        MyIList<String> output9 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable9 = new MyDictionary<>();
        MyIHeap heap9 = new MyHeap();
        IStatement ex9 = prgStates.get(8);
        try{
            ex9.typeCheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(symTable9, execStack9, output9, ex9, fileTable9, heap9);
            IRepository repo9 = new Repository("log9.txt");
            repo9.addPrgState(prg9);
            IController ctr9 = new Controller(repo9, true);
            menu.addCommand(new RunExampleCommand("9", ex9.toString(), (Controller) ctr9));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex9.toString()));
        }

        MyIDictionary<String, IValue> symTable10 = new MyDictionary<>();
        MyIStack<IStatement> execStack10 = new MyStack<>();
        MyIList<String> output10 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable10 = new MyDictionary<>();
        MyIHeap heap10 = new MyHeap();
        IStatement ex10 = prgStates.get(9);
        try{
            ex10.typeCheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(symTable10, execStack10, output10, ex10, fileTable10, heap10);
            IRepository repo10 = new Repository("log10.txt");
            repo10.addPrgState(prg10);
            IController ctr10 = new Controller(repo10, true);
            menu.addCommand(new RunExampleCommand("10", ex10.toString(), (Controller) ctr10));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex10.toString()));
        }

        MyIDictionary<String, IValue> symTable11 = new MyDictionary<>();
        MyIStack<IStatement> execStack11 = new MyStack<>();
        MyIList<String> output11 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable11 = new MyDictionary<>();
        MyIHeap heap11 = new MyHeap();
        IStatement ex11 = prgStates.get(10);
        try {
            ex11.typeCheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(symTable11, execStack11, output11, ex11, fileTable11, heap11);
            IRepository repo11 = new Repository("log11.txt");
            repo11.addPrgState(prg11);
            IController ctr11 = new Controller(repo11, true);
            menu.addCommand(new RunExampleCommand("11", ex11.toString(), (Controller) ctr11));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(MessageFormat.format("Program {0} failed type checker so will not be available for execution", ex11.toString()));
        }

        menu.show();
    }


}

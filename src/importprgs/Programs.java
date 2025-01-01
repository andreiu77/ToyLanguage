package importprgs;

import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.List;

public class Programs {
    public static List<IStatement> getPrgStates() {
        IStatement exTypeCheckError = new CompStatement(new VarDeclStatement("vTYPECHECKERROR", new StringType()),
                new CompStatement(new AssignStatement("vTYPECHECKERROR", new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("vTYPECHECKERROR"))));

        IStatement ex1 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("v"))));

        IStatement ex2 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("b", new IntType()),
                        new CompStatement(new AssignStatement("a", new ArithmeticalExpression(new ValueExpression(new IntValue(2)), ArithmeticalOperator.ADD, new
                                ArithmeticalExpression(new ValueExpression(new IntValue(3)), ArithmeticalOperator.MULTIPLY, new ValueExpression(new IntValue(5))))),
                                new CompStatement(new AssignStatement("b", new ArithmeticalExpression(new VariableExpression("a"),
                                        ArithmeticalOperator.ADD, new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        IStatement ex3 = new CompStatement(new VarDeclStatement("a", new BoolType()),
                new CompStatement(new VarDeclStatement("v", new IntType()),
                        new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v",
                                        new ValueExpression(new IntValue(2))), new AssignStatement("v",
                                        new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        IStatement ex4 = new CompStatement(new VarDeclStatement("varf", new StringType()), new CompStatement(
                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))), new CompStatement(
                new OpenFileStatement(new VariableExpression("varf")), new CompStatement(new VarDeclStatement("varc", new IntType()),
                new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                        new PrintStatement(new VariableExpression("varc")), new CompStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompStatement(
                        new PrintStatement(new VariableExpression("varc")), new CloseFileStatement(new VariableExpression("varf"))
                ))
                ))
        ))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement ex5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocHeapStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocHeapStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticalExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), ArithmeticalOperator.ADD, new ValueExpression(new IntValue(5)))))))));

        //Ref int v;new(v,20);print(rH(v));wH(v,30);print(rH(v)+5);
        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), new CompStatement(
                                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))), new PrintStatement(new ArithmeticalExpression(new ReadHeapExpression(new VariableExpression("v")), ArithmeticalOperator.ADD, new ValueExpression(new IntValue(5))))
                        ))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new AllocHeapStatement("a", new VariableExpression("v")),
                                        new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

        //Ref int v;new(v,20);Ref Ref int a; Ref Ref Ref int b; new(a,v); new(b,a); new(v,30);print(rH(rH(a)))
        //extra test case to check the safe garbage collector implementation
        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new VarDeclStatement("b", new RefType(new RefType(new RefType(new IntType())))),
                                        new CompStatement(new AllocHeapStatement("a", new VariableExpression("v")),
                                                new CompStatement(new AllocHeapStatement("b", new VariableExpression("a")),
                                                        new CompStatement(new AllocHeapStatement("v", new ValueExpression(new IntValue(30))),
                                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))))));

        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), RelationalOperator.GREATER, new ValueExpression(new IntValue(0))),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticalExpression(new VariableExpression("v"), ArithmeticalOperator.SUBTRACT, new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        //int v; Ref int a; v=10;new(a,22);
        // fork(wH(a,30);v=32;print(v);print(rH(a)));
        // print(v);print(rH(a))
        IStatement ex11 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new AllocHeapStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(new CompStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));
        return List.of(exTypeCheckError, ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10, ex11);
    }
}

package controller;

import exceptions.ADTException;
import model.state.PrgState;

import java.util.List;

public interface IController {
    boolean oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException;
    void allStep();
}

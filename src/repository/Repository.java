package repository;

import exceptions.EmptyStatesList;
import exceptions.RepoException;
import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> listStates;
    private String fileName;
    private int currentIndex;

    public Repository(String filen) {
        listStates = new ArrayList<>();
        fileName = filen;
        currentIndex = 0;
    }

    @Override
    public List<PrgState> getListStates() {
        return listStates;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            logFile.println(state.toString());
            logFile.close();
        } catch (IOException e) {
            throw new RepoException("File does not exist");
        }

    }

    @Override
    public List<PrgState> getPrgList() {
        return this.listStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        listStates = prgList;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        listStates.add(prgState);
    }
}

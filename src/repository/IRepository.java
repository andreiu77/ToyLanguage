package repository;

import exceptions.RepoException;
import model.state.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void addPrgState(PrgState prgState);
    List<PrgState> getListStates();
    void logPrgStateExec(PrgState state) throws RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}

package controller;

import exceptions.ADTException;
import exceptions.ControllerException;
import exceptions.EmptyStackException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.Collection;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    IRepository repository;
    boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repository, boolean displayFlag) {
        this.repository = repository;
        this.displayFlag = displayFlag;
        this.executor = Executors.newFixedThreadPool(2);
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap){
        //unsafe
        //return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey()))
        //        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        //update the code above such that it won't filter the addresses which are not in symTableAddr,
        //but are indirectly referenced in the heap
        //safe
        return heap.entrySet().stream().filter(e -> symTableAddr.contains(e.getKey()) || isIndirectlyReferenced(e.getKey(), heap))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    boolean isIndirectlyReferenced(int key, Map<Integer, IValue> heap){
        return heap.values().stream().anyMatch(v -> v instanceof RefValue && ((RefValue)v).getAddress() == key ||
                (v instanceof RefValue && isIndirectlyReferenced(((RefValue) v).getAddress(), heap)));
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream().filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }

    public boolean oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException{
        prgList.forEach(prg -> prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getValues()), prg.getHeap().getHeap())));
        prgList.forEach(prg -> repository.logPrgStateExec(prg));
        prgList.forEach(prg -> System.out.println(prg.toString()));
        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) -> (Callable<PrgState>) (p::oneStep)).toList();
        List<PrgState> newPrgList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new ControllerException(e.getMessage());
            }
        }).filter(Objects::nonNull).toList();
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> repository.logPrgStateExec(prg));
        prgList.forEach(prg -> System.out.println(prg.toString()));
        repository.setPrgList(prgList);
        List<PrgState> prgListToRun = removeCompletedPrg(repository.getPrgList());
        if(prgListToRun.size() == 0){
            executor.shutdownNow();
            repository.setPrgList(prgList);
            return false;
        }
        return true;
    }

    @Override
    public void allStep(){
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while(prgList.size() > 0){
            try {
                oneStepForAllPrg(prgList);
            }
            catch(RuntimeException | InterruptedException e){
                throw new ControllerException(e.getMessage());
            }
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(state -> state.isNotComplete()).collect(Collectors.toList());
    }
}

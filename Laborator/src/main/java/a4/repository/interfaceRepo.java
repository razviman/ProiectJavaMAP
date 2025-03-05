package a4.repository;
import a4.domain.entity;

import java.util.ArrayList;

public abstract class interfaceRepo<T extends entity> {

    protected ArrayList<T> list;

    public interfaceRepo() {
        list = new ArrayList<>();
    }

    public abstract ArrayList<T> getList();

    public abstract boolean findById(int id);

    public abstract T getElementById(int id) throws RepoException;

    public abstract void Add(T element) throws RepoException;

    public abstract void removeById(int id) throws RepoException;

    public abstract void remove(T element) throws Exception;

    public abstract T getLast() throws Exception;


    public int size() {
        return list.size();
    }
}
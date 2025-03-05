package a4.repository;
import a4.domain.entity;
import java.util.ArrayList;

public class Repository <T extends entity> extends interfaceRepo<T>{


    @Override
    public ArrayList<T> getList() {
        return list;
    }

    @Override
    public boolean findById(int id) {
        if(!list.isEmpty()) {
            for (T element : list) {
                if(element.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T getElementById(int id) throws RepoException {
        if(!findById(id)) {
            throw new RepoException("ID inexistent !!");
        }
        for (T element : list) {
            if(element.getId() == id) {
                return element;
            }
        }
        return null;
    }
    @Override
    public void Add(T element) throws RepoException{
        if(!findById(element.getId())) {
            list.add(element);
        }
        else throw new RepoException("ID deja existent !!");
    }
    @Override
    public void removeById(int id) throws RepoException{
        if(!findById(id)) {
            throw new RepoException("ID inexistent !!");
        }
        list.remove(getElementById(id));

    }
    @Override
    public void remove(T element) throws Exception{
        if(!findById(element.getId())) {
            throw new RepoException("ID inexistent!!");
        }
        list.remove(element);
    }

    @Override
    public T getLast() throws Exception {
        return list.get(list.size()-1);
    }

    public void Update(int id, T element) throws RepoException {
        removeById(id);
        list.add(element);

    }
}
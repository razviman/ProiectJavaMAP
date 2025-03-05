package a4.repository;
import a4.domain.entity;
public abstract class AbstractFileRepo<T extends entity> extends Repository<T> {
    protected String fileName;

    public AbstractFileRepo(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void Add(T elem) throws RepoException {
        super.Add(elem);
        SaveToFile();
    }

    @Override
    public void remove(T elem) throws Exception {
        super.remove(elem);
        SaveToFile();
    }

    @Override
    public void removeById(int id) throws RepoException {
        super.removeById(id);
        SaveToFile();
    }


    protected abstract void SaveToFile() throws RepoException;

    protected abstract void LoadFromFile() throws RepoException;




}

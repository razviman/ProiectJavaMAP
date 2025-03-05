package a4.domain;

public class converterTort extends converter<tort>{
    @Override
    public String toString(tort elem) {

        return elem.getId() + ";" + elem.getTip();
    }

    @Override
    public tort toEntity(String elem) {
        String[] tokens = elem.split(";");
        String tip = tokens[1];
        return new tort(tip);
    }
}

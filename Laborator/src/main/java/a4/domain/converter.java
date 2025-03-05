package a4.domain;

public abstract class converter<T extends entity> {


    public abstract String toString(T elem) ;

    public abstract T toEntity(String elem) ;

}

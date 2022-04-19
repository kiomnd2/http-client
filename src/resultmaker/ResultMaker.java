package resultmaker;

public interface ResultMaker<T> {
    T parseResult(String param);
}

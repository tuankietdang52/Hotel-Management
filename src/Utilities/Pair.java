package Utilities;

public class Pair<T1, T2> {
    private T1 first;
    private T2 last;

    public Pair(T1 first, T2 last){
        this.first = first;
        this.last = last;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getLast() {
        return last;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public void setLast(T2 last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "[" + first + ", " + last + "]";
    }
}

package net.nokok.either4j;

public class Main {
    public void doSomething() {
        Either.right(1); //Either<Object, Integer>
        Either.<String, Integer>right(1); // Either<String, Integer>
    }
}

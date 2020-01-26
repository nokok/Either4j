# Either4j

## Examples

```java
Either.right(1); //Either<Object, Integer>
Either.<String, Integer>.right(1); // Either<String, Integer>
```

```java
Either.left("ERROR"); // Either<String, Object>
Either.<String, Integer>.left("ERROR"); // Either<String, Integer>
```

## trying

```java
Either<Exception, Integer> e = Either.trying(() -> Integer.parseInt("abc"));
// Either.Left(java.lang.NumberFormatException: For input string : "abc")
```

## cond

```java
Either.cond(Character.isDigit('1'), 1, "not digit"); // Either.Right(1)
```

## map/leftMap

```java
Either.right(1).map(i -> i + 1); // Either.Right(2)
```

```java
Either.left("Hello").leftMap(s -> s + " Error"); // Either.Left(Hello Error)
```

## match

```java
Integer a = Either
            .trying(() -> Integer.parseInt("a"))
            .match(
                    i -> i + 2, // Right
                    e -> 2 // Left
            );

```

## getRight/getLeft

```java
Either<Object, Integer> e = Either.right(1);
e.getRight(); //1
e.getLeft(); // NoSuchElementException
```

## recover

```java
Either.left("ERROR").recover(s -> 123); // Either.Right(123)
```

## toOptional

```java
Either.right(1).toOptional(); // Optional[1]
Either.left(1).toOptional(); // Optional.empty
```

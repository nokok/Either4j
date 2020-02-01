package net.nokok.either4j;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Either<L, R> {

    public static <L, R> Either<L, R> left(L left) {
        return new Left(left);
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Right(right);
    }

    public static <R> Either<Exception, R> trying(Supplier<R> r) {
        try {
            return Either.right(r.get());
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    public static <L, R> Either<L, R> cond(boolean cond, R right, L left) {
        if (cond) {
            return Either.right(right);
        } else {
            return Either.left(left);
        }
    }

    public static <L, R> Either<L, R> cond(Supplier<Boolean> cond, R right, L left) {
        return Either.cond(cond.get(), right, left);
    }

    public boolean isRight();

    public default boolean isLeft() {
        return !isRight();
    }

    public R getRight();

    public L getLeft();

    default Optional<R> toOptional() {
        if (this.isRight()) {
            return Optional.of(this.getRight());
        } else {
            return Optional.empty();
        }
    }

    default <T> T match(Function<R, T> r, Function<L, T> l) {
        if (this.isRight()) {
            return r.apply(this.getRight());
        } else {
            return l.apply(this.getLeft());
        }
    }

    default <RR> Either<L, RR> map(Function<R, RR> f) {
        if (this.isRight()) {
            return Either.right(f.apply(this.getRight()));
        } else {
            return Either.left(this.getLeft());
        }
    }

    default <RR extends R> Either<L, RR> flatMap(Function<R, Either<L, RR>> f) {
        if (this.isRight()) {
            return f.apply(this.getRight());
        } else {
            return Either.left(this.getLeft());
        }
    }

    default <LL> Either<LL, R> leftMap(Function<L, LL> f) {
        if (this.isLeft()) {
            return Either.left(f.apply(this.getLeft()));
        } else {
            return Either.right(this.getRight());
        }
    }

    default Either<L, R> recover(Function<L, R> f) {
        if (this.isLeft()) {
            return Either.right(f.apply(this.getLeft()));
        } else {
            return this;
        }
    }

    public static class Left<L, R> implements Either<L, R> {
        private final L value;

        private Left(L value) {
            Objects.requireNonNull(value);
            this.value = value;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public R getRight() {
            throw new NoSuchElementException();
        }

        @Override
        public L getLeft() {
            return value;
        }

        @Override
        public String toString() {
            return "Either.Left(" + value + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Left<?, ?> left = (Left<?, ?>) o;
            return value.equals(left.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class Right<L, R> implements Either<L, R> {
        private final R value;

        private Right(R value) {
            Objects.requireNonNull(value);
            this.value = value;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public R getRight() {
            return this.value;
        }

        @Override
        public L getLeft() {
            throw new NoSuchElementException();
        }

        @Override
        public String toString() {
            return "Either.Right(" + value + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Right<?, ?> right = (Right<?, ?>) o;
            return value.equals(right.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}

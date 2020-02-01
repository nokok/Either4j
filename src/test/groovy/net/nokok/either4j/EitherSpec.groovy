package net.nokok.either4j

import spock.lang.Specification

class EitherSpec extends Specification {
    def "Either#trying"() {
        def r = Either.trying({ -> Integer.parseInt("1") })
        def l = Either.trying({ -> Integer.parseInt("a") })

        expect:
        r.isRight()
        l.isLeft()
    }

    def "Either#cond"() {
        expect:
        Either.cond(condition, 1, 2) == expected

        where:
        condition || expected
        true      || Either.right(1)
        false     || Either.left(2);

    }

    def "Either#cond(Supplier)"() {
        expect:
        Either.cond({ -> true }, 1, 2) == Either.right(1)
        Either.cond({ -> false }, 1, 2) == Either.left(2)
    }

    def "Right#isRight"() {
        expect:
        Either.right(1).isRight()
    }

    def "Right#getRight"() {
        expect:
        Either.right(1).getRight() == 1
    }

    def "Right#getLeft"() {
        when:
        Either.right(1).getLeft()

        then:
        thrown(NoSuchElementException)
    }

    def "Left#getRight"() {
        when:
        Either.left(2).getRight()

        then:
        thrown(NoSuchElementException)
    }

    def "Either#map (Right)"() {
        def e = Either.right(1)
        def result = e.map({ i -> i + "2" })

        expect:
        result == Either.right("12")
    }

    def "Either#map (Left)"() {
        def e = Either.left(2)
        def result = e.map({ i -> i + "2" })

        expect:
        result == Either.left(2)
    }

    def "Either#flatMap (Right)"() {
        def e = Either.right(1)
        def result = e.flatMap({ _ -> Either.right(2) })

        expect:
        result == Either.right(2)
    }

    def "Either#flatMap (Left)"() {
        def e = Either.left(1)
        def result = e.flatMap({ _ -> Either.right(2) })

        expect:
        result == Either.left(1)
    }

    def "Either#leftMap (Right)"() {
        def e = Either.right(1)
        def result = e.leftMap({ _ -> 2 })

        expect:
        result == Either.right(1)
    }

    def "Either#leftMap (Left)"() {
        def e = Either.left(1)
        def result = e.leftMap({ _ -> 2 })

        expect:
        result == Either.left(2)
    }

    def "Either#recover (Right)"() {
        def e = Either.right(1)
        def result = e.recover({ _ -> 2 })

        expect:
        result == Either.right(1)
    }

    def "Either#recover (Left)"() {
        def e = Either.left(1)
        def result = e.recover({ _ -> 2 })

        expect:
        result == Either.right(2)
    }

    def "Either#match (Right)"() {
        Either<Integer, Integer> e = Either.right(1)

        expect:
        e.match(
                { r -> "Right ${r}" },
                { r -> "Left ${r}" }
        ) == "Right 1"
    }

    def "Either#match (Left)"() {
        Either<Integer, Integer> e = Either.left(1)

        expect:
        e.match(
                { r -> "Right ${r}" },
                { r -> "Left ${r}" }
        ) == "Left 1"
    }

    def "Right#toOptional"() {
        expect:
        Either.right(1).toOptional() == Optional.of(1)
    }

    def "Left#toOptional"() {
        expect:
        Either.left("Error").toOptional() == Optional.empty()
    }
}

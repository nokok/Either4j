package net.nokok.either4j

import spock.lang.Specification

class EitherSpec extends Specification {
    Either<?, Integer> right = Either.right(1)

    def "Either#trying"() {
        def r = Either.trying({ -> Integer.parseInt("1") })
        def l = Either.trying({ -> Integer.parseInt("a") })

        expect:
        r.isRight()
        l.isLeft()
    }

    def "Right#isRight"() {
        expect:
        right.isRight()
    }

    def "Right#getRight"() {
        expect:
        right.getRight() == 1
    }

    def "Right#getLeft"() {
        when:
        right.getLeft()

        then:
        thrown(NoSuchElementException)
    }

    def "Either#map"() {
        Either<String, Integer> e = Either.right(1)
        Either<String, String> result = e.map({ i -> i + "2" })

        String r = result.match(
                { s -> s },
                { s -> s }
        )

        expect:
        r == "12"
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

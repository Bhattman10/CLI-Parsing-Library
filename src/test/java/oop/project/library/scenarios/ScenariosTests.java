package oop.project.library.scenarios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

class ScenariosTests {

    @ParameterizedTest
    @MethodSource
    public void testLex(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testLex() {
        return Stream.of(
            Arguments.of("Literal", """
                lex literal
                """, Map.of("0", "literal")),
            Arguments.of("Integer", """
                lex 1
                """, Map.of("0", "1")),
            Arguments.of("Negative Decimal", """
                lex -2.0
                """, Map.of("0", "-2.0")),
            Arguments.of("Flag", """
                lex --flag value
                """, Map.of("flag", "value")),
            Arguments.of("Multiple Positional Arguments", """
                lex 0 1 2
                """, Map.of("0", "0", "1", "1", "2", "2")),
            Arguments.of("Positional Argument, Named Argument", """
                lex literal --flag value
                """, Map.of("0", "literal", "flag", "value")),
            Arguments.of("Named Argument, Positional Argument", """
                lex --flag value literal
                """, Map.of("flag", "value", "0", "literal")),
            Arguments.of("Flag Without Value", """
                lex --flag
                """, null),
            Arguments.of("Three Hyphens", """
                lex ---flag
                """, null),
            Arguments.of("Flag as a Value", """
                lex --invalid --argument
                """, null),
            Arguments.of("Missing Positional Argument", """
                lex
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testAdd(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testAdd() {
        return Stream.of(
            Arguments.of("Positives", """
                add 1 2
                """, Map.of("left", 1, "right", 2)),
            Arguments.of("Negatives", """
                add -1 -2
                """, Map.of("left", -1, "right", -2)),
            Arguments.of("Zeros", """
                add 0 0
                """, Map.of("left", 0, "right", 0)),
            Arguments.of("Non-Numeric Left", """
                add one 2
                """, null),
            Arguments.of("Non-Numeric Right", """
                add 1 two
                """, null),
            Arguments.of("Non-Integer Left", """
                add 1.0 2
                """, null),
            Arguments.of("Non-Integer Right", """
                add 1 2.0
                """, null),
            Arguments.of("Missing Argument", """
                add 1
                """, null),
            Arguments.of("Extraneous Argument", """
                add 1 2 3
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testSub(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testSub() {
        return Stream.of(
            Arguments.of("Positives", """
                sub --left 1 --right 2.0
                """, Map.of("left", 1.0, "right", 2.0)),
            Arguments.of("Negative Integer", """
                sub --left -1 --right 2.0
                """, Map.of("left", -1.0, "right", 2.0)),
            Arguments.of("Negative Decimal", """
                sub --left 1 --right -2.0
                """, Map.of("left", 1.0, "right", -2.0)),
            Arguments.of("Missing Argument", """
                sub --left 1
                """, null),
            Arguments.of("Incorrect Type", """
                sub --left 1 --right hello
                """, null),
            Arguments.of("Invalid Flag Name", """
                sub --flag 1 --right 2
                """, null),
            Arguments.of("Too Many Arguments", """
                sub --left 1 --right 2 --extra 3
                """, null),
            Arguments.of("Positional Arguments", """
                sub 1 2
                """, null),
            Arguments.of("Positional", """
                sub left 2 right 1
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testFizzbuzz(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testFizzbuzz() {
        return Stream.of(
            Arguments.of("Valid", """
                fizzbuzz 15
                """, Map.of("number", 15)),
            Arguments.of("One", """
                fizzbuzz 1
                """, Map.of("number", 1)),
            Arguments.of("Hundred", """
                fizzbuzz 100
                """, Map.of("number", 100)),
            Arguments.of("Zero", """
                fizzbuzz 0
                """, null),
            Arguments.of("Named Argument", """
                fizzbuzz -number 22
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testDifficulty(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testDifficulty() {
        return Stream.of(
            Arguments.of("Easy", """
                difficulty easy
                """, Map.of("difficulty", "easy")),
            Arguments.of("Normal", """
                difficulty normal
                """, Map.of("difficulty", "normal")),
            Arguments.of("Hard", """
                difficulty hard
                """, Map.of("difficulty", "hard")),
            Arguments.of("Peaceful", """
                difficulty peaceful
                """, Map.of("difficulty", "peaceful")),
            Arguments.of("Hardcore", """
                difficulty hardcore
                """, null),
            Arguments.of("Named Difficulty", """
                difficulty --difficulty easy
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testEcho(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testEcho() {
        return Stream.of(
//            Arguments.of("Default", """
//                echo
//                """, Map.of("message", "Echo, echo, echo...")),
            Arguments.of("Message", """
                echo message
                """, Map.of("message", "message")),
            Arguments.of("Multiple Arguments", """
                echo 1 2 3
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testSearch(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testSearch() {
        return Stream.of(
            Arguments.of("Lowercase", """
                search apple
                """, Map.of("term", "apple", "case-insensitive", false)),
            Arguments.of("Case Insensitive", """
                search ApPlE --case-insensitive true
                """, Map.of("term", "ApPlE", "case-insensitive", true)),
            Arguments.of("Extraneous Argument", """
                search apple pie
                """, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void testWeekday(String name, String command, Map<String, Object> expected) {
        test(command, expected);
    }

    private static Stream<Arguments> testWeekday() {
        return Stream.of(
            Arguments.of("Valid", """
                weekday 2024-10-23
                """, Map.of("date", LocalDate.of(2024, 10, 23))),
            Arguments.of("Invalid Month", """
                weekday 2024-23-10
                """, null),
            Arguments.of("Leap Year", """
                weekday 2024-02-29
                """, Map.of("date", LocalDate.of(2024, 2, 29))),
            Arguments.of("Invalid", """
                weekday tuesday
                """, null),
            Arguments.of("Flag", """
                weekday --flag 2024-10-23
                """, null)
        );
    }

    private static void test(String command, Map<String, Object> expected) {
        var result = Scenarios.parse(command.stripTrailing()); //trailing newline
        if (expected != null) {
            Assertions.assertEquals(new Result.Success<>(expected), result);
        } else {
            Assertions.assertInstanceOf(Result.Failure.class, result, "Expected a failed parse but received " + result + ".");
        }
    }

}

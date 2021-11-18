package com.geekbrains.homeWork3;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticUtilsTest {

    @BeforeAll
    static void beforeAll() {

    }

    public static Stream<Arguments> paramsSum() {
        return Stream.of(
                Arguments.of(1, 3, 4),
                Arguments.of(5, 5, 10),
                Arguments.of(5, -8, -3),
                Arguments.of(9, -6, 3)
        );
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Тест вычисления суммы")
    @ParameterizedTest(name = "{index}) {0} + {1} = {2}")
    @MethodSource("paramsSum")
    void sum(int x, int y, int expected) {
        int actually = ArithmeticUtils.sum(x, y);
        Assertions.assertEquals(expected, actually);
    }
}
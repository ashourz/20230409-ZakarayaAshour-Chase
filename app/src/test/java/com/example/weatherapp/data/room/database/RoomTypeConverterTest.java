package com.example.weatherapp.data.room.database;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.Month;

@RunWith(JUnit4.class)
public class RoomTypeConverterTest {
    @Test
    public void testFromLong() {
        LocalDateTime expected = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0, 0);
        Long input = 1640995200L; // Represents January 1, 2022 at midnight in Unix time

        LocalDateTime actual = RoomTypeConverter.fromLong(input);

        assertEquals(expected, actual);
    }

    @Test
    public void testFromLongWithNullInput() {
        assertNull(RoomTypeConverter.fromLong(null));
    }

    @Test
    public void testFromLocalDateTime() {
        Long expected = 1640995200L; // Represents January 1, 2022 at midnight in Unix time
        LocalDateTime input = LocalDateTime.of(2022, Month.JANUARY, 1, 0, 0, 0);

        Long actual = RoomTypeConverter.fromLocalDateTime(input);

        assertEquals(expected, actual);
    }

    @Test
    public void testFromLocalDateTimeWithNullInput() {
        assertNull(RoomTypeConverter.fromLocalDateTime(null));
    }
}
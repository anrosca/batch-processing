package com.endava.batch.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactorialServiceTest {

    @Test
    public void test() {
        FactorialService service = new FactorialService();
        assertEquals(1, service.calculateFor(0));
        assertEquals(1, service.calculateFor(1));
        assertEquals(2, service.calculateFor(2));
        assertEquals(6, service.calculateFor(3));
        assertEquals(24, service.calculateFor(4));
        assertEquals(120, service.calculateFor(5));
    }
}
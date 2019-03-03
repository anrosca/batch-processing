package com.endava.batch.service;

import org.springframework.stereotype.Service;

@Service
public class FactorialService {

    public long calculateFor(int value) {
        if (value < 2)
            return 1;
        long result = 1;
        for (int i = 1; i <= value; ++i)
            result *= i;
        return result;
    }
}

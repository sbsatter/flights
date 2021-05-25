package com.sbsatter.utils;

import java.time.LocalDateTime;

public class DatePair {
    public LocalDateTime start;
    public LocalDateTime end;
    
    public DatePair(LocalDateTime e, LocalDateTime f) {
        start = e;
        end = f;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}

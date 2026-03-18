package com.example.sportradarbe;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.example.sportradarbe.exception.GlobalExceptionHandlerTest;
import com.example.sportradarbe.service.EventServiceTest;

@Suite
@SelectClasses({
        EventServiceTest.class,
        GlobalExceptionHandlerTest.class
})
class TestsSuite {
}

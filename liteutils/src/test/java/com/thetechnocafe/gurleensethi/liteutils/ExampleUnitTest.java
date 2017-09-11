package com.thetechnocafe.gurleensethi.liteutils;

import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testValidator() {
        Validator validator = new Validator("abcdokokok-saruseth234324xyz");
        validator.atLeastOneSpecialCharacter()
                .maximumLength(14)
                .startsWith("abcd")
                .endsWith("xyz")
                .doesNotContains("sarusethi")
                .addErrorCallback(new Function1<ValidationError, Unit>() {
                    @Override
                    public Unit invoke(ValidationError validationError) {
                        System.out.println("Error " + validationError.toString());
                        return null;
                    }
                })
                .addSuccessCallback(new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        System.out.println("Success");
                        return null;
                    }
                })
                .validate();
    }
}
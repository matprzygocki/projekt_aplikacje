package org.example;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestStringCalc {
    @Test
    public void testFunction () {
        Assertions.assertThat(Main.add(""));
    }



}

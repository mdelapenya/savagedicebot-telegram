package com.github.mdelapenya.savagedicebot.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RPGDiceTest {

    public static class WithSingleRoll {

        @Test
        public void shouldFailCreation() {
            RPGDice d = RPGDice.parse("1d6d");
            Assert.assertNull(d);
        }

        @Test
        public void shouldFailCreationWhenAdding() {
            RPGDice d = RPGDice.parse("1d6+d");
            Assert.assertNull(d);
        }

        @Test
        public void shouldCreateDice() {
            RPGDice d = RPGDice.parse("1d6");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertFalse(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

        @Test
        public void shouldCreateDiceExplodingWithSavage() {
            RPGDice d = RPGDice.parse("1d6es");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertTrue(d.isExplodes());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
        }

        @Test
        public void shouldCreateDiceWithSavage() {
            RPGDice d = RPGDice.parse("1d6s");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertFalse(d.isExplodes());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
        }

        @Test
        public void shouldFailCreationWithoutRoll() {
            RPGDice d = RPGDice.parse("d6d");
            Assert.assertNull(d);
        }

        @Test
        public void shouldCreateDiceWithoutRoll() {
            RPGDice d = RPGDice.parse("d6");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertFalse(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

    }

    public static class WithMultipleRoll {

        @Test
        public void shouldFailCreation() {
            RPGDice d = RPGDice.parse("33d8d");
            Assert.assertNull(d);
        }

        @Test
        public void shouldCreateDice() {
            RPGDice d = RPGDice.parse("33d8");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertFalse(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

        @Test
        public void shouldCreateDiceExploding() {
            RPGDice d = RPGDice.parse("33d8e");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertTrue(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

        @Test
        public void shouldCreateDiceExplodingWithSavage() {
            RPGDice d = RPGDice.parse("33d8es");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertTrue(d.isExplodes());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
        }

        @Test
        public void shouldCreateDiceWithSavage() {
            RPGDice d = RPGDice.parse("33d8s");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertFalse(d.isExplodes());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
        }

        @Test
        public void shouldFailCreationWithAddition() {
            RPGDice d = RPGDice.parse("33d8d+28");
            Assert.assertNull(d);
        }

        @Test
        public void shouldFailCreationWhenAdding() {
            RPGDice d = RPGDice.parse("33d8+rwd");
            Assert.assertNull(d);
        }

        @Test
        public void shouldCreateDiceWithAddition() {
            RPGDice d = RPGDice.parse("33d8+28");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertEquals(d.getAdditive(), 28);
            Assert.assertFalse(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

        @Test
        public void shouldCreateDiceExplodingWithAddition() {
            RPGDice d = RPGDice.parse("33d8e+28");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertEquals(d.getAdditive(), 28);
            Assert.assertTrue(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
        }

    }

}

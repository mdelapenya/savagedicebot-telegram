package com.github.mdelapenya.savagedicebot.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class RPGDiceTest {

    public static class WhenValidatingFormula {

        @Test
        public void shouldReturnFormula() {
            RPGDice d = RPGDice.parse("1d6");
            Assert.assertNotNull(d);
            Assert.assertEquals("+1d6", d.getFormula());
        }

        @Test
        public void shouldReturnFormulaWithNegativeSign() {
            RPGDice d = RPGDice.parse("-1d6");
            Assert.assertNotNull(d);
            Assert.assertEquals("-1d6", d.getFormula());
        }

        @Test
        public void shouldReturnFormulaWithAdditionPositive() {
            RPGDice d = RPGDice.parse("d6+5");
            Assert.assertNotNull(d);
            Assert.assertEquals("+1d6+5", d.getFormula());
        }

        @Test
        public void shouldReturnFormulaWithAdditionNegative() {
            RPGDice d = RPGDice.parse("d6-5");
            Assert.assertNotNull(d);
            Assert.assertEquals("+1d6-5", d.getFormula());
        }

        @Test
        public void shouldReturnFormulaWithoutNumberOfDices() {
            RPGDice d = RPGDice.parse("d6");
            Assert.assertNotNull(d);
            Assert.assertEquals("+1d6", d.getFormula());
        }

    }

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
            Assert.assertTrue(d.isPositive());
        }

        @Test
        public void shouldCreateDiceWithNegativeSign() {
            RPGDice d = RPGDice.parse("-1d6");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertFalse(d.isExplodes());
            Assert.assertNull(d.getSavageDice());
            Assert.assertFalse(d.isPositive());
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
        public void shouldCreateDiceWithNegativeSignSavage() {
            RPGDice d = RPGDice.parse("-1d6s");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 6);
            Assert.assertEquals(d.getRolls(), 1);
            Assert.assertFalse(d.isExplodes());
            Assert.assertFalse(d.isPositive());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
            Assert.assertTrue(savage.isPositive());
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

        @Test
        public void shouldPrintDiceRoll() {
            RPGDice d = RPGDice.parse("d6");

            Assert.assertNotNull(d);

            DiceRoll roll = d.getDiceResult();
            Assert.assertTrue(roll.getDetail().startsWith("+1d6 ("));
            Assert.assertFalse(roll.getDetail().contains(" + "));
            Assert.assertTrue(roll.getDetail().contains(") = "));
        }

        @Test
        public void shouldPrintDiceRollWithSavage() {
            RPGDice d = RPGDice.parse("d6s");

            Assert.assertNotNull(d);

            DiceRoll roll = d.getDiceResult();
            Assert.assertTrue(roll.getDetail().startsWith("+1d6 (["));
            Assert.assertFalse(roll.getDetail().contains(" + "));
            Assert.assertTrue(roll.getDetail().contains(" / savage(+1d6): "));
            Assert.assertTrue(roll.getDetail().contains("]) = "));
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
        public void shouldCreateDiceExplodingWithNegativeSignAndSavage() {
            RPGDice d = RPGDice.parse("-33d8es");

            Assert.assertNotNull(d);
            Assert.assertEquals(d.getFaces(), 8);
            Assert.assertEquals(d.getRolls(), 33);
            Assert.assertTrue(d.isExplodes());
            Assert.assertFalse(d.isPositive());

            RPGDice savage = d.getSavageDice();
            Assert.assertNotNull(savage);
            Assert.assertEquals(savage.getFaces(), 6);
            Assert.assertEquals(savage.getRolls(), 1);
            Assert.assertTrue(savage.isExplodes());
            Assert.assertTrue(savage.isPositive());
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

        @Test
        public void shouldPrintDiceRoll() {
            RPGDice d = RPGDice.parse("2d6");

            Assert.assertNotNull(d);

            DiceRoll roll = d.getDiceResult();
            Assert.assertTrue(roll.getDetail().startsWith("+2d6 ("));
            Assert.assertTrue(roll.getDetail().contains(" + "));
            Assert.assertTrue(roll.getDetail().contains(") = "));
        }

        @Test
        public void shouldPrintDiceRollWithSavage() {
            RPGDice d = RPGDice.parse("2d6s");

            Assert.assertNotNull(d);

            DiceRoll roll = d.getDiceResult();
            Assert.assertTrue(roll.getDetail().startsWith("+2d6 (["));
            Assert.assertTrue(roll.getDetail().contains(" + "));
            Assert.assertTrue(roll.getDetail().contains(" / savage(+1d6): "));
            Assert.assertTrue(roll.getDetail().contains("]) = "));
        }

    }

}

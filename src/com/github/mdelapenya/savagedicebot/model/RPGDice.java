package com.github.mdelapenya.savagedicebot.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGDice {

    public static final int DEFAULT_SAVAGE_FACES = 6;

    /**
     * SIGN: determines if the dice is positive or negative. Default "true" (positive).
     *  Accepted values: [+-] (i.e. +1d4, -1d8)
     * SAV: determines if the dice is savage. Default "true".
     *  Accepted values: [!] (i.e. !1d4, 1d8)
     * A: determines the number of rolls for a dice. Default value "1".
     *  Accepted values: [integer] (i.e. 1d4, 3d8)
     * B: determines the number of faces of a dice. Default value "1".
     *  Accepted values: [integer] (i.e. 1d4, 1d8)
     * EXP: determines if the dice explodes when it reaches the highest value (6 on d6, 8 on d8). Default value "false".
     *  Accepted values: [e] (i.e. 1d4, 1d4e)
     * ADD: determines if there is any addition to the roll. Default value "empty" (+0).
     *  Accepted values: [+-][integer] (i.e. 1d8+4, 2d4-2)
     */
    private static final Pattern DICE_PATTERN = Pattern.compile(
        "(?>(?<SIGN>[+-]))?(?>(?<SAV>!))?(?<A>\\d*)d(?<B>\\d+)(?>(?<EXP>e))?(?>(?<ADD>[+-])(?<C>\\d+))?");

    private int rolls;
    private int faces;
    private int additive;
    private boolean explodes;
    private boolean positive;
    private RPGDice savageDice;

    private RPGDice(int rolls, int faces, int additive, boolean explodes, boolean savage, int savageFaces, boolean positive) {
        this.rolls = rolls;
        this.faces = faces;
        this.additive = additive;
        this.explodes = explodes;
        this.positive = positive;
        if (savage) {
            this.savageDice = new RPGDice(1, savageFaces, 0, true, false, 0, true);
        }
    }

    public int getRolls() {
        return rolls;
    }

    public int getFaces() {
        return faces;
    }

    public String getFormula() {
        String formula = "+";

        if (!isPositive()) {
            formula = "-";
        }

        formula += getRolls() + "d" + getFaces();

        if (getAdditive() > 0) {
            formula += "+" + getAdditive();
        } else if (getAdditive() < 0) {
            formula += getAdditive();
        }

        return formula;
    }

    public boolean hasSavageDice() {
        return savageDice != null;
    }

    public boolean isExplodes() {
        return explodes;
    }

    public boolean isPositive() {
        return positive;
    }

    public int getAdditive() {
        return additive;
    }

    public RPGDice getSavageDice() {
        return savageDice;
    }

    @Override
    public String toString() {
        return String.format("{\"rolls\": %s, \"faces\": %s, \"explodes\": %b, \"additive\": %s, \"savage\": %o}", rolls, faces, explodes, additive, savageDice);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private static Boolean getBoolean(Matcher matcher, String group, String expected) {
        String groupValue = matcher.group(group);
        return !isEmpty(groupValue) && groupValue.equalsIgnoreCase(expected);
    }

    private static Boolean getBooleanOrNull(Matcher matcher, String group, String expected) {
        String groupValue = matcher.group(group);
        return isEmpty(groupValue) || groupValue.equalsIgnoreCase(expected);
    }

    private static Integer getInt(Matcher matcher, String group, int defaultValue) {
        String groupValue = matcher.group(group);
        return isEmpty(groupValue) ? defaultValue : Integer.parseInt(groupValue);
    }

    private static Integer getSign(Matcher matcher, String group, String positiveValue) {
        String groupValue = matcher.group(group);
        return isEmpty(groupValue) || groupValue.equals(positiveValue) ? 1 : -1;
    }

    public DiceRoll getDiceResult() {
        StringBuilder text = new StringBuilder(getFormula());
        int rollResult = 0;

        text.append(" (");
        for (int i = 0; i < this.rolls; i++) {
            int roll = rollDice();

            if (!this.positive) {
                roll *= -1;
            }

            if(hasSavageDice()) {
                int savageRoll = savageDice.getDiceResult().getResult();

                text.append("[");
                text.append(this.getFormula());
                text.append(": ");
                text.append(roll);
                text.append(" / savage(");
                text.append(savageDice.getFormula());
                text.append("): ");
                text.append(savageRoll);
                text.append("]");

                if(savageRoll > roll) {
                    roll = savageRoll;
                }
            } else {
                text.append(roll);
            }

            if(i < this.rolls - 1) {
                text.append(" + ");
            }

            rollResult += roll;
        }

        text.append(")");
        text.append(" = ");
        text.append(rollResult);

        return new DiceRoll(text.toString(), rollResult);
    }

    public static RPGDice parse(String formula) {
        return parse(formula, DEFAULT_SAVAGE_FACES);
    }

    public static RPGDice parse(String formula, int savageFaces) {
        Matcher matcher = DICE_PATTERN.matcher(formula);
        if(matcher.matches()) {
            int rolls = getInt(matcher, "A", 1);
            int faces = getInt(matcher, "B", -1);
            int additive = getInt(matcher, "C", 0);
            int additiveSign = getSign(matcher, "ADD", "+");
            boolean explodes = getBoolean(matcher, "EXP", "e");
            boolean savage = !getBoolean(matcher, "SAV", "!");
            boolean positive = getBooleanOrNull(matcher, "SIGN", "+");

            return new RPGDice(rolls, faces, additive * additiveSign, explodes, savage, savageFaces, positive);
        }
        return null;
        // OR
        // throw new IllegalArgumentException("Invalid Expression");
    }

    private int rollDice() {
        int sum = 0;
        int roll;

        do {
            roll = (int)Math.floor((Math.random() * this.faces) + 1);
            sum += roll;
        } while(this.explodes && roll == this.faces);

        return sum;
    }

}
package com.github.mdelapenya.savagedicebot.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGDice {

    private static final Pattern DICE_PATTERN = Pattern.compile(
        "(?>(?<SIGN>[+-]))?(?<A>\\d*)d(?<B>\\d+)(?>(?<EXP>e))?(?>(?<SAV>s))?(?>(?<ADD>[+-])(?<C>\\d+))?");

    private int rolls;
    private int faces;
    private int additive;
    private boolean explodes;
    private boolean positive;
    private RPGDice savageDice;

    private RPGDice(int rolls, int faces, int additive, boolean explodes, boolean savage, boolean positive) {
        this.rolls = rolls;
        this.faces = faces;
        this.additive = additive;
        this.explodes = explodes;
        this.positive = positive;
        if (savage) {
            this.savageDice = new RPGDice(1, 6, 0, true, false, true);
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
        Matcher matcher = DICE_PATTERN.matcher(formula);
        if(matcher.matches()) {
            int rolls = getInt(matcher, "A", 1);
            int faces = getInt(matcher, "B", -1);
            int additive = getInt(matcher, "C", 0);
            int additiveSign = getSign(matcher, "ADD", "+");
            boolean explodes = getBoolean(matcher, "EXP", "e");
            boolean savage = getBoolean(matcher, "SAV", "s");
            boolean positive = getBooleanOrNull(matcher, "SIGN", "+");

            return new RPGDice(rolls, faces, additive * additiveSign, explodes, savage, positive);
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
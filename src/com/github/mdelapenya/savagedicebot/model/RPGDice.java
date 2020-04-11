package com.github.mdelapenya.savagedicebot.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGDice {

    private static final Pattern DICE_PATTERN = Pattern.compile(
        "(?<A>\\d*)d(?<B>\\d+)(?>(?<EXP>e))?(?>(?<SAV>s))?(?>(?<ADD>[+-])(?<C>\\d+))?");

    private int rolls;
    private int faces;
    private int additive;
    private boolean explodes;
    private RPGDice savageDice;

    private RPGDice(int rolls, int faces, int additive, boolean explodes, boolean savage) {
        this.rolls = rolls;
        this.faces = faces;
        this.additive = additive;
        this.explodes = explodes;
        if (savage) {
            this.savageDice = new RPGDice(1, 6, 1, true, false);
        }
    }

    public int getRolls() {
        return rolls;
    }

    public int getFaces() {
        return faces;
    }

    public boolean isExplodes() {
        return explodes;
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

    private static Integer getInt(Matcher matcher, String group, int defaultValue) {
        String groupValue = matcher.group(group);
        return isEmpty(groupValue) ? defaultValue : Integer.parseInt(groupValue);
    }

    private static Integer getSign(Matcher matcher, String group, String positiveValue) {
        String groupValue = matcher.group(group);
        return isEmpty(groupValue) || groupValue.equals(positiveValue) ? 1 : -1;
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

            return new RPGDice(rolls, faces, additive * additiveSign, explodes, savage);
        }
        return null;
        // OR
        // throw new IllegalArgumentException("Invalid Expression");
    }
}
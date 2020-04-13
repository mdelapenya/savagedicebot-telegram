package com.github.mdelapenya.savagedicebot.model;

public class DiceRoll {

    private String detail;
    private int result;

    public DiceRoll(String detail, int result) {
        this.detail = detail;
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "DiceRoll{" + "detail=" + detail + ", result=" + result + '}';
    }

}
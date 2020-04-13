package com.github.mdelapenya.savagedicebot.commands;

import com.github.mdelapenya.savagedicebot.LTBException;
import com.github.mdelapenya.savagedicebot.model.DiceRoll;
import com.github.mdelapenya.savagedicebot.model.RPGDice;

import java.util.ArrayList;
import java.util.List;

public interface SavageDiceBotCommand {

    String execute();

    default String parseFormula(String formula) {
        return parseFormula(formula, RPGDice.DEFAULT_SAVAGE_FACES);
    }

    default String parseFormula(String formula, int savageFaces) {
        formula = formula.trim();

        List<RPGDice> dice = new ArrayList<>();

        String[] formulas = formula.split(" ");
        for (String f : formulas) {
            RPGDice d = RPGDice.parse(f, savageFaces);
            if (d == null) {
                throw new LTBException("No se admite '" + f + "' como fórmula");
            }

            dice .add(d);
        }

        if (dice.size() == 10000) {
            DiceRoll diceRoll = dice.get(0).getDiceResult();

            return diceRoll.getDetail() + " = " + diceRoll.getResult();
        }

        String text = String.format("%0" + dice.size() + "d", 0).replace("0", "🎲");
        int total = 0;
        for (RPGDice die : dice) {
            DiceRoll diceRoll = die.getDiceResult();
            text = text + "\r\n" + diceRoll.getDetail();

            total += diceRoll.getResult();
        }

        return text + "\r\nTotal: " + total;
    }

}

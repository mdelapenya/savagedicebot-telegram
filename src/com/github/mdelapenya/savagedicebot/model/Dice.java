package com.github.mdelapenya.savagedicebot.model;

import com.github.mdelapenya.savagedicebot.LTBException;

public class Dice {

    private String formula;
    private int base;
    private int number;
    private int sides;
    private boolean explodes;
    private Dice savage;

    public Dice(String formula) {
        try {
            this.formula = formula;
            if(formula.startsWith("+")) {
                base = 1;
            }
            else if(formula.startsWith("-")) {
                base = -1;
            }
            else {
                throw new LTBException("Error en formula");
            }

            formula = formula.substring(1);

            if(formula.startsWith("d")) {
                formula = "1" + formula;
            }

            number = Integer.parseInt(formula.substring(0, formula.indexOf("d")));
            
            formula = formula.substring(formula.indexOf("d") + 1);
            
            if(formula.contains("e")) {
                explodes = true;
                sides = Integer.parseInt(formula.substring(0, formula.indexOf("e")));
                formula = null;
            } else if(formula.contains("s")) {
                explodes = true;
                sides = Integer.parseInt(formula.substring(0, formula.indexOf("s")));
                formula = formula.substring(formula.indexOf("s") + 1);
                if(formula.isEmpty()) {
                    formula = "6";
                }
                formula = "+d" + formula + "e";
                savage = new Dice(formula);
                formula = null;
            } else {
                explodes = false;
                sides = Integer.parseInt(formula);
                formula = null;
            }
        }
        catch (LTBException e) {
            throw e;
        }
        catch (Exception e) {
            throw new LTBException("Error en formula");
        }
    }
    
    public DiceRoll getDiceResult() {
        String text = formula;
        int diceResult = 0;
        
        text = text + " (";
        for (int i = 0; i < number; i++) {
            int roll = rollDice(sides, explodes);
            
            if(savage != null) {
                int trSalv = savage.getDiceResult().getResult();
                text = text + "[" + roll + "/" + trSalv + "]";
                
                if(trSalv > roll) {
                    roll = trSalv;
                }
            }
            else {
                text = text + roll;
            }
            
            if(number > 1) {
                text = text + "+";
            }
            
            diceResult += roll;
        }

        if(number > 1) {
            text = text.substring(0, text.length() - 1) + "=" + diceResult + ")";
        } else {
            text = text + ")";
        }
        
        diceResult = diceResult * base;
        
        return new DiceRoll(text, diceResult);
    }

    // /d 2d8 + 3d4s6 - 2d6e
    
    @Override
    public String toString() {
        return "Dice{" + "base=" + base + ", number=" + number + ", sides=" + sides + ", explodes=" + explodes + ", savage=" + savage + '}';
    }
    
    private int rollDice(int sides, boolean explodes) {
        int sum = 0;
        int roll;
        
        do {
            roll = (int)Math.floor((Math.random() * sides) + 1);
            sum += roll;
        } while(explodes && roll == sides);
        
        return sum;
    }

}
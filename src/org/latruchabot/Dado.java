package org.latruchabot;

public class Dado
{
    private String formula;
    private int base;
    private int numero;
    private int caras;
    private boolean explota;
    private Dado salvage;

    public Dado(String formula)
    {
        try
        {
            this.formula = formula;
            if(formula.startsWith("+"))
            {
                base = 1;
            }
            else if(formula.startsWith("-"))
            {
                base = -1;
            }
            else
            {
                throw new LTBException("Error en formula");
            }

            formula = formula.substring(1);

            if(formula.startsWith("d")) formula = "1" + formula;

            numero = Integer.parseInt(formula.substring(0, formula.indexOf("d")));
            
            formula = formula.substring(formula.indexOf("d") + 1);
            
            if(formula.contains("e"))
            {
                explota = true;
                caras = Integer.parseInt(formula.substring(0, formula.indexOf("e")));
                formula = null;
            }
            else if(formula.contains("s"))
            {
                explota = true;
                caras = Integer.parseInt(formula.substring(0, formula.indexOf("s")));
                formula = formula.substring(formula.indexOf("s") + 1);
                if(formula.isEmpty()) formula = "6";
                formula = "+d" + formula + "e";
                salvage = new Dado(formula);
                formula = null;
            }
            else
            {
                explota = false;
                caras = Integer.parseInt(formula);
                formula = null;
            }
        }
        catch (LTBException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new LTBException("Error en formula");
        }
    }
    
    public ResultadoTirada getResultadoTirada()
    {
        String texto = formula;
        int resultadoTirada = 0;
        
        texto = texto + " (";
        for (int i = 0; i < numero; i++)
        {
            int tirada = lanzarDado(caras, explota);
            
            if(salvage != null)
            {
                int trSalv = salvage.getResultadoTirada().getResultado();
                texto = texto + "[" + tirada + "/" + trSalv + "]";
                
                if(trSalv > tirada)
                {
                    tirada = trSalv;
                }
            }
            else
            {
                texto = texto + tirada;
            }
            
            if(numero > 1)
            {
                texto = texto + "+";
            }
            
            resultadoTirada += tirada;
            
        }

        if(numero > 1)
        {
            texto = texto.substring(0, texto.length() - 1) + "=" + resultadoTirada + ")";
        }
        else
        {
            texto = texto + ")";
        }
        
        resultadoTirada = resultadoTirada * base;
        
        return new ResultadoTirada(texto, resultadoTirada);
    }

    // /d 2d8 + 3d4s6 - 2d6e
    
    @Override
    public String toString() {
        return "Dado{" + "base=" + base + ", numero=" + numero + ", caras=" + caras + ", explota=" + explota + ", salvage=" + salvage + '}';
    }
    
    private int lanzarDado(int caras, boolean explota)
    {
        int acumulado = 0;
        int tirada;
        
        do
        {
            tirada = (int)Math.floor((Math.random() * caras) + 1);
            acumulado += tirada;
            
        } while(explota && tirada == caras);
        
        return acumulado;
    }
}
package org.latruchabot;

public class ResultadoTirada
{
    private String detalle;
    private int resultado;

    public ResultadoTirada(String detalle, int resultado)
    {
        this.detalle = detalle;
        this.resultado = resultado;
    }

    public String getDetalle()
    {
        return detalle;
    }

    public int getResultado()
    {
        return resultado;
    }

    @Override
    public String toString() {
        return "ResultadoTirada{" + "detalle=" + detalle + ", resultado=" + resultado + '}';
    }
}
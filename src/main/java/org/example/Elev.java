package org.example;

public class Elev extends Utilizator {
    private String nume;
    private String scoala;

    Elev(String nume, String scoala) {
        this.nume = nume;
        this.scoala = scoala;
    }


    public String getNume() {
        return this.nume;
    }

    /** returneaza scoala la care studiaza elevul */
    public String getScoala() {
        return this.scoala;
    }

    @Override
    public void aruncaExceptii(String tipCerere) throws  ExceptieTipCerere{
        if (tipCerere.compareTo("inlocuire buletin") != 0
                && tipCerere.compareTo("inlocuire carnet de elev") != 0) {
            throw new ExceptieTipCerere();
        }
    }

    @Override
    public int verificareExceptii(String tipCerere, String fileOut) {
        try {
            aruncaExceptii(tipCerere);
        }
        catch (ExceptieTipCerere e) {
            String str = "Utilizatorul de tip elev nu poate inainta o cerere de tip " +  tipCerere;
            insertLineInFile(str, fileOut);
            return 0;
        }
        return 1;
    }
}

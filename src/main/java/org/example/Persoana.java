package org.example;

public class Persoana extends Utilizator{
    private String nume;

    Persoana(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return this.nume;
    }

    @Override
    public void aruncaExceptii(String tipCerere) throws  ExceptieTipCerere{
        if (tipCerere.compareTo("inlocuire buletin") != 0
                && tipCerere.compareTo("inlocuire carnet de sofer") != 0) {
            throw new ExceptieTipCerere();
        }
    }

    @Override
    public int verificareExceptii(String tipCerere, String fileOut) {
        try {
            aruncaExceptii(tipCerere);
        }
        catch (ExceptieTipCerere e) {
            String str = "Utilizatorul de tip persoana nu poate inainta o cerere de tip " +  tipCerere;
            insertLineInFile(str, fileOut);
            return 0;
        }
        return 1;
    }
}

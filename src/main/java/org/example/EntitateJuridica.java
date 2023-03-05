package org.example;

public class EntitateJuridica extends Utilizator{
    private String reprezentant;
    private String numeCompanie;

    EntitateJuridica(String numeCompanie, String reprezentant) {
        this.numeCompanie = numeCompanie;
        this.reprezentant = reprezentant;
    }

    public String getNume() {
        return this.numeCompanie;
    }

    /** returneaza numele reprezentantului companiei */
    public String getReprezentant() {
        return this.reprezentant;
    }

    @Override
    public void aruncaExceptii(String tipCerere) throws  ExceptieTipCerere{
        if (tipCerere.compareTo("creare act constitutiv") != 0
                && tipCerere.compareTo("reinnoire autorizatie") != 0) {
            throw new ExceptieTipCerere();
        }
    }

    public int verificareExceptii(String tipCerere, String fileOut) {
        try {
            aruncaExceptii(tipCerere);
        }
        catch (ExceptieTipCerere e) {
            String str = "Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " +  tipCerere;
            insertLineInFile(str, fileOut);
            return 0;
        }
        return 1;
    }
}

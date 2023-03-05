package org.example;


public class Angajat extends Utilizator{
    private String nume;
    private String companie;

    Angajat(String nume, String companie) {
        this.nume = nume;
        this.companie = companie;
    }
    public String getNume() {
        return this.nume;
    }

    /** returneaza compania la care lucreaza angajatul */
    public String getCompanie() {
        return this.companie;
    }

    @Override
    public void aruncaExceptii(String tipCerere) throws  ExceptieTipCerere{
        if (tipCerere.compareTo("inlocuire buletin") != 0
                && tipCerere.compareTo("inlocuire carnet de sofer") != 0
                && tipCerere.compareTo("inregistrare venit salarial") != 0) {
            throw new ExceptieTipCerere();
        }
    }
    @Override
    public int verificareExceptii(String tipCerere, String fileOut) {
        try {
            aruncaExceptii(tipCerere);
        }
        catch (ExceptieTipCerere e) {
            String str = "Utilizatorul de tip angajat nu poate inainta o cerere de tip " +  tipCerere;
            insertLineInFile(str, fileOut);
            return 0;
        }
        return 1;
    }

}

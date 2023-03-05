package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class Cerere implements Comparable {

    private enum tipCerere {
        INLOCUIRE_BULETIN,
        INREGISTRARE_SALARIU,
        INLOCUIRE_CARNET_SOFER,
        INLOCUIRE_CARNET_ELEV,
        CREARE_ACT_CONSTITUIV,
        REINNOIRE_AUTORIZATIE,
        INREGISTRARE_CUPOANE_PENSIE;
    }
    private String text;
    private String data;

    private int prioritate;
    private tipCerere tip;

    private Utilizator utilizator;

    /** returneaza data la care a fost scrisa cererea */
    public String getData() {
        return this.data;
    }

    /** returneaza prioritatea cererii */
    public int getPrioritate() {
        return this.prioritate;
    }

    /** returneaza tipul cererii */
    public String getTip() {
        if (this.tip == tipCerere.INLOCUIRE_BULETIN) {
            return "inlocuire buletin";
        } else if (this.tip == tipCerere.INREGISTRARE_SALARIU) {
            return "inregistrare venit salarial";
        } else if (this.tip == tipCerere.INLOCUIRE_CARNET_SOFER) {
            return "inlocuire carnet de sofer";
        } else if (this.tip == tipCerere.INLOCUIRE_CARNET_ELEV) {
            return "inlocuire carnet de elev";
        } else if (this.tip == tipCerere.CREARE_ACT_CONSTITUIV) {
            return "creare act constitutiv";
        } else if (this.tip == tipCerere.REINNOIRE_AUTORIZATIE) {
            return "reinnoire autorizatie";
        } else {
            return "inregistrare cupoane de pensie";
        }
    }

    /** returneaza utilizatorul care a initiat cererea */
    public Utilizator getUtilizator() {
        return this.utilizator;
    }

    Cerere(String text, String data, int prioritate, Utilizator u) {
        this.text = text;
        this.data = data;
        this.prioritate = prioritate;
        this.utilizator = u;
        if (text.contains("inlocuire buletin")) {
            this.tip = tipCerere.INLOCUIRE_BULETIN;
        } else if (text.contains("inregistrare venit salarial")) {
            this.tip = tipCerere.INREGISTRARE_SALARIU;
        } else if (text.contains("inlocuire carnet de sofer")) {
            this.tip = tipCerere.INLOCUIRE_CARNET_SOFER;
        } else if (text.contains("inlocuire carnet de elev")) {
            this.tip = tipCerere.INLOCUIRE_CARNET_ELEV;
        } else if (text.contains("creare act constitutiv")) {
            this.tip = tipCerere.CREARE_ACT_CONSTITUIV;
        } else if (text.contains("reinnoire autorizatie")) {
            this.tip = tipCerere.REINNOIRE_AUTORIZATIE;
        } else if (text.contains("inregistrare cupoane de pensie")) {
            this.tip = tipCerere.INREGISTRARE_CUPOANE_PENSIE;
        }
    }

    /** se afiseaza toate cererile ale unui utilizator */
    public static void afisareCereri(ArrayList<Utilizator> listaUtilizatori, String nume, String statusCereri, String fileOut) {
        Utilizator u = Utilizator.cautaUtilizator(listaUtilizatori, nume);
        if (u == null) {
            return;
        }
        PriorityQueue<Cerere> aux;
        if (statusCereri.compareTo("finalizate") == 0) {
            aux = new PriorityQueue<>(u.getCereriFinalizate());
        } else {
            aux = new PriorityQueue<>(u.getCereriInitiate());
        }

        String str, companie, scoala, reprezentant;
        str = u.getNume() + " - cereri in " + statusCereri + ":";
        Utilizator.insertLineInFile(str, fileOut);
        while (!aux.isEmpty()) {
            Cerere c = aux.poll();
            str = c.getData() + " - Subsemnatul ";
            if (u instanceof EntitateJuridica) {
                str += ((EntitateJuridica) u).getReprezentant() + ", ";
            } else {
                str += u.getNume() + ", ";
            }
            if (u instanceof Angajat) {
                companie = ((Angajat) u).getCompanie();
                str += "angajat la compania " + companie + ", ";
            }

            if (u instanceof Elev) {
                scoala = ((Elev) u).getScoala();
                str += "elev la scoala " + scoala + ", ";
            }

            if (u instanceof EntitateJuridica) {
                companie = u.getNume();
                str += "reprezentant legal al companiei " + companie + ", ";
            }

            str += "va rog sa-mi aprobati urmatoarea solicitare: " + c.getTip();
            Utilizator.insertLineInFile(str, fileOut);
        }
    }

    @Override
    public int compareTo(Object o) {
        Cerere c = (Cerere)o;
        Date d1 = new Date(this.data);
        Date d2 = new Date(c.getData());

        if (d1.compareTo(d2) != 0) {
            return d1.compareTo(d2);
        }
        return this.prioritate - c.prioritate;

    }

}

/** clasa conceputa pentru a putea sorta in doua moduri diferite coada de cereri (Priority Queue) */
class SortareDupaPrioritate implements Comparator<Cerere> {

    @Override
    public int compare(Cerere c1, Cerere c2) {
        if (c1.getPrioritate() != c2.getPrioritate()) {
            return c2.getPrioritate() - c1.getPrioritate();
        }
        return c1.getData().compareTo(c2.getData());
    }
}



package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
public abstract class Utilizator {

    /** returneaza numele utilizatorului */
    public abstract String getNume();

    /** arunca exceptii pentru cererile invalide */
    public abstract void aruncaExceptii(String tipCerere) throws ExceptieTipCerere;

    /** trateaza exceptiile aruncate de metoda "aruncaExceptii" */
    public abstract int verificareExceptii(String tipCerere, String fileOut);
    private PriorityQueue<Cerere> cereriInitiate;
    private PriorityQueue<Cerere> cereriFinalizate;
    Utilizator() {
        this.cereriInitiate = new PriorityQueue<>();
        this.cereriFinalizate = new PriorityQueue<>();
    }

    public PriorityQueue getCereriInitiate() {
        return this.cereriInitiate;
    }

    public PriorityQueue getCereriFinalizate() {
        return this.cereriFinalizate;
    }

    /** deschide un fisier si insereaza un string in format append */
    public static void insertLineInFile(String line, String fileName) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(line);
            out.println("");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /** returneaza utilizatorul din lista cu utilizatori care are numele egal cu stringul dat ca parametru */
    public static Utilizator cautaUtilizator(ArrayList<Utilizator> listaUtilizatori, String nume) {
        for (Utilizator u : listaUtilizatori) {
            if (u.getNume().compareTo(nume) == 0) {
                return u;
            }
        }
        return null;
    }

}

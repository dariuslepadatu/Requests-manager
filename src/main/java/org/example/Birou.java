package org.example;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Birou<E> {
    private PriorityQueue<Cerere> cereriBirou;
    private ArrayList<FunctionarPublic<E>> listaFunctionari;
    private String tipUtilizator;
    Birou() {
        this.cereriBirou = new PriorityQueue<>(new SortareDupaPrioritate());
        this.listaFunctionari = new ArrayList<>();
    }

    /** adauga o cerere in coada de cereri in asteptare */
    public void addCerere(Cerere c, E e) {
        this.cereriBirou.add(c);
        if (e instanceof Angajat) {
            this.tipUtilizator = "angajat";
        } else if (e instanceof Elev) {
            this.tipUtilizator = "elev";
        } else if (e instanceof Pensionar) {
            this.tipUtilizator = "pensionar";
        } else if (e instanceof EntitateJuridica){
            this.tipUtilizator = "entitate juridica";
        } else {
            this.tipUtilizator = "persoana";
        }
    }
    /** adauga un functionar in lista de functionari publici */
    public void addFunctionar(FunctionarPublic<E> functionar) {
        this.listaFunctionari.add(functionar);
    }

    /** se rezolva cererea cu cea mai mare priotate: se sterge din cererile biroului,
     * se sterge din cererile initializate ale utilizatorului si este adaugata in coada de
     * cereri finalizate ale acestuia */
    public void rezolvaCerere(FunctionarPublic f) {
        String fileOut = "src/main/resources/output/functionar_" + f.getNume() + ".txt";
        Utilizator u;
        if (!this.cereriBirou.isEmpty()) {
            Cerere c1 = this.cereriBirou.poll();
            u = c1.getUtilizator();
            u.getCereriFinalizate().add(c1);
            PriorityQueue<Cerere> aux = new PriorityQueue<>(u.getCereriInitiate());
            while (!aux.isEmpty()) {
                Cerere c2 = aux.poll();
                if (c2.getData().compareTo(c1.getData()) == 0) {
                    u.getCereriInitiate().remove(c2);
                }
            }
            Utilizator.insertLineInFile(c1.getData() + " - " + u.getNume(), fileOut);
        }
    }

    /** returneaza un obiect de tip functionar in functie de numele acestuia */
    public FunctionarPublic cautaFunctionar(String nume) {
        for (FunctionarPublic f : this.listaFunctionari) {
            if (f.getNume().compareTo(nume) == 0) {
                return f;
            }
        }
        return null;
    }

    /** se sterge cererea care are data egala cu cea trimisa ca parametru:
     * este scoasa din coada cererilor din birou si din coada cererilor
     * initiate ale utilizatorului */
    public void stergeCerere(Utilizator u, String data) {
        if (u != null) {
            PriorityQueue<Cerere> aux = new PriorityQueue<>(u.getCereriInitiate());
            while (!aux.isEmpty()) {
                Cerere c = aux.poll();
                if (c.getData().compareTo(data) == 0) {
                    u.getCereriInitiate().remove(c);
                    break;
                }
            }
            aux = new PriorityQueue<>(this.cereriBirou);
            while (!aux.isEmpty()) {
                Cerere c = aux.poll();
                if (c.getData().compareTo(data) == 0) {
                    this.cereriBirou.remove(c);
                    break;
                }
            }
        }
    }
    /** se afiseaza cererile din birou */
    public void afisareBirou(String fileOut) {
        Utilizator.insertLineInFile(this.tipUtilizator + " - cereri in birou:", fileOut);
        String str, companie, scoala;
        PriorityQueue<Cerere> aux = new PriorityQueue<>(this.cereriBirou);

        try (FileWriter fw = new FileWriter(fileOut, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            while (!aux.isEmpty()) {
                Cerere c = aux.poll();
                Utilizator u = c.getUtilizator();
                str = c.getPrioritate() + " - " + c.getData() + " - ";
                if (u instanceof EntitateJuridica) {
                    str += "Subsemnatul " + ((EntitateJuridica) u).getReprezentant() + ", ";
                } else {
                    str += "Subsemnatul " + u.getNume() + ", ";
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
                out.println(str);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}

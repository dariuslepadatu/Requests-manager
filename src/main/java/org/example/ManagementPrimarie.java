package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ManagementPrimarie {

    /** extrage al catelea substring ne dorim dintr-un string, folosindu-se de un delimitator */
    public static String getSubstring(String strIn, String delim, int cont) {
        StringTokenizer p = new StringTokenizer(strIn, delim);
        while (cont > 0) {
            p.nextToken();
            cont--;
        }
        String strOut = p.nextToken();
        return strOut.trim();
    }

    public static void main(String[] args) {
        String fileIn = "src/main/resources/input/" + args[0];
        String fileOut = "src/main/resources/output/" + args[0];
        ArrayList<Utilizator> listaUtilizatori = new ArrayList<>();
        Birou<Angajat> birouAngajati = new Birou<>();
        Birou<Pensionar> birouPensionari = new Birou<>();
        Birou<Elev> birouElevi = new Birou<>();
        Birou<Persoana> birouPersoane = new Birou<>();
        Birou<EntitateJuridica> birouEntJuridice = new Birou<>();
        String line, nume, companie, tipCerere, scoala, data;
        int prioritate;
        Utilizator u;
        Angajat angajat;
        Pensionar pensionar;
        Persoana persoana;
        Elev elev;
        EntitateJuridica entitateJuridica;
        FunctionarPublic<Angajat> functionarAngajati;
        FunctionarPublic<Elev> functionarElevi;
        FunctionarPublic<Pensionar> functionarPensionari;
        FunctionarPublic<Persoana> functionarPersoane;
        FunctionarPublic<EntitateJuridica> functionarEntJuridice;
        Cerere cerere;
        try {
            File questions = new File(fileIn);
            Scanner myReader = new Scanner(questions);
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();

                if (line.contains("adauga_utilizator")) {
                    nume = getSubstring(line, ";", 2);
                    if (line.contains("angajat")) {
                        companie = getSubstring(line, ";", 3);
                        angajat = new Angajat(nume, companie);
                        listaUtilizatori.add(angajat);
                    }

                    if (line.contains("elev")) {
                        scoala = getSubstring(line, ";", 3);
                        elev = new Elev(nume, scoala);
                        listaUtilizatori.add(elev);
                    }

                    if (line.contains("persoana")) {
                        persoana = new Persoana(nume);
                        listaUtilizatori.add(persoana);
                    }

                    if (line.contains("pensionar")) {
                        pensionar = new Pensionar(nume);
                        listaUtilizatori.add(pensionar);
                    }

                    if (line.contains("entitate juridica")) {
                        companie = getSubstring(line, ";", 2);
                        nume = getSubstring(line, ";", 3);
                        entitateJuridica = new EntitateJuridica(companie, nume);
                        listaUtilizatori.add(entitateJuridica);
                    }

                }

                if (line.contains("adauga_functionar")) {
                    nume = getSubstring(line, ";", 2);
                    if (line.contains("angajat")) {
                        functionarAngajati = new FunctionarPublic<Angajat>(nume);
                        birouAngajati.addFunctionar(functionarAngajati);
                    }

                    if (line.contains("elev")) {
                        functionarElevi = new FunctionarPublic<Elev>(nume);
                        birouElevi.addFunctionar(functionarElevi);
                    }

                    if (line.contains("persoana")) {
                        functionarPersoane = new FunctionarPublic<Persoana>(nume);
                        birouPersoane.addFunctionar(functionarPersoane);
                    }

                    if (line.contains("pensionar")) {
                        functionarPensionari = new FunctionarPublic<Pensionar>(nume);
                        birouPensionari.addFunctionar(functionarPensionari);
                    }

                    if (line.contains("entitate juridica")) {
                        functionarEntJuridice = new FunctionarPublic<EntitateJuridica>(nume);
                        birouEntJuridice.addFunctionar(functionarEntJuridice);
                    }

                }

                if (line.contains("rezolva_cerere")) {
                    nume = getSubstring(line, ";", 2);
                    if (line.contains("angajat")) {
                        functionarAngajati = birouAngajati.cautaFunctionar(nume);
                        birouAngajati.rezolvaCerere(functionarAngajati);
                    }
                    if (line.contains("elev")) {
                        functionarElevi = birouElevi.cautaFunctionar(nume);
                        birouElevi.rezolvaCerere(functionarElevi);
                    }

                    if (line.contains("persoana")) {
                        functionarPersoane = birouPersoane.cautaFunctionar(nume);
                        birouPersoane.rezolvaCerere(functionarPersoane);
                    }

                    if (line.contains("pensionar")) {
                        functionarPensionari = birouPensionari.cautaFunctionar(nume);
                        birouPensionari.rezolvaCerere(functionarPensionari);
                    }

                    if (line.contains("entitate juridica")) {
                        functionarEntJuridice = birouEntJuridice.cautaFunctionar(nume);
                        birouEntJuridice.rezolvaCerere(functionarEntJuridice);
                    }

                }

                if (line.contains(("cerere_noua"))) {
                    nume = getSubstring(line, ";", 1);
                    tipCerere = getSubstring(line, ";", 2);
                    data = getSubstring(line, ";", 3);
                    prioritate = Integer.parseInt(getSubstring(line, ";", 4));
                    u = Utilizator.cautaUtilizator(listaUtilizatori, nume);

                    if (u != null &&  u.verificareExceptii(tipCerere, fileOut) == 1) {
                        cerere = new Cerere(line, data, prioritate, u);
                        u.getCereriInitiate().add(cerere);
                        if (u instanceof Angajat) {
                            birouAngajati.addCerere(cerere, (Angajat)u);
                        } else if (u instanceof Elev) {
                            birouElevi.addCerere(cerere, (Elev)u);
                        } else if (u instanceof Pensionar) {
                            birouPensionari.addCerere(cerere, (Pensionar)u);
                        } else if (u instanceof Persoana) {
                            birouPersoane.addCerere(cerere, (Persoana) u);
                        } else if (u instanceof EntitateJuridica) {
                            birouEntJuridice.addCerere(cerere, (EntitateJuridica)u);
                        }

                    }
                }

                if (line.contains(("afiseaza_cereri_in_asteptare"))) {
                    nume = getSubstring(line, ";", 1);
                    Cerere.afisareCereri(listaUtilizatori, nume, "asteptare",fileOut);
                }

                if (line.contains(("afiseaza_cereri_finalizate"))) {
                    nume = getSubstring(line, ";", 1);
                    Cerere.afisareCereri(listaUtilizatori, nume, "finalizate",fileOut);
                }

                if (line.contains(("retrage_cerere"))) {
                    nume = getSubstring(line, ";", 1);
                    data = getSubstring(line, ";", 2);
                    u = Utilizator.cautaUtilizator(listaUtilizatori, nume);
                    if (u instanceof Angajat) {
                        birouAngajati.stergeCerere(u, data);
                    } else if (u instanceof Elev) {
                        birouElevi.stergeCerere(u, data);
                    } else if (u instanceof Pensionar) {
                        birouPensionari.stergeCerere(u, data);
                    } else if (u instanceof Persoana) {
                        birouPersoane.stergeCerere(u, data);
                    } else if (u instanceof EntitateJuridica) {
                        birouEntJuridice.stergeCerere(u, data);
                    }
                }

                if (line.contains("afiseaza_cereri")) {
                    if (line.contains("angajat")) {
                        birouAngajati.afisareBirou(fileOut);
                    }
                    if (line.contains("elev")) {
                        birouElevi.afisareBirou(fileOut);
                    }
                    if (line.contains("persoana")) {
                        birouPersoane.afisareBirou(fileOut);
                    }
                    if (line.contains("pensionar")) {
                        birouPensionari.afisareBirou(fileOut);
                    }
                    if (line.contains("entitate juridica")) {
                        birouEntJuridice.afisareBirou(fileOut);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
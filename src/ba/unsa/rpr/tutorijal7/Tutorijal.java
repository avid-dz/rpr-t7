package ba.unsa.rpr.tutorijal7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Tutorijal {

    public static void main(String[] args) {
        ArrayList<Grad> lista = ucitajGradove();
        for (Grad grad : lista) {
            System.out.println("Grad " + grad.getNaziv() + " ima temperature:");
            for (int i = 0; i < grad.getBrojMjerenja(); i++) {
                System.out.print((grad.getTemperature())[i] + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<Grad> ucitajGradove() {
        Scanner ulazniTok = null;
        try {
            ulazniTok = new Scanner(new FileReader("mjerenja.txt"));
            ulazniTok.useDelimiter(",");
            ulazniTok.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka mjerenja.txt ne postoji ili se ne moze otvoriti");
            return new ArrayList<>();
        }
        ArrayList<Grad> listaGradova = new ArrayList<>();
        try {
            while (ulazniTok.hasNext()) {
                Grad grad = new Grad();
                grad.setNaziv(ulazniTok.next());
                int velicinaZaTajGrad = 0;
                double[] nizTemperatura = new double[1000];
                while (ulazniTok.hasNextDouble()) {
                    nizTemperatura[velicinaZaTajGrad] = ulazniTok.nextDouble();
                    velicinaZaTajGrad = velicinaZaTajGrad + 1;
                    if (velicinaZaTajGrad == 1000) break;
                }
                grad.setTemperature(nizTemperatura);
                grad.setBrojMjerenja(velicinaZaTajGrad);
                listaGradova.add(grad);
                if (ulazniTok.hasNextLine()) ulazniTok.nextLine();
            }
        } catch(Exception e) {
            System.out.println("Problem pri citanju iz datoteke.");
        } finally {
            ulazniTok.close();
        }
        return listaGradova;
    }

    public static void zapisiXML(UN un) {

    }
}

package ba.unsa.rpr.tutorijal7;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Tutorijal {

    public static void main(String[] args) {

    }

    public static void ucitajGradove() {
        Scanner ulazniTok = null;
        try {
            ulazniTok = new Scanner(new FileReader("mjerenja.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka \"mjerenja.txt\" ne postoji ili se ne moze otvoriti.");
        } finally {
            ulazniTok.close();
        }
    }
}

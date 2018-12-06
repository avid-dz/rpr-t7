package ba.unsa.rpr.tutorijal7;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        ArrayList<Drzava> listaDrzava = new ArrayList<>();
        Drzava d = new Drzava();
        Drzava c = new Drzava();
        Drzava e = new Drzava();
        d.setNaziv("Bosna");
        c.setNaziv("Hercegovina");
        e.setNaziv("Njemacka");
        e.setBrojStanovnika(10000);
        c.setBrojStanovnika(20000);
        d.setBrojStanovnika(50000);
        listaDrzava.add(d);
        listaDrzava.add(c);
        listaDrzava.add(e);
        UN un = new UN();
        un.setListaDrzava(listaDrzava);
        zapisiXML(un);
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

    public static UN ucitajXML(ArrayList<Grad> listaGradova) {
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument!");
            return new UN();
        }
        UN un = new UN();
        Element korijenskiElementDrzave = xmldoc.getDocumentElement();
        NodeList listaDrzava = korijenskiElementDrzave.getChildNodes();
        int brojDrzava = listaDrzava.getLength();
        for (int i = 0; i < brojDrzava; i++) {
            Node dijete = listaDrzava.item(i);
            if (dijete instanceof Element) {

            }
        }
        return un;
    }

    public static void zapisiXML(UN un) {
        XMLEncoder izlazXML = null;
        try {
            izlazXML = new XMLEncoder(new FileOutputStream("un.xml"));
        } catch (Exception e) {
            System.out.println("Greska pri otvaranju ili kreiranju XML datoteke.");
            return;
        }
        try {
            izlazXML.writeObject(un);
        } catch (Exception e) {
            System.out.println("Greska pri upisu u XML datoteku.");
        } finally {
            izlazXML.close();
        }
    }
}

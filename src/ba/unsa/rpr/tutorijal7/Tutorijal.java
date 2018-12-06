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
        UN un = ucitajXml(lista);
        System.out.println();
        System.out.println();
        for (Drzava drzava : un.getListaDrzava()) {
            System.out.println("Drzava " + drzava.getNaziv());
            System.out.println("Povrsina " + drzava.getPovrsina() + " " + drzava.getJedinicaZaPovrsinu());
            System.out.println("Glavni grad: " + drzava.getGlavniGrad().getNaziv());
            System.out.println("Broj stanovnika glavnog grada: " + drzava.getGlavniGrad().getBrojStanovnika());
            System.out.println("Temperature glavnog grada: ");
            if (drzava.getGlavniGrad().getBrojMjerenja() == 0) {
                System.out.println("Nema mjerenja temperatura za njen glavni grad.");
            }
            else {
                for (int brojac = 0; brojac < drzava.getGlavniGrad().getBrojMjerenja(); brojac++) {
                    System.out.print((drzava.getGlavniGrad().getTemperature())[brojac] + " ");
                }
            }
            System.out.println();
            System.out.println();
        }
        zapisiXml(un);
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

    public static UN ucitajXml(ArrayList<Grad> listaGradova) {
        Document xmldoc = null;
        try {
            DocumentBuilder docReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmldoc = docReader.parse(new File("drzave.xml"));
        } catch (Exception e) {
            System.out.println("drzave.xml nije validan XML dokument!");
            return new UN();
        }
        UN un = new UN();
        ArrayList<Drzava> unLista = new ArrayList<>();
        Element korijenskiElementDrzave = xmldoc.getDocumentElement();
        NodeList listaDrzava = korijenskiElementDrzave.getChildNodes();
        int brojDrzava = listaDrzava.getLength();
        for (int i = 0; i < brojDrzava; i++) {
            Node dijeteDrzava = listaDrzava.item(i);
            if (dijeteDrzava instanceof Element) {
                Drzava drzava = new Drzava();
                Element drzavaElement = (Element) dijeteDrzava;
                String brojStanovnikaDrzave = drzavaElement.getAttribute("stanovnika");
                drzava.setBrojStanovnika(Integer.parseInt(brojStanovnikaDrzave));
                NodeList listaDjeceOdDrzave = drzavaElement.getChildNodes();
                int brojDjeceOdDrzave = listaDjeceOdDrzave.getLength();
                for (int j = 0; j < brojDjeceOdDrzave; j++) {
                    Node dijeteOdDrzave = listaDjeceOdDrzave.item(j);
                    if (dijeteOdDrzave instanceof Element) {
                        Element dijeteOdDrzaveElement = (Element) dijeteOdDrzave;
                        if (dijeteOdDrzaveElement.getTagName().equals("naziv")) {
                            drzava.setNaziv(dijeteOdDrzaveElement.getTextContent());
                        }
                        else if (dijeteOdDrzaveElement.getTagName().equals("glavnigrad")) {
                            Grad glavniGradDrzave = new Grad();
                            NodeList listaDjeceOdGrada = dijeteOdDrzaveElement.getChildNodes();
                            String brojStanovnikaGrada = dijeteOdDrzaveElement.getAttribute("stanovnika");
                            glavniGradDrzave.setBrojStanovnika(Integer.parseInt(brojStanovnikaGrada));
                            int brojDjeceOdGrada = listaDjeceOdGrada.getLength();
                            for (int k = 0; k < brojDjeceOdGrada; k++) {
                                Node dijeteOdGrada = listaDjeceOdGrada.item(k);
                                if (dijeteOdGrada instanceof Element) {
                                    Element dijeteOdGradaElement = (Element) dijeteOdGrada;
                                    if (dijeteOdGradaElement.getTagName().equals("naziv")) {
                                        glavniGradDrzave.setNaziv(dijeteOdGradaElement.getTextContent());
                                    }
                                }
                            }
                            for (Grad iter : listaGradova) {
                                if (iter.getNaziv().equals(glavniGradDrzave.getNaziv())) {
                                    glavniGradDrzave.setBrojMjerenja(iter.getBrojMjerenja());
                                    glavniGradDrzave.setTemperature(iter.getTemperature());
                                }
                            }
                            drzava.setGlavniGrad(glavniGradDrzave);
                        }
                        else if (dijeteOdDrzaveElement.getTagName().equals("povrsina")) {
                            drzava.setPovrsina(Double.parseDouble(dijeteOdDrzaveElement.getTextContent()));
                            String jedinicaZaPovrsinuDrzave = dijeteOdDrzaveElement.getAttribute("jedinica");
                            drzava.setJedinicaZaPovrsinu(jedinicaZaPovrsinuDrzave);
                        }
                    }
                }
                unLista.add(drzava);
            }
        }
        un.setListaDrzava(unLista);
        return un;
    }

    public static void zapisiXml(UN un) {
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

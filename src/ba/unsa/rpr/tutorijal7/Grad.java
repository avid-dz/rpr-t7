package ba.unsa.rpr.tutorijal7;

public class Grad {

    private String naziv;
    private int brojStanovnika;
    private double[] temperature;
    private int brojMjerenja;

    public Grad() {
        naziv = "";
        brojStanovnika = 0;
        temperature = new double[1000];
        brojMjerenja = 0;
    }

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public double[] getTemperature() {
        return temperature;
    }
    public void setTemperature(double[] temperature) {
        this.temperature = temperature;
    }
    public int getBrojStanovnika() {
        return brojStanovnika;
    }
    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }
    public int getBrojMjerenja() { return brojMjerenja; }
    public void setBrojMjerenja(int brojMjerenja) { this.brojMjerenja = brojMjerenja; }
}

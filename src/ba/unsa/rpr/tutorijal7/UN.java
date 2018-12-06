package ba.unsa.rpr.tutorijal7;

import java.io.Serializable;
import java.util.ArrayList;

public class UN implements Serializable {

    private ArrayList<Drzava> listaDrzava;

    public UN() {
        listaDrzava = new ArrayList<>();
    }

    public ArrayList<Drzava> getListaDrzava() {
        return listaDrzava;
    }
    public void setListaDrzava(ArrayList<Drzava> listaDrzava) {
        this.listaDrzava = listaDrzava;
    }
}

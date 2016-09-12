package com.fabianmainz.fractionscalculator;

/**
 * Created by fabi on 9/11/2016.
 */
public class Bruch {
    private int zaehler;
    private int nenner;

    //(a/b), (c/d)
    public Bruch(int z, int n) {
        zaehler = z;
        nenner = n;
    }

    public int getNenner() {
        return nenner;
    }

    public int getZaehler() {
        return zaehler;
    }

    public void setZaehler(int z) {
        zaehler = z;
    }

    public void setNenner(int n) {
        nenner = n;
    }
    //(ad + bc) / bd
    public Bruch add(Bruch a) {
        int newZaehler = (zaehler*a.getNenner()+nenner*a.getZaehler());
        int newNenner = (nenner*a.getNenner());
        return new Bruch(newZaehler, newNenner);
    }

    //(ad - bc) / bd
    public Bruch subtract(Bruch a) {
        int newZaehler = (zaehler*a.getNenner()-nenner*a.getZaehler());
        int newNenner = (nenner*a.getNenner());
        return new Bruch(newZaehler, newNenner);
    }

    //ad / bc
    public Bruch divide(Bruch a) {
        int newZaehler = (zaehler*a.getNenner());
        int newNenner = (nenner*a.getZaehler());
        return new Bruch(newZaehler, newNenner);
    }

    //ac / bd
    public Bruch multiply(Bruch a) {
        int newZaehler = (zaehler*a.getZaehler());
        int newNenner = (nenner*a.getNenner());
        return new Bruch(newZaehler, newNenner);
    }

    public Bruch simplifiy() {
        int m = zaehler;
        int n = nenner;
        int r = m % n;
        while (r > 0) {
            m = n;
            n = r;
            r = m % n;
        }
        return new Bruch(zaehler/n, nenner/n);
    }
}

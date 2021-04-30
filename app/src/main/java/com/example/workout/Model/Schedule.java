package com.example.workout.Model;

public class Schedule {
    private String jenis;
    private String tanggal;
    private String jamAwal;
    private String jamAkhir;

    public Schedule(String jenis, String tanggal, String jamAwal, String jamAkhir) {
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public void setJamAwal(String jamAwal) {
        this.jamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        this.jamAkhir = jamAkhir;
    }
}
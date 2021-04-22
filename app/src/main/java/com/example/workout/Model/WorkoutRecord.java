package com.example.workout.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class WorkoutRecord {
    @PrimaryKey
    private int wid;

    @ColumnInfo(name = "jenis")
    private String jenis; //Ada 3 kemungkinan, Cycling, Walking, Running

    @ColumnInfo(name = "jarakTempuh")
    private Double jarakTempuh; //Kalau bukan cycling, kembalikan -1

    @ColumnInfo(name = "jumlahStep")
    private int jumlahStep; //Kalau cycling, kembalikan -1

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    public WorkoutRecord(int wid, String jenis, Double jarakTempuh, int jumlahStep, String tanggal) {
        this.wid = wid;
        this.jenis = jenis;
        this.jarakTempuh = jarakTempuh;
        this.jumlahStep = jumlahStep;
        this.tanggal = tanggal;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wID) {
        this.wid = wID;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Double getJarakTempuh() {
        return jarakTempuh;
    }

    public void setJarakTempuh(Double jarakTempuh) {
        this.jarakTempuh = jarakTempuh;
    }

    public int getJumlahStep() {
        return jumlahStep;
    }

    public void setJumlahStep(int jumlahStep) {
        this.jumlahStep = jumlahStep;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}

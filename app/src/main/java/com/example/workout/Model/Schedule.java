package com.example.workout.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="schedule")
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    private int sid;

    @ColumnInfo(name = "jenis")
    private String jenis;

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @ColumnInfo(name = "jamAwal")
    private String jamAwal;

    @ColumnInfo(name = "jamAkhir")
    private String jamAkhir;

    public Schedule(String jenis, String tanggal, String jamAwal, String jamAkhir) {
        this.jenis = jenis;
        this.tanggal = tanggal;
        this.jamAwal = jamAwal;
        this.jamAkhir = jamAkhir;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
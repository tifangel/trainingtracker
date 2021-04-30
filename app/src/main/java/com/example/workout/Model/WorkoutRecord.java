package com.example.workout.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="workout")
public class WorkoutRecord implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int wid;

    @ColumnInfo(name = "jenis")
    private String jenis; //Ada 3 kemungkinan, Cycling, Walking, Running

    @ColumnInfo(name = "jarakTempuh")
    private Double jarakTempuh; //Kalau bukan cycling, kembalikan -1

    @ColumnInfo(name = "jumlahStep")
    private int jumlahStep; //Kalau cycling, kembalikan -1

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    public WorkoutRecord(String jenis, Double jarakTempuh, int jumlahStep, String tanggal) {
//        this.wid = wid;
        this.jenis = jenis;
        this.jarakTempuh = jarakTempuh;
        this.jumlahStep = jumlahStep;
        this.tanggal = tanggal;
    }

    protected WorkoutRecord(Parcel in) {
        wid = in.readInt();
        jenis = in.readString();
        if (in.readByte() == 0) {
            jarakTempuh = null;
        } else {
            jarakTempuh = in.readDouble();
        }
        jumlahStep = in.readInt();
        tanggal = in.readString();
    }

    public static final Creator<WorkoutRecord> CREATOR = new Creator<WorkoutRecord>() {
        @Override
        public WorkoutRecord createFromParcel(Parcel in) {
            return new WorkoutRecord(in);
        }

        @Override
        public WorkoutRecord[] newArray(int size) {
            return new WorkoutRecord[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wid);
        dest.writeString(jenis);
        if (jarakTempuh == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(jarakTempuh);
        }
        dest.writeInt(jumlahStep);
        dest.writeString(tanggal);
    }
}

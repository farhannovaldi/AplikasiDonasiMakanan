package com.project.nvl.aplikasidonasimakanan;

import java.util.HashMap;
import java.util.Map;

public class Donasi {
    private String jenisMakanan;
    private String jumlahMakanan;
    private String hpDonasi;
    private String jemputDonasi;
    private String catatanDonasi;
    private String uid;

    public Donasi() {
    }

    public Donasi(String jenisMakanan, String jumlahMakanan, String hpDonasi, String jemputDonasi, String catatanDonasi, String uid) {
        this.jenisMakanan = jenisMakanan;
        this.jumlahMakanan = jumlahMakanan;
        this.hpDonasi = hpDonasi;
        this.jemputDonasi = jemputDonasi;
        this.catatanDonasi = catatanDonasi;
        this.uid = uid;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    public String getJumlahMakanan() {
        return jumlahMakanan;
    }

    public void setJumlahMakanan(String jumlahMakanan) {
        this.jumlahMakanan = jumlahMakanan;
    }

    public String getHpDonasi() {
        return hpDonasi;
    }

    public void setHpDonasi(String hpDonasi) {
        this.hpDonasi = hpDonasi;
    }

    public String getJemputDonasi() {
        return jemputDonasi;
    }

    public void setJemputDonasi(String jemputDonasi) {
        this.jemputDonasi = jemputDonasi;
    }

    public String getCatatanDonasi() {
        return catatanDonasi;
    }

    public void setCatatanDonasi(String catatanDonasi) {
        this.catatanDonasi = catatanDonasi;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Convert object to a Map for Firebase
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("jenisMakanan", jenisMakanan);
        map.put("jumlahMakanan", jumlahMakanan);
        map.put("hpDonasi", hpDonasi);
        map.put("jemputDonasi", jemputDonasi);
        map.put("catatanDonasi", catatanDonasi);
        map.put("uid", uid);
        return map;
    }
}

public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String waldos;

    public Mahasiswa(String nim, String nama, String jenisKelamin, String waldos) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.waldos = waldos;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setWaldos(String waldos) {this.waldos = waldos;}

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public String getWaldos() {return this.waldos;}
}

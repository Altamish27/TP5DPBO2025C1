## Saya Hasbi Haqqul Fikri dengan NIM 2309245 mengerjakan soal TP 5 dalam mata kuliah DPBO untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Aplikasi Manajemen Data Mahasiswa (Terintegrasi Database)

## Deskripsi
Aplikasi ini adalah sebuah program berbasis Java Swing yang digunakan untuk mengelola data mahasiswa. Berbeda dengan versi sebelumnya, kali ini data mahasiswa disimpan dalam database MySQL. Pengguna dapat menambahkan, mengedit, dan menghapus data mahasiswa yang mencakup **NIM, Nama, Jenis Kelamin, dan Wali Dosen (Waldos)**. Program ini juga menampilkan daftar mahasiswa dalam JTable serta menyediakan berbagai komponen UI seperti JTextField, JComboBox, dan JButton untuk interaksi pengguna.

## Fitur Utama
- **Menampilkan daftar mahasiswa** dalam bentuk tabel dengan data yang diambil langsung dari database.
- **Menambahkan data mahasiswa** ke database dengan validasi NIM unik.
- **Mengedit data mahasiswa** yang sudah ada dalam database.
- **Menghapus data mahasiswa** langsung dari database dengan konfirmasi sebelum penghapusan.
- **Validasi Input:**
  - Tidak dapat menambahkan data jika NIM sudah ada di database.
  - Tidak dapat menambahkan atau mengedit data jika ada kolom yang kosong.
- **Menggunakan JComboBox** untuk memilih Jenis Kelamin dan Wali Dosen agar input lebih konsisten.

## Desain Program
Program ini menggunakan **Java Swing** dan **JDBC (Java Database Connectivity)** untuk berinteraksi dengan MySQL.

- **Database:** `db_mahasiswa`
- **Tabel:** `mahasiswa` dengan struktur berikut:
  ```sql
  CREATE TABLE mahasiswa (
      nim VARCHAR(10) PRIMARY KEY,
      nama VARCHAR(100) NOT NULL,
      jenis_kelamin ENUM('Laki-laki', 'Perempuan') NOT NULL,
      waldos VARCHAR(100) NOT NULL
  );
  ```
- **DefaultTableModel** digunakan untuk menampilkan data mahasiswa dalam JTable.

## Alur Program
### 1. Inisialisasi Database dan Data
- Program terhubung dengan MySQL menggunakan JDBC.
- Data mahasiswa diambil dari tabel `mahasiswa` dan ditampilkan dalam JTable.

### 2. Menambahkan Data
- Pengguna mengisi **NIM, Nama, Jenis Kelamin, dan Wali Dosen** melalui form input.
- Jika ada kolom yang kosong, muncul pesan error.
- Jika NIM sudah ada dalam database, muncul pesan error.
- Jika data valid, sistem akan menambahkan ke database dan memperbarui tampilan tabel.

### 3. Mengedit Data
- Pengguna memilih salah satu baris di tabel, lalu data tersebut ditampilkan kembali di form input.
- Jika tombol **Update** ditekan, sistem akan memperbarui data dalam database.
- Jika ada kolom yang kosong, muncul pesan error.
- Jika berhasil, tabel akan diperbarui.

### 4. Menghapus Data
- Setelah memilih data dalam tabel, pengguna dapat menekan tombol **Delete**.
- Program akan meminta konfirmasi sebelum menghapus.
- Jika disetujui, data akan dihapus dari database dan tabel diperbarui.

## Implementasi Validasi
### 1. Validasi NIM Unik Saat Insert
Sebelum menambahkan data, program akan melakukan pengecekan apakah NIM sudah ada di database:
```java
public boolean isNimExists(String nim) {
    String query = "SELECT COUNT(*) FROM mahasiswa WHERE nim = '" + nim + "'";
    try {
        ResultSet rs = database.selectQuery(query);
        if (rs.next() && rs.getInt(1) > 0) {
            return true; // NIM sudah ada
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
```

### 2. Validasi Kolom Kosong
Sebelum menyimpan atau memperbarui data, program akan memeriksa apakah ada kolom kosong:
```java
public boolean isFormValid() {
    return !nimField.getText().isEmpty() &&
           !namaField.getText().isEmpty() &&
           jenisKelaminComboBox.getSelectedItem() != null &&
           waldosComboBox.getSelectedItem() != null;
}
```

Jika ada kolom yang kosong, tampilkan pesan error:
```java
if (!isFormValid()) {
    JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}
```

## Kesimpulan
Aplikasi ini memungkinkan pengguna untuk mengelola data mahasiswa dengan penyimpanan langsung ke database MySQL. Dengan fitur validasi tambahan, pengguna tidak dapat memasukkan NIM yang sudah ada atau menyimpan data dengan kolom kosong. Program ini mempermudah pengelolaan data mahasiswa dengan tampilan berbasis GUI yang intuitif.

---
**Screenshot Tampilan Program:**
![image](https://github.com/user-attachments/assets/ce59deb3-01ae-48d0-b823-567cf4dd13a1)


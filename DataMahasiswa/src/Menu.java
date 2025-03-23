import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // Buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 540);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);

        window.setContentPane(window.mainPanel);
        // ubah warna background
        window.getContentPane().setBackground(Color.white);

        // tampilkan window
        window.setVisible(true);

        // agar program tidak berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel Waldos;
    private JComboBox waldosComboBox;
    private JLabel waldosLabel;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = {"", "Laki-Laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));

        String[] waldosData = {
                "",
                "Prof. Dr. Munir, M.IT.",
                "Prof. Dr. H. Wawan Setiawan, M.Kom.",
                "Drs. H. Heri Sutarno, M.T.",
                "Drs. H. Eka Fitrajaya Rahman, M.T.",
                "Asep Wahyudin, M.T.",
                "Eddy Prasetyo Nugroho, M.T.",
                "Dr. Enjang Ali Nurdin, M.Kom.",
                "Herbert Siregar, M.T.",
                "Jajang Kusnendar, M.T.",
                "Lala Septem Riza, M.T., Ph.D.",
                "Rasim, M.T.",
                "Rizky Rahman J., M.Kom.",
                "Muhammad Nursalman, M.T.",
                "Wahyudin, M.T.",
                "Dr. Yudi Wibisono, M.T.",
                "Rosa Ariani Sukamto, M.T.",
                "Harsa Wara P., M.Pd.",
                "Budi Laksono Putro, M.T.",
                "Novi Sofia Fitriasari, M.T.",
                "Enjun Junaeti, M.Si.",
                "Dr. Rani Megasari, M.T.",
                "Yaya Wihardi, M.Kom.",
                "Eki Nugraha, M.Kom.",
                "Erna Piantari, M.T.",
                "Erlangga, M.T.",
                "Yudi Ahmad Hambali, M.T."
        };

        waldosComboBox.setModel(new DefaultComboBoxModel<>(waldosData));



        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedWaldos = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();


                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                waldosComboBox.setSelectedItem(selectedWaldos);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    // setTable()
    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Waldos"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);
        try {
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");
            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[5];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("waldos");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    // insertData()
    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String waldos = waldosComboBox.getSelectedItem().toString();

        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || waldos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cek apakah NIM sudah ada di database
        ResultSet rs = database.selectQuery("SELECT * FROM mahasiswa WHERE nim='" + nim + "'");
        try {
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO mahasiswa (nim, nama, jenis_kelamin, waldos) VALUES ('" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + waldos + "')";
        database.insertUpdateDeleteQuery(sql);
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    // updateData()
    public void updateData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String waldos = waldosComboBox.getSelectedItem().toString();

        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || waldos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE mahasiswa SET nama='" + nama + "', jenis_kelamin='" + jenisKelamin + "', waldos='" + waldos + "' WHERE nim='" + nim + "'";

        int rowsAffected = database.insertUpdateDeleteQuery(sql);
        if (rowsAffected > 0) {
            mahasiswaTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } else {
            JOptionPane.showMessageDialog(null, "Update gagal! Pastikan NIM benar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // deleteData()
// deleteData()
    public void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String nim = nimField.getText();
            String sql = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
            database.insertUpdateDeleteQuery(sql);
            mahasiswaTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    // clearForm()
    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

}

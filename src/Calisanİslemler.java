
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calisanİslemler {
    
    //veri tabanındaki guncellestirme alma islemeri burda yapacaz
    
    private Connection con =null;
    private Statement statement=null;
    private PreparedStatement preparedStatement = null;

    public ArrayList <Calisan> calisanlariGetir(){
        ArrayList<Calisan> cikti = new ArrayList< Calisan>();
        
        try {
            statement=con.createStatement();
            String sorgu = "Select * From calisanlar";
            ResultSet rs =statement.executeQuery(sorgu);
            while (rs.next()) {                
                int id =rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String dept = rs.getString("departman");
                String maas = rs.getString("maas");
                
                cikti.add(new Calisan(id, ad, soyad, dept, maas));
            }
            return cikti;
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanİslemler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
         
    } 
    public void calisanGuncelle (int id,String yeni_ad ,String yeni_soyad ,String yeni_departman ,String yeni_maas){
        
        String sorgu = "Update calisanlar Set ad = ? , soyad = ? , departman = ? , maas = ? Where id = ?" ;
        try {
            preparedStatement = con.prepareCall(sorgu);
            preparedStatement.setString(1, yeni_ad);
            preparedStatement.setString(2, yeni_soyad);
            preparedStatement.setString(3, yeni_departman);
            preparedStatement.setString(4, yeni_maas);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Calisanİslemler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calisanSil(int id){
        String sorgu = "Delete From calisanlar Where id = ?";
        try {
            
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanİslemler.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }
    public void calisaEkle(String ad ,String soyad ,String departman ,String maas){
        String sorgu = "Insert Into calisanlar(ad,soyad,departman,maas) Values(?,?,?,?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Calisanİslemler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean girisYap (String kullanici_adi , String parola){
        
        String sorgu = "Select * From adminler where username = ? and password = ?";
        try {
            preparedStatement = con.prepareCall(sorgu);
            preparedStatement.setString(1, kullanici_adi);
            preparedStatement.setString(2, parola);
            //ResultSet interface'i bir sorgudan dönen sonuçları içerir.
            ResultSet rs = preparedStatement.executeQuery();
           return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanİslemler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            

    }
    public Calisanİslemler() {
        String url = "jdbc:mysql://" + DataBase.host + ":" + DataBase.port + "/" + DataBase.db_ismi+ "?useUnicode=true&characterEncoding=utf8";
        
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }
        
        
        try {
            con = DriverManager.getConnection(url, DataBase.kullanici_adi, DataBase.parola);
            System.out.println("Bağlantı Başarılı...");
            
            
        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.imatge;


import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;




/**
 *
 * @author annagarcia-nieto
 */
@WebService(serviceName = "ImatgeWS", wsdlLocation = "WEB-INF/wsdl/ImatgeWS.wsdl")
public class ImatgeWS {

    /**
     * Web service operation
     * @param imatge
     * @param foto
     * @return 
     */
    @WebMethod(operationName = "registreImatge")
   
    public int registreImatge(@WebParam(name = "imatge") Imatge imatge, Image foto) {
        //TODO write your implementation code here:
        String titol = imatge.getTitol();
        String autor = imatge.getAutor();
        String data = imatge.getDataCreacio();
        String descripcio = imatge.getDescripcio();
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
           //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
           PreparedStatement statement = conn.prepareStatement("insert into imagenes values (?, ?, ?, ?, ?, ? , ?)");
           statement.setInt(1, imatge.getId());
           statement.setString(2, "Jordi");
           statement.setString(3, imatge.getTitol());
           statement.setString(4, imatge.getDescripcio());
           statement.setString(5, imatge.getKeywords());
           statement.setString(6, imatge.getAutor());
           statement.setString(7, imatge.getDataCreacio());
           statement.executeUpdate();
            return 1;
        }
        catch(SQLException ex){
            return 0;
        }
    }

    /**
     * Web service operation
     * @param image
     * @return 
     */
    @WebMethod(operationName = "modifyImatge")

    public int modifyImatge(@WebParam(name = "imatge") Imatge image) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
           //conn = DriverManager.getConnection("jdbc:sqlite:/Users/Jordi/Desktop/loquesea.db");
           
           PreparedStatement statement = conn.prepareStatement("update imagenes set titulo = ?, descripcion = ?, palabras_clave = ?, autor = ? where id_imagen = ?;");
           statement.setString(1, image.getTitol());
           statement.setString(2, image.getDescripcio());
           statement.setString(3, image.getKeywords());
           statement.setString(4, image.getAutor());
           statement.setString(5, String.valueOf(image.getId()));

           statement.executeUpdate();
           return 1;
        }
        catch(SQLException e){
            System.out.println(e);
            return 0;
        }
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "listImatges")
    public List<Imatge> listImatges() {
        //TODO write your implementation code here:
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes"); 

            List<Imatge> list = new ArrayList();
            
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Imatge imatge = new Imatge();
                System.out.println(rs.getInt("id_imagen"));
                imatge.setTitol(rs.getString("titulo"));
                imatge.setId(rs.getInt("id_imagen"));
                imatge.setDescripcio(rs.getString("descripcion"));
                imatge.setKeywords(rs.getString("palabras_clave"));
                imatge.setDataCreacio(rs.getString("creacion"));
                imatge.setAutor(rs.getString("autor"));
                            
                list.add(imatge);

            }
            return list;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }  
    }

    /**
     * Web service operation
     * @param Id
     * @return 
     */
    @WebMethod(operationName = "searchById")
    public Imatge searchById(@WebParam(name = "Id") int Id) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where id_imagen = ?");
            statement.setString(1, String.valueOf(Id));
            ResultSet rs = statement.executeQuery();
            Imatge im = new Imatge();
            im.setTitol(rs.getString("titulo"));
            im.setId(rs.getInt("id_imagen"));
            im.setDescripcio(rs.getString("descripcion"));
            im.setKeywords(rs.getString("palabras_clave"));
            im.setDataCreacio(rs.getString("creacion"));
            im.setAutor(rs.getString("autor"));
            return im;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * Web service operation
     * @param title
     * @return 
     */
    @WebMethod(operationName = "searchByTitle")
    public List<Imatge> searchByTitle(@WebParam(name = "title") String title) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where titulo = ?"); 
            statement.setString(1, title);
            List<Imatge> list = new ArrayList();
            
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Imatge imatge = new Imatge();
                System.out.println(rs.getInt("id_imagen"));
                imatge.setTitol(rs.getString("titulo"));
                imatge.setId(rs.getInt("id_imagen"));
                imatge.setDescripcio(rs.getString("descripcion"));
                imatge.setKeywords(rs.getString("palabras_clave"));
                imatge.setDataCreacio(rs.getString("creacion"));
                imatge.setAutor(rs.getString("autor"));
                            
                list.add(imatge);

            }
            return list;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }  
    }

    /**
     * Web service operation
     * @param date
     * @return 
     */
    @WebMethod(operationName = "searchByDate")
    public List<Imatge> searchByDate(@WebParam(name = "date") String date) {
          Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where creacion = ?"); 
            statement.setString(1, date);
            List<Imatge> list = new ArrayList();
            
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Imatge imatge = new Imatge();
                System.out.println(rs.getInt("id_imagen"));
                imatge.setTitol(rs.getString("titulo"));
                imatge.setId(rs.getInt("id_imagen"));
                imatge.setDescripcio(rs.getString("descripcion"));
                imatge.setKeywords(rs.getString("palabras_clave"));
                imatge.setDataCreacio(rs.getString("creacion"));
                imatge.setAutor(rs.getString("autor"));
                            
                list.add(imatge);

            }
            return list;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }  
    }

    /**
     * Web service operation
     * @param autor
     * @return 
     */
    @WebMethod(operationName = "searchByAutor")
    public List<Imatge> searchByAutor(@WebParam(name = "autor") String autor) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes where autor = ?"); 
            statement.setString(1, autor);
            List<Imatge> list = new ArrayList();
            
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Imatge imatge = new Imatge();
                System.out.println(rs.getInt("id_imagen"));
                imatge.setTitol(rs.getString("titulo"));
                imatge.setId(rs.getInt("id_imagen"));
                imatge.setDescripcio(rs.getString("descripcion"));
                imatge.setKeywords(rs.getString("palabras_clave"));
                imatge.setDataCreacio(rs.getString("creacion"));
                imatge.setAutor(rs.getString("autor"));
                            
                list.add(imatge);

            }
            return list;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Web service operation
     * @param keywords
     * @return 
     */
    @WebMethod(operationName = "searchByKeywords")
    public List<Imatge> searchByKeywords(@WebParam(name = "keywords") String keywords) {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error class.forname");
        }
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Oriol\\Desktop\\basedades.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:\\Users\\oriol\\OneDrive\\Escritorio\\loquesea.db");
            //conn = DriverManager.getConnection("jdbc:sqlite:/Usuaris/annagarcia-nieto/Escriptori/basedades.db");
            PreparedStatement statement =  conn.prepareStatement("select * from imagenes"); 
            List<Imatge> list = new ArrayList();
            
            
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if(rs.getString("palabras_clave").contains(keywords)){
                    Imatge imatge = new Imatge();
                    System.out.println(rs.getInt("id_imagen"));
                    imatge.setTitol(rs.getString("titulo"));
                    imatge.setId(rs.getInt("id_imagen"));
                    imatge.setDescripcio(rs.getString("descripcion"));
                    imatge.setKeywords(rs.getString("palabras_clave"));
                    imatge.setDataCreacio(rs.getString("creacion"));
                    imatge.setAutor(rs.getString("autor"));

                    list.add(imatge);
                }

            }
            return list;
        } catch(SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    
    private byte[] getImageBytes(String name) throws IOException{
        URL resource = this.getClass().getResource("/org/flower/resources/"+name+".");
        return getBytes(resource);
    }
    
    private byte[] getBytes(URL resource) throws IOException{
        InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for( int read; (read = in.read(buf)) != -1;){
            bos.write(buf, 0 , read);
        }
        return bos.toByteArray();
    }
    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail){
            param.setSourceSubsampling(4,4,0,0);
        }
        return reader.read(0, param);
    }
    
    private List allFlowers() throws IOException {
        List flowers = new ArrayList();
        for (String flower: FLOWERS){
            URL resource = this.getClass().getResource("Path");
            flowers.add(getBytes(resource));
        }
        return flowers;
    }
}

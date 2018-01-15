/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlgen;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author d8user
 */
public class SqlGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("controls3");
        dataSource.setUser("root");
        dataSource.setPassword("");
        
//        dataSource.setServerName("myDBHost.example.org");
        dataSource.setServerName("localhost");

        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clin_musculoes");
        
        ResultSetMetaData rsm =  rs.getMetaData();
        System.out.println(rsm);
        System.out.println(rsm.getColumnCount());
        String nombreCol = "";
        String tipo = "";
            
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= rsm.getColumnCount(); i++) {
            nombreCol = rsm.getColumnName(i);
            tipo = rsm.getColumnTypeName(i);
            
            sb.append("DECLARE _").append(nombreCol).append(" ").append(tipo).append(";");
            
//          System.out.println(nombreCol);
//          System.out.println(tipo);
            System.out.println(sb);
            sb = new StringBuilder();
        }
        System.out.println("-------------------");
        
        
        sb = new StringBuilder();
        for (int i = 1; i <= rsm.getColumnCount(); i++) {
            nombreCol = rsm.getColumnName(i);
            tipo = rsm.getColumnTypeName(i);
            
            sb.append("SET _").append(nombreCol).append(" = fnExplode(_criterios,'*',").append(i-2).append(");");
            
//            System.out.println(nombreCol);
//            System.out.println(tipo);
            System.out.println(sb);
            sb = new StringBuilder();
        }
        System.out.println("-------------------");
        
        sb = new StringBuilder();
        for (int i = 1; i <= rsm.getColumnCount(); i++) {
            nombreCol = rsm.getColumnName(i);
            tipo = rsm.getColumnTypeName(i);
            
            sb.append(nombreCol).append(" = _").append(nombreCol).append(",");
            
//            System.out.println(nombreCol);
//            System.out.println(tipo);
            System.out.println(sb);
            sb = new StringBuilder();
        }
        System.out.println("-------------------");
        
        
        
        
        System.out.println(sb);
        
//        System.out.println(rsm);
//        while (rs.next()) {
//            System.out.println(rs.getString("peso"));
//            int x = rs.getInt("a");
//            String s = rs.getString("b");
//            float f = rs.getFloat("c");
//        }

        rs.close();
        stmt.close();
        conn.close();
    }
}

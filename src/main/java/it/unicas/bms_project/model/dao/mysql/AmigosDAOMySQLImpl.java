package it.unicas.bms_project.model.dao.mysql;

import it.unicas.bms_project.model.Amigos;
import it.unicas.bms_project.model.dao.DAO;
import it.unicas.bms_project.model.dao.DAOException;
import it.unicas.bms_project.util.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class AmigosDAOMySQLImpl implements DAO<Amigos> {

    private AmigosDAOMySQLImpl(){}

    private static DAO dao = null;
    private static Logger logger = null;

    public static DAO getInstance(){
        if (dao == null){
            dao = new AmigosDAOMySQLImpl();
            logger = Logger.getLogger(AmigosDAOMySQLImpl.class.getName());
        }
        return dao;
    }

    public static void main(String args[]) throws DAOException {
        AmigosDAOMySQLImpl c = new AmigosDAOMySQLImpl();


        c.insert(new Amigos("Mario", "Rossi", "0824981", "molinara@uni.it", "21-10-2017", null));
        c.insert(new Amigos("Carlo", "Ciampi", "0824982", "ciampi@uni.it", "22-02-2017", null));
        c.insert(new Amigos("Ornella", "Vaniglia", "0824983", "vaniglia@uni.it", "23-05-2017", null));
        c.insert(new Amigos("Cornelia", "Crudelia", "0824984", "crudelia@uni.it", "24-05-2017", null));
        c.insert(new Amigos("Franco", "Bertolucci", "0824985", "bertolucci@uni.it", "25-10-2017", null));
        c.insert(new Amigos("Carmine", "Labagnara", "0824986", "lagbagnara@uni.it", "26-10-2017", null));
        c.insert(new Amigos("Mauro", "Cresta", "0824987", "cresta@uni.it", "27-12-2017", null));
        c.insert(new Amigos("Andrea", "Coluccio", "0824988", "coluccio@uni.it", "28-01-2017", null));


        List<Amigos> list = c.select(null);
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }


        Amigos toDelete = new Amigos();
        toDelete.setNombre("");
        toDelete.setApellido("");
        toDelete.setEmail("");
        toDelete.setTelefono("");
        toDelete.setIdamigos(7);

        c.delete(toDelete);

        list = c.select(null);

        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }

    }


    @Override
    public List<Amigos> select(Amigos a) throws DAOException {

        if (a == null){
            a = new Amigos("", "", "", "", "", null); // Cerca tutti gli elementi
        }

        ArrayList<Amigos> lista = new ArrayList<>();
        try{

            if (a == null || a.getApellido() == null
                    || a.getNombre() == null
                    || a.getEmail() == null
                    || a.getTelefono() == null){
                throw new DAOException("In select: any field can be null");
            }

            Statement st = DAOMySQLSettings.getStatement();

            String sql = "select * from amigos where apellido like '";
            sql += a.getApellido() + "%' and nombre like '" + a.getNombre();
            sql += "%' and telefono like '" + a.getTelefono() + "%'";
            if (a.getCumpleanos() != null){
                sql += "%' and cumpleanos like '" + a.getCumpleanos() + "%'";
            }
            sql += " and email like '" + a.getEmail() + "%'";

            try{
              logger.info("SQL: " + sql);
            } catch(NullPointerException nullPointerException){
              System.out.println("SQL: " + sql);
            }
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lista.add(new Amigos(rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("cumpleanos"),
                        rs.getInt("idamigos")));
            }
            DAOMySQLSettings.closeStatement(st);

        } catch (SQLException sq){
            throw new DAOException("In select(): " + sq.getMessage());
        }
        return lista;
    }

    @Override
    public void delete(Amigos a) throws DAOException {
        if (a == null || a.getIdamigos() == null){
            throw new DAOException("In delete: idamigos cannot be null");
        }
        String query = "DELETE FROM amigos WHERE idamigos='" + a.getIdamigos() + "';";

        try{
          logger.info("SQL: " + query);
        } catch (NullPointerException nullPointerException){
          System.out.println("SQL: " + query);
        }

        executeUpdate(query);

    }

    private void verifyObject(Amigos a) throws DAOException {
        if (a == null || a.getApellido() == null
                || a.getNombre() == null
                || a.getEmail() == null
                || a.getCumpleanos() == null
                || a.getTelefono() == null){
            throw new DAOException("In select: any field can be null");
        }
    }

    private void executeUpdate(String query) throws DAOException{
        try {
            Statement st = DAOMySQLSettings.getStatement();
            int n = st.executeUpdate(query);

            DAOMySQLSettings.closeStatement(st);

        } catch (SQLException e) {
            throw new DAOException("In insert(): " + e.getMessage());
        }
    }



    @Override
    public void insert(Amigos a) throws DAOException {


        verifyObject(a);


        String query = "INSERT INTO amigos (nombre, apellido, telefono, email, cumpleanos, idamigos) VALUES  ('" +
                a.getNombre() + "', '" + a.getApellido() + "', '" +
                a.getTelefono() + "', '" + a.getEmail() + "', '" +
                DateUtil.format(a.getCumpleanos()) + "', NULL)";
        try {
          logger.info("SQL: " + query);
        } catch (NullPointerException nullPointerException){
          System.out.println("SQL: " + query);
        }
        executeUpdate(query);
    }





    @Override
    public void update(Amigos a) throws DAOException {

        verifyObject(a);

        String query = "UPDATE amigos SET nombre = '" + a.getNombre() + "', apellido = '" + a.getApellido() + "',  telefono = '" + a.getTelefono() + "', email = '" + a.getEmail() + "', cumpleanos = '" + DateUtil.format(a.getCumpleanos()) + "'";
        query = query + " WHERE idamigos = " + a.getIdamigos() + ";";
        logger.info("SQL: " + query);

        executeUpdate(query);

    }

}

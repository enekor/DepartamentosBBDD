import POJO.Programador;
import db.DataBaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class App {
    DataBaseController dbc = DataBaseController.getInstance();
    public App(){
        try {
            dbc.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newDepartamento(int id,String nombre,double presupuesto,int idJefe){
        String query = "insert into POJO.Departamento(ID,nombre,presupuesto,idJefe) values(?,?,?,?)";
        try {
            dbc.insert(query,id, nombre,presupuesto,idJefe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newProgrammer(int id, String nombre, double salario, int idDep){
        String query = "insert into POJO.Programador((ID,nombre,salario,idDepartamento) values(?,?,?,?)";
        try {
            dbc.insert(query,id, nombre,salario,idDep);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectProgrammersById(int id) throws SQLException {
        String query = "select * from programador where id=?";
        Optional<ResultSet> select = dbc.select(query, id);
        if(select.get().first()){
            Programador p = new Programador();
            p.setId(select.get().getInt("ID"));
            p.setNombre(select.get().getString("nombre"));
            p.setSalario(select.get().getDouble("salario"));
            p.setTrabajo();
            //nombre salario departamento
        }
    }

    public static void main(String[] args) {
        App p = new App();
        p.newDepartamento(2,"algo",44567,1);
        p.newProgrammer(2,"Jose",1027,2);
    }
}

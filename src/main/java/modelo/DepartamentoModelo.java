package modelo;

import db.DataBaseController;
import POJO.*;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Data
public class DepartamentoModelo {

    DataBaseController dbc = DataBaseController.getInstance();
    private Map<Integer,Departamento> departamentos = new HashMap<>();

    private DepartamentoModelo(){
        try {
            dbc.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static DepartamentoModelo dep = null;
    public static DepartamentoModelo getInstance() {
        if(dep==null){
            dep = new DepartamentoModelo();
        }
        return dep;
    }


    public void newDepartamento(int id,String nombre,double presupuesto,int idJefe){
        String query = "insert into departamento(ID,nombre,presupuesto,idJefe) values(?,?,?,?)";
        try {
            dbc.insert(query,id, nombre,presupuesto,idJefe);
            if(ProgramadorModelo.getInstance().getProgramadores().containsKey(idJefe)){
                Programador lider = ProgramadorModelo.getInstance().getProgramadores().get(idJefe);//aqui
                departamentos.put(id,new Departamento().builder().id(id).nombre(nombre).presupuesto(presupuesto).lider(lider).build());
            }else System.err.println("no se puede asignar como jefe de departamento un programador no existente");

        } catch (SQLException e) {
            System.err.println("no se pudo aniadir el departamento solicitado");
        }
    }

    public void newDepartamento(Departamento departamento){
        String query = "insert into departamento(ID,nombre,presupuesto,idJefe) values(?,?,?,?)";
        try {
            dbc.insert(query,departamento.getId(), departamento.getNombre(),departamento.getPresupuesto(),departamento.getLider().getId());
            departamentos.put(departamento.getId(),departamento);
        } catch (SQLException e) {
            System.err.println("no se pudo aniadir el departamento solicitado");
        }
    }

    public void updateDepartamento(String nombre, double presupuesto, int idJefe, int idCambio){
        String query = "update departamento set nombre=?, presupuesto=?,idJefe=? where ID=?";
        try {
            dbc.update(query,nombre,presupuesto,idJefe,idCambio);
            Programador lider = new Programador();//aqui
            departamentos.remove(idCambio);
            departamentos.put(idCambio,new Departamento().builder().id(idCambio).nombre(nombre).presupuesto(presupuesto).lider(lider).build());
        } catch (SQLException e) {
            System.err.println("no se pudo cambiar el departamento con id "+idCambio);
        }
    }

    public void deleteDepartament(int id){
        String query = "delete from departamento where ID=?";
        try {
            dbc.delete(query,id);
            departamentos.remove(id);
        } catch (SQLException e) {
            System.err.println("no se pudo borrar el departamento con id "+id);
        }
    }

    public void selectById(int id){
        String query = "select * from departamento where ID=?";
        try{
            Optional<ResultSet> select = dbc.select(query, id);
            for(int i=0;i<select.stream().count();i++){
                ResultSet select2 = (ResultSet) select.get().getObject(i);
                Departamento p = new Departamento().builder().id((select2.getInt("ID"))).
                        nombre(select.get().getString("nombre")).
                        presupuesto(Double.parseDouble(select2.getString("presupuesto"))).build();
                if(ProgramadorModelo.getInstance().getProgramadores().containsKey(select2.getInt("idJefe"))){
                    p.setLider((Programador) ProgramadorModelo.getInstance().getProgramadores().get(select2.getInt("idJefe")));
                }
                departamentos.put(select.get().getInt("ID"),p);
            }
            departamentos.values().forEach(System.out::println);
        } catch (SQLException throwables) {
            System.err.println("no se ha podido obtener el listado de departamentos");
        }
    }
}

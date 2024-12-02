package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.DBQuery;

public class ProfissionalServicoDAO extends DBQuery {

    // Construtor para inicializar a tabela e campos
    public ProfissionalServicoDAO() {
        this.setTableName("profissionalServico");
        this.setFieldsName("profissionalId, servicoId");
        this.setFieldKey("profissionalId");
    }

  
    public int save(ProfissionalServico profissionalServico) {
        if (profissionalServico.getProfissionalId() >= 0) {
          
            return this.update(profissionalServico.toArray());
        } else {
          
            return this.insert(profissionalServico.toArray());
        }
    }

  
    public ResultSet select(String where) {
        return super.select(where); 
    }

   
    public ArrayList<ProfissionalServico> findAll() {
        ResultSet rs = this.select("");
        ArrayList<ProfissionalServico> list = new ArrayList<>();

        try {
            while (rs.next()) {
                ProfissionalServico profissionalServico = new ProfissionalServico();
                profissionalServico.setProfissionalId(rs.getInt("profissionalId"));
                profissionalServico.setServicoId(rs.getInt("servicoId"));
                
               
                list.add(profissionalServico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

  
    public ProfissionalServico findById(int id) {
        ProfissionalServico profissionalServico = null;

      
        try (ResultSet rs = this.select("WHERE " + this.getFieldKey() + " = " + id)) {
            if (rs != null && rs.next()) {
                
                profissionalServico = new ProfissionalServico();
                profissionalServico.setProfissionalId(rs.getInt("profissionalId"));
                profissionalServico.setServicoId(rs.getInt("servicoId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profissionalServico; 
    }
}

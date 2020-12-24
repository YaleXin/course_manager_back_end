/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Progress;
import com.yalexin.uitl.Constants;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UploadDao extends BaseDao{

    public UploadDao( ) {
        super("progress");
    }


   public int addProgressByTeamId(int teamId, Progress progress){
       System.out.println("----------addProgressByTeamId---------------");
       System.out.println(teamId);
       if (progress == null || teamId <= 0) return 0;
       int result = 0;
       try {
           connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
           String sql = "insert into " + TABLE_NAME + "(team_id,stu_id,date, content) values(?,?,?,?)";
           preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, progress.getTeam_id());
           preparedStatement.setInt(2, progress.getStu_id());
           preparedStatement.setDate(3, progress.getDate());
           preparedStatement.setString(4, progress.getContent());
           result = preparedStatement.executeUpdate();
           System.out.println("======== upload ========== preparedStatement = " + preparedStatement);
           System.out.println(result);
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           closeHelper();
           return result;
       }
   }
}

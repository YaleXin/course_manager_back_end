/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Progress;
import com.yalexin.uitl.Constants;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgressDao extends BaseDao{

    public ProgressDao( ) {
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

   public ArrayList<Progress> getAllTeamsProgressesByTeacherId(int teacherId){
        if (teacherId <= 0)return null;
//       SELECT progress.* from progress,team WHERE progress.team_id=team.id and team.id in
//       (SELECT team.id from team,`subject` WHERE team.su_id=`subject`.id and `subject`.id in
//       (SELECT `subject`.id FROM `subject`,teacher WHERE teacher.id=1));

//       SELECT progress.content,progress.date,student.`name` from progress,team,student WHERE progress.team_id=team.id and stu_id=student.id and team.id in
//       (SELECT team.id from team,`subject` WHERE team.su_id=`subject`.id and `subject`.id in
//       (SELECT `subject`.id FROM `subject`,teacher WHERE teacher.id=1));
       ArrayList<Progress> progresses = new ArrayList<>();
       try {
           connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
           String sql = "SELECT progress.content,progress.date,student.`name` from progress,team,student WHERE progress.team_id=team.id and stu_id=student.id and team.id in\n" +
                   "       (SELECT team.id from team,`subject` WHERE team.su_id=`subject`.id and `subject`.id in\n" +
                   "       (SELECT `subject`.id FROM `subject`,teacher WHERE subject.t_id=teacher.id and teacher.id=?))";
           preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, teacherId);
           resultSet = preparedStatement.executeQuery();
           System.out.println("========getAllTeamsProgressesByTeacherId ============= " + preparedStatement);
           while (resultSet.next()){
               progresses.add(getOneSimpleProgressByResult(resultSet));
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           closeHelper();
       }
       return progresses;
   }

    public ArrayList<Progress> getAllTeamsProgressesByTeamId(int teamId){
        if (teamId <= 0)return null;
        ArrayList<Progress> progresses = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "SELECT progress.date,progress.content,student.`name` FROM progress,student WHERE progress.stu_id=student.id and team_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teamId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                progresses.add(getOneSimpleProgressByResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
        }
        return progresses;
    }

    private Progress getOneSimpleProgressByResult(ResultSet resultSet) throws SQLException {
        Progress progress = new Progress();
        progress.setDate(resultSet.getDate("date"));
        progress.setContent(resultSet.getString("content"));
        progress.setUploader(resultSet.getString("name"));
        return progress;
    }

}

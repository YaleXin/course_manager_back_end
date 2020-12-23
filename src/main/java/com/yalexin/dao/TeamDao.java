/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Student;
import com.yalexin.entity.Team;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDao extends BaseDao{

    public TeamDao() {
        super("team");
    }

    public Team getTeamByOneStudnet(int id) {
        if (id <= 0)return null;
        Team team = null;
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "select * from " + TABLE_NAME + " where captain=? or member1=? or member2=? limit 1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);
            resultSet = preparedStatement.executeQuery();
            System.out.println("preparedStatement = " + preparedStatement);
            if (resultSet.next()) team = getOneTeamByResultSet(resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            closeHelper();
        }
        if (team == null)return null;
        if (team.getCap_id() == 0)return null;
        setTeam(team);
        return team;
    }

    private Team getOneTeamByResultSet(ResultSet resultSet) throws SQLException {
        Team team = new Team();
        team.setId(resultSet.getInt("id"));
        team.setCap_id(resultSet.getInt("captain"));
        team.setMem1_id(resultSet.getInt("member1"));
        team.setMem2_id(resultSet.getInt("member2"));
        team.setSu_id(resultSet.getInt("su_id"));
        if (team.getMem2_id() == 0)team.setFulled(false);
        else team.setFulled(true);
        return team;
    }

    /**
     * 设置队员各个成员名
     * @param team
     */
    private void setTeam(Team team){
        StudentDao studentDao = new StudentDao();
        Student captain = studentDao.getStudentById(team.getCap_id());

        team.setCaptain(captain.getUsername());
        if (team.getMem1_id() != 0){
            Student mem1 = studentDao.getStudentById(team.getMem1_id());
            team.setMember1(mem1.getUsername());
        }
        if (team.getMem2_id() != 0){
            Student mem2 = studentDao.getStudentById(team.getMem2_id());
            team.setMember2(mem2.getUsername());
        }
    }
}

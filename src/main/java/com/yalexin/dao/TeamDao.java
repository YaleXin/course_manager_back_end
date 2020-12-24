/**
 * Author: Yalexin
 * Email: 181303209@yzu.edu.cn
 **/
package com.yalexin.dao;

import com.yalexin.entity.Student;
import com.yalexin.entity.Teacher;
import com.yalexin.entity.Team;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamDao extends BaseDao {

    public TeamDao() {
        super("team");
    }

    public Team getTeamByOneStudnet(int id) {
        if (id <= 0) return null;
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
        if (team == null) return null;
        if (team.getCap_id() == 0) return null;
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
        team.setSubName(resultSet.getString("subName"));
        if (team.getMem2_id() == 0) team.setFulled(false);
        else team.setFulled(true);
        return team;
    }

    /**
     * 设置队员各个成员名
     *
     * @param team
     */
    private void setTeam(Team team) {
        StudentDao studentDao = new StudentDao();
        Student captain = studentDao.getStudentById(team.getCap_id());

        team.setCaptain(captain.getUsername());
        if (team.getMem1_id() != 0) {
            Student mem1 = studentDao.getStudentById(team.getMem1_id());
            team.setMember1(mem1.getUsername());
        }
        if (team.getMem2_id() != 0) {
            Student mem2 = studentDao.getStudentById(team.getMem2_id());
            team.setMember2(mem2.getUsername());
        }
    }

    public int addTeadByCaptain(int captainId, int[] members, int subject) {
        if (captainId <= 0) return 0;
        int result = 0, mem1 = -1, mem2 = -1;
        if (members.length >= 1) mem1 = members[0];
        if (members.length >= 2) mem2 = members[1];
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
            String sql = "";
            if (mem1 == -1) {
                sql = "insert into " + TABLE_NAME + "(captain,su_id) values(?,?)";
                preparedStatement.setInt(1, captainId);
                preparedStatement.setInt(2, subject);
            } else if (mem2 == -1) {
                sql = "insert into " + TABLE_NAME + "(captain,member1, su_id) values(?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, captainId);
                preparedStatement.setInt(2, mem1);
                preparedStatement.setInt(3, subject);
            } else {
                sql = "insert into " + TABLE_NAME + "(captain,member1,member2, su_id) values(?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, captainId);
                preparedStatement.setInt(2, mem1);
                preparedStatement.setInt(3, mem2);
                preparedStatement.setInt(4, subject);
            }
            System.out.println("preparedStatement = " + preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeHelper();
            return result;
        }
    }


    public ArrayList<Team> getTeamsByTeacherId(int teacherId) {
        if (teacherId <= 0) return null;
        ArrayList<Team> teams = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(URL + EXTRA_PARAMETER, USERNAME, PASSWORD);
//            String sql = "SELECT team.*,`subject`.`name` as subName from team,teacher,subject WHERE team.su_id=`subject`.id and `subject`.t_id=teacher.id and team.approved=0 and teacher.id=555;";
            String sql = "SELECT team.*,`subject`.`name` as subName  from team,teacher,subject WHERE team.su_id=`subject`.id and `subject`.t_id=teacher.id and team.approved=0 and teacher.id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacherId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Team team = getOneTeamByResultSet(resultSet);
                setTeam(team);
                teams.add(team);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            closeHelper();
        }
        return teams;
    }
}

package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface GroupMapper {

    @Insert("INSERT INTO `group` (name, room, schoolid) VALUES (#{group.name}, #{group.room}, #{school.id})")
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    void insert(@Param("school") School school, @Param("group") Group group);

    @Update("UPDATE `group` SET name = #{name}, room = #{room} WHERE id = #{id}")
    void update(Group group);

    @Select("SELECT id, name, room FROM `group`")
    List<Group> getAll();

    @Select("SELECT id, name, room FROM `group` WHERE schoolid = #{school.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup", fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup", fetchType = FetchType.LAZY))
    })
    List<Group> getBySchool(@Param("school") School school);

    @Delete("DELETE FROM `group` WHERE id = #{id}")
    void delete(Group group);

    @Update("UPDATE trainee SET groupid = #{group.id} WHERE id = #{trainee.id}")
    void moveTraineeToGroup(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Update("UPDATE trainee SET groupid = null WHERE id = #{id}")
    void deleteTraineeFromGroup(Trainee trainee);

    @Insert("INSERT INTO group_subject (groupid, subjectid) VALUES (#{group.id}, #{subject.id})")
    void addSubjectToGroup(@Param("group") Group group, @Param("subject") Subject subject);
}

package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TraineeMapper {

    @Insert("INSERT INTO trainee (firstName, lastName, rating, groupid) VALUES (#{trainee.firstName}, #{trainee.lastName}," +
            "#{trainee.rating}, #{group.id})")
    @Options(useGeneratedKeys = true, keyProperty = "trainee.id")
    Integer insert(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Select("SELECT id, firstName, lastName, rating FROM trainee WHERE id = #{id}")
    Trainee getById(int id);

    @Select("SELECT id, firstName, lastName, rating FROM trainee WHERE groupid = #{group.id}")
    List<Trainee> getByGroup(Group group);

    @Select("SELECT id, firstName, lastName, rating FROM trainee")
    List<Trainee> getAll();

    @Update("UPDATE trainee SET firstName = #{firstName}, lastName = #{lastName}, rating = #{rating} WHERE id = #{id}")
    void update(Trainee trainee);

    @Select({"<script>",
                "SELECT id, firstName, lastName, rating FROM trainee",
                "<where>",
                    "<if test = 'firstName != null'> firstName like #{firstName}",
                    "</if>",
                    "<if test = 'lastName != null'> AND lastName like #{lastName}",
                    "</if>",
                    "<if test = 'rating != null'> AND rating like #{rating}",
                    "</if>",
                "</where>",
            "</script>"})
    List<Trainee> getAllWithParams(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                   @Param("rating") Integer rating);

    @Insert({"<script>",
            "INSERT INTO trainee (firstName, lastName, rating) VALUES",
            "<foreach item = 'item' collection = 'list' separator = ','>",
            "( #{item.firstName}, #{item.lastName}, #{item.rating} )",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "list.id")
    void batchInsert(@Param("list") List<Trainee> trainees);

    @Delete("DELETE FROM trainee WHERE id = #{id}")
    void delete(Trainee trainee);

    @Delete("DELETE FROM trainee")
    void deleteAll();
}

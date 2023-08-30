package com.dream.exerciseSystem.dao;


import com.dream.exerciseSystem.domain.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IStudentDao {
    // #{id}中id可以是方法参数（如下面的id），也可以说方法参数的一个属性（如下面的stu.id）
    @Insert("INSERT INTO student_test2 (name, email, password) VALUES(#{name}, #{email}, #{password})")
    Boolean createOne(Student stu);

    @Select("SELECT * FROM student_test2 where name = #{name}")
    Student retrieveOneByName(String name);

    @Select("SELECT * FROM student_test2 where email = #{email}")
    Student retrieveOneByEmail(String email);

    @Select("SELECT * FROM student_test2")
    List<Student> retrieveAll();

//    @Update("UPDATE student_test2 SET name=#{name}, password=#{password} WHERE id=#{id}")
//    Boolean updateOne(Student stu);
}

package com.dream.exerciseSystem.elasticSearch;

import co.elastic.clients.json.JsonData;
import com.dream.exerciseSystem.domain.ExerciseVector;
import com.dream.exerciseSystem.domain.UserVector;
import com.dream.exerciseSystem.mapper.ExerciseVectorRepository;
import com.dream.exerciseSystem.mapper.UserVectorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;

@SpringBootTest
public class EStest {

//    @Resource
//    private VectorService vectorService;
    @Resource
    private UserVectorRepository userVectorRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ExerciseVectorRepository exerciseVectorRepository;


    @Test
    void insertDataTestFunctionFromESTemplate(){
        float[] array = new float[800];
                // 创建一个Random对象
        Random random = new Random();

        // 填充数组
        for (int i = 0; i < 800; i++) {
            array[i] = random.nextFloat();
        }
        ExerciseVector vectorObject = new ExerciseVector();
        vectorObject.setId("12345671");
        vectorObject.setVector(array);
        vectorObject.setExerciseType("MATH");
        elasticsearchTemplate.save(vectorObject);
    }

    @Test
    void insertDataFunctionFromESRepository(){
        float[] array = new float[2];
        // 创建一个Random对象
        Random random = new Random();

        // 填充数组
        for (int i = 0; i < 2; i++) {
            array[i] = random.nextFloat();
        }
        UserVector userVector = new UserVector();
        userVector.setId("123458");
//        userVector.setName("xzy3");
        userVector.setVector(array);
        userVectorRepository.save(userVector);
    }

    @Test
    void searchDataById(){
        String id = "123456";
        Criteria criteria = new Criteria("id").is(id);
        Query query = new CriteriaQuery(criteria);
        UserVector result = elasticsearchTemplate.searchOne(query, UserVector.class).getContent();
        //System.out.println(Arrays.toString(result.getVector()));
    }

    @Test
    void searchDataBySimilarity(){
        float[] array = new float[800];
        // 创建一个Random对象
        Random random = new Random();

        // 填充数组
        for (int i = 0; i < 800; i++) {
            array[i] = random.nextFloat();
        }
//        List<UserVector> list = userVectorRepository.findBySimilar(JsonData.of(array).toJson(new JacksonJsonpMapper()).toString());
//        for(UserVector i:list){
//            System.out.println(i.getId());
//        }
        List<ExerciseVector> list = exerciseVectorRepository.findDissimilarJAVAExercise(JsonData.of(array).toJson(new JacksonJsonpMapper()).toString());
        for(ExerciseVector i:list){
            System.out.println(i.getId());
        }
    }
}

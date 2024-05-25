package com.dream.exerciseSystem.elasticSearch;

import co.elastic.clients.json.JsonData;
import com.dream.exerciseSystem.domain.ExerciseVector;
import com.dream.exerciseSystem.domain.UserVector;
import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.mapper.ExerciseVectorRepository;
import com.dream.exerciseSystem.mapper.UserVectorRepository;
import com.dream.exerciseSystem.service.RecommendService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.Arrays;
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

    @Resource
    private RecommendService recommendService;


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
        String id = "fdbe03f3222e356041df2b64a25c12b7";
        Criteria criteria = new Criteria("id").is(id);
        Query query = new CriteriaQuery(criteria);
        ExerciseVector result = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();

    }

    @Test
    void searchDataBySimilarity(){
        float[] array = new float[64];
        // 创建一个Random对象
        Random random = new Random();

        //float[] arr1 = {0.044141296, 0.0128730675, -0.025063947, -0.0037574724, 0.002702995, 4.718969E-4, 0.075147, 0.013112671, -0.006765335, 0.023113199, 0.036083937, 0.016143253, 0.030497361, 0.055799242, -0.010880067, 0.0034864738, 0.010396623, -0.05410107, -0.003004545, -0.014718199, 0.05443052, -0.020293247, 0.011128775, 0.02887389, 0.024667365, 0.0023678173, 0.02908059, 0.013504562, -0.049611807, 0.00956995, 0.057712127, 0.016269382, 0.01601155, -0.006104889, 0.0020536801, 0.01852377, 0.0018690645, 0.0040918975, 0.016906036, -0.012535234, -0.0063840766, 0.0033474462, 0.05568159, 0.020708712, -0.0022645476, -0.012846052, -0.0578119, -0.045343943, -0.04923254, 0.038791493, -0.017638711, 4.710171E-4, 0.03626315, 0.007077616, -0.032419007, 0.0014079838, -0.024098434, 0.021123441, 0.035773948, -0.018597465, -0.0032419595, 0.029823683, 0.03398994, -0.10336962};

        String id = "dbb936fe8417aa408b6157df99d88e12";
        Criteria criteria = new Criteria("id").is(id);
        Query query = new CriteriaQuery(criteria);
        ExerciseVector result = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
        float[] arr2 = result.getVector();
        System.out.println(Arrays.toString(arr2));
        // 填充数组
        for (int i = 0; i < 64; i++) {
            array[i] = random.nextFloat();
        }
//        List<UserVector> list = userVectorRepository.findBySimilar(JsonData.of(array).toJson(new JacksonJsonpMapper()).toString());
//        for(UserVector i:list){
//            System.out.println(i.getId());
//        }
//        List<ExerciseVector> list = exerciseVectorRepository.findSimilarExercise(JsonData.of(arr2).toJson(new JacksonJsonpMapper()).toString());
//        System.out.println(list.size());
//        for(ExerciseVector i:list){
//            System.out.println(i.getId());
//        }
    }

    @Test
    void defaultRecommendByUserId(){
        String studentId = "014fb3a2b39f8e9cb02aac2ec6b4ac2f";
        DataWrapper dataWrapper = recommendService.defaultRecommend(studentId);
        List<Exercise> recommendExerciseContentList = (List<Exercise>)dataWrapper.getData();
        System.out.println(recommendExerciseContentList.size());
    }
}

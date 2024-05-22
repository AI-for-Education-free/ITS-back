package com.dream.exerciseSystem.elasticSearch;

import co.elastic.clients.json.JsonData;
import com.dream.exerciseSystem.domain.UserVector;
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
    @Test
    void insertDataTestFunctionFromESTemplate(){
        float[] array = new float[800];
        float[] array1 = new float[800];
        // 创建一个Random对象
        Random random = new Random();

        // 填充数组
        for (int i = 0; i < 800; i++) {
            array[i] = random.nextFloat();
            array1[i] = random.nextFloat();
        }
        UserVector vectorObject = new UserVector();
        vectorObject.setId("123456");
        vectorObject.setVector(array);
       vectorObject.setVector(array1);
        elasticsearchTemplate.save(vectorObject);
    }

    @Test
    void insertDataFunctionFromESRepository(){
        float []arr = {1,10};
        UserVector userVector = new UserVector();
        userVector.setId("123459");
//        userVector.setName("xzy3");
//        vectorObject.setVector(arr);
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
        float[] arr = new float[]{1,6};
        List<UserVector> list = userVectorRepository.findBySimilar(JsonData.of(arr).toJson(new JacksonJsonpMapper()).toString());
        //
    }
}

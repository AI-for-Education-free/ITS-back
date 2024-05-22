package com.dream.exerciseSystem.service.impl;

import com.dream.exerciseSystem.domain.UserVector;
import com.dream.exerciseSystem.mapper.UserVectorRepository;
import com.dream.exerciseSystem.service.VectorService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VectorServiceImpl implements VectorService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @Resource
    private UserVectorRepository userVectorRepository;

    @Override
    public DataWrapper searchIdenticalVec(String id) {
        //Get the vector from the ES by id
        Criteria criteria = new Criteria("id").is(id);
        Query query = new CriteriaQuery(criteria);
        UserVector userVector = elasticsearchTemplate.searchOne(query, UserVector.class).getContent();
        //float[] vectorInput = vectorObject.getVector();

        //List<VectorObject> list = vectorRepository.findBySimilar(JsonData.of(vectorInput).toJson(new JacksonJsonpMapper()).toString());
        //return new DataWrapper(true).msgBuilder("success").dataBuilder(list.iterator().next().getVector());
        return null;
    }
}

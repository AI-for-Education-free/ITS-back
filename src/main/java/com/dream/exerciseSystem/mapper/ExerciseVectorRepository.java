package com.dream.exerciseSystem.mapper;

import com.dream.exerciseSystem.domain.ExerciseVector;
import com.dream.exerciseSystem.domain.UserVector;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ExerciseVectorRepository extends ElasticsearchRepository<ExerciseVector, String> {
    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"JAVA\"}}," +
            "       \"script\": {"+
            "           \"source\": \"cosineSimilarity(params.queryVector, 'vector') +0.1\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarJAVAExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"MATH\"}}," +
            "       \"script\": {"+
            "           \"source\": \"cosineSimilarity(params.queryVector, 'vector') +0.1\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarMathExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"JAVA\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = cosineSimilarity(params.queryVector, 'vector') +0.1; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDissimilarJAVAExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"MATH\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = cosineSimilarity(params.queryVector, 'vector') +0.1, return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDissimilarMathExercise(String content);

}

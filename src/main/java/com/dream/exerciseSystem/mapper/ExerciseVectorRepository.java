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
            "           \"source\": \"(dotProduct(params.queryVector, 'vector') +1.0)*0.6\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarJAVAExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"MATH\"}}," +
            "       \"script\": {"+
            "           \"source\": \"(dotProduct(params.queryVector, 'vector') +1.0)*0.6\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarMathExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"JAVA\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = (dotProduct(params.queryVector, 'vector') +1.0)*0.6; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDissimilarJAVAExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"MATH\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = (dotProduct(params.queryVector, 'vector') +1.0)*0.6; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDissimilarMathExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"单选\"}}," +
            "       \"script\": {"+
            "           \"source\": \"(dotProduct(params.queryVector, 'vector') +1.0)*0.6\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarSingleChoiceExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"单选\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = (dotProduct(params.queryVector, 'vector') +1.0)*0.6; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDisimilarSingleChoiceExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"填空\"}}," +
            "       \"script\": {"+
            "           \"source\": \"(dotProduct(params.queryVector, 'vector') +1.0)*0.6\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarFillInExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"填空\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = (dotProduct(params.queryVector, 'vector') +1.0)*0.6; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDisimilarFillInExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"编程\"}}," +
            "       \"script\": {"+
            "           \"source\": \"(dotProduct(params.queryVector, 'vector') +1.0)*0.6\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findSimilarJavaProgramExercise(String content);

    @Query("{" +
            "    \"script_score\": {" +
            "       \"query\": {\"match\": {\"exerciseType\":\"编程\"}}," +
            "       \"script\": {"+
            "           \"source\": \"double result = (dotProduct(params.queryVector, 'vector') +1.0)*0.6; return 1/result\","+
            "           \"params\": {\"queryVector\": ?0}"+
            "       }"+
            "   }"+
            "}")
    List<ExerciseVector> findDisimilarJavaProgramExercise(String content);
}

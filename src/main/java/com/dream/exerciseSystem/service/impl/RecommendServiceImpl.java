package com.dream.exerciseSystem.service.impl;

import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.domain.ExerciseVector;
import com.dream.exerciseSystem.domain.StudentAnswerRecord;
import com.dream.exerciseSystem.domain.exercise.Exercise;
import com.dream.exerciseSystem.domain.exercise.FillInExercise;
import com.dream.exerciseSystem.domain.exercise.JavaProgramExercise;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.mapper.ExerciseVectorRepository;
import com.dream.exerciseSystem.mapper.StudentAnswerRecordMapper;
import com.dream.exerciseSystem.service.RecommendService;
import com.dream.exerciseSystem.utils.DataWrapper;
import com.dream.exerciseSystem.utils.MongoUtils;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl extends ServiceImpl<StudentAnswerRecordMapper, StudentAnswerRecord> implements RecommendService {

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private StudentAnswerRecordMapper studentAnswerRecordMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ExerciseVectorRepository exerciseVectorRepository;

    @Resource
    private MongoUtils mongoUtils;

    @Override
    public DataWrapper defaultRecommend(String studentId) {
        // query the student exercise history from the db
        QueryWrapper<StudentAnswerRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", studentId).orderByDesc("answerTimestamp");
        List<StudentAnswerRecord> exerciseRecordList = studentAnswerRecordMapper.selectList(queryWrapper);
        List<StudentAnswerRecord> exerciseFalseRecordList = exerciseRecordList.stream()
                .filter(record -> record.getAnswerCorrectness() == 0)
                //.map(record -> record)
                .collect(Collectors.toList());
        List<StudentAnswerRecord> exerciseCorrectRecordList = exerciseRecordList.stream()
                .filter((record -> record.getAnswerCorrectness() == 1))
                .collect(Collectors.toList());

        // the recommendedExerciseIdList and content list is inited here
        List<Exercise> recommendExerciseContentList = new ArrayList<>();
        List<String> recommendExerciseIdList = new ArrayList<>();

        // the code below is to generate recommend exercise list
        if (exerciseFalseRecordList.isEmpty()) {
            if (exerciseCorrectRecordList.isEmpty()) {
                // if user has no exercise record, the system reccmmend random exercise for user
//                mongoTemplate.aggregate()
                recommendExerciseContentList = mongoUtils.getRandomExerciseContent();
                return new DataWrapper(true).msgBuilder("random 5 exercise contents").dataBuilder(recommendExerciseContentList);
            }
            if (exerciseCorrectRecordList.size() < 5) {
                // if user only has the correct exercise record and the number of exercise record is less than 5,
                // we recommend 5 exercise based on the exercise record
                for (int i = 0; i <= exerciseCorrectRecordList.size() - 1; i++) {
                    int questionType = exerciseCorrectRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(exerciseCorrectRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    // find the dissimilar exercise by exercise id given
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    //for the reason that the number of correct record is not sufficient, we pick the last answer record and recommend more exercise based on this record
                    if (i == exerciseCorrectRecordList.size() - 1) {
                        for (int j = 0; j <= 5 - exerciseCorrectRecordList.size(); j++) {
                            recommendExerciseIdList.add(exerciseVectorList.get(j).getId());
                        }
                    } else
                        recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
            }
            if (exerciseCorrectRecordList.size() >= 5){
                // if user only has the correct exercise record and the number of exercise record is less than 5,
                // we randomly get 5 exercise from record and recommend 5 exercise based on the exercise record

                // randomly shuffle the list and get a new list with five elements
                Collections.shuffle(exerciseCorrectRecordList);
                List<StudentAnswerRecord> randomExerciseCorrectRecordList = exerciseCorrectRecordList.subList(0, 5);

                for (int i = 0; i <= randomExerciseCorrectRecordList.size() - 1; i++) {
                    int questionType = randomExerciseCorrectRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(randomExerciseCorrectRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    //for the reason that we already have 5 wrong records, and we generate the corresponding recommend exercise for each record by find the lowest similarity
                    recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
            }
        }
        if (exerciseFalseRecordList.size() < 5) {
            if (exerciseCorrectRecordList.isEmpty()) {
                // for the reason that the user only has the wrong record and the number of records is less than 5,
                // we recommend  the corresponding exercise with the given record and continue to recommend with the last record
                for (int i = 0; i <= exerciseFalseRecordList.size() - 1; i++) {
                    int questionType = exerciseFalseRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(exerciseFalseRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    // get the highest similarity by the code below
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    //recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                    if (i == exerciseFalseRecordList.size() - 1) {
                        for (int j = 0; j <= 5 - exerciseFalseRecordList.size(); j++) {
                            recommendExerciseIdList.add(exerciseVectorList.get(j).getId());
                        }
                    } else
                        recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
            }
            if (exerciseFalseRecordList.size() + exerciseCorrectRecordList.size() < 5) {
                // correctlist and falselist number is less than 5,
                // generate the corresponding recommend exercise and continue recommend from the random chosen exercise
                for (int i = 0; i <= exerciseCorrectRecordList.size() - 1; i++) {
                    int questionType = exerciseCorrectRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(exerciseCorrectRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    // find the dissimilar exercise by exercise id given
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    //each correct record only recommend one
                    recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
                for (int i = 0; i <= exerciseFalseRecordList.size() - 1; i++) {
                    int questionType = exerciseFalseRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(exerciseFalseRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    // get the highest similarity by the code below
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    // each wrong record only recommend one
                    // because the recommend list is still less than 5, we  continue to recoomend
                    if (i == exerciseFalseRecordList.size() - 1) {
                        for (int j = 0; j <= 5 - exerciseFalseRecordList.size() - exerciseCorrectRecordList.size(); j++) {
                            recommendExerciseIdList.add(exerciseVectorList.get(j).getId());
                        }
                    } else
                        recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
            }
            if (exerciseFalseRecordList.size() + exerciseCorrectRecordList.size() >= 5) {
                // all the record is more than 5, so we first generate the corresponding recommendation for each wrong record,
                // for the right records, we randomly select the right record and  generate the corresponding recommendation
                for (int i = 0; i <= exerciseFalseRecordList.size() - 1; i++) {
                    int questionType = exerciseFalseRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(exerciseFalseRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    // get the highest similarity by the code below
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findSimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
                // shuffle the correct record list, and pick up the coresponding number
                Collections.shuffle(exerciseCorrectRecordList);
                List<StudentAnswerRecord> randomExerciseCorrectRecordList = exerciseCorrectRecordList.subList(0, 5 - exerciseFalseRecordList.size());

                for (int i = 0; i <= randomExerciseCorrectRecordList.size() - 1; i++) {
                    int questionType = randomExerciseCorrectRecordList.get(i).getQuestionType();
                    Criteria criteria = new Criteria("id").is(randomExerciseCorrectRecordList.get(i).getQuestionId());
                    Query query = new CriteriaQuery(criteria);
                    ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                    float[] vector = exerciseVector.getVector();
                    List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                    if (questionType == 0) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    if (questionType == 1 || questionType == 2) {
                        exerciseVectorList = exerciseVectorRepository.findDissimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                    }
                    //for the reason that we already have 5 wrong records, and we generate the corresponding recommend exercise for each record by find the lowest similarity
                    recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
                }
            }
        }
        if (exerciseFalseRecordList.size() >= 5) {
            // randomly choose 5 wrong record and generate the recommendation
            Collections.shuffle(exerciseFalseRecordList);
            List<StudentAnswerRecord> randomExerciseFalseRecordList = exerciseFalseRecordList.subList(0, 5);

            for (int i = 0; i <= randomExerciseFalseRecordList.size() - 1; i++) {
                int questionType = randomExerciseFalseRecordList.get(i).getQuestionType();
                Criteria criteria = new Criteria("id").is(randomExerciseFalseRecordList.get(i).getQuestionId());
                Query query = new CriteriaQuery(criteria);
                ExerciseVector exerciseVector = elasticsearchTemplate.searchOne(query, ExerciseVector.class).getContent();
                float[] vector = exerciseVector.getVector();
                List<ExerciseVector> exerciseVectorList = new ArrayList<>();
                if (questionType == 0) {
                    exerciseVectorList = exerciseVectorRepository.findSimilarJAVAExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                }
                if (questionType == 1 || questionType == 2) {
                    exerciseVectorList = exerciseVectorRepository.findSimilarMathExercise(JsonData.of(vector).toJson(new JacksonJsonpMapper()).toString());
                }
                //for the reason that we already have 5 wrong records, and we generate the corresponding recommend exercise for each record by find the lowest similarity
                recommendExerciseIdList.add(exerciseVectorList.get(0).getId());
            }
        }


        for (String recommendExerciseId : recommendExerciseIdList) {
            org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
            query.addCriteria(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(recommendExerciseId));
            if (mongoTemplate.exists(query, "javaProgramExercise")) {
                JavaProgramExercise result = mongoTemplate.findById(recommendExerciseId, JavaProgramExercise.class);
                recommendExerciseContentList.add(result.getExercise());
            } else if (mongoTemplate.exists(query, "javaSingleChoiceExercise")) {
                SingleChoiceExercise result = mongoTemplate.findById(recommendExerciseId, SingleChoiceExercise.class);
                recommendExerciseContentList.add(result.getExercise());
            } else if (mongoTemplate.exists(query, "fillInExercise")) {
                FillInExercise result = mongoTemplate.findById(recommendExerciseId, FillInExercise.class);
                recommendExerciseContentList.add(result.getExercise());
            }
        }

        return new DataWrapper(true).msgBuilder("return five default recommendation").dataBuilder(recommendExerciseContentList);
    }
}
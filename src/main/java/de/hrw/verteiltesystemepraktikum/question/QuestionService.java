package de.hrw.verteiltesystemepraktikum.question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Question saveQuestion(Question question);

    List<Question> getAllQuestions();

    Long deleteAllQuestions();

    Optional<Question> findQuestionById(Long id) throws QuestionNotFoundException;

    Question updateQuestionById(Question question, Long id) throws QuestionNotFoundException;

    void deleteQuestionById(Long id) throws QuestionNotFoundException;

    List<Question> updateAllQuestions(Question updatedQuestion);
}

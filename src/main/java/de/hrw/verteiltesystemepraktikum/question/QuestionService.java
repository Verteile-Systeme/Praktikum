package de.hrw.verteiltesystemepraktikum.question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    String saveQuestion(Question question);

    List<Question> getAllQuestions();

    String deleteAllQuestions();

    Optional<Question> findQuestionById(Long id) throws QuestionNotFoundException;

    String updateQuestionById(Question question, Long id) throws QuestionNotFoundException;

    String deleteQuestionById(Long id) throws QuestionNotFoundException;

    String updateAllQuestions(Question updatedQuestion);
}

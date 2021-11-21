package de.hrw.verteiltesystemepraktikum.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question saveQuestion(@Valid Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Long deleteAllQuestions() {
        Long entities = questionRepository.count();
        questionRepository.deleteAll();
        return entities;
    }

    @Override
    public Optional<Question> findQuestionById(Long id) throws QuestionNotFoundException{
        if(!questionRepository.existsById(id)){
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new QuestionNotFoundException(errorString);
        }
        return questionRepository.findById(id);
    }

    @Override
    public Question updateQuestionById(Question updatedQuestion, Long id) throws QuestionNotFoundException {
        if(!questionRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new QuestionNotFoundException(errorString);
        }
        return questionRepository.findById(id)
                .map(question -> {
                    question.setAuthor(updatedQuestion.getAuthor());
                    question.setEmail(updatedQuestion.getEmail());
                    question.setSubject(updatedQuestion.getSubject());
                    question.setContent(updatedQuestion.getContent());
                    question.setRead(updatedQuestion.isRead());
                    question.setAnswered(updatedQuestion.isAnswered());
                    return questionRepository.save(question);
                }).orElseGet(() -> questionRepository.save(updatedQuestion));
    }

    @Override
    public void deleteQuestionById(Long id) throws QuestionNotFoundException {
        if(!questionRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new QuestionNotFoundException(errorString);
        }
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> updateAllQuestions(Question updatedQuestion) {
        List<Question> updatedQuestions = new ArrayList<>();
        List<Question> allQuestions = this.getAllQuestions();
        if(!allQuestions.isEmpty()) {
            for (Question temp :
                    allQuestions) {
                updatedQuestions.add(updateQuestionById(updatedQuestion, temp.getId()));
            }
            return updatedQuestions;
        }
        return null;
    }
}

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

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public String saveQuestion(@Valid Question question) {
        questionRepository.save(question);
        return "Created successfully";
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public String deleteAllQuestions() {
        questionRepository.deleteAll();
        return "Deleted successfully";
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
    public String updateQuestionById(Question updatedQuestion, Long id) throws QuestionNotFoundException {
        if(!questionRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new QuestionNotFoundException(errorString);
        }
        questionRepository.findById(id)
                .map(question -> {
                    question.setAuthor(updatedQuestion.getAuthor());
                    question.setEmail(updatedQuestion.getEmail());
                    question.setSubject(updatedQuestion.getSubject());
                    question.setContent(updatedQuestion.getContent());
                    return questionRepository.save(question);
                }).orElseGet(() -> questionRepository.save(updatedQuestion));
        return "Updated successfully";
    }

    @Override
    public String deleteQuestionById(Long id) throws QuestionNotFoundException {
        if(!questionRepository.existsById(id)) {
            String errorString = "The specified id <" + id + "> does not exists.";
            throw new QuestionNotFoundException(errorString);
        }
        questionRepository.deleteById(id);
        return "Deleted successfully";
    }

    @Override
    public String updateAllQuestions(Question updatedQuestion) {
        List<Question> allQuestions = this.getAllQuestions();
        if(!allQuestions.isEmpty()) {
            for (Question temp :
                    allQuestions) {
                updateQuestionById(updatedQuestion, temp.getId());
            }
            return "Updated successfully";
        }
        return null;
    }
}

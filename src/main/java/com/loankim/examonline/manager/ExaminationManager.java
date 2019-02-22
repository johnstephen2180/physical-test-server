package com.loankim.examonline.manager;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.loankim.examonline.dao.ExaminationRepository;
import com.loankim.examonline.dao.LessonHistoryRepository;
import com.loankim.examonline.dao.QuestionRepository;
import com.loankim.examonline.exception.CreateExamException;
import com.loankim.examonline.om.Answer;
import com.loankim.examonline.om.Examination;
import com.loankim.examonline.om.LessonHistory;
import com.loankim.examonline.om.Question;
import com.loankim.examonline.om.Suggest;
import com.loankim.examonline.service.AutoIncrementService;

@Service
public class ExaminationManager implements InitializingBean {
	@Autowired
	private AutoIncrementService autoIncrService;
	@Autowired
	private ExaminationRepository examRepo;
	@Autowired
	private QuestionRepository questionRepo;
	@Autowired
	private LessonHistoryRepository lessonHistoryRepo;
	@Autowired
	private AccountManager accountManager;

	private Map<Long, Examination> createExamCache;
	private Map<Long, Examination> examCache;
	private Map<String, LessonHistory> userExamCache;


	@Override
	public void afterPropertiesSet() throws Exception {
		createExamCache = new HashMap<>();
		examCache = new HashMap<>();
		userExamCache = new HashMap<>();
	}


	public Examination getExam(long examId) {
		Examination exam = examCache.get(examId);
		if (exam == null) {
			exam = examRepo.findOne(examId);
			List<Question> questions = questionRepo.findQuestionByExamId(examId);
			exam.setQuestionList(questions);
			examCache.put(exam.getId(), exam);
		}

		return exam;
	}


	public Page<Examination> getExam(int page) {
		return examRepo.findAll(new PageRequest(page, 5, new Sort(Sort.Direction.DESC, "id")));
	}


	public Examination createExamination(long userId, String title, InputStream questionFile, InputStream suggestFile,
			InputStream answerFile) throws CreateExamException {

		Examination exam = new Examination();

		Map<Integer, Answer> answerMap = new HashMap<>();
		int readAtSlide = 1;
		try (XMLSlideShow answerPPT = new XMLSlideShow(answerFile)) {
			for (XSLFSlide xslfSlide : answerPPT.getSlides()) {
				String slideTitle = xslfSlide.getTitle().trim();
				String[] items = StringUtils.split(slideTitle, "#");
				int answerOrder = Integer.parseInt(items[0]);
				Float result = null;
				if (items.length >= 2) {
					String value = items[1].replace(",", ".");
					result = Float.parseFloat(value);
				}

				Answer answer = answerMap.get(answerOrder);
				if (answer == null) {
					answer = new Answer(xslfSlide, answerOrder, result);
					answerMap.put(answerOrder, answer);
				} else {
					answer.addSlide(xslfSlide);
				}
				readAtSlide++;
			}

		} catch (Exception e) {
			throw CreateExamException.answerSlideExpception(readAtSlide);
		}

		int questionSize = answerMap.size();

		Map<Integer, List<Suggest>> suggestMap = new HashMap<>();
		try (XMLSlideShow suggestPPT = new XMLSlideShow(suggestFile)) {
			suggestMap = suggestPPT.getSlides().stream().map(slide -> {
				Suggest suggest = new Suggest();
				String suggestCode = slide.getTitle().trim();
				String[] items = StringUtils.split(suggestCode, "#");
				suggest.setQuestionOrder(Integer.parseInt(items[0]));
				suggest.setOrder(Integer.parseInt(items[1]));
				suggest.setSlide(slide);
				return suggest;
			}).collect(Collectors.groupingBy(Suggest::getQuestionOrder));

		} catch (Exception e) {
			throw new CreateExamException();
		}

		if (suggestMap.size() < questionSize)
			throw new CreateExamException();

		List<Question> questionList = new ArrayList<>();

		// getting the dimensions and size of the slide
		try (XMLSlideShow questionPPT = new XMLSlideShow(questionFile)) {
			List<XSLFSlide> questionSlideList = questionPPT.getSlides();
			for (int i = 0; i < questionSlideList.size(); i++) {
				int questionOrder = i + 1;
				Question question = new Question();
				question.setOrder(questionOrder);
				question.setAnswer(answerMap.get(questionOrder));
				question.setSuggestList(suggestMap.get(questionOrder));
				question.setSlide(questionSlideList.get(i));
				questionList.add(question);
			}
		} catch (Exception e) {
			throw new CreateExamException();
		}

		if (questionList.size() < questionSize)
			throw new CreateExamException();

		exam.setTitle(title);
		exam.setAccountId(userId);
		exam.setCreateTime(System.currentTimeMillis());
		exam.setQuestionList(questionList);

		createExamCache.put(userId, exam);
		return exam;
	}


	public Examination commitExamination(long userId) {
		Examination exam = createExamCache.get(userId);
		long examId = autoIncrService.genExamId();
		exam.setId(examId);
		exam.getQuestionList().stream().forEach(question -> {
			question.setExamId(examId);
			question.setId(autoIncrService.genQuestionId());
		});

		String folder = "images/" + String.valueOf(System.currentTimeMillis());
		File directory = new File(folder);
		if (!directory.exists())
			directory.mkdir();
		exam.genContent(folder);

		examRepo.save(exam);
		questionRepo.save(exam.getQuestionList());

		return exam;
	}


	public Question getQuestion(long questionId) {
		return questionRepo.findOne(questionId);
	}


	public boolean trackQuestion(long accountId, long examId, int questionOrder, int suggestOrder, boolean isRight) {
		String historyId = LessonHistory.genId(accountId, examId);
		LessonHistory lessonHistory = userExamCache.get(historyId);
		if (lessonHistory == null) {
			lessonHistory = lessonHistoryRepo.findOne(historyId);
			if (lessonHistory == null)
				lessonHistory = new LessonHistory(accountId, examId);

			lessonHistory.setExamination(getExam(examId));
			userExamCache.put(historyId, lessonHistory);
		}

		lessonHistory.updateQuestionTrack(questionOrder, suggestOrder, isRight);
		lessonHistoryRepo.save(lessonHistory);
		return true;
	}


	public Page<LessonHistory> getExamByAccountId(long accountId, int page) {
		Page<LessonHistory> lessionHistoryPage = lessonHistoryRepo.getLessionHistoryByAccountId(accountId,
				new PageRequest(page, 5, new Sort(Sort.Direction.DESC, "startTime")));
		lessionHistoryPage.getContent().stream().forEach(lesson -> lesson.setExamination(getExam(lesson.getExamId())));
		return lessionHistoryPage;
	}


	public LessonHistory getLessonHistory(String historyId) {
		LessonHistory lessonHistory = lessonHistoryRepo.findOne(historyId);
		lessonHistory.setExamination(getExam(lessonHistory.getExamId()));
		lessonHistory.setAccount(accountManager.getAccount(lessonHistory.getAccountId()));
		return lessonHistory;
	}


	public Page<LessonHistory> getLessonHistory(int page) {
		Page<LessonHistory> lessionHistoryPage = lessonHistoryRepo
				.getLessionHistory(new PageRequest(page, 5, new Sort(Sort.Direction.DESC, "startTime")));
		lessionHistoryPage.getContent().stream().forEach(lesson -> {
			lesson.setExamination(getExam(lesson.getExamId()));
			lesson.setAccount(accountManager.getAccount(lesson.getAccountId()));
		});
		return lessionHistoryPage;
	}

}

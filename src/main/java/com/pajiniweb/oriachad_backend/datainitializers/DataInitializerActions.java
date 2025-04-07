//package com.pajiniweb.oriachad_backend.datainitializers;
//
//
//import com.pajiniweb.oriachad_backend.actions.domains.entities.Action;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionType;
//import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
//import com.pajiniweb.oriachad_backend.admin.domains.entities.AdminTechnicalCard;
//import com.pajiniweb.oriachad_backend.admin.repositories.AdminTechnicalCardRepository;
//import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
//import com.pajiniweb.oriachad_backend.repositories.LevelRepository;
//import com.pajiniweb.oriachad_backend.repositories.TechnicalCardCategoryRepository;
//import com.pajiniweb.oriachad_backend.repositories.TechnicalCardRepository;
//import com.pajiniweb.oriachad_backend.settings.configuration.AppSettings;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component()
//@Order(2)
//@AllArgsConstructor
//@Transactional
//public class DataInitializerActions implements ApplicationRunner {
//
//
//	private final AppSettings appSettings;
//	private final LevelRepository levelRepository;
//
//	private final TechnicalCardCategoryRepository technicalCardCategoryRepository;
//	private final ActionRepository actionRepository;
//	private final TechnicalCardRepository technicalCardRepository;
//	private final AdminTechnicalCardRepository adminTechnicalCardRepository;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		//technicalCardCategoryRepository.
//		//	initTaskTCR001();
//		//	initTaskTCP001();
//
//		//initTaskTCG001();
//		//initTaskTCG002();
//		initTaskTCE002();
//	}
//
//	private void initTaskTCE002() {
//		List<Action> newActions = new ArrayList<>();
//		Action action1 = Action.builder().title("إجراء تحليل و استثمار نتائج الماضي الدراسي في نشاطات التوجيه والإرشاد للسنة الأولى الثانية و الثالثة ثانوي").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("interview.create").build();
//		newActions.add(action1);
//		newActions = actionRepository.saveAll(newActions);
//		List<Action> finalNewActions = newActions;
//
//		Optional<AdminTechnicalCard> optionalTechnicalCard = adminTechnicalCardRepository.findByCode("TCE-002");
//		optionalTechnicalCard.ifPresent(adminTechnicalCard -> {
//			adminTechnicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//			adminTechnicalCardRepository.save(adminTechnicalCard);
//		});
//
//	}
//
//
//	private void initTaskTCG001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action1 = Action.builder().title("إجراء مقابلات إرشادية ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("interview.create").build();
//		newActions.add(action1);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		Optional<AdminTechnicalCard> optionalTechnicalCard = adminTechnicalCardRepository.findByCode("TCG-001");
//		optionalTechnicalCard.ifPresent(adminTechnicalCard -> {
//			adminTechnicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//			adminTechnicalCardRepository.save(adminTechnicalCard);
//		});
//
//	}
//	private void initTaskTCG002() {
//		List<Action> newActions = new ArrayList<>();
//		Action action1 = Action.builder().title("إستكشاف حالات المتابعة الإرشادية و التكفل النفسي و التربوي بالتلاميذ ").type(ActionType.manual).resultType(ActionResultType.file).description("").resultValue("followup.create").build();
//		newActions.add(action1);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		Optional<AdminTechnicalCard> optionalTechnicalCard = adminTechnicalCardRepository.findByCode("TCE-002");
//		optionalTechnicalCard.ifPresent(adminTechnicalCard -> {
//			adminTechnicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//			adminTechnicalCardRepository.save(adminTechnicalCard);
//		});
//
//	}
//	private void initTaskTCP001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action1 = Action.builder().title("طباعة جدول الإحصاءات العامة للمؤسسة ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.general").build();
//		Action action2 = Action.builder().title("طباعة أوقات فراغ التلاميذ ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.breaks").build();
//		Action action3 = Action.builder().title("طباعة قائمة الأساتذة الرئيسيين ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.mains").build();
//		Action action4 = Action.builder().title("طباعة قائمة الأساتذة منسقي المواد ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.coordinators").build();
//		Action action5 = Action.builder().title("طباعة أوقات فراغ الاساتذة ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.breaks").build();
//		Action action6 = Action.builder().title("طباعة قائمة التلاميذ المعوزين  ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.needs").build();
//		Action action7 = Action.builder().title(" طباعة قائمة التلاميذ ذوي الأمراض المزمنة").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.diseases").build();
//		Action action8 = Action.builder().title("طباعة قائمة التلاميذ الأيتام").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.orphans").build();
//		Action action9 = Action.builder().title("طباعة الحزمة الكاملة للبطاقات التقنية ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.technical-cards").build();
//		Action action10 = Action.builder().title(" طباعة البرنامج السنوي").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.year-program").build();
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions.add(action4);
//		newActions.add(action5);
//		newActions.add(action6);
//		newActions.add(action7);
//		newActions.add(action8);
//		newActions.add(action9);
//		newActions.add(action10);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		List<TechnicalCard> technicalCards = technicalCardRepository.findByCode("TCP-001");
//
//		technicalCards.forEach((technicalCard) -> {
//			if (technicalCard.getActions().isEmpty()) {
//				technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//				technicalCardRepository.save(technicalCard);
//			}
//
//		});
//
//	}
//
//	private void initTaskTCR001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action1 = Action.builder().title("تحميل نموذج استيراد للمؤسسة ").type(ActionType.manual).resultType(ActionResultType.file).description("هذا النمودج يتك ملئه بالبيانات اللازمة يساعدك على استيرا البيانات العامة للتلاميذ إلى المنصة دون عناء تسجيلهم شريطو عدم إحداث اي نوع من التغير على الملف").resultValue("student.template.file.download").build();
//		Action action2 = Action.builder().title("تحميل نموذج استيراد البيانات التربوية للتلاميذ للسنة الدراسية السابقة").type(ActionType.manual).dependencies(Set.of(action1)).resultType(ActionResultType.file).description("هذا النمودج يتك ملئه بالبيانات اللازمة يساعدك على استيرا البيانات التربوية للتلاميذ إلى المنصة دون عناء تسجيلهم شريطو عدم إحداث اي نوع من التغير على الملف").resultValue("note.template.file.download").build();
//		Action action3 = Action.builder().title("تسجيل البيانات الخصوصية للتلميذ").type(ActionType.manual).dependencies(Set.of(action1, action2)).resultType(ActionResultType.page).description("هذا الصفحة تسجيل بيانات التلاميذ الشخصية فرديا على المنصة ").resultValue("student.list").build();
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions = actionRepository.saveAll(newActions);
//		List<Action> finalNewActions = newActions;
//		List<TechnicalCard> technicalCards = technicalCardRepository.findByCode("TCR-001");
//		technicalCards.forEach((technicalCard) -> {
//			if (technicalCard.getActions().isEmpty()) {
//				technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//			}
//		});
//		technicalCardRepository.saveAll(technicalCards);
//
//	}
//
//}

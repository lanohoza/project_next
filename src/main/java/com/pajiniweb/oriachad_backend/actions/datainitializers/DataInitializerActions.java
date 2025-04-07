//package com.pajiniweb.oriachad_backend.actions.datainitializers;
//
//
//import com.pajiniweb.oriachad_backend.actions.domains.entities.Action;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
//import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionType;
//import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
//import com.pajiniweb.oriachad_backend.actions.repositories.ScriptRepository;
//import com.pajiniweb.oriachad_backend.administration.domains.entities.AdminTechnicalCard;
//import com.pajiniweb.oriachad_backend.administration.repositories.AdminTechnicalCardRepository;
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
//	private final ScriptRepository scriptRepository;
//
//	private final TechnicalCardCategoryRepository technicalCardCategoryRepository;
//	private final ActionRepository actionRepository;
//	private final AdminTechnicalCardRepository adminTechnicalCardRepository;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		initTCEM001();
//		initTCES001();
//		initTC0M001();
//		initTC0S001();
//	}
//
//
//	private void initTCEM001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action0 = Action.builder().title("تنفيذ إجراء فحص المكتسبات القبلية من خلال دراسة نتائج الماضي الدراسي" )
//				.type(ActionType.script)
//				.resultType(ActionResultType.script)
//				.script(scriptRepository.findByCode("TCE002").orElseThrow())
//				.description("فحص المكتسبات القبلية من خلال دراسة نتائج الماضي الدراسي لتلاميذ  " )
//				.build();
//		Action action1 = Action.builder().title("معاينة نتائج الإجراء على سجل المتابعة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.description("عرضهم  على سجل المتابعة للمتحصلين على اقل من 10 بوضعية للمتابعة او للتنفيذ" )
//				.dependencies(Set.of(action0))
//				.resultValue("interviews").build();
//		Action action2 = Action.builder().title("معاينة نتائج الإجراء على سجل المقابلات" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرضهم  على سجل المقابلات  اقل من 10 في باب المقابلات المبرمجة بوضعية  للمقابلة بدون تاريخ " )
//				.resultValue("followups").build();
//		Action action3 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتلميذ" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تلميذ وعرض بطاقة التلميذ عند المجال المكتوب فيه نتيجة الماضي الدراسي " )
//				.resultValue("cards.student").build();
//		Action action4 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للقسم" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن قسم وعرض بطاقة قسم عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.classe").build();
//
//		Action action5 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمادة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض صفحة المادة عند مجال نتائج هذا السكريبت" )
//				.resultValue("cards.subject").build();
//		Action action6 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتخصص" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تخصص وعرض بطاقة تخصص عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.speciality").build();
//		Action action7 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمستوى" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن مستوى وعرض بطاقة مستوى عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		Action action8 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمؤسسة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن المؤسسة وعرض بطاقة المؤسسة  عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		newActions.add(action0);
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions.add(action4);
//		newActions.add(action5);
//		newActions.add(action6);
//		newActions.add(action7);
//		newActions.add(action8);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		AdminTechnicalCard technicalCard = adminTechnicalCardRepository.findByCode("TCEM-001").orElseThrow();
//		technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//		adminTechnicalCardRepository.save(technicalCard);
//
//
//	}
//	private void initTCES001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action0 = Action.builder().title("تنفيذ إجراء فحص المكتسبات القبلية من خلال دراسة نتائج الماضي الدراسي" )
//				.type(ActionType.script)
//				.resultType(ActionResultType.script)
//				.script(scriptRepository.findByCode("TCE002").orElseThrow())
//				.description("فحص المكتسبات القبلية من خلال دراسة نتائج الماضي الدراسي لتلاميذ  " )
//				.build();
//		Action action1 = Action.builder().title("معاينة نتائج الإجراء على سجل المتابعة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.description("عرضهم  على سجل المتابعة للمتحصلين على اقل من 10 بوضعية للمتابعة او للتنفيذ" )
//				.dependencies(Set.of(action0))
//				.resultValue("interviews").build();
//		Action action2 = Action.builder().title("معاينة نتائج الإجراء على سجل المقابلات" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرضهم  على سجل المقابلات  اقل من 10 في باب المقابلات المبرمجة بوضعية  للمقابلة بدون تاريخ " )
//				.resultValue("followups").build();
//		Action action3 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتلميذ" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تلميذ وعرض بطاقة التلميذ عند المجال المكتوب فيه نتيجة الماضي الدراسي " )
//				.resultValue("cards.student").build();
//		Action action4 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للقسم" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن قسم وعرض بطاقة قسم عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.classe").build();
//
//		Action action5 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمادة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض صفحة المادة عند مجال نتائج هذا السكريبت" )
//				.resultValue("cards.subject").build();
//		Action action6 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتخصص" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تخصص وعرض بطاقة تخصص عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.speciality").build();
//		Action action7 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمستوى" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن مستوى وعرض بطاقة مستوى عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		Action action8 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمؤسسة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن المؤسسة وعرض بطاقة المؤسسة  عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		newActions.add(action0);
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions.add(action4);
//		newActions.add(action5);
//		newActions.add(action6);
//		newActions.add(action7);
//		newActions.add(action8);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		AdminTechnicalCard technicalCard = adminTechnicalCardRepository.findByCode("TCES-001").orElseThrow();
//		technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//		adminTechnicalCardRepository.save(technicalCard);
//
//	}
//
//	private void initTC0M001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action0 = Action.builder().title("تنفيذ إجراء عملية التوجيه التدريجي بناءا على نتائج الفصل الأول " )
//				.type(ActionType.script)
//				.resultType(ActionResultType.script)
//				.script(scriptRepository.findByCode("TCO001").orElseThrow())
//				.description("إجراء عملية التوجيه التدريجي بناءا على نتائج الفصل" )
//				.build();
//		Action action1 = Action.builder().title("معاينة نتائج الإجراء على سجل المتابعة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.description("عرضهم  على سجل المتابعة للمتحصلين على اقل من 10 بوضعية للمتابعة او للتنفيذ" )
//				.dependencies(Set.of(action0))
//				.resultValue("interviews").build();
//		Action action2 = Action.builder().title("معاينة نتائج الإجراء على سجل المقابلات" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرضهم  على سجل المقابلات  اقل من 10 في باب المقابلات المبرمجة بوضعية  للمقابلة بدون تاريخ " )
//				.resultValue("followups").build();
//		Action action3 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتلميذ" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تلميذ وعرض بطاقة التلميذ عند المجال المكتوب فيه نتيجة الماضي الدراسي " )
//				.resultValue("cards.student").build();
//		Action action4 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للقسم" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن قسم وعرض بطاقة قسم عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.classe").build();
//
//		Action action5 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمادة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض صفحة المادة عند مجال نتائج هذا السكريبت" )
//				.resultValue("cards.subject").build();
//		Action action6 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتخصص" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تخصص وعرض بطاقة تخصص عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.speciality").build();
//		Action action7 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمستوى" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن مستوى وعرض بطاقة مستوى عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		Action action8 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمؤسسة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن المؤسسة وعرض بطاقة المؤسسة  عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		newActions.add(action0);
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions.add(action4);
//		newActions.add(action5);
//		newActions.add(action6);
//		newActions.add(action7);
//		newActions.add(action8);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		AdminTechnicalCard technicalCard = adminTechnicalCardRepository.findByCode("TC0M-001").orElseThrow();
//		technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//		adminTechnicalCardRepository.save(technicalCard);
//
//	}
//	private void initTC0S001() {
//		List<Action> newActions = new ArrayList<>();
//		Action action0 = Action.builder().title("تنفيذ إجراء عملية التوجيه التدريجي بناءا على نتائج الفصل الأول " )
//				.type(ActionType.script)
//				.resultType(ActionResultType.script)
//				.script(scriptRepository.findByCode("TCO001").orElseThrow())
//				.description("إجراء عملية التوجيه التدريجي بناءا على نتائج الفصل الأول" )
//				.build();
//		Action action1 = Action.builder().title("معاينة نتائج الإجراء على سجل المتابعة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.description("عرضهم  على سجل المتابعة للمتحصلين على اقل من 10 بوضعية للمتابعة او للتنفيذ" )
//				.dependencies(Set.of(action0))
//				.resultValue("interviews").build();
//		Action action2 = Action.builder().title("معاينة نتائج الإجراء على سجل المقابلات" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرضهم  على سجل المقابلات  اقل من 10 في باب المقابلات المبرمجة بوضعية  للمقابلة بدون تاريخ " )
//				.resultValue("followups").build();
//		Action action3 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتلميذ" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تلميذ وعرض بطاقة التلميذ عند المجال المكتوب فيه نتيجة الماضي الدراسي " )
//				.resultValue("cards.student").build();
//		Action action4 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للقسم" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن قسم وعرض بطاقة قسم عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.classe").build();
//
//		Action action5 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمادة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض صفحة المادة عند مجال نتائج هذا السكريبت" )
//				.resultValue("cards.subject").build();
//		Action action6 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للتخصص" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن تخصص وعرض بطاقة تخصص عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.speciality").build();
//		Action action7 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمستوى" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن مستوى وعرض بطاقة مستوى عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		Action action8 = Action.builder().title("معاينة نتائج الإجراء على البطاقة الإرشادية للمؤسسة" )
//				.type(ActionType.manual)
//				.resultType(ActionResultType.page)
//				.dependencies(Set.of(action0))
//				.description("عرض بحث عن المؤسسة وعرض بطاقة المؤسسة  عند المجال المكتوب فيه نتيجة الماضي الدراسي" )
//				.resultValue("cards.level").build();
//		newActions.add(action0);
//		newActions.add(action1);
//		newActions.add(action2);
//		newActions.add(action3);
//		newActions.add(action4);
//		newActions.add(action5);
//		newActions.add(action6);
//		newActions.add(action7);
//		newActions.add(action8);
//		newActions = actionRepository.saveAll(newActions);
//
//		List<Action> finalNewActions = newActions;
//		AdminTechnicalCard technicalCard = adminTechnicalCardRepository.findByCode("TC0S-001").orElseThrow();
//		technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
//		adminTechnicalCardRepository.save(technicalCard);
//
//	}
//
//
//}

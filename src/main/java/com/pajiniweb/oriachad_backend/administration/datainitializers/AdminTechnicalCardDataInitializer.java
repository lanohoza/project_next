package com.pajiniweb.oriachad_backend.administration.datainitializers;//package com.pajiniweb.oriachad_backend.datainitializers;

import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
import com.pajiniweb.oriachad_backend.administration.repositories.AdminTechnicalCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component()
@Order(2)
@AllArgsConstructor
@Transactional
public class AdminTechnicalCardDataInitializer implements ApplicationRunner {


	private final ActionRepository actionRepository;
	private final AdminTechnicalCardRepository adminTechnicalCardRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}

	private void initTaskTCP001() {
		/*List<Action> newActions = new ArrayList<>();
		Action action1 = Action.builder().title("طباعة جدول الإحصاءات العامة للمؤسسة ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.general").build();
		Action action2 = Action.builder().title("طباعة أوقات فراغ التلاميذ ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.breaks").build();
		Action action3 = Action.builder().title("طباعة قائمة الأساتذة الرئيسيين ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.mains").build();
		Action action4 = Action.builder().title("طباعة قائمة الأساتذة منسقي المواد ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.coordinators").build();
		Action action5 = Action.builder().title("طباعة أوقات فراغ الاساتذة ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.professors.breaks").build();
		Action action6 = Action.builder().title("طباعة قائمة التلاميذ المعوزين  ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.needs").build();
		Action action7 = Action.builder().title(" طباعة قائمة التلاميذ ذوي الأمراض المزمنة").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.diseases").build();
		Action action8 = Action.builder().title("طباعة قائمة التلاميذ الأيتام").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.statistics.students.orphans").build();
		Action action9 = Action.builder().title("طباعة الحزمة الكاملة للبطاقات التقنية ").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.technical-cards").build();
		Action action10 = Action.builder().title(" طباعة البرنامج السنوي").type(ActionType.manual).resultType(ActionResultType.page).description("").resultValue("pdf.year-program").build();
		newActions.add(action1);
		newActions.add(action2);
		newActions.add(action3);
		newActions.add(action4);
		newActions.add(action5);
		newActions.add(action6);
		newActions.add(action7);
		newActions.add(action8);
		newActions.add(action9);
		newActions.add(action10);
		newActions = actionRepository.saveAll(newActions);

		List<Action> finalNewActions = newActions;
		AdminTechnicalCard adminTechnicalCard=AdminTechnicalCard.builder()


				.build();
		//List<TechnicalCard> technicalCards = technicalCardRepository.findByCode("TCP-001");

/*		technicalCards.forEach((technicalCard) -> {
			if (technicalCard.getActions().isEmpty()) {
				technicalCard.setActions(finalNewActions.stream().collect(Collectors.toSet()));
				technicalCardRepository.save(technicalCard);
			}

		});*/

	}


}

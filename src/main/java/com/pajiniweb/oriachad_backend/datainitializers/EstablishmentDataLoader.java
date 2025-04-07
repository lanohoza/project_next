//package com.pajiniweb.oriachad_backend.datainitializers;
//
//import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
//import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
//import com.pajiniweb.oriachad_backend.services.EstablishmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class EstablishmentDataLoader implements CommandLineRunner {
//
//	@Autowired
//	private EstablishmentService establishmentService;
//
//	@Override
//	public void run(String... args) throws Exception {
//		List<Establishment> establishments = new ArrayList<>();
//		/**
//		 * establishments of Eloued
//		 * */
//		establishments.add(Establishment.builder().code("39012001").type(TypeEstablishment.middle).name("متوسطة بن باديس").build());
//		establishments.add(Establishment.builder().code("39012002").type(TypeEstablishment.middle).name("متوسطة الامير عبد القادر").build());
//		establishments.add(Establishment.builder().code("39012003").type(TypeEstablishment.middle).name("متوسطة الشهيد أحمد التجاني").build());
//		establishments.add(Establishment.builder().code("39012004").type(TypeEstablishment.middle).name("متوسطة المجاهد قابوسة محمد الصالح").build());
//		establishments.add(Establishment.builder().code("39012005").type(TypeEstablishment.middle).name("متوسطة مصباحي مصطفى").build());
//		establishments.add(Establishment.builder().code("39012006").type(TypeEstablishment.middle).name("متوسطة زوبيدي عبد القادر").build());
//		establishments.add(Establishment.builder().code("39012007").type(TypeEstablishment.middle).name("متوسطة الشهيد الأرقط الكيلاني").build());
//		establishments.add(Establishment.builder().code("39012008").type(TypeEstablishment.middle).name("متوسطة حويذق عبد الكريم").build());
//		establishments.add(Establishment.builder().code("39012009").type(TypeEstablishment.middle).name("متوسطة محمد الامين العمودي").build());
//		establishments.add(Establishment.builder().code("39012010").type(TypeEstablishment.middle).name("متوسطة المجاهد باهي علي").build());
//		establishments.add(Establishment.builder().code("39012011").type(TypeEstablishment.middle).name("متوسطة حمامة العلمي").build());
//		establishments.add(Establishment.builder().code("39012012").type(TypeEstablishment.middle).name("متوسطة الشهيد محمود شريفي").build());
//		establishments.add(Establishment.builder().code("39012013").type(TypeEstablishment.middle).name("متوسطة الشهيد جاب الله البشير").build());
//		establishments.add(Establishment.builder().code("39012014").type(TypeEstablishment.middle).name("متوسطة غندير عمر").build());
//		establishments.add(Establishment.builder().code("39012015").type(TypeEstablishment.middle).name("متوسطة هزلة المولدي").build());
//		establishments.add(Establishment.builder().code("39012016").type(TypeEstablishment.middle).name("متوسطة الوئام المدني").build());
//		establishments.add(Establishment.builder().code("39012017").type(TypeEstablishment.middle).name("متوسطة طير حسين").build());
//		establishments.add(Establishment.builder().code("39012018").type(TypeEstablishment.middle).name("متوسطة الشهيد بحير بلحسن").build());
//		establishments.add(Establishment.builder().code("39012019").type(TypeEstablishment.middle).name("متوسطة الشهيد عياشي عمر الطاهر").build());
//		establishments.add(Establishment.builder().code("39012020").type(TypeEstablishment.middle).name("متوسطة ضيف الله احمد").build());
//		establishments.add(Establishment.builder().code("39012021").type(TypeEstablishment.middle).name("متوسطة المجاهد دربال عبد القادر").build());
//		establishments.add(Establishment.builder().code("39012022").type(TypeEstablishment.middle).name("متوسطة الشهيد نيد الطاهر").build());
//		establishments.add(Establishment.builder().code("39012023").type(TypeEstablishment.middle).name("متوسطة الشهيد مسعي أحمد بالقاسم").build());
//		establishments.add(Establishment.builder().code("39012024").type(TypeEstablishment.middle).name("متوسطة الشهيد ديدي محمد").build());
//		establishments.add(Establishment.builder().code("39012025").type(TypeEstablishment.middle).name("متوسطة سبل النجاح").build());
//		establishments.add(Establishment.builder().code("39012026").type(TypeEstablishment.middle).name("متوسطة المجاهد عيدة خليفة").build());
//		establishments.add(Establishment.builder().code("39012027").type(TypeEstablishment.middle).name("متوسطة المجاهد زغيب محمد الطاهر").build());
//		establishments.add(Establishment.builder().code("39012028").type(TypeEstablishment.middle).name("متوسطة سبل المستقبل").build());
//
//		establishments.add(Establishment.builder().code("39072001").type(TypeEstablishment.middle).name("متوسطة صلوح عبد الحفيظ").build());
//		establishments.add(Establishment.builder().code("39072002").type(TypeEstablishment.middle).name("متوسطة الشيخ بلعبيدي السعيد").build());
//
//		establishments.add(Establishment.builder().code("39022001").type(TypeEstablishment.middle).name("متوسطة آل ياسر").build());
//		establishments.add(Establishment.builder().code("39022002").type(TypeEstablishment.middle).name("متوسطة 15جانفي1956").build());
//		establishments.add(Establishment.builder().code("39022003").type(TypeEstablishment.middle).name("متوسطة محمد الطاهر بوغزالة").build());
//		establishments.add(Establishment.builder().code("39022004").type(TypeEstablishment.middle).name("متوسطة الشهيد تونسي بشير").build());
//		establishments.add(Establishment.builder().code("39026201").type(TypeEstablishment.middle).name("متوسطة مدرسة الأطفال المعوقين بصريا").build());
//
//		establishments.add(Establishment.builder().code("39052001").type(TypeEstablishment.middle).name("متوسطة حسين حمادي").build());
//		establishments.add(Establishment.builder().code("39052002").type(TypeEstablishment.middle).name("متوسطة المجاهد عبد الكريم عسيله").build());
//		establishments.add(Establishment.builder().code("39052003").type(TypeEstablishment.middle).name("متوسطة عروة محمد").build());
//
//		establishments.add(Establishment.builder().code("39252001").type(TypeEstablishment.middle).name("متوسطة الشهيد عروة عبد القادر").build());
//		establishments.add(Establishment.builder().code("39252002").type(TypeEstablishment.middle).name("متوسطة الشهيد بن موسى عبد القادر").build());
//
//		establishments.add(Establishment.builder().code("39042001").type(TypeEstablishment.middle).name("متوسطة الشهيد طليبة بوراس").build());
//		establishments.add(Establishment.builder().code("39042002").type(TypeEstablishment.middle).name("متوسطة الخوارزمي").build());
//		establishments.add(Establishment.builder().code("39042003").type(TypeEstablishment.middle).name("متوسطة الشهيد عبادي عبادي").build());
//		establishments.add(Establishment.builder().code("39042004").type(TypeEstablishment.middle).name("متوسطة ناقص عبد الرحمان").build());
//		establishments.add(Establishment.builder().code("39042005").type(TypeEstablishment.middle).name("متوسطة الشهيد صوالح عليلة العيد").build());
//		establishments.add(Establishment.builder().code("39042006").type(TypeEstablishment.middle).name("متوسطة الأخوين بوصبيع").build());
//		establishments.add(Establishment.builder().code("39042007").type(TypeEstablishment.middle).name("متوسطة المجاهد رضواني عبد القادر").build());
//		establishments.add(Establishment.builder().code("39042008").type(TypeEstablishment.middle).name("متوسطة المجاهد عبادي بشير").build());
//
//		establishments.add(Establishment.builder().code("39062001").type(TypeEstablishment.middle).name("متوسطة محمد البشير الإبراهيمي").build());
//		establishments.add(Establishment.builder().code("39062002").type(TypeEstablishment.middle).name("متوسطة العلامة خليفة بن حسن").build());
//		establishments.add(Establishment.builder().code("39062003").type(TypeEstablishment.middle).name("متوسطة عبد الرحمان بن عيشة").build());
//		establishments.add(Establishment.builder().code("39062004").type(TypeEstablishment.middle).name("متوسطة الشهيد رويسي بلقاسم").build());
//		establishments.add(Establishment.builder().code("39062005").type(TypeEstablishment.middle).name("متوسطة الشهيد عربية أحمد").build());
//		establishments.add(Establishment.builder().code("39062006").type(TypeEstablishment.middle).name("متوسطة الشهيد لقريد عبد الكريم").build());
//		establishments.add(Establishment.builder().code("39062007").type(TypeEstablishment.middle).name("متوسطة الشهيد ابراهيم قارة").build());
//
//		establishments.add(Establishment.builder().code("39102001").type(TypeEstablishment.middle).name("متوسطة مصطفى بن بولعيد").build());
//		establishments.add(Establishment.builder().code("39102002").type(TypeEstablishment.middle).name("متوسطة خراز الطيب").build());
//		establishments.add(Establishment.builder().code("39102003").type(TypeEstablishment.middle).name("متوسطة الشهيد بن ناصر بوبكر").build());
//
//		establishments.add(Establishment.builder().code("39202001").type(TypeEstablishment.middle).name("متوسطة الشهيد حريز التجاني").build());
//		establishments.add(Establishment.builder().code("39202002").type(TypeEstablishment.middle).name("متوسطة المجاهد سالم العربي").build());
//
//		establishments.add(Establishment.builder().code("39082001").type(TypeEstablishment.middle).name("متوسطة عيسى مسعودي").build());
//		establishments.add(Establishment.builder().code("39082002").type(TypeEstablishment.middle).name("متوسطة الشهيد العيد الزاوي").build());
//		establishments.add(Establishment.builder().code("39082003").type(TypeEstablishment.middle).name("متوسطة الشهيد باهي عبد الرزاق").build());
//		establishments.add(Establishment.builder().code("39082004").type(TypeEstablishment.middle).name("متوسطة الشهيد العيد ضو").build());
//		establishments.add(Establishment.builder().code("39082005").type(TypeEstablishment.middle).name("متوسطة الشهيد مبارك بوضبية").build());
//		establishments.add(Establishment.builder().code("39082006").type(TypeEstablishment.middle).name("متوسطة محمد بوضياف").build());
//		establishments.add(Establishment.builder().code("39082007").type(TypeEstablishment.middle).name("متوسطة العرفجي").build());
//		establishments.add(Establishment.builder().code("39082008").type(TypeEstablishment.middle).name("متوسطة الفولية").build());
//		establishments.add(Establishment.builder().code("39086201").type(TypeEstablishment.middle).name("متوسطة مدرسة الأطفال المعوقين سمعيا").build());
//
//		establishments.add(Establishment.builder().code("39092001").type(TypeEstablishment.middle).name("متوسطة المجاهد دقعة الطاهر").build());
//		establishments.add(Establishment.builder().code("39092002").type(TypeEstablishment.middle).name("متوسطة المجاهد طويل مبارك").build());
//
//		establishments.add(Establishment.builder().code("39112001").type(TypeEstablishment.middle).name("متوسطة حامدي عمار").build());
//		establishments.add(Establishment.builder().code("39112002").type(TypeEstablishment.middle).name("متوسطة الشهيد أحمد خنوفة").build());
//		establishments.add(Establishment.builder().code("39112003").type(TypeEstablishment.middle).name("متوسطة الشهيد قاسمي بشير").build());
//		establishments.add(Establishment.builder().code("39112004").type(TypeEstablishment.middle).name("متوسطة الشهيد لسود خليفة").build());
//		establishments.add(Establishment.builder().code("39112005").type(TypeEstablishment.middle).name("متوسطة الشهيد زعبي بشير").build());
//		establishments.add(Establishment.builder().code("39112006").type(TypeEstablishment.middle).name("متوسطة الشهيد عمار لمقدم").build());
//		establishments.add(Establishment.builder().code("39112007").type(TypeEstablishment.middle).name("متوسطة المجاهد خزان الجباري").build());
//
//		establishments.add(Establishment.builder().code("39122001").type(TypeEstablishment.middle).name("متوسطة حمودي عبد الرحمان").build());
//		establishments.add(Establishment.builder().code("39122002").type(TypeEstablishment.middle).name("متوسطة الشهيد معمري عبد الرحمان").build());
//		establishments.add(Establishment.builder().code("39122003").type(TypeEstablishment.middle).name("متوسطة مناني محمد الساسي").build());
//		establishments.add(Establishment.builder().code("39122004").type(TypeEstablishment.middle).name("متوسطة رحومة صحراوي").build());
//		establishments.add(Establishment.builder().code("39122005").type(TypeEstablishment.middle).name("متوسطة المجاهد عصامي عبد العزيز").build());
//		establishments.add(Establishment.builder().code("39122006").type(TypeEstablishment.middle).name("متوسطة غريسي علوي الجيلاني").build());
//
//		establishments.add(Establishment.builder().code("39132001").type(TypeEstablishment.middle).name("متوسطة شراحي مصباح").build());
//		establishments.add(Establishment.builder().code("39132002").type(TypeEstablishment.middle).name("متوسطة ونيسي الأمين").build());
//		establishments.add(Establishment.builder().code("39132003").type(TypeEstablishment.middle).name("متوسطة الشهيد مقى عمار").build());
//		establishments.add(Establishment.builder().code("39132004").type(TypeEstablishment.middle).name("متوسطة اللبي علي بن عمارة").build());
//		establishments.add(Establishment.builder().code("39132005").type(TypeEstablishment.middle).name("متوسطة عقاب سالم").build());
//		establishments.add(Establishment.builder().code("39132006").type(TypeEstablishment.middle).name("متوسطة الشهيد لسود احمد").build());
//		establishments.add(Establishment.builder().code("39132007").type(TypeEstablishment.middle).name("متوسطة مقى عمار 2").build());
//
//		establishments.add(Establishment.builder().code("39172001").type(TypeEstablishment.middle).name("متوسطة سلطاني أحمد").build());
//		establishments.add(Establishment.builder().code("39172002").type(TypeEstablishment.middle).name("متوسطة الشهيد زربيط عبد الحفيظ").build());
//
//		establishments.add(Establishment.builder().code("39142001").type(TypeEstablishment.middle).name("متوسطة المجاهد دويم مبارك بن العربي").build());
//		establishments.add(Establishment.builder().code("39142002").type(TypeEstablishment.middle).name("متوسطة المجاهد رقيعة عبد القادر").build());
//		establishments.add(Establishment.builder().code("39142003").type(TypeEstablishment.middle).name("متوسطة قرية خماد").build());
//		establishments.add(Establishment.builder().code("39142004").type(TypeEstablishment.middle).name("متوسطة السباييس").build());
//
//		establishments.add(Establishment.builder().code("39152001").type(TypeEstablishment.middle).name("متوسطة الشهيد قويدري ابراهيم").build());
//		establishments.add(Establishment.builder().code("39152002").type(TypeEstablishment.middle).name("متوسطة المجاهد بن الصغير مسعود").build());
//		establishments.add(Establishment.builder().code("39152003").type(TypeEstablishment.middle).name("متوسطة قرية أميه الشيخ").build());
//
//		establishments.add(Establishment.builder().code("39192001").type(TypeEstablishment.middle).name("متوسطة المجاهد بلابل قويدر").build());
//		establishments.add(Establishment.builder().code("39192002").type(TypeEstablishment.middle).name("متوسطة الدويلات").build());
//
//		establishments.add(Establishment.builder().code("39162001").type(TypeEstablishment.middle).name("متوسطة بحري مختار").build());
//		establishments.add(Establishment.builder().code("39162002").type(TypeEstablishment.middle).name("متوسطة العوامر ابراهيم").build());
//		establishments.add(Establishment.builder().code("39162003").type(TypeEstablishment.middle).name("متوسطة سليمان تواتي").build());
//		establishments.add(Establishment.builder().code("39162004").type(TypeEstablishment.middle).name("متوسطة المجاهد محمد الطاهر ضو").build());
//		establishments.add(Establishment.builder().code("39162005").type(TypeEstablishment.middle).name("متوسطة المجاهد رحومة إبراهيم").build());
//
//		establishments.add(Establishment.builder().code("39182001").type(TypeEstablishment.middle).name("متوسطة بته لعبيدي").build());
//		establishments.add(Establishment.builder().code("39182002").type(TypeEstablishment.middle).name("متوسطة تواتي ابراهيم أحمد").build());
//		establishments.add(Establishment.builder().code("39182003").type(TypeEstablishment.middle).name("متوسطة حمي العرابي بن الصادق").build());
//		establishments.add(Establishment.builder().code("39182004").type(TypeEstablishment.middle).name("متوسطة المجاهد عياشي عمر نصر").build());
//		establishments.add(Establishment.builder().code("39182005").type(TypeEstablishment.middle).name("متوسطة المجاهد وقادي مسعود").build());
//		establishments.add(Establishment.builder().code("39182006").type(TypeEstablishment.middle).name("متوسطة الشهيد لبزة الحبيب").build());
//		establishments.add(Establishment.builder().code("39182007").type(TypeEstablishment.middle).name("متوسطة الشهيد وقادي خليفة").build());
//
//		establishments.add(Establishment.builder().code("39032001").type(TypeEstablishment.middle).name("متوسطة 19 مارس 1962").build());
//		establishments.add(Establishment.builder().code("39032002").type(TypeEstablishment.middle).name("متوسطة المجاهد قدة علي").build());
//		establishments.add(Establishment.builder().code("39032003").type(TypeEstablishment.middle).name("متوسطة المجاهد تامة عبد المالك").build());
//
//		establishments.add(Establishment.builder().code("39262001").type(TypeEstablishment.middle).name("متوسطة بوغزالة محمد الصالح").build());
//		establishments.add(Establishment.builder().code("39262002").type(TypeEstablishment.middle).name("متوسطة محمد الاخضر السائحي").build());
//		establishments.add(Establishment.builder().code("39262003").type(TypeEstablishment.middle).name("متوسطة المجاهد تومي الساسي").build());
//		establishments.add(Establishment.builder().code("39262004").type(TypeEstablishment.middle).name("متوسطة المجاهد عطية الهادي").build());
//		establishments.add(Establishment.builder().code("39262005").type(TypeEstablishment.middle).name("متوسطة وادي الترك").build());
//		establishments.add(Establishment.builder().code("39262006").type(TypeEstablishment.middle).name("متوسطة المجاهد بوغزالة حمد عبد الرحمان").build());
//		establishments.add(Establishment.builder().code("39263001").type(TypeEstablishment.secondary).name("ثانوية الاخوين كيرد").build());
//		establishments.add(Establishment.builder().code("39033001").type(TypeEstablishment.secondary).name("ثانوية الشهيد بوعزيز الهادي").build());
//		establishments.add(Establishment.builder().code("39183001").type(TypeEstablishment.secondary).name("ثانوية حنكة علي").build());
//		establishments.add(Establishment.builder().code("39183002").type(TypeEstablishment.secondary).name("ثانوية المجاهد لوبيري محمد - الحمادين").build());
//		establishments.add(Establishment.builder().code("39183003").type(TypeEstablishment.secondary).name("ثانوية معركة صحن الرتم 15 مارس 1955").build());
//		establishments.add(Establishment.builder().code("39163001").type(TypeEstablishment.secondary).name("ثانوية شرفي ابراهيم").build());
//		establishments.add(Establishment.builder().code("39153001").type(TypeEstablishment.secondary).name("ثانوية الشهيد بن الصغير عبد القادر").build());
//		establishments.add(Establishment.builder().code("39143001").type(TypeEstablishment.secondary).name("ثانوية دويم بشير").build());
//		establishments.add(Establishment.builder().code("39173001").type(TypeEstablishment.secondary).name("ثانوية المجاهد ضو صالح").build());
//		establishments.add(Establishment.builder().code("39133001").type(TypeEstablishment.secondary).name("ثانوية هواري بومدين").build());
//		establishments.add(Establishment.builder().code("39133002").type(TypeEstablishment.secondary).name("ثانوية غربي بشير").build());
//		establishments.add(Establishment.builder().code("39133003").type(TypeEstablishment.secondary).name("ثانوية حميداتو أحمد").build());
//		establishments.add(Establishment.builder().code("39123001").type(TypeEstablishment.secondary).name("ثانوية ديدي صالح").build());
//		establishments.add(Establishment.builder().code("39123002").type(TypeEstablishment.secondary).name("ثانوية المجاهد حليس محمد").build());
//		establishments.add(Establishment.builder().code("39123003").type(TypeEstablishment.secondary).name("ثانوية بدر الدين صالح").build());
//		establishments.add(Establishment.builder().code("39113001").type(TypeEstablishment.secondary).name("ثانوية محمد العيد آل خليفة").build());
//		establishments.add(Establishment.builder().code("39113002").type(TypeEstablishment.secondary).name("ثانوية شعباني عباس").build());
//		establishments.add(Establishment.builder().code("39113003").type(TypeEstablishment.secondary).name("ثانوية داسي خليفة").build());
//		establishments.add(Establishment.builder().code("39093001").type(TypeEstablishment.secondary).name("ثانوية دقعة علي").build());
//		establishments.add(Establishment.builder().code("39083001").type(TypeEstablishment.secondary).name("ثانوية رضواني الساسي").build());
//		establishments.add(Establishment.builder().code("39083002").type(TypeEstablishment.secondary).name("ثانوية الشهيد سعد شعباني").build());
//		establishments.add(Establishment.builder().code("39203001").type(TypeEstablishment.secondary).name("ثانوية المجاهد شوية الجباري").build());
//		establishments.add(Establishment.builder().code("39103001").type(TypeEstablishment.secondary).name("ثانوية بوضياف بوضياف").build());
//		establishments.add(Establishment.builder().code("39063001").type(TypeEstablishment.secondary).name("ثانوية هالي عبد الكريم").build());
//		establishments.add(Establishment.builder().code("39063002").type(TypeEstablishment.secondary).name("ثانوية العلامة عبد القادر الياجوري").build());
//		establishments.add(Establishment.builder().code("39063003").type(TypeEstablishment.secondary).name("ثانوية الشهيد عليه محمد").build());
//		establishments.add(Establishment.builder().code("39043001").type(TypeEstablishment.secondary).name("ثانوية مفدي زكرياء").build());
//		establishments.add(Establishment.builder().code("39043002").type(TypeEstablishment.secondary).name("ثانوية تجاني أمحمد").build());
//		establishments.add(Establishment.builder().code("39043003").type(TypeEstablishment.secondary).name("ثانوية المجاهد خوازم الطاهر").build());
//		establishments.add(Establishment.builder().code("39253001").type(TypeEstablishment.secondary).name("ثانوية الشهيد باهي الطاهر").build());
//		establishments.add(Establishment.builder().code("39053001").type(TypeEstablishment.secondary).name("ثانوية الشهيد علي بن حمده").build());
//		establishments.add(Establishment.builder().code("39023001").type(TypeEstablishment.secondary).name("ثانوية كركوبية خليفة").build());
//		establishments.add(Establishment.builder().code("39023002").type(TypeEstablishment.secondary).name("ثانوية لقرع محمد الضيف").build());
//		establishments.add(Establishment.builder().code("39073001").type(TypeEstablishment.secondary).name("ثانوية حفيان محمد العيد").build());
//		establishments.add(Establishment.builder().code("39073002").type(TypeEstablishment.secondary).name("ثانوية صنديد محمد منيب").build());
//		establishments.add(Establishment.builder().code("39013001").type(TypeEstablishment.secondary).name("ثانوية عبد العزيز الشريف").build());
//		establishments.add(Establishment.builder().code("39013002").type(TypeEstablishment.secondary).name("ثانوية بوشوشة").build());
//		establishments.add(Establishment.builder().code("39013003").type(TypeEstablishment.secondary).name("ثانوية السعيد عبد الحي").build());
//		establishments.add(Establishment.builder().code("39013004").type(TypeEstablishment.secondary).name("ثانوية ميلودي العروسي").build());
//		establishments.add(Establishment.builder().code("39013005").type(TypeEstablishment.secondary).name("ثانوية بوصبيع صالح عبد المجيد").build());
//		establishments.add(Establishment.builder().code("39013006").type(TypeEstablishment.secondary).name("ثانوية شنوف حمزة").build());
//		establishments.add(Establishment.builder().code("39013007").type(TypeEstablishment.secondary).name("ثانوية المجاهد بكار بحري").build());
//		establishments.add(Establishment.builder().code("39013008").type(TypeEstablishment.secondary).name("ثانوية علي عون").build());
//		establishments.add(Establishment.builder().code("39013009").type(TypeEstablishment.secondary).name("ثانوية المجاهد عيدة عبد الرزاق").build());
//		establishments.add(Establishment.builder().code("39013010").type(TypeEstablishment.secondary).name("ثانوية تواتي حمد لخضر").build());
//		establishments.add(Establishment.builder().code("39013011").type(TypeEstablishment.secondary).name("ثانوية المجاهد محمد رحال").build());
//		establishments.add(Establishment.builder().code("39013012").type(TypeEstablishment.secondary).name("ثانوية سبل النجاح").build());
//		establishments.add(Establishment.builder().code("39013013").type(TypeEstablishment.secondary).name("ثانوية المجاهد بن عون لخضر").build());
//		establishments.add(Establishment.builder().code("39013014").type(TypeEstablishment.secondary).name("ثانوية سبل المستقبل").build());
//		establishments.add(Establishment.builder().code("39013015").type(TypeEstablishment.secondary).name("ثانوية المجاهد دويم محمد الهاشمي").build());
//		/**
//		 * establishments of Biskra
//		 * */
//
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012001").name("متوسطة يوسف العمودي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012002").name("متوسطة الاخوات أوراغ").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012003").name("متوسطة خملة ابراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012004").name("متوسطة بشير بن ناصر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012005").name("متوسطة لبصايرة فاطمة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012006").name("متوسطة خولة بنت الازور").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012007").name("متوسطة الشيخ محمد العابد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012008").name("متوسطة زراري محمد الصالح").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012009").name("متوسطة محمود حوحو").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012010").name("متوسطة الاخوة بركات").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012011").name("متوسطة غمري حسين").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012012").name("متوسطة زاغز جلول").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012013").name("متوسطة أحمد رضا حوحو").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012014").name("متوسطة أبوبكر مصطفى بن رحمون").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012015").name("متوسطة رميشي محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012016").name("متوسطة مداني رحمون").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012017").name("متوسطة رمضان حسوني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012018").name("متوسطة حمودي محمد الصغير").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012019").name("متوسطة محمد الطاهر قدوري بن علي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012020").name("متوسطة الزين بن مداني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012021").name("متوسطة أبو بكر مسعودي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012022").name("متوسطة طيبي عبد الرحمان").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012023").name("متوسطة إبراهيمي محمد بن حمي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012024").name("متوسطة أحمد زيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012025").name("متوسطة عشورمصطفى").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012026").name("متوسطة بالطيبي بلقاسم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012027").name("متوسطة الاخوة قروف").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012028").name("متوسطة محمد شهرة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012029").name("متوسطة عجال محمود").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012030").name("متوسطة بجاوي العربي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012031").name("متوسطة تكوتي أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012032").name("متوسطة عباس عبد الكريم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012033").name("متوسطة بوزلزل إبراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012034").name("متوسطة حليمي رشيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012035").name("متوسطة عاشور زيان بن مبروك").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012036").name("متوسطة الاخوة الشهداء عصمان").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012037").name("متوسطة عمري مبروك بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012038").name("متوسطة عبدلي محمد الشريف بن صالح").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012039").name("متوسطة عليةعلي بن لعماري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012040").name("متوسطة المجاهد لعلالي علي بن لعلالي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012041").name("متوسطة الشهيد تغليسية محمد الطاهر بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07012042").name("متوسطة الشهيد كابرين أحمد بن صديق").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07016201").name("متوسطة مدرسة الأطفال المعوقين سمعيا").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07016202").name("متوسطة مدرسة الأطفال المعوقين بصريا").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07042001").name("متوسطة عروسي محمد الصادق").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07042002").name("متوسطة محمد عباس قواند").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07042003").name("متوسطة رميضيني أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07112001").name("متوسطة بن طراح أبراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07112002").name("متوسطة الشيخ صالح مسعودي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07112003").name("متوسطة شادلي أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07112004").name("متوسطة بن عمارة عبد الحفيظ").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07112005").name("متوسطة 17أكتوبر 61").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07132001").name("متوسطة عبد القادر نور الدين").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07142001").name("متوسطة سقني سقني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07122001").name("متوسطة محمد عثماني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07122002").name("متوسطة يوبي الطاهر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07122003").name("متوسطة شاهدي عمار").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07152001").name("متوسطة الشيخ مولود الزريبي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07152002").name("متوسطة حفيظي الطاهر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07152003").name("متوسطة شرقي مكي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07152004").name("متوسطة بحري الجموعي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07162001").name("متوسطة الاخوة سعدي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07162002").name("متوسطة الاخوة لفرادي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07282001").name("متوسطة الاخوة الشهداء جبابري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07282002").name("متوسطة المزيرعة الجديدة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07332001").name("متوسطة بلمكي محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07172001").name("متوسطة نورالدين عبد الباقي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07172002").name("متوسطة بوحوفاني محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07172003").name("متوسطة بليل محمد الدراجي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07182001").name("متوسطة محمد بودونات").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07182002").name("متوسطة معكوف بلقاسم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07192001").name("متوسطة الاخوة الشهداء صقعة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07192002").name("متوسطة المجاهد عميري السعيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07192003").name("متوسطة المجاهد بوجمعة الشريف بن بوجمعة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07032001").name("متوسطة المجاهد احمد رميضيني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07202001").name("متوسطة هادف أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07202002").name("متوسطة شنشونة لخضر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07202003").name("متوسطة المجاهد عبد الله ركيبي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212001").name("متوسطة الصيد نورالدين").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212002").name("متوسطة دعاس محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212003").name("متوسطة الاخوة منصر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212004").name("متوسطة العقيد محمد شعباني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212005").name("متوسطة خيذرمحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212006").name("متوسطة التومي بن عاشور").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212007").name("متوسطة قبايلي أحمد بن رمضان").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212008").name("متوسطة عبد الحميد بن باديس").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212009").name("متوسطة العلامة إبن خلدون").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07212010").name("متوسطة المجاهد كلاتمة عمار بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07232001").name("متوسطة الشيخ بوحامد فرحات").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07232002").name("متوسطة المجاهد كحلة بلقاسم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07272001").name("متوسطة علي عمارة البرجي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07272002").name("متوسطة شريف العربي بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07292001").name("متوسطة هواري بومدين").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07292002").name("متوسطة بن جاب الله عمر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07022001").name("متوسطة سالم بوزيدي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07022002").name("متوسطة قطاف الطاهر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07222001").name("متوسطة قارة عبد الله").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07222002").name("متوسطة صياد لعلى").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07222003").name("متوسطة جعادي العمري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07222004").name("متوسطة النوي الطيب بن بشير").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07222005").name("متوسطة ليوة الجديدة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07242001").name("متوسطة أحمد بن طالب").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07242002").name("متوسطة الشيخ العلامة خليفة السعدوني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07252001").name("متوسطة صحراوي أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07252002").name("متوسطة براهيم غانم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07302001").name("متوسطة عبد الرحمن الاخضري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07302002").name("متوسطة محمد لخضاري المدعو حاج حمي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07262001").name("متوسطة أرويجع أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07262002").name("متوسطة بوغرارة ابراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07312001").name("متوسطة عيسى حسناوي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07312002").name("متوسطة مزغيش الصالح").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("07312003").name("متوسطة الشهيد احمد شيحي").build());
//		// Adding secondary schools
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013001").name("ثانوية العربي بن مهيدي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013002").name("ثانوية الدكتور سعدان").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013003").name("ثانوية مكي مني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013004").name("ثانوية السعيد بن شايب").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013005").name("ثانوية رشيد رضا العشوري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013006").name("ثانوية محمد خير الدين").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013007").name("ثانوية محمد قروف بن بلقاسم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013008").name("ثانوية محمد بلونار").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013009").name("ثانوية العقيد سي الحواس").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013010").name("ثانوية السعيد عبيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013011").name("ثانوية محمد بوصبيعات").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013012").name("ثانوية محمد بجاوي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013013").name("ثانوية حساني عبد الكريم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013014").name("ثانوية الشهداء الاخوين عبيد الله محمد وبلقاسم بن مسعود").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07013015").name("ثانوية ضو مسعود").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07323001").name("ثانوية هيشر لعماري بن ابراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07043001").name("ثانوية مودع الهاشمي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07043002").name("ثانوية المجاهد سويكي عبد القادر بن لعربي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07113001").name("ثانوية السايب بولرباح").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07113002").name("ثانوية بشير بسكري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07113003").name("ثانوية زراري محمد بن المهدي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07133001").name("ثانوية ثانوية الحوش").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07143001").name("ثانوية صالحي بلخير").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07123001").name("ثانوية أحمد منصوري بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07153001").name("ثانوية بادي مكي بن عبد القادر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07153002").name("ثانوية الشهداء الاخوة خضراوي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07163001").name("ثانوية الاخوة بن ناجي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07283001").name("ثانوية الصادق بلحاج").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07333001").name("ثانوية عاشور بن محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07173001").name("ثانوية محمد إدريس المدعو عمر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07183001").name("ثانوية أحمد بن براهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07193001").name("ثانوية بوجمعة محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07193002").name("ثانوية الاخوة الشهداء برباري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07033001").name("ثانوية الشهيد لطيف مسعود").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07203001").name("ثانوية لغويل منفوخ").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07213001").name("ثانوية محمد العربي بعرير").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07213002").name("ثانوية شكري محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07213003").name("ثانوية الحاج محمد المقراني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07213004").name("ثانوية اول نوفمبر 1954").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07233001").name("ثانوية الزعاطشة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07273001").name("ثانوية جلاب عبد الحفيظ").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07293001").name("ثانوية حميمي السعدي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07023001").name("ثانوية لخضر رمضاني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07223001").name("ثانوية بن ناصر محمد بن احمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07243001").name("ثانوية زاغز جلول").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07253001").name("ثانوية عبد الرحمن قوتال").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07303001").name("ثانوية الشيخ عبد الرحمان الاخضري").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07263001").name("ثانوية دريسي محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("07313001").name("ثانوية حاجي عمار بن بلقاسم").build());
//
//		/**
//		 * establishments of EL magair
//		 * */
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272001").name("متوسطة الشهيد ابراهيم شهرة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272002").name("متوسطة الشهيد أحمد بوزقاق").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272003").name("متوسطة الشهيد عيسى مشحاط").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272004").name("متوسطة امبارك الميلي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272005").name("متوسطة الشهيد تقار بريق").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272006").name("متوسطة الشهيد عمار شهرة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272007").name("متوسطة الشهيدة جروني خضرة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272008").name("متوسطة المجاهد العايز بوبكر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39272009").name("متوسطة المجاهد البار عبد العالي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39212001").name("متوسطة الشهيد سوالمي ابراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39232001").name("متوسطة الشهيد بالرابح محمد الهادي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39232002").name("متوسطة الشهيد بالرابح حشاني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39232003").name("متوسطة الشهيد بالطاهر علي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39292001").name("متوسطة سبع مبارك").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39292002").name("متوسطة الشهيد ربيح عمار").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39292003").name("متوسطة البعاج").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39222001").name("متوسطة الشهيد عروك قويدر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39222002").name("متوسطة العبادلية").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39242001").name("متوسطة سلطاني الطاهر بن ابراهيم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39242002").name("متوسطة حساني عمر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282001").name("متوسطة ابن باديس جامعة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282002").name("متوسطة جعفري يوسف").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282003").name("متوسطة الشهيد سلطاني علي بن عمر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282004").name("متوسطة العقيد محمد شعباني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282005").name("متوسطة الشهيد حميدي لخضر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282006").name("متوسطة الشهيد معروفي الطاهر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282007").name("متوسطة الشهيد بوعنان العيد بن بوليفة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282008").name("متوسطة الشهيد بن عوالي الساسي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39282009").name("متوسطة الشهيد بن مبروك محمد العيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39302001").name("متوسطة بن سبتي محمد الصغير").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39302002").name("متوسطة أول نوفمبر 1954").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39302003").name("متوسطة 17 اكتوبر1961").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39302004").name("متوسطة المجاهد بوخالفة مصطفى").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.middle).code("39302005").name("متوسطة المجاهد الوافي عبد الرزاق").build());
//
//		// Adding secondary schools
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39273001").name("ثانوية شهرة محمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39273002").name("ثانوية عبيد مروش").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39273003").name("ثانوية حبة عبد المجيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39273004").name("ثانوية المجاهد زوبير لخضر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39273005").name("ثانوية المجاهد الصائم محمد رشيد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39213001").name("ثانوية حسينات النوي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39293001").name("ثانوية بن عدي الحاج").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39293002").name("ثانوية المجاهد المرحوم جلالي العربي").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39223001").name("ثانوية الشهيد حداج لخضر بن أحمد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39243001").name("ثانوية الشهيد سلطاني عمر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39283001").name("ثانوية الشيخ محمد المقراني").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39283002").name("ثانوية حساني لخضر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39283003").name("ثانوية متقن جامعة").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39283004").name("ثانوية الشهيد شباب محمد بن بلقاسم").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39283005").name("ثانوية المجاهد ختة محمد بن لخضر").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39303001").name("ثانوية عمراني العابد").build());
//		establishments.add(Establishment.builder().type(TypeEstablishment.secondary).code("39303002").name("ثانوية الشهيد لمنور قادري").build());
//
//		establishmentService.saveEstablishments(establishments);
//	}
//}

package com.example.androidpart.service;

public class EcoStandards {
    // Plastic
    public static final String PET = "01PET";
    public static final String PE_HD = "02PE-HD";
    public static final String PVC = "03PVC"; // no recycle
    public static final String PE_LD = "04PE-LD";
    public static final String PP = "05PP";
    public static final String PS = "06PS"; // can, but not often
    public static final String NO_RECYCLE_PS = "Маркировкой PS обозначают полистирол." +
            " Он бывает двух видов — вспененный и обычный. Из вспененного делают контейнеры для яиц," +
            " подложки для мяса, овощей и фруктов. Из обычного полистирола изготавливают баночки для" +
            " йогурта и почти всю одноразовую посуду." +
            " Пластик с маркировкой «6» можно сдать на переработку, но его принимают редко." +
            " Поэтому лучше избегать покупок в упаковке из полистирола.";
    public static final String OTHER = "07OTHER"; // no recycle
    public static final String NO_RECYCLE_OTHER =
            "Пластик с маркировкой «7» не перерабатывают в России." +
            " Под эту категорию попадают смешанные виды пластика." +
            " Чаще всего его используют в составе упаковок для косметики, " +
            "корма для животных и пачек для кофе. Пластика с такой маркировкой стоит избегать," +
            " если есть возможность купить нужный товар в другой упаковке.";
    public static final String ABS = "ABS";
    public static final String PC = "PC";
    public static final String SAN = "SAN";

    public static final String HOW_TO_PREP_PLASTIC = "Ополосните, чтобы удалить остатки еды," +
            " косметики, бытовой химии. " +
            "Этим вы облегчите труд сортировщиков и сделаете переработку сырья дешевле." +
            " Отмывать тару до идеальной чистоты не обязательно.\n" +
            "Сделайте упаковку компактнее. Сомните или скрутите пластиковые бутылки, " +
            "чтобы выпустить воздух. Контейнеры спрессуйте или сложите стопкой." +
            " Так они будут занимать меньше места.\n" +
            "Снимите всё лишнее. Обязательно уберите дозаторы и распылители —" +
            " они перерабатываются отдельно. Этикетки из тонкого картона или термоусадочной плёнки" +
            " стоит убрать," +
            " а вот счищать или смывать бумажные наклейки не нужно.";

    public static final String[] NO_RECYCLE_PLASTIC = {OTHER, PS, PVC};
    public static final String[] RECYCLE_PLASTIC = {PET, PE_HD};
    public static final String[] RECYCLE_NOT_ALWAYS = {PE_LD, PP, ABS};

    // Paper
    public static final String[] PAP = {"20", "21", "22"};
    public static final String NOT_RECYCLE_PAPER = "Упаковка от соков, молочных продуктов — " +
            "сдаётся отдельно как тетрапак\n" +
            "грязная (жирная, масляная) макулатура — можно компостировать\n" +
            "бумажные салфетки и полотенца\n" +
            "ламинированная (на разрыв остаётся плёнка)\n" +
            "пергаментная, бумага для выпечки, вощёная, калька\n" +
            "бумажные стаканчики (принимаются отдельно)\n" +
            "чеки и бумага для факсов (принимаются отдельно)\n" +
            "фотобумага\n" +
            "обои\n" +
            "рисунки из мелков, растопленных утюгом; рисунки из пластилина; картины масляными красками";
    public static final String HOW_TO_PREP_PAPER = "Перед тем, как отдавать муколатуры, убедитесь в отсутствии ламинации (тонкой плёнки)" +
            " — надорвите край изделия, если плёнка не тянется и не видна, то можно сдавать\n" +
            "удалите металлические элементы — пружины, скрепки и тому подобное" +
            " (скобы от степлера можно оставлять)\n" +
            "удалите  пластиковые элементы — ручки у коробок, плёнку и файлы, пружины и" +
            " тому подобное (скотч можно оставлять)\n" +
            "объемные коробки сложите в плоские\n" +
            "картон рекомендуется перевязать в плотные кипы или сложить в коробки\n" +
            "если сдаёте в контейнер во дворе, в котором макулатура может разлететься или" +
            " испачкаться — используйте плотный пакет";

    // Metal
    public static final String FE = "40FE"; // iron
    public static final String ALU = "41ALU"; // aluminium

    // Wood
    public static final String FOR = "50FOR";

    // Glass
    public static final String[] GL = {"70", "71", "72", "73", "74"};
    public static final String NO_RECYCLE_GLASS = "оконные стекла;\n" +
            "ветровые стекла автомобилей;\n" +
            "лампочки;\n" +
            "ламинированная посуда;\n" +
            "хрусталь, фарфор и керамика;\n" +
            "зеркала.";

    // Compositional
    public static final String PA_PLA = "81"; // paper + plastic
    public static final String PA_ALU = "82"; // paper + aluminium
    public static final String C_PAP = "84"; // paper + plastic + aluminium
    public static final String PL_ALU = "90"; // plastic + aluminium
}

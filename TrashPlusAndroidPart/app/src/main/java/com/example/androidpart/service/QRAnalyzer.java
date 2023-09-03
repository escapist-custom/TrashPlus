package com.example.androidpart.service;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.androidpart.MainActivity;
import com.example.androidpart.R;
import com.example.androidpart.domain.Product;
import com.example.androidpart.domain.User;
import com.example.androidpart.repository.AppDatabase;
import com.example.androidpart.repository.product.dao.ProductTrashPlusDao;
import com.example.androidpart.repository.user.dao.UserTrashPlusDao;
import com.example.androidpart.rest.impl.AppApiVolley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QRAnalyzer implements ImageAnalysis.Analyzer {

    public final MultiFormatReader multiFormatReader;
    private AppApiVolley appApiVolley;
    private static Context context;
    public static Result result;
    public static boolean scanFlag = true;
    public ImageProxy imageProxy;
    private static String classOfProduct = "";

    public QRAnalyzer(Context context, AppDatabase db, Fragment fragment) {
        multiFormatReader = new MultiFormatReader();
        this.context = context;
        Map<DecodeHintType, Object> hintTypeObjectMap = new EnumMap<DecodeHintType, Object>(
                DecodeHintType.class
        );
        appApiVolley = new AppApiVolley(fragment);
        hintTypeObjectMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.of(BarcodeFormat.QR_CODE));
        multiFormatReader.setHints(hintTypeObjectMap);
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        imageProxy = image;
        ByteBuffer byteBuffer = imageProxy.getPlanes()[0].getBuffer();
        ExecutorService service = Executors.newSingleThreadExecutor();

        byte[] data = new byte[byteBuffer.remaining()];
        byteBuffer.get(data);

        int[] intData = new int[data.length];
        for (int i = 0; i < intData.length; i++) {
            intData[i] = data[i] & 0xFF;
        }

        int h = imageProxy.getHeight();
        int w = imageProxy.getWidth();

        try {
            result = multiFormatReader.decodeWithState(
                    new BinaryBitmap(
                            new HybridBinarizer(
                                    new RGBLuminanceSource(w, h, intData)
                            )
                    )
            );
            String barcode = "";
            if (result.getText() != null) {
                barcode = result.getText().split("/")[0];
                classOfProduct = result.getText().split("/")[1];
            }
            GetProductByBarcode getProductByBarcode = new GetProductByBarcode(MainActivity.db,
                    barcode, result, classOfProduct);
            service.execute(getProductByBarcode);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } finally {
            multiFormatReader.reset();
            imageProxy.close();
        }
    }

    public static void launchDialogError() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.incorrect_qr);
        TextView button = dialog.findViewById(R.id.btn_back_incor_qr);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFlag = true;
                dialog.cancel();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public static void launchDialogSuccess(Product product) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_bottom_sheet);
        TextView nameOfProductSheet = dialog.findViewById(R.id.tv_product_name);
        TextView infoProduct = dialog.findViewById(R.id.tv_product_info);
        TextView infoCovering = dialog.findViewById(R.id.tv_recycling_info);
        ImageView ivProduct = dialog.findViewById(R.id.iv_product_photo);
        AppCompatButton button = dialog.findViewById(R.id.btn_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFlag = true;
                dialog.cancel();
            }
        });
        AppApiVolley.requestFlag = true;
        infoCovering.setText(settingTexts(classOfProduct));
        nameOfProductSheet.setText(product.getNameOfProduct());
        infoProduct.setText(product.getInformation());
        try {
            Glide.with(ivProduct)
                    .load(new URL(product.getPhotoLink()))
                    .into(ivProduct);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    // set text about covering based on recycling code
    private static String settingTexts(String code) {
        String text = "";
        switch (code) {
            case "01PET":
                text = "\"Данный вид пластика можно переработать, но если это - бутылка с " +
                        "кислотным или чёрных цветом, то такую упаковку могут непринять на пункте " +
                        "приёма. Также упаковку не примут, если это - контйнер или бутылки, флаконы " +
                        "для косметики и бытовой химии." + "\n" +
                        "Чтобы подготовить данный вид упаковок к переработке нужно: " + "\n" +
                        "сжать её, " + "\n" +
                        "термоусадочную этикетку — пленку, которая обтягивает бутылку целиком — " +
                        "надо снять" + "\n" +
                        "а крышки и колечки можно оставить на упаковке" + "\n" +
                        "Положите упаковку в специально отведённый пакет для этого типа пластика.";
                break;
            case "02PE-HD":
                text = "Данный вид пластика можно переработать!" + "\n" +
                        "Чаще всего используется для хранения бытовой химии." +
                        "\n" +
                        "Чтобы подготовить упаковку к переработке нужно: " + "\n" +
                        "очистить от остатков содержимого, " + "\n" +
                        "снять крышку, " + "\n" +
                        "снять этикетки" + "\n" +
                        "Положете её в специально отделённый пакет для этого типа пластика.";
                break;
            case "03PVC":
                text = "Изделия из поливинилхлорида отмечены маркировкой PVC." + "\n" +
                        "Этот вид пластика не используют в пищевой промышленности,\" +\n" +
                        "потому что поливинилхлорид может содержать остатки кадмия — тяжелого металла,\" +\n" +
                        "опасного для человека.\" +\n" +
                        "Из этого пластика делают, например, оконные рамы, пленки для натяжных потолков, перчатки.\" +\n" +
                        "В России изделия с маркировкой PVC практически невозможно сдать на переработку";
                break;
            case "04PE-LD":
                text = "Данный вид пластика можно переработать!" + "\n" +
                        "Перед тем, как сдать упаковку в пункт переработки, нужно ополустнуть упаковку " +
                        "и снять с её всё лишнее, сжать, чтобы уменьшить объём, а затем положить " +
                        "её в специально выделаенный для этого типа пластика пакет.";
                break;
            case "05PP":
                text = "Данный вид пластика можно переработать!" + "\n" +
                        "Перед сдачей в пункт приёма нужно: " + "\n" +
                        "содрать все наклейки" +
                        "позвонить в пункт переработки и убедиться, что они примут этот вид пластика, " +
                        "положить упаковку в специально отведённый пакет.";
                break;
            case "06PS":
                text = "Маркировкой PS обозначают полистирол." +
                        " Он бывает двух видов — вспененный и обычный. Из вспененного делают контейнеры для яиц," +
                        " подложки для мяса, овощей и фруктов. Из обычного полистирола изготавливают баночки для" +
                        " йогурта и почти всю одноразовую посуду." +
                        " Пластик с маркировкой «6» можно сдать на переработку, но его принимают редко." +
                        " Поэтому лучше избегать покупок в упаковке из полистирола.";
                break;
            case "07OTHER":
                text = "Пластик с маркировкой «7» не перерабатывают в России." +
                        " Под эту категорию попадают смешанные виды пластика." +
                        " Чаще всего его используют в составе упаковок для косметики, " +
                        "корма для животных и пачек для кофе. Пластика с такой маркировкой стоит избегать," +
                        " если есть возможность купить нужный товар в другой упаковке.";
                break;
            case "ABS":
                text = "Данный вид пластика можно перерабатывать!" + "\n" +
                        "Этот вид пластика нуно отнести в пункт приёма старой техники, так как этот " +
                        "используют для корпусов техники";
                break;
            case "PC":
                text = "Данный вид пластика можно перерабатывать!" + "\n" +
                        "Сдать данный пластик можно в специализированный пункт приема, получив при" +
                        " этом дополнительный доход.";
                break;
            case "SAN":
                text = "Данный вид пластика можно перерабатывать, но нужно убедиться, что пункт " +
                        "приёма, куда Вы планируете сдать этот пластик, примет его." + "\n" +
                        "Перед тем, как сдавать его, нужно: " + "\n" +
                        "удалить остатки еды и положить в отдельный пакет для этого типа пластика.";
                break;
            case "20":
                text = "Перед тем, как отдавать муколатуры, убедитесь в отсутствии ламинации (тонкой плёнки)" +
                        " — надорвите край изделия, если плёнка не тянется и не видна, то можно сдавать\n" +
                        "удалите металлические элементы — пружины, скрепки и тому подобное" +
                        " (скобы от степлера можно оставлять)\n" +
                        "удалите  пластиковые элементы — ручки у коробок, плёнку и файлы, пружины и" +
                        " тому подобное (скотч можно оставлять)\n" +
                        "объемные коробки сложите в плоские\n" +
                        "картон рекомендуется перевязать в плотные кипы или сложить в коробки\n" +
                        "если сдаёте в контейнер во дворе, в котором макулатура может разлететься или" +
                        " испачкаться — используйте плотный пакет.";
                break;
            case "21":
                text = "Перед тем, как отдавать муколатуры, убедитесь в отсутствии ламинации (тонкой плёнки)" +
                        " — надорвите край изделия, если плёнка не тянется и не видна, то можно сдавать\n" +
                        "удалите металлические элементы — пружины, скрепки и тому подобное" +
                        " (скобы от степлера можно оставлять)\n" +
                        "удалите  пластиковые элементы — ручки у коробок, плёнку и файлы, пружины и" +
                        " тому подобное (скотч можно оставлять)\n" +
                        "объемные коробки сложите в плоские\n" +
                        "картон рекомендуется перевязать в плотные кипы или сложить в коробки\n" +
                        "если сдаёте в контейнер во дворе, в котором макулатура может разлететься или" +
                        " испачкаться — используйте плотный пакет.";
                break;
            case "22":
                text = "Не перерабатываются: " + "\n" +
                        "Упаковка от соков, молочных продуктов — " +
                        "сдаётся отдельно как тетрапак\n" +
                        "грязная (жирная, масляная) макулатура — можно компостировать\n" +
                        "бумажные салфетки и полотенца\n" +
                        "ламинированная (на разрыв остаётся плёнка)\n" +
                        "пергаментная, бумага для выпечки, вощёная, калька\n" +
                        "бумажные стаканчики (принимаются отдельно)\n" +
                        "чеки и бумага для факсов (принимаются отдельно)\n" +
                        "фотобумага\n" +
                        "обои\n" +
                        "рисунки из мелков, растопленных утюгом; рисунки из пластилина; картины " +
                        "масляными красками" + "\n" +
                        "Перед тем, как отдавать муколатуры, убедитесь в отсутствии ламинации " +
                        "(тонкой плёнки)" +
                        " — надорвите край изделия, если плёнка не тянется и не видна," +
                        " то можно сдавать\n" +
                        "удалите металлические элементы — пружины, скрепки и тому подобное" +
                        " (скобы от степлера можно оставлять)\n" +
                        "удалите  пластиковые элементы — ручки у коробок, плёнку и файлы, пружины и" +
                        " тому подобное (скотч можно оставлять)\n" +
                        "объемные коробки сложите в плоские\n" +
                        "картон рекомендуется перевязать в плотные кипы или сложить в коробки\n" +
                        "если сдаёте в контейнер во дворе, в котором макулатура может разлететься" +
                        " или" +
                        " испачкаться — используйте плотный пакет.";
                break;
            case "40FE":
                text = "Обычно металл сдаётся в металлолом, а не в пункты переработки." + "\n" +
                        "Перед сдачей металла нужно отделить от него все неметаллические компоненты.";
                break;
            case "41ALU":
                text = "Обычно металл сдаётся в металлолом, а не в пункты переработки." + "\n" +
                        "Перед сдачей металла нужно отделить от него все неметаллические " +
                        "компоненты.";
                break;
            case "50FOR":
                text = "На данный момент, сдача древесины на переработку вызовит затруднения, потому " +
                        "что количества компаний, которые этим занимаются недостаточно." + "\n" +
                        "Вам необходимо поискать, где возможно сдать това";
                break;
            case "70":
                text = "Стекло является самым экологичным материалом, так что пытайтесь покупать " +
                        "продукты в стеклянной упаковке" + "\n" +
                        "Не принимается на переработку: " + "\n" +
                        "керамическая и стеклянная посуда (бокалы, стаканы, кружки, тарелки),\n" +
                        "крышки от сковородок и кастрюль,\n" +
                        "оптическое стекло (очки, линзы),\n" +
                        "автомобильное стекло,\n" +
                        "лампочки,\n" +
                        "хрусталь,\n" +
                        "зеркала." + "\n" +
                        "Для того, чтобы подготовить стекло к переработке нужно: " + "\n" +
                        "сполоснуть от остатков пищи,\n" +
                        "с банок рекомендуется снять крышки,\n" +
                        "этикетки, дозаторы и прочее удалять не надо";
                break;
            case "71":
                text = "Данный материал является самым экологичным, так что пытайтесь покупать " +
                        "продукты в стеклянной упаковке" + "\n" +
                        "Не принимается на переработку: " + "\n" +
                        "керамическая и стеклянная посуда (бокалы, стаканы, кружки, тарелки)\n" +
                        "крышки от сковородок и кастрюль\n" +
                        "оптическое стекло (очки, линзы)\n" +
                        "автомобильное стекло\n" +
                        "лампочки\n" +
                        "хрусталь\n" +
                        "зеркала" + "\n" +
                        "Для того, чтобы подготовить стекло к переработке нужно: " + "\n" +
                        "сполоснуть от остатков пищи\n" +
                        "с банок рекомендуется снять крышки\n" +
                        "этикетки, дозаторы и прочее удалять не надо";
                break;
            case "72":
                text = "Данный материал является самым экологичным, так что пытайтесь покупать " +
                        "продукты в стеклянной упаковке" + "\n" +
                        "Не принимается на переработку: " + "\n" +
                        "керамическая и стеклянная посуда (бокалы, стаканы, кружки, тарелки)\n" +
                        "крышки от сковородок и кастрюль\n" +
                        "оптическое стекло (очки, линзы)\n" +
                        "автомобильное стекло\n" +
                        "лампочки\n" +
                        "хрусталь\n" +
                        "зеркала" + "\n" +
                        "Для того, чтобы подготовить стекло к переработке нужно: " + "\n" +
                        "сполоснуть от остатков пищи\n" +
                        "с банок рекомендуется снять крышки\n" +
                        "этикетки, дозаторы и прочее удалять не надо";
                break;
            case "73":
                text = "Данный материал является самым экологичным, так что пытайтесь покупать " +
                        "продукты в стеклянной упаковке" + "\n" +
                        "Не принимается на переработку: " + "\n" +
                        "керамическая и стеклянная посуда (бокалы, стаканы, кружки, тарелки)\n" +
                        "крышки от сковородок и кастрюль\n" +
                        "оптическое стекло (очки, линзы)\n" +
                        "автомобильное стекло\n" +
                        "лампочки\n" +
                        "хрусталь\n" +
                        "зеркала" + "\n" +
                        "Для того, чтобы подготовить стекло к переработке нужно: " + "\n" +
                        "сполоснуть от остатков пищи\n" +
                        "с банок рекомендуется снять крышки\n" +
                        "этикетки, дозаторы и прочее удалять не надо";
                break;
            case "74":
                text = "Данный материал является самым экологичным, так что пытайтесь покупать " +
                        "продукты в стеклянной упаковке" + "\n" +
                        "Не принимается на переработку: " + "\n" +
                        "керамическая и стеклянная посуда (бокалы, стаканы, кружки, тарелки)\n" +
                        "крышки от сковородок и кастрюль\n" +
                        "оптическое стекло (очки, линзы)\n" +
                        "автомобильное стекло\n" +
                        "лампочки\n" +
                        "хрусталь\n" +
                        "зеркала" + "\n" +
                        "Для того, чтобы подготовить стекло к переработке нужно: " + "\n" +
                        "сполоснуть от остатков пищи\n" +
                        "с банок рекомендуется снять крышки\n" +
                        "этикетки, дозаторы и прочее удалять не надо";
                break;
        }
        return text;
    }

    class GetProductByBarcode implements Runnable {

        private final ProductTrashPlusDao productDao;
        private final UserTrashPlusDao userDao;
        private Handler handler;

        private Product product;
        private final String barcode;
        private final Result result;
        private final String coverCode;

        GetProductByBarcode(AppDatabase db, String barcode, Result result, String coverCode) {
            this.productDao = db.trashPlusDaoProduct();
            this.userDao = db.trashPlusDaoUser();
            this.barcode = barcode;
            this.result = result;
            this.coverCode = coverCode;
        }

        @Override
        public void run() {
            if (productDao.getProductByBarcode(barcode) == null) {
                if (barcode != null) {
                    if (AppApiVolley.requestFlag) {
                        appApiVolley.getProduct(barcode);
                        AppApiVolley.requestFlag = false;
                    }
                }
            } else {
                product = productDao.getProductByBarcode(barcode);
                User user = userDao.getUser();
                product.setClassOfCover(coverCode);
                product.setUserId(user.getId());
                productDao.update(product);
                handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (scanFlag) {
                            if (result.getText().split("/").length != 2) {
                                launchDialogError();
                            } else {
                                if (product != null) {
                                    launchDialogSuccess(product);
                                }
                            }
                            scanFlag = false;
                        }
                    }
                });
            }
        }
    }
}

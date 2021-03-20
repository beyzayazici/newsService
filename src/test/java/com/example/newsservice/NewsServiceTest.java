package com.example.newsservice;

import com.example.newsservice.model.Condition;
import com.example.newsservice.model.News;
import com.example.newsservice.service.Helper;
import com.example.newsservice.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public class NewsServiceTest {

    private NewsService newsService;

    @Before
    public void setup() {
        newsService = new NewsService();
    }

    @Test
    public void ahoCorasickAlgorithmRuleControl() {
        List<News> newsMockData = mockDataList();
        HashMap<String, List<News>> hashMap = newsService.findRuleNews(newsMockData);
        assertThat(hashMap.keySet().size()).isEqualTo(1);
    }

    @Test
    public void ahoCorasickAlgorithmRuleControlWithRuleSet1() {
        List<News> newsMockData = mockDataList();
        HashMap<String, List<News>> hashMap = newsService.findRuleNews(newsMockData);
        assertThat(hashMap.keySet().size()).isEqualTo(1);
    }

    @Test
    public void ahoCorasickAlgorithmControl() {
        Condition condition = new Condition();
        List<String> listConditionValue = Arrays.asList("Sağlık Müdürlüğü", "Sağlık Müdürü", "haberine açıklama");
        condition.setKey("keywords");
        condition.setValues(listConditionValue);
        List<News> newsMockData = mockDataList();
        assertThat(newsService.ahoCorasickAlgorithm(newsMockData.get(0), condition)).isEqualTo(true);
    }

    @Test
    public void normalizationLowerCaseENControl() {
        String result = Helper.normalization("IL SAGLIK MUDURLUGU", "EN");
        assertThat(result).isEqualTo("il saglik mudurlugu");
    }

    @Test
    public void normalizationLowerCaseControl() {
        String result = Helper.normalization("İL SAĞLIK MÜDÜRLÜĞÜ", "TR");
        assertThat(result).isEqualTo("il sağlık müdürlüğü");
    }

    @Test
    public void normalizationNumbersControl() {
        String result = Helper.normalization("İ45L 7S6AĞ9L0I0K 10MÜ6D7Ü8R9L0Ü9ĞÜ100", "TR");
        assertThat(result).isEqualTo("il sağlık müdürlüğü");
    }

    @Test
    public void normalizationPunctuationControl() {
        String result = Helper.normalization("İ*-='!\"L #^+%SAĞL[]}{IK &M§½$£Ü/DÜ?|\\_-RLÜ<>@;,.:“’ĞÜ", "TR");
        assertThat(result).isEqualTo("il sağlık müdürlüğü");
    }

    @Test
    public void normalizationStopWordsControl() {
        String result = Helper.normalization("altmýþ İL bizden SAĞLIK ben MÜDÜRLÜĞÜ", "TR");
        assertThat(result).isEqualTo("il sağlık müdürlüğü");
    }


    private List<News> mockDataList() {
        List<News> news = new ArrayList();

        List<String> categories1 = Arrays.asList("Yaşam");
        List<String> tags1 = Arrays.asList("news");

        List<String> tags2 = Arrays.asList("news");
        List<String> categories2 = Arrays.asList("Ekonomi");

        News news1 = new News("-8871707514567516000", "https://www.bursasancak.com.tr/van/saglik-mudurlugunden-vanda-300-kisinin-yasadigi-sitede-korona-alarmi-haberine-aciklama-h71706.html", "BURSA SANCAK", "tr", "Regional", tags1, categories1, "Bursa Sancak - Sağlık Müdürlüğünden “Van’da 300 kişinin yaşadığı sitede korona alarmı” haberine açıklama", "Bursa Sancak - VAN İL SAĞLIK MÜDÜRÜ DOÇ. DR. MAHMUT SÜNNETÇİOĞLU, YEREL BASINDA YER ALAN 'VAN'DA 300 KİŞİNİN YAŞADIĞI SİTEDE KORONA ALARMI” HABERİ'", " Van İl Sağlık Müdürü Doç. Dr. Mahmut Sünnetçioğlu, yerel basında yer alan “Van’da 300 kişinin yaşadığı sitede korona alarmı” haberi nedeniyle bir açıklama yayımladı. Doç. Dr. Sünnetçioğlu, diğer tüm illerde olduğu gibi Van’da da korona virüs hastalarının tanı, tedavi ve filyasyon süreçlerinin yakından takip edildiği belirtilerek, “Şikayetleri olan tüm vatandaşlarımıza korona virüsü yönüyle gerekli incelemeler hastanelerimizde aralıksız olarak sunulmaktadır. Başvurular ya da taramalar neticesinde olası vaka tanımına uyan tüm hastalarımızın tespiti, hastane transferleri ve temaslı olan kişilerin belirlenmesi ile ilgili yönlendirmeler için müdürlüğümüz bünyesinde ve bizzat İl Sağlık Müdürü olarak şahsım koordinasyonundaki Pandemi Operasyon Merkezimiz haftanın 7 günü 24 saat salgın kontrolü ve halkımızın sağlığı için çalışmaktadır. Test sonucu ya da muayene sonucu korona virüsüyle uyumlu olduğu tespit edilen vatandaşlarımıza sunulan tedavi edici sağlık hizmetlerimizin yanı sıra hastalarımız ile aynı iş yerinde, aynı hanede ve olası bulaş ihtimali olan diğer sosyal alanlarda filyasyon çalışmaları ile koruyucu sağlık hizmetlerimiz de aralıksız sunulmaktadır. Müdürlük ekiplerimizce yürütülmekte olan filyasyon çalışmaları ile temas varlığı bulunabilecek vatandaşlarımız evlerinde ya da iş yerlerinde ziyaret edilmektedir. Toplumuzun duyarlı yaklaşımı sayesinde hastalar ve yakınları ile ilk görüşmede elde edilemeyen bilgilere de daha sonradan ulaşılarak sağlık için gerekli ek tedbirler alınmaktadır. Vaka ya da temaslı yoğunluğu gösteren alanlarda her ne kadar istenmese de toplumuzun iyilik hali için kamu kurum ve kuruluşları da dahil iş yerleri, sokak ve mahalleler karantina altına alınabilmektedir. Bununla birlikte halkımızın sağlık ve iyilik hali gözetilirken, hastalarımızın sosyal hayatı ya da iş hayatı da düşünülmekte, kimsenin kişilik haklarının incinmesine mahal vermeden gerekli iş ve işlemler mümkün mertebe gizlilik ilkeleri doğrultusunda fakat bir o kadar da şeffaf ve raporlanabilir bir şekilde yürütülmektedir. Medyada yer alan ‘Van’da 300 kişinin yaşadığı sitede korona alarmı’ başlıklı haberde belirtilen hastamız ve sosyal çevresinde sağlık için gerekli tüm önlemler Sağlık Müdürlüğümüz ve saha ekiplerimizce alınmıştır. İlgili hastamızın daha önceden de pozitif bir vaka ile temas halinde olduğu geçtiğimiz hafta içerisinde belirlenmiş olup izolasyon altına alınarak topluma olası bulaş ihtimalinin önüne geçmek için gerekli çalışmalar başlatılmıştı. İzlem ve izolasyondaki vatandaşımızın şikayetlerinin gelişmesi üzerine kendisinde örnek alınmış ve sonucunun da pozitif olduğu belirlenmiştir. Yani kişi pozitifliği tespit edilmeden önce temaslı olarak izolasyonda tutulmuştur. Test sonucu öğrenildiğinde hastamız bilgilendirilmiş ve filyasyon çalışmaları tekrar başlatılmıştır. Hali hazırda mevcut riski nedeniyle izole bir halde olan hastamızın filyasyon çalışmalarında iş yerinde ve ikamet alanında tespit edilen temaslılarına da ulaşılarak gerekli bilgilendirmeler yapılmış, kişiler izolasyon altına alınmıştır. Hasta hakları ve mahremiyeti ilkeleri doğrultusunda temaslı olduğu ya da risk altında olduğu tespit edilen kişiler dışındaki bireylere, hasta bilgileri ya da riskli olduğu tespit edilen kişilerin bilgileri paylaşılmamış, müdürlüğümüze ulaşan vatandaşlarımıza genel korunma tedbirleri ve Sağlık Bakanlığımızın hastalıktan korunmada uyulması gereken 14 kuralı hatırlatılmıştır. Bölge halkımızın iyilik halinin devamı için ilgili yaşam alanında alınması gereken tedbirler de eksiksiz olarak alınmıştır. Sağlık Müdürlüğümüz ve bağlı birimlerimizce halkımızın sağlığını yakından gözetiyor, risk altında olan tüm vatandaşlarımızı bilgilendiriyor ve risk altında olduğu tespit edilen kişiler dışındaki hastalarımızın kimlik bilgilerini ya da kendilerine yapılan iş ve işlemleri paylaşmayarak onların mahremiyetlerini, iş hayatını ve sosyal yaşantılarını korumaya devam ediyor olacağız” dedi. ", "2020-06-30T20:02:23.314Z", "2020-06-30T17:42:00Z", "2020-06-30T20:07:45.694Z");
        News news2 = new News("-2772355923090525000", "http://www.gazetevatan.com/corona-virus-bilim-kurulu-uyesinden-asi-tarihi-ongorusu-1328059-corona-virusu-haberleri/", "VATAN (İSTANBUL)", "tr", "Regional", tags2, categories2, "Corona virüs Bilim Kurulu Üyesinden aşı tarihi öngörüsü", "Sağlık Bakanlığı Corona virüs Bilim Kurulu Üyesi Prof. Dr. İlhami Çelik, \\\"Aşı için öngörülen süre 2022 gibi duruyor. Biz tabi inşallah o dönemlere kalmayız.\\\" dedi.", "Kayseri Ticaret Odası Olağan Meclis Toplantısı'na katılan Çelik,  burada yaptığı konuşmada, Türkiye'deki ilk yeni tip koronavirüs (Kovid-19)  vakasının 11 Mart'ta İstanbul'da bir hastada görüldüğünü anımsattı.   Sağlık Bakanlığının aldığı tedbirlerle Kovid-19'un yayılma hızının  kontrol altına alındığını ancak bireysel sorumsuzluklar nedeniyle virüsün birçok  kişiye bulaşabildiğini belirten Çelik, testi pozitif çıkan kişinin bir  mahalledeki nişan törenine katılması sonrası virüsün yayılmasını örnek gösterdi.   Çelik, cenazeye gidip virüs bulaşan insan sayısının da fazla olduğunu  vurguladı.   Sağlık Bakanlığı'nın yaptığı bir çalışmada, Kayseri'de Kovid-19 virüsü  tespit edilenlerin nüfusun binde 8'i olduğu bilgisini veren Çelik, bunun kentin  iyi korunduğunu gösterdiğini belirtti.   Salgına ilişkin Dünya Sağlık Örgütü'nün geç refleks verdiğini belirten  Çelik, tıp bilimini yönlendiren bazı ilaç firmalarının açıklamalarına da temkinli  olduklarını kaydetti.   Kovid-19 salgınında sadece yaşlıların değil, genç yaşta ölümlerin de  görüldüğüne işaret eden Çelik, özellikle kalp, diyabet hastalığı ve aşırı kilolu  kişilerin risk altında olduğunu vurguladı.   \\\"Sürü bağışıklığı çok riskli\\\"   Salgının bundan sonraki seyrine ilişkin tecrübelerini de paylaşan  Prof. Dr. İlhami Çelik, şöyle konuştu:   \\\"Pandeminin bitmesiyle ilgili önümüzde kanıta dair herhangi bir şey  yok. Bu devam edecek gözüküyor. Benim öngörüm, bunun yazın azalacağı şeklindeydi.  'Azaldı mı?' Bana göre, evet. Karantina tedbirlerine devam edilseydi belki de şu  anda vakalar sıfırlanmıştı. Ancak, toplum dinamik bir toplum. Sizin yaşamak için  çalışmanız ve üretmeniz gerekiyor. O yüzden bu durumu sonsuza kadar devam  ettiremezsiniz.\\\"   Çelik, sokağa çıkma kısıtlaması uzun süre devam eden yaşlı kişilerde  başka sağlık sorunlarının da çıkabildiğine değinerek, \\\"Çok riskli bir olay. Sizin  sürü bağışıklığı demeniz için yüzde 50 ile 70'inin enfekte olması ve bunların  yüzde 5'inin ölmesi gerekiyor. Bu Türkiye için 3-5 milyon arası bir rakam olması  gerekiyor. Biz bir kişinin canı için uğraşıyoruz.\\\" ifadelerini kullandı.   Türkiye'nin sosyal devlet anlayışı gereği koronavirüs testini ücretsiz  yaptığını vurgulayan Çelik, \\\"Amerika'da ise her şey özel sigorta ve para ile  yapılıyor. Orada paranız kadar yaşıyorsunuz.\\\" dedi.    Virüsün ölümcül etkisinin azaldığına dair bazı açıklamalar yapıldığını  belirten Çelik, bunun gerçeği yansıtmadığını, aşı çalışmalarının hız kazandığını  aktardı.   \\\"Aşı 2022 yılını bulur\\\"    Çelik, \\\"Aşı için öngörülen süre 2022 gibi duruyor. Biz tabii inşallah  o dönemlere kalmayız. Kendimizi şöyle alıştırmıştık, yaz gelince biz de bitecek  modundaydık açıkçası. Ancak Allah'a şükür şu anda çok ciddi vakalarımız yok.  Böyle anormal gereksiz bir korku sendromuna da yol açmaya gerek olmadığını  düşünüyorum. Çünkü insan cesur olursa güçlü olur. Bu bağışıklık sistemi için de  böyledir.\\\" diye konuştu.   ", "2020-06-30T20:01:02.270Z", "2020-06-30T19:58:00Z", "2020-06-30T20:06:18.055Z");
        news.add(news1);
        news.add(news2);

        return news;
    }

    private News mockData() {
        News news = new News();
        return news;
    }
}

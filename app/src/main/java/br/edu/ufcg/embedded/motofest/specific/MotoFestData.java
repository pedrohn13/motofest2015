package br.edu.ufcg.embedded.motofest.specific;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.model.Event;
import br.edu.ufcg.embedded.motofest.model.MarkerSettings;
import br.edu.ufcg.embedded.motofest.utils.AtracoesListItem;


public class MotoFestData implements Event {

    private static final double LATITUDE_HOTEL_1 = -7.217698;
    private static final double LONGITUDE_HOTEL_1 = -35.883597;
    private static final double LATITUDE_HOTEL_2 = -7.2171804;
    private static final double LONGITUDE_HOTEL_2 = -35.8843897;
    private static final double LATITUDE_HOTEL_3 = -7.223488;
    private static final double LONGITUDE_HOTEL_3 = -35.887240;
    private static final double LATITUDE_HOTEL_4 = -7.214636;
    private static final double LONGITUDE_HOTEL_4 = -35.878119;
    private static final double LATITUDE_HOTEL_5 = -7.2160491;
    private static final double LONGITUDE_HOTEL_5 = -35.878172;
    private static final double LATITUDE_HOTEL_6 = -7.223666;
    private static final double LONGITUDE_HOTEL_6 = -35.892660;
    private static final double LATITUDE_HOTEL_7 = -7.220542;
    private static final double LONGITUDE_HOTEL_7 = -35.882956;
    private static final double LATITUDE_HOTEL_8 = -7.217365;
    private static final double LONGITUDE_HOTEL_8 = -35.884876;
    private static final double LATITUDE_HOTEL_9 = -7.220548;
    private static final double LONGITUDE_HOTEL_9 = -35.879031;
    private static final double LATITUDE_HOTEL_10 = -7.217322;
    private static final double LONGITUDE_HOTEL_10 = -35.913654;
    private static final double LATITUDE_HOTEL_11 = -7.238764;
    private static final double LONGITUDE_HOTEL_11 = -35.873043;
    private static final double LATITUDE_HOTEL_12 = -7.216344;
    private static final double LONGITUDE_HOTEL_12 = -35.882204;
    private static final double LATITUDE_HOTEL_13 = -7.218649;
    private static final double LONGITUDE_HOTEL_13 = -35.888647;
    private static final double LATITUDE_HOTEL_14 = -7.218976;
    private static final double LONGITUDE_HOTEL_14 = -35.884062;
    private static final double LATITUDE_HOTEL_15 = -7.217974;
    private static final double LONGITUDE_HOTEL_15 = -35.890776;
    private static final double LATITUDE_HOTEL_16 = -7.247400;
    private static final double LONGITUDE_HOTEL_16 = -35.870823;
    private static final double LATITUDE_HOTEL_17 = -7.248361;
    private static final double LONGITUDE_HOTEL_17 = -35.878397;
    private static final double LATITUDE_HOTEL_18 = -7.227559;
    private static final double LONGITUDE_HOTEL_18 = -35.872159;
    private static final double LATITUDE_HOTEL_19 = -7.220997;
    private static final double LONGITUDE_HOTEL_19 = -35.884464;
    private static final double LATITUDE_HOTEL_20 = -7.223239;
    private static final double LONGITUDE_HOTEL_20 = -35.891126;
    private static final double LATITUDE_HOTEL_21 = -7.216892;
    private static final double LONGITUDE_HOTEL_21 = -35.884087;
    private static final double LATITUDE_HOTEL_22 = -7.160917;
    private static final double LONGITUDE_HOTEL_22 = -35.853341;
    private static final double LATITUDE_HOTEL_23 = -7.248108;
    private static final double LONGITUDE_HOTEL_23 = -35.871418;
    private static final double LATITUDE_HOTEL_24 = -7.254330;
    private static final double LONGITUDE_HOTEL_24 = -35.968558;
    private static final double LATITUDE_HOTEL_25 = -7.155752;
    private static final double LONGITUDE_HOTEL_25 = -35.849433;
    private static final double LATITUDE_HOTEL_26 = -7.236648;
    private static final double LONGITUDE_HOTEL_26 = -35.880405;
    private static final double LATITUDE_HOTEL_27 = -7.222700;
    private static final double LONGITUDE_HOTEL_27 = -35.893955;
    private static final double LATITUDE_HOTEL_28 = -7.219849;
    private static final double LONGITUDE_HOTEL_28 = -35.866293;
    private static final double LATITUDE_HOTEL_29 = -7.236757;
    private static final double LONGITUDE_HOTEL_29 = -35.866244;
    private static final double LATITUDE_HOTEL_30 = -7.225843;
    private static final double LONGITUDE_HOTEL_30 = -35.891650;
    private static final double LATITUDE_HOTEL_31 = -7.214424;
    private static final double LONGITUDE_HOTEL_31 = -35.880378;
    private static final double LATITUDE_HOTEL_32 = -7.221023;
    private static final double LONGITUDE_HOTEL_32 = -35.885184;
    private static final double LATITUDE_HOTEL_33 = -7.223048;
    private static final double LONGITUDE_HOTEL_33 = -35.886044;
    private static final double LATITUDE_HOTEL_34 = -7.225599;
    private static final double LONGITUDE_HOTEL_34 = -35.885430;
    private static final double LATITUDE_HOTEL_35 = -7.241793;
    private static final double LONGITUDE_HOTEL_35 = -35.894878;
    private static final double LATITUDE_HOTEL_36 = -7.224135;
    private static final double LONGITUDE_HOTEL_36 = -35.893285;

    private static final double LATITUDE_ESTANDE_1 = -7.2228049867474615;
    private static final double LONGITUDE_ESTANDE_1 = -35.887944214046;
    private static final double LATITUDE_ESTANDE_2 = -7.223123299786341;
    private static final double LONGITUDE_ESTANDE_2 = -35.88775612413883;
    private static final double LATITUDE_ESTANDE_3 = -7.223495828868019;
    private static final double LONGITUDE_ESTANDE_3 = -35.88767398148775;
    private static final double LATITUDE_ESTANDE_4 = -7.223545055902262;
    private static final double LONGITUDE_ESTANDE_4 = -35.88830262422562;
    private static final double LATITUDE_ESTANDE_5 = -7.223173192091876;
    private static final double LONGITUDE_ESTANDE_5 = -35.88836766779423;
    private static final double LATITUDE_ESTACIONAMENTO_1 = -7.223082388091696;
    private static final double LONGITUDE_ESTACIONAMENTO_1 = -35.887904316186905;
    private static final double LATITUDE_ESTACIONAMENTO_2 = -7.223361784957019;
    private static final double LONGITUDE_ESTACIONAMENTO_2 = -35.88779702782631;
    private static final double LATITUDE_ESTACIONAMENTO_3 = -7.223197473011905;
    private static final double LONGITUDE_ESTACIONAMENTO_3 = -35.88821444660425;
    private static final double LATITUDE_ESTACIONAMENTO_4 = -7.224073913567267;
    private static final double LONGITUDE_ESTACIONAMENTO_4 = -35.887283720076084;
    private static final double LATITUDE_ALIMENTACAO_1 = -7.222891466801095;
    private static final double LONGITUDE_ALIMENTACAO_1 = -35.88842198252678;
    private static final double LATITUDE_ALIMENTACAO_2 = -7.2237782190119635;
    private static final double LONGITUDE_ALIMENTACAO_2 = -35.88783625513315;
    private static final double LATITUDE_INSCRICAO = -7.2233305191259065;
    private static final double LONGITUDE_INSCRICAO = -35.8879029750824;
    private static final double LATITUDE_FOME_ZERO_1 = -7.223272644071135;
    private static final double LONGITUDE_FOME_ZERO_1 = -35.88760927319527;
    private static final double LATITUDE_FOME_ZERO_2 = -7.223675440994121;
    private static final double LONGITUDE_FOME_ZERO_2 = -35.88737357407808;
    private static final double LATITUDE_BANHEIRO = -7.2237369747616516;
    private static final double LONGITUDE_BANHEIRO = -35.88826809078455;
    private static final double LATITUDE_PALCO = -7.222562842509253;
    private static final double LONGITUDE_PALCO = -35.888364650309086;
    private static final double LATITUDE_BLIPS = -7.222977614222689;
    private static final double LONGITUDE_BLIPS = -35.887663923203945;
    private static final double LATITUDE_PAVILHAO_1 = -7.223240380387949;
    private static final double LONGITUDE_PAVILHAO_1 = -35.88799919933081;
    private static final double LATITUDE_PAVILHAO_2 = -7.223763916570762;
    private static final double LONGITUDE_PAVILHAO_2 = -35.88743258267641;
    private static final double LATITUDE_CONCESSIONARIAS = -7.2236907412830575;
    private static final double LONGITUDE_CONCESSIONARIAS = -35.88804244995117;
    private static final double LATITUDE_SEGURANCA_1 = -7.2233621175722265;
    private static final double LONGITUDE_SEGURANCA_1 = -35.88755998760462;
    private static final double LATITUDE_SEGURANCA_2 = -7.223951178730337;
    private static final double LONGITUDE_SEGURANCA_2 = -35.88719017803669;
    private static final double LATITUDE_SAIDA = -7.222498315039285;
    private static final double LONGITUDE_SAIDA = -35.88803809136152;

    private static final float HUE_VERDON = 210f;
    private static final float HUE_GOLD = 78f;
    private static final float HUE_CREAM = 60f;
    private static final float HUE_PURPLE = 285f;
    private static final float HUE_SALMOM = 0f;
    private static final String PT_BR = "pt_BR";


    private final ArrayList<MarkerSettings> markersBr;
    private final ArrayList<MarkerSettings> markersUs;
    private final ArrayList<String> typeMarkers;


    private static final int NOTIFICATION_TITLE_1 = R.string.notification_title_1;
    private static final int NOTIFICATION_TITLE_2 = R.string.notification_title_2;
    private static final int NOTIFICATION_TITLE_3 = R.string.notification_title_3;
    private static final String TRINTA_DIAS_RESTANTES = "18/08/2015";
    //public static final String TRINTA_DIAS_RESTANTES = "22/09/2015";
    private static final String QUINZE_DIAS_RESTANTES = "19/08/2015";
    //public static final String QUINZE_DIAS_RESTANTES = "08/10/2015";
    private static final String SETE_DIAS_RESTANTES = "20/08/2015";
    //public static final String SETE_DIAS_RESTANTES = "15/10/2015";

    private static final String VINTE_DOIS_OUT = "22/10/2015";
    private static final String VINTE_TRES_OUT = "23/10/2015";
    private static final String VINTE_QUATRO_OUT = "24/10/2015";
    private static final String VINTE_CINCO_OUT = "25/10/2015";


    private final String nome;
    private boolean isPtBr;

    public MotoFestData() {
        nome = "Campina MotoFest 2015";
        markersBr = new ArrayList<>();
        markersUs = new ArrayList<>();
        typeMarkers = new ArrayList<>();
        isPtBr = false;
        createMarkers();
    }


    @Override
    public String getNomeEvento() {
        return nome;
    }

    @Override
    public HashMap<String, List<AtracoesListItem>> getMapaEventosAtracoes() {
        HashMap<String, List<AtracoesListItem>> aux = new HashMap<>();
        String vintedois;
        String vintetres;
        String vintequatro;
        String vintecinco;
        if (String.valueOf(Locale.getDefault()).equals(PT_BR)) {
         vintedois = "22/10/2015, Quinta-Feira";
         vintetres = "23/10/2015, Sexta-Feira";
         vintequatro = "24/10/2015, Sábado";
         vintecinco = "25/10/2015, Domingo";
        } else {
           vintedois = "10/22/2015, Thursday";
            vintetres = "10/23/2015, Friday";
            vintequatro = "10/24/2015, Saturday";
            vintecinco = "10/25/2015, Sunday";
        }
        aux.put(vintedois, getListAtracoes(VINTE_DOIS_OUT));
        aux.put(vintetres, getListAtracoes(VINTE_TRES_OUT));
        aux.put(vintequatro, getListAtracoes(VINTE_QUATRO_OUT));
        aux.put(vintecinco, getListAtracoes(VINTE_CINCO_OUT));
        return aux;
    }

    public void setLanguage(String language) {
        if (language.equals(PT_BR)) {
            isPtBr = true;
        }
        isPtBr = false;
    }

    @Override
    public ArrayList<MarkerSettings> getMarkersSettings(String language) {
        if (language.equals(PT_BR)) {
            isPtBr = true;
            return markersBr;
        } else {
            isPtBr = false;
            return markersUs;
        }
    }

    public ArrayList<String> getTypeMarkers(ArrayList<MarkerSettings> markers) {
        for (MarkerSettings m: markers) {
            if (!typeMarkers.contains(m.getType())) {
                typeMarkers.add(m.getType());
            }
        }
        return typeMarkers;
    }

    private List<AtracoesListItem> getListAtracoes(String date) {
        List<AtracoesListItem> result = new ArrayList<>();
        switch (date) {
            case VINTE_DOIS_OUT:
                if (String.valueOf(Locale.getDefault()).equals(PT_BR)) {
                    result.add(new AtracoesListItem("Som Mecânico", "17h"));
                    result.add(new AtracoesListItem("Banda Dammage", "20h", true));
                    result.add(new AtracoesListItem("Victor Jambo", "22h", true));
                    result.add(new AtracoesListItem("New Band", "24h", true));
                } else {
                    result.add(new AtracoesListItem("Mechanical Sound", "17h"));
                    result.add(new AtracoesListItem("Banda Dammage", "20h", true));
                    result.add(new AtracoesListItem("Victor Jambo", "22h", true));
                    result.add(new AtracoesListItem("New Band", "24h", true));
                }
                break;
            case VINTE_TRES_OUT:
                if (String.valueOf(Locale.getDefault()).equals(PT_BR)) {
                    result.add(new AtracoesListItem("Show de Acrobacias", "18h30"));
                    result.add(new AtracoesListItem("Banda Warcursed", "20h", true));
                    result.add(new AtracoesListItem("Banda Motorádio", "21h30", true));
                    result.add(new AtracoesListItem("Renato Marinho", "23h30", true));
                } else {
                    result.add(new AtracoesListItem("Stunt Show", "18h30"));
                    result.add(new AtracoesListItem("Banda Warcursed", "20h", true));
                    result.add(new AtracoesListItem("Banda Motorádio", "21h30", true));
                    result.add(new AtracoesListItem("Renato Marinho", "23h30", true));
                }
                break;
            case VINTE_QUATRO_OUT:
                if (String.valueOf(Locale.getDefault()).equals(PT_BR)) {
                    result.add(new AtracoesListItem("Recepção com café da manhã", "8h"));
                    result.add(new AtracoesListItem("Doação de Sangue", "8h"));
                    result.add(new AtracoesListItem("Aulão de ginástica laboral", "8h30"));
                    result.add(new AtracoesListItem("Som Mecânico", "9h"));
                    result.add(new AtracoesListItem("2º Passeio Motociclístico", "11h00"));
                    result.add(new AtracoesListItem("Renato Marinho", "12h", true));
                    result.add(new AtracoesListItem("Feijoada local", "13h30"));
                    result.add(new AtracoesListItem("Banda Motorádio", "14h30", true));
                    result.add(new AtracoesListItem("Banda God's Way", "16h30", true));
                    result.add(new AtracoesListItem("Show de Acrobacias", "18h"));
                    result.add(new AtracoesListItem("Abertura Oficial", "20h"));
                    result.add(new AtracoesListItem("Stella Alves e Forró da Loirinha", "20h30", true));
                    result.add(new AtracoesListItem("Banda Retrohollics Classic Rock", "22h30", true));
                    result.add(new AtracoesListItem("Raul Seixas Cover", "24h", true));
                } else {
                    result.add(new AtracoesListItem("Reception with breakfast", "8h"));
                    result.add(new AtracoesListItem("Blood donation", "8h"));
                    result.add(new AtracoesListItem("Labor gymnastics lessons", "8h30"));
                    result.add(new AtracoesListItem("Mechanical Sound", "9h"));
                    result.add(new AtracoesListItem("2nd ride motorcycle", "11h00"));
                    result.add(new AtracoesListItem("Renato Marinho", "12h", true));
                    result.add(new AtracoesListItem("Local Feijoada", "13h30"));
                    result.add(new AtracoesListItem("Banda Motorádio", "14h30", true));
                    result.add(new AtracoesListItem("Banda God's Way", "16h30", true));
                    result.add(new AtracoesListItem("Stunt Show", "18h"));
                    result.add(new AtracoesListItem("Official Opening", "20h"));
                    result.add(new AtracoesListItem("Stella Alves e Forró da Loirinha", "20h30", true));
                    result.add(new AtracoesListItem("Banda Retrohollics Classic Rock", "22h30", true));
                    result.add(new AtracoesListItem("Raul Seixas Cover", "24h", true));
                }
                break;
            case VINTE_CINCO_OUT:
                if (String.valueOf(Locale.getDefault()).equals(PT_BR)) {
                    result.add(new AtracoesListItem("Encerramento Oficial", "8h"));
                } else {
                    result.add(new AtracoesListItem("Official Closing", "8h"));
                }
                break;
            default:
                break;

        }
        return result;
    }

    private void createMarkers() {
        //Inglês
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_1, LONGITUDE_HOTEL_1,
                "Majestic Hotel", "Maciel Pinheiro Street, nº 216" + "\n" + "(083) 3341-2009 / 3341-1769", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_2, LONGITUDE_HOTEL_2,
                "Hotel Hellu's", "Maciel Pinheiro Street, nº 313" + "\n" + "(083) 3321-4115 / 8836-2741", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_3, LONGITUDE_HOTEL_3,
                "JK Inn", "Desembargador Trindade Street" + "\n" + "(083) 3341-2794", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_4, LONGITUDE_HOTEL_4,
                "Campina Express Hotel", "Clayton Ismael Street, nº 134" + "\n" + "(083) 3322-8384 / 083 8169-1963", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_5, LONGITUDE_HOTEL_5,
                "Hotel do Vale", "Janúncio Ferreira Street, nº 10" + "\n" + "(083) 3341-3111", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_6, LONGITUDE_HOTEL_6,
                "Hotel Casa Blanca", "Floriano Peixoto Avenue, nº 129" + "\n" + "(083) 3063-2680", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_7, LONGITUDE_HOTEL_7,
                "Onigrat Hotel", "João Lourenço Porto Street, nº 20" + "\n" + "(083) 3341-2929", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_8, LONGITUDE_HOTEL_8,
                "Central Hotel", "Venâncio Neiva Street, nº 200" + "\n" + "(083) 3342-3644 / (083) 3342-3535", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_9, LONGITUDE_HOTEL_9,
                "Titão Plaza Hotel", "Vila Nova da Rainha Street, nº 312" + "\n" + "(083) 3343-5460", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_10, LONGITUDE_HOTEL_10,
                "Hotel Confortel Executive", "27 de Julho Street" + "\n" + "(083) 3341-3111", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_11, LONGITUDE_HOTEL_11,
                "Iguatemi Hotel", "Eutécia Vital Ribeiro Street, nº 185" + "\n" + "(083) 3337-1252", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_12, LONGITUDE_HOTEL_12,
                "Hotel Serrano", "Tavares Cavalcante Street, nº 27" + "\n" + "(083) 3341-3131", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_13, LONGITUDE_HOTEL_13,
                "Marc Center", "Pres. Getúlio Vargas Avenue, nº 369" + "\n" + "(083) 3315-1300", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_14, LONGITUDE_HOTEL_14,
                "Hotel Vitória", "Venâncio Neiva Street, nº 57" + "\n" + "(083) 3342-1003", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_15, LONGITUDE_HOTEL_15,
                "Hotel Getúlio Vargas", "Pres. Getúlio Vargas Avenue, nº 629" + "\n" + "(083) 3331-5093", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_16, LONGITUDE_HOTEL_16,
                "Motel OK", "Narciso Costa Figueiredo Street, nº 1277" + "\n" + "(083) 3331-2034", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_17, LONGITUDE_HOTEL_17,
                "Motel Bel Recanto", "Valeriano Ferreira de Melo Street, nº 2203" + "\n" + "(083) 3331-2034", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_18, LONGITUDE_HOTEL_18,
                "Tonho Inn", "Dep. Ascendino Moura Street, nº 25" + "\n" + "(083) 3337-3770", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_19, LONGITUDE_HOTEL_19,
                "Hotel Campina", "Irineu Joffily Street, nº 115" + "\n" + "(083) 3321-1100", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_20, LONGITUDE_HOTEL_20,
                "Magia do Verde Inn and Restaurant", "Américo Braga Street, nº 33 - sl 203" + "\n" + "(083) 3322-2828", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_21, LONGITUDE_HOTEL_21,
                "Amante Inn", "Cavalcante Belo Street, nº 38A" + "\n" + "(083) 3066-1515", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_22, LONGITUDE_HOTEL_22,
                "Hotel Ariticum", "Near to Lagoa Seca" + "\n" + "(083) 98888-7105/ 99118-2730", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_23, LONGITUDE_HOTEL_23,
                "Itararé Inn", "Senador Argemiro de Figueiredo Avenue,nº 1365" + "\n" + "(083) 3201-1090 / (083) 98751-9767", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_24, LONGITUDE_HOTEL_24,
                "Day Camp – Farm Hotel", "Sítio Lucas Eco Turismo Ltda." + "\n" + "(083) 3339-9494 / 98662-6202", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_25, LONGITUDE_HOTEL_25,
                "Centro Marista de Eventos", "Lagoa Seca" + "\n" + "(083) 3366-1248 / 99972-5333", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_26, LONGITUDE_HOTEL_26,
                "House", "Cristina Procópio Silva Street, nº 218" + "\n" + "(083) 98700-9301 / 99650-6821", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_27, LONGITUDE_HOTEL_27,
                "House", "José de Alencar Street, nº 110" + "\n" + "(083) 98803-7690", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_28, LONGITUDE_HOTEL_28,
                "House", "Chile Street, nº 273" + "\n" + "(083) 98838-2277 / 3322-7123", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_29, LONGITUDE_HOTEL_29,
                "House", "José de Lucena Street, nº 26" + "\n" + "(083) 98746-3537 / (081) 99971-4525", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_30, LONGITUDE_HOTEL_30,
                "House", "Padre Aristídes Lôbo Street, nº 349" + "\n" + "(083) 8845-9759", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_31, LONGITUDE_HOTEL_31,
                "Apartment", "Americo Porto Street, nº 290" + "\n" + "(083) 8889-3559 / 3321-3559", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_32, LONGITUDE_HOTEL_32,
                "House", "Afonso Pena Street, nº 89" + "\n" + "(083) 8732-5020 / 3201-5002", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_33, LONGITUDE_HOTEL_33,
                "House", "Solon de Lucena Street, nº 229" + "\n" + "(083) 3201-2474", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_34, LONGITUDE_HOTEL_34,
                "House", "Otacílio de Albuquerque Street, nº 72" + "\n" + "(083) 99124-2142", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_35, LONGITUDE_HOTEL_35,
                "House", "Papa João Vinte e Três Street, nº 724" + "\n" + "(083) 8763-7277 / 3322-2431", BitmapDescriptorFactory.HUE_AZURE));
        markersUs.add(new MarkerSettings("Accommodation", LATITUDE_HOTEL_36, LONGITUDE_HOTEL_36,
                "House", "Siqueira Campos Street, nº 101" + "\n" + "(083) 98800-7295 / 98898-6321", BitmapDescriptorFactory.HUE_AZURE));

        markersUs.add(new MarkerSettings("Booth", LATITUDE_ESTANDE_1, LONGITUDE_ESTANDE_1,
                "Booth", "", BitmapDescriptorFactory.HUE_CYAN));
        markersUs.add(new MarkerSettings("Booth", LATITUDE_ESTANDE_2, LONGITUDE_ESTANDE_2,
                "Booth", "", BitmapDescriptorFactory.HUE_CYAN));
        markersUs.add(new MarkerSettings("Booth", LATITUDE_ESTANDE_3, LONGITUDE_ESTANDE_3,
                "Booth", "", BitmapDescriptorFactory.HUE_CYAN));
        markersUs.add(new MarkerSettings("Booth", LATITUDE_ESTANDE_4, LONGITUDE_ESTANDE_4,
                "Booth", "", BitmapDescriptorFactory.HUE_CYAN));
        markersUs.add(new MarkerSettings("Booth", LATITUDE_ESTANDE_5, LONGITUDE_ESTANDE_5,
                "Booth", "", BitmapDescriptorFactory.HUE_CYAN));

        markersUs.add(new MarkerSettings("Parking", LATITUDE_ESTACIONAMENTO_1, LONGITUDE_ESTACIONAMENTO_1,
                "Parking", "", BitmapDescriptorFactory.HUE_GREEN));
        markersUs.add(new MarkerSettings("Parking", LATITUDE_ESTACIONAMENTO_2, LONGITUDE_ESTACIONAMENTO_2,
                "Parking", "", BitmapDescriptorFactory.HUE_GREEN));
        markersUs.add(new MarkerSettings("Parking", LATITUDE_ESTACIONAMENTO_3, LONGITUDE_ESTACIONAMENTO_3,
                "Parking", "", BitmapDescriptorFactory.HUE_GREEN));
        markersUs.add(new MarkerSettings("Parking", LATITUDE_ESTACIONAMENTO_4, LONGITUDE_ESTACIONAMENTO_4,
                "Parking", "", BitmapDescriptorFactory.HUE_GREEN));

        markersUs.add(new MarkerSettings("Food court", LATITUDE_ALIMENTACAO_1, LONGITUDE_ALIMENTACAO_1,
                "Food court", "", BitmapDescriptorFactory.HUE_MAGENTA));
        markersUs.add(new MarkerSettings("Food court", LATITUDE_ALIMENTACAO_2, LONGITUDE_ALIMENTACAO_2,
                "Food court", "", BitmapDescriptorFactory.HUE_MAGENTA));

        markersUs.add(new MarkerSettings("Booth Inscription", LATITUDE_INSCRICAO, LONGITUDE_INSCRICAO,
                "Booth Inscription", "Inscription to enter the partners motorcycle clubs", BitmapDescriptorFactory.HUE_ORANGE));

        markersUs.add(new MarkerSettings("Booth of Fome Zero", LATITUDE_FOME_ZERO_1, LONGITUDE_FOME_ZERO_1,
                "Booth of Fome Zero", "", BitmapDescriptorFactory.HUE_RED));
        markersUs.add(new MarkerSettings("Booth of Fome Zero", LATITUDE_FOME_ZERO_2, LONGITUDE_FOME_ZERO_2,
                "Booth of Fome Zero", "", BitmapDescriptorFactory.HUE_RED));

        markersUs.add(new MarkerSettings("WC", LATITUDE_BANHEIRO, LONGITUDE_BANHEIRO,
                "WC", "", BitmapDescriptorFactory.HUE_ROSE));

        markersUs.add(new MarkerSettings("Stage", LATITUDE_PALCO, LONGITUDE_PALCO,
                "Stage", "", BitmapDescriptorFactory.HUE_VIOLET));

        markersUs.add(new MarkerSettings("Blip's", LATITUDE_BLIPS, LONGITUDE_BLIPS,
                "Blip's", "Disclosure of MotoFest partners", HUE_PURPLE));

        markersUs.add(new MarkerSettings("Pavilion", LATITUDE_PAVILHAO_1, LONGITUDE_PAVILHAO_1,
                "Pavilion", "", HUE_VERDON));
        markersUs.add(new MarkerSettings("Pavilion", LATITUDE_PAVILHAO_2, LONGITUDE_PAVILHAO_2,
                "Pavilion", "", HUE_VERDON));

        markersUs.add(new MarkerSettings("Dealers", LATITUDE_CONCESSIONARIAS, LONGITUDE_CONCESSIONARIAS,
                "Dealers", "", HUE_GOLD));

        markersUs.add(new MarkerSettings("Security", LATITUDE_SEGURANCA_1, LONGITUDE_SEGURANCA_1,
                "Security", "Policing and Ambulatory", HUE_CREAM));
        markersUs.add(new MarkerSettings("Security", LATITUDE_SEGURANCA_2, LONGITUDE_SEGURANCA_2,
                "Security", "Policing and Ambulatory", HUE_CREAM));

        markersUs.add(new MarkerSettings("Exit", LATITUDE_SAIDA, LONGITUDE_SAIDA,
                "Exit", "", HUE_SALMOM));
        createMarkersBR();
    }

    private void createMarkersBR() {
        //Português
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_1, LONGITUDE_HOTEL_1,
                "Majestic Hotel", "Rua Maciel Pinheiro, nº 216" + "\n" + "(083) 3341-2009 / 3341-1769", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_2, LONGITUDE_HOTEL_2,
                "Hotel Hellu's", "Rua Maciel Pinheiro, nº 313" + "\n" + "(083) 3321-4115 / 8836-2741", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_3, LONGITUDE_HOTEL_3,
                "Pousada JK", "Rua Desembargador Trindade" + "\n" + "(083) 3341-2794", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_4, LONGITUDE_HOTEL_4,
                "Campina Express Hotel", "Rua Clayton Ismael, nº 134" + "\n" + "(083) 3322-8384 / 083 8169-1963", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_5, LONGITUDE_HOTEL_5,
                "Hotel do Vale", "Rua Janúncio Ferreira, nº 10" + "\n" + "(083) 3341-3111", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_6, LONGITUDE_HOTEL_6,
                "Hotel Casa Blanca", "Av. Floriano Peixoto, nº 129" + "\n" + "(083) 3063-2680", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_7, LONGITUDE_HOTEL_7,
                "Onigrat Hotel", "Rua João Lourenço Porto, nº 20" + "\n" + "(083) 3341-2929", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_8, LONGITUDE_HOTEL_8,
                "Central Hotel", "Rua Venâncio Neiva, nº 200" + "\n" + "(083) 3342-3644 / (083) 3342-3535", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_9, LONGITUDE_HOTEL_9,
                "Titão Plaza Hotel", "Rua Vila Nova da Rainha, nº 312" + "\n" + "(083) 3343-5460", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_10, LONGITUDE_HOTEL_10,
                "Hotel Confortel Executive", "Rua 27 de Julho" + "\n" + "(083) 3341-3111", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_11, LONGITUDE_HOTEL_11,
                "Iguatemi Hotel", "Rua Eutécia Vital Ribeiro, nº 185" + "\n" + "(083) 3337-1252", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_12, LONGITUDE_HOTEL_12,
                "Hotel Serrano", "Rua Tavares Cavalcante, nº 27" + "\n" + "(083) 3341-3131", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_13, LONGITUDE_HOTEL_13,
                "Marc Center", "Av. Pres. Getúlio Vargas, nº 369" + "\n" + "(083) 3315-1300", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_14, LONGITUDE_HOTEL_14,
                "Hotel Vitória", "Rua Venâncio Neiva, nº 57" + "\n" + "(083) 3342-1003", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_15, LONGITUDE_HOTEL_15,
                "Hotel Getúlio Vargas", "Av. Pres. Getúlio Vargas, nº 629" + "\n" + "(083) 3331-5093", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_16, LONGITUDE_HOTEL_16,
                "Motel OK", "Rua Narciso Costa Figueiredo, nº 1277" + "\n" + "(083) 3331-2034", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_17, LONGITUDE_HOTEL_17,
                "Motel Bel Recanto", "Rua Valeriano Ferreira de Melo, nº 2203" + "\n" + "(083) 3331-2034", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_18, LONGITUDE_HOTEL_18,
                "Pousada do Tonho", "Rua Dep. Ascendino Moura, nº 25" + "\n" + "(083) 3337-3770", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_19, LONGITUDE_HOTEL_19,
                "Hotel Campina", "Rua Irineu Joffily, nº 115" + "\n" + "(083) 3321-1100", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_20, LONGITUDE_HOTEL_20,
                "Pousada e Restaurante Magia do Verde", "Rua Américo Braga, nº 33 - sl 203" + "\n" + "(083) 3322-2828", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_21, LONGITUDE_HOTEL_21,
                "Pousada de Amante", "Rua Cavalcante Belo, nº 38A" + "\n" + "(083) 3066-1515", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_22, LONGITUDE_HOTEL_22,
                "Hotel Ariticum", "Próximo a  Lagoa  Seca" + "\n" + "(083) 98888-7105/ 99118-2730", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_23, LONGITUDE_HOTEL_23,
                "Pousada Itararé", "Av. Senador Argemiro de Figueiredo, nº 1365" + "\n" + "(083) 3201-1090 / (083) 98751-9767", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_24, LONGITUDE_HOTEL_24,
                "Day Camp – Hotel Fazenda", "Sítio Lucas Eco Turismo Ltda." + "\n" + "(083) 3339-9494 / 98662-6202", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_25, LONGITUDE_HOTEL_25,
                "Centro Marista de Eventos", "Lagoa Seca" + "\n" + "(083) 3366-1248 / 99972-5333", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_26, LONGITUDE_HOTEL_26,
                "Casa", "Rua Cristina Procópio Silva, nº 218" + "\n" + "(083) 98700-9301 / 99650-6821", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_27, LONGITUDE_HOTEL_27,
                "Casa", "Rua José de Alencar, nº 110" + "\n" + "(083) 98803-7690", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_28, LONGITUDE_HOTEL_28,
                "Casa", "Rua Chile, nº 273" + "\n" + "(083) 98838-2277 / 3322-7123", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_29, LONGITUDE_HOTEL_29,
                "Casa", "Rua José de Lucena, nº 26" + "\n" + "(083) 98746-3537 / (081) 99971-4525", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_30, LONGITUDE_HOTEL_30,
                "Casa", "Rua Padre Aristídes Lôbo, nº 349" + "\n" + "(083) 8845-9759", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_31, LONGITUDE_HOTEL_31,
                "Apartamentos", "Rua Americo Porto, nº 290" + "\n" + "(083) 8889-3559 / 3321-3559", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_32, LONGITUDE_HOTEL_32,
                "Casa", "Rua Afonso Pena, nº 89" + "\n" + "(083) 8732-5020 / 3201-5002", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_33, LONGITUDE_HOTEL_33,
                "Casa", "Rua Solon de Lucena, nº 229" + "\n" + "(083) 3201-2474", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_34, LONGITUDE_HOTEL_34,
                "Casa", "Rua Otacílio de Albuquerque, nº 72" + "\n" + "(083) 99124-2142", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_35, LONGITUDE_HOTEL_35,
                "Casa", "Rua Papa João Vinte e Três, nº 724" + "\n" + "(083) 8763-7277 / 3322-2431", BitmapDescriptorFactory.HUE_AZURE));
        markersBr.add(new MarkerSettings("Hospedagem", LATITUDE_HOTEL_36, LONGITUDE_HOTEL_36,
                "Casa", "Rua Siqueira Campos, nº 101" + "\n" + "(083) 98800-7295 / 98898-6321", BitmapDescriptorFactory.HUE_AZURE));

        markersBr.add(new MarkerSettings("Estande", LATITUDE_ESTANDE_1, LONGITUDE_ESTANDE_1,
                "Estande", "", BitmapDescriptorFactory.HUE_CYAN));
        markersBr.add(new MarkerSettings("Estande", LATITUDE_ESTANDE_2, LONGITUDE_ESTANDE_2,
                "Estande", "", BitmapDescriptorFactory.HUE_CYAN));
        markersBr.add(new MarkerSettings("Estande", LATITUDE_ESTANDE_3, LONGITUDE_ESTANDE_3,
                "Estande", "", BitmapDescriptorFactory.HUE_CYAN));
        markersBr.add(new MarkerSettings("Estande", LATITUDE_ESTANDE_4, LONGITUDE_ESTANDE_4,
                "Estande" , "", BitmapDescriptorFactory.HUE_CYAN));
        markersBr.add(new MarkerSettings("Estande", LATITUDE_ESTANDE_5, LONGITUDE_ESTANDE_5,
                "Estande", "", BitmapDescriptorFactory.HUE_CYAN));

        markersBr.add(new MarkerSettings("Estacionamento", LATITUDE_ESTACIONAMENTO_1, LONGITUDE_ESTACIONAMENTO_1,
                "Estacionamento", "", BitmapDescriptorFactory.HUE_GREEN));
        markersBr.add(new MarkerSettings("Estacionamento", LATITUDE_ESTACIONAMENTO_2, LONGITUDE_ESTACIONAMENTO_2,
                "Estacionamento", "", BitmapDescriptorFactory.HUE_GREEN));
        markersBr.add(new MarkerSettings("Estacionamento", LATITUDE_ESTACIONAMENTO_3, LONGITUDE_ESTACIONAMENTO_3,
                "Estacionamento", "", BitmapDescriptorFactory.HUE_GREEN));
        markersBr.add(new MarkerSettings("Estacionamento", LATITUDE_ESTACIONAMENTO_4, LONGITUDE_ESTACIONAMENTO_4,
                "Estacionamento", "", BitmapDescriptorFactory.HUE_GREEN));

        markersBr.add(new MarkerSettings("Alimentação", LATITUDE_ALIMENTACAO_1, LONGITUDE_ALIMENTACAO_1,
                "Praça de Alimentação", "", BitmapDescriptorFactory.HUE_MAGENTA));
        markersBr.add(new MarkerSettings("Alimentação", LATITUDE_ALIMENTACAO_2, LONGITUDE_ALIMENTACAO_2,
                "Praça de Alimentação", "", BitmapDescriptorFactory.HUE_MAGENTA));

        markersBr.add(new MarkerSettings("Inscrição", LATITUDE_INSCRICAO, LONGITUDE_INSCRICAO,
                "Estande de Inscrição", "Inscriçao para entrar nos motoclubes parceiros", BitmapDescriptorFactory.HUE_ORANGE));

        markersBr.add(new MarkerSettings("Fome Zero", LATITUDE_FOME_ZERO_1, LONGITUDE_FOME_ZERO_1,
                "Estande do Fome Zero", "", BitmapDescriptorFactory.HUE_RED));
        markersBr.add(new MarkerSettings("Fome Zero", LATITUDE_FOME_ZERO_2, LONGITUDE_FOME_ZERO_2,
                "Estande do Fome Zero", "", BitmapDescriptorFactory.HUE_RED));

        markersBr.add(new MarkerSettings("Banheiro", LATITUDE_BANHEIRO, LONGITUDE_BANHEIRO,
                "Banheiros", "Banheiros Químicos", BitmapDescriptorFactory.HUE_ROSE));

        markersBr.add(new MarkerSettings("Palco", LATITUDE_PALCO, LONGITUDE_PALCO,
                "Palco", "Onde as atrações irão apresentar-se", BitmapDescriptorFactory.HUE_VIOLET));

        markersBr.add(new MarkerSettings("Blip's", LATITUDE_BLIPS, LONGITUDE_BLIPS,
                "Blip's", "Divulgação dos parceiros do MotoFest", HUE_PURPLE));

        markersBr.add(new MarkerSettings("Pavilhão", LATITUDE_PAVILHAO_1, LONGITUDE_PAVILHAO_1,
                "Pavilhão", "", HUE_VERDON));
        markersBr.add(new MarkerSettings("Pavilhão", LATITUDE_PAVILHAO_2, LONGITUDE_PAVILHAO_2,
                "Pavilhão", "", HUE_VERDON));

        markersBr.add(new MarkerSettings("Concessionárias", LATITUDE_CONCESSIONARIAS, LONGITUDE_CONCESSIONARIAS,
                "Concessionárias", "", HUE_GOLD));

        markersBr.add(new MarkerSettings("Segurança", LATITUDE_SEGURANCA_1, LONGITUDE_SEGURANCA_1,
                "Segurança", "Policiamento e Ambulatório", HUE_CREAM));
        markersBr.add(new MarkerSettings("Segurança", LATITUDE_SEGURANCA_2, LONGITUDE_SEGURANCA_2,
                "Segurança", "Policiamento e Ambulatório", HUE_CREAM));

        markersBr.add(new MarkerSettings("Saída", LATITUDE_SAIDA, LONGITUDE_SAIDA,
                "Saída", "", HUE_SALMOM));
    }


    public int getNotificacaoDia(String date) {
        switch (date) {
            case TRINTA_DIAS_RESTANTES:
                return NOTIFICATION_TITLE_1;
            case QUINZE_DIAS_RESTANTES:
                return NOTIFICATION_TITLE_2;
            case SETE_DIAS_RESTANTES:
                return NOTIFICATION_TITLE_3;
            default:
                break;
        }

        return 0;
    }

    public List<String> getAllNotification() {
        List<String> listNotification = new ArrayList<>();
        listNotification.add(TRINTA_DIAS_RESTANTES);
        listNotification.add(QUINZE_DIAS_RESTANTES);
        listNotification.add(SETE_DIAS_RESTANTES);
        return listNotification;
    }


}

package com.sec.secwatch.wrapper;

import com.sec.secwatch.R;

/**
 * Created by rbitt on 2017-04-30.
 */

public class GameInfoConverter {

    public static String getTimeString(int time ) {

        if (time > 3600000)
            return String.format("%.0f시간", (float)time / 3600000.0f);
        else if (time > 60000)
            return String.format("%.0f분", (float)time / 60000.0f);
        else
            return String.format("%.0f초",(float)time);
    }

    public static int getSmallPortraitResourceFromString(String name) {
        switch (name) {
            case "리퍼":
                return R.drawable.small_reaper;
            case "아나":
                return R.drawable.small_ana;
            case "바스티온":
                return R.drawable.small_bastion;
            case "디바":
                return R.drawable.small_dva;
            case "겐지":
                return R.drawable.small_genji;
            case "한조":
                return R.drawable.small_hanzo;
            case "정크랫":
                return R.drawable.small_junkrat;
            case "루시우":
                return R.drawable.small_lucio;
            case "맥크리":
                return R.drawable.small_mccree;
            case "메이":
                return R.drawable.small_mei;
            case "메르시":
                return R.drawable.small_mercy;
            case "오리사":
                return R.drawable.small_orisa;
            case "파라":
                return R.drawable.small_pharah;
            case "라인하르트":
                return R.drawable.small_reinhardt;
            case "로드호그":
                return R.drawable.small_roadhog;
            case "솔져:76":
                return R.drawable.small_soldier76;
            case "솜브라":
                return R.drawable.small_sombra;
            case "시메트라":
                return R.drawable.small_symmetra;
            case "토르비욘":
                return R.drawable.small_torbjorn;
            case "트레이서":
                return R.drawable.small_tracer;
            case "위도우메이커":
                return R.drawable.small_widowmaker;
            case "윈스턴":
                return R.drawable.small_winston;
            case "자리야":
                return R.drawable.small_zarya;
            case "젠야타":
                return R.drawable.small_zenyatta;
        }
        return 0;
    }
}

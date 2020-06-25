package com.digitaldreamsapps.dierhanna.util;

import java.util.Locale;

public class Config {
    public static Language language;


    public static String setLanguageConfig(String l){
        if (l.equals("ar")){
            Config.language= Language.ARABIC;
        }else{
            if (l.equals("iw")){
                Config.language= Language.HEBREW;
            }else{
                if (l.equals("")){
                    l = Locale.getDefault().getLanguage();
                    if (l.equals("ar")){
                        Config.language= Language.ARABIC;
                    }else{
                        if (l.equals("iw")){
                            Config.language= Language.HEBREW;
                        }else{
                            l="en";
                            Config.language= Language.ENGLISH;
                        }
                    }

                }else{
                    l="en";
                    Config.language= Language.ENGLISH;
                }
            }
        }

        return l;
    }





}

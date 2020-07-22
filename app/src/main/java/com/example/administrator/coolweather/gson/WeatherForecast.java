package com.example.administrator.coolweather.gson;


import java.util.List;

public class WeatherForecast {

    /**
     * code : 200
     * updateTime : 2020-07-22T10:56+08:00
     * fxLink : http://hfx.link/2ax1
     * daily : [{"fxDate":"2020-07-22","sunrise":"05:05","sunset":"19:35","moonrise":"06:18","moonset":"21:02","moonPhase":"峨眉月","tempMax":"33","tempMin":"22","iconDay":"101","textDay":"多云","iconNight":"101","textNight":"多云","wind360Day":"143","windDirDay":"东南风","windScaleDay":"1-2","windSpeedDay":"3","wind360Night":"165","windDirNight":"东南风","windScaleNight":"1-2","windSpeedNight":"7","humidity":"58","precip":"0.0","pressure":"1002","vis":"25","cloud":"25","uvIndex":"6"},{"fxDate":"2020-07-23","sunrise":"05:06","sunset":"19:35","moonrise":"07:30","moonset":"21:38","moonPhase":"峨眉月","tempMax":"34","tempMin":"22","iconDay":"101","textDay":"多云","iconNight":"150","textNight":"晴","wind360Day":"106","windDirDay":"东南风","windScaleDay":"1-2","windSpeedDay":"6","wind360Night":"350","windDirNight":"北风","windScaleNight":"1-2","windSpeedNight":"1","humidity":"47","precip":"0.0","pressure":"1002","vis":"25","cloud":"1","uvIndex":"5"},{"fxDate":"2020-07-24","sunrise":"05:07","sunset":"19:34","moonrise":"08:42","moonset":"22:10","moonPhase":"峨眉月","tempMax":"35","tempMin":"23","iconDay":"100","textDay":"晴","iconNight":"150","textNight":"晴","wind360Day":"264","windDirDay":"西风","windScaleDay":"1-2","windSpeedDay":"11","wind360Night":"262","windDirNight":"西风","windScaleNight":"1-2","windSpeedNight":"4","humidity":"61","precip":"0.0","pressure":"1001","vis":"25","cloud":"1","uvIndex":"11"}]
     * refer : {"sources":["Weather China"],"license":["no commercial use"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    private ReferBean refer;
    private List<DailyBean> daily;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
    }

    public static class ReferBean {
        private List<String> sources;
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }

    public static class DailyBean {
        /**
         * fxDate : 2020-07-22
         * sunrise : 05:05
         * sunset : 19:35
         * moonrise : 06:18
         * moonset : 21:02
         * moonPhase : 峨眉月
         * tempMax : 33
         * tempMin : 22
         * iconDay : 101
         * textDay : 多云
         * iconNight : 101
         * textNight : 多云
         * wind360Day : 143
         * windDirDay : 东南风
         * windScaleDay : 1-2
         * windSpeedDay : 3
         * wind360Night : 165
         * windDirNight : 东南风
         * windScaleNight : 1-2
         * windSpeedNight : 7
         * humidity : 58
         * precip : 0.0
         * pressure : 1002
         * vis : 25
         * cloud : 25
         * uvIndex : 6
         */

        private String fxDate;
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        private String moonPhase;
        private String tempMax;
        private String tempMin;
        private String iconDay;
        private String textDay;
        private String iconNight;
        private String textNight;
        private String wind360Day;
        private String windDirDay;
        private String windScaleDay;
        private String windSpeedDay;
        private String wind360Night;
        private String windDirNight;
        private String windScaleNight;
        private String windSpeedNight;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String uvIndex;

        public String getFxDate() {
            return fxDate;
        }

        public void setFxDate(String fxDate) {
            this.fxDate = fxDate;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getMoonrise() {
            return moonrise;
        }

        public void setMoonrise(String moonrise) {
            this.moonrise = moonrise;
        }

        public String getMoonset() {
            return moonset;
        }

        public void setMoonset(String moonset) {
            this.moonset = moonset;
        }

        public String getMoonPhase() {
            return moonPhase;
        }

        public void setMoonPhase(String moonPhase) {
            this.moonPhase = moonPhase;
        }

        public String getTempMax() {
            return tempMax;
        }

        public void setTempMax(String tempMax) {
            this.tempMax = tempMax;
        }

        public String getTempMin() {
            return tempMin;
        }

        public void setTempMin(String tempMin) {
            this.tempMin = tempMin;
        }

        public String getIconDay() {
            return iconDay;
        }

        public void setIconDay(String iconDay) {
            this.iconDay = iconDay;
        }

        public String getTextDay() {
            return textDay;
        }

        public void setTextDay(String textDay) {
            this.textDay = textDay;
        }

        public String getIconNight() {
            return iconNight;
        }

        public void setIconNight(String iconNight) {
            this.iconNight = iconNight;
        }

        public String getTextNight() {
            return textNight;
        }

        public void setTextNight(String textNight) {
            this.textNight = textNight;
        }

        public String getWind360Day() {
            return wind360Day;
        }

        public void setWind360Day(String wind360Day) {
            this.wind360Day = wind360Day;
        }

        public String getWindDirDay() {
            return windDirDay;
        }

        public void setWindDirDay(String windDirDay) {
            this.windDirDay = windDirDay;
        }

        public String getWindScaleDay() {
            return windScaleDay;
        }

        public void setWindScaleDay(String windScaleDay) {
            this.windScaleDay = windScaleDay;
        }

        public String getWindSpeedDay() {
            return windSpeedDay;
        }

        public void setWindSpeedDay(String windSpeedDay) {
            this.windSpeedDay = windSpeedDay;
        }

        public String getWind360Night() {
            return wind360Night;
        }

        public void setWind360Night(String wind360Night) {
            this.wind360Night = wind360Night;
        }

        public String getWindDirNight() {
            return windDirNight;
        }

        public void setWindDirNight(String windDirNight) {
            this.windDirNight = windDirNight;
        }

        public String getWindScaleNight() {
            return windScaleNight;
        }

        public void setWindScaleNight(String windScaleNight) {
            this.windScaleNight = windScaleNight;
        }

        public String getWindSpeedNight() {
            return windSpeedNight;
        }

        public void setWindSpeedNight(String windSpeedNight) {
            this.windSpeedNight = windSpeedNight;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPrecip() {
            return precip;
        }

        public void setPrecip(String precip) {
            this.precip = precip;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getUvIndex() {
            return uvIndex;
        }

        public void setUvIndex(String uvIndex) {
            this.uvIndex = uvIndex;
        }
    }
}

package com.example.administrator.coolweather.gson;


import java.util.List;

public class Lifestyle {


    /**
     * code : 200
     * updateTime : 2020-07-22T09:10+08:00
     * fxLink : http://hfx.link/2ax2
     * daily : [{"date":"2020-07-22","type":"13","name":"化妆指数","level":"6","category":"防脱水","text":"天气较热，易出汗，建议使用防脱水化妆品，少用粉底和胭脂，经常补粉。"},
     * {"date":"2020-07-22","type":"6","name":"旅游指数","level":"2","category":"较适宜","text":"天气较好，温度较高，天气较热，但有微风相伴，还是比较适宜旅游的，不过外出时要注意防暑防晒哦！"},
     * {"date":"2020-07-22","type":"14","name":"晾晒指数","level":"2","category":"适宜","text":"天气不错，适宜晾晒。赶紧把久未见阳光的衣物搬出来吸收一下太阳的味道吧！"},
     * {"date":"2020-07-22","type":"15","name":"交通指数","level":"1","category":"良好","text":"天气较好，路面干燥，交通气象条件良好，车辆可以正常行驶。"},
     * {"date":"2020-07-22","type":"10","name":"空气污染扩散条件指数","level":"2","category":"中","text":"气象条件对空气污染物稀释、扩散和清除无明显影响。"},
     * {"date":"2020-07-22","type":"3","name":"穿衣指数","level":"7","category":"炎热","text":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"},
     * {"date":"2020-07-22","type":"16","name":"防晒指数","level":"3","category":"中等","text":"属中等强度紫外辐射天气，注意防护，建议涂擦SPF指数高于15，PA+的防晒护肤品。"},
     * {"date":"2020-07-22","type":"4","name":"钓鱼指数","level":"2","category":"较适宜","text":"较适合垂钓，但风力稍大，会对垂钓产生一定的影响。"},
     * {"date":"2020-07-22","type":"5","name":"紫外线指数","level":"3","category":"中等","text":"属中等强度紫外线辐射天气，建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},
     * {"date":"2020-07-22","type":"11","name":"空调开启指数","level":"2","category":"部分时间开启","text":"天气热，再加上近两天的较高温天气您将感到些燥热，因此需要适当开启制冷空调来降低温度。"},
     * {"date":"2020-07-22","type":"12","name":"太阳镜指数","level":"4","category":"很必要","text":"白天虽有白云遮挡，但太阳辐射仍很强，建议佩戴透射比2级且标注UV400的遮阳镜"},
     * {"date":"2020-07-22","type":"7","name":"过敏指数","level":"2","category":"不易发","text":"天气条件不易诱发过敏，除特殊体质外，无需担心过敏问题。"},
     * {"date":"2020-07-22","type":"8","name":"舒适度指数","level":"3","category":"较不舒适","text":"白天天气多云，同时会感到有些热，不很舒适。"},
     * {"date":"2020-07-22","type":"9","name":"感冒指数","level":"1","category":"少发","text":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},
     * {"date":"2020-07-22","type":"2","name":"洗车指数","level":"1","category":"适宜","text":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"},
     * {"date":"2020-07-22","type":"1","name":"运动指数","level":"2","category":"较适宜","text":"天气较好，较适宜进行各种运动，但因天气热，请适当减少运动时间，降低运动强度。"}]
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
         * date : 2020-07-22
         * type : 13
         * name : 化妆指数
         * level : 6
         * category : 防脱水
         * text : 天气较热，易出汗，建议使用防脱水化妆品，少用粉底和胭脂，经常补粉。
         */

        private String date;
        private String type;
        private String name;
        private String level;
        private String category;
        private String text;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

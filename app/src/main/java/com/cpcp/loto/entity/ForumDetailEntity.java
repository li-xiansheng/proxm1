package com.cpcp.loto.entity;

import java.util.List;

/**
 * 功能描述：论坛详细
 */

public class ForumDetailEntity {


    /**
     * id : 1
     * full_name : 13981798782
     * email : 2448021477@qq.com
     * title : 标题
     * msg : 内容
     * createtime : 2017-04-05 09:54:16
     * status : 1
     * is_zhiding : 0
     * is_jinghua : 0
     * readnum : 8
     * qishu : 021期
     * userinfo : {"user_nicename":"","mobile":"13981798782","avatar":"lac.83gw.com/data/upload/avatar/590ac151185f1.png","score":"18"}
     * reply : [{"id":"1","full_name":"18615773227","email":"","title":null,"msg":"这里是回复\r\n1","createtime":"2017-04-05 10:23:30","status":"1","parent_id":"1","tworeply":[{"id":"9","full_name":"18627075341","email":"","title":null,"msg":"Xxcccvv","createtime":"2017-04-20 21:52:15","status":"1","parent_id":"1","tworeply":"1"},{"id":"24","full_name":"18627075341","email":"","title":null,"msg":"Wqqqqwwwwww","createtime":"2017-04-20 22:25:48","status":"1","parent_id":"1","tworeply":"1"},{"id":"25","full_name":"18627075341","email":"","title":null,"msg":"Fffff","createtime":"2017-04-20 22:31:44","status":"1","parent_id":"1","tworeply":"1"},{"id":"11","full_name":"18627075341","email":"","title":null,"msg":"Dfghh弟弟妹妹吃吃吃吃吃吃吃吃吃吃吃吃吃吃吃吃反反复复哥哥哥哥哥哥","createtime":"2017-04-20 21:52:49","status":"1","parent_id":"1","tworeply":"1"},{"id":"13","full_name":"18627075341","email":"","title":null,"msg":"广告歌","createtime":"2017-04-20 21:55:01","status":"1","parent_id":"1","tworeply":"1"},{"id":"23","full_name":"18627075341","email":"","title":null,"msg":"Ggvv","createtime":"2017-04-20 22:25:33","status":"1","parent_id":"1","tworeply":"1"},{"id":"16","full_name":"18627075341","email":"","title":null,"msg":"反反复复感觉","createtime":"2017-04-20 22:00:52","status":"1","parent_id":"1","tworeply":"1"},{"id":"17","full_name":"18627075341","email":"","title":null,"msg":"gfhhtrr ","createtime":"2017-04-20 22:02:15","status":"1","parent_id":"1","tworeply":"1"},{"id":"18","full_name":"18627075341","email":"","title":null,"msg":"Fffffff风反反复复反反复复方法","createtime":"2017-04-20 22:02:56","status":"1","parent_id":"1","tworeply":"1"}],"userinfo":{"user_nicename":"","mobile":"18615773227","avatar":"lac.83gw.com/data/upload/avatar/avatar.png","score":"96"}},{"id":"2","full_name":"18615773227","email":"","title":"回复标题","msg":"回复内容","createtime":"2017-04-11 09:32:12","status":"1","parent_id":"1","tworeply":[{"id":"3","full_name":"13981798782","email":"","title":null,"msg":"222222","createtime":"2017-04-18 14:50:55","status":"1","parent_id":"2","tworeply":"1"},{"id":"4","full_name":"13981798782","email":"","title":null,"msg":"222222","createtime":"2017-04-18 14:52:31","status":"1","parent_id":"2","tworeply":"1"},{"id":"5","full_name":"13981798782","email":"","title":null,"msg":"222222","createtime":"2017-04-18 14:53:02","status":"1","parent_id":"2","tworeply":"1"},{"id":"6","full_name":"13981798782","email":"","title":null,"msg":"222222","createtime":"2017-04-18 14:53:26","status":"1","parent_id":"2","tworeply":"1"},{"id":"28","full_name":"18627075341","email":"","title":null,"msg":"Gggggggg滴滴答答滴滴答答滴滴答答滴滴答答滴滴答答","createtime":"2017-04-20 22:33:11","status":"1","parent_id":"2","tworeply":"1"}],"userinfo":{"user_nicename":"","mobile":"18615773227","avatar":"lac.83gw.com/data/upload/avatar/avatar.png","score":"96"}},{"id":"26","full_name":"18627075341","email":"","title":null,"msg":"Vvcvvcccccc","createtime":"2017-04-20 22:32:03","status":"1","parent_id":"1","tworeply":[],"userinfo":{"user_nicename":"","mobile":"18627075341","avatar":"lac.83gw.com/data/upload/avatar/590b279ee91e3.jpeg","score":"6021"}}]
     */

    private String id;
    private String full_name;
    private String email;
    private String title;
    private String msg;
    private String createtime;
    private String status;
    private String is_zhiding;
    private String is_jinghua;
    private String readnum;
    private String qishu;
    private UserinfoBean userinfo;
    private List<ReplyBean> reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_zhiding() {
        return is_zhiding;
    }

    public void setIs_zhiding(String is_zhiding) {
        this.is_zhiding = is_zhiding;
    }

    public String getIs_jinghua() {
        return is_jinghua;
    }

    public void setIs_jinghua(String is_jinghua) {
        this.is_jinghua = is_jinghua;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class UserinfoBean {
        /**
         * user_nicename :
         * mobile : 13981798782
         * avatar : lac.83gw.com/data/upload/avatar/590ac151185f1.png
         * score : 18
         */

        private String user_nicename;
        private String mobile;
        private String avatar;
        private String score;

        public String getUser_nicename() {
            return user_nicename;
        }

        public void setUser_nicename(String user_nicename) {
            this.user_nicename = user_nicename;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    public static class ReplyBean {
        /**
         * id : 1
         * full_name : 18615773227
         * email :
         * title : null
         * msg : 这里是回复
         1
         * createtime : 2017-04-05 10:23:30
         * status : 1
         * parent_id : 1
         * tworeply : [{"id":"9","full_name":"18627075341","email":"","title":null,"msg":"Xxcccvv","createtime":"2017-04-20 21:52:15","status":"1","parent_id":"1","tworeply":"1"},{"id":"24","full_name":"18627075341","email":"","title":null,"msg":"Wqqqqwwwwww","createtime":"2017-04-20 22:25:48","status":"1","parent_id":"1","tworeply":"1"},{"id":"25","full_name":"18627075341","email":"","title":null,"msg":"Fffff","createtime":"2017-04-20 22:31:44","status":"1","parent_id":"1","tworeply":"1"},{"id":"11","full_name":"18627075341","email":"","title":null,"msg":"Dfghh弟弟妹妹吃吃吃吃吃吃吃吃吃吃吃吃吃吃吃吃反反复复哥哥哥哥哥哥","createtime":"2017-04-20 21:52:49","status":"1","parent_id":"1","tworeply":"1"},{"id":"13","full_name":"18627075341","email":"","title":null,"msg":"广告歌","createtime":"2017-04-20 21:55:01","status":"1","parent_id":"1","tworeply":"1"},{"id":"23","full_name":"18627075341","email":"","title":null,"msg":"Ggvv","createtime":"2017-04-20 22:25:33","status":"1","parent_id":"1","tworeply":"1"},{"id":"16","full_name":"18627075341","email":"","title":null,"msg":"反反复复感觉","createtime":"2017-04-20 22:00:52","status":"1","parent_id":"1","tworeply":"1"},{"id":"17","full_name":"18627075341","email":"","title":null,"msg":"gfhhtrr ","createtime":"2017-04-20 22:02:15","status":"1","parent_id":"1","tworeply":"1"},{"id":"18","full_name":"18627075341","email":"","title":null,"msg":"Fffffff风反反复复反反复复方法","createtime":"2017-04-20 22:02:56","status":"1","parent_id":"1","tworeply":"1"}]
         * userinfo : {"user_nicename":"","mobile":"18615773227","avatar":"lac.83gw.com/data/upload/avatar/avatar.png","score":"96"}
         */

        private String id;
        private String full_name;
        private String email;
        private String title;
        private String msg;
        private String createtime;
        private String status;
        private String parent_id;
        private UserinfoBean userinfo;
        private List<TworeplyBean> tworeply;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public List<TworeplyBean> getTworeply() {
            return tworeply;
        }

        public void setTworeply(List<TworeplyBean> tworeply) {
            this.tworeply = tworeply;
        }


        public static class TworeplyBean {
            /**
             * id : 9
             * full_name : 18627075341
             * email :
             * title : null
             * msg : Xxcccvv
             * createtime : 2017-04-20 21:52:15
             * status : 1
             * parent_id : 1
             * tworeply : 1
             */

            private String id;
            private String full_name;
            private String email;
            private String title;
            private String msg;
            private String createtime;
            private String status;
            private String parent_id;
            private String tworeply;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getTworeply() {
                return tworeply;
            }

            public void setTworeply(String tworeply) {
                this.tworeply = tworeply;
            }
        }
    }
}

package com.example.franklin.conference.Data;

import java.util.List;

// 从服务器获取的所有的会议的全部信息，经过Json解析后所形成的类
public class ConferenceData {
    public int count;
    public String next;
    public String previous;
    public List<Results> results;

    /*******************************************************************************************/
    //内部类Results
    public static class Results {
        public int id;
        public Conference_place conference_place;
        public Type type;
        public Moderator moderator;
        public String name;
        public String conference_content;
        public String moderator_profile;
        public boolean is_public;
        public String imge;
        public String conference_time;
        public int participants_nums;
        public int fav_nums;
        public String add_time;

        /**************************************************************************************/
        //内部类Conference_place
        public static class Conference_place {
            public int id;
            public String name;
            public String place_content;
            public String add_time;
        }

        //内部类Type
        public static class Type {
            public int id;
            public String name;
            public String type_content;
            public String add_time;
        }

        //内部类moderator
        public static class Moderator {
            public int id;
            public String username;
            public String profile;
        }
        /*************************************************************************************/
    }
}

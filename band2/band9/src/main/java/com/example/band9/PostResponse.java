package com.example.band9;

import java.util.List;

public class PostResponse {
    public ResultData result_data;

    public class ResultData {
        public List<PostItem> items;

        public class PostItem {
            public String post_key;
        }
    }
}


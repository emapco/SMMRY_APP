package com.manny.smmry;

public class SMMRYAPI {

    public String sm_api_character_count;
    public String sm_api_title;
    public String sm_api_content;
    public String sm_api_limitation;
    public String sm_api_message;
    public String sm_api_error;
    public String[] sm_api_keyword_array;
    public int sm_api_credit_cost;
    public String sm_api_credit_balance;

    public String get_sm_api_content() {
        return sm_api_content;
    }

    public String get_sm_api_message() {
        return sm_api_message;
    }

    public String get_sm_api_character_count() {
        return sm_api_character_count;
    }

    public String get_sm_api_title() {
        return sm_api_title;
    }

    public String get_sm_api_limitation() {
        return sm_api_limitation;
    }

    public String get_sm_api_error() {
        return sm_api_error;
    }

    public String[] get_sm_api_keyword_array() {
        return sm_api_keyword_array;
    }

    public int get_sm_api_credit_cost() {
        return sm_api_credit_cost;
    }

    public String get_sm_api_credit_balance() {
        return sm_api_credit_balance;
    }
}
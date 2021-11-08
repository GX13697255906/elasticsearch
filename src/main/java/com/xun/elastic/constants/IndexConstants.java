package com.xun.elastic.constants;

/**
 * @author xun.guo
 * <p>
 * 索引 枚举
 */
public enum IndexConstants {

    TEST_INDEX("test"),
    PERSON_INDEX("person"),
    BANK_INDEX("bank"),
    POWER_PLANT_REGISTER_INFO_INDEX("biz_power_plant_register_info"),
    UNIT_PARAM_REGISTER_INDEX("biz_unit_param_register_info");


    private String indexName;

    private IndexConstants(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}

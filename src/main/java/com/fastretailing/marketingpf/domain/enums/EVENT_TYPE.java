package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum EVENT_TYPE {

    USER_ATTRIBUTE(1, "ユーザ属性"),
    ADD_TO_CART(2, "カート追加"),
    MOVE_TO_PURCHASE_PAGE(3, "購入ページ遷移"),
    VIEW_PRODUCT_DETAIL(4, "商品詳細閲覧"),
    EC_PURCHASE_COMPLETED(5, "購入完了（EC）"),
    EC_SHIPPING(6, "出荷（EC）"),
    STORE_PURCHASE_COMPLETED(7, "店舗購買完了"),
    REMOVE_FROM_CART(8, "カート削除"),
    PAGE_VIEW(9, "ページビュー"),
    ADD_TO_FAVORITE_PRODUCT(10, "お気に入り（商品）追加"),
    REGISTER_RESTOCK_NOTIFICATION(11, "再入荷通知登録"),
    START_UP(12, "起動"),
    EC_SHIPPING_STORE_PURCHASE(13, "購買完了(店舗&EC出荷)"),
    ;

    @Getter
    private int value;

    @Getter
    private String label;

    EVENT_TYPE(int value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * Create from value
     *
     * @param value
     * @return EVENT_TYPE
     */
    public static EVENT_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (EVENT_TYPE eventType : values()) {
            if (value.equals(eventType.getValueAsString())) {
                return eventType;
            }
        }
        return null;
    }

    /**
     * Get value of enum as String
     *
     * @return String
     */
    public String getValueAsString() {
        return String.valueOf(this.value);
    }
}

package com.fastretailing.marketingpf.domain.enums;

import lombok.Getter;

public enum INPUT_TYPE {

    TEXT_BOX_TEXT(1, true, true, "Text box (text)", "テキストボックス（テキスト）"),
    TEXT_BOX_NUMERICAL_VALUE(2, true, false, "Text box (Numerical value)", "テキストボックス（数値）"),
    SINGLE_SELECTION_TEXT(3, true, true, "Single selection (text)", "単一選択（テキスト）"),
    SINGLE_SELECTION_NUMERICAL_VALUE(4, true, false, "Single selection (Numerical value)", "単一選択（数値）"),
    SINGLE_SELECTION_API_TEXT(5, true, true, "Single selection (API) (text)", "単一選択（API）（テキスト）"),
    SINGLE_SELECTION_API_NUMERICAL_VALUE(6, true, false, "Single selection (API) (Numerical value)", "単一選択（API）（数値）"),
    MULTIPLE_SELECTION_TEXT(7, false, true, "Multiple selection (text)", "複数選択（テキスト）"),
    MULTIPLE_SELECTION_NUMERICAL_VALUE(8, false, false, "Multiple selection (Numerical value)", "複数選択（数値）"),
    MULTIPLE_SELECTION_API_TEXT(9, false, true, "Multiple selection (API) (text)", " 複数選択（API）（テキスト）"),
    MULTIPLE_SELECTION_API_NUMERICAL_VALUE(10, false, false, "Multiple selection (API) (Numerical value)", "複数選択（API）（数値）"),
    ;

    @Getter
    private int value;

    @Getter
    private String name;

    @Getter
    private String jpName;

    @Getter
    private boolean isSingleValue;

    @Getter
    private boolean isStringType;

    INPUT_TYPE(int value, boolean isSingleValue, boolean isStringType, String name, String jpName) {
        this.value = value;
        this.isSingleValue = isSingleValue;
        this.isStringType = isStringType;
        this.name = name;
        this.jpName = jpName;
    }

    /**
     * Create from value
     *
     * @param value
     * @return INPUT_TYPE
     */
    public static INPUT_TYPE createFromValue(String value) {
        if (value == null) {
            return null;
        }
        for (INPUT_TYPE inputType : values()) {
            if (value.equals(inputType.getValueAsString())) {
                return inputType;
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

    /**
     * check input type is numberical value or not
     *
     * @return boolean
     */
    public boolean isNumericalValue() {
        return this == INPUT_TYPE.TEXT_BOX_NUMERICAL_VALUE || this == INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE || this == INPUT_TYPE.SINGLE_SELECTION_API_NUMERICAL_VALUE
                || this == INPUT_TYPE.MULTIPLE_SELECTION_NUMERICAL_VALUE || this == INPUT_TYPE.MULTIPLE_SELECTION_API_NUMERICAL_VALUE;
    }

    /**
     * check input type exists choiceslist available
     *
     * @return boolean
     */
    public boolean hasChoiceslistAvailable() {
        return this == INPUT_TYPE.SINGLE_SELECTION_TEXT || this == INPUT_TYPE.SINGLE_SELECTION_NUMERICAL_VALUE
                || this == INPUT_TYPE.MULTIPLE_SELECTION_TEXT || this == INPUT_TYPE.MULTIPLE_SELECTION_NUMERICAL_VALUE;
    }
}

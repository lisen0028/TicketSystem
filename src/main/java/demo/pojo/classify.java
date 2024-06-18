package demo.pojo;

/**
 * @Author
 * @Date 2024/6/16 12:25
 * @Description:
 */
public enum classify {
    CONCERT("concert"),
    THEATER("theater"),
    SPORTS("sports"),
    KIDS("kids");

    // 枚举常量的构造参数
    private final String value;

    // 构造函数，用于初始化枚举常量的构造参数
    classify(String value) {
        this.value = value;
    }

    // 获取枚举常量的值
    public String getValue() {
        return value;
    }

    // 重写 toString 方法，方便输出枚举常量的字符串表示
    @Override
    public String toString() {
        return value;
    }

    // 通过字符串值获取对应的枚举常量
    public static classify fromValue(String value) {
        for (classify classify : values()) {
            if (classify.value.equals(value)) {
                return classify;
            }
        }
        throw new IllegalArgumentException("Unknown classify value: " + value);
    }
}


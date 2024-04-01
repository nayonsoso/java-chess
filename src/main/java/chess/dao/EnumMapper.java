package chess.dao;

import java.util.Arrays;

public class EnumMapper {

    private EnumMapper() {
    }

    public static <E extends Enum<E>> E mapToEnum(Class<E> enumClass, String input) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equals(input))
                .findAny()
                .orElseThrow(() -> {
                    String message = formatErrorMessage(enumClass.getSimpleName());
                    return new IllegalArgumentException(message);
                });
    }

    public static <E extends Enum<E>> String mapToString(E e) {
        return e.name();
    }

    private static String formatErrorMessage(String type) {
        return String.format("%s로 매핑할 수 없습니다.", type);
    }
}

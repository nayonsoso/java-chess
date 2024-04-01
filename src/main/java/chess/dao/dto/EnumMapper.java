package chess.dao.dto;

import java.util.Arrays;

public class EnumMapper {

    public static <E extends Enum<E>> E mapToEnum(Class<E> enumClass, String input) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumConstant -> enumConstant.name().equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(formatErrorMessage(enumClass.getName())));
    }

    public static <E extends Enum<E>> String mapToString(E e) {
        return e.name();
    }

    private static String formatErrorMessage(String type) {
        return String.format("%로 매핑할 수 없습니다.", type);
    }
}

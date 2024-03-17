package intellispaces.templateengine.function.common;

import intellispaces.commons.CharFunctions;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public interface StringFunctions {

  static boolean isWordChar(char ch) {
    return isLetter(ch) || isDigit(ch) || (ch == '_');
  }

  static boolean isBeginWithLineBreakIgnoreBlanks(String string) {
    char[] chars = string.toCharArray();
    int length = chars.length;
    if (length > 0) {
      int index = 0;
      while (index < length && CharFunctions.isBlankChar(chars[index])) {
        index++;
      }
      if ((index < length && chars[index] == '\n') || (index + 1 < length && chars[index] == '\r' && chars[index + 1] == '\n')) {
        return true;
      }
    }
    return false;
  }

  static boolean isEndWithLineBreakIgnoreBlanks(String string) {
    char[] chars = string.toCharArray();
    int length = chars.length;
    if (length > 0) {
      int index = chars.length - 1;
      while (index >= 0 && CharFunctions.isBlankChar(chars[index])) {
        index--;
      }
      if (
          (index >= 0 && chars[index] == '\n')
              || (index - 1 >= 0 && chars[index - 1] == '\r' && chars[index] == '\n')
              || (index - 1 >= 0 && chars[index - 1] == '\n' && chars[index] == '\r')
      ) {
        return true;
      }
    }
    return false;
  }

  static String removeLastBlanks(String string) {
    char[] chars = string.toCharArray();
    int length = chars.length;
    if (length > 0) {
      int index = chars.length - 1;
      while (index >= 0 && CharFunctions.isBlankChar(chars[index])) {
        index--;
      }
      return string.substring(0, index + 1);
    }
    return string;
  }

  static String removeFirstBlanksAndLineBreak(String string) {
    char[] chars = string.toCharArray();
    int length = chars.length;
    if (length > 0) {
      int index = 0;
      while (index < length && CharFunctions.isBlankChar(chars[index])) {
        index++;
      }
      if (index + 1 < length && ((chars[index] == '\r' && chars[index + 1] == '\n') || (chars[index] == '\n' && chars[index + 1] == '\r'))) {
        return string.substring(index + 2);
      } else if (index < length && chars[index] == '\n') {
        return string.substring(index + 1);
      }
    }
    return string;
  }
}
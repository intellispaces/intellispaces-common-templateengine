package intellispaces.templateengine.object.value;

import intellispaces.templateengine.exception.NotApplicableOperationException;
import intellispaces.templateengine.exception.ResolveTemplateException;
import intellispaces.templateengine.function.cast.CastFunctions;
import intellispaces.templateengine.model.value.BooleanValue;
import intellispaces.templateengine.model.value.IntegerValue;
import intellispaces.templateengine.model.value.ListValue;
import intellispaces.templateengine.model.value.MapValue;
import intellispaces.templateengine.model.value.RealValue;
import intellispaces.templateengine.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.same;

/**
 * Tests for {@link IntegerValueBuilder}.
 */
public class IntegerValueTest {

  @Test
  public void testTypename() {
    assertThat(IntegerValueBuilder.build(123).typename().get()).isEqualTo(ValueTypes.Integer.typename());
  }

  @Test
  public void testAsBoolean() throws Exception {
    // Given
    IntegerValue integerValue = IntegerValueBuilder.build(1);
    try (MockedStatic<CastFunctions> castFunctions = Mockito.mockStatic(CastFunctions.class)) {
      boolean expectedValue = true;
      castFunctions.when(() -> CastFunctions.castToBoolean(integerValue)).thenReturn(expectedValue);

      // When
      BooleanValue booleanValue = integerValue.asBoolean();

      // Then
      assertThat(booleanValue.get()).isTrue();
      castFunctions.verify(() -> CastFunctions.castToBoolean(same(integerValue)), Mockito.times(1));
    }
  }

  @Test
  public void testAsInteger() throws ResolveTemplateException {
    IntegerValue integerValue = IntegerValueBuilder.build(123);
    assertThat(integerValue.asInteger()).isSameAs(integerValue);
  }

  @Test
  public void testAsReal() throws Exception {
    // Given
    IntegerValue integerValue = IntegerValueBuilder.build(123);
    try (MockedStatic<CastFunctions> castFunctions = Mockito.mockStatic(CastFunctions.class)) {
      double expectedValue = 123.0;
      castFunctions.when(() -> CastFunctions.castToReal(integerValue)).thenReturn(expectedValue);

      // When
      RealValue realValue = integerValue.asReal();

      // Then
      assertThat(realValue.get()).isEqualTo(expectedValue);
      castFunctions.verify(() -> CastFunctions.castToReal(same(integerValue)), Mockito.times(1));
    }
  }

  @Test
  public void testAString() throws Exception {
    // Given
    IntegerValue integerValue = IntegerValueBuilder.build(123);
    try (MockedStatic<CastFunctions> castFunctions = Mockito.mockStatic(CastFunctions.class)) {
      String expectedValue = "123";
      castFunctions.when(() -> CastFunctions.castToString(integerValue)).thenReturn(expectedValue);

      // When
      StringValue stringValue = integerValue.asString();

      // Then
      assertThat(stringValue.get()).isEqualTo(expectedValue);
      castFunctions.verify(() -> CastFunctions.castToString(same(integerValue)), Mockito.times(1));
    }
  }

  @Test
  public void testAsList() throws Exception {
    // Given
    IntegerValue integerValue = IntegerValueBuilder.build(123);
    try (MockedStatic<CastFunctions> castFunctions = Mockito.mockStatic(CastFunctions.class)) {
      List<?> expectedValue = List.of();
      castFunctions.when(() -> CastFunctions.castToList(integerValue)).thenReturn(expectedValue);

      // When
      ListValue listValue = integerValue.asList();

      // Then
      assertThat(listValue.get()).isSameAs(expectedValue);
      castFunctions.verify(() -> CastFunctions.castToList(same(integerValue)), Mockito.times(1));
    }
  }

  @Test
  public void testAsMap() throws Exception {
    // Given
    IntegerValue integerValue = IntegerValueBuilder.build(123);
    try (MockedStatic<CastFunctions> castFunctions = Mockito.mockStatic(CastFunctions.class)) {
      var expectedValue = new LinkedHashMap<>();
      castFunctions.when(() -> CastFunctions.castToMap(integerValue)).thenReturn(expectedValue);

      // When
      MapValue mapValue = integerValue.asMap();

      // Then
      assertThat(mapValue.get()).isSameAs(expectedValue);
      castFunctions.verify(() -> CastFunctions.castToMap(same(integerValue)), Mockito.times(1));
    }
  }

  @Test
  public void testEq() throws ResolveTemplateException {
    assertThat(IntegerValueBuilder.build(123).eq(IntegerValueBuilder.build(123)).get()).isTrue();
    assertThat(IntegerValueBuilder.build(123).eq(IntegerValueBuilder.build(125)).get()).isFalse();

    assertThat(IntegerValueBuilder.build(123).eq(RealValueBuilder.build(123.0)).get()).isTrue();
    assertThat(IntegerValueBuilder.build(123).eq(RealValueBuilder.build(123.1)).get()).isFalse();

    assertThat(IntegerValueBuilder.build(1).eq(BooleanValueBuilder.build(true)).get()).isFalse();
    assertThat(IntegerValueBuilder.build(123).eq(StringValueBuilder.build("123")).get()).isFalse();
    assertThat(IntegerValueBuilder.build(123).eq(ListValueBuilder.build(123)).get()).isFalse();
    assertThat(IntegerValueBuilder.build(123).eq(MapValueBuilder.build(123, 123)).get()).isFalse();
    assertThat(IntegerValueBuilder.build(0).eq(VoidValues.get()).get()).isFalse();
  }

  @Test
  public void testIsVoid() {
    assertThat(IntegerValueBuilder.build(123).isVoid().get()).isFalse();
    assertThat(IntegerValueBuilder.build(0).isVoid().get()).isFalse();
  }

  @Test
  public void testIsEmpty() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).isEmpty())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'isEmpty' is not applicable for value type integer. Expected string, List or map");
  }

  @Test
  public void testIsBlank() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).isBlank())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'isBlank' is not applicable for value type integer. Expected string");
  }

  @Test
  public void testCapitalizeFirstLetter() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).capitalizeFirstLetter())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'capitalizeFirstLetter' is not applicable for value type integer. Expected string");
  }

  @Test
  public void testInvert() throws ResolveTemplateException {
    assertThat(IntegerValueBuilder.build(123).invert().get()).isEqualTo(-123);
  }

  @Test
  public void testFetch() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).fetch(IntegerValueBuilder.build(0)))
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'fetch' is not applicable for value type integer. Expected map, list or string");
  }

  @Test
  public void testFind() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).find(IntegerValueBuilder.build(1)))
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'find' is not applicable for value type integer. Expected string or list");
  }

  @Test
  public void testIndex() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).index())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'index' is not applicable for this value");
  }

  @Test
  public void testIsFirst() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).isFirst())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'isFirst' is not applicable for this value");
  }

  @Test
  public void testIsLast() {
    assertThatThrownBy(() -> IntegerValueBuilder.build(123).isLast())
        .isExactlyInstanceOf(NotApplicableOperationException.class)
        .hasMessage("Operation 'isLast' is not applicable for this value");
  }
}
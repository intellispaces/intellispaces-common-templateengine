package tech.intellispaces.templates.template.expression.value;

import tech.intellispaces.templates.exception.ResolveTemplateException;

/**
 * Boolean value.
 */
public interface BooleanValue extends Value {

  boolean get();

  @Override
  BooleanValue invert() throws ResolveTemplateException;
}

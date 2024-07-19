package com.luizmedeirosn.futs3.shared.exceptions;

import java.io.Serial;

public class FormatterException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public FormatterException(String message) {
    super(message);
  }
}

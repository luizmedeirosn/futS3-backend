package com.luizmedeirosn.futs3.shared.exceptions;

import java.io.Serial;

public class PageableException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public PageableException(String message) {
    super(message);
  }
}

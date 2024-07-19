package com.luizmedeirosn.futs3.shared.exceptions;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(String message) {
    super(message);
  }
}

package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.entities.pks.PlayerParameterPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "tb_player_parameter")
public class PlayerParameter implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @EmbeddedId private PlayerParameterPK id;

  @Column(nullable = false)
  @Check(constraints = "score > 0 AND score <= 100")
  private Integer score;

  public PlayerParameter() {}

  public PlayerParameter(Player player, Parameter parameter, Integer score) {
    id = new PlayerParameterPK();
    id.setPlayer(player);
    id.setParameter(parameter);
    this.score = score;
  }

  public PlayerParameterPK getId() {
    return id;
  }

  public void setId(PlayerParameterPK id) {
    this.id = id;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PlayerParameter that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

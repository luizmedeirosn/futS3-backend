CREATE TABLE IF NOT EXISTS tb_player_picture (
  player_id bigint NOT NULL,
  content_type VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  content oid NOT NULL,

  PRIMARY KEY (player_id),
  FOREIGN KEY (player_id) REFERENCES tb_player(id)
);

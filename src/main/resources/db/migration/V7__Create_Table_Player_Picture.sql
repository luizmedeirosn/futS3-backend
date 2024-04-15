CREATE TABLE IF NOT EXISTS tb_player_picture (
  player_id bigint NOT NULL,
  content_type character varying(255),
  filename character varying(255),
  content oid,

  PRIMARY KEY (player_id),
  FOREIGN KEY (player_id) REFERENCES tb_player(id)
);

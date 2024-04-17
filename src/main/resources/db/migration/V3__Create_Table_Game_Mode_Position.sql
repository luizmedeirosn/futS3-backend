CREATE TABLE IF NOT EXISTS tb_game_mode_position
(
    game_mode_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,

    PRIMARY KEY (game_mode_id, position_id),
    FOREIGN KEY (game_mode_id) REFERENCES tb_game_mode (id),
    FOREIGN KEY (position_id) REFERENCES tb_position (id)
);

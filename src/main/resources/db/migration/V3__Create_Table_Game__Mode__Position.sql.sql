CREATE TABLE IF NOT EXISTS tb_gamemode_position
(
    gamemode_id BIGINT NOT NULL,
    position_id BIGINT NOT NULL,

    PRIMARY KEY (gamemode_id, position_id),
    FOREIGN KEY (gamemode_id) REFERENCES tb_gamemode (id),
    FOREIGN KEY (position_id) REFERENCES tb_position (id)
);

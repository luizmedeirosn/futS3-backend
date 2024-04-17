CREATE TABLE IF NOT EXISTS tb_player_parameter
(
    player_id    BIGINT  NOT NULL,
    parameter_id BIGINT  NOT NULL,
    score        INT NOT NULL,

    PRIMARY KEY (player_id, parameter_id),
    FOREIGN KEY (player_id) REFERENCES tb_player(id),
    FOREIGN KEY (parameter_id) REFERENCES tb_parameter(id),
    CHECK ((score > 0) AND (score <= 100))
);

CREATE TABLE IF NOT EXISTS tb_position_parameter
(
    position_id  BIGINT  NOT NULL,
    parameter_id BIGINT  NOT NULL,
    weight       INT NOT NULL,

    PRIMARY KEY (position_id, parameter_id),
    FOREIGN KEY (position_id) REFERENCES tb_position (id),
    FOREIGN KEY (parameter_id) REFERENCES tb_parameter (id),
    CHECK ((weight > 0) AND (weight <= 100))
);

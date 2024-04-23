CREATE TABLE IF NOT EXISTS tb_player_picture (
    player_id bigint,
    content_type VARCHAR(255),
    filename VARCHAR(255) ,
    content oid,

    PRIMARY KEY (player_id),
    FOREIGN KEY (player_id) REFERENCES tb_player(id)
);

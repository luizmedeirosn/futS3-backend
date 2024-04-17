INSERT INTO tb_position_parameter
    (position_id, parameter_id, weight)
VALUES
    -- ATACANTE
    (1, 1, 10),  -- ACELERAÇÃO
    (1, 2, 10),  -- ACERTO DE PASSE
    (1, 3, 10),  -- AGILIDADE
    (1, 5, 10),  -- ASSISTÊNCIA
    (1, 6, 10),  -- CHUTE DE CURTA DISTÂNCIA
    (1, 7, 10),  -- CHUTE DE LONGA DISTÂNCIA
    (1, 9, 10),  -- CONDUÇÃO
    (1, 14, 10), -- FINALIZAÇÃO
    (1, 24, 10), -- RESISTÊNCIA
    (1, 25, 10), -- VELOCIDADE

    -- LATERAL ESQUERDO
    (5, 1, 25),  -- ACERTO DE PASSE
    (5, 9, 25),  -- CONDUÇÃO
    (5, 12, 25), -- DESARME
    (5, 23, 25), -- POTÊNCIA DO CHUTE

    -- MEIA ATACANTE
    (6, 1, 30),  -- ACELERAÇÃO
    (6, 3, 20),  -- AGILIDADE
    (6, 5, 20),  -- ASSISTÊNCIA
    (6, 9, 30); -- CONDUÇÃO

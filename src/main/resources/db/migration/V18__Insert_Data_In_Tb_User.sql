INSERT INTO tb_user
    (username, email, password, roles)
VALUES
    ('master', 'mastern@futs3.com', '$argon2id$v=19$m=16384,t=2,p=1$/BDS00loe8QjBKOpOi6ivg$mZ+nIK/SosVwy49YtfQa0lzR/MPifqwAP61ZTIsnabU', 'ROLE_ADMIN'), -- PASSWORD: 'master'
    ('user', 'user@futs3.com', '$argon2id$v=19$m=16384,t=2,p=1$+YC/lzVzhTsgG8R464ITUA$wrpsf5w3yoGyGmtM8Z36UOjtKJooC1A1bu/4ZDTYLNo', 'ROLE_USER'); -- PASSWORD: 'user'

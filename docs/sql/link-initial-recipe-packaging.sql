-- Script OPERACIONAL OPT-IN. Não é migração Flyway nem executado automaticamente.
-- Aplicar somente após confirmar IDs/nomes no banco de destino e backup.
-- Não manipula stocks, zero_cost_entry ou stock_movements.
BEGIN;
CREATE TEMP TABLE _brata_packaging_map (
    output_id BIGINT NOT NULL,
    packaging_id BIGINT NOT NULL,
    output_name TEXT NOT NULL,
    packaging_name TEXT NOT NULL,
    PRIMARY KEY (output_id, packaging_id)
) ON COMMIT DROP;
INSERT INTO _brata_packaging_map VALUES
    (53, 46, 'Tapenade de Azeitona Preta', 'pote lapianezza 210ml'),
    (53, 33, 'Tapenade de Azeitona Preta', 'Rotulo Tapenade de azeitona preta'),
    (53, 39, 'Tapenade de Azeitona Preta', 'Lacre - Tapenade de Azeitona Preta'),
    (54, 46, 'Tapenade de Azeitona Verde', 'pote lapianezza 210ml'),
    (54, 34, 'Tapenade de Azeitona Verde', 'Rotulo Tapenade de azeitona verde'),
    (54, 42, 'Tapenade de Azeitona Verde', 'Lacre - Tapenade de Azeitona Verde'),
    (55, 46, 'Geleia de Melão', 'pote lapianezza 210ml'),
    (55, 44, 'Geleia de Melão', 'Rotulo Geleia de melao'),
    (55, 52, 'Geleia de Melão', 'Lacre - Geleia de Melão'),
    (56, 46, 'Pesto de Tomate Seco', 'pote lapianezza 210ml'),
    (56, 36, 'Pesto de Tomate Seco', 'Rotulo Pesto de tomate seco'),
    (56, 35, 'Pesto de Tomate Seco', 'Lacre - Pesto de Tomate Seco'),
    (57, 46, 'Mousse de Pimentão', 'pote lapianezza 210ml'),
    (57, 37, 'Mousse de Pimentão', 'Rotulo Mousse de pimentao'),
    (57, 45, 'Mousse de Pimentão', 'Lacre - Mousse de Pimentão'),
    (58, 46, 'Caramelo à Flor de Sal', 'pote lapianezza 210ml'),
    (58, 43, 'Caramelo à Flor de Sal', 'Rotulo Caramelo a flor de sal'),
    (58, 51, 'Caramelo à Flor de Sal', 'Lacre - Caramelo à Flor de Sal'),
    (59, 46, 'Caviar de Berinjela', 'pote lapianezza 210ml'),
    (59, 38, 'Caviar de Berinjela', 'Rotulo Caviar de berinjela'),
    (59, 47, 'Caviar de Berinjela', 'Lacre - Caviar de Berinjela'),
    (60, 46, 'Cebola Confitada', 'pote lapianezza 210ml'),
    (60, 40, 'Cebola Confitada', 'Rotulo Cebola confitada'),
    (60, 49, 'Cebola Confitada', 'Lacre - Cebola Confitada'),
    (61, 46, 'Chutney de Figo', 'pote lapianezza 210ml'),
    (61, 41, 'Chutney de Figo', 'Rotulo Chutney de figo'),
    (61, 50, 'Chutney de Figo', 'Lacre - Chutney de Figo');
DO $$
BEGIN
    IF (SELECT COUNT(*) FROM _brata_packaging_map) <> 27 OR
        (SELECT COUNT(*) FROM _brata_packaging_map s
         JOIN items o ON o.id=s.output_id AND o.name=s.output_name AND o.type='FINISHED_PRODUCT' AND o.base_unit='UN'
         JOIN items p ON p.id=s.packaging_id AND p.name=s.packaging_name AND p.type='PACKAGING' AND p.base_unit='UN'
         JOIN recipes r ON r.output_item_id=o.id) <> 27
    THEN
        RAISE EXCEPTION 'Mapeamento de embalagens divergente; nenhum vínculo será criado';
    END IF;
    IF EXISTS (
        SELECT 1 FROM _brata_packaging_map s
        JOIN recipes r ON r.output_item_id=s.output_id
        JOIN recipe_items ri ON ri.recipe_id=r.id AND ri.item_id=s.packaging_id
        WHERE ri.quantity <> 1
    ) THEN
        RAISE EXCEPTION 'Já existe consumo diferente de 1 por pote; revisar manualmente';
    END IF;
END $$;
INSERT INTO recipe_items (recipe_id, item_id, quantity)
SELECT r.id, s.packaging_id, 1
FROM _brata_packaging_map s
JOIN recipes r ON r.output_item_id=s.output_id
WHERE NOT EXISTS (
    SELECT 1 FROM recipe_items ri WHERE ri.recipe_id=r.id AND ri.item_id=s.packaging_id
);
COMMIT;
-- Esperado: 27 vínculos ao total, 3 por receita. Não informa quantos foram inseridos neste run.
SELECT r.name, COUNT(*) AS embalagens
FROM recipes r JOIN recipe_items ri ON ri.recipe_id=r.id
JOIN items i ON i.id=ri.item_id
WHERE i.type='PACKAGING'
GROUP BY r.id, r.name ORDER BY r.name;

INSERT INTO professional_category (id, name, description, enabled, created_at, updated_at)
VALUES (1, 'Cardiología', 'Especialistas en salud cardiovascular', true, NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

INSERT INTO professional (id, category_id, first_name, last_name, enabled, created_at, updated_at)
VALUES (1, 1, 'Juan', 'Pérez', true, NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

INSERT INTO weekly_schedule (id, professional_id, day_of_week, start_time, end_time, slot_duration_minutes, enabled, created_at, updated_at)
VALUES (1, 1, 'MONDAY', '08:00:00', '12:00:00', 30, true, NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;